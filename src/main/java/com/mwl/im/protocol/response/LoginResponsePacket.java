package com.mwl.im.protocol.response;

import com.mwl.im.protocol.Packet;
import com.mwl.im.protocol.command.Command;
import lombok.Data;

/**
 * @author mawenlong
 * @date 2019-02-18 22:13
 */
@Data
public class LoginResponsePacket extends Packet {
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
