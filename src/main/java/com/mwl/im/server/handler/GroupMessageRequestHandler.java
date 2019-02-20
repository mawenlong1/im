package com.mwl.im.server.handler;

import com.mwl.im.protocol.request.GroupMessageRequestPacket;
import com.mwl.im.protocol.response.GroupMessageResponsePacket;
import com.mwl.im.utils.SessionUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author mawenlong
 * @date 2019-02-20 21:16
 */
@Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        String toGroup = msg.getToGroupId();
        String message = msg.getMessage();

        GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
        responsePacket.setFromGroupId(toGroup);
        responsePacket.setMessage(message);
        responsePacket.setFromUser(SessionUtil.getSession(ctx.channel()));

        SessionUtil.getChannelGroup(toGroup).writeAndFlush(responsePacket);
    }
}
