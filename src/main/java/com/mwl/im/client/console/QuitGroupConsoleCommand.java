package com.mwl.im.client.console;

import com.mwl.im.protocol.request.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author mawenlong
 * @date 2019-02-20 20:12
 */
public class QuitGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        QuitGroupRequestPacket requestPacket = new QuitGroupRequestPacket();

        System.out.print("输入 groupId，退出群聊：");
        String groupId = scanner.next();

        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);
    }
}
