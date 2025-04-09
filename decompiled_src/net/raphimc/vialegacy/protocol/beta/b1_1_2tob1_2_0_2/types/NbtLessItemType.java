/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.types;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class NbtLessItemType
extends Type<Item> {
    public NbtLessItemType() {
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
        item.setData(buffer.readByte());
        return item;
    }

    @Override
    public void write(ByteBuf buffer, Item item) {
        if (item == null) {
            buffer.writeShort(-1);
        } else {
            buffer.writeShort(item.identifier());
            buffer.writeByte(item.amount());
            buffer.writeByte((int)item.data());
        }
    }
}

