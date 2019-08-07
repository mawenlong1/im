package com.mwl.im.client.console;

import com.mwl.im.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author mawenlong
 * @date 2019/02/20
 */
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入要发送给的用户id：");
        String toUserId = scanner.next();
        System.out.println("输入要发送的消息");
        String message = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId,message));
    }
}
