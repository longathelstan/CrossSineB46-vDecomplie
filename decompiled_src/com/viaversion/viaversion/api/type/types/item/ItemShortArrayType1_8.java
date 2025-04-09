/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.item;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.item.BaseItemArrayType;
import io.netty.buffer.ByteBuf;

public class ItemShortArrayType1_8
extends BaseItemArrayType {
    @Override
    public Item[] read(ByteBuf buffer) {
        int amount = Types.SHORT.readPrimitive(buffer);
        Item[] array = new Item[amount];
        for (int i = 0; i < amount; ++i) {
            array[i] = (Item)Types.ITEM1_8.read(buffer);
        }
        return array;
    }

    @Override
    public void write(ByteBuf buffer, Item[] object) {
        Types.SHORT.writePrimitive(buffer, (short)object.length);
        for (Item o : object) {
            Types.ITEM1_8.write(buffer, o);
        }
    }
}

