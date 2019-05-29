package com.lp.netty.serialized.client;

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
 * <li>Description: Netty客户端 过滤器</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-28 11:58</li>
 * <li>Version: V1.0</li>
 */
public class NettyClientFilter extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
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
