/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.alpha.a1_2_2toa1_2_3_1_2_3_4.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;
import net.raphimc.vialegacy.api.splitter.PreNettyTypes;

public enum ServerboundPacketsa1_2_2 implements ServerboundPacketType,
PreNettyPacketType
{
    KEEP_ALIVE(0, (user, buf) -> {}),
    LOGIN(1, (user, buf) -> {
        buf.skipBytes(4);
        PreNettyTypes.readUTF(buf);
        PreNettyTypes.readUTF(buf);
        buf.skipBytes(9);
    }),
    HANDSHAKE(2, (user, buf) -> PreNettyTypes.readUTF(buf)),
    CHAT(3, (user, buf) -> PreNettyTypes.readUTF(buf)),
    PLAYER_INVENTORY(5, (user, buf) -> {
        buf.skipBytes(4);
        int x = buf.readShort();
        for (int i = 0; i < x; ++i) {
            PreNettyTypes.readItemStackb1_2(buf);
        }
    }),
    INTERACT(7, (user, buf) -> buf.skipBytes(8)),
    MOVE_PLAYER_STATUS_ONLY(10, (user, buf) -> buf.skipBytes(1)),
    MOVE_PLAYER_POS(11, (user, buf) -> buf.skipBytes(33)),
    MOVE_PLAYER_ROT(12, (user, buf) -> buf.skipBytes(9)),
    MOVE_PLAYER_POS_ROT(13, (user, buf) -> buf.skipBytes(41)),
    PLAYER_ACTION(14, (user, buf) -> buf.skipBytes(11)),
    USE_ITEM_ON(15, (user, buf) -> buf.skipBytes(12)),
    SET_CARRIED_ITEM(16, (user, buf) -> buf.skipBytes(6)),
    SWING(18, (user, buf) -> buf.skipBytes(5)),
    SPAWN_ITEM(21, (user, buf) -> buf.skipBytes(22)),
    BLOCK_ENTITY_DATA(59, (user, buf) -> {
        buf.skipBytes(10);
        int x = buf.readUnsignedShort();
        for (int i = 0; i < x; ++i) {
            buf.readByte();
        }
    }),
    DISCONNECT(255, (user, buf) -> PreNettyTypes.readUTF(buf));

    private static final ServerboundPacketsa1_2_2[] REGISTRY;
    private final int id;
    private final BiConsumer<UserConnection, ByteBuf> packetReader;

    public static ServerboundPacketsa1_2_2 getPacket(int id) {
        return REGISTRY[id];
    }

    private ServerboundPacketsa1_2_2(int id, BiConsumer<UserConnection, ByteBuf> packetReader) {
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
        REGISTRY = new ServerboundPacketsa1_2_2[256];
        ServerboundPacketsa1_2_2[] serverboundPacketsa1_2_2Array = ServerboundPacketsa1_2_2.values();
        int n = serverboundPacketsa1_2_2Array.length;
        for (int i = 0; i < n; ++i) {
            ServerboundPacketsa1_2_2 packet;
            ServerboundPacketsa1_2_2.REGISTRY[packet.id] = packet = serverboundPacketsa1_2_2Array[i];
        }
    }
}

