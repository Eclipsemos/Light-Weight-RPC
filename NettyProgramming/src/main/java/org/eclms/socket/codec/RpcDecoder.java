package org.eclms.socket.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.eclms.common.constants.MsgType;
import org.eclms.common.constants.ProtocolConstants;
import org.eclms.socket.serialization.RpcSerialization;
import org.eclms.socket.serialization.SerializationFactory;

import java.util.List;

public class RpcDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() < ProtocolConstants.HEADER_TOTAL_LEN) {
            return;
        }

        in.markReaderIndex();

        short magic = in.readShort();
        if (magic != ProtocolConstants.MAGIC)
            throw new IllegalArgumentException("magic number is illegal, " + magic);

        byte version = in.readByte();
        byte msgType = in.readByte();
        byte status = in.readByte();
        long requestId = in.readLong();
        final int len = in.readInt();
        if (in.readableBytes() < len) {
            in.resetReaderIndex();
            return;
        }
        final byte[] bytes = new byte[len];
        in.readBytes(bytes);
        final String serialization = new String(bytes);

        int dataLength = in.readInt();

        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        MsgType msgTypeEnum = MsgType.findByType(msgType);
        if (msgTypeEnum == null) {
            return;
        }

        MsgHeader header = new MsgHeader();
        header.setMagic(magic);
        header.setVersion(version);
        header.setStatus(status);
        header.setRequestId(requestId);
        header.setMsgLen(msgType);
        header.setSerialization(bytes);
        header.setSerializationLen(len);
        header.setMsgLen(dataLength);
        RpcSerialization rpcSerialization = SerializationFactory.get(org.eclms.common.constants.RpcSerialization.get(serialization));
        final String body = rpcSerialization.deserialize(data, String.class);
        RpcProtocol protocol = new RpcProtocol<>();
        protocol.setHeader(header);
        protocol.setBody(body);
        out.add(protocol);
    }
}
