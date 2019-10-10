package com.mwl.im.client.handler;

import com.mwl.im.protocol.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author mawenlong
 * @date 2019/02/20
 */
public class CreateGroupRequestHandler
        extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg)
            throws Exception {
        System.out.print("群创建成功，id为[" + msg.getGroupId() + "],");
        System.out.println("群成员有：" + msg.getUserNameList());
    }
}
