/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;

public enum ServerboundPacketsc0_30cpe implements ServerboundPacketType,
PreNettyPacketType
{
    LOGIN(0, (user, buf) -> buf.skipBytes(130)),
    USE_ITEM_ON(5, (user, buf) -> buf.skipBytes(8)),
    MOVE_PLAYER_POS_ROT(8, (user, buf) -> buf.skipBytes(9)),
    CHAT(13, (user, buf) -> buf.skipBytes(65)),
    EXTENSION_PROTOCOL_INFO(16, (user, buf) -> buf.skipBytes(66)),
    EXTENSION_PROTOCOL_ENTRY(17, (user, buf) -> buf.skipBytes(68)),
    EXT_CUSTOM_BLOCKS_SUPPORT_LEVEL(19, (user, buf) -> buf.skipBytes(1)),
    EXT_TWO_WAY_PING(43, (user, buf) -> buf.skipBytes(3));

    private static final ServerboundPacketsc0_30cpe[] REGISTRY;
    private final int id;
    private final BiConsumer<UserConnection, ByteBuf> packetReader;

    public static ServerboundPacketsc0_30cpe getPacket(int id) {
        return REGISTRY[id];
    }

    private ServerboundPacketsc0_30cpe(int id, BiConsumer<UserConnection, ByteBuf> packetReader) {
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
        REGISTRY = new ServerboundPacketsc0_30cpe[256];
        ServerboundPacketsc0_30cpe[] serverboundPacketsc0_30cpeArray = ServerboundPacketsc0_30cpe.values();
        int n = serverboundPacketsc0_30cpeArray.length;
        for (int i = 0; i < n; ++i) {
            ServerboundPacketsc0_30cpe packet;
            ServerboundPacketsc0_30cpe.REGISTRY[packet.id] = packet = serverboundPacketsc0_30cpeArray[i];
        }
    }
}

