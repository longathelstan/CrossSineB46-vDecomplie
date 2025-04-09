/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.api.type.item;

import com.viaversion.viarewind.api.type.RewindTypes;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class ItemArrayType
extends Type<Item[]> {
    public ItemArrayType() {
        super(Item[].class);
    }

    @Override
    public Item[] read(ByteBuf buffer) {
        int amount = Types.SHORT.readPrimitive(buffer);
        Item[] items = new Item[amount];
        for (int i = 0; i < amount; ++i) {
            items[i] = (Item)RewindTypes.COMPRESSED_NBT_ITEM.read(buffer);
        }
        return items;
    }

    @Override
    public void write(ByteBuf buffer, Item[] items) {
        Types.SHORT.writePrimitive(buffer, (short)items.length);
        for (Item item : items) {
            RewindTypes.COMPRESSED_NBT_ITEM.write(buffer, item);
        }
    }
}

