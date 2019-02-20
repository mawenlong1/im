package com.mwl.im.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author mawenlong
 * @date 2019/02/20
 */
public class LogoutConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        channel.writeAndFlush(new LogoutConsoleCommand());
    }
}
