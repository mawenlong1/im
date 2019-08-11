package com.mwl.im.client.handler;

import com.mwl.im.protocol.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author mawenlong
 * @date 2019-02-20 20:10
 */
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println("退出群聊[ " + msg.getGroupId() + " ]成功！");
        } else {
            System.out.println("退出群聊[ " + msg.getGroupId() + " ]失败！");
        }
    }
}
