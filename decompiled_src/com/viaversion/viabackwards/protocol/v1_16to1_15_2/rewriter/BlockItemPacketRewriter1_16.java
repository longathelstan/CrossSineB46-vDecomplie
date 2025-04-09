/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_16to1_15_2.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.LongArrayTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.rewriters.BackwardsItemRewriter;
import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viabackwards.api.rewriters.MapColorRewriter;
import com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.storage.BiomeStorage;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.data.MapColorMappings1_15_2;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_15;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.rewriter.ItemPacketRewriter1_16;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.UUIDUtil;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class BlockItemPacketRewriter1_16
extends BackwardsItemRewriter<ClientboundPackets1_16, ServerboundPackets1_14, Protocol1_16To1_15_2> {
    EnchantmentRewriter enchantmentRewriter;

    public BlockItemPacketRewriter1_16(Protocol1_16To1_15_2 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_SHORT_ARRAY);
    }

    @Override
    protected void registerPackets() {
        BlockRewriter<ClientboundPackets1_16> blockRewriter = BlockRewriter.for1_14(this.protocol);
        RecipeRewriter recipeRewriter = new RecipeRewriter(this.protocol);
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.UPDATE_RECIPES, wrapper -> {
            int size;
            int newSize = size = wrapper.passthrough(Types.VAR_INT).intValue();
            for (int i = 0; i < size; ++i) {
                String originalType = wrapper.read(Types.STRING);
                String type = Key.stripMinecraftNamespace(originalType);
                if (type.equals("smithing")) {
                    --newSize;
                    wrapper.read(Types.STRING);
                    wrapper.read(Types.ITEM1_13_2_ARRAY);
                    wrapper.read(Types.ITEM1_13_2_ARRAY);
                    wrapper.read(Types.ITEM1_13_2);
                    continue;
                }
                wrapper.write(Types.STRING, originalType);
                wrapper.passthrough(Types.STRING);
                recipeRewriter.handleRecipeType(wrapper, type);
            }
            wrapper.set(Types.VAR_INT, 0, newSize);
        });
        this.registerCooldown(ClientboundPackets1_16.COOLDOWN);
        this.registerSetContent(ClientboundPackets1_16.CONTAINER_SET_CONTENT);
        this.registerSetSlot(ClientboundPackets1_16.CONTAINER_SET_SLOT);
        this.registerMerchantOffers(ClientboundPackets1_16.MERCHANT_OFFERS);
        this.registerAdvancements(ClientboundPackets1_16.UPDATE_ADVANCEMENTS);
        blockRewriter.registerBlockBreakAck(ClientboundPackets1_16.BLOCK_BREAK_ACK);
        blockRewriter.registerBlockEvent(ClientboundPackets1_16.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_16.BLOCK_UPDATE);
        blockRewriter.registerChunkBlocksUpdate(ClientboundPackets1_16.CHUNK_BLOCKS_UPDATE);
        blockRewriter.registerLevelChunk(ClientboundPackets1_16.LEVEL_CHUNK, ChunkType1_16.TYPE, ChunkType1_15.TYPE, (connection, chunk) -> {
            CompoundTag heightMaps = chunk.getHeightMap();
            for (Tag heightMapTag : heightMaps.values()) {
                if (!(heightMapTag instanceof LongArrayTag)) continue;
                LongArrayTag heightMap = (LongArrayTag)heightMapTag;
                int[] heightMapData = new int[256];
                CompactArrayUtil.iterateCompactArrayWithPadding(9, heightMapData.length, heightMap.getValue(), (i, v) -> {
                    heightMapData[i] = v;
                });
                heightMap.setValue(CompactArrayUtil.createCompactArray(9, heightMapData.length, i -> heightMapData[i]));
            }
            if (chunk.isBiomeData()) {
                if (connection.getProtocolInfo().serverProtocolVersion().newerThanOrEqualTo(ProtocolVersion.v1_16_2)) {
                    BiomeStorage biomeStorage = connection.get(BiomeStorage.class);
                    for (int i2 = 0; i2 < 1024; ++i2) {
                        int biome = chunk.getBiomeData()[i2];
                        int legacyBiome = biomeStorage.legacyBiome(biome);
                        if (legacyBiome == -1) {
                            int n = biome;
                            ((Protocol1_16To1_15_2)this.protocol).getLogger().warning("Biome sent that does not exist in the biome registry: " + n);
                            legacyBiome = 1;
                        }
                        chunk.getBiomeData()[i2] = legacyBiome;
                    }
                } else {
                    for (int i3 = 0; i3 < 1024; ++i3) {
                        int biome = chunk.getBiomeData()[i3];
                        switch (biome) {
                            case 170: 
                            case 171: 
                            case 172: 
                            case 173: {
                                chunk.getBiomeData()[i3] = 8;
                            }
                        }
                    }
                }
            }
            if (chunk.getBlockEntities() == null) {
                return;
            }
            for (CompoundTag blockEntity : chunk.getBlockEntities()) {
                this.handleBlockEntity(blockEntity);
            }
        });
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.SET_EQUIPMENT, ClientboundPackets1_15.SET_EQUIPPED_ITEM, wrapper -> {
            byte slot;
            int entityId = wrapper.passthrough(Types.VAR_INT);
            ArrayList<EquipmentData> equipmentData = new ArrayList<EquipmentData>();
            do {
                slot = wrapper.read(Types.BYTE);
                Item item = this.handleItemToClient(wrapper.user(), wrapper.read(Types.ITEM1_13_2));
                int rawSlot = slot & 0x7F;
                equipmentData.add(new EquipmentData(rawSlot, item));
            } while ((slot & 0xFFFFFF80) != 0);
            EquipmentData firstData = (EquipmentData)equipmentData.get(0);
            wrapper.write(Types.VAR_INT, firstData.slot);
            wrapper.write(Types.ITEM1_13_2, firstData.item);
            for (int i = 1; i < equipmentData.size(); ++i) {
                PacketWrapper equipmentPacket = wrapper.create(ClientboundPackets1_15.SET_EQUIPPED_ITEM);
                EquipmentData data = (EquipmentData)equipmentData.get(i);
                equipmentPacket.write(Types.VAR_INT, entityId);
                equipmentPacket.write(Types.VAR_INT, data.slot);
                equipmentPacket.write(Types.ITEM1_13_2, data.item);
                equipmentPacket.send(Protocol1_16To1_15_2.class);
            }
        });
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.LIGHT_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.read(Types.BOOLEAN);
            }
        });
        blockRewriter.registerLevelEvent(ClientboundPackets1_16.LEVEL_EVENT, 1010, 2001);
        this.registerLevelParticles(ClientboundPackets1_16.LEVEL_PARTICLES, Types.DOUBLE);
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.CONTAINER_SET_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.handler(wrapper -> {
                    short property = wrapper.get(Types.SHORT, 0);
                    if (property >= 4 && property <= 6) {
                        short enchantmentId = wrapper.get(Types.SHORT, 1);
                        if (enchantmentId > 11) {
                            enchantmentId = (short)(enchantmentId - 1);
                            wrapper.set(Types.SHORT, 1, enchantmentId);
                        } else if (enchantmentId == 11) {
                            wrapper.set(Types.SHORT, 1, (short)9);
                        }
                    }
                });
            }
        });
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.MAP_ITEM_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BYTE);
                this.map(Types.BOOLEAN);
                this.map(Types.BOOLEAN);
                this.handler(MapColorRewriter.getRewriteHandler(MapColorMappings1_15_2::getMappedColor));
            }
        });
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.BLOCK_ENTITY_DATA, wrapper -> {
            wrapper.passthrough(Types.BLOCK_POSITION1_14);
            wrapper.passthrough(Types.UNSIGNED_BYTE);
            CompoundTag tag = wrapper.passthrough(Types.NAMED_COMPOUND_TAG);
            this.handleBlockEntity(tag);
        });
        this.registerContainerClick(ServerboundPackets1_14.CONTAINER_CLICK);
        this.registerSetCreativeModeSlot(ServerboundPackets1_14.SET_CREATIVE_MODE_SLOT);
        ((Protocol1_16To1_15_2)this.protocol).registerServerbound(ServerboundPackets1_14.EDIT_BOOK, wrapper -> this.handleItemToServer(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2)));
    }

    void handleBlockEntity(CompoundTag tag) {
        String id = tag.getString("id");
        if (id == null) {
            return;
        }
        if ((id = Key.namespaced(id)).equals("minecraft:conduit")) {
            Tag targetUuidTag = tag.remove("Target");
            if (!(targetUuidTag instanceof IntArrayTag)) {
                return;
            }
            UUID targetUuid = UUIDUtil.fromIntArray((int[])targetUuidTag.getValue());
            tag.putString("target_uuid", targetUuid.toString());
        } else if (id.equals("minecraft:skull")) {
            Tag targetUuid = tag.remove("SkullOwner");
            if (!(targetUuid instanceof CompoundTag)) {
                return;
            }
            CompoundTag skullOwnerTag = (CompoundTag)targetUuid;
            Tag tag2 = skullOwnerTag.remove("Id");
            if (tag2 instanceof IntArrayTag) {
                IntArrayTag ownerUuidTag = (IntArrayTag)tag2;
                UUID ownerUuid = UUIDUtil.fromIntArray(ownerUuidTag.getValue());
                skullOwnerTag.putString("Id", ownerUuid.toString());
            }
            CompoundTag ownerTag = new CompoundTag();
            for (Map.Entry entry : skullOwnerTag) {
                ownerTag.put((String)entry.getKey(), (Tag)entry.getValue());
            }
            tag.put("Owner", ownerTag);
        }
    }

    @Override
    protected void registerRewrites() {
        this.enchantmentRewriter = new EnchantmentRewriter(this);
        this.enchantmentRewriter.registerEnchantment("minecraft:soul_speed", "\u00a77Soul Speed");
    }

    @Override
    public Item handleItemToClient(UserConnection connection, Item item) {
        ListTag<StringTag> pagesTag;
        IntArrayTag idTag;
        CompoundTag ownerTag;
        if (item == null) {
            return null;
        }
        super.handleItemToClient(connection, item);
        CompoundTag tag = item.tag();
        if (item.identifier() == 771 && tag != null && (ownerTag = tag.getCompoundTag("SkullOwner")) != null && (idTag = ownerTag.getIntArrayTag("Id")) != null) {
            UUID ownerUuid = UUIDUtil.fromIntArray(idTag.getValue());
            ownerTag.putString("Id", ownerUuid.toString());
        }
        if (item.identifier() == 759 && tag != null && (pagesTag = tag.getListTag("pages", StringTag.class)) != null) {
            for (StringTag page : pagesTag) {
                JsonElement jsonElement = ((Protocol1_16To1_15_2)this.protocol).getComponentRewriter().processText(connection, page.getValue());
                page.setValue(jsonElement.toString());
            }
        }
        ItemPacketRewriter1_16.newToOldAttributes(item);
        this.enchantmentRewriter.handleToClient(item);
        return item;
    }

    @Override
    public Item handleItemToServer(UserConnection connection, Item item) {
        StringTag idTag;
        CompoundTag ownerTag;
        if (item == null) {
            return null;
        }
        int identifier = item.identifier();
        super.handleItemToServer(connection, item);
        CompoundTag tag = item.tag();
        if (identifier == 771 && tag != null && (ownerTag = tag.getCompoundTag("SkullOwner")) != null && (idTag = ownerTag.getStringTag("Id")) != null) {
            UUID ownerUuid = UUID.fromString(idTag.getValue());
            ownerTag.put("Id", new IntArrayTag(UUIDUtil.toIntArray(ownerUuid)));
        }
        ItemPacketRewriter1_16.oldToNewAttributes(item);
        this.enchantmentRewriter.handleToServer(item);
        return item;
    }

    private static final class EquipmentData {
        final int slot;
        final Item item;

        EquipmentData(int slot, Item item) {
            this.slot = slot;
            this.item = item;
        }

        public int slot() {
            return this.slot;
        }

        public Item item() {
            return this.item;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof EquipmentData)) {
                return false;
            }
            EquipmentData equipmentData = (EquipmentData)object;
            return this.slot == equipmentData.slot && Objects.equals(this.item, equipmentData.item);
        }

        public int hashCode() {
            return (0 * 31 + Integer.hashCode(this.slot)) * 31 + Objects.hashCode(this.item);
        }

        public String toString() {
            return String.format("%s[slot=%s, item=%s]", this.getClass().getSimpleName(), Integer.toString(this.slot), Objects.toString(this.item));
        }
    }
}

