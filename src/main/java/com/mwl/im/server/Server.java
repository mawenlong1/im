package com.mwl.im.server;

import com.mwl.im.codec.PacketCodecHandler;
import com.mwl.im.codec.Spliter;
import com.mwl.im.handler.IMIdleStateHandler;
import com.mwl.im.server.handler.AuthHandler;
import com.mwl.im.server.handler.HeartBeatRequestHandler;
import com.mwl.im.server.handler.IMHandler;
import com.mwl.im.server.handler.LoginRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mawenlong
 * @date 2019/02/18
 */
@Slf4j
public class Server {
    static final int PORT = Integer.parseInt(System.getProperty("port", "5050"));

    public void start(int port) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(boss, worker)
         .channel(NioServerSocketChannel.class)
         // 系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
         .option(ChannelOption.SO_BACKLOG, 1024)
         //表示是否开启TCP底层心跳机制，true为开启
         .childOption(ChannelOption.SO_KEEPALIVE, true)
         // 表示是否开启Nagle算法，true表示关闭，false表示开启，
         // 通俗地说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
         .childOption(ChannelOption.TCP_NODELAY, true)
         .handler(new LoggingHandler(LogLevel.INFO))
         .attr(AttributeKey.newInstance("serverName"), "IM Server")
         .childHandler(new ChannelInitializer<NioSocketChannel>() {
             @Override
             protected void initChannel(NioSocketChannel ch) throws Exception {
                 ch.pipeline()
                   //心跳检测
                   .addLast(new IMIdleStateHandler())
                   //会累积当前channel读到的数据,所以不能使用单例
                   .addLast(new Spliter())
                   //解码信息
                   .addLast(PacketCodecHandler.INSTANCE)
                   //心跳检测
                   .addLast(HeartBeatRequestHandler.INSTANCE)
                   //登录
                   .addLast(LoginRequestHandler.INSTANCE)
                   //认证
                   .addLast(AuthHandler.INSTANCE)
                   .addLast(IMHandler.INSTANCE);
             }
         });
        bind(b, port);
    }

    public void bind(ServerBootstrap b, int port) {
        b.bind(port).addListener(f -> {
            if (f.isSuccess()) {
                log.info("端口[" + port + "]绑定成功!");
            } else {
                log.info("端口[" + port + "]绑定失败!");
                bind(b, port + 1);
            }
        });
    }

    public static void main(String[] args) {
        new Server().start(PORT);
    }
}
