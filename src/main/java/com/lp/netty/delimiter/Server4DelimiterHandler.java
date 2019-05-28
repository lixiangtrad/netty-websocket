package com.lp.netty.delimiter;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class Server4DelimiterHandler extends SimpleChannelInboundHandler<String> {

    // 业务处理逻辑
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        String message = msg.toString();
        System.out.println("from client : " + message);
        String line = "server message $E$ test delimiter handler!! $E$ second message $E$";
        ctx.writeAndFlush(Unpooled.copiedBuffer(line.getBytes("UTF-8")));
    }


    // 异常处理逻辑
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("server exceptionCaught method run...");
        // cause.printStackTrace();
        ctx.close();
    }

}
