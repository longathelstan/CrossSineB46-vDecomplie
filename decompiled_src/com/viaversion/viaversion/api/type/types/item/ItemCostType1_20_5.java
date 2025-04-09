/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.item;

import com.viaversion.viaversion.api.minecraft.data.StructuredData;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.StructuredItem;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public final class ItemCostType1_20_5
extends Type<Item> {
    static final StructuredData<?>[] EMPTY_DATA_ARRAY = new StructuredData[0];
    final Type<StructuredData<?>[]> dataArrayType;

    public ItemCostType1_20_5(Type<StructuredData<?>[]> dataArrayType) {
        super(Item.class);
        this.dataArrayType = dataArrayType;
    }

    @Override
    public Item read(ByteBuf buffer) {
        int id = Types.VAR_INT.readPrimitive(buffer);
        int amount = Types.VAR_INT.readPrimitive(buffer);
        StructuredData[] dataArray = (StructuredData[])this.dataArrayType.read(buffer);
        return new StructuredItem(id, amount, new StructuredDataContainer(dataArray));
    }

    @Override
    public void write(ByteBuf buffer, Item object) {
        Types.VAR_INT.writePrimitive(buffer, object.identifier());
        Types.VAR_INT.writePrimitive(buffer, object.amount());
        this.dataArrayType.write(buffer, object.dataContainer().data().values().toArray(EMPTY_DATA_ARRAY));
    }

    public static final class OptionalItemCostType
    extends OptionalType<Item> {
        public OptionalItemCostType(Type<Item> type) {
            super(type);
        }
    }
}

