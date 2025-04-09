/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.alpha.a1_0_15toa1_0_16_2.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;
import net.raphimc.vialegacy.api.splitter.PreNettyTypes;

public enum ServerboundPacketsa1_0_15 implements ServerboundPacketType,
PreNettyPacketType
{
    KEEP_ALIVE(0, (user, buf) -> {}),
    LOGIN(1, (user, buf) -> {
        buf.skipBytes(4);
        PreNettyTypes.readUTF(buf);
        PreNettyTypes.readUTF(buf);
    }),
    CHAT(3, (user, buf) -> PreNettyTypes.readUTF(buf)),
    MOVE_PLAYER_STATUS_ONLY(10, (user, buf) -> buf.skipBytes(1)),
    MOVE_PLAYER_POS(11, (user, buf) -> buf.skipBytes(33)),
    MOVE_PLAYER_ROT(12, (user, buf) -> buf.skipBytes(9)),
    MOVE_PLAYER_POS_ROT(13, (user, buf) -> buf.skipBytes(41)),
    PLAYER_ACTION(14, (user, buf) -> buf.skipBytes(11)),
    USE_ITEM_ON(15, (user, buf) -> buf.skipBytes(12)),
    SET_CARRIED_ITEM(16, (user, buf) -> buf.skipBytes(6)),
    SWING(18, (user, buf) -> buf.skipBytes(5)),
    SPAWN_ITEM(21, (user, buf) -> buf.skipBytes(22)),
    DISCONNECT(255, (user, buf) -> PreNettyTypes.readUTF(buf));

    private static final ServerboundPacketsa1_0_15[] REGISTRY;
    private final int id;
    private final BiConsumer<UserConnection, ByteBuf> packetReader;

    public static ServerboundPacketsa1_0_15 getPacket(int id) {
        return REGISTRY[id];
    }

    private ServerboundPacketsa1_0_15(int id, BiConsumer<UserConnection, ByteBuf> packetReader) {
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
        REGISTRY = new ServerboundPacketsa1_0_15[256];
        ServerboundPacketsa1_0_15[] serverboundPacketsa1_0_15Array = ServerboundPacketsa1_0_15.values();
        int n = serverboundPacketsa1_0_15Array.length;
        for (int i = 0; i < n; ++i) {
            ServerboundPacketsa1_0_15 packet;
            ServerboundPacketsa1_0_15.REGISTRY[packet.id] = packet = serverboundPacketsa1_0_15Array[i];
        }
    }
}

