package com.lp.netty.delimiter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

public class Client4DelimiterHandler extends SimpleChannelInboundHandler<String> {

	@Override
	public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		try{
			String message = msg.toString();
			System.out.println("from server : " + message);
		}finally{
			// 用于释放缓存。避免内存溢出
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("client exceptionCaught method run...");
		// cause.printStackTrace();
		ctx.close();
	}

}
