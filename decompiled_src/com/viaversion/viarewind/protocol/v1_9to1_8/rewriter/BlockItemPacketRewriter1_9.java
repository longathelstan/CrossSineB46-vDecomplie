/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_9to1_8.rewriter;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.rewriters.LegacyEnchantmentRewriter;
import com.viaversion.viarewind.api.rewriter.VRBlockItemRewriter;
import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viarewind.protocol.v1_9to1_8.data.PotionIdMappings1_8;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.WindowTracker;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.EntityIds1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.PotionIdMappings1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_8;
import com.viaversion.viaversion.util.Key;
import java.util.HashSet;
import java.util.Set;

public class BlockItemPacketRewriter1_9
extends VRBlockItemRewriter<ClientboundPackets1_9, ServerboundPackets1_8, Protocol1_9To1_8> {
    public final Set<String> VALID_ATTRIBUTES = new HashSet<String>();
    LegacyEnchantmentRewriter enchantmentRewriter;

    public BlockItemPacketRewriter1_9(Protocol1_9To1_8 protocol) {
        super(protocol, "1.9");
        this.VALID_ATTRIBUTES.add("generic.maxHealth");
        this.VALID_ATTRIBUTES.add("generic.followRange");
        this.VALID_ATTRIBUTES.add("generic.knockbackResistance");
        this.VALID_ATTRIBUTES.add("generic.movementSpeed");
        this.VALID_ATTRIBUTES.add("generic.attackDamage");
        this.VALID_ATTRIBUTES.add("horse.jumpStrength");
        this.VALID_ATTRIBUTES.add("zombie.spawnReinforcements");
    }

    @Override
    protected void registerPackets() {
        this.registerBlockChange(ClientboundPackets1_9.BLOCK_UPDATE);
        this.registerMultiBlockChange(ClientboundPackets1_9.CHUNK_BLOCKS_UPDATE);
        this.registerSetCreativeModeSlot(ServerboundPackets1_8.SET_CREATIVE_MODE_SLOT);
        ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.CONTAINER_CLOSE, wrapper -> {
            short windowId = wrapper.passthrough(Types.UNSIGNED_BYTE);
            WindowTracker tracker = wrapper.user().get(WindowTracker.class);
            String windowType = tracker.get(windowId);
            if (windowType != null && windowType.equalsIgnoreCase("minecraft:enchanting_table")) {
                tracker.clearEnchantmentProperties();
            }
            tracker.remove(windowId);
        });
        ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.OPEN_SCREEN, wrapper -> {
            short windowId = wrapper.passthrough(Types.UNSIGNED_BYTE);
            String windowType = wrapper.passthrough(Types.STRING);
            wrapper.user().get(WindowTracker.class).put(windowId, windowType);
        });
        ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.CONTAINER_SET_CONTENT, wrapper -> {
            short windowId = wrapper.passthrough(Types.UNSIGNED_BYTE);
            Item[] items = wrapper.read(Types.ITEM1_8_SHORT_ARRAY);
            for (int i = 0; i < items.length; ++i) {
                items[i] = this.handleItemToClient(wrapper.user(), items[i]);
            }
            if (windowId == 0 && items.length == 46) {
                Item[] old = items;
                items = new Item[45];
                System.arraycopy(old, 0, items, 0, 45);
            } else {
                String type = wrapper.user().get(WindowTracker.class).get(windowId);
                if (type != null && type.equalsIgnoreCase("minecraft:brewing_stand")) {
                    System.arraycopy(items, 0, wrapper.user().get(WindowTracker.class).getBrewingItems(windowId), 0, 4);
                    WindowTracker.updateBrewingStand(wrapper.user(), items[4], windowId);
                    Item[] old = items;
                    items = new Item[old.length - 1];
                    System.arraycopy(old, 0, items, 0, 4);
                    System.arraycopy(old, 5, items, 4, old.length - 5);
                }
            }
            wrapper.write(Types.ITEM1_8_SHORT_ARRAY, items);
        });
        ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.CONTAINER_SET_SLOT, wrapper -> {
            byte windowId = wrapper.passthrough(Types.UNSIGNED_BYTE).byteValue();
            short slot = wrapper.passthrough(Types.SHORT);
            Item item = wrapper.passthrough(Types.ITEM1_8);
            this.handleItemToClient(wrapper.user(), item);
            if (windowId == 0 && slot == 45) {
                wrapper.cancel();
                return;
            }
            WindowTracker windowTracker = wrapper.user().get(WindowTracker.class);
            String windowType = windowTracker.get(windowId);
            if (windowType != null && windowType.equalsIgnoreCase("minecraft:brewing_stand")) {
                if (slot > 4) {
                    wrapper.set(Types.SHORT, 0, (short)(slot - 1));
                } else if (slot == 4) {
                    wrapper.cancel();
                    WindowTracker.updateBrewingStand(wrapper.user(), wrapper.get(Types.ITEM1_8, 0), windowId);
                } else {
                    windowTracker.getBrewingItems((short)((short)windowId))[slot] = wrapper.get(Types.ITEM1_8, 0);
                }
            }
        });
        ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.CONTAINER_CLOSE, wrapper -> {
            short windowId = wrapper.passthrough(Types.UNSIGNED_BYTE);
            wrapper.user().get(WindowTracker.class).remove(windowId);
        });
        ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.CONTAINER_CLICK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE, Types.VAR_INT);
                this.map(Types.ITEM1_8);
                this.handler(wrapper -> BlockItemPacketRewriter1_9.this.handleItemToServer(wrapper.user(), wrapper.get(Types.ITEM1_8, 0)));
                this.handler(wrapper -> {
                    short slot;
                    short windowId = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    String windowType = wrapper.user().get(WindowTracker.class).get(windowId);
                    if (windowType != null && windowType.equalsIgnoreCase("minecraft:brewing_stand") && (slot = wrapper.get(Types.SHORT, 0).shortValue()) > 3) {
                        wrapper.set(Types.SHORT, 0, (short)(slot + 1));
                    }
                });
            }
        });
        ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.CONTAINER_SET_DATA, wrapper -> {
            short windowId = wrapper.passthrough(Types.UNSIGNED_BYTE);
            short key = wrapper.read(Types.SHORT);
            short value = wrapper.read(Types.SHORT);
            WindowTracker tracker = wrapper.user().get(WindowTracker.class);
            String windowType = tracker.get(windowId);
            if (windowType != null && windowType.equalsIgnoreCase("minecraft:enchanting_table")) {
                if (key >= 4 && key <= 6) {
                    tracker.putEnchantmentProperty(key, value);
                    wrapper.cancel();
                } else if (key >= 7 && key <= 9) {
                    key = (short)(key - 3);
                    short property = tracker.getEnchantmentValue(key);
                    value = (short)(property | value << 8);
                }
            }
            wrapper.write(Types.SHORT, key);
            wrapper.write(Types.SHORT, value);
        });
    }

    @Override
    protected void registerRewrites() {
        this.enchantmentRewriter = new LegacyEnchantmentRewriter(this.nbtTagName());
        this.enchantmentRewriter.registerEnchantment(9, "\u00a77Frost Walker");
        this.enchantmentRewriter.registerEnchantment(70, "\u00a77Mending");
    }

    @Override
    public Item handleItemToClient(UserConnection connection, Item item) {
        ListTag<CompoundTag> attributeModifiers;
        boolean lingeringPotion;
        CompoundTag displayTag;
        if (item == null) {
            return null;
        }
        super.handleItemToClient(connection, item);
        CompoundTag tag = item.tag();
        this.enchantmentRewriter.handleToClient(item);
        CompoundTag compoundTag = displayTag = tag == null ? null : tag.getCompoundTag("display");
        if (item.data() != 0) {
            ByteTag unbreakableTag;
            ByteTag byteTag = unbreakableTag = tag == null ? null : tag.getByteTag("Unbreakable");
            if (unbreakableTag != null && unbreakableTag.asByte() != 0) {
                ListTag<StringTag> loreTag;
                String string = this.nbtTagName();
                tag.put(string + "|Unbreakable", new ByteTag(unbreakableTag.asByte()));
                tag.remove("Unbreakable");
                if (displayTag == null) {
                    displayTag = new CompoundTag();
                    tag.put("display", displayTag);
                    String string2 = this.nbtTagName();
                    tag.put(string2 + "|noDisplay", new ByteTag(true));
                }
                if ((loreTag = displayTag.getListTag("Lore", StringTag.class)) == null) {
                    loreTag = new ListTag<StringTag>(StringTag.class);
                    displayTag.put("Lore", loreTag);
                }
                loreTag.add(new StringTag("\u00a79Unbreakable"));
            }
        }
        if (item.identifier() == 383 && item.data() == 0) {
            StringTag idTag;
            CompoundTag entityTag;
            int data = 0;
            CompoundTag compoundTag2 = entityTag = tag == null ? null : tag.getCompoundTag("EntityTag");
            if (entityTag != null && (idTag = entityTag.getStringTag("id")) != null) {
                String id = idTag.getValue();
                if (EntityIds1_8.ENTITY_NAME_TO_ID.containsKey(id)) {
                    data = EntityIds1_8.ENTITY_NAME_TO_ID.get(id);
                } else if (displayTag == null) {
                    displayTag = new CompoundTag();
                    tag.put("display", displayTag);
                    String string = this.nbtTagName();
                    tag.put(string + "|noDisplay", new ByteTag(true));
                    String string3 = id;
                    displayTag.put("Name", new StringTag("\u00a7rSpawn " + string3));
                }
            }
            item.setData((short)data);
        }
        boolean potion = item.identifier() == 373;
        boolean splashPotion = item.identifier() == 438;
        boolean bl = lingeringPotion = item.identifier() == 441;
        if (potion || splashPotion || lingeringPotion) {
            StringTag potionTag;
            int data = 0;
            StringTag stringTag = potionTag = tag == null ? null : tag.getStringTag("Potion");
            if (potionTag != null) {
                String potionName = Key.stripMinecraftNamespace(potionTag.getValue());
                if (PotionIdMappings1_8.POTION_NAME_TO_ID.containsKey(potionName)) {
                    data = PotionIdMappings1_8.POTION_NAME_TO_ID.get(potionName);
                }
                if (splashPotion) {
                    String string = potionName;
                    potionName = string + "_splash";
                } else if (lingeringPotion) {
                    String string = potionName;
                    potionName = string + "_lingering";
                }
                if ((displayTag == null || !displayTag.contains("Name")) && PotionIdMappings1_8.POTION_NAME_INDEX.containsKey(potionName)) {
                    displayTag = new CompoundTag();
                    tag.put("display", displayTag);
                    String string = this.nbtTagName();
                    tag.put(string + "|noDisplay", new ByteTag(true));
                    displayTag.put("Name", new StringTag(PotionIdMappings1_8.POTION_NAME_INDEX.get(potionName)));
                }
            }
            if (splashPotion || lingeringPotion) {
                item.setIdentifier(373);
                data += 8192;
            }
            item.setData((short)data);
        }
        ListTag<CompoundTag> listTag = attributeModifiers = tag == null ? null : tag.getListTag("AttributeModifiers", CompoundTag.class);
        if (attributeModifiers != null) {
            String string = this.nbtTagName();
            tag.put(string + "|AttributeModifiers", attributeModifiers.copy());
            attributeModifiers.getValue().removeIf(entries -> {
                StringTag nameTag = entries.getStringTag("AttributeName");
                return nameTag != null && !this.VALID_ATTRIBUTES.contains(nameTag.getValue());
            });
        }
        return item;
    }

    @Override
    public Item handleItemToServer(UserConnection connection, Item item) {
        String string;
        Tag attributeModifiersTag;
        String string2;
        Tag unbreakableTag;
        if (item == null) {
            return null;
        }
        super.handleItemToServer(connection, item);
        CompoundTag tag = item.tag();
        this.enchantmentRewriter.handleToServer(item);
        if (item.identifier() == 383 && item.data() != 0) {
            if ((tag == null || !tag.contains("EntityTag")) && EntityIds1_8.ENTITY_ID_TO_NAME.containsKey(item.data())) {
                if (tag == null) {
                    tag = new CompoundTag();
                    item.setTag(tag);
                }
                CompoundTag entityTag = new CompoundTag();
                entityTag.put("id", new StringTag(EntityIds1_8.ENTITY_ID_TO_NAME.get(item.data())));
                tag.put("EntityTag", entityTag);
            }
            item.setData((short)0);
        }
        if (!(item.identifier() != 373 || tag != null && tag.contains("Potion"))) {
            if (item.data() >= 16384) {
                item.setIdentifier(438);
                item.setData((short)(item.data() - 8192));
            }
            if (tag == null) {
                String name;
                tag = new CompoundTag();
                item.setTag(tag);
                String string3 = name = item.data() == 8192 ? "water" : PotionIdMappings1_9.potionNameFromDamage(item.data());
                tag.put("Potion", new StringTag("minecraft:" + string3));
            }
            item.setData((short)0);
        }
        if (tag == null) {
            return item;
        }
        String string4 = this.nbtTagName();
        Tag noDisplayTag = tag.remove(string4 + "|noDisplay");
        if (noDisplayTag != null) {
            tag.remove("display");
            if (tag.isEmpty()) {
                item.setTag(null);
            }
        }
        if ((unbreakableTag = tag.remove((string2 = this.nbtTagName()) + "|Unbreakable")) != null) {
            tag.put("Unbreakable", unbreakableTag);
        }
        if ((attributeModifiersTag = tag.remove((string = this.nbtTagName()) + "|AttributeModifiers")) != null) {
            tag.put("AttributeModifiers", attributeModifiersTag);
        }
        return item;
    }
}

