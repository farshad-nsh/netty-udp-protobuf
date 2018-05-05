package com.cat.udp.businessLogic;

import com.cat.udp.UDP;
import com.cat.udp.common.CMD;
import io.netty.channel.*;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

public class Multiplexer extends ChannelInboundHandlerAdapter {

    private HashMap<Integer, MyMessageHandler> handlerMap = new HashMap<>();

    public Multiplexer() {
        System.out.println("inside constructor for multiplexer");
        handlerMap.put(CMD.AddTask.value(), new AddTaskHandler());
        handlerMap.put(CMD.PassToWorkFlow.value(), new WorkFlowHandler());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        UDP.Update message=(UDP.Update)msg;
        int cmd = message.getCmd();
        MyMessageHandler handler = handlerMap.get(cmd);
       // System.out.println("CMD inside Multiplexer:" +cmd + ",handler:" + handler);
        handler.messageReceived(ctx,(UDP.Update)msg);

        super.channelRead(ctx, msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channel active inside Multiplexer!");
        ctx.fireChannelActive();
    }


}
