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

public class ItemType1_20_2
extends Type<Item> {
    public ItemType1_20_2() {
        super(Item.class);
    }

    @Override
    public @Nullable Item read(ByteBuf buffer) {
        if (!buffer.readBoolean()) {
            return null;
        }
        DataItem item = new DataItem();
        item.setIdentifier(Types.VAR_INT.readPrimitive(buffer));
        item.setAmount(buffer.readByte());
        item.setTag((CompoundTag)Types.COMPOUND_TAG.read(buffer));
        return item;
    }

    @Override
    public void write(ByteBuf buffer, @Nullable Item object) {
        if (object == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            Types.VAR_INT.writePrimitive(buffer, object.identifier());
            buffer.writeByte(object.amount());
            Types.COMPOUND_TAG.write(buffer, object.tag());
        }
    }
}

