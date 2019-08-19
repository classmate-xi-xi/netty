package com.example.demo.Socket;

import io.netty.channel.ChannelHandlerContext;

import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 每当从服务端读到客户端写入信息时,将信息转发给其他客户端的Channel.
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }

}
