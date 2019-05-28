package com.lp.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * <p/>
 * <li>Description: 初始化连接时候的各个组件
 *                  Netty服务端 过滤器</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-01-27 16:32</li>
 */
public class MyWebSocketChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel e) {
        e.pipeline().addLast("http-codec", new HttpServerCodec());
        e.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        e.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        e.pipeline().addLast("handler", new MyWebSocketHandler());
    }

}