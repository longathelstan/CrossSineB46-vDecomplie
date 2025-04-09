/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_0_20a_27toc0_28_30.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;

public enum ClientboundPacketsc0_20a implements ClientboundPacketType,
PreNettyPacketType
{
    LOGIN(0, (user, buf) -> buf.skipBytes(130)),
    KEEP_ALIVE(1, (user, buf) -> {}),
    LEVEL_INIT(2, (user, buf) -> {}),
    LEVEL_DATA(3, (user, buf) -> buf.skipBytes(1027)),
    LEVEL_FINALIZE(4, (user, buf) -> buf.skipBytes(6)),
    BLOCK_UPDATE(6, (user, buf) -> buf.skipBytes(7)),
    ADD_PLAYER(7, (user, buf) -> buf.skipBytes(73)),
    TELEPORT_ENTITY(8, (user, buf) -> buf.skipBytes(9)),
    MOVE_ENTITY_POS_ROT(9, (user, buf) -> buf.skipBytes(6)),
    MOVE_ENTITY_POS(10, (user, buf) -> buf.skipBytes(4)),
    MOVE_ENTITY_ROT(11, (user, buf) -> buf.skipBytes(3)),
    REMOVE_ENTITIES(12, (user, buf) -> buf.skipBytes(1)),
    CHAT(13, (user, buf) -> buf.skipBytes(65)),
    DISCONNECT(14, (user, buf) -> buf.skipBytes(64));

    private static final ClientboundPacketsc0_20a[] REGISTRY;
    private final int id;
    private final BiConsumer<UserConnection, ByteBuf> packetReader;

    public static ClientboundPacketsc0_20a getPacket(int id) {
        return REGISTRY[id];
    }

    private ClientboundPacketsc0_20a(int id, BiConsumer<UserConnection, ByteBuf> packetReader) {
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
        REGISTRY = new ClientboundPacketsc0_20a[256];
        ClientboundPacketsc0_20a[] clientboundPacketsc0_20aArray = ClientboundPacketsc0_20a.values();
        int n = clientboundPacketsc0_20aArray.length;
        for (int i = 0; i < n; ++i) {
            ClientboundPacketsc0_20a packet;
            ClientboundPacketsc0_20a.REGISTRY[packet.id] = packet = clientboundPacketsc0_20aArray[i];
        }
    }
}

