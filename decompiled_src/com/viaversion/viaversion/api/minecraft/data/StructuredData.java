/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.data;

import com.viaversion.viaversion.api.minecraft.data.EmptyStructuredData;
import com.viaversion.viaversion.api.minecraft.data.FilledStructuredData;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.util.IdHolder;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface StructuredData<T>
extends IdHolder {
    public static <T> StructuredData<T> of(StructuredDataKey<T> key, T value, int id) {
        return new FilledStructuredData<T>(key, value, id);
    }

    public static <T> StructuredData<T> empty(StructuredDataKey<T> key, int id) {
        return new EmptyStructuredData<T>(key, id);
    }

    public @Nullable T value();

    public void setValue(T var1);

    public void setId(int var1);

    public StructuredDataKey<T> key();

    default public boolean isPresent() {
        return !this.isEmpty();
    }

    public boolean isEmpty();

    public void write(ByteBuf var1);
}

