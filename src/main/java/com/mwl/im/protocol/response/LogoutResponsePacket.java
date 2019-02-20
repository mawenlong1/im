package com.mwl.im.protocol.response;

import com.mwl.im.protocol.Packet;
import com.mwl.im.protocol.command.Command;
import lombok.Data;

/**
 * @author mawenlong
 * @date 2019-02-20 20:16
 */
@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;
    private String reason;


    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
