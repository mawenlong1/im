package com.mwl.im.client.handler;

import com.mwl.im.protocol.request.LoginRequestPacket;
import com.mwl.im.protocol.response.LoginResponsePacket;
import com.mwl.im.utils.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mawenlong
 * @date 2019-02-19 21:00
 */
@Slf4j
public class LoginReponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端开始登录");
        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserId("userid");
        requestPacket.setUsername("张三");
        requestPacket.setPassword("password");

        ctx.channel().writeAndFlush(requestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            LoginUtil.markAsLogin(ctx.channel());
            log.info("登录成功！！！！！");
        } else {
            log.info("登录失败！！！ 原因：" + msg.getReason());
        }
    }
}
