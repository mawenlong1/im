package com.mwl.im.client;

import com.mwl.im.utils.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;


/**
 * @author mawenlong
 * @date 2019-02-18 21:29
 */
@Slf4j
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端写数据");
        ctx.channel().writeAndFlush(ByteBufUtil.getByteBuf(ctx, "你好，我是客户端。。。。。"));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("从服务器读取到的数据----->" + byteBuf.toString(Charset.forName("utf-8")));
    }


}
