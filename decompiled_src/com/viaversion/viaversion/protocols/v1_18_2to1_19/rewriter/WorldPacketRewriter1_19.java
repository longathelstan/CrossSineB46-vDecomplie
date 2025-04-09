/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_18_2to1_19.rewriter;

import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.packet.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.packet.ServerboundPackets1_19;
import com.viaversion.viaversion.rewriter.BlockRewriter;

public final class WorldPacketRewriter1_19 {
    public static void register(Protocol1_18_2To1_19 protocol) {
        BlockRewriter<ClientboundPackets1_18> blockRewriter = BlockRewriter.for1_14(protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_18.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_18.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate(ClientboundPackets1_18.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelEvent(ClientboundPackets1_18.LEVEL_EVENT, 1010, 2001);
        blockRewriter.registerLevelChunk1_19(ClientboundPackets1_18.LEVEL_CHUNK_WITH_LIGHT, ChunkType1_18::new);
        protocol.cancelClientbound(ClientboundPackets1_18.BLOCK_BREAK_ACK);
        protocol.registerServerbound(ServerboundPackets1_19.SET_BEACON, wrapper -> {
            if (wrapper.read(Types.BOOLEAN).booleanValue()) {
                wrapper.passthrough(Types.VAR_INT);
            } else {
                wrapper.write(Types.VAR_INT, -1);
            }
            if (wrapper.read(Types.BOOLEAN).booleanValue()) {
                wrapper.passthrough(Types.VAR_INT);
            } else {
                wrapper.write(Types.VAR_INT, -1);
            }
        });
    }
}

