package com.lp.netty.delimiter.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * <p/>
 * <li>Description: Netty客户端 </li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-28 11:31</li>
 * <li>Version: V1.0</li>
 */
public class NettyClient {

    // 处理请求和处理服务端响应的线程组
    private EventLoopGroup group = null;
    // 客户端启动相关配置信息
    private Bootstrap bootstrap = null;

    private Channel ch = null;


    public NettyClient(int port, String host) {
        init(port, host);
    }

    private void init(int port, String host) {
        try {
            group = new NioEventLoopGroup();
            bootstrap = new Bootstrap();
            // 绑定线程组
            bootstrap.group(group);
            // 设定通讯模式为NIO
            bootstrap.channel(NioSocketChannel.class);
            /**
             * 客户端的Bootstrap没有childHandler方法。只有handler方法。
             * 方法含义等同ServerBootstrap中的childHandler
             * 在客户端必须绑定处理器，也就是必须调用handler方法。
             * 服务器必须绑定处理器，必须调用childHandler方法。
             */
            bootstrap.handler(new NettyClientFilter());
            ch = bootstrap.connect(host, port).sync().channel();
            Scanner s = null;
            while (true) {
                s = new Scanner(System.in);
                System.out.print("enter message send to server (enter 'exit' for close client) > ");
                String line = s.nextLine();
                if ("exit".equals(line)) {
                    // addListener - 增加监听，当某条件满足的时候，触发监听器。
                    // ChannelFutureListener.CLOSE - 关闭监听器，代表ChannelFuture执行返回后，关闭连接。
                    ch.writeAndFlush(line).addListener(ChannelFutureListener.CLOSE);
                    System.out.println("客户端关闭连接");
                    break;
                }
                ch.writeAndFlush(line+"\r\n");
                System.out.println("客户端发送数据:" + line+"  \r  \n");
                TimeUnit.SECONDS.sleep(1);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyClient(9999, "localhost");
    }
}
