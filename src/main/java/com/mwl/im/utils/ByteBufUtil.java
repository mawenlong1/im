package com.mwl.im.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.Charset;

/**
 * @author mawenlong
 * @date 2019-02-18 21:33
 */
public class ByteBufUtil {
    public static ByteBuf getByteBuf(ChannelHandlerContext ctx, String sendMessage) {
        byte[] bytes = sendMessage.getBytes(Charset.forName("utf-8"));
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
