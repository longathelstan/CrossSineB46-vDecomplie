/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.minecraft.HolderImpl;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;

public interface Holder<T> {
    public static <T> Holder<T> of(int id) {
        return new HolderImpl(id);
    }

    public static <T> Holder<T> of(T value) {
        return new HolderImpl<T>(value);
    }

    public boolean isDirect();

    public boolean hasId();

    public T value();

    public int id();

    public Holder<T> updateId(Int2IntFunction var1);
}

