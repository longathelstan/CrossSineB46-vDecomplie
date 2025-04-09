/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20_3to1_20_2.rewriter;

import com.viaversion.viabackwards.api.rewriters.BackwardsItemRewriter;
import com.viaversion.viabackwards.protocol.v1_20_3to1_20_2.Protocol1_20_3To1_20_2;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_20_2;
import com.viaversion.viaversion.api.type.types.version.Types1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundPacket1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.rewriter.RecipeRewriter1_20_3;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ServerboundPacket1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ServerboundPackets1_20_2;
import com.viaversion.viaversion.rewriter.BlockRewriter;

public final class BlockItemPacketRewriter1_20_3
extends BackwardsItemRewriter<ClientboundPacket1_20_3, ServerboundPacket1_20_2, Protocol1_20_3To1_20_2> {
    public BlockItemPacketRewriter1_20_3(Protocol1_20_3To1_20_2 protocol) {
        super(protocol, Types.ITEM1_20_2, Types.ITEM1_20_2_ARRAY);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_20_3> blockRewriter = BlockRewriter.for1_20_2(this.protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_20_3.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_20_3.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate1_20(ClientboundPackets1_20_3.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelEvent(ClientboundPackets1_20_3.LEVEL_EVENT, 1010, 2001);
        blockRewriter.registerLevelChunk1_19(ClientboundPackets1_20_3.LEVEL_CHUNK_WITH_LIGHT, ChunkType1_20_2::new);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_20_3.BLOCK_ENTITY_DATA);
        this.registerCooldown(ClientboundPackets1_20_3.COOLDOWN);
        this.registerSetContent1_17_1(ClientboundPackets1_20_3.CONTAINER_SET_CONTENT);
        this.registerSetSlot1_17_1(ClientboundPackets1_20_3.CONTAINER_SET_SLOT);
        this.registerSetEquipment(ClientboundPackets1_20_3.SET_EQUIPMENT);
        this.registerContainerClick1_17_1(ServerboundPackets1_20_2.CONTAINER_CLICK);
        this.registerMerchantOffers1_19(ClientboundPackets1_20_3.MERCHANT_OFFERS);
        this.registerSetCreativeModeSlot(ServerboundPackets1_20_2.SET_CREATIVE_MODE_SLOT);
        this.registerContainerSetData(ClientboundPackets1_20_3.CONTAINER_SET_DATA);
        ((Protocol1_20_3To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_20_3.LEVEL_PARTICLES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BOOLEAN);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    ParticleMappings particleMappings;
                    int id = wrapper.get(Types.VAR_INT, 0);
                    if (id == (particleMappings = ((Protocol1_20_3To1_20_2)BlockItemPacketRewriter1_20_3.this.protocol).getMappingData().getParticleMappings()).id("vibration")) {
                        int positionSourceType = wrapper.read(Types.VAR_INT);
                        if (positionSourceType == 0) {
                            wrapper.write(Types.STRING, "minecraft:block");
                        } else if (positionSourceType == 1) {
                            wrapper.write(Types.STRING, "minecraft:entity");
                        } else {
                            int n = positionSourceType;
                            ((Protocol1_20_3To1_20_2)BlockItemPacketRewriter1_20_3.this.protocol).getLogger().warning("Unknown position source type: " + n);
                            wrapper.cancel();
                        }
                    }
                });
                this.handler(BlockItemPacketRewriter1_20_3.this.levelParticlesHandler(Types.VAR_INT));
            }
        });
        new RecipeRewriter1_20_3<ClientboundPacket1_20_3>(this.protocol){

            @Override
            public void handleCraftingShaped(PacketWrapper wrapper) {
                String group = wrapper.read(Types.STRING);
                int craftingBookCategory = wrapper.read(Types.VAR_INT);
                int width = wrapper.passthrough(Types.VAR_INT);
                int height = wrapper.passthrough(Types.VAR_INT);
                wrapper.write(Types.STRING, group);
                wrapper.write(Types.VAR_INT, craftingBookCategory);
                int ingredients = height * width;
                for (int i = 0; i < ingredients; ++i) {
                    this.handleIngredient(wrapper);
                }
                this.rewrite(wrapper.user(), wrapper.passthrough(this.itemType()));
                wrapper.passthrough(Types.BOOLEAN);
            }
        }.register(ClientboundPackets1_20_3.UPDATE_RECIPES);
        ((Protocol1_20_3To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_20_3.EXPLODE, wrapper -> {
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.FLOAT);
            int blocks = wrapper.read(Types.VAR_INT);
            byte[][] toBlow = new byte[blocks][3];
            for (int i = 0; i < blocks; ++i) {
                toBlow[i] = new byte[]{wrapper.read(Types.BYTE), wrapper.read(Types.BYTE), wrapper.read(Types.BYTE)};
            }
            float knockbackX = wrapper.read(Types.FLOAT).floatValue();
            float knockbackY = wrapper.read(Types.FLOAT).floatValue();
            float knockbackZ = wrapper.read(Types.FLOAT).floatValue();
            int blockInteraction = wrapper.read(Types.VAR_INT);
            if (blockInteraction == 1 || blockInteraction == 2) {
                wrapper.write(Types.VAR_INT, blocks);
                for (byte[] relativeXYZ : toBlow) {
                    wrapper.write(Types.BYTE, relativeXYZ[0]);
                    wrapper.write(Types.BYTE, relativeXYZ[1]);
                    wrapper.write(Types.BYTE, relativeXYZ[2]);
                }
            } else {
                wrapper.write(Types.VAR_INT, 0);
            }
            wrapper.write(Types.FLOAT, Float.valueOf(knockbackX));
            wrapper.write(Types.FLOAT, Float.valueOf(knockbackY));
            wrapper.write(Types.FLOAT, Float.valueOf(knockbackZ));
            wrapper.read(Types1_20_3.PARTICLE);
            wrapper.read(Types1_20_3.PARTICLE);
            wrapper.read(Types.STRING);
            wrapper.read(Types.OPTIONAL_FLOAT);
        });
    }
}

