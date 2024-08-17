package org.eclms.socket.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.eclms.socket.serialization.RpcSerialization;
import org.eclms.socket.serialization.SerializationFactory;

public class RpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol<Object> msg, ByteBuf byteBuf) throws Exception {
        MsgHeader header = msg.getHeader();
        byteBuf.writeShort(header.getMagic());
        byteBuf.writeByte(header.getVersion());
        byteBuf.writeByte(header.getMsgType());
        byteBuf.writeByte(header.getStatus());
        byteBuf.writeLong(header.getRequestId());
        byteBuf.writeInt(header.getSerializationLen());
        final byte[] ser = header.getSerialization();
        final String serialization = new String(ser);
        byteBuf.writeBytes(ser);
        //header write done
        RpcSerialization rpcSerialization = SerializationFactory.get(org.eclms.common.constants.RpcSerialization.get(serialization));
        byte[] data = rpcSerialization.serialize(msg.getBody());

        byteBuf.writeInt(data.length);

        byteBuf.writeBytes(data);

    }
}
