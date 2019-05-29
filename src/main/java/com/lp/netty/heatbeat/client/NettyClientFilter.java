package com.lp.netty.heatbeat.client;

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
 * <li>Description: Netty客户端 过滤器</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-28 11:58</li>
 * <li>Version: V1.0</li>
 */
public class NettyClientFilter extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        /**
         * 解码和编码，应和服务端一致
         */
        ChannelPipeline ph = ch.pipeline();
        /**
         * 入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
         * 因为服务端设置的超时时间是5秒，所以设置4秒
         */

        ph.addLast( new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
        ph.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        ph.addLast("decoder", new StringDecoder());
        ph.addLast("encoder", new StringEncoder());
        //客户端的逻辑
        ph.addLast("handler", new NettyClientHandler());

    }
}
