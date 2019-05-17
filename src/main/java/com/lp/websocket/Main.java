package com.lp.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
/**
 * <p/>
 * <li>Description: 程序的入口，负责启动应用</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-01-27 16:34</li>
 */
public class Main {

    public static void main(String[] args) {
        /**
         *  初始化线程组,构建线程组的时候，如果不传递参数，则默认构建的线程组线程数是CPU核心数量。
         *  bossGroup监听线程组，监听客户端请求
         *  workGroup处理客户端相关操作线程组，负责处理与客户端的数据通讯
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            // 初始化服务的配置
            ServerBootstrap b = new ServerBootstrap();
            //绑定线程组
            b.group(bossGroup, workGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new MyWebSocketChannelHandler());
            System.out.println("服务端开启等待客户端连接....");
            Channel ch = b.bind(8888).sync().channel();
            ch.closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //优雅的退出程序
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
