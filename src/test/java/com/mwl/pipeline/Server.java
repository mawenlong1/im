package com.mwl.pipeline;

import com.mwl.pipeline.handler.InBoundHandlerA;
import com.mwl.pipeline.handler.InBoundHandlerB;
import com.mwl.pipeline.handler.InBoundHandlerC;
import com.mwl.pipeline.handler.OutBoundHandlerA;
import com.mwl.pipeline.handler.OutBoundHandlerB;
import com.mwl.pipeline.handler.OutBoundHandlerC;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author mawenlong
 * @date 2019/02/19
 */
public class Server {
    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(boss, worker)
         .channel(NioServerSocketChannel.class)
         .childHandler(new ChannelInitializer<SocketChannel>() {
             @Override
             protected void initChannel(SocketChannel ch) throws Exception {
                 // inBound，处理读数据的逻辑链
                 ch.pipeline().addLast(new InBoundHandlerA());
                 // ch.pipeline().addLast(new OutBoundHandlerA());
                 // ch.pipeline().addLast(new OutBoundHandlerB());
                 ch.pipeline().addLast(new InBoundHandlerB());
                 ch.pipeline().addLast(new InBoundHandlerC());

                 // outBound，处理写数据的逻辑链
                 ch.pipeline().addLast(new OutBoundHandlerA());
                 ch.pipeline().addLast(new OutBoundHandlerB());
                 ch.pipeline().addLast(new OutBoundHandlerC());
             }
         });
        b.bind(4040);
    }
}
