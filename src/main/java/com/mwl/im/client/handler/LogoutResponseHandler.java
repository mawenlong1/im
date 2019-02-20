package com.mwl.im.client.handler;

import com.mwl.im.protocol.response.LogoutResponsePacket;
import com.mwl.im.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author mawenlong
 * @date 2019-02-20 20:40
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
