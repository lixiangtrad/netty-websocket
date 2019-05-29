package com.lp.netty.timer.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * <p/>
 * <li>Description: Netty服务端
 * 参考：https://github.com/xuwujing/Netty-study
 * </li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-28 11:01</li>
 * <li>Version: V1.0</li>
 */
public class NettyServer {
    /**
     * 监听线程组，监听客户端请求
     */
    private EventLoopGroup acceptorGroup = null;
    /**
     * 处理客户端相关操作线程组，负责处理与客户端的数据通讯
     */
    private EventLoopGroup clientGroup = null;
    /**
     * 服务启动相关配置信息
     */
    private ServerBootstrap bootstrap = null;

    public NettyServer(int port) {
        init(port);
    }

    private void init(int port) {

        ChannelFuture future = null;
        // 初始化线程组,构建线程组的时候，如果不传递参数，则默认构建的线程组线程数是CPU核心数量。
        acceptorGroup = new NioEventLoopGroup();
        clientGroup = new NioEventLoopGroup();
        // 初始化服务的配置
        bootstrap = new ServerBootstrap();
        try {
            // 绑定线程组
            bootstrap.group(acceptorGroup, clientGroup);
            // 设定通讯模式为NIO， 同步非阻塞
            bootstrap.channel(NioServerSocketChannel.class);
            // 设定缓冲区大小， 缓存区的单位是字节。
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            // SO_SNDBUF发送缓冲区，SO_RCVBUF接收缓冲区，SO_KEEPALIVE开启心跳监测（保证连接有效）
            bootstrap.option(ChannelOption.SO_SNDBUF, 16 * 1024)
                    .option(ChannelOption.SO_RCVBUF, 16 * 1024)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            /**
             * 设置过滤器
             * childHandler是服务的Bootstrap独有的方法。是用于提供处理对象的。
             * 可以一次性增加若干个处理逻辑。是类似责任链模式的处理方式。
             * 增加A，B两个处理逻辑，在处理客户端请求数据的时候，根据A-》B顺序依次处理。
             *
             */
            bootstrap.childHandler(new NettyServerFilter());
            /**
             * bind方法 - 绑定监听端口的。ServerBootstrap可以绑定多个监听端口。 多次调用bind方法即可
             * sync - 开始监听逻辑。 返回一个ChannelFuture。 返回结果代表的是监听成功后的一个对应的未来结果
             * 可以使用ChannelFuture实现后续的服务器和客户端的交互。
             */
            future = bootstrap.bind(port).sync();
            System.out.println("服务端启动成功...");
            /**
             * 监听服务器 关闭监听
             */
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (future != null) {
                try {
                    future.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /**
             * shutdownGracefully - 方法是一个安全关闭的方法。可以保证不放弃任何一个已接收的客户端请求。
             */
            acceptorGroup.shutdownGracefully();
            clientGroup.shutdownGracefully();

        }
    }

    public static void main(String[] args) {
        new NettyServer(9999);
    }
}
