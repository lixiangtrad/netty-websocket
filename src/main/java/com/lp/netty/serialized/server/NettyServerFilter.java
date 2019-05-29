package com.lp.netty.serialized.server;

import com.lp.netty.serialized.client.NettyClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


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
        /**
         * 对象系列化的编码和解码器
         */
        ph.addLast("decoder",  new ObjectDecoder(1024 * 1024,
                ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
        ph.addLast("encoder", new ObjectEncoder());
        //客户端的逻辑
        ph.addLast("handler", new NettyClientHandler());
    }
}
