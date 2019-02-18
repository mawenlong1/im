package com.mwl.im.client;

import com.mwl.im.protocol.Packet;
import com.mwl.im.protocol.PacketCodec;
import com.mwl.im.protocol.request.LoginRequestPacket;
import com.mwl.im.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mawenlong
 * @date 2019-02-18 23:06
 */
@Slf4j
public class LoginClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端开始登录");

        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserId("userid");
        requestPacket.setUsername("张三");
        requestPacket.setPassword("password");

        ByteBuf byteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), requestPacket);

        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket responsePacket = (LoginResponsePacket) packet;
            if (responsePacket.isSuccess()) {
                log.info("登录成功！！！！！");
            } else {
                log.info("登录失败！！！ 原因：" + responsePacket.getReason());
            }
        }
    }
}
