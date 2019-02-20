package com.mwl.im.client;

import com.mwl.im.client.console.ConsoleCommandManager;
import com.mwl.im.client.console.LoginConsoleCommand;
import com.mwl.im.client.handler.CreateGroupRequestHandler;
import com.mwl.im.client.handler.JoinGroupResponseHandler;
import com.mwl.im.client.handler.ListGroupMembersResponseHandler;
import com.mwl.im.client.handler.LoginReponseHandler;
import com.mwl.im.client.handler.MessageResponseHandler;
import com.mwl.im.client.handler.QuitGroupResponseHandler;
import com.mwl.im.codec.PacketDecoder;
import com.mwl.im.codec.PacketEncoder;
import com.mwl.im.codec.Spliter;
import com.mwl.im.utils.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
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
         // 表示连接的超时时间，超过这个时间还是建立不上的话则代表连接失败
         .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
         // 表示是否开启 TCP 底层心跳机制，true 为开启
         .option(ChannelOption.SO_KEEPALIVE, true)
         // 表示是否开始 Nagle 算法，true 表示关闭，false 表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就设置为 true 关闭，如果需要减少发送次数减少网络交互，就设置为 false 开启
         .option(ChannelOption.TCP_NODELAY, true)
         .attr(AttributeKey.newInstance("clientName"), "IM Client")
         .handler(new ChannelInitializer<SocketChannel>() {
             @Override
             protected void initChannel(SocketChannel ch) throws Exception {
                 ch.pipeline()
                   .addLast(new Spliter())
                   //解码
                   .addLast(new PacketDecoder())
                   //登录
                   .addLast(new LoginReponseHandler())
                   //创建群聊
                   .addLast(new CreateGroupRequestHandler())
                   //输出群聊成员
                   .addLast(new ListGroupMembersResponseHandler())
                   //加入群聊
                   .addLast(new JoinGroupResponseHandler())
                   //退出群聊
                   .addLast(new QuitGroupResponseHandler())
                   //单聊
                   .addLast(new MessageResponseHandler())
                   // 编码
                   .addLast(new PacketEncoder());
             }
         });
        connect(b, host, port, MAX_RETRY);
    }

    public void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("连接成功!");
                Channel channel = ((ChannelFuture) future).channel();
                startConsolThread(channel);
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

    private static void startConsolThread(Channel channel) {
        ConsoleCommandManager manager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();

        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (SessionUtil.hasLogin(channel)) {
                    manager.exec(scanner, channel);
                } else {
                    loginConsoleCommand.exec(scanner, channel);
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new Client().start(HOST, PORT);
    }
}
