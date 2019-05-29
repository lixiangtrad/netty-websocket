package com.lp.netty.fixedlength.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.UnsupportedEncodingException;
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
                System.out.print("enter message send to server > ");
                String line = s.nextLine();
                byte[] bs = new byte[5];
                byte[] temp = new byte[0];
                try {
                    temp = line.getBytes("UTF-8");
                    if (temp.length <= 5) {
                        for (int i = 0; i < temp.length; i++) {
                            bs[i] = temp[i];
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (temp.length <= 5) {
                    for (int i = 0; i < temp.length; i++) {
                        bs[i] = temp[i];
                    }
                }
                ch.writeAndFlush(Unpooled.copiedBuffer(bs));
                System.out.println("客户端发送数据:" + line);
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
