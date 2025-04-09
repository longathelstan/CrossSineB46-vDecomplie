/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.data.MappedItem;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.StructuredItemRewriter;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BackwardsStructuredItemRewriter<C extends ClientboundPacketType, S extends ServerboundPacketType, T extends BackwardsProtocol<C, ?, ?, S>>
extends StructuredItemRewriter<C, S, T> {
    public BackwardsStructuredItemRewriter(T protocol, Type<Item> itemType, Type<Item[]> itemArrayType, Type<Item> mappedItemType, Type<Item[]> mappedItemArrayType, Type<Item> itemCostType, Type<Item> optionalItemCostType, Type<Item> mappedItemCostType, Type<Item> mappedOptionalItemCostType, Type<Particle> particleType, Type<Particle> mappedParticleType) {
        super(protocol, itemType, itemArrayType, mappedItemType, mappedItemArrayType, itemCostType, optionalItemCostType, mappedItemCostType, mappedOptionalItemCostType, particleType, mappedParticleType);
    }

    public BackwardsStructuredItemRewriter(T protocol, Type<Item> itemType, Type<Item[]> itemArrayType, Type<Item> mappedItemType, Type<Item[]> mappedItemArrayType) {
        super(protocol, itemType, itemArrayType, mappedItemType, mappedItemArrayType);
    }

    public BackwardsStructuredItemRewriter(T protocol, Type<Item> itemType, Type<Item[]> itemArrayType) {
        super(protocol, itemType, itemArrayType);
    }

    @Override
    public Item handleItemToClient(UserConnection connection, Item item) {
        MappedItem mappedItem;
        if (item.isEmpty()) {
            return item;
        }
        StructuredDataContainer dataContainer = item.dataContainer();
        this.updateItemDataComponentTypeIds(dataContainer, true);
        BackwardsMappingData mappingData = ((BackwardsProtocol)this.protocol).getMappingData();
        MappedItem mappedItem2 = mappedItem = mappingData != null ? mappingData.getMappedItem(item.identifier()) : null;
        if (mappedItem == null) {
            if (mappingData != null && mappingData.getItemMappings() != null) {
                item.setIdentifier(mappingData.getNewItemId(item.identifier()));
            }
            this.updateItemDataComponents(connection, item, true);
            return item;
        }
        CompoundTag tag = this.createCustomTag(item);
        tag.putInt(this.nbtTagName("id"), item.identifier());
        item.setIdentifier(mappedItem.id());
        if (mappedItem.customModelData() != null && !dataContainer.has(StructuredDataKey.CUSTOM_MODEL_DATA)) {
            dataContainer.set(StructuredDataKey.CUSTOM_MODEL_DATA, mappedItem.customModelData());
        }
        if (!dataContainer.has(StructuredDataKey.CUSTOM_NAME)) {
            dataContainer.set(StructuredDataKey.CUSTOM_NAME, mappedItem.tagName());
            tag.putBoolean(this.nbtTagName("added_custom_name"), true);
        }
        this.updateItemDataComponents(connection, item, true);
        return item;
    }

    @Override
    public Item handleItemToServer(UserConnection connection, Item item) {
        Tag tag;
        CompoundTag customData;
        if (item.isEmpty()) {
            return item;
        }
        StructuredDataContainer dataContainer = item.dataContainer();
        this.updateItemDataComponentTypeIds(dataContainer, false);
        BackwardsMappingData mappingData = ((BackwardsProtocol)this.protocol).getMappingData();
        if (mappingData != null && mappingData.getItemMappings() != null) {
            item.setIdentifier(mappingData.getOldItemId(item.identifier()));
        }
        if ((customData = dataContainer.get(StructuredDataKey.CUSTOM_DATA)) != null && (tag = customData.remove(this.nbtTagName("id"))) instanceof IntTag) {
            IntTag originalTag = (IntTag)tag;
            item.setIdentifier(originalTag.asInt());
        }
        this.restoreTextComponents(item);
        this.updateItemDataComponents(connection, item, false);
        return item;
    }

    protected void saveListTag(CompoundTag tag, ListTag<?> original, String name) {
        String backupName = this.nbtTagName(name);
        if (!tag.contains(backupName)) {
            tag.put(backupName, original.copy());
        }
    }

    public <T extends Tag> @Nullable ListTag<T> removeListTag(CompoundTag tag, String tagName, Class<T> tagType) {
        String backupName = this.nbtTagName(tagName);
        ListTag<T> data = tag.getListTag(backupName, tagType);
        if (data == null) {
            return null;
        }
        tag.remove(backupName);
        return data;
    }

    protected void saveGenericTagList(CompoundTag tag, List<Tag> original, String name) {
        String backupName = this.nbtTagName(name);
        if (!tag.contains(backupName)) {
            CompoundTag output = new CompoundTag();
            for (int i = 0; i < original.size(); ++i) {
                output.put(Integer.toString(i), original.get(i));
            }
            tag.put(backupName, output);
        }
    }

    protected List<Tag> removeGenericTagList(CompoundTag tag, String name) {
        String backupName = this.nbtTagName(name);
        CompoundTag data = tag.getCompoundTag(backupName);
        if (data == null) {
            return null;
        }
        tag.remove(backupName);
        return new ArrayList<Tag>(data.values());
    }

    @Override
    public String nbtTagName() {
        String string = ((BackwardsProtocol)this.protocol).getClass().getSimpleName();
        return "VB|" + string;
    }
}

