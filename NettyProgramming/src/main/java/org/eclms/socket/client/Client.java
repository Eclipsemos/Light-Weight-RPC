package org.eclms.socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
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
                               .addLast()
                               .addLast();
                   }
                });
        channelFuture = bootstrap.connect(host, port).sync();
    }

    public void sendRequest(Object o) {
        channelFuture.channel().writeAndFlush(o);
    }

}
