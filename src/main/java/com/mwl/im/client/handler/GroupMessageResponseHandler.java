package com.mwl.im.client.handler;

import com.mwl.im.protocol.response.GroupMessageResponsePacket;
import com.mwl.im.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author mawenlong
 * @date 2019-02-20 21:22
 */
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket msg) throws Exception {
        System.out.println("收到群[" + msg.getFromGroupId() + "]中[" + msg.getFromUser() + "]发来的消息：" + msg.getMessage());
    }
}
