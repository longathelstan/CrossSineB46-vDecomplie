/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_0_15a_1toc0_0_16a_02.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;

public enum ClientboundPacketsc0_15a implements ClientboundPacketType,
PreNettyPacketType
{
    LOGIN(0, (user, buf) -> buf.skipBytes(64)),
    KEEP_ALIVE(1, (user, buf) -> {}),
    LEVEL_INIT(2, (user, buf) -> {}),
    LEVEL_DATA(3, (user, buf) -> buf.skipBytes(1027)),
    LEVEL_FINALIZE(4, (user, buf) -> buf.skipBytes(6)),
    BLOCK_UPDATE(6, (user, buf) -> buf.skipBytes(7)),
    ADD_PLAYER(7, (user, buf) -> buf.skipBytes(73)),
    TELEPORT_ENTITY(8, (user, buf) -> buf.skipBytes(9)),
    REMOVE_ENTITIES(9, (user, buf) -> buf.skipBytes(1));

    private static final ClientboundPacketsc0_15a[] REGISTRY;
    private final int id;
    private final BiConsumer<UserConnection, ByteBuf> packetReader;

    public static ClientboundPacketsc0_15a getPacket(int id) {
        return REGISTRY[id];
    }

    private ClientboundPacketsc0_15a(int id, BiConsumer<UserConnection, ByteBuf> packetReader) {
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
        REGISTRY = new ClientboundPacketsc0_15a[256];
        ClientboundPacketsc0_15a[] clientboundPacketsc0_15aArray = ClientboundPacketsc0_15a.values();
        int n = clientboundPacketsc0_15aArray.length;
        for (int i = 0; i < n; ++i) {
            ClientboundPacketsc0_15a packet;
            ClientboundPacketsc0_15a.REGISTRY[packet.id] = packet = clientboundPacketsc0_15aArray[i];
        }
    }
}

