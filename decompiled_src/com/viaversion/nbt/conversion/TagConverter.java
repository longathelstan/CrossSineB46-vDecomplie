/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.conversion;

import com.viaversion.nbt.tag.Tag;

public interface TagConverter<T extends Tag, V> {
    public V convert(T var1);

    public T convert(V var1);
}

