package com.mwl.im.client.console;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author mawenlong
 * @date 2019/02/20
 */
public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("1", new SendToUserConsoleCommand());
        consoleCommandMap.put("2", new LogoutConsoleCommand());
        consoleCommandMap.put("3", new CreateGroupConsoleCommand());
        consoleCommandMap.put("4", new JoinGroupConsoleCommand());
        consoleCommandMap.put("5", new QuitGroupConsoleCommand());
        consoleCommandMap.put("6", new ListGroupMembersConsoleCommand());
        consoleCommandMap.put("7", new SendToGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("1.sendToUser\n" +
                           "2.logout\n" +
                           "3.createGroup\n" +
                           "4.joinGroup\n" +
                           "5.quitGroup\n" +
                           "6.listGroupMembers\n" +
                           "7.sendToGroup\n" +
                           " 请输入命令：");
        String command = scanner.next();
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.err.println("无法识别[" + command + "]指令，请重新输入!");
        }
    }
}
