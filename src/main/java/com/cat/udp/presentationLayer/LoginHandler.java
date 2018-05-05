package com.cat.udp.presentationLayer;

import com.cat.udp.UDP;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LoginHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UDP.Update  message=(UDP.Update)msg;
        System.out.println("id in loginHandler is:"+message.getId());
        System.out.println("text in loginHandler is:"+message.getText());
        System.out.println("command in loginHandler is:"+message.getCmd());
        System.out.println("-------go to next handler-----------");

        if (String.valueOf(message.getId()).equals("18387")){
            //user is allowed to use the services
            super.channelRead(ctx, msg);
        }
        /*
       The default behavior of channelRead in ChannelInboundHandlerAdapter
       is forward the msg to the next ChannelInboundHandler.
       If there is no other handlers care about the msg, then there is no need to call it.
        */
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channel active inside LoginHandler!");
        ctx.fireChannelActive();
    }

}

