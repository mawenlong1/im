package com.mwl.im.protocol;

import com.mwl.im.protocol.command.Command;
import com.mwl.im.protocol.request.CreateGroupRequestPacket;
import com.mwl.im.protocol.request.GroupMessageRequestPacket;
import com.mwl.im.protocol.request.HeartBeatRequestPacket;
import com.mwl.im.protocol.request.JoinGroupRequestPacket;
import com.mwl.im.protocol.request.ListGroupMembersRequestPacket;
import com.mwl.im.protocol.request.LoginRequestPacket;
import com.mwl.im.protocol.request.LogoutRequestPacket;
import com.mwl.im.protocol.request.MessageRequestPacket;
import com.mwl.im.protocol.request.QuitGroupRequestPacket;
import com.mwl.im.protocol.response.CreateGroupResponsePacket;
import com.mwl.im.protocol.response.GroupMessageResponsePacket;
import com.mwl.im.protocol.response.HeartBeatResponsePacket;
import com.mwl.im.protocol.response.JoinGroupResponsePacket;
import com.mwl.im.protocol.response.ListGroupMembersResponsePacket;
import com.mwl.im.protocol.response.LoginResponsePacket;
import com.mwl.im.protocol.response.LogoutResponsePacket;
import com.mwl.im.protocol.response.MessageResponsePacket;
import com.mwl.im.protocol.response.QuitGroupResponsePacket;
import com.mwl.im.serialize.Serializer;
import com.mwl.im.serialize.SerializerAlgorithm;
import com.mwl.im.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mawenlong
 * @date 2019-02-18 22:30
 */
public class PacketCodec {
    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodec INSTANCE = new PacketCodec();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(Command.LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(Command.LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap
                .put(Command.LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        packetTypeMap.put(Command.GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);
        packetTypeMap.put(Command.HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(Command.HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(SerializerAlgorithm.JSON, serializer);
    }

    /**
     * 编码
     *
     * @param byteBuf
     * @param packet
     *
     * @return
     */
    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        // TODO buffer与ioBuffer区别
//        ByteBuf byteBuf = allocator.buffer();
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        //魔术
        byteBuf.writeInt(MAGIC_NUMBER);
        //版本
        byteBuf.writeByte(packet.getVersion());
        //序列化算法
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        //命令
        byteBuf.writeByte(packet.getCommand());
        //长度
        byteBuf.writeInt(bytes.length);
        //数据
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    /**
     * 解码
     *
     * @param byteBuf
     *
     * @return
     */
    public Packet decode(ByteBuf byteBuf) {
        //跳过魔术
        byteBuf.skipBytes(4);
        //跳过版本号
        byteBuf.skipBytes(1);
        //序列化算法
        byte serializeAlgorithm = byteBuf.readByte();
        //命令
        byte command = byteBuf.readByte();
        //数据长度
        int length = byteBuf.readInt();
        //数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
