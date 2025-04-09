/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_13to1_13_1.rewriter;

import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_13to1_13_1.Protocol1_13To1_13_1;
import com.viaversion.viaversion.rewriter.BlockRewriter;

public class WorldPacketRewriter1_13_1 {
    public static void register(final Protocol1_13To1_13_1 protocol) {
        BlockRewriter<ClientboundPackets1_13> blockRewriter = BlockRewriter.legacy(protocol);
        protocol.registerClientbound(ClientboundPackets1_13.LEVEL_CHUNK, wrapper -> {
            Object clientWorld = wrapper.user().getClientWorld(Protocol1_13To1_13_1.class);
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
                    if (id == 2000) {
                        int data = wrapper.get(Types.INT, 1);
                        switch (data) {
                            case 1: {
                                wrapper.set(Types.INT, 1, 2);
                                break;
                            }
                            case 0: 
                            case 3: 
                            case 6: {
                                wrapper.set(Types.INT, 1, 4);
                                break;
                            }
                            case 2: 
                            case 5: 
                            case 8: {
                                wrapper.set(Types.INT, 1, 5);
                                break;
                            }
                            case 7: {
                                wrapper.set(Types.INT, 1, 3);
                                break;
                            }
                            default: {
                                wrapper.set(Types.INT, 1, 0);
                                break;
                            }
                        }
                    } else if (id == 1010) {
                        wrapper.set(Types.INT, 1, protocol.getMappingData().getNewItemId(wrapper.get(Types.INT, 1)));
                    } else if (id == 2001) {
                        wrapper.set(Types.INT, 1, protocol.getMappingData().getNewBlockStateId(wrapper.get(Types.INT, 1)));
                    }
                });
            }
        });
    }
}

