/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.rewriter;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.BlockIdData;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.MappingData1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.SoundSource1_12_2;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.SpawnEggMappings1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ClientboundPackets1_12_1;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.IdAndData;
import com.viaversion.viaversion.util.Key;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

public class ItemPacketRewriter1_13
extends ItemRewriter<ClientboundPackets1_12_1, ServerboundPackets1_13, Protocol1_12_2To1_13> {
    public ItemPacketRewriter1_13(Protocol1_12_2To1_13 protocol) {
        super(protocol, Types.ITEM1_8, Types.ITEM1_8_SHORT_ARRAY, Types.ITEM1_13, Types.ITEM1_13_SHORT_ARRAY);
    }

    @Override
    public void registerPackets() {
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.CONTAINER_SET_SLOT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.SHORT);
                this.map(Types.ITEM1_8, Types.ITEM1_13);
                this.handler(wrapper -> ItemPacketRewriter1_13.this.handleItemToClient(wrapper.user(), wrapper.get(Types.ITEM1_13, 0)));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.CONTAINER_SET_CONTENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.ITEM1_8_SHORT_ARRAY, Types.ITEM1_13_SHORT_ARRAY);
                this.handler(wrapper -> {
                    Item[] items;
                    for (Item item : items = wrapper.get(Types.ITEM1_13_SHORT_ARRAY, 0)) {
                        ItemPacketRewriter1_13.this.handleItemToClient(wrapper.user(), item);
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.CONTAINER_SET_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.handler(wrapper -> {
                    short property = wrapper.get(Types.SHORT, 0);
                    if (property >= 4 && property <= 6) {
                        wrapper.set(Types.SHORT, 1, (short)((Protocol1_12_2To1_13)ItemPacketRewriter1_13.this.protocol).getMappingData().getEnchantmentMappings().getNewId(wrapper.get(Types.SHORT, 1).shortValue()));
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.CUSTOM_PAYLOAD, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.handlerSoftFail(wrapper -> {
                    String channel = wrapper.get(Types.STRING, 0);
                    if (channel.equals("MC|StopSound")) {
                        String originalSource = wrapper.read(Types.STRING);
                        String originalSound = wrapper.read(Types.STRING);
                        wrapper.clearPacket();
                        wrapper.setPacketType(ClientboundPackets1_13.STOP_SOUND);
                        byte flags = 0;
                        wrapper.write(Types.BYTE, flags);
                        if (!originalSource.isEmpty()) {
                            flags = (byte)(flags | 1);
                            Optional<SoundSource1_12_2> finalSource = SoundSource1_12_2.findBySource(originalSource);
                            if (finalSource.isPresent() ^ true) {
                                if (!Via.getConfig().isSuppressConversionWarnings()) {
                                    String string = originalSource;
                                    Protocol1_12_2To1_13.LOGGER.warning("Could not handle unknown sound source " + string + " falling back to default: master");
                                }
                                finalSource = Optional.of(SoundSource1_12_2.MASTER);
                            }
                            wrapper.write(Types.VAR_INT, finalSource.get().getId());
                        }
                        if (!originalSound.isEmpty()) {
                            flags = (byte)(flags | 2);
                            wrapper.write(Types.STRING, originalSound);
                        }
                        wrapper.set(Types.BYTE, 0, flags);
                        return;
                    }
                    if (channel.equals("MC|TrList")) {
                        channel = "minecraft:trader_list";
                        ItemPacketRewriter1_13.this.handleTradeList(wrapper);
                    } else {
                        String old = channel;
                        if ((channel = ItemPacketRewriter1_13.getNewPluginChannelId(channel)) == null) {
                            if (!Via.getConfig().isSuppressConversionWarnings()) {
                                String string = old;
                                ((Protocol1_12_2To1_13)ItemPacketRewriter1_13.this.protocol).getLogger().warning("Ignoring clientbound plugin message with channel: " + string);
                            }
                            wrapper.cancel();
                            return;
                        }
                        if (channel.equals("minecraft:register") || channel.equals("minecraft:unregister")) {
                            String[] channels = new String(wrapper.read(Types.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                            ArrayList<String> rewrittenChannels = new ArrayList<String>();
                            for (String s : channels) {
                                String rewritten = ItemPacketRewriter1_13.getNewPluginChannelId(s);
                                if (rewritten != null) {
                                    rewrittenChannels.add(rewritten);
                                    continue;
                                }
                                if (Via.getConfig().isSuppressConversionWarnings()) continue;
                                String string = s;
                                String string2 = Key.stripMinecraftNamespace(channel).toUpperCase(Locale.ROOT);
                                ((Protocol1_12_2To1_13)ItemPacketRewriter1_13.this.protocol).getLogger().warning("Ignoring plugin channel in clientbound " + string2 + ": " + string);
                            }
                            if (!rewrittenChannels.isEmpty()) {
                                wrapper.write(Types.REMAINING_BYTES, Joiner.on((char)'\u0000').join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
                            } else {
                                wrapper.cancel();
                                return;
                            }
                        }
                    }
                    wrapper.set(Types.STRING, 0, channel);
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.SET_EQUIPPED_ITEM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.ITEM1_8, Types.ITEM1_13);
                this.handler(wrapper -> ItemPacketRewriter1_13.this.handleItemToClient(wrapper.user(), wrapper.get(Types.ITEM1_13, 0)));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_13.CONTAINER_CLICK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.VAR_INT);
                this.map(Types.ITEM1_13, Types.ITEM1_8);
                this.handler(wrapper -> ItemPacketRewriter1_13.this.handleItemToServer(wrapper.user(), wrapper.get(Types.ITEM1_8, 0)));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_13.CUSTOM_PAYLOAD, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.handler(wrapper -> {
                    String channel;
                    String old = channel = wrapper.get(Types.STRING, 0);
                    if ((channel = ItemPacketRewriter1_13.getOldPluginChannelId(channel)) == null) {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                            String string = old;
                            ((Protocol1_12_2To1_13)ItemPacketRewriter1_13.this.protocol).getLogger().warning("Ignoring serverbound plugin message with channel: " + string);
                        }
                        wrapper.cancel();
                        return;
                    }
                    if (channel.equals("REGISTER") || channel.equals("UNREGISTER")) {
                        String[] channels = new String(wrapper.read(Types.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                        ArrayList<String> rewrittenChannels = new ArrayList<String>();
                        for (String s : channels) {
                            String rewritten = ItemPacketRewriter1_13.getOldPluginChannelId(s);
                            if (rewritten != null) {
                                rewrittenChannels.add(rewritten);
                                continue;
                            }
                            if (Via.getConfig().isSuppressConversionWarnings()) continue;
                            String string = s;
                            String string2 = channel;
                            ((Protocol1_12_2To1_13)ItemPacketRewriter1_13.this.protocol).getLogger().warning("Ignoring plugin channel in serverbound " + string2 + ": " + string);
                        }
                        wrapper.write(Types.REMAINING_BYTES, Joiner.on((char)'\u0000').join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
                    }
                    wrapper.set(Types.STRING, 0, channel);
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_13.SET_CREATIVE_MODE_SLOT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.SHORT);
                this.map(Types.ITEM1_13, Types.ITEM1_8);
                this.handler(wrapper -> ItemPacketRewriter1_13.this.handleItemToServer(wrapper.user(), wrapper.get(Types.ITEM1_8, 0)));
            }
        });
    }

    @Override
    public Item handleItemToClient(UserConnection connection, Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.tag();
        int originalId = item.identifier() << 16 | item.data() & 0xFFFF;
        int rawId = IdAndData.toRawData(item.identifier(), item.data());
        if (ItemPacketRewriter1_13.isDamageable(item.identifier())) {
            if (tag == null) {
                tag = new CompoundTag();
                item.setTag(tag);
            }
            tag.put("Damage", new IntTag(item.data()));
        }
        if (item.identifier() == 358) {
            if (tag == null) {
                tag = new CompoundTag();
                item.setTag(tag);
            }
            tag.put("map", new IntTag(item.data()));
        }
        if (tag != null) {
            ListTag<?> listTag;
            ListTag<?> canPlaceOnTag;
            Tag idTag;
            ListTag<CompoundTag> storedEnch;
            ListTag<CompoundTag> ench;
            StringTag name;
            CompoundTag display;
            CompoundTag blockEntityTag;
            boolean banner;
            boolean bl = banner = item.identifier() == 425;
            if ((banner || item.identifier() == 442) && (blockEntityTag = tag.getCompoundTag("BlockEntityTag")) != null) {
                ListTag<CompoundTag> patternsTag;
                NumberTag baseTag = blockEntityTag.getNumberTag("Base");
                if (baseTag != null) {
                    if (banner) {
                        rawId = 6800 + baseTag.asInt();
                    }
                    blockEntityTag.putInt("Base", 15 - baseTag.asInt());
                }
                if ((patternsTag = blockEntityTag.getListTag("Patterns", CompoundTag.class)) != null) {
                    for (CompoundTag compoundTag : patternsTag) {
                        NumberTag colorTag = compoundTag.getNumberTag("Color");
                        if (colorTag == null) continue;
                        compoundTag.putInt("Color", 15 - colorTag.asInt());
                    }
                }
            }
            if ((display = tag.getCompoundTag("display")) != null && (name = display.getStringTag("Name")) != null) {
                display.putString(this.nbtTagName("Name"), name.getValue());
                name.setValue(ComponentUtil.legacyToJsonString(name.getValue(), true));
            }
            if ((ench = tag.getListTag("ench", CompoundTag.class)) != null) {
                ListTag<CompoundTag> enchantments = new ListTag<CompoundTag>(CompoundTag.class);
                for (CompoundTag compoundTag : ench) {
                    short oldId = compoundTag.getShort("id", (short)0);
                    CompoundTag enchantmentEntry = new CompoundTag();
                    String newId = (String)Protocol1_12_2To1_13.MAPPINGS.getOldEnchantmentsIds().get((Object)oldId);
                    if (newId == null) {
                        short s = oldId;
                        newId = "viaversion:legacy/" + s;
                    }
                    enchantmentEntry.putString("id", newId);
                    enchantmentEntry.putShort("lvl", compoundTag.getShort("lvl", (short)0));
                    enchantments.add(enchantmentEntry);
                }
                tag.remove("ench");
                tag.put("Enchantments", enchantments);
            }
            if ((storedEnch = tag.getListTag("StoredEnchantments", CompoundTag.class)) != null) {
                ListTag<CompoundTag> newStoredEnch = new ListTag<CompoundTag>(CompoundTag.class);
                for (CompoundTag enchEntry : storedEnch) {
                    idTag = enchEntry.getNumberTag("id");
                    if (idTag == null) continue;
                    CompoundTag enchantmentEntry = new CompoundTag();
                    short oldId = idTag.asShort();
                    String newId = (String)Protocol1_12_2To1_13.MAPPINGS.getOldEnchantmentsIds().get((Object)oldId);
                    if (newId == null) {
                        short s = oldId;
                        newId = "viaversion:legacy/" + s;
                    }
                    enchantmentEntry.putString("id", newId);
                    NumberTag levelTag = enchEntry.getNumberTag("lvl");
                    if (levelTag != null) {
                        enchantmentEntry.putShort("lvl", levelTag.asShort());
                    }
                    newStoredEnch.add(enchantmentEntry);
                }
                tag.put("StoredEnchantments", newStoredEnch);
            }
            if ((canPlaceOnTag = tag.getListTag("CanPlaceOn")) != null) {
                ListTag<StringTag> listTag2 = new ListTag<StringTag>(StringTag.class);
                tag.put(this.nbtTagName("CanPlaceOn"), canPlaceOnTag.copy());
                for (Object oldTag : canPlaceOnTag) {
                    String[] newValues;
                    Object value = oldTag.getValue();
                    String oldId = Key.stripMinecraftNamespace(value.toString());
                    String numberConverted = BlockIdData.numberIdToString.get(Ints.tryParse((String)oldId));
                    if (numberConverted != null) {
                        oldId = numberConverted;
                    }
                    if ((newValues = BlockIdData.blockIdMapping.get(oldId.toLowerCase(Locale.ROOT))) != null) {
                        for (String newValue : newValues) {
                            listTag2.add(new StringTag(newValue));
                        }
                        continue;
                    }
                    listTag2.add(new StringTag(oldId.toLowerCase(Locale.ROOT)));
                }
                tag.put("CanPlaceOn", listTag2);
            }
            if ((listTag = tag.getListTag("CanDestroy")) != null) {
                ListTag<StringTag> newCanDestroy = new ListTag<StringTag>(StringTag.class);
                tag.put(this.nbtTagName("CanDestroy"), listTag.copy());
                for (Tag oldTag : listTag) {
                    String[] newValues;
                    Object value = oldTag.getValue();
                    String oldId = Key.stripMinecraftNamespace(value.toString());
                    String numberConverted = BlockIdData.numberIdToString.get(Ints.tryParse((String)oldId));
                    if (numberConverted != null) {
                        oldId = numberConverted;
                    }
                    if ((newValues = BlockIdData.blockIdMapping.get(oldId.toLowerCase(Locale.ROOT))) != null) {
                        for (String newValue : newValues) {
                            newCanDestroy.add(new StringTag(newValue));
                        }
                        continue;
                    }
                    newCanDestroy.add(new StringTag(oldId.toLowerCase(Locale.ROOT)));
                }
                tag.put("CanDestroy", newCanDestroy);
            }
            if (item.identifier() == 383) {
                CompoundTag entityTag = tag.getCompoundTag("EntityTag");
                if (entityTag != null) {
                    idTag = entityTag.getStringTag("id");
                    if (idTag != null) {
                        rawId = SpawnEggMappings1_13.getSpawnEggId(((StringTag)idTag).getValue());
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
                } else {
                    rawId = 25100288;
                }
            }
            if (tag.isEmpty()) {
                tag = null;
                item.setTag(null);
            }
        }
        if (Protocol1_12_2To1_13.MAPPINGS.getItemMappings().getNewId(rawId) == -1) {
            if (!ItemPacketRewriter1_13.isDamageable(item.identifier()) && item.identifier() != 358) {
                if (tag == null) {
                    tag = new CompoundTag();
                    item.setTag(tag);
                }
                tag.put(this.nbtTagName(), new IntTag(originalId));
            }
            if (item.identifier() == 31 && item.data() == 0) {
                rawId = IdAndData.toRawData(32);
            } else if (Protocol1_12_2To1_13.MAPPINGS.getItemMappings().getNewId(IdAndData.removeData(rawId)) != -1) {
                rawId = IdAndData.removeData(rawId);
            } else {
                if (!Via.getConfig().isSuppressConversionWarnings()) {
                    int n = item.identifier();
                    ((Protocol1_12_2To1_13)this.protocol).getLogger().warning("Failed to get new item for " + n);
                }
                rawId = 16;
            }
        }
        item.setIdentifier(Protocol1_12_2To1_13.MAPPINGS.getItemMappings().getNewId(rawId));
        item.setData((short)0);
        return item;
    }

    public static String getNewPluginChannelId(String old) {
        String string;
        switch (old) {
            case "MC|TrList": {
                string = "minecraft:trader_list";
                break;
            }
            case "MC|Brand": {
                string = "minecraft:brand";
                break;
            }
            case "MC|BOpen": {
                string = "minecraft:book_open";
                break;
            }
            case "MC|DebugPath": {
                string = "minecraft:debug/paths";
                break;
            }
            case "MC|DebugNeighborsUpdate": {
                string = "minecraft:debug/neighbors_update";
                break;
            }
            case "REGISTER": {
                string = "minecraft:register";
                break;
            }
            case "UNREGISTER": {
                string = "minecraft:unregister";
                break;
            }
            case "BungeeCord": {
                string = "bungeecord:main";
                break;
            }
            case "bungeecord:main": {
                string = null;
                break;
            }
            default: {
                String mappedChannel = (String)Protocol1_12_2To1_13.MAPPINGS.getChannelMappings().get((Object)old);
                if (mappedChannel != null) {
                    string = mappedChannel;
                    break;
                }
                string = MappingData1_13.validateNewChannel(old);
                break;
            }
        }
        return string;
    }

    @Override
    public Item handleItemToServer(UserConnection connection, Item item) {
        int oldId;
        NumberTag viaTag;
        if (item == null) {
            return null;
        }
        Integer rawId = null;
        boolean gotRawIdFromTag = false;
        CompoundTag tag = item.tag();
        if (tag != null && (viaTag = tag.getNumberTag(this.nbtTagName())) != null) {
            rawId = viaTag.asInt();
            tag.remove(this.nbtTagName());
            gotRawIdFromTag = true;
        }
        if (rawId == null && (oldId = Protocol1_12_2To1_13.MAPPINGS.getItemMappings().inverse().getNewId(item.identifier())) != -1) {
            Optional<String> eggEntityId = SpawnEggMappings1_13.getEntityId(oldId);
            if (eggEntityId.isPresent()) {
                rawId = 25100288;
                if (tag == null) {
                    tag = new CompoundTag();
                    item.setTag(tag);
                }
                if (!tag.contains("EntityTag")) {
                    CompoundTag entityTag = new CompoundTag();
                    entityTag.put("id", new StringTag(eggEntityId.get()));
                    tag.put("EntityTag", entityTag);
                }
            } else {
                rawId = IdAndData.getId(oldId) << 16 | oldId & 0xF;
            }
        }
        if (rawId == null) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
                int n = item.identifier();
                ((Protocol1_12_2To1_13)this.protocol).getLogger().warning("Failed to get old item for " + n);
            }
            rawId = 65536;
        }
        item.setIdentifier((short)(rawId >> 16));
        item.setData((short)(rawId & 0xFFFF));
        if (tag != null) {
            String[] newValues;
            Object value;
            ListTag<?> old;
            ListTag<CompoundTag> storedEnch;
            ListTag<CompoundTag> enchantments;
            StringTag name;
            CompoundTag display;
            CompoundTag blockEntityTag;
            NumberTag mapTag;
            NumberTag damageTag;
            if (ItemPacketRewriter1_13.isDamageable(item.identifier()) && (damageTag = tag.getNumberTag("Damage")) != null) {
                if (!gotRawIdFromTag) {
                    item.setData(damageTag.asShort());
                }
                tag.remove("Damage");
            }
            if (item.identifier() == 358 && (mapTag = tag.getNumberTag("map")) != null) {
                if (!gotRawIdFromTag) {
                    item.setData(mapTag.asShort());
                }
                tag.remove("map");
            }
            if ((item.identifier() == 442 || item.identifier() == 425) && (blockEntityTag = tag.getCompoundTag("BlockEntityTag")) != null) {
                ListTag<CompoundTag> patternsTag;
                NumberTag baseTag = blockEntityTag.getNumberTag("Base");
                if (baseTag != null) {
                    blockEntityTag.putInt("Base", 15 - baseTag.asInt());
                }
                if ((patternsTag = blockEntityTag.getListTag("Patterns", CompoundTag.class)) != null) {
                    for (CompoundTag compoundTag : patternsTag) {
                        NumberTag colorTag = compoundTag.getNumberTag("Color");
                        compoundTag.putInt("Color", 15 - colorTag.asInt());
                    }
                }
            }
            if ((display = tag.getCompoundTag("display")) != null && (name = display.getStringTag("Name")) != null) {
                Tag via = display.remove(this.nbtTagName("Name"));
                name.setValue(via instanceof StringTag ? (String)via.getValue() : ComponentUtil.jsonToLegacy(name.getValue()));
            }
            if ((enchantments = tag.getListTag("Enchantments", CompoundTag.class)) != null) {
                ListTag<CompoundTag> ench = new ListTag<CompoundTag>(CompoundTag.class);
                for (CompoundTag compoundTag : enchantments) {
                    StringTag idTag = compoundTag.getStringTag("id");
                    if (idTag == null) continue;
                    CompoundTag enchEntry = new CompoundTag();
                    String newId = idTag.getValue();
                    Short oldId2 = (Short)Protocol1_12_2To1_13.MAPPINGS.getOldEnchantmentsIds().inverse().get((Object)newId);
                    if (oldId2 == null && newId.startsWith("viaversion:legacy/")) {
                        oldId2 = Short.valueOf(newId.substring(18));
                    }
                    if (oldId2 == null) continue;
                    enchEntry.putShort("id", oldId2);
                    enchEntry.putShort("lvl", compoundTag.getShort("lvl", (short)0));
                    ench.add(enchEntry);
                }
                tag.remove("Enchantments");
                tag.put("ench", ench);
            }
            if ((storedEnch = tag.getListTag("StoredEnchantments", CompoundTag.class)) != null) {
                ListTag<CompoundTag> newStoredEnch = new ListTag<CompoundTag>(CompoundTag.class);
                for (CompoundTag enchantmentEntry : storedEnch) {
                    StringTag idTag = enchantmentEntry.getStringTag("id");
                    if (idTag == null) continue;
                    CompoundTag enchEntry = new CompoundTag();
                    String newId = idTag.getValue();
                    Short oldId3 = (Short)Protocol1_12_2To1_13.MAPPINGS.getOldEnchantmentsIds().inverse().get((Object)newId);
                    if (oldId3 == null && newId.startsWith("viaversion:legacy/")) {
                        oldId3 = Short.valueOf(newId.substring(18));
                    }
                    if (oldId3 == null) continue;
                    enchEntry.putShort("id", oldId3);
                    NumberTag levelTag = enchantmentEntry.getNumberTag("lvl");
                    if (levelTag != null) {
                        enchEntry.putShort("lvl", levelTag.asShort());
                    }
                    newStoredEnch.add(enchEntry);
                }
                tag.put("StoredEnchantments", newStoredEnch);
            }
            if (tag.getListTag(this.nbtTagName("CanPlaceOn")) != null) {
                tag.put("CanPlaceOn", tag.remove(this.nbtTagName("CanPlaceOn")));
            } else if (tag.getListTag("CanPlaceOn") != null) {
                old = tag.getListTag("CanPlaceOn");
                ListTag<StringTag> listTag = new ListTag<StringTag>(StringTag.class);
                for (Tag oldTag : old) {
                    value = oldTag.getValue();
                    newValues = BlockIdData.fallbackReverseMapping.get(value instanceof String ? Key.stripMinecraftNamespace((String)value) : null);
                    if (newValues != null) {
                        for (String newValue : newValues) {
                            listTag.add(new StringTag(newValue));
                        }
                        continue;
                    }
                    listTag.add(new StringTag(value.toString()));
                }
                tag.put("CanPlaceOn", listTag);
            }
            if (tag.getListTag(this.nbtTagName("CanDestroy")) != null) {
                tag.put("CanDestroy", tag.remove(this.nbtTagName("CanDestroy")));
            } else if (tag.getListTag("CanDestroy") != null) {
                old = tag.getListTag("CanDestroy");
                ListTag<StringTag> listTag = new ListTag<StringTag>(StringTag.class);
                for (Tag oldTag : old) {
                    value = oldTag.getValue();
                    newValues = BlockIdData.fallbackReverseMapping.get(value instanceof String ? Key.stripMinecraftNamespace((String)value) : null);
                    if (newValues != null) {
                        for (String newValue : newValues) {
                            listTag.add(new StringTag(newValue));
                        }
                        continue;
                    }
                    listTag.add(new StringTag(oldTag.getValue().toString()));
                }
                tag.put("CanDestroy", listTag);
            }
        }
        return item;
    }

    public static String getOldPluginChannelId(String newId) {
        String string;
        if ((newId = MappingData1_13.validateNewChannel(newId)) == null) {
            return null;
        }
        switch (newId) {
            case "minecraft:trader_list": {
                string = "MC|TrList";
                break;
            }
            case "minecraft:book_open": {
                string = "MC|BOpen";
                break;
            }
            case "minecraft:debug/paths": {
                string = "MC|DebugPath";
                break;
            }
            case "minecraft:debug/neighbors_update": {
                string = "MC|DebugNeighborsUpdate";
                break;
            }
            case "minecraft:register": {
                string = "REGISTER";
                break;
            }
            case "minecraft:unregister": {
                string = "UNREGISTER";
                break;
            }
            case "minecraft:brand": {
                string = "MC|Brand";
                break;
            }
            case "bungeecord:main": {
                string = "BungeeCord";
                break;
            }
            default: {
                String mappedChannel = (String)Protocol1_12_2To1_13.MAPPINGS.getChannelMappings().inverse().get((Object)newId);
                if (mappedChannel != null) {
                    string = mappedChannel;
                    break;
                }
                if (newId.length() > 20) {
                    string = newId.substring(0, 20);
                    break;
                }
                string = newId;
                break;
            }
        }
        return string;
    }

    public static boolean isDamageable(int id) {
        return id >= 256 && id <= 259 || id == 261 || id >= 267 && id <= 279 || id >= 283 && id <= 286 || id >= 290 && id <= 294 || id >= 298 && id <= 317 || id == 346 || id == 359 || id == 398 || id == 442 || id == 443;
    }
}

