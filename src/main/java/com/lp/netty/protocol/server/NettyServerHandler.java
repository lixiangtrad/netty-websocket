package com.lp.netty.protocol.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;

/**
 * <p/>
 * <li>Description: 服务端业务逻辑</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-28 11:05</li>
 * <li>Version: V1.0</li>
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 服务端接受到消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception{

        System.out.println("server receive protocol content : " + msg);
        String message = NettyServerHandler.ProtocolParser.parse(msg);
        if(null == message){
            System.out.println("error request from client");
            return ;
        }
        System.out.println("from client : " + message);
        String line = "server message";
        //将line 转成指定协议格式的消息
        line = NettyServerHandler.ProtocolParser.transferTo(line);
        System.out.println("server send protocol content : " + line);
        ctx.writeAndFlush(line);
    }


    /**
     * 建立连接时，返回消息
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
        ctx.writeAndFlush("客户端" + InetAddress.getLocalHost().getHostName() + "成功与服务端建立连接！ \n");
        super.channelActive(ctx);
    }

    static class ProtocolParser{
        public static String parse(String message){
            String[] temp = message.split("HEADBODY");
            temp[0] = temp[0].substring(4);
            temp[1] = temp[1].substring(0, (temp[1].length()-4));
            int length = Integer.parseInt(temp[0].substring(temp[0].indexOf(":")+1));
            if(length != temp[1].length()){
                return null;
            }
            return temp[1];
        }
        public static String transferTo(String message){
            message = "HEADcontent-length:" + message.length() + "HEADBODY" + message + "BODY";
            return message;
        }
    }

}
