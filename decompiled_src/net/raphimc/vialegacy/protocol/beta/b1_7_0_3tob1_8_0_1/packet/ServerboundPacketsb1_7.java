/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;
import net.raphimc.vialegacy.api.splitter.PreNettyTypes;

public enum ServerboundPacketsb1_7 implements ServerboundPacketType,
PreNettyPacketType
{
    KEEP_ALIVE(0, (user, buf) -> {}),
    LOGIN(1, (user, buf) -> {
        buf.skipBytes(4);
        PreNettyTypes.readString(buf);
        buf.skipBytes(9);
    }),
    HANDSHAKE(2, (user, buf) -> PreNettyTypes.readString(buf)),
    CHAT(3, (user, buf) -> PreNettyTypes.readString(buf)),
    INTERACT(7, (user, buf) -> buf.skipBytes(9)),
    RESPAWN(9, (user, buf) -> buf.skipBytes(1)),
    MOVE_PLAYER_STATUS_ONLY(10, (user, buf) -> buf.skipBytes(1)),
    MOVE_PLAYER_POS(11, (user, buf) -> buf.skipBytes(33)),
    MOVE_PLAYER_ROT(12, (user, buf) -> buf.skipBytes(9)),
    MOVE_PLAYER_POS_ROT(13, (user, buf) -> buf.skipBytes(41)),
    PLAYER_ACTION(14, (user, buf) -> buf.skipBytes(11)),
    USE_ITEM_ON(15, (user, buf) -> {
        buf.skipBytes(10);
        PreNettyTypes.readItemStackb1_2(buf);
    }),
    SET_CARRIED_ITEM(16, (user, buf) -> buf.skipBytes(2)),
    SWING(18, (user, buf) -> buf.skipBytes(5)),
    PLAYER_COMMAND(19, (user, buf) -> buf.skipBytes(5)),
    POSITION(27, (user, buf) -> buf.skipBytes(18)),
    CONTAINER_CLOSE(101, (user, buf) -> buf.skipBytes(1)),
    CONTAINER_CLICK(102, (user, buf) -> {
        buf.skipBytes(7);
        PreNettyTypes.readItemStackb1_2(buf);
    }),
    CONTAINER_ACK(106, (user, buf) -> buf.skipBytes(4)),
    SIGN_UPDATE(130, (user, buf) -> {
        buf.skipBytes(10);
        PreNettyTypes.readString(buf);
        PreNettyTypes.readString(buf);
        PreNettyTypes.readString(buf);
        PreNettyTypes.readString(buf);
    }),
    DISCONNECT(255, (user, buf) -> PreNettyTypes.readString(buf));

    private static final ServerboundPacketsb1_7[] REGISTRY;
    private final int id;
    private final BiConsumer<UserConnection, ByteBuf> packetReader;

    public static ServerboundPacketsb1_7 getPacket(int id) {
        return REGISTRY[id];
    }

    private ServerboundPacketsb1_7(int id, BiConsumer<UserConnection, ByteBuf> packetReader) {
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
        REGISTRY = new ServerboundPacketsb1_7[256];
        ServerboundPacketsb1_7[] serverboundPacketsb1_7Array = ServerboundPacketsb1_7.values();
        int n = serverboundPacketsb1_7Array.length;
        for (int i = 0; i < n; ++i) {
            ServerboundPacketsb1_7 packet;
            ServerboundPacketsb1_7.REGISTRY[packet.id] = packet = serverboundPacketsb1_7Array[i];
        }
    }
}

