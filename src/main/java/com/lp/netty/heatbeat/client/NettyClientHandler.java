package com.lp.netty.heatbeat.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 * <p/>
 * <li>Description: 客户端业务逻辑实现</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-28 11:59</li>
 * <li>Version: V1.0</li>
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端请求的心跳命令
     */
    private static final ByteBuf HEARTBEAT_SEQUENCE =
            Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hb_request", CharsetUtil.UTF_8));

    /**
     * 空闲次数
     */
    private int idle_count = 1;

    /**
     * 发送次数
     */
    private int count = 1;

    /**
     * 循环次数
     */
    private int fcount = 1;

    /**
     * 建立连接时
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("建立连接时：" + new Date());
        ctx.fireChannelActive();
    }

    /**
     * 关闭连接时
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("关闭连接时：" + new Date());
    }

    /**
     * 心跳请求处理,每4秒发送一次心跳请求;
     * userEventTriggered是Netty 处理心跳超时事件，在IdleStateHandler设置超时时间，如果达到了，就会直接调用该方法。
     * 如果没有超时则不调用。我们重写该方法的话，就可以自行进行相关的业务逻辑处理了。
     *
     * @param ctx
     * @param obj
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        System.out.println("循环请求的时间：" + new Date() + "，次数" + fcount);
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            //如果写通道处于空闲状态,就发送心跳命令
            if (IdleState.WRITER_IDLE.equals(event.state())) {
                //设置发送次数
                if (idle_count <= 3) {
                    idle_count++;
                    //netty内置的ByteBuf
                    ctx.channel().writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());
                } else {
                    System.out.println("不再发送心跳请求了！");
                }
                fcount++;
            }
        }
    }

    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("第" + count + "次" + ",客户端接受的消息:" + msg);
        count++;
    }


}
