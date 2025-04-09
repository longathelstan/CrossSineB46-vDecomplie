/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.FloatTag;

public class FloatTagConverter
implements TagConverter<FloatTag, Float> {
    @Override
    public Float convert(FloatTag tag) {
        return tag.getValue();
    }

    @Override
    public FloatTag convert(Float value) {
        return new FloatTag(value.floatValue());
    }
}

