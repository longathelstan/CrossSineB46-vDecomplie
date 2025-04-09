/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.data.StructuredData;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.data.FilterableComponent;
import com.viaversion.viaversion.api.minecraft.item.data.Instrument;
import com.viaversion.viaversion.api.minecraft.item.data.WrittenBook;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class StructuredItemRewriter<C extends ClientboundPacketType, S extends ServerboundPacketType, T extends Protocol<C, ?, ?, S>>
extends ItemRewriter<C, S, T> {
    public StructuredItemRewriter(T protocol, Type<Item> itemType, Type<Item[]> itemArrayType, Type<Item> mappedItemType, Type<Item[]> mappedItemArrayType, Type<Item> itemCostType, Type<Item> optionalItemCostType, Type<Item> mappedItemCostType, Type<Item> mappedOptionalItemCostType, Type<Particle> particleType, Type<Particle> mappedParticleType) {
        super(protocol, itemType, itemArrayType, mappedItemType, mappedItemArrayType, itemCostType, optionalItemCostType, mappedItemCostType, mappedOptionalItemCostType, particleType, mappedParticleType);
    }

    public StructuredItemRewriter(T protocol, Type<Item> itemType, Type<Item[]> itemArrayType, Type<Item> mappedItemType, Type<Item[]> mappedItemArrayType) {
        super(protocol, itemType, itemArrayType, mappedItemType, mappedItemArrayType);
    }

    public StructuredItemRewriter(T protocol, Type<Item> itemType, Type<Item[]> itemArrayType) {
        super(protocol, itemType, itemArrayType, itemType, itemArrayType);
    }

    @Override
    public Item handleItemToClient(UserConnection connection, Item item) {
        if (item.isEmpty()) {
            return item;
        }
        MappingData mappingData = this.protocol.getMappingData();
        if (mappingData != null && mappingData.getItemMappings() != null) {
            item.setIdentifier(mappingData.getNewItemId(item.identifier()));
        }
        this.updateItemDataComponentTypeIds(item.dataContainer(), true);
        this.updateItemDataComponents(connection, item, true);
        return item;
    }

    @Override
    public Item handleItemToServer(UserConnection connection, Item item) {
        if (item.isEmpty()) {
            return item;
        }
        MappingData mappingData = this.protocol.getMappingData();
        if (mappingData != null && mappingData.getItemMappings() != null) {
            item.setIdentifier(mappingData.getOldItemId(item.identifier()));
        }
        this.updateItemDataComponentTypeIds(item.dataContainer(), false);
        this.updateItemDataComponents(connection, item, false);
        this.restoreTextComponents(item);
        return item;
    }

    protected void updateItemDataComponentTypeIds(StructuredDataContainer container, boolean mappedNames) {
        MappingData mappingData = this.protocol.getMappingData();
        if (mappingData == null) {
            return;
        }
        FullMappings dataComponentMappings = mappingData.getDataComponentSerializerMappings();
        if (dataComponentMappings == null) {
            return;
        }
        if (!mappedNames) {
            dataComponentMappings = dataComponentMappings.inverse();
        }
        container.setIdLookup(this.protocol, mappedNames);
        container.updateIds(this.protocol, dataComponentMappings::getNewId);
    }

    protected void updateItemDataComponents(UserConnection connection, Item item, boolean clientbound) {
        StructuredDataContainer container = item.dataContainer();
        MappingData mappingData = this.protocol.getMappingData();
        if (mappingData.getItemMappings() != null) {
            Int2IntFunction itemIdRewriter = clientbound ? mappingData::getNewItemId : mappingData::getOldItemId;
            container.replace(StructuredDataKey.TRIM, value -> value.rewrite(itemIdRewriter));
            container.replace(StructuredDataKey.POT_DECORATIONS, value -> value.rewrite(itemIdRewriter));
        }
        if (mappingData.getBlockMappings() != null) {
            Int2IntFunction blockIdRewriter = clientbound ? mappingData::getNewBlockId : mappingData::getOldBlockId;
            container.replace(StructuredDataKey.TOOL, value -> value.rewrite(blockIdRewriter));
            container.replace(StructuredDataKey.CAN_PLACE_ON, value -> value.rewrite(blockIdRewriter));
            container.replace(StructuredDataKey.CAN_BREAK, value -> value.rewrite(blockIdRewriter));
        }
        if (mappingData.getSoundMappings() != null) {
            Int2IntFunction soundIdRewriter = clientbound ? mappingData::getNewSoundId : mappingData::getOldSoundId;
            container.replace(StructuredDataKey.INSTRUMENT, value -> value.isDirect() ? Holder.of(((Instrument)value.value()).rewrite(soundIdRewriter)) : value);
            container.replace(StructuredDataKey.JUKEBOX_PLAYABLE, value -> value.rewrite(soundIdRewriter));
        }
        if (clientbound && this.protocol.getComponentRewriter() != null) {
            WrittenBook book;
            this.updateComponent(connection, item, StructuredDataKey.ITEM_NAME, "item_name");
            this.updateComponent(connection, item, StructuredDataKey.CUSTOM_NAME, "custom_name");
            Tag[] lore = container.get(StructuredDataKey.LORE);
            if (lore != null) {
                for (Tag tag : lore) {
                    this.protocol.getComponentRewriter().processTag(connection, tag);
                }
            }
            if ((book = container.get(StructuredDataKey.WRITTEN_BOOK_CONTENT)) != null) {
                for (FilterableComponent page : book.pages()) {
                    this.protocol.getComponentRewriter().processTag(connection, (Tag)page.raw());
                    if (!page.isFiltered()) continue;
                    this.protocol.getComponentRewriter().processTag(connection, (Tag)page.filtered());
                }
            }
        }
        ItemHandler itemHandler = clientbound ? this::handleItemToClient : this::handleItemToServer;
        for (Map.Entry<StructuredDataKey<?>, StructuredData<?>> entry : container.data().entrySet()) {
            StructuredData<?> data = entry.getValue();
            if (data.isEmpty()) continue;
            StructuredDataKey<?> key = entry.getKey();
            Class<?> outputClass = key.type().getOutputClass();
            if (outputClass == Item.class) {
                StructuredData<?> itemData = data;
                itemData.setValue(itemHandler.rewrite(connection, (Item)itemData.value()));
                continue;
            }
            if (outputClass != Item[].class) continue;
            StructuredData<?> itemArrayData = data;
            Item[] items = (Item[])itemArrayData.value();
            for (int i = 0; i < items.length; ++i) {
                items[i] = itemHandler.rewrite(connection, items[i]);
            }
        }
    }

    protected void updateComponent(UserConnection connection, Item item, StructuredDataKey<Tag> key, String backupKey) {
        Tag name = item.dataContainer().get(key);
        if (name == null) {
            return;
        }
        Tag originalName = name.copy();
        this.protocol.getComponentRewriter().processTag(connection, name);
        if (!name.equals(originalName)) {
            this.saveTag(this.createCustomTag(item), originalName, backupKey);
        }
    }

    protected void restoreTextComponents(Item item) {
        StructuredDataContainer data = item.dataContainer();
        CompoundTag customData = data.get(StructuredDataKey.CUSTOM_DATA);
        if (customData == null) {
            return;
        }
        if (customData.remove(this.nbtTagName("added_custom_name")) != null) {
            data.remove(StructuredDataKey.CUSTOM_NAME);
        } else {
            Tag itemName;
            Tag customName2 = this.removeBackupTag(customData, "custom_name");
            if (customName2 != null) {
                data.set(StructuredDataKey.CUSTOM_NAME, customName2);
            }
            if ((itemName = this.removeBackupTag(customData, "item_name")) != null) {
                data.set(StructuredDataKey.ITEM_NAME, itemName);
            }
        }
    }

    protected CompoundTag createCustomTag(Item item) {
        StructuredDataContainer data = item.dataContainer();
        CompoundTag customData = data.get(StructuredDataKey.CUSTOM_DATA);
        if (customData == null) {
            customData = new CompoundTag();
            data.set(StructuredDataKey.CUSTOM_DATA, customData);
        }
        return customData;
    }

    protected void saveTag(CompoundTag customData, Tag tag, String name) {
        String backupName = this.nbtTagName(name);
        if (!customData.contains(backupName)) {
            customData.put(backupName, tag);
        }
    }

    protected @Nullable Tag removeBackupTag(CompoundTag customData, String tagName) {
        return customData.remove(this.nbtTagName(tagName));
    }

    @FunctionalInterface
    private static interface ItemHandler {
        public Item rewrite(UserConnection var1, Item var2);
    }
}

