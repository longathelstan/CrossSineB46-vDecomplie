/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_9_3to1_9_1.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.protocol.v1_9_3to1_9_1.Protocol1_9_3To1_9_1;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockEntity1_9_1 {
    private static final Map<String, Integer> TYPES = new HashMap<String, Integer>();

    public static void handle(List<CompoundTag> tags, UserConnection connection) {
        for (CompoundTag tag : tags) {
            int newId;
            String id;
            StringTag idTag = tag.getStringTag("id");
            if (idTag == null || !TYPES.containsKey(id = idTag.getValue()) || (newId = TYPES.get(id).intValue()) == -1) continue;
            int x = tag.getNumberTag("x").asInt();
            short y = tag.getNumberTag("y").asShort();
            int z = tag.getNumberTag("z").asInt();
            BlockPosition pos = new BlockPosition(x, y, z);
            BlockEntity1_9_1.updateBlockEntity(pos, (short)newId, tag, connection);
        }
    }

    private static void updateBlockEntity(BlockPosition pos, short id, CompoundTag tag, UserConnection connection) {
        PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, null, connection);
        wrapper.write(Types.BLOCK_POSITION1_8, pos);
        wrapper.write(Types.UNSIGNED_BYTE, id);
        wrapper.write(Types.NAMED_COMPOUND_TAG, tag);
        wrapper.scheduleSend(Protocol1_9_3To1_9_1.class, false);
    }

    static {
        TYPES.put("MobSpawner", 1);
        TYPES.put("Control", 2);
        TYPES.put("Beacon", 3);
        TYPES.put("Skull", 4);
        TYPES.put("FlowerPot", 5);
        TYPES.put("Banner", 6);
        TYPES.put("UNKNOWN", 7);
        TYPES.put("EndGateway", 8);
        TYPES.put("Sign", 9);
    }
}

