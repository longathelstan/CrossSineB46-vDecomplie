/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.ShortTag;

public class ShortTagConverter
implements TagConverter<ShortTag, Short> {
    @Override
    public Short convert(ShortTag tag) {
        return tag.getValue();
    }

    @Override
    public ShortTag convert(Short value) {
        return new ShortTag(value);
    }
}

