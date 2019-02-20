package com.mwl.im.server.handler;

import com.mwl.im.protocol.request.CreateGroupRequestPacket;
import com.mwl.im.protocol.response.CreateGroupResponsePacket;
import com.mwl.im.utils.IDUtil;
import com.mwl.im.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mawenlong
 * @date 2019/02/20
 */
public class CreateGroupRequestHandler
        extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg)
            throws Exception {
        List<String> userIds = msg.getUserIdList();

        List<String> userNames = new ArrayList<>();
        ChannelGroup channels = new DefaultChannelGroup(ctx.executor());

        for (String userId : userIds) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channels.add(channel);
                userNames.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        String groupId = IDUtil.randomInt().toString();
        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        responsePacket.setUserNameList(userNames);

        channels.writeAndFlush(responsePacket);
        System.out.print("群创建成功，id 为[" + groupId + "], ");
        System.out.println("群里面有：" + responsePacket.getUserNameList());

        SessionUtil.bindChannelGroup(groupId, channels);
    }
}
