/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;

public enum ServerboundPacketsc0_28 implements ServerboundPacketType,
PreNettyPacketType
{
    LOGIN(0, (user, buf) -> buf.skipBytes(130)),
    USE_ITEM_ON(5, (user, buf) -> buf.skipBytes(8)),
    MOVE_PLAYER_POS_ROT(8, (user, buf) -> buf.skipBytes(9)),
    CHAT(13, (user, buf) -> buf.skipBytes(65));

    private static final ServerboundPacketsc0_28[] REGISTRY;
    private final int id;
    private final BiConsumer<UserConnection, ByteBuf> packetReader;

    public static ServerboundPacketsc0_28 getPacket(int id) {
        return REGISTRY[id];
    }

    private ServerboundPacketsc0_28(int id, BiConsumer<UserConnection, ByteBuf> packetReader) {
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
        REGISTRY = new ServerboundPacketsc0_28[256];
        ServerboundPacketsc0_28[] serverboundPacketsc0_28Array = ServerboundPacketsc0_28.values();
        int n = serverboundPacketsc0_28Array.length;
        for (int i = 0; i < n; ++i) {
            ServerboundPacketsc0_28 packet;
            ServerboundPacketsc0_28.REGISTRY[packet.id] = packet = serverboundPacketsc0_28Array[i];
        }
    }
}

