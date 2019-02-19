package com.mwl.im.server.handler;

import com.mwl.im.protocol.request.MessageRequestPacket;
import com.mwl.im.protocol.response.MessageResponsePacket;
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
        //处理接受消息
        log.info("收到客户端消息：" + msg.getMessage());
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setMessage("服务器回复【"+msg.getMessage()+"】");
        ctx.channel().writeAndFlush(responsePacket);
    }
}
