/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;
import net.raphimc.vialegacy.api.splitter.PreNettyTypes;

public enum ServerboundPackets1_2_4 implements ServerboundPacketType,
PreNettyPacketType
{
    KEEP_ALIVE(0, (user, buf) -> buf.skipBytes(4)),
    LOGIN(1, (user, buf) -> {
        buf.skipBytes(4);
        PreNettyTypes.readString(buf);
        PreNettyTypes.readString(buf);
        buf.skipBytes(11);
    }),
    HANDSHAKE(2, (user, buf) -> PreNettyTypes.readString(buf)),
    CHAT(3, (user, buf) -> PreNettyTypes.readString(buf)),
    INTERACT(7, (user, buf) -> buf.skipBytes(9)),
    RESPAWN(9, (user, buf) -> {
        buf.skipBytes(8);
        PreNettyTypes.readString(buf);
    }),
    MOVE_PLAYER_STATUS_ONLY(10, (user, buf) -> buf.skipBytes(1)),
    MOVE_PLAYER_POS(11, (user, buf) -> buf.skipBytes(33)),
    MOVE_PLAYER_ROT(12, (user, buf) -> buf.skipBytes(9)),
    MOVE_PLAYER_POS_ROT(13, (user, buf) -> buf.skipBytes(41)),
    PLAYER_ACTION(14, (user, buf) -> buf.skipBytes(11)),
    USE_ITEM_ON(15, (user, buf) -> {
        buf.skipBytes(10);
        PreNettyTypes.readItemStack1_0(buf);
    }),
    SET_CARRIED_ITEM(16, (user, buf) -> buf.skipBytes(2)),
    SWING(18, (user, buf) -> buf.skipBytes(5)),
    PLAYER_COMMAND(19, (user, buf) -> buf.skipBytes(5)),
    CONTAINER_CLOSE(101, (user, buf) -> buf.skipBytes(1)),
    CONTAINER_CLICK(102, (user, buf) -> {
        buf.skipBytes(7);
        PreNettyTypes.readItemStack1_0(buf);
    }),
    CONTAINER_ACK(106, (user, buf) -> buf.skipBytes(4)),
    SET_CREATIVE_MODE_SLOT(107, (user, buf) -> {
        buf.skipBytes(2);
        PreNettyTypes.readItemStack1_0(buf);
    }),
    CONTAINER_BUTTON_CLICK(108, (user, buf) -> buf.skipBytes(2)),
    SIGN_UPDATE(130, (user, buf) -> {
        buf.skipBytes(10);
        PreNettyTypes.readString(buf);
        PreNettyTypes.readString(buf);
        PreNettyTypes.readString(buf);
        PreNettyTypes.readString(buf);
    }),
    PLAYER_ABILITIES(202, (user, buf) -> buf.skipBytes(4)),
    CUSTOM_PAYLOAD(250, (user, buf) -> {
        PreNettyTypes.readString(buf);
        int s = buf.readShort();
        for (int i = 0; i < s; ++i) {
            buf.readByte();
        }
    }),
    SERVER_PING(254, (user, buf) -> {}),
    DISCONNECT(255, (user, buf) -> PreNettyTypes.readString(buf));

    private static final ServerboundPackets1_2_4[] REGISTRY;
    private final int id;
    private final BiConsumer<UserConnection, ByteBuf> packetReader;

    public static ServerboundPackets1_2_4 getPacket(int id) {
        return REGISTRY[id];
    }

    private ServerboundPackets1_2_4(int id, BiConsumer<UserConnection, ByteBuf> packetReader) {
        this.id = id;
        this.packetReader = packetReader;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public BiConsumer<UserConnection, ByteBuf> getPacketReader() {
        return this.packetReader;
    }

    static {
        REGISTRY = new ServerboundPackets1_2_4[256];
        ServerboundPackets1_2_4[] serverboundPackets1_2_4Array = ServerboundPackets1_2_4.values();
        int n = serverboundPackets1_2_4Array.length;
        for (int i = 0; i < n; ++i) {
            ServerboundPackets1_2_4 packet;
            ServerboundPackets1_2_4.REGISTRY[packet.id] = packet = serverboundPackets1_2_4Array[i];
        }
    }
}

