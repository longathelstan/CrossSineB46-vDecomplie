/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.alpha.a1_0_16_2toa1_0_17_1_0_17_4.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;
import net.raphimc.vialegacy.api.splitter.PreNettyTypes;

public enum ClientboundPacketsa1_0_16 implements ClientboundPacketType,
PreNettyPacketType
{
    KEEP_ALIVE(0, (user, buf) -> {}),
    LOGIN(1, (user, buf) -> {
        buf.skipBytes(4);
        PreNettyTypes.readUTF(buf);
        PreNettyTypes.readUTF(buf);
    }),
    HANDSHAKE(2, (user, buf) -> PreNettyTypes.readUTF(buf)),
    CHAT(3, (user, buf) -> PreNettyTypes.readUTF(buf)),
    MOVE_PLAYER_STATUS_ONLY(10, (user, buf) -> buf.skipBytes(1)),
    MOVE_PLAYER_POS(11, (user, buf) -> buf.skipBytes(33)),
    MOVE_PLAYER_ROT(12, (user, buf) -> buf.skipBytes(9)),
    PLAYER_POSITION(13, (user, buf) -> buf.skipBytes(41)),
    SET_CARRIED_ITEM(16, (user, buf) -> buf.skipBytes(6)),
    ADD_TO_INVENTORY(17, (user, buf) -> buf.skipBytes(5)),
    ANIMATE(18, (user, buf) -> buf.skipBytes(5)),
    ADD_PLAYER(20, (user, buf) -> {
        buf.skipBytes(4);
        PreNettyTypes.readUTF(buf);
        buf.skipBytes(16);
    }),
    SPAWN_ITEM(21, (user, buf) -> buf.skipBytes(22)),
    TAKE_ITEM_ENTITY(22, (user, buf) -> buf.skipBytes(8)),
    ADD_ENTITY(23, (user, buf) -> buf.skipBytes(17)),
    REMOVE_ENTITIES(29, (user, buf) -> buf.skipBytes(4)),
    MOVE_ENTITY(30, (user, buf) -> buf.skipBytes(4)),
    MOVE_ENTITY_POS(31, (user, buf) -> buf.skipBytes(7)),
    MOVE_ENTITY_ROT(32, (user, buf) -> buf.skipBytes(6)),
    MOVE_ENTITY_POS_ROT(33, (user, buf) -> buf.skipBytes(9)),
    TELEPORT_ENTITY(34, (user, buf) -> buf.skipBytes(18)),
    PRE_CHUNK(50, (user, buf) -> buf.skipBytes(9)),
    LEVEL_CHUNK(51, (user, buf) -> {
        buf.skipBytes(13);
        int x = buf.readInt();
        for (int i = 0; i < x; ++i) {
            buf.readByte();
        }
    }),
    CHUNK_BLOCKS_UPDATE(52, (user, buf) -> {
        int i;
        buf.skipBytes(8);
        int x = buf.readShort();
        for (i = 0; i < x; ++i) {
            buf.readShort();
        }
        for (i = 0; i < x; ++i) {
            buf.readByte();
        }
        for (i = 0; i < x; ++i) {
            buf.readByte();
        }
    }),
    BLOCK_UPDATE(53, (user, buf) -> buf.skipBytes(11)),
    DISCONNECT(255, (user, buf) -> PreNettyTypes.readUTF(buf));

    private static final ClientboundPacketsa1_0_16[] REGISTRY;
    private final int id;
    private final BiConsumer<UserConnection, ByteBuf> packetReader;

    public static ClientboundPacketsa1_0_16 getPacket(int id) {
        return REGISTRY[id];
    }

    private ClientboundPacketsa1_0_16(int id, BiConsumer<UserConnection, ByteBuf> packetReader) {
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
        REGISTRY = new ClientboundPacketsa1_0_16[256];
        ClientboundPacketsa1_0_16[] clientboundPacketsa1_0_16Array = ClientboundPacketsa1_0_16.values();
        int n = clientboundPacketsa1_0_16Array.length;
        for (int i = 0; i < n; ++i) {
            ClientboundPacketsa1_0_16 packet;
            ClientboundPacketsa1_0_16.REGISTRY[packet.id] = packet = clientboundPacketsa1_0_16Array[i];
        }
    }
}

