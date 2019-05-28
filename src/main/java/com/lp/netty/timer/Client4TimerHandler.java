package com.lp.netty.timer;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class Client4TimerHandler extends SimpleChannelInboundHandler<String> {


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("from server : ClassName - " + msg.getClass().getName()
				+ " ; message : " + msg.toString());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("client exceptionCaught method run...");
		cause.printStackTrace();
		ctx.close();
	}

	/**
	 * 当连接建立成功后，出发的代码逻辑。
	 * 在一次连接中只运行唯一一次。
	 * 通常用于实现连接确认和资源初始化的。
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("client channel active");
	}

}
