package com.mwl.im.protocol.response;

import com.mwl.im.protocol.Packet;
import com.mwl.im.protocol.command.Command;

/**
 * @author mawenlong
 * @date 2019-02-20 22:25
 */
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }
}
