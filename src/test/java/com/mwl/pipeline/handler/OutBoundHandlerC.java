package com.mwl.pipeline.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author mawenlong
 * @date 2019/02/19
 */
public class OutBoundHandlerC extends ChannelOutboundHandlerAdapter {
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
            throws Exception {
        System.out.println("OutBoundHandlerC: " + msg);
        super.write(ctx, msg, promise);
    }
}
