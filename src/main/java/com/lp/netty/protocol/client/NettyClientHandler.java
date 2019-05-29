package com.lp.netty.protocol.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * <p/>
 * <li>Description: 客户端业务逻辑实现</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-28 11:59</li>
 * <li>Version: V1.0</li>
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        try{
            System.out.println("client receive protocol content : " + msg);
            String message = NettyClientHandler.ProtocolParser.parse(msg);
            if(null == message){
                System.out.println("error response from server");
                return ;
            }
            System.out.println("from server : " + message);
        }finally{
            // 用于释放缓存。避免内存溢出
            ReferenceCountUtil.release(msg);
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("正在连接... ");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接关闭! ");
        super.channelInactive(ctx);
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
