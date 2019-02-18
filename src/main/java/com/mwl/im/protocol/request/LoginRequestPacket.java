package com.mwl.im.protocol.request;

import com.mwl.im.protocol.Packet;
import com.mwl.im.protocol.command.Command;
import lombok.Data;

/**
 * @author mawenlong
 * @date 2019-02-18 22:08
 */
@Data
public class LoginRequestPacket extends Packet {
    private String userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
