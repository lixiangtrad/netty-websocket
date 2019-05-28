package com.lp.netty.first;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * <p/>
 * <li>Description: 客户端处理消息Handler</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-27 22:42</li>
 */
public class Client4HelloWorldHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf readBuffer = (ByteBuf) msg;
            byte[] tempDatas = new byte[readBuffer.readableBytes()];
            readBuffer.readBytes(tempDatas);
            System.out.println("from server : " + new String(tempDatas, "UTF-8"));
        } finally {
            // 用于释放缓存。避免内存溢出
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 异常处理
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("client exceptionCaught method run...");
        cause.printStackTrace();
        ctx.close();
    }

    @Override // 断开连接时执行
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("channelInactive method run...");
    }

    @Override // 连接通道建立成功时执行
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channelActive method run...");
    }

    @Override // 每次读取完成时执行
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("channelReadComplete method run...");
    }

}
