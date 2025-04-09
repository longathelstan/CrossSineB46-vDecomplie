/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_19to1_18_2.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.api.rewriters.BackwardsItemRewriter;
import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viabackwards.protocol.v1_19to1_18_2.Protocol1_19To1_18_2;
import com.viaversion.viabackwards.protocol.v1_19to1_18_2.storage.LastDeathPosition;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.GlobalBlockPosition;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.packet.ClientboundPackets1_19;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.MathUtil;

public final class BlockItemPacketRewriter1_19
extends BackwardsItemRewriter<ClientboundPackets1_19, ServerboundPackets1_17, Protocol1_19To1_18_2> {
    final EnchantmentRewriter enchantmentRewriter = new EnchantmentRewriter(this);

    public BlockItemPacketRewriter1_19(Protocol1_19To1_18_2 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_ARRAY);
    }

    @Override
    protected void registerPackets() {
        BlockRewriter<ClientboundPackets1_19> blockRewriter = BlockRewriter.for1_14(this.protocol);
        new RecipeRewriter<ClientboundPackets1_19>(this.protocol).register(ClientboundPackets1_19.UPDATE_RECIPES);
        this.registerCooldown(ClientboundPackets1_19.COOLDOWN);
        this.registerSetContent1_17_1(ClientboundPackets1_19.CONTAINER_SET_CONTENT);
        this.registerSetSlot1_17_1(ClientboundPackets1_19.CONTAINER_SET_SLOT);
        this.registerSetEquipment(ClientboundPackets1_19.SET_EQUIPMENT);
        this.registerAdvancements(ClientboundPackets1_19.UPDATE_ADVANCEMENTS);
        this.registerContainerClick1_17_1(ServerboundPackets1_17.CONTAINER_CLICK);
        blockRewriter.registerBlockEvent(ClientboundPackets1_19.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_19.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate(ClientboundPackets1_19.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelEvent(ClientboundPackets1_19.LEVEL_EVENT, 1010, 2001);
        this.registerSetCreativeModeSlot(ServerboundPackets1_17.SET_CREATIVE_MODE_SLOT);
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_19.MERCHANT_OFFERS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int size = wrapper.read(Types.VAR_INT);
                    wrapper.write(Types.UNSIGNED_BYTE, (short)size);
                    for (int i = 0; i < size; ++i) {
                        BlockItemPacketRewriter1_19.this.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                        BlockItemPacketRewriter1_19.this.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                        Item secondItem = wrapper.read(Types.ITEM1_13_2);
                        if (secondItem != null) {
                            BlockItemPacketRewriter1_19.this.handleItemToClient(wrapper.user(), secondItem);
                            wrapper.write(Types.BOOLEAN, true);
                            wrapper.write(Types.ITEM1_13_2, secondItem);
                        } else {
                            wrapper.write(Types.BOOLEAN, false);
                        }
                        wrapper.passthrough(Types.BOOLEAN);
                        wrapper.passthrough(Types.INT);
                        wrapper.passthrough(Types.INT);
                        wrapper.passthrough(Types.INT);
                        wrapper.passthrough(Types.INT);
                        wrapper.passthrough(Types.FLOAT);
                        wrapper.passthrough(Types.INT);
                    }
                });
            }
        });
        this.registerContainerSetData(ClientboundPackets1_19.CONTAINER_SET_DATA);
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_19.BLOCK_CHANGED_ACK, null, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.read(Types.VAR_INT);
                this.handler(PacketWrapper::cancel);
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_19.LEVEL_PARTICLES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
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
                    int id = wrapper.get(Types.INT, 0);
                    if (id == (particleMappings = ((Protocol1_19To1_18_2)BlockItemPacketRewriter1_19.this.protocol).getMappingData().getParticleMappings()).id("sculk_charge")) {
                        wrapper.set(Types.INT, 0, -1);
                        wrapper.cancel();
                    } else if (id == particleMappings.id("shriek")) {
                        wrapper.set(Types.INT, 0, -1);
                        wrapper.cancel();
                    } else if (id == particleMappings.id("vibration")) {
                        wrapper.set(Types.INT, 0, -1);
                        wrapper.cancel();
                    }
                });
                this.handler(BlockItemPacketRewriter1_19.this.levelParticlesHandler());
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_19.LEVEL_CHUNK_WITH_LIGHT, wrapper -> {
            Object tracker = ((Protocol1_19To1_18_2)this.protocol).getEntityRewriter().tracker(wrapper.user());
            ChunkType1_18 chunkType = new ChunkType1_18(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_19To1_18_2)this.protocol).getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(tracker.biomesSent()));
            Chunk chunk = wrapper.passthrough(chunkType);
            for (ChunkSection section : chunk.getSections()) {
                DataPalette blockPalette = section.palette(PaletteType.BLOCKS);
                for (int i = 0; i < blockPalette.size(); ++i) {
                    int id = blockPalette.idByIndex(i);
                    blockPalette.setIdByIndex(i, ((Protocol1_19To1_18_2)this.protocol).getMappingData().getNewBlockStateId(id));
                }
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerServerbound(ServerboundPackets1_17.PLAYER_ACTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BLOCK_POSITION1_14);
                this.map(Types.UNSIGNED_BYTE);
                this.create(Types.VAR_INT, 0);
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerServerbound(ServerboundPackets1_17.USE_ITEM_ON, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BLOCK_POSITION1_14);
                this.map(Types.VAR_INT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.BOOLEAN);
                this.create(Types.VAR_INT, 0);
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerServerbound(ServerboundPackets1_17.USE_ITEM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.create(Types.VAR_INT, 0);
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerServerbound(ServerboundPackets1_17.SET_BEACON, wrapper -> {
            int primaryEffect = wrapper.read(Types.VAR_INT);
            if (primaryEffect != -1) {
                wrapper.write(Types.BOOLEAN, true);
                wrapper.write(Types.VAR_INT, primaryEffect);
            } else {
                wrapper.write(Types.BOOLEAN, false);
            }
            int secondaryEffect = wrapper.read(Types.VAR_INT);
            if (secondaryEffect != -1) {
                wrapper.write(Types.BOOLEAN, true);
                wrapper.write(Types.VAR_INT, secondaryEffect);
            } else {
                wrapper.write(Types.BOOLEAN, false);
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.enchantmentRewriter.registerEnchantment("minecraft:swift_sneak", "\u00a77Swift Sneak");
    }

    @Override
    public Item handleItemToClient(UserConnection connection, Item item) {
        if (item == null) {
            return null;
        }
        int identifier = item.identifier();
        super.handleItemToClient(connection, item);
        if (identifier != 834) {
            return item;
        }
        LastDeathPosition lastDeathPosition = connection.get(LastDeathPosition.class);
        if (lastDeathPosition == null) {
            return item;
        }
        GlobalBlockPosition position = lastDeathPosition.position();
        CompoundTag lodestonePosTag = new CompoundTag();
        item.tag().putBoolean(this.nbtTagName(), true);
        item.tag().put("LodestonePos", lodestonePosTag);
        item.tag().putString("LodestoneDimension", position.dimension());
        lodestonePosTag.putInt("X", position.x());
        lodestonePosTag.putInt("Y", position.y());
        lodestonePosTag.putInt("Z", position.z());
        this.enchantmentRewriter.handleToClient(item);
        return item;
    }

    @Override
    public Item handleItemToServer(UserConnection connection, Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToServer(connection, item);
        CompoundTag tag = item.tag();
        if (item.identifier() == 834 && tag != null) {
            if (tag.contains(this.nbtTagName())) {
                tag.remove(this.nbtTagName());
                tag.remove("LodestonePos");
                tag.remove("LodestoneDimension");
            }
            if (tag.isEmpty()) {
                item.setTag(null);
            }
        }
        this.enchantmentRewriter.handleToServer(item);
        return item;
    }
}

