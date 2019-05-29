package com.lp.netty.serialized.server;

import com.lp.netty.serialized.RequestMessage;
import com.lp.netty.utils.GzipUtils;
import com.lp.netty.serialized.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetAddress;

/**
 * <p/>
 * <li>Description: 服务端业务逻辑</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-05-28 11:05</li>
 * <li>Version: V1.0</li>
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 服务端接受到消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{

        System.out.println("from client : ClassName - " + msg.getClass().getName()
                + " ; message : " + msg.toString());
        if(msg instanceof RequestMessage){
            RequestMessage request = (RequestMessage)msg;
            byte[] attachment = GzipUtils.unzip(request.getAttachment());
            System.out.println(new String(attachment));
        }
        ResponseMessage response = new ResponseMessage(0L, "test response");
        ctx.writeAndFlush(response);
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

}
