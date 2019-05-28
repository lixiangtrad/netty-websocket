package com.lp.netty.first.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
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

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();
        // 以("\n")为结尾分割的 解码器
        ph.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        // 解码和编码，应和客户端一致
        ph.addLast("decoder", new StringDecoder());
        ph.addLast("encoder", new StringEncoder());
        // 服务端业务逻辑
        ph.addLast("handler", new NettyServerHandler());
    }
}
