/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.api.type.item;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viarewind.api.type.RewindTypes;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class ItemType
extends Type<Item> {
    public ItemType() {
        super(Item.class);
    }

    @Override
    public Item read(ByteBuf buffer) {
        short id = buffer.readShort();
        if (id < 0) {
            return null;
        }
        DataItem item = new DataItem();
        item.setIdentifier(id);
        item.setAmount(buffer.readByte());
        item.setData(buffer.readShort());
        item.setTag((CompoundTag)RewindTypes.COMPRESSED_NBT.read(buffer));
        return item;
    }

    @Override
    public void write(ByteBuf buffer, Item item) {
        if (item == null) {
            buffer.writeShort(-1);
        } else {
            buffer.writeShort(item.identifier());
            buffer.writeByte(item.amount());
            buffer.writeShort((int)item.data());
            RewindTypes.COMPRESSED_NBT.write(buffer, item.tag());
        }
    }
}

