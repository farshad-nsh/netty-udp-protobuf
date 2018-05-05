package com.cat.udp.presentationLayer;

import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetStateChangeHandler extends ChannelDuplexHandler {
    private static final Logger log = LoggerFactory
            .getLogger(NetStateChangeHandler.class);


    /**
     * Calls ChannelHandlerContext.fireChannelUnregistered() to forward to the next
     * ChannelInboundHandler in the ChannelPipeline
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelRegistered");
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelUnregistered");
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive");
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive");
        System.out.println("channelInactive");
        closeChannel(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            if (event.state() == IdleState.READER_IDLE) {
                log.info("IdleState.READER_IDLE");
                System.out.println("IdleState.READER_IDLE");
                ctx.disconnect();

            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("IdleState.WRITER_IDLE");

            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("IdleState.ALL_IDLE");

            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        log.info("exceptionCaught",cause);
        closeChannel(ctx);
    }

    private void closeChannel(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
       // final String addrRemote = Util.parseChannelRemoteAddr(channel);
        channel.close().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future)
                    throws Exception {
              /*
                log.info(
                        "closeChannel: close the connection to remote address[{}] result: {}",
                        addrRemote, future.isSuccess());

                if (future.isSuccess()) {
                    ClientChannelManager.onChannelClosed(addrRemote);
                }
                */
            }
        });
    }

}
