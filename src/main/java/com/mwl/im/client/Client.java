package com.mwl.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author mawenlong
 * @date 2019/02/18
 */
@Slf4j
public class Client {
    static final int PORT = Integer.parseInt(System.getProperty("port", "5050"));
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int MAX_RETRY = 3;

    public void start(String host, int port) {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group)
         .channel(NioSocketChannel.class)
         .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
         .option(ChannelOption.SO_KEEPALIVE, true)
         .option(ChannelOption.TCP_NODELAY, true)
         .handler(new ChannelInitializer<SocketChannel>() {
             @Override
             protected void initChannel(SocketChannel ch) throws Exception {

             }
         });
        connect(b, host, port, MAX_RETRY);
    }

    public void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("连接成功!");
            } else if (retry == 0) {
                log.info("重试次数已用完，放弃连接！");
            } else {
                int order = (MAX_RETRY - retry) + 1;
                //本次重连间隔
                int delay = 1 << order;
                log.info("连接失败，第" + order + "次重连……");
                bootstrap.config().group()
                         .schedule(() -> connect(bootstrap, host, port, retry - 1), delay,
                                   TimeUnit.SECONDS);
            }
        });
    }

    public static void main(String[] args) {
        new Client().start(HOST, PORT);
    }
}
