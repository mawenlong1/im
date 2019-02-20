package com.mwl.im.protocol.response;

import com.mwl.im.protocol.Packet;
import com.mwl.im.protocol.command.Command;
import com.mwl.im.session.Session;
import lombok.Data;

/**
 * @author mawenlong
 * @date 2019-02-20 21:14
 */
@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;
    private Session fromUser;
    private String message;


    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
