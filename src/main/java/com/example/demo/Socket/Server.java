package com.example.demo.Socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {
    private int port;

    public Server(int port){
        this.port = port;
    }

    public void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{

            ServerBootstrap sBootstrap = new ServerBootstrap();

            sBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleChatServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("SimpleChatServer 启动了");

            ChannelFuture future = sBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } finally {

            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();

            System.out.println("SimpleChatServer 关闭了");
        }

    }
    public class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();

            pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
            pipeline.addLast("decoder", new StringDecoder());
            pipeline.addLast("encoder", new StringEncoder());
            pipeline.addLast("handler", new ServerHandler());

            System.out.println("SimpleChatClient:" + ch.remoteAddress()+"连接上服务器");
        }
    }

    public static void main(String[] args) throws Exception {
        new Server(8000).run();
    }
}

