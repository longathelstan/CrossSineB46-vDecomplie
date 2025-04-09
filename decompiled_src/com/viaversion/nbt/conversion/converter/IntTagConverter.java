/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.IntTag;

public class IntTagConverter
implements TagConverter<IntTag, Integer> {
    @Override
    public Integer convert(IntTag tag) {
        return tag.getValue();
    }

    @Override
    public IntTag convert(Integer value) {
        return new IntTag(value);
    }
}

