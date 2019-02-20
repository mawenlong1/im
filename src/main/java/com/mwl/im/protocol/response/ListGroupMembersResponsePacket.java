package com.mwl.im.protocol.response;

import com.mwl.im.protocol.Packet;
import com.mwl.im.protocol.command.Command;
import com.mwl.im.session.Session;
import lombok.Data;

import java.util.List;

/**
 * @author mawenlong
 * @date 2019-02-20 20:03
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;
    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
