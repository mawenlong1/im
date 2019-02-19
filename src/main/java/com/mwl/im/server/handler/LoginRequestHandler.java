package com.mwl.im.server.handler;

import com.mwl.im.protocol.PacketCodec;
import com.mwl.im.protocol.request.LoginRequestPacket;
import com.mwl.im.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mawenlong
 * @date 2019-02-19 20:43
 */
@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        log.info("=======开始处理客户端登录请求=====");
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(msg.getVersion());
        if (valid(msg)) {
            responsePacket.setSuccess(true);
            log.info(msg.getUsername() + "---->登录成功！！！！");
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("账户校验失败。。。");
            log.info(msg.getUsername() + "--->登录失败！！！！！！");

        }
        ctx.channel().writeAndFlush(responsePacket);
    }

    private boolean valid(LoginRequestPacket requestPacket) {
        if ("张三".equals(requestPacket.getUsername())) {
            return true;
        } else {
            return false;
        }
    }
}
