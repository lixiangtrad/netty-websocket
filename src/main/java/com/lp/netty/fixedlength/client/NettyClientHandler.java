package com.lp.netty.fixedlength.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * <p/>
 * <li>Description: 客户端业务逻辑实现</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-28 11:59</li>
 * <li>Version: V1.0</li>
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = msg.toString();
        System.out.println("from server : " + message.trim());
        // 用于释放缓存。避免内存溢出
        ReferenceCountUtil.release(msg);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("正在连接... ");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接关闭! ");
        super.channelInactive(ctx);
    }
}
