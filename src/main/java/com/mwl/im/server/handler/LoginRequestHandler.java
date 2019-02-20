package com.mwl.im.server.handler;

import com.mwl.im.protocol.request.LoginRequestPacket;
import com.mwl.im.protocol.response.LoginResponsePacket;
import com.mwl.im.session.Session;
import com.mwl.im.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author mawenlong
 * @date 2019-02-19 20:43
 */
@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(msg.getVersion());
        responsePacket.setUserName(msg.getUserName());

        if (valid(ctx, msg)) {
            responsePacket.setSuccess(true);
           // String userId = rendomUserId();
            String userId = msg.getUserName();
            responsePacket.setUserId(userId);
            SessionUtil.bindSession(new Session(userId, msg.getUserName()), ctx.channel());
            log.info("[" + msg.getUserName() + "]登录成功");
            ctx.pipeline().remove(this);
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("账户校验失败。。。");
            log.info(msg.getUserName() + "--->登录失败！！！！！！");

        }
        ctx.channel().writeAndFlush(responsePacket);
    }

    private String rendomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }


    private boolean valid(ChannelHandlerContext ctx, LoginRequestPacket requestPacket) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
