package com.cat.udp.businessLogic;

import com.cat.udp.UDP;
import io.netty.channel.ChannelHandlerContext;

public interface MyMessageHandler {
    void messageReceived(ChannelHandlerContext ctx, UDP.Update msg)
            throws Exception;
}
