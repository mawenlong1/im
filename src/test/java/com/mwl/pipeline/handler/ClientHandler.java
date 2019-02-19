package com.mwl.pipeline.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @author mawenlong
 * @date 2019/02/19
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(getByteBuf(ctx, "你好，我是客户端。。。。。"));
    }
    ByteBuf getByteBuf(ChannelHandlerContext ctx, String sendMessage) {
        byte[] bytes = sendMessage.getBytes(Charset.forName("utf-8"));
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
