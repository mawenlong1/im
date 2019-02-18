package com.mwl.im.server;

import com.mwl.im.utils.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author mawenlong
 * @date 2019-02-18 21:20
 */
@Slf4j
public class FirstSererHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("接收到客户端的信息----->" + byteBuf.toString(Charset.forName("utf-8")));

        log.info("服务器发送消息");
        ByteBuf out = ByteBufUtil.getByteBuf(ctx, "你好，我是服务器。。。。。。");
        ctx.channel().writeAndFlush(out);
    }

}
