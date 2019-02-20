package com.mwl.im.client.console;

import com.mwl.im.protocol.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author mawenlong
 * @date 2019-02-20 21:24
 */
public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个某个群组：");
        String toGroupId = scanner.next();
        System.out.print("需要发送的消息：");
        String message = scanner.next();
        channel.writeAndFlush(new GroupMessageRequestPacket(toGroupId, message));
    }
}
