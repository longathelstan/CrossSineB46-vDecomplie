/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;
import net.raphimc.vialegacy.api.splitter.PreNettyTypes;

public enum ClientboundPackets1_6_4 implements ClientboundPacketType,
PreNettyPacketType
{
    KEEP_ALIVE(0, (user, buf) -> buf.skipBytes(4)),
    LOGIN(1, (user, buf) -> {
        buf.skipBytes(4);
        PreNettyTypes.readString(buf);
        buf.skipBytes(5);
    }),
    CHAT(3, (user, buf) -> PreNettyTypes.readString(buf)),
    SET_TIME(4, (user, buf) -> buf.skipBytes(16)),
    SET_EQUIPPED_ITEM(5, (user, buf) -> {
        buf.skipBytes(6);
        PreNettyTypes.readItemStack1_3_1(buf);
    }),
    SET_DEFAULT_SPAWN_POSITION(6, (user, buf) -> buf.skipBytes(12)),
    SET_HEALTH(8, (user, buf) -> buf.skipBytes(10)),
    RESPAWN(9, (user, buf) -> {
        buf.skipBytes(8);
        PreNettyTypes.readString(buf);
    }),
    MOVE_PLAYER_STATUS_ONLY(10, (user, buf) -> buf.skipBytes(1)),
    MOVE_PLAYER_POS(11, (user, buf) -> buf.skipBytes(33)),
    MOVE_PLAYER_ROT(12, (user, buf) -> buf.skipBytes(9)),
    PLAYER_POSITION(13, (user, buf) -> buf.skipBytes(41)),
    SET_CARRIED_ITEM(16, (user, buf) -> buf.skipBytes(2)),
    PLAYER_SLEEP(17, (user, buf) -> buf.skipBytes(14)),
    ANIMATE(18, (user, buf) -> buf.skipBytes(5)),
    ADD_PLAYER(20, (user, buf) -> {
        buf.skipBytes(4);
        PreNettyTypes.readString(buf);
        buf.skipBytes(16);
        PreNettyTypes.readEntityDataList1_4_4(buf);
    }),
    TAKE_ITEM_ENTITY(22, (user, buf) -> buf.skipBytes(8)),
    ADD_ENTITY(23, (user, buf) -> {
        buf.skipBytes(19);
        int i = buf.readInt();
        if (i > 0) {
            buf.skipBytes(6);
        }
    }),
    ADD_MOB(24, (user, buf) -> {
        buf.skipBytes(26);
        PreNettyTypes.readEntityDataList1_4_4(buf);
    }),
    ADD_PAINTING(25, (user, buf) -> {
        buf.skipBytes(4);
        PreNettyTypes.readString(buf);
        buf.skipBytes(16);
    }),
    ADD_EXPERIENCE_ORB(26, (user, buf) -> buf.skipBytes(18)),
    SET_ENTITY_MOTION(28, (user, buf) -> buf.skipBytes(10)),
    REMOVE_ENTITIES(29, (user, buf) -> {
        int x = buf.readUnsignedByte();
        for (int i = 0; i < x; ++i) {
            buf.readInt();
        }
    }),
    MOVE_ENTITY(30, (user, buf) -> buf.skipBytes(4)),
    MOVE_ENTITY_POS(31, (user, buf) -> buf.skipBytes(7)),
    MOVE_ENTITY_ROT(32, (user, buf) -> buf.skipBytes(6)),
    MOVE_ENTITY_POS_ROT(33, (user, buf) -> buf.skipBytes(9)),
    TELEPORT_ENTITY(34, (user, buf) -> buf.skipBytes(18)),
    ROTATE_HEAD(35, (user, buf) -> buf.skipBytes(5)),
    ENTITY_EVENT(38, (user, buf) -> buf.skipBytes(5)),
    SET_ENTITY_LINK(39, (user, buf) -> buf.skipBytes(9)),
    SET_ENTITY_DATA(40, (user, buf) -> {
        buf.skipBytes(4);
        PreNettyTypes.readEntityDataList1_4_4(buf);
    }),
    UPDATE_MOB_EFFECT(41, (user, buf) -> buf.skipBytes(8)),
    REMOVE_MOB_EFFECT(42, (user, buf) -> buf.skipBytes(5)),
    SET_EXPERIENCE(43, (user, buf) -> buf.skipBytes(8)),
    UPDATE_ATTRIBUTES(44, (user, buf) -> {
        buf.skipBytes(4);
        int x = buf.readInt();
        for (int i = 0; i < x; ++i) {
            PreNettyTypes.readString(buf);
            buf.skipBytes(8);
            int s = buf.readUnsignedShort();
            for (int k = 0; k < s; ++k) {
                buf.skipBytes(25);
            }
        }
    }),
    LEVEL_CHUNK(51, (user, buf) -> {
        buf.skipBytes(13);
        int x = buf.readInt();
        for (int i = 0; i < x; ++i) {
            buf.readByte();
        }
    }),
    CHUNK_BLOCKS_UPDATE(52, (user, buf) -> {
        buf.skipBytes(10);
        int x = buf.readInt();
        for (int i = 0; i < x; ++i) {
            buf.readByte();
        }
    }),
    BLOCK_UPDATE(53, (user, buf) -> buf.skipBytes(12)),
    BLOCK_EVENT(54, (user, buf) -> buf.skipBytes(14)),
    BLOCK_DESTRUCTION(55, (user, buf) -> buf.skipBytes(17)),
    MAP_BULK_CHUNK(56, (user, buf) -> {
        int i;
        int x = buf.readShort();
        int y = buf.readInt();
        buf.readBoolean();
        for (i = 0; i < y; ++i) {
            buf.readByte();
        }
        for (i = 0; i < x; ++i) {
            buf.skipBytes(12);
        }
    }),
    EXPLODE(60, (user, buf) -> {
        buf.skipBytes(28);
        int x = buf.readInt();
        for (int i = 0; i < x; ++i) {
            buf.skipBytes(3);
        }
        buf.skipBytes(12);
    }),
    LEVEL_EVENT(61, (user, buf) -> buf.skipBytes(18)),
    CUSTOM_SOUND(62, (user, buf) -> {
        PreNettyTypes.readString(buf);
        buf.skipBytes(17);
    }),
    LEVEL_PARTICLES(63, (user, buf) -> {
        PreNettyTypes.readString(buf);
        buf.skipBytes(32);
    }),
    GAME_EVENT(70, (user, buf) -> buf.skipBytes(2)),
    ADD_GLOBAL_ENTITY(71, (user, buf) -> buf.skipBytes(17)),
    OPEN_SCREEN(100, (user, buf) -> {
        buf.skipBytes(1);
        byte x = buf.readByte();
        PreNettyTypes.readString(buf);
        buf.skipBytes(2);
        if (x == 11) {
            buf.readInt();
        }
    }),
    CONTAINER_CLOSE(101, (user, buf) -> buf.skipBytes(1)),
    CONTAINER_SET_SLOT(103, (user, buf) -> {
        buf.skipBytes(3);
        PreNettyTypes.readItemStack1_3_1(buf);
    }),
    CONTAINER_SET_CONTENT(104, (user, buf) -> {
        buf.skipBytes(1);
        int x = buf.readShort();
        for (int i = 0; i < x; ++i) {
            PreNettyTypes.readItemStack1_3_1(buf);
        }
    }),
    CONTAINER_SET_DATA(105, (user, buf) -> buf.skipBytes(5)),
    CONTAINER_ACK(106, (user, buf) -> buf.skipBytes(4)),
    SET_CREATIVE_MODE_SLOT(107, (user, buf) -> {
        buf.skipBytes(2);
        PreNettyTypes.readItemStack1_3_1(buf);
    }),
    UPDATE_SIGN(130, (user, buf) -> {
        buf.skipBytes(10);
        PreNettyTypes.readString(buf);
        PreNettyTypes.readString(buf);
        PreNettyTypes.readString(buf);
        PreNettyTypes.readString(buf);
    }),
    MAP_ITEM_DATA(131, (user, buf) -> {
        buf.skipBytes(4);
        int x = buf.readUnsignedShort();
        for (int i = 0; i < x; ++i) {
            buf.readByte();
        }
    }),
    BLOCK_ENTITY_DATA(132, (user, buf) -> {
        buf.skipBytes(11);
        PreNettyTypes.readTag(buf);
    }),
    OPEN_SIGN_EDITOR(133, (user, buf) -> buf.skipBytes(13)),
    AWARD_STATS(200, (user, buf) -> buf.skipBytes(8)),
    PLAYER_INFO(201, (user, buf) -> {
        PreNettyTypes.readString(buf);
        buf.skipBytes(3);
    }),
    PLAYER_ABILITIES(202, (user, buf) -> buf.skipBytes(9)),
    COMMAND_SUGGESTIONS(203, (user, buf) -> PreNettyTypes.readString(buf)),
    SET_OBJECTIVE(206, (user, buf) -> {
        PreNettyTypes.readString(buf);
        PreNettyTypes.readString(buf);
        buf.skipBytes(1);
    }),
    SET_SCORE(207, (user, buf) -> {
        PreNettyTypes.readString(buf);
        byte b = buf.readByte();
        if (b != 1) {
            PreNettyTypes.readString(buf);
            buf.skipBytes(4);
        }
    }),
    SET_DISPLAY_OBJECTIVE(208, (user, buf) -> {
        buf.skipBytes(1);
        PreNettyTypes.readString(buf);
    }),
    SET_PLAYER_TEAM(209, (user, buf) -> {
        PreNettyTypes.readString(buf);
        int x = buf.readByte();
        if (x == 0 || x == 2) {
            PreNettyTypes.readString(buf);
            PreNettyTypes.readString(buf);
            PreNettyTypes.readString(buf);
            buf.skipBytes(1);
        }
        if (x == 0 || x == 3 || x == 4) {
            x = buf.readShort();
            for (int i = 0; i < x; ++i) {
                PreNettyTypes.readString(buf);
            }
        }
    }),
    CUSTOM_PAYLOAD(250, (user, buf) -> {
        PreNettyTypes.readString(buf);
        int s = buf.readShort();
        for (int i = 0; i < s; ++i) {
            buf.readByte();
        }
    }),
    SHARED_KEY(252, (user, buf) -> {
        PreNettyTypes.readByteArray(buf);
        PreNettyTypes.readByteArray(buf);
    }),
    SERVER_AUTH_DATA(253, (user, buf) -> {
        PreNettyTypes.readString(buf);
        PreNettyTypes.readByteArray(buf);
        PreNettyTypes.readByteArray(buf);
    }),
    DISCONNECT(255, (user, buf) -> PreNettyTypes.readString(buf));

    private static final ClientboundPackets1_6_4[] REGISTRY;
    private final int id;
    private final BiConsumer<UserConnection, ByteBuf> packetReader;

    public static ClientboundPackets1_6_4 getPacket(int id) {
        return REGISTRY[id];
    }

    private ClientboundPackets1_6_4(int id, BiConsumer<UserConnection, ByteBuf> packetReader) {
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
        REGISTRY = new ClientboundPackets1_6_4[256];
        ClientboundPackets1_6_4[] clientboundPackets1_6_4Array = ClientboundPackets1_6_4.values();
        int n = clientboundPackets1_6_4Array.length;
        for (int i = 0; i < n; ++i) {
            ClientboundPackets1_6_4 packet;
            ClientboundPackets1_6_4.REGISTRY[packet.id] = packet = clientboundPackets1_6_4Array[i];
        }
    }
}

