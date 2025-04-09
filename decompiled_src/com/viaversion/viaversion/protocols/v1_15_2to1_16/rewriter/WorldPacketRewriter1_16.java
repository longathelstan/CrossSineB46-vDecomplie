/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_15_2to1_16.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.LongArrayTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_15;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.UUIDUtil;
import java.util.Map;
import java.util.UUID;

public class WorldPacketRewriter1_16 {
    public static void register(Protocol1_15_2To1_16 protocol) {
        BlockRewriter<ClientboundPackets1_15> blockRewriter = BlockRewriter.for1_14(protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_15.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_15.BLOCK_UPDATE);
        blockRewriter.registerChunkBlocksUpdate(ClientboundPackets1_15.CHUNK_BLOCKS_UPDATE);
        blockRewriter.registerBlockBreakAck(ClientboundPackets1_15.BLOCK_BREAK_ACK);
        blockRewriter.registerLevelChunk(ClientboundPackets1_15.LEVEL_CHUNK, ChunkType1_15.TYPE, ChunkType1_16.TYPE, (connection, chunk) -> {
            chunk.setIgnoreOldLightData(chunk.isFullChunk());
            CompoundTag heightMaps = chunk.getHeightMap();
            for (Tag heightMapTag : heightMaps.values()) {
                LongArrayTag heightMap = (LongArrayTag)heightMapTag;
                int[] heightMapData = new int[256];
                CompactArrayUtil.iterateCompactArray(9, heightMapData.length, heightMap.getValue(), (i, v) -> {
                    heightMapData[i] = v;
                });
                heightMap.setValue(CompactArrayUtil.createCompactArrayWithPadding(9, heightMapData.length, i -> heightMapData[i]));
            }
            if (chunk.getBlockEntities() == null) {
                return;
            }
            for (CompoundTag blockEntity : chunk.getBlockEntities()) {
                WorldPacketRewriter1_16.handleBlockEntity(protocol, connection, blockEntity);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_15.LIGHT_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> wrapper.write(Types.BOOLEAN, true));
            }
        });
        protocol.registerClientbound(ClientboundPackets1_15.BLOCK_ENTITY_DATA, wrapper -> {
            wrapper.passthrough(Types.BLOCK_POSITION1_14);
            wrapper.passthrough(Types.UNSIGNED_BYTE);
            CompoundTag tag = wrapper.passthrough(Types.NAMED_COMPOUND_TAG);
            WorldPacketRewriter1_16.handleBlockEntity(protocol, wrapper.user(), tag);
        });
        blockRewriter.registerLevelEvent(ClientboundPackets1_15.LEVEL_EVENT, 1010, 2001);
    }

    static void handleBlockEntity(Protocol1_15_2To1_16 protocol, UserConnection connection, CompoundTag compoundTag) {
        StringTag spawnDataIdTag;
        CompoundTag spawnDataTag;
        StringTag idTag = compoundTag.getStringTag("id");
        if (idTag == null) {
            return;
        }
        String id = Key.namespaced(idTag.getValue());
        if (id.equals("minecraft:conduit")) {
            Tag targetUuidTag = compoundTag.remove("target_uuid");
            if (!(targetUuidTag instanceof StringTag)) {
                return;
            }
            UUID targetUuid = UUID.fromString((String)targetUuidTag.getValue());
            compoundTag.put("Target", new IntArrayTag(UUIDUtil.toIntArray(targetUuid)));
        } else if (id.equals("minecraft:skull") && compoundTag.getCompoundTag("Owner") != null) {
            CompoundTag ownerTag = (CompoundTag)compoundTag.removeUnchecked("Owner");
            Tag ownerUuidTag = ownerTag.remove("Id");
            if (ownerUuidTag instanceof StringTag) {
                UUID ownerUuid = UUID.fromString(((StringTag)ownerUuidTag).getValue());
                ownerTag.put("Id", new IntArrayTag(UUIDUtil.toIntArray(ownerUuid)));
            }
            CompoundTag skullOwnerTag = new CompoundTag();
            for (Map.Entry<String, Tag> entry : ownerTag.entrySet()) {
                skullOwnerTag.put(entry.getKey(), entry.getValue());
            }
            compoundTag.put("SkullOwner", skullOwnerTag);
        } else if (id.equals("minecraft:sign")) {
            for (int i = 1; i <= 4; ++i) {
                int n = i;
                StringTag line = compoundTag.getStringTag("Text" + n);
                if (line == null) continue;
                JsonElement text = protocol.getComponentRewriter().processText(connection, line.getValue());
                int n2 = i;
                compoundTag.putString("Text" + n2, text.toString());
            }
        } else if (id.equals("minecraft:mob_spawner") && (spawnDataTag = compoundTag.getCompoundTag("SpawnData")) != null && (spawnDataIdTag = spawnDataTag.getStringTag("id")) != null && spawnDataIdTag.getValue().equals("minecraft:zombie_pigman")) {
            spawnDataIdTag.setValue("minecraft:zombified_piglin");
        }
    }
}

