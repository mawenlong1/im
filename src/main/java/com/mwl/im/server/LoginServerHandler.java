package com.mwl.im.server;

import com.mwl.im.protocol.Packet;
import com.mwl.im.protocol.PacketCodec;
import com.mwl.im.protocol.request.LoginRequestPacket;
import com.mwl.im.protocol.request.MessageRequestPacket;
import com.mwl.im.protocol.response.LoginResponsePacket;
import com.mwl.im.protocol.response.MessageResponsePacket;
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
        log.info("服务器开始处理客户端的信息。。。。。。");
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginRequestPacket) {
            //处理登录
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
            ByteBuf out = PacketCodec.INSTANCE.encode(ctx.alloc(), responsePacket);
            ctx.channel().writeAndFlush(out);
        } else if (packet instanceof MessageRequestPacket) {
            //处理接受消息
            MessageRequestPacket requestPacket = (MessageRequestPacket) packet;
            log.info("收到客户端消息：" + requestPacket.getMessage());

            MessageResponsePacket responsePacket = new MessageResponsePacket();
            responsePacket.setMessage("服务器回复【已经接收到客户端发送的消息】");
            ByteBuf out = PacketCodec.INSTANCE.encode(ctx.alloc(), responsePacket);
            ctx.channel().writeAndFlush(out);
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
