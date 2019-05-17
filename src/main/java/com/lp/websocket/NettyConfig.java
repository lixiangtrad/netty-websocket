package com.lp.websocket;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * <p/>
 * <li>Description: 存储整个netty工程的全局配置</li>
 * <li>@author: lipan@cechealth.cn</li>
 * <li>Date: 2019-01-27 16:30</li>
 */
public class NettyConfig {

    /**
     * 存储每一个客户端接入进来时的channel对象
     */
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
