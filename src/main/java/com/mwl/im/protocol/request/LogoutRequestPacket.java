package com.mwl.im.protocol.request;

import com.mwl.im.protocol.Packet;
import com.mwl.im.protocol.command.Command;

/**
 * @author mawenlong
 * @date 2019/02/20
 */
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
