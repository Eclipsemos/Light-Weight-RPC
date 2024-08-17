package org.eclms.socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.eclms.common.constants.ProtocolConstants;
import org.eclms.common.constants.RpcSerialization;
import org.eclms.socket.codec.MsgHeader;
import org.eclms.socket.codec.RpcDecoder;
import org.eclms.socket.codec.RpcEncoder;
import org.eclms.socket.codec.RpcProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

    //Create Logger for Client class using Factory mode
    private Logger logger = LoggerFactory.getLogger(Client.class);

    private final String host;
    private final Integer port;


    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;

    private ChannelFuture channelFuture;

    public Client(String host, Integer port) throws InterruptedException {
        this.host = host;
        this.port = port;
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup(4);

        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new RpcEncoder())
                                .addLast(new RpcDecoder());
                    }
                });
        channelFuture = bootstrap.connect(host, port).sync();
    }

    public void sendRequest(Object o) {
        channelFuture.channel().writeAndFlush(o);
    }

    public static void main(String[] args) throws Exception {
        final Client nettyClient = new Client("127.0.0.1",8081);
        final RpcProtocol rpcProtocol = new RpcProtocol();

        MsgHeader header = new MsgHeader();
        long requestId = 123;
        header.setMagic(ProtocolConstants.MAGIC);
        header.setVersion(ProtocolConstants.VERSION);
        header.setRequestId(requestId);

        final byte[] serialization = RpcSerialization.JSON.name.getBytes();
    }
}
