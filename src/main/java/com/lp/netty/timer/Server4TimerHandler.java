/**
 * @Sharable注解 - 
 *  代表当前Handler是一个可以分享的处理器。也就意味着，服务器注册此Handler后，可以分享给多个客户端同时使用。
 *  如果不使用注解描述类型，则每次客户端请求时，必须为客户端重新创建一个新的Handler对象。
 *  
 */
package com.lp.netty.timer;

import com.lp.netty.serialized.ResponseMessage;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class Server4TimerHandler extends SimpleChannelInboundHandler<String> {
	
	// 业务处理逻辑
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("from client : ClassName - " + msg.getClass().getName()
				+ " ; message : " + msg);
		ResponseMessage response = new ResponseMessage(0L, "test response");
		ctx.writeAndFlush(response);
	}

	// 异常处理逻辑
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("server exceptionCaught method run...");
		// cause.printStackTrace();
		ctx.close();
	}

}
