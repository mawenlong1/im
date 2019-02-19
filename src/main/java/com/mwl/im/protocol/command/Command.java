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

}
