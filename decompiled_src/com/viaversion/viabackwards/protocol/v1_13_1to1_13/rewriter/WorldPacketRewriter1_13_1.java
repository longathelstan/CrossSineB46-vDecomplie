/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13_1to1_13.rewriter;

import com.viaversion.viabackwards.protocol.v1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.rewriter.BlockRewriter;

public class WorldPacketRewriter1_13_1 {
    public static void register(final Protocol1_13_1To1_13 protocol) {
        BlockRewriter<ClientboundPackets1_13> blockRewriter = BlockRewriter.legacy(protocol);
        protocol.registerClientbound(ClientboundPackets1_13.LEVEL_CHUNK, wrapper -> {
            Object clientWorld = wrapper.user().getClientWorld(Protocol1_13_1To1_13.class);
            Chunk chunk = wrapper.passthrough(ChunkType1_13.forEnvironment(((ClientWorld)clientWorld).getEnvironment()));
            blockRewriter.handleChunk(chunk);
        });
        blockRewriter.registerBlockEvent(ClientboundPackets1_13.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_13.BLOCK_UPDATE);
        blockRewriter.registerChunkBlocksUpdate(ClientboundPackets1_13.CHUNK_BLOCKS_UPDATE);
        protocol.registerClientbound(ClientboundPackets1_13.LEVEL_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Types.INT, 0);
                    int data = wrapper.get(Types.INT, 1);
                    if (id == 1010) {
                        wrapper.set(Types.INT, 1, protocol.getMappingData().getNewItemId(data));
                    } else if (id == 2001) {
                        wrapper.set(Types.INT, 1, protocol.getMappingData().getNewBlockStateId(data));
                    } else if (id == 2000) {
                        switch (data) {
                            case 0: 
                            case 1: {
                                BlockPosition pos = wrapper.get(Types.BLOCK_POSITION1_8, 0);
                                BlockFace relative = data == 0 ? BlockFace.BOTTOM : BlockFace.TOP;
                                wrapper.set(Types.BLOCK_POSITION1_8, 0, pos.getRelative(relative));
                                wrapper.set(Types.INT, 1, 4);
                                break;
                            }
                            case 2: {
                                wrapper.set(Types.INT, 1, 1);
                                break;
                            }
                            case 3: {
                                wrapper.set(Types.INT, 1, 7);
                                break;
                            }
                            case 4: {
                                wrapper.set(Types.INT, 1, 3);
                                break;
                            }
                            case 5: {
                                wrapper.set(Types.INT, 1, 5);
                            }
                        }
                    }
                });
            }
        });
    }
}

