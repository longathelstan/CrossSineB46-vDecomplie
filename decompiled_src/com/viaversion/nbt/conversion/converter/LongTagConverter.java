/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.LongTag;

public class LongTagConverter
implements TagConverter<LongTag, Long> {
    @Override
    public Long convert(LongTag tag) {
        return tag.getValue();
    }

    @Override
    public LongTag convert(Long value) {
        return new LongTag(value);
    }
}

