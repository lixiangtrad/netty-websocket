package com.lp.netty.fixedlength.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;

/**
 * <p/>
 * <li>Description: 服务端业务逻辑</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-28 11:05</li>
 * <li>Version: V1.0</li>
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<Object> {

    /**
     * 服务端接受到消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {

        String message = msg.toString();
        System.out.println("from client : " + message.trim());
        String line = "ok ";
        // 写操作自动释放缓存，避免内存溢出问题。
        try {
            ctx.writeAndFlush(Unpooled.copiedBuffer(line.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 建立连接时，返回消息
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
        ctx.writeAndFlush("客户端" + InetAddress.getLocalHost().getHostName() + "成功与服务端建立连接！ \n");
        super.channelActive(ctx);
    }

}
