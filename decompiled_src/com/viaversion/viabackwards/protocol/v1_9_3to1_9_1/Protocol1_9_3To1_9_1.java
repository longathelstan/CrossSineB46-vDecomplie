/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_9_3to1_9_1;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_9_3to1_9_1.data.BlockEntity1_9_1;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_1;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ServerboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.ComponentRewriter;

public class Protocol1_9_3To1_9_1
extends BackwardsProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9, ServerboundPackets1_9_3, ServerboundPackets1_9> {
    public Protocol1_9_3To1_9_1() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.handler(wrapper -> {
                    if (wrapper.get(Types.UNSIGNED_BYTE, 0) == 9) {
                        BlockPosition position = wrapper.get(Types.BLOCK_POSITION1_8, 0);
                        CompoundTag tag = wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
                        wrapper.clearPacket();
                        wrapper.setPacketType(ClientboundPackets1_9.UPDATE_SIGN);
                        wrapper.write(Types.BLOCK_POSITION1_8, position);
                        for (int i = 0; i < 4; ++i) {
                            int n = i + 1;
                            StringTag textTag = tag.getStringTag("Text" + n);
                            String line = textTag != null ? textTag.getValue() : "";
                            wrapper.write(Types.STRING, line);
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.LEVEL_CHUNK, wrapper -> {
            Object clientWorld = wrapper.user().getClientWorld(Protocol1_9_3To1_9_1.class);
            ChunkType1_9_3 newType = ChunkType1_9_3.forEnvironment(((ClientWorld)clientWorld).getEnvironment());
            ChunkType1_9_1 oldType = ChunkType1_9_1.forEnvironment(((ClientWorld)clientWorld).getEnvironment());
            Chunk chunk = wrapper.read(newType);
            wrapper.write(oldType, chunk);
            BlockEntity1_9_1.handle(chunk.getBlockEntities(), wrapper.user());
        });
        this.registerClientbound(ClientboundPackets1_9_3.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    Object clientWorld = wrapper.user().getClientWorld(Protocol1_9_3To1_9_1.class);
                    int dimensionId = wrapper.get(Types.INT, 1);
                    ((ClientWorld)clientWorld).setEnvironment(dimensionId);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.handler(wrapper -> {
                    Object clientWorld = wrapper.user().getClientWorld(Protocol1_9_3To1_9_1.class);
                    int dimensionId = wrapper.get(Types.INT, 0);
                    ((ClientWorld)clientWorld).setEnvironment(dimensionId);
                });
            }
        });
        TranslatableRewriter<ClientboundPackets1_9_3> componentRewriter = new TranslatableRewriter<ClientboundPackets1_9_3>(this, ComponentRewriter.ReadType.JSON);
        componentRewriter.registerComponentPacket(ClientboundPackets1_9_3.CHAT);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addClientWorld(this.getClass(), new ClientWorld());
    }
}

