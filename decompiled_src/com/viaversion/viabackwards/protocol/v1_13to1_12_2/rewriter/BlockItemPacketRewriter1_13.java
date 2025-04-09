/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13to1_12_2.rewriter;

import com.google.common.primitives.Ints;
import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.BackwardsItemRewriter;
import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers.FlowerPotHandler;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.provider.BackwardsBlockEntityProvider;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage.BackwardsBlockStorage;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage.NoteBlockStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_13;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.BlockIdData;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.SpawnEggMappings1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ServerboundPackets1_12_1;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.IdAndData;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class BlockItemPacketRewriter1_13
extends BackwardsItemRewriter<ClientboundPackets1_13, ServerboundPackets1_12_1, Protocol1_13To1_12_2> {
    final Map<String, String> enchantmentMappings = new HashMap<String, String>();
    final String extraNbtTag = this.nbtTagName("2");

    public BlockItemPacketRewriter1_13(Protocol1_13To1_12_2 protocol) {
        super(protocol, Types.ITEM1_13, Types.ITEM1_13_SHORT_ARRAY, Types.ITEM1_8, Types.ITEM1_8_SHORT_ARRAY);
    }

    public static boolean isDamageable(int id) {
        return id >= 256 && id <= 259 || id == 261 || id >= 267 && id <= 279 || id >= 283 && id <= 286 || id >= 290 && id <= 294 || id >= 298 && id <= 317 || id == 346 || id == 359 || id == 398 || id == 442 || id == 443;
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.COOLDOWN, wrapper -> {
            int itemId = wrapper.read(Types.VAR_INT);
            int oldId = ((Protocol1_13To1_12_2)this.protocol).getMappingData().getItemMappings().getNewId(itemId);
            if (oldId == -1) {
                wrapper.cancel();
                return;
            }
            if (SpawnEggMappings1_13.getEntityId(oldId).isPresent()) {
                wrapper.write(Types.VAR_INT, IdAndData.toRawData(383));
                return;
            }
            wrapper.write(Types.VAR_INT, IdAndData.getId(oldId));
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.BLOCK_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    BlockPosition position;
                    NoteBlockStorage noteBlockStorage;
                    Pair<Integer, Integer> update;
                    int blockId = wrapper.get(Types.VAR_INT, 0);
                    if (blockId == 73) {
                        blockId = 25;
                    } else if (blockId == 99) {
                        blockId = 33;
                    } else if (blockId == 92) {
                        blockId = 29;
                    } else if (blockId == 142) {
                        blockId = 54;
                    } else if (blockId == 305) {
                        blockId = 146;
                    } else if (blockId == 249) {
                        blockId = 130;
                    } else if (blockId == 257) {
                        blockId = 138;
                    } else if (blockId == 140) {
                        blockId = 52;
                    } else if (blockId == 472) {
                        blockId = 209;
                    } else if (blockId >= 483 && blockId <= 498) {
                        blockId = blockId - 483 + 219;
                    }
                    if (blockId == 25 && (update = (noteBlockStorage = wrapper.user().get(NoteBlockStorage.class)).getNoteBlockUpdate(position = wrapper.get(Types.BLOCK_POSITION1_8, 0))) != null) {
                        wrapper.set(Types.UNSIGNED_BYTE, 0, update.key().shortValue());
                        wrapper.set(Types.UNSIGNED_BYTE, 1, update.value().shortValue());
                    }
                    wrapper.set(Types.VAR_INT, 0, blockId);
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.handler(wrapper -> {
                    BackwardsBlockEntityProvider provider = Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
                    if (wrapper.get(Types.UNSIGNED_BYTE, 0) == 5) {
                        wrapper.cancel();
                    }
                    wrapper.set(Types.NAMED_COMPOUND_TAG, 0, provider.transform(wrapper.user(), wrapper.get(Types.BLOCK_POSITION1_8, 0), wrapper.get(Types.NAMED_COMPOUND_TAG, 0)));
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.FORGET_LEVEL_CHUNK, wrapper -> {
            int chunkMinX = wrapper.passthrough(Types.INT) << 4;
            int chunkMinZ = wrapper.passthrough(Types.INT) << 4;
            int chunkMaxX = chunkMinX + 15;
            int chunkMaxZ = chunkMinZ + 15;
            BackwardsBlockStorage blockStorage = wrapper.user().get(BackwardsBlockStorage.class);
            blockStorage.getBlocks().entrySet().removeIf(entry -> {
                BlockPosition position = (BlockPosition)entry.getKey();
                return position.x() >= chunkMinX && position.z() >= chunkMinZ && position.x() <= chunkMaxX && position.z() <= chunkMaxZ;
            });
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.BLOCK_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.handler(wrapper -> {
                    int blockState = wrapper.read(Types.VAR_INT);
                    BlockPosition position = wrapper.get(Types.BLOCK_POSITION1_8, 0);
                    if (blockState >= 249 && blockState <= 748) {
                        wrapper.user().get(NoteBlockStorage.class).storeNoteBlockUpdate(position, blockState);
                    }
                    BackwardsBlockStorage storage = wrapper.user().get(BackwardsBlockStorage.class);
                    storage.checkAndStore(position, blockState);
                    wrapper.write(Types.VAR_INT, ((Protocol1_13To1_12_2)BlockItemPacketRewriter1_13.this.protocol).getMappingData().getNewBlockStateId(blockState));
                    BlockItemPacketRewriter1_13.flowerPotSpecialTreatment(wrapper.user(), blockState, position);
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.CHUNK_BLOCKS_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BLOCK_CHANGE_ARRAY);
                this.handler(wrapper -> {
                    BackwardsBlockStorage storage = wrapper.user().get(BackwardsBlockStorage.class);
                    for (BlockChangeRecord record : wrapper.get(Types.BLOCK_CHANGE_ARRAY, 0)) {
                        int chunkX = wrapper.get(Types.INT, 0);
                        int chunkZ = wrapper.get(Types.INT, 1);
                        int block = record.getBlockId();
                        BlockPosition position = new BlockPosition(record.getSectionX() + chunkX * 16, record.getY(), record.getSectionZ() + chunkZ * 16);
                        storage.checkAndStore(position, block);
                        BlockItemPacketRewriter1_13.flowerPotSpecialTreatment(wrapper.user(), block, position);
                        record.setBlockId(((Protocol1_13To1_12_2)BlockItemPacketRewriter1_13.this.protocol).getMappingData().getNewBlockStateId(block));
                    }
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.CONTAINER_SET_CONTENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.ITEM1_13_SHORT_ARRAY, Types.ITEM1_8_SHORT_ARRAY);
                this.handler(wrapper -> {
                    Item[] items;
                    for (Item item : items = wrapper.get(Types.ITEM1_8_SHORT_ARRAY, 0)) {
                        BlockItemPacketRewriter1_13.this.handleItemToClient(wrapper.user(), item);
                    }
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.CONTAINER_SET_SLOT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.SHORT);
                this.map(Types.ITEM1_13, Types.ITEM1_8);
                this.handler(wrapper -> BlockItemPacketRewriter1_13.this.handleItemToClient(wrapper.user(), wrapper.get(Types.ITEM1_8, 0)));
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.LEVEL_CHUNK, wrapper -> {
            int i;
            Object clientWorld = wrapper.user().getClientWorld(Protocol1_13To1_12_2.class);
            ChunkType1_9_3 type_old = ChunkType1_9_3.forEnvironment(((ClientWorld)clientWorld).getEnvironment());
            ChunkType1_13 type = ChunkType1_13.forEnvironment(((ClientWorld)clientWorld).getEnvironment());
            Chunk chunk = wrapper.read(type);
            BackwardsBlockEntityProvider provider = Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
            BackwardsBlockStorage storage = wrapper.user().get(BackwardsBlockStorage.class);
            for (CompoundTag tag : chunk.getBlockEntities()) {
                int sectionIndex;
                String id;
                StringTag idTag = tag.getStringTag("id");
                if (idTag == null || !provider.isHandled(id = idTag.getValue()) || (sectionIndex = tag.getNumberTag("y").asInt() >> 4) < 0 || sectionIndex > 15) continue;
                ChunkSection section = chunk.getSections()[sectionIndex];
                int x = tag.getNumberTag("x").asInt();
                short y = tag.getNumberTag("y").asShort();
                int z = tag.getNumberTag("z").asInt();
                BlockPosition position = new BlockPosition(x, y, z);
                int block = section.palette(PaletteType.BLOCKS).idAt(x & 0xF, y & 0xF, z & 0xF);
                storage.checkAndStore(position, block);
                provider.transform(wrapper.user(), position, tag);
            }
            for (i = 0; i < chunk.getSections().length; ++i) {
                ChunkSection section = chunk.getSections()[i];
                if (section == null) continue;
                DataPalette palette = section.palette(PaletteType.BLOCKS);
                for (int y = 0; y < 16; ++y) {
                    for (int z = 0; z < 16; ++z) {
                        for (int x = 0; x < 16; ++x) {
                            int block = palette.idAt(x, y, z);
                            if (!FlowerPotHandler.isFlowah(block)) continue;
                            BlockPosition pos = new BlockPosition(x + (chunk.getX() << 4), (short)(y + (i << 4)), z + (chunk.getZ() << 4));
                            storage.checkAndStore(pos, block);
                            CompoundTag nbt = provider.transform(wrapper.user(), pos, "minecraft:flower_pot");
                            chunk.getBlockEntities().add(nbt);
                        }
                    }
                }
                for (int j = 0; j < palette.size(); ++j) {
                    int mappedBlockStateId = ((Protocol1_13To1_12_2)this.protocol).getMappingData().getNewBlockStateId(palette.idByIndex(j));
                    palette.setIdByIndex(j, mappedBlockStateId);
                }
            }
            if (chunk.isBiomeData()) {
                for (i = 0; i < 256; ++i) {
                    int newId;
                    int biome = chunk.getBiomeData()[i];
                    switch (biome) {
                        case 40: 
                        case 41: 
                        case 42: 
                        case 43: {
                            int n = 9;
                            break;
                        }
                        case 47: 
                        case 48: 
                        case 49: {
                            int n = 24;
                            break;
                        }
                        case 50: {
                            int n = 10;
                            break;
                        }
                        case 44: 
                        case 45: 
                        case 46: {
                            int n = 0;
                            break;
                        }
                        default: {
                            int n = newId = -1;
                        }
                    }
                    if (newId == -1) continue;
                    chunk.getBiomeData()[i] = newId;
                }
            }
            wrapper.write(type_old, chunk);
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.LEVEL_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Types.INT, 0);
                    int data = wrapper.get(Types.INT, 1);
                    if (id == 1010) {
                        wrapper.set(Types.INT, 1, ((Protocol1_13To1_12_2)BlockItemPacketRewriter1_13.this.protocol).getMappingData().getItemMappings().getNewId(data) >> 4);
                    } else if (id == 2001) {
                        data = ((Protocol1_13To1_12_2)BlockItemPacketRewriter1_13.this.protocol).getMappingData().getNewBlockStateId(data);
                        int blockId = data >> 4;
                        int blockData = data & 0xF;
                        wrapper.set(Types.INT, 1, blockId & 0xFFF | blockData << 12);
                    }
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.MAP_ITEM_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BYTE);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    int iconCount = wrapper.passthrough(Types.VAR_INT);
                    for (int i = 0; i < iconCount; ++i) {
                        int type = wrapper.read(Types.VAR_INT);
                        byte x = wrapper.read(Types.BYTE);
                        byte z = wrapper.read(Types.BYTE);
                        byte direction = wrapper.read(Types.BYTE);
                        wrapper.read(Types.OPTIONAL_COMPONENT);
                        if (type > 9) {
                            wrapper.set(Types.VAR_INT, 1, wrapper.get(Types.VAR_INT, 1) - 1);
                            continue;
                        }
                        wrapper.write(Types.BYTE, (byte)(type << 4 | direction & 0xF));
                        wrapper.write(Types.BYTE, x);
                        wrapper.write(Types.BYTE, z);
                    }
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.SET_EQUIPPED_ITEM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.ITEM1_13, Types.ITEM1_8);
                this.handler(wrapper -> BlockItemPacketRewriter1_13.this.handleItemToClient(wrapper.user(), wrapper.get(Types.ITEM1_8, 0)));
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.CONTAINER_SET_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.handler(wrapper -> {
                    short property = wrapper.get(Types.SHORT, 0);
                    if (property >= 4 && property <= 6) {
                        short oldId = wrapper.get(Types.SHORT, 1);
                        wrapper.set(Types.SHORT, 1, (short)((Protocol1_13To1_12_2)BlockItemPacketRewriter1_13.this.protocol).getMappingData().getEnchantmentMappings().getNewId(oldId));
                    }
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_12_1.SET_CREATIVE_MODE_SLOT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.SHORT);
                this.map(Types.ITEM1_8, Types.ITEM1_13);
                this.handler(wrapper -> BlockItemPacketRewriter1_13.this.handleItemToServer(wrapper.user(), wrapper.get(Types.ITEM1_13, 0)));
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_12_1.CONTAINER_CLICK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.VAR_INT);
                this.map(Types.ITEM1_8, Types.ITEM1_13);
                this.handler(wrapper -> BlockItemPacketRewriter1_13.this.handleItemToServer(wrapper.user(), wrapper.get(Types.ITEM1_13, 0)));
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.enchantmentMappings.put("minecraft:loyalty", "\u00a77Loyalty");
        this.enchantmentMappings.put("minecraft:impaling", "\u00a77Impaling");
        this.enchantmentMappings.put("minecraft:riptide", "\u00a77Riptide");
        this.enchantmentMappings.put("minecraft:channeling", "\u00a77Channeling");
    }

    @Override
    public Item handleItemToClient(UserConnection connection, Item item) {
        Tag originalIdTag;
        if (item == null) {
            return null;
        }
        int originalId = item.identifier();
        Integer rawId = null;
        boolean gotRawIdFromTag = false;
        CompoundTag tag = item.tag();
        if (tag != null && (originalIdTag = tag.remove(this.extraNbtTag)) instanceof NumberTag) {
            rawId = ((NumberTag)originalIdTag).asInt();
            gotRawIdFromTag = true;
        }
        if (rawId == null) {
            super.handleItemToClient(connection, item);
            if (item.identifier() == -1) {
                if (originalId == 362) {
                    rawId = 0xE50000;
                } else {
                    if (!Via.getConfig().isSuppressConversionWarnings()) {
                        int n = originalId;
                        ((Protocol1_13To1_12_2)this.protocol).getLogger().warning("Failed to get new item for " + n);
                    }
                    rawId = 65536;
                }
            } else {
                if (tag == null) {
                    tag = item.tag();
                }
                rawId = this.itemIdToRaw(item.identifier(), item, tag);
            }
        }
        item.setIdentifier(rawId >> 16);
        item.setData((short)(rawId & 0xFFFF));
        if (tag != null) {
            StringTag name;
            if (BlockItemPacketRewriter1_13.isDamageable(item.identifier())) {
                Tag damageTag = tag.remove("Damage");
                if (!gotRawIdFromTag && damageTag instanceof NumberTag) {
                    item.setData(((NumberTag)damageTag).asShort());
                }
            }
            if (item.identifier() == 358) {
                Tag mapTag = tag.remove("map");
                if (!gotRawIdFromTag && mapTag instanceof NumberTag) {
                    item.setData(((NumberTag)mapTag).asShort());
                }
            }
            this.invertShieldAndBannerId(item, tag);
            CompoundTag display = tag.getCompoundTag("display");
            if (display != null && (name = display.getStringTag("Name")) != null) {
                String string = this.extraNbtTag;
                display.putString(string + "|Name", name.getValue());
                name.setValue(((Protocol1_13To1_12_2)this.protocol).jsonToLegacy(connection, name.getValue()));
            }
            this.rewriteEnchantmentsToClient(tag, false);
            this.rewriteEnchantmentsToClient(tag, true);
            this.rewriteCanPlaceToClient(tag, "CanPlaceOn");
            this.rewriteCanPlaceToClient(tag, "CanDestroy");
        }
        return item;
    }

    int itemIdToRaw(int oldId, Item item, CompoundTag tag) {
        Optional<String> eggEntityId = SpawnEggMappings1_13.getEntityId(oldId);
        if (eggEntityId.isPresent()) {
            if (tag == null) {
                tag = new CompoundTag();
                item.setTag(tag);
            }
            if (!tag.contains("EntityTag")) {
                CompoundTag entityTag = new CompoundTag();
                entityTag.putString("id", eggEntityId.get());
                tag.put("EntityTag", entityTag);
            }
            return 25100288;
        }
        return oldId >> 4 << 16 | oldId & 0xF;
    }

    void rewriteCanPlaceToClient(CompoundTag tag, String tagName) {
        ListTag<?> blockTag = tag.getListTag(tagName);
        if (blockTag == null) {
            return;
        }
        ListTag<StringTag> newCanPlaceOn = new ListTag<StringTag>(StringTag.class);
        String string = tagName;
        String string2 = this.extraNbtTag;
        tag.put(string2 + "|" + string, blockTag.copy());
        for (Tag oldTag : blockTag) {
            String[] newValues;
            Object value = oldTag.getValue();
            String[] stringArray = newValues = value instanceof String ? BlockIdData.fallbackReverseMapping.get(Key.stripMinecraftNamespace((String)value)) : null;
            if (newValues != null) {
                for (String newValue : newValues) {
                    newCanPlaceOn.add(new StringTag(newValue));
                }
                continue;
            }
            newCanPlaceOn.add(new StringTag(oldTag.getValue().toString()));
        }
        tag.put(tagName, newCanPlaceOn);
    }

    void rewriteEnchantmentsToClient(CompoundTag tag, boolean storedEnch) {
        String key = storedEnch ? "StoredEnchantments" : "Enchantments";
        ListTag<CompoundTag> enchantments = tag.getListTag(key, CompoundTag.class);
        if (enchantments == null) {
            return;
        }
        ListTag<CompoundTag> noMapped = new ListTag<CompoundTag>(CompoundTag.class);
        ListTag<CompoundTag> newEnchantments = new ListTag<CompoundTag>(CompoundTag.class);
        ArrayList<StringTag> lore = new ArrayList<StringTag>();
        boolean hasValidEnchants = false;
        Iterator iterator2 = ((ListTag)enchantments.copy()).iterator();
        while (iterator2.hasNext()) {
            CompoundTag enchantmentEntry = (CompoundTag)iterator2.next();
            StringTag idTag = enchantmentEntry.getStringTag("id");
            if (idTag == null) continue;
            String newId = idTag.getValue();
            NumberTag levelTag = enchantmentEntry.getNumberTag("lvl");
            if (levelTag == null) continue;
            int levelValue = levelTag.asInt();
            int level = levelValue < Short.MAX_VALUE ? (int)levelValue : Short.MAX_VALUE;
            String mappedEnchantmentId = this.enchantmentMappings.get(newId);
            if (mappedEnchantmentId != null) {
                String string = EnchantmentRewriter.getRomanNumber(level);
                String string2 = mappedEnchantmentId;
                lore.add(new StringTag(string2 + " " + string));
                noMapped.add(enchantmentEntry);
                continue;
            }
            if (newId.isEmpty()) continue;
            Short oldId = (Short)Protocol1_12_2To1_13.MAPPINGS.getOldEnchantmentsIds().inverse().get((Object)Key.stripMinecraftNamespace(newId));
            if (oldId == null) {
                if (!newId.startsWith("viaversion:legacy/")) {
                    noMapped.add(enchantmentEntry);
                    if (ViaBackwards.getConfig().addCustomEnchantsToLore()) {
                        String name = newId;
                        int index2 = name.indexOf(58) + 1;
                        if (index2 != 0 && index2 != name.length()) {
                            name = name.substring(index2);
                        }
                        String string = name.substring(1).toLowerCase(Locale.ENGLISH);
                        char c = Character.toUpperCase(name.charAt(0));
                        name = "\u00a77" + c + string;
                        String string3 = EnchantmentRewriter.getRomanNumber(level);
                        String string4 = name;
                        lore.add(new StringTag(string4 + " " + string3));
                    }
                    if (!Via.getManager().isDebug()) continue;
                    String string = newId;
                    ((Protocol1_13To1_12_2)this.protocol).getLogger().warning("Found unknown enchant: " + string);
                    continue;
                }
                oldId = Short.valueOf(newId.substring(18));
            }
            if (level != 0) {
                hasValidEnchants = true;
            }
            CompoundTag newEntry = new CompoundTag();
            newEntry.putShort("id", oldId);
            newEntry.putShort("lvl", (short)level);
            newEnchantments.add(newEntry);
        }
        if (!storedEnch && !hasValidEnchants) {
            NumberTag hideFlags = tag.getNumberTag("HideFlags");
            if (hideFlags == null) {
                hideFlags = new IntTag();
                String string = this.extraNbtTag;
                tag.put(string + "|DummyEnchant", new ByteTag(false));
            } else {
                String string = this.extraNbtTag;
                tag.putInt(string + "|OldHideFlags", hideFlags.asByte());
            }
            if (newEnchantments.isEmpty()) {
                CompoundTag enchEntry = new CompoundTag();
                enchEntry.putShort("id", (short)0);
                enchEntry.putShort("lvl", (short)0);
                newEnchantments.add(enchEntry);
            }
            int value = hideFlags.asByte() | 1;
            tag.putInt("HideFlags", value);
        }
        if (!noMapped.isEmpty()) {
            String string = key;
            String string5 = this.extraNbtTag;
            tag.put(string5 + "|" + string, noMapped);
            if (!lore.isEmpty()) {
                ListTag<StringTag> loreTag;
                CompoundTag display = tag.getCompoundTag("display");
                if (display == null) {
                    display = new CompoundTag();
                    tag.put("display", display);
                }
                if ((loreTag = display.getListTag("Lore", StringTag.class)) == null) {
                    loreTag = new ListTag<StringTag>(StringTag.class);
                    display.put("Lore", loreTag);
                    String string6 = this.extraNbtTag;
                    tag.put(string6 + "|DummyLore", new ByteTag(false));
                } else if (!loreTag.isEmpty()) {
                    ListTag<StringTag> oldLore = new ListTag<StringTag>(StringTag.class);
                    for (StringTag value : loreTag) {
                        oldLore.add(value.copy());
                    }
                    String string7 = this.extraNbtTag;
                    tag.put(string7 + "|OldLore", oldLore);
                    lore.addAll((Collection<StringTag>)loreTag.getValue());
                }
                loreTag.setValue(lore);
            }
        }
        tag.remove("Enchantments");
        tag.put(storedEnch ? key : "ench", newEnchantments);
    }

    @Override
    public Item handleItemToServer(UserConnection connection, Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.tag();
        int originalId = item.identifier() << 16 | item.data() & 0xFFFF;
        int rawId = IdAndData.toRawData(item.identifier(), item.data());
        if (BlockItemPacketRewriter1_13.isDamageable(item.identifier())) {
            if (tag == null) {
                tag = new CompoundTag();
                item.setTag(tag);
            }
            tag.putInt("Damage", item.data());
        }
        if (item.identifier() == 358) {
            if (tag == null) {
                tag = new CompoundTag();
                item.setTag(tag);
            }
            tag.putInt("map", item.data());
        }
        if (tag != null) {
            StringTag name;
            this.invertShieldAndBannerId(item, tag);
            CompoundTag display = tag.getCompoundTag("display");
            if (display != null && (name = display.getStringTag("Name")) != null) {
                String string = this.extraNbtTag;
                Tag via = display.remove(string + "|Name");
                name.setValue(via instanceof StringTag ? ((StringTag)via).getValue() : ComponentUtil.legacyToJsonString(name.getValue()));
            }
            this.rewriteEnchantmentsToServer(tag, false);
            this.rewriteEnchantmentsToServer(tag, true);
            this.rewriteCanPlaceToServer(tag, "CanPlaceOn");
            this.rewriteCanPlaceToServer(tag, "CanDestroy");
            if (item.identifier() == 383) {
                StringTag identifier;
                CompoundTag entityTag = tag.getCompoundTag("EntityTag");
                if (entityTag != null && (identifier = entityTag.getStringTag("id")) != null) {
                    rawId = SpawnEggMappings1_13.getSpawnEggId(identifier.getValue());
                    if (rawId == -1) {
                        rawId = 25100288;
                    } else {
                        entityTag.remove("id");
                        if (entityTag.isEmpty()) {
                            tag.remove("EntityTag");
                        }
                    }
                } else {
                    rawId = 25100288;
                }
            }
            if (tag.isEmpty()) {
                tag = null;
                item.setTag(null);
            }
        }
        int identifier = item.identifier();
        item.setIdentifier(rawId);
        super.handleItemToServer(connection, item);
        if (item.identifier() != rawId && item.identifier() != -1) {
            return item;
        }
        item.setIdentifier(identifier);
        int newId = -1;
        if (((Protocol1_13To1_12_2)this.protocol).getMappingData().getItemMappings().inverse().getNewId(rawId) == -1) {
            if (!BlockItemPacketRewriter1_13.isDamageable(item.identifier()) && item.identifier() != 358) {
                if (tag == null) {
                    tag = new CompoundTag();
                    item.setTag(tag);
                }
                tag.putInt(this.extraNbtTag, originalId);
            }
            if (item.identifier() == 229) {
                newId = 362;
            } else if (item.identifier() == 31 && item.data() == 0) {
                rawId = IdAndData.toRawData(32);
            } else if (((Protocol1_13To1_12_2)this.protocol).getMappingData().getItemMappings().inverse().getNewId(rawId & 0xFFFFFFF0) != -1) {
                rawId &= 0xFFFFFFF0;
            } else {
                if (!Via.getConfig().isSuppressConversionWarnings()) {
                    int n = item.identifier();
                    ((Protocol1_13To1_12_2)this.protocol).getLogger().warning("Failed to get old item for " + n);
                }
                rawId = 16;
            }
        }
        if (newId == -1) {
            newId = ((Protocol1_13To1_12_2)this.protocol).getMappingData().getItemMappings().inverse().getNewId(rawId);
        }
        item.setIdentifier(newId);
        item.setData((short)0);
        return item;
    }

    void rewriteCanPlaceToServer(CompoundTag tag, String tagName) {
        if (tag.getListTag(tagName) == null) {
            return;
        }
        String string = tagName;
        String string2 = this.extraNbtTag;
        ListTag<?> blockTag = tag.getListTag(string2 + "|" + string);
        if (blockTag != null) {
            String string3 = tagName;
            String string4 = this.extraNbtTag;
            tag.remove(string4 + "|" + string3);
            tag.put(tagName, blockTag.copy());
        } else {
            blockTag = tag.getListTag(tagName);
            if (blockTag != null) {
                ListTag<StringTag> newCanPlaceOn = new ListTag<StringTag>(StringTag.class);
                for (Tag oldTag : blockTag) {
                    String lowerCaseId;
                    String[] newValues;
                    Object value = oldTag.getValue();
                    String oldId = Key.stripMinecraftNamespace(value.toString());
                    int key = Ints.tryParse((String)oldId);
                    String numberConverted = (String)BlockIdData.numberIdToString.get(key);
                    if (numberConverted != null) {
                        oldId = numberConverted;
                    }
                    if ((newValues = BlockIdData.blockIdMapping.get(lowerCaseId = oldId.toLowerCase(Locale.ROOT))) != null) {
                        for (String newValue : newValues) {
                            newCanPlaceOn.add(new StringTag(newValue));
                        }
                        continue;
                    }
                    newCanPlaceOn.add(new StringTag(lowerCaseId));
                }
                tag.put(tagName, newCanPlaceOn);
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    void rewriteEnchantmentsToServer(CompoundTag tag, boolean storedEnch) {
        void var8_15;
        String string;
        ListTag<StringTag> oldLore;
        CompoundTag compoundTag;
        String key = storedEnch ? "StoredEnchantments" : "Enchantments";
        ListTag<CompoundTag> enchantments = tag.getListTag(storedEnch ? key : "ench", CompoundTag.class);
        if (enchantments == null) {
            return;
        }
        ListTag<CompoundTag> newEnchantments = new ListTag<CompoundTag>(CompoundTag.class);
        boolean dummyEnchant = false;
        if (!storedEnch) {
            String string2 = this.extraNbtTag;
            Tag hideFlags = tag.remove(string2 + "|OldHideFlags");
            if (hideFlags instanceof IntTag) {
                tag.putInt("HideFlags", ((NumberTag)hideFlags).asByte());
                dummyEnchant = true;
            } else {
                String string3 = this.extraNbtTag;
                if (tag.remove(string3 + "|DummyEnchant") != null) {
                    tag.remove("HideFlags");
                    dummyEnchant = true;
                }
            }
        }
        for (CompoundTag compoundTag2 : enchantments) {
            short level;
            NumberTag idTag = compoundTag2.getNumberTag("id");
            NumberTag levelTag = compoundTag2.getNumberTag("lvl");
            CompoundTag enchantmentEntry = new CompoundTag();
            short oldId = idTag != null ? idTag.asShort() : (short)0;
            short s = level = levelTag != null ? levelTag.asShort() : (short)0;
            if (dummyEnchant && oldId == 0 && level == 0) continue;
            String newId = (String)Protocol1_12_2To1_13.MAPPINGS.getOldEnchantmentsIds().get((Object)oldId);
            if (newId == null) {
                short s2 = oldId;
                newId = "viaversion:legacy/" + s2;
            }
            enchantmentEntry.putString("id", newId);
            enchantmentEntry.putShort("lvl", level);
            newEnchantments.add(enchantmentEntry);
        }
        String string2 = this.extraNbtTag;
        ListTag<CompoundTag> noMapped = tag.getListTag(string2 + "|Enchantments", CompoundTag.class);
        if (noMapped != null) {
            for (CompoundTag value : noMapped) {
                newEnchantments.add(value);
            }
            String string3 = this.extraNbtTag;
            tag.remove(string3 + "|Enchantments");
        }
        if ((compoundTag = tag.getCompoundTag("display")) == null) {
            CompoundTag compoundTag3 = new CompoundTag();
            tag.put("display", compoundTag3);
        }
        if ((oldLore = tag.getListTag((string = this.extraNbtTag) + "|OldLore", StringTag.class)) != null) {
            ListTag<StringTag> lore = var8_15.getListTag("Lore", StringTag.class);
            if (lore == null) {
                lore = new ListTag<StringTag>(StringTag.class);
                tag.put("Lore", lore);
            }
            lore.setValue((List<StringTag>)oldLore.getValue());
            String string4 = this.extraNbtTag;
            tag.remove(string4 + "|OldLore");
        } else {
            String string5 = this.extraNbtTag;
            if (tag.remove(string5 + "|DummyLore") != null) {
                var8_15.remove("Lore");
                if (var8_15.isEmpty()) {
                    tag.remove("display");
                }
            }
        }
        if (!storedEnch) {
            tag.remove("ench");
        }
        tag.put(key, newEnchantments);
    }

    void invertShieldAndBannerId(Item item, CompoundTag tag) {
        ListTag<CompoundTag> patterns;
        if (item.identifier() != 442 && item.identifier() != 425) {
            return;
        }
        CompoundTag blockEntityTag = tag.getCompoundTag("BlockEntityTag");
        if (blockEntityTag == null) {
            return;
        }
        NumberTag base = blockEntityTag.getNumberTag("Base");
        if (base != null) {
            blockEntityTag.putInt("Base", 15 - base.asInt());
        }
        if ((patterns = blockEntityTag.getListTag("Patterns", CompoundTag.class)) != null) {
            for (CompoundTag pattern : patterns) {
                NumberTag colorTag = pattern.getNumberTag("Color");
                pattern.putInt("Color", 15 - colorTag.asInt());
            }
        }
    }

    static void flowerPotSpecialTreatment(UserConnection user, int blockState, BlockPosition position) {
        if (FlowerPotHandler.isFlowah(blockState)) {
            BackwardsBlockEntityProvider beProvider = Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
            CompoundTag nbt = beProvider.transform(user, position, "minecraft:flower_pot");
            PacketWrapper blockUpdateRemove = PacketWrapper.create(ClientboundPackets1_12_1.BLOCK_UPDATE, user);
            blockUpdateRemove.write(Types.BLOCK_POSITION1_8, position);
            blockUpdateRemove.write(Types.VAR_INT, 0);
            blockUpdateRemove.scheduleSend(Protocol1_13To1_12_2.class);
            PacketWrapper blockCreate = PacketWrapper.create(ClientboundPackets1_12_1.BLOCK_UPDATE, user);
            blockCreate.write(Types.BLOCK_POSITION1_8, position);
            blockCreate.write(Types.VAR_INT, Protocol1_13To1_12_2.MAPPINGS.getNewBlockStateId(blockState));
            blockCreate.scheduleSend(Protocol1_13To1_12_2.class);
            PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_12_1.BLOCK_ENTITY_DATA, user);
            wrapper.write(Types.BLOCK_POSITION1_8, position);
            wrapper.write(Types.UNSIGNED_BYTE, (short)5);
            wrapper.write(Types.NAMED_COMPOUND_TAG, nbt);
            wrapper.scheduleSend(Protocol1_13To1_12_2.class);
        }
    }
}

