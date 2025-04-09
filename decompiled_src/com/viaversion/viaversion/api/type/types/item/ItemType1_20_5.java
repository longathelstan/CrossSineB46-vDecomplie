/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.item;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.data.StructuredData;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.StructuredItem;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.item.StructuredDataType;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectOpenHashMap;
import io.netty.buffer.ByteBuf;
import java.util.Map;

public class ItemType1_20_5
extends Type<Item> {
    final StructuredDataType dataType;

    public ItemType1_20_5(StructuredDataType dataType) {
        super(Item.class);
        this.dataType = dataType;
    }

    @Override
    public Item read(ByteBuf buffer) {
        int amount = Types.VAR_INT.readPrimitive(buffer);
        if (amount <= 0) {
            return StructuredItem.empty();
        }
        int id = Types.VAR_INT.readPrimitive(buffer);
        Map<StructuredDataKey<?>, StructuredData<?>> data = this.readData(buffer);
        return new StructuredItem(id, amount, new StructuredDataContainer(data));
    }

    Map<StructuredDataKey<?>, StructuredData<?>> readData(ByteBuf buffer) {
        StructuredDataKey<?> key;
        int i;
        int valuesSize = Types.VAR_INT.readPrimitive(buffer);
        int markersSize = Types.VAR_INT.readPrimitive(buffer);
        if (valuesSize == 0 && markersSize == 0) {
            return new Reference2ObjectOpenHashMap();
        }
        Reference2ObjectOpenHashMap map = new Reference2ObjectOpenHashMap();
        for (i = 0; i < valuesSize; ++i) {
            Object value = this.dataType.read(buffer);
            key = this.dataType.key(value.id());
            Preconditions.checkNotNull(key, (String)"No data component serializer found for %s", (Object[])new Object[]{value});
            map.put(key, (StructuredData<?>)value);
        }
        for (i = 0; i < markersSize; ++i) {
            int id = Types.VAR_INT.readPrimitive(buffer);
            key = this.dataType.key(id);
            Preconditions.checkNotNull(key, (String)"No data component serializer found for empty id %s", (Object[])new Object[]{id});
            map.put(key, StructuredData.empty(key, id));
        }
        return map;
    }

    @Override
    public void write(ByteBuf buffer, Item object) {
        if (object.isEmpty()) {
            Types.VAR_INT.writePrimitive(buffer, 0);
            return;
        }
        Types.VAR_INT.writePrimitive(buffer, object.amount());
        Types.VAR_INT.writePrimitive(buffer, object.identifier());
        Map<StructuredDataKey<?>, StructuredData<?>> data = object.dataContainer().data();
        int valuesSize = 0;
        int markersSize = 0;
        for (StructuredData<?> value : data.values()) {
            if (value.isPresent()) {
                ++valuesSize;
                continue;
            }
            ++markersSize;
        }
        Types.VAR_INT.writePrimitive(buffer, valuesSize);
        Types.VAR_INT.writePrimitive(buffer, markersSize);
        for (StructuredData<?> value : data.values()) {
            if (!value.isPresent()) continue;
            this.dataType.write(buffer, value);
        }
        for (StructuredData<?> value : data.values()) {
            if (!value.isEmpty()) continue;
            Types.VAR_INT.writePrimitive(buffer, value.id());
        }
    }

    public final class OptionalItemType
    extends OptionalType<Item> {
        public OptionalItemType() {
            super(ItemType1_20_5.this);
        }
    }
}

