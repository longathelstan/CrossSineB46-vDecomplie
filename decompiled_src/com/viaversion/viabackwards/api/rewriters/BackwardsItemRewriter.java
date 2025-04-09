/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.MappedItem;
import com.viaversion.viabackwards.api.rewriters.BackwardsItemRewriterBase;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BackwardsItemRewriter<C extends ClientboundPacketType, S extends ServerboundPacketType, T extends BackwardsProtocol<C, ?, ?, S>>
extends BackwardsItemRewriterBase<C, S, T> {
    public BackwardsItemRewriter(T protocol, Type<Item> itemType, Type<Item[]> itemArrayType) {
        super(protocol, itemType, itemArrayType, true);
    }

    public BackwardsItemRewriter(T protocol, Type<Item> itemType, Type<Item[]> itemArrayType, Type<Item> mappedItemType, Type<Item[]> mappedItemArrayType) {
        super(protocol, itemType, itemArrayType, mappedItemType, mappedItemArrayType, true);
    }

    @Override
    public @Nullable Item handleItemToClient(UserConnection connection, @Nullable Item item) {
        MappedItem data;
        CompoundTag display;
        if (item == null) {
            return null;
        }
        CompoundTag compoundTag = display = item.tag() != null ? item.tag().getCompoundTag("display") : null;
        if (((BackwardsProtocol)this.protocol).getComponentRewriter() != null && display != null) {
            ListTag<StringTag> lore;
            StringTag name = display.getStringTag("Name");
            if (name != null) {
                String newValue = ((ComponentRewriter)((BackwardsProtocol)this.protocol).getComponentRewriter()).processText(connection, name.getValue()).toString();
                if (!newValue.equals(name.getValue())) {
                    this.saveStringTag(display, name, "Name");
                }
                name.setValue(newValue);
            }
            if ((lore = display.getListTag("Lore", StringTag.class)) != null) {
                boolean changed = false;
                for (StringTag loreEntry : lore) {
                    String newValue = ((ComponentRewriter)((BackwardsProtocol)this.protocol).getComponentRewriter()).processText(connection, loreEntry.getValue()).toString();
                    if (!changed && !newValue.equals(loreEntry.getValue())) {
                        changed = true;
                        this.saveListTag(display, lore, "Lore");
                    }
                    loreEntry.setValue(newValue);
                }
            }
        }
        MappedItem mappedItem = data = ((BackwardsProtocol)this.protocol).getMappingData() != null ? ((BackwardsProtocol)this.protocol).getMappingData().getMappedItem(item.identifier()) : null;
        if (data == null) {
            return super.handleItemToClient(connection, item);
        }
        if (item.tag() == null) {
            item.setTag(new CompoundTag());
        }
        item.tag().putInt(this.nbtTagName("id"), item.identifier());
        item.setIdentifier(data.id());
        if (data.customModelData() != null && !item.tag().contains("CustomModelData")) {
            item.tag().putInt("CustomModelData", data.customModelData());
        }
        if (display == null) {
            display = new CompoundTag();
            item.tag().put("display", display);
        }
        if (!display.contains("Name")) {
            display.put("Name", new StringTag(data.jsonName()));
            display.put(this.nbtTagName("customName"), new ByteTag(false));
        }
        return item;
    }

    @Override
    public @Nullable Item handleItemToServer(UserConnection connection, @Nullable Item item) {
        Tag originalId;
        if (item == null) {
            return null;
        }
        super.handleItemToServer(connection, item);
        if (item.tag() != null && (originalId = item.tag().remove(this.nbtTagName("id"))) instanceof IntTag) {
            item.setIdentifier(((NumberTag)originalId).asInt());
        }
        return item;
    }
}

