/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_16_1to1_16_2.rewriter;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_16_2;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16_2;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import java.util.ArrayList;
import java.util.List;

public class WorldPacketRewriter1_16_2 {
    private static final BlockChangeRecord[] EMPTY_RECORDS = new BlockChangeRecord[0];

    public static void register(Protocol1_16_1To1_16_2 protocol) {
        BlockRewriter<ClientboundPackets1_16> blockRewriter = BlockRewriter.for1_14(protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_16.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_16.BLOCK_UPDATE);
        blockRewriter.registerBlockBreakAck(ClientboundPackets1_16.BLOCK_BREAK_ACK);
        blockRewriter.registerLevelChunk(ClientboundPackets1_16.LEVEL_CHUNK, ChunkType1_16.TYPE, ChunkType1_16_2.TYPE);
        protocol.registerClientbound(ClientboundPackets1_16.CHUNK_BLOCKS_UPDATE, ClientboundPackets1_16_2.SECTION_BLOCKS_UPDATE, wrapper -> {
            BlockChangeRecord[] blockChangeRecord;
            wrapper.cancel();
            int chunkX = wrapper.read(Types.INT);
            int chunkZ = wrapper.read(Types.INT);
            long chunkPosition = 0L;
            chunkPosition |= ((long)chunkX & 0x3FFFFFL) << 42;
            chunkPosition |= ((long)chunkZ & 0x3FFFFFL) << 20;
            List[] sectionRecords = new List[16];
            for (BlockChangeRecord record : blockChangeRecord = wrapper.read(Types.BLOCK_CHANGE_ARRAY)) {
                int chunkY = record.getY() >> 4;
                ArrayList<BlockChangeRecord1_16_2> list = sectionRecords[chunkY];
                if (list == null) {
                    sectionRecords[chunkY] = list = new ArrayList<BlockChangeRecord1_16_2>();
                }
                int blockId = protocol.getMappingData().getNewBlockStateId(record.getBlockId());
                list.add(new BlockChangeRecord1_16_2(record.getSectionX(), record.getSectionY(), record.getSectionZ(), blockId));
            }
            for (int chunkY = 0; chunkY < sectionRecords.length; ++chunkY) {
                List sectionRecord = sectionRecords[chunkY];
                if (sectionRecord == null) continue;
                PacketWrapper newPacket = wrapper.create(ClientboundPackets1_16_2.SECTION_BLOCKS_UPDATE);
                newPacket.write(Types.LONG, chunkPosition | (long)chunkY & 0xFFFFFL);
                newPacket.write(Types.BOOLEAN, false);
                newPacket.write(Types.VAR_LONG_BLOCK_CHANGE_ARRAY, sectionRecord.toArray(EMPTY_RECORDS));
                newPacket.send(Protocol1_16_1To1_16_2.class);
            }
        });
        blockRewriter.registerLevelEvent(ClientboundPackets1_16.LEVEL_EVENT, 1010, 2001);
    }
}

