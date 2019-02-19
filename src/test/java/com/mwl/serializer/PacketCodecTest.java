package com.mwl.serializer;

import com.mwl.im.protocol.Packet;
import com.mwl.im.protocol.PacketCodec;
import com.mwl.im.protocol.request.LoginRequestPacket;
import com.mwl.im.protocol.response.LoginResponsePacket;
import com.mwl.im.serialize.Serializer;
import com.mwl.im.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author mawenlong
 * @date 2019-02-18 23:37
 */
public class PacketCodecTest {
    @Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserName("zhangsan");
        loginRequestPacket.setPassword("password");

        PacketCodec packetCodec = PacketCodec.INSTANCE;
        ByteBuf byteBuf = packetCodec.encode(ByteBufAllocator.DEFAULT.ioBuffer(), loginRequestPacket);
        Packet decodedPacket = packetCodec.decode(byteBuf);

        System.out.println(((LoginRequestPacket) decodedPacket).getUserName());
        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }

    @Test
    public void serializer() {

        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setReason("reason");
        responsePacket.setSuccess(false);

        Serializer serializer = new JSONSerializer();
        byte[] bytes = serializer.serialize(responsePacket);

        LoginResponsePacket packet = serializer.deserialize(LoginResponsePacket.class, bytes);
        System.out.println(packet.getReason());
    }
}
