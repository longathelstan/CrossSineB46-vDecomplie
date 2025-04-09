/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.item;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ItemType1_13
extends Type<Item> {
    public ItemType1_13() {
        super(Item.class);
    }

    @Override
    public @Nullable Item read(ByteBuf buffer) {
        short id = buffer.readShort();
        if (id < 0) {
            return null;
        }
        DataItem item = new DataItem();
        item.setIdentifier(id);
        item.setAmount(buffer.readByte());
        item.setTag((CompoundTag)Types.NAMED_COMPOUND_TAG.read(buffer));
        return item;
    }

    @Override
    public void write(ByteBuf buffer, @Nullable Item object) {
        if (object == null) {
            buffer.writeShort(-1);
        } else {
            buffer.writeShort(object.identifier());
            buffer.writeByte(object.amount());
            Types.NAMED_COMPOUND_TAG.write(buffer, object.tag());
        }
    }
}

