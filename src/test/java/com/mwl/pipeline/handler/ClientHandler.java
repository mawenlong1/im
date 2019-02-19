package com.mwl.pipeline.handler;

import com.mwl.im.utils.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author mawenlong
 * @date 2019/02/19
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(ByteBufUtil.getByteBuf(ctx, "你好，我是客户端。。。。。"));
    }
}
