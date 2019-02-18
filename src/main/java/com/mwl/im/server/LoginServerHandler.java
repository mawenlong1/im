package com.mwl.im.server;

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
 * @date 2019-02-18 22:56
 */
@Slf4j
public class LoginServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("服务器开始处理客户端登录。。。。。。");
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket requestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket responsePacket = new LoginResponsePacket();
            responsePacket.setVersion(packet.getVersion());
            if (valid(requestPacket)) {
                responsePacket.setSuccess(true);
                log.info(requestPacket.getUsername() + "---->登录成功！！！！");
            } else {
                responsePacket.setSuccess(false);
                responsePacket.setReason("账户校验失败。。。");
                log.info(requestPacket.getUsername() + "--->登录失败！！！！！！");

            }
            ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), responsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket requestPacket) {
        if ("张三".equals(requestPacket.getUsername())) {
            return true;
        } else {
            return false;
        }
    }
}
