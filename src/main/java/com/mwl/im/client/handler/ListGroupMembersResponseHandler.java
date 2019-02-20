package com.mwl.im.client.handler;

import com.mwl.im.protocol.response.ListGroupMembersResponsePacket;
import com.mwl.im.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * @author mawenlong
 * @date 2019-02-20 20:07
 */
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket msg) throws Exception {
        List<Session> sessions = msg.getSessionList();
        System.out.println("群[" + msg.getGroupId() + "]中的人包括：" + sessions);

    }
}
