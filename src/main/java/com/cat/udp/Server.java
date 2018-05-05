/*
 * Copyright (C) 2017 Farshad Noravesh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cat.udp;

import com.cat.udp.UDP.Update;
import com.cat.udp.presentationLayer.LoginHandler;
import com.cat.udp.businessLogic.Multiplexer;
import com.cat.udp.presentationLayer.NetStateChangeHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.DatagramPacketDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Receive an update.
 *
 * @author Farshad Noravesh
 */
public class Server extends SimpleChannelInboundHandler<Update> {

    public static final int PORT = 3002;

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ServerInitializer());
            b.bind(PORT).sync().channel().closeFuture().await();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            group.shutdownGracefully();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, Update update) throws Exception {
        System.out.println("text in server:"+update.getText());
        System.out.println("id in server:"+update.getId());

    }
}

class ServerInitializer extends ChannelInitializer<DatagramChannel> {

    @Override
    protected void initChannel(DatagramChannel c) throws Exception {
        ChannelPipeline p = c.pipeline();
        p.addLast(new DatagramPacketDecoder(new ProtobufDecoder(Update.getDefaultInstance())));
        p.addLast("idleMonitor", new IdleStateHandler(60, 0, 0));
        p.addLast("idleHandler", new NetStateChangeHandler());

        p.addLast("login",new LoginHandler());
        p.addLast("switch",new Multiplexer());
        p.addLast("server",new Server());

    }

}
