package com.mwl.im.server.handler;

import com.mwl.im.protocol.request.MessageRequestPacket;
import com.mwl.im.protocol.response.MessageResponsePacket;
import com.mwl.im.session.Session;
import com.mwl.im.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mawenlong
 * @date 2019-02-19 20:48
 */
@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        Session session = SessionUtil.getSession(ctx.channel());

        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setFromUserName(session.getUserName());
        responsePacket.setFromUserId(session.getUserId());
        responsePacket.setMessage(msg.getMessage());

        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());

        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(responsePacket);
        } else {
            log.info("[" + msg.getToUserId() + "] 不在线，发送失败!");
        }
    }
}
