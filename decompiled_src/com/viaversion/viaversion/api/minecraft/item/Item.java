/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Item {
    public int identifier();

    public void setIdentifier(int var1);

    public int amount();

    public void setAmount(int var1);

    default public short data() {
        return 0;
    }

    default public void setData(short data) {
        throw new UnsupportedOperationException();
    }

    public @Nullable CompoundTag tag();

    public void setTag(@Nullable CompoundTag var1);

    public StructuredDataContainer dataContainer();

    public Item copy();

    default public boolean isEmpty() {
        return this.identifier() == 0 || this.amount() <= 0;
    }
}

