/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.types;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.NbtItemList1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

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
        if (NbtItemList1_2_4.hasNbt(id)) {
            item.setTag((CompoundTag)Types1_7_6.NBT.read(buffer));
        }
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
            if (NbtItemList1_2_4.hasNbt(item.identifier())) {
                Types1_7_6.NBT.write(buffer, item.tag());
            }
        }
    }
}

