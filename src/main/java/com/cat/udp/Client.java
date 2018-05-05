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
import com.cat.udp.UDP.Update.Builder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.DatagramPacketEncoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import java.net.InetSocketAddress;

/**
 * Send an update.
 *
 * @author Farshad Noravesh
 */
public class Client extends SimpleChannelInboundHandler<Update> {

    private static final InetSocketAddress ADDRESS
            = new InetSocketAddress("localhost", Server.PORT);

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ClientInitializer());

            Channel ch = b.bind(0).sync().channel();
            //Channel ch = b.connect(ADDRESS).sync().channel();

            Builder builder = Update.newBuilder();
            builder.setId(18387);
            builder.setText("Farshad");
            builder.setCmd(2001); //see common package for command description
            ByteBuf data = Unpooled.wrappedBuffer(builder.build().toByteArray());
            DatagramPacket packet = new DatagramPacket(data, ADDRESS);

            //ch.writeAndFlush(builder.build());
            ch.writeAndFlush(packet);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            group.shutdownGracefully();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Update msg) throws Exception {
        System.err.println("Server received packet in error");
    }
}

class ClientInitializer extends ChannelInitializer<DatagramChannel> {

    @Override
    protected void initChannel(DatagramChannel c) throws Exception {
        ChannelPipeline p = c.pipeline();
        p.addLast(new DatagramPacketEncoder(new ProtobufEncoder()));
        p.addLast(new Client());
    }

}
