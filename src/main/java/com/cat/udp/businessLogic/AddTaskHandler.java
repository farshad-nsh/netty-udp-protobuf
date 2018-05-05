package com.cat.udp.businessLogic;

import com.cat.udp.UDP;
import io.netty.channel.ChannelHandlerContext;

public class AddTaskHandler implements MyMessageHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, UDP.Update msg) throws Exception {
        System.out.println("inside AddTaskHandler id :"+msg.getId());
        System.out.println("inside AddTaskHandler text :"+msg.getText());
        System.out.println("inside AddTaskHandler id :"+msg.getCmd());



    }
}
