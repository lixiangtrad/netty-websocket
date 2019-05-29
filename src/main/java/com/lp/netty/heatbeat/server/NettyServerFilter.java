package com.lp.netty.heatbeat.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


/**
 * <p/>
 * <li>Description: Netty 服务端过滤器</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-28 11:03</li>
 * <li>Version: V1.0</li>
 */
public class NettyServerFilter extends ChannelInitializer<SocketChannel> {

    /**
     * ChannelInitializer - 用于提供处理器的一个模型对象。
     * 其中定义了一个方法，initChannel方法。
     * 方法是用于初始化处理逻辑责任链条的。
     * 可以保证服务端的Bootstrap只初始化一次处理器，尽量提供处理逻辑的重用。
     * 避免反复的创建处理器对象。节约资源开销。
     *
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();
        // 以("\n")为结尾分割的 解码器
        ph.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
        ph.addLast(new IdleStateHandler(5,0,0, TimeUnit.SECONDS));
        ph.addLast("decoder", new StringDecoder());
        ph.addLast("encoder", new StringEncoder());
        // 服务端业务逻辑
        ph.addLast("handler", new NettyServerHandler());
    }
}
