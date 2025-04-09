/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.StringTag;

public class StringTagConverter
implements TagConverter<StringTag, String> {
    @Override
    public String convert(StringTag tag) {
        return tag.getValue();
    }

    @Override
    public StringTag convert(String value) {
        return new StringTag(value);
    }
}

