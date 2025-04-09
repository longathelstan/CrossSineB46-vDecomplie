/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_14to1_13_2.rewriter;

import com.google.common.collect.ImmutableSet;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.api.rewriters.BackwardsItemRewriter;
import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viabackwards.protocol.v1_14to1_13_2.storage.ChunkLightStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLightImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_13;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_14;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.utils.TextUtils;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.SerializerVersion;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class BlockItemPacketRewriter1_14
extends BackwardsItemRewriter<ClientboundPackets1_14, ServerboundPackets1_13, Protocol1_14To1_13_2> {
    EnchantmentRewriter enchantmentRewriter;

    public BlockItemPacketRewriter1_14(Protocol1_14To1_13_2 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_SHORT_ARRAY);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_14To1_13_2)this.protocol).registerServerbound(ServerboundPackets1_13.EDIT_BOOK, wrapper -> this.handleItemToServer(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2)));
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.OPEN_SCREEN, wrapper -> {
            JsonObject object;
            int windowId = wrapper.read(Types.VAR_INT);
            wrapper.write(Types.UNSIGNED_BYTE, (short)windowId);
            int type = wrapper.read(Types.VAR_INT);
            String stringType = null;
            String containerTitle = null;
            int slotSize = 0;
            if (type < 6) {
                if (type == 2) {
                    containerTitle = "Barrel";
                }
                stringType = "minecraft:container";
                slotSize = (type + 1) * 9;
            } else {
                switch (type) {
                    case 11: {
                        stringType = "minecraft:crafting_table";
                        break;
                    }
                    case 9: 
                    case 13: 
                    case 14: 
                    case 20: {
                        if (type == 9) {
                            containerTitle = "Blast Furnace";
                        } else if (type == 20) {
                            containerTitle = "Smoker";
                        } else if (type == 14) {
                            containerTitle = "Grindstone";
                        }
                        stringType = "minecraft:furnace";
                        slotSize = 3;
                        break;
                    }
                    case 6: {
                        stringType = "minecraft:dropper";
                        slotSize = 9;
                        break;
                    }
                    case 12: {
                        stringType = "minecraft:enchanting_table";
                        break;
                    }
                    case 10: {
                        stringType = "minecraft:brewing_stand";
                        slotSize = 5;
                        break;
                    }
                    case 18: {
                        stringType = "minecraft:villager";
                        break;
                    }
                    case 8: {
                        stringType = "minecraft:beacon";
                        slotSize = 1;
                        break;
                    }
                    case 7: 
                    case 21: {
                        if (type == 21) {
                            containerTitle = "Cartography Table";
                        }
                        stringType = "minecraft:anvil";
                        break;
                    }
                    case 15: {
                        stringType = "minecraft:hopper";
                        slotSize = 5;
                        break;
                    }
                    case 19: {
                        stringType = "minecraft:shulker_box";
                        slotSize = 27;
                    }
                }
            }
            if (stringType == null) {
                int n = type;
                ((Protocol1_14To1_13_2)this.protocol).getLogger().warning("Can't open inventory for player! Type: " + n);
                wrapper.cancel();
                return;
            }
            wrapper.write(Types.STRING, stringType);
            JsonElement title2 = wrapper.read(Types.COMPONENT);
            if (containerTitle != null && title2.isJsonObject() && (object = title2.getAsJsonObject()).has("translate") && (type != 2 || object.getAsJsonPrimitive("translate").getAsString().equals("container.barrel"))) {
                title2 = ComponentUtil.legacyToJson(containerTitle);
            }
            wrapper.write(Types.COMPONENT, title2);
            wrapper.write(Types.UNSIGNED_BYTE, (short)slotSize);
        });
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.HORSE_SCREEN_OPEN, ClientboundPackets1_13.OPEN_SCREEN, wrapper -> {
            wrapper.passthrough(Types.UNSIGNED_BYTE);
            wrapper.write(Types.STRING, "EntityHorse");
            JsonObject object = new JsonObject();
            object.addProperty("translate", "minecraft.horse");
            wrapper.write(Types.COMPONENT, object);
            wrapper.write(Types.UNSIGNED_BYTE, wrapper.read(Types.VAR_INT).shortValue());
            wrapper.passthrough(Types.INT);
        });
        BlockRewriter<ClientboundPackets1_14> blockRewriter = BlockRewriter.legacy(this.protocol);
        this.registerCooldown(ClientboundPackets1_14.COOLDOWN);
        this.registerSetContent(ClientboundPackets1_14.CONTAINER_SET_CONTENT);
        this.registerSetSlot(ClientboundPackets1_14.CONTAINER_SET_SLOT);
        this.registerAdvancements(ClientboundPackets1_14.UPDATE_ADVANCEMENTS);
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.MERCHANT_OFFERS, ClientboundPackets1_13.CUSTOM_PAYLOAD, wrapper -> {
            wrapper.write(Types.STRING, "minecraft:trader_list");
            int windowId = wrapper.read(Types.VAR_INT);
            wrapper.write(Types.INT, windowId);
            int size = wrapper.passthrough(Types.UNSIGNED_BYTE).shortValue();
            for (int i = 0; i < size; ++i) {
                Item input = wrapper.read(Types.ITEM1_13_2);
                input = this.handleItemToClient(wrapper.user(), input);
                wrapper.write(Types.ITEM1_13_2, input);
                Item output = wrapper.read(Types.ITEM1_13_2);
                output = this.handleItemToClient(wrapper.user(), output);
                wrapper.write(Types.ITEM1_13_2, output);
                boolean secondItem = wrapper.passthrough(Types.BOOLEAN);
                if (secondItem) {
                    Item second = wrapper.read(Types.ITEM1_13_2);
                    second = this.handleItemToClient(wrapper.user(), second);
                    wrapper.write(Types.ITEM1_13_2, second);
                }
                wrapper.passthrough(Types.BOOLEAN);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.read(Types.INT);
                wrapper.read(Types.INT);
                wrapper.read(Types.FLOAT);
            }
            wrapper.read(Types.VAR_INT);
            wrapper.read(Types.VAR_INT);
            wrapper.read(Types.BOOLEAN);
        });
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.OPEN_BOOK, ClientboundPackets1_13.CUSTOM_PAYLOAD, wrapper -> {
            wrapper.write(Types.STRING, "minecraft:book_open");
            wrapper.passthrough(Types.VAR_INT);
        });
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.SET_EQUIPPED_ITEM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.ITEM1_13_2);
                this.handler(wrapper -> BlockItemPacketRewriter1_14.this.handleItemToClient(wrapper.user(), wrapper.get(Types.ITEM1_13_2, 0)));
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.VAR_INT, 0);
                    EntityType entityType = wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class).entityType(entityId);
                    if (entityType == null) {
                        return;
                    }
                    if (entityType.isOrHasParent(EntityTypes1_14.ABSTRACT_HORSE)) {
                        int armorType;
                        wrapper.setPacketType(ClientboundPackets1_13.SET_ENTITY_DATA);
                        wrapper.resetReader();
                        wrapper.passthrough(Types.VAR_INT);
                        wrapper.read(Types.VAR_INT);
                        Item item = wrapper.read(Types.ITEM1_13_2);
                        int n = armorType = item == null || item.identifier() == 0 ? 0 : item.identifier() - 726;
                        if (armorType < 0 || armorType > 3) {
                            wrapper.cancel();
                            return;
                        }
                        ArrayList<EntityData> entityDataList = new ArrayList<EntityData>();
                        entityDataList.add(new EntityData(16, Types1_13_2.ENTITY_DATA_TYPES.varIntType, armorType));
                        wrapper.write(Types1_13.ENTITY_DATA_LIST, entityDataList);
                    }
                });
            }
        });
        RecipeRewriter recipeHandler = new RecipeRewriter(this.protocol);
        ImmutableSet removedTypes = ImmutableSet.of((Object)"crafting_special_suspiciousstew", (Object)"blasting", (Object)"smoking", (Object)"campfire_cooking", (Object)"stonecutting");
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.UPDATE_RECIPES, arg_0 -> BlockItemPacketRewriter1_14.lambda$registerPackets$5((Set)removedTypes, recipeHandler, arg_0));
        this.registerContainerClick(ServerboundPackets1_13.CONTAINER_CLICK);
        this.registerSetCreativeModeSlot(ServerboundPackets1_13.SET_CREATIVE_MODE_SLOT);
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_DESTRUCTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BLOCK_POSITION1_14, Types.BLOCK_POSITION1_8);
                this.map(Types.BYTE);
            }
        });
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_14, Types.BLOCK_POSITION1_8);
            }
        });
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_14, Types.BLOCK_POSITION1_8);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int mappedId = ((Protocol1_14To1_13_2)BlockItemPacketRewriter1_14.this.protocol).getMappingData().getNewBlockId(wrapper.get(Types.VAR_INT, 0));
                    if (mappedId == -1) {
                        wrapper.cancel();
                        return;
                    }
                    wrapper.set(Types.VAR_INT, 0, mappedId);
                });
            }
        });
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.BLOCK_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_14, Types.BLOCK_POSITION1_8);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Types.VAR_INT, 0);
                    wrapper.set(Types.VAR_INT, 0, ((Protocol1_14To1_13_2)BlockItemPacketRewriter1_14.this.protocol).getMappingData().getNewBlockStateId(id));
                });
            }
        });
        blockRewriter.registerChunkBlocksUpdate(ClientboundPackets1_14.CHUNK_BLOCKS_UPDATE);
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.EXPLODE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.handler(wrapper -> {
                    for (int i = 0; i < 3; ++i) {
                        float coord = wrapper.get(Types.FLOAT, i).floatValue();
                        if (!(coord < 0.0f)) continue;
                        coord = (float)Math.floor(coord);
                        wrapper.set(Types.FLOAT, i, Float.valueOf(coord));
                    }
                });
            }
        });
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.LEVEL_CHUNK, wrapper -> {
            Object clientWorld = wrapper.user().getClientWorld(Protocol1_14To1_13_2.class);
            Chunk chunk = wrapper.read(ChunkType1_14.TYPE);
            wrapper.write(ChunkType1_13.forEnvironment(((ClientWorld)clientWorld).getEnvironment()), chunk);
            ChunkLightStorage.ChunkLight chunkLight = wrapper.user().get(ChunkLightStorage.class).getStoredLight(chunk.getX(), chunk.getZ());
            for (int i = 0; i < chunk.getSections().length; ++i) {
                ChunkSection section = chunk.getSections()[i];
                if (section == null) continue;
                ChunkSectionLightImpl sectionLight = new ChunkSectionLightImpl();
                section.setLight(sectionLight);
                if (chunkLight == null) {
                    sectionLight.setBlockLight(ChunkLightStorage.FULL_LIGHT);
                    if (((ClientWorld)clientWorld).getEnvironment() == Environment.NORMAL) {
                        sectionLight.setSkyLight(ChunkLightStorage.FULL_LIGHT);
                    }
                } else {
                    byte[] blockLight = chunkLight.blockLight()[i];
                    sectionLight.setBlockLight(blockLight != null ? blockLight : ChunkLightStorage.FULL_LIGHT);
                    if (((ClientWorld)clientWorld).getEnvironment() == Environment.NORMAL) {
                        byte[] skyLight = chunkLight.skyLight()[i];
                        sectionLight.setSkyLight(skyLight != null ? skyLight : ChunkLightStorage.FULL_LIGHT);
                    }
                }
                DataPalette palette = section.palette(PaletteType.BLOCKS);
                if (Via.getConfig().isNonFullBlockLightFix() && section.getNonAirBlocksCount() != 0 && sectionLight.hasBlockLight()) {
                    for (int x = 0; x < 16; ++x) {
                        for (int y = 0; y < 16; ++y) {
                            for (int z = 0; z < 16; ++z) {
                                int id = palette.idAt(x, y, z);
                                if (!Protocol1_13_2To1_14.MAPPINGS.getNonFullBlocks().contains(id)) continue;
                                sectionLight.getBlockLightNibbleArray().set(x, y, z, 0);
                            }
                        }
                    }
                }
                for (int j = 0; j < palette.size(); ++j) {
                    int mappedBlockStateId = ((Protocol1_14To1_13_2)this.protocol).getMappingData().getNewBlockStateId(palette.idByIndex(j));
                    palette.setIdByIndex(j, mappedBlockStateId);
                }
            }
        });
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.FORGET_LEVEL_CHUNK, wrapper -> {
            int x = wrapper.passthrough(Types.INT);
            int z = wrapper.passthrough(Types.INT);
            wrapper.user().get(ChunkLightStorage.class).unloadChunk(x, z);
        });
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.LEVEL_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BLOCK_POSITION1_14, Types.BLOCK_POSITION1_8);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Types.INT, 0);
                    int data = wrapper.get(Types.INT, 1);
                    if (id == 1010) {
                        wrapper.set(Types.INT, 1, ((Protocol1_14To1_13_2)BlockItemPacketRewriter1_14.this.protocol).getMappingData().getNewItemId(data));
                    } else if (id == 2001) {
                        wrapper.set(Types.INT, 1, ((Protocol1_14To1_13_2)BlockItemPacketRewriter1_14.this.protocol).getMappingData().getNewBlockStateId(data));
                    }
                });
            }
        });
        this.registerLevelParticles(ClientboundPackets1_14.LEVEL_PARTICLES, Types.FLOAT);
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.MAP_ITEM_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BYTE);
                this.map(Types.BOOLEAN);
                this.read(Types.BOOLEAN);
            }
        });
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.SET_DEFAULT_SPAWN_POSITION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_14, Types.BLOCK_POSITION1_8);
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.enchantmentRewriter = new EnchantmentRewriter(this, false);
        this.enchantmentRewriter.registerEnchantment("minecraft:multishot", "\u00a77Multishot");
        this.enchantmentRewriter.registerEnchantment("minecraft:quick_charge", "\u00a77Quick Charge");
        this.enchantmentRewriter.registerEnchantment("minecraft:piercing", "\u00a77Piercing");
    }

    @Override
    public Item handleItemToClient(UserConnection connection, Item item) {
        ListTag<StringTag> lore;
        CompoundTag display;
        if (item == null) {
            return null;
        }
        super.handleItemToClient(connection, item);
        CompoundTag tag = item.tag();
        if (tag != null && (display = tag.getCompoundTag("display")) != null && (lore = display.getListTag("Lore", StringTag.class)) != null) {
            this.saveListTag(display, lore, "Lore");
            try {
                Iterator<StringTag> each = lore.iterator();
                while (each.hasNext()) {
                    StringTag loreEntry = each.next();
                    ATextComponent component = SerializerVersion.V1_12.toComponent(loreEntry.getValue());
                    if (component == null) {
                        each.remove();
                        continue;
                    }
                    TextUtils.setTranslator(component, s -> Protocol1_12_2To1_13.MAPPINGS.getMojangTranslation().getOrDefault(s, TranslatableRewriter.getTranslatableMappings("1.14").get(s)));
                    loreEntry.setValue(component.asLegacyFormatString());
                }
            }
            catch (JsonParseException e) {
                display.remove("Lore");
            }
        }
        this.enchantmentRewriter.handleToClient(item);
        return item;
    }

    @Override
    public Item handleItemToServer(UserConnection connection, Item item) {
        ListTag<StringTag> lore;
        CompoundTag display;
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.tag();
        if (tag != null && (display = tag.getCompoundTag("display")) != null && (lore = display.getListTag("Lore", StringTag.class)) != null && !this.hasBackupTag(display, "Lore")) {
            for (StringTag loreEntry : lore) {
                loreEntry.setValue(ComponentUtil.legacyToJsonString(loreEntry.getValue()));
            }
        }
        this.enchantmentRewriter.handleToServer(item);
        super.handleItemToServer(connection, item);
        return item;
    }

    static /* synthetic */ void lambda$registerPackets$5(Set removedTypes, RecipeRewriter recipeHandler, PacketWrapper wrapper) throws InformativeException {
        int size = wrapper.passthrough(Types.VAR_INT);
        int deleted = 0;
        for (int i = 0; i < size; ++i) {
            String type = wrapper.read(Types.STRING);
            String id = wrapper.read(Types.STRING);
            if (removedTypes.contains(type = Key.stripMinecraftNamespace(type))) {
                switch (type) {
                    case "blasting": 
                    case "smoking": 
                    case "campfire_cooking": {
                        wrapper.read(Types.STRING);
                        wrapper.read(Types.ITEM1_13_2_ARRAY);
                        wrapper.read(Types.ITEM1_13_2);
                        wrapper.read(Types.FLOAT);
                        wrapper.read(Types.VAR_INT);
                        break;
                    }
                    case "stonecutting": {
                        wrapper.read(Types.STRING);
                        wrapper.read(Types.ITEM1_13_2_ARRAY);
                        wrapper.read(Types.ITEM1_13_2);
                    }
                }
                ++deleted;
                continue;
            }
            wrapper.write(Types.STRING, id);
            wrapper.write(Types.STRING, type);
            recipeHandler.handleRecipeType(wrapper, type);
        }
        wrapper.set(Types.VAR_INT, 0, size - deleted);
    }
}

