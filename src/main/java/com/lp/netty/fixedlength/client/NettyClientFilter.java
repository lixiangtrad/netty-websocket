package com.lp.netty.fixedlength.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
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
        // 自定义数据分隔符
        ChannelPipeline ph = ch.pipeline();
        // 使用定长解决粘包问题
        ph.addLast("framer", new FixedLengthFrameDecoder(3));
        /**
         * 解码和编码，应和服务端一致
         */
        ph.addLast("decoder", new StringDecoder());
        ph.addLast("encoder", new StringEncoder());
        //客户端的逻辑
        ph.addLast("handler", new NettyClientHandler());

    }
}
