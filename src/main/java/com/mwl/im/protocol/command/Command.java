package com.mwl.im.protocol.command;

/**
 * @author mawenlong
 * @date 2019-02-18 22:05
 * <p>
 * TODO 修改成枚举
 */
public interface Command {
    /**
     * 登录请求命令
     */
    byte LOGIN_REQUEST = 1;
    /**
     * 登录响应命令
     */
    byte LOGIN_RESPONSE = 2;
    /**
     * 客户端发送消息命令
     */
    byte MESSAGE_REQUEST = 3;
    /**
     * 服务器端响应信息命令
     */
    byte MESSAGE_RESPONSE = 4;
    /**
     * 退出请求
     */
    byte LOGOUT_REQUEST = 5;

    /**
     * 退出响应
     */
    Byte LOGOUT_RESPONSE = 6;
    /**
     * 创建群聊请求
     */
    Byte CREATE_GROUP_REQUEST = 7;
    /**
     * 创建群聊响应
     */
    Byte CREATE_GROUP_RESPONSE = 8;
    /**
     * 列出群聊请求
     */
    Byte LIST_GROUP_MEMBERS_REQUEST = 9;
    /**
     * 列出群聊响应
     */
    Byte LIST_GROUP_MEMBERS_RESPONSE = 10;
    /**
     * 加入群聊请求
     */
    Byte JOIN_GROUP_REQUEST = 11;
    /**
     * 加入群聊响应
     */
    Byte JOIN_GROUP_RESPONSE = 12;
    /**
     * 退出群聊请求
     */
    Byte QUIT_GROUP_REQUEST = 13;
    /**
     * 退出群聊响应
     */
    Byte QUIT_GROUP_RESPONSE = 14;

    /**
     * 群聊消息发送请求
     */
    Byte GROUP_MESSAGE_REQUEST = 15;
    /**
     * 群聊消息发送响应
     */
    Byte GROUP_MESSAGE_RESPONSE = 16;
    /**
     * 心跳请求
     */
    Byte HEARTBEAT_REQUEST = 17;
    /**
     * 心跳响应
     */
    Byte HEARTBEAT_RESPONSE = 18;
}
