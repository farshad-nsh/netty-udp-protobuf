package com.cat.udp.businessLogic;

import com.cat.udp.UDP;
import io.netty.channel.ChannelHandlerContext;

public class WorkFlowHandler implements MyMessageHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, UDP.Update msg) throws Exception {
        System.out.println("inside WorkFlowHandler id :"+msg.getId());
        System.out.println("inside WorkFlowHandler text :"+msg.getText());
        System.out.println("inside WorkFlowHandler id :"+msg.getCmd());
        ctx.channel().writeAndFlush(msg);
    }
}
