/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_2to1_20_3.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_20_2;
import com.viaversion.viaversion.api.type.types.version.Types1_20_3;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.Protocol1_20_2To1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ServerboundPacket1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ServerboundPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundPacket1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundPackets1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.rewriter.RecipeRewriter1_20_2;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.SerializerVersion;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BlockItemPacketRewriter1_20_3
extends ItemRewriter<ClientboundPacket1_20_2, ServerboundPacket1_20_3, Protocol1_20_2To1_20_3> {
    public BlockItemPacketRewriter1_20_3(Protocol1_20_2To1_20_3 protocol) {
        super(protocol, Types.ITEM1_20_2, Types.ITEM1_20_2_ARRAY);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_20_2> blockRewriter = BlockRewriter.for1_20_2(this.protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_20_2.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_20_2.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate1_20(ClientboundPackets1_20_2.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelEvent(ClientboundPackets1_20_2.LEVEL_EVENT, 1010, 2001);
        blockRewriter.registerLevelChunk1_19(ClientboundPackets1_20_2.LEVEL_CHUNK_WITH_LIGHT, ChunkType1_20_2::new);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_20_2.BLOCK_ENTITY_DATA);
        this.registerCooldown(ClientboundPackets1_20_2.COOLDOWN);
        this.registerSetContent1_17_1(ClientboundPackets1_20_2.CONTAINER_SET_CONTENT);
        this.registerSetSlot1_17_1(ClientboundPackets1_20_2.CONTAINER_SET_SLOT);
        this.registerSetEquipment(ClientboundPackets1_20_2.SET_EQUIPMENT);
        this.registerContainerClick1_17_1(ServerboundPackets1_20_3.CONTAINER_CLICK);
        this.registerMerchantOffers1_19(ClientboundPackets1_20_2.MERCHANT_OFFERS);
        this.registerSetCreativeModeSlot(ServerboundPackets1_20_3.SET_CREATIVE_MODE_SLOT);
        this.registerContainerSetData(ClientboundPackets1_20_2.CONTAINER_SET_DATA);
        ((Protocol1_20_2To1_20_3)this.protocol).registerClientbound(ClientboundPackets1_20_2.LEVEL_PARTICLES, new PacketHandlers(){

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
                    if (id == (particleMappings = ((Protocol1_20_2To1_20_3)BlockItemPacketRewriter1_20_3.this.protocol).getMappingData().getParticleMappings()).id("vibration")) {
                        String resourceLocation = Key.stripMinecraftNamespace(wrapper.read(Types.STRING));
                        wrapper.write(Types.VAR_INT, resourceLocation.equals("block") ? 0 : 1);
                    }
                });
                this.handler(BlockItemPacketRewriter1_20_3.this.levelParticlesHandler(Types.VAR_INT));
            }
        });
        new RecipeRewriter1_20_2<ClientboundPacket1_20_2>(this.protocol){

            @Override
            public void handleCraftingShaped(PacketWrapper wrapper) {
                int width = wrapper.read(Types.VAR_INT);
                int height = wrapper.read(Types.VAR_INT);
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.VAR_INT);
                wrapper.write(Types.VAR_INT, width);
                wrapper.write(Types.VAR_INT, height);
                int ingredients = height * width;
                for (int i = 0; i < ingredients; ++i) {
                    this.handleIngredient(wrapper);
                }
                this.rewrite(wrapper.user(), wrapper.passthrough(this.itemType()));
                wrapper.passthrough(Types.BOOLEAN);
            }
        }.register(ClientboundPackets1_20_2.UPDATE_RECIPES);
        ((Protocol1_20_2To1_20_3)this.protocol).registerClientbound(ClientboundPackets1_20_2.EXPLODE, wrapper -> {
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.FLOAT);
            int blocks = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < blocks; ++i) {
                wrapper.passthrough(Types.BYTE);
                wrapper.passthrough(Types.BYTE);
                wrapper.passthrough(Types.BYTE);
            }
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.FLOAT);
            wrapper.write(Types.VAR_INT, 1);
            wrapper.write(Types1_20_3.PARTICLE, new Particle(((Protocol1_20_2To1_20_3)this.protocol).getMappingData().getParticleMappings().mappedId("explosion")));
            wrapper.write(Types1_20_3.PARTICLE, new Particle(((Protocol1_20_2To1_20_3)this.protocol).getMappingData().getParticleMappings().mappedId("explosion_emitter")));
            wrapper.write(Types.STRING, "minecraft:entity.generic.explode");
            wrapper.write(Types.OPTIONAL_FLOAT, null);
        });
    }

    @Override
    public @Nullable Item handleItemToClient(UserConnection connection, @Nullable Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.tag();
        if (tag != null && item.identifier() == 1047) {
            CompoundTag filteredPages;
            ListTag<StringTag> pages = tag.getListTag("pages", StringTag.class);
            if (pages != null) {
                for (StringTag pageTag : pages) {
                    this.updatePageTag(pageTag);
                }
            }
            if ((filteredPages = tag.getCompoundTag("filtered_pages")) != null) {
                for (String string : filteredPages.keySet()) {
                    this.updatePageTag(filteredPages.getStringTag(string));
                }
            }
        }
        return super.handleItemToClient(connection, item);
    }

    void updatePageTag(StringTag pageTag) {
        block2: {
            try {
                JsonElement updatedComponent = ComponentUtil.convertJson(pageTag.getValue(), SerializerVersion.V1_19_4, SerializerVersion.V1_20_3);
                pageTag.setValue(updatedComponent.toString());
            }
            catch (Exception e) {
                if (Via.getConfig().isSuppressConversionWarnings()) break block2;
                String string = pageTag.getValue();
                ((Protocol1_20_2To1_20_3)this.protocol).getLogger().log(Level.SEVERE, "Error during book conversion: " + string, e);
            }
        }
    }
}

