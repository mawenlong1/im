package com.mwl.im.server.handler;

import com.mwl.im.protocol.request.ListGroupMembersRequestPacket;
import com.mwl.im.protocol.response.ListGroupMembersResponsePacket;
import com.mwl.im.session.Session;
import com.mwl.im.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mawenlong
 * @date 2019-02-20 20:01
 */
@Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        List<Session> sessionList = new ArrayList<>();
        for (Channel channel : channelGroup) {
            sessionList.add(SessionUtil.getSession(channel));
        }

        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSessionList(sessionList);
        ctx.writeAndFlush(responsePacket);

    }
}
