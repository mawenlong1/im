package com.mwl.im.client.console;

import com.mwl.im.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author mawenlong
 * @date 2019/02/20
 */
public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket requestPacket = new LoginRequestPacket();

        System.out.print("输入用户名登录: ");
        requestPacket.setUserName(scanner.nextLine());
        requestPacket.setPassword("pwd");

        channel.writeAndFlush(requestPacket);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
