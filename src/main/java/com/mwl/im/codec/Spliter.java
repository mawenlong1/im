package com.mwl.im.codec;

import com.mwl.im.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author mawenlong
 * @date 2019-02-19 21:25
 *
 * 基于长度分割
 */
public class Spliter extends LengthFieldBasedFrameDecoder {

    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 屏蔽非本协议的客户端请求
        if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
