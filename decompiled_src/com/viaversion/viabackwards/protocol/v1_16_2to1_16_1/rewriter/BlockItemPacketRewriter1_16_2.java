/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.viabackwards.api.rewriters.BackwardsItemRewriter;
import com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16_2;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BlockItemPacketRewriter1_16_2
extends BackwardsItemRewriter<ClientboundPackets1_16_2, ServerboundPackets1_16, Protocol1_16_2To1_16_1> {
    public BlockItemPacketRewriter1_16_2(Protocol1_16_2To1_16_1 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_SHORT_ARRAY);
    }

    @Override
    protected void registerPackets() {
        BlockRewriter<ClientboundPackets1_16_2> blockRewriter = BlockRewriter.for1_14(this.protocol);
        new RecipeRewriter<ClientboundPackets1_16_2>(this.protocol).register(ClientboundPackets1_16_2.UPDATE_RECIPES);
        this.registerCooldown(ClientboundPackets1_16_2.COOLDOWN);
        this.registerSetContent(ClientboundPackets1_16_2.CONTAINER_SET_CONTENT);
        this.registerSetSlot(ClientboundPackets1_16_2.CONTAINER_SET_SLOT);
        this.registerSetEquipment(ClientboundPackets1_16_2.SET_EQUIPMENT);
        this.registerMerchantOffers(ClientboundPackets1_16_2.MERCHANT_OFFERS);
        this.registerAdvancements(ClientboundPackets1_16_2.UPDATE_ADVANCEMENTS);
        ((Protocol1_16_2To1_16_1)this.protocol).registerClientbound(ClientboundPackets1_16_2.RECIPE, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.BOOLEAN);
            wrapper.passthrough(Types.BOOLEAN);
            wrapper.passthrough(Types.BOOLEAN);
            wrapper.passthrough(Types.BOOLEAN);
            wrapper.read(Types.BOOLEAN);
            wrapper.read(Types.BOOLEAN);
            wrapper.read(Types.BOOLEAN);
            wrapper.read(Types.BOOLEAN);
        });
        blockRewriter.registerBlockBreakAck(ClientboundPackets1_16_2.BLOCK_BREAK_ACK);
        blockRewriter.registerBlockEvent(ClientboundPackets1_16_2.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_16_2.BLOCK_UPDATE);
        blockRewriter.registerLevelChunk(ClientboundPackets1_16_2.LEVEL_CHUNK, ChunkType1_16_2.TYPE, ChunkType1_16.TYPE, (connection, chunk) -> {
            chunk.setIgnoreOldLightData(true);
            for (CompoundTag blockEntity : chunk.getBlockEntities()) {
                if (blockEntity == null) continue;
                this.handleBlockEntity(blockEntity);
            }
        });
        ((Protocol1_16_2To1_16_1)this.protocol).registerClientbound(ClientboundPackets1_16_2.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_14);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> BlockItemPacketRewriter1_16_2.this.handleBlockEntity(wrapper.passthrough(Types.NAMED_COMPOUND_TAG)));
            }
        });
        ((Protocol1_16_2To1_16_1)this.protocol).registerClientbound(ClientboundPackets1_16_2.SECTION_BLOCKS_UPDATE, ClientboundPackets1_16.CHUNK_BLOCKS_UPDATE, wrapper -> {
            long chunkPosition = wrapper.read(Types.LONG);
            wrapper.read(Types.BOOLEAN);
            int chunkX = (int)(chunkPosition >> 42);
            int chunkY = (int)(chunkPosition << 44 >> 44);
            int chunkZ = (int)(chunkPosition << 22 >> 42);
            wrapper.write(Types.INT, chunkX);
            wrapper.write(Types.INT, chunkZ);
            BlockChangeRecord[] blockChangeRecord = wrapper.read(Types.VAR_LONG_BLOCK_CHANGE_ARRAY);
            wrapper.write(Types.BLOCK_CHANGE_ARRAY, blockChangeRecord);
            for (int i = 0; i < blockChangeRecord.length; ++i) {
                BlockChangeRecord record = blockChangeRecord[i];
                int blockId = ((Protocol1_16_2To1_16_1)this.protocol).getMappingData().getNewBlockStateId(record.getBlockId());
                blockChangeRecord[i] = new BlockChangeRecord1_8(record.getSectionX(), record.getY(chunkY), record.getSectionZ(), blockId);
            }
        });
        blockRewriter.registerLevelEvent(ClientboundPackets1_16_2.LEVEL_EVENT, 1010, 2001);
        this.registerLevelParticles(ClientboundPackets1_16_2.LEVEL_PARTICLES, Types.DOUBLE);
        this.registerContainerClick(ServerboundPackets1_16.CONTAINER_CLICK);
        this.registerSetCreativeModeSlot(ServerboundPackets1_16.SET_CREATIVE_MODE_SLOT);
        ((Protocol1_16_2To1_16_1)this.protocol).registerServerbound(ServerboundPackets1_16.EDIT_BOOK, wrapper -> this.handleItemToServer(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2)));
    }

    @Override
    public @Nullable Item handleItemToClient(UserConnection connection, @Nullable Item item) {
        if (item != null && item.tag() != null) {
            this.addValueHashAsId(item.tag());
        }
        return super.handleItemToClient(connection, item);
    }

    void handleBlockEntity(CompoundTag tag) {
        String id = tag.getString("id");
        if (id != null && Key.stripMinecraftNamespace(id).equals("skull")) {
            this.addValueHashAsId(tag);
        }
    }

    void addValueHashAsId(CompoundTag tag) {
        CompoundTag first;
        CompoundTag skullOwnerTag = tag.getCompoundTag("SkullOwner");
        if (skullOwnerTag == null) {
            return;
        }
        if (!skullOwnerTag.contains("Id")) {
            return;
        }
        CompoundTag properties = skullOwnerTag.getCompoundTag("Properties");
        if (properties == null) {
            return;
        }
        ListTag<CompoundTag> textures = properties.getListTag("textures", CompoundTag.class);
        if (textures == null) {
            return;
        }
        CompoundTag compoundTag = first = !textures.isEmpty() ? textures.get(0) : null;
        if (first == null) {
            return;
        }
        int hashCode = first.get("Value").getValue().hashCode();
        int[] uuidIntArray = new int[]{hashCode, 0, 0, 0};
        skullOwnerTag.put("Id", new IntArrayTag(uuidIntArray));
    }
}

