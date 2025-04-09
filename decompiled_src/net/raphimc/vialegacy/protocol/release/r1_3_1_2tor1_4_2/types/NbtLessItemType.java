/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types;

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
        byte amount = buffer.readByte();
        short data = buffer.readShort();
        if (id < 0) {
            return null;
        }
        DataItem item = new DataItem();
        item.setIdentifier(id);
        item.setAmount(amount);
        item.setData(data);
        return item;
    }

    @Override
    public void write(ByteBuf buffer, Item item) {
        if (item == null) {
            buffer.writeShort(-1);
            buffer.writeByte(0);
            buffer.writeShort(0);
        } else {
            buffer.writeShort(item.identifier());
            buffer.writeByte(item.amount());
            buffer.writeShort((int)item.data());
        }
    }
}

