/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.stringified;

import com.viaversion.nbt.stringified.CharBuffer;
import com.viaversion.nbt.stringified.StringifiedTagParseException;
import com.viaversion.nbt.stringified.TagStringReader;
import com.viaversion.nbt.stringified.TagStringWriter;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.Tag;

public final class SNBT {
    private SNBT() {
    }

    public static Tag deserialize(String snbt) {
        CharBuffer buffer = new CharBuffer(snbt);
        TagStringReader parser = new TagStringReader(buffer);
        Tag tag = parser.tag();
        if (buffer.skipWhitespace().hasMore()) {
            throw new StringifiedTagParseException("Input has trailing content", buffer.index());
        }
        return tag;
    }

    public static CompoundTag deserializeCompoundTag(String snbt) {
        CharBuffer buffer = new CharBuffer(snbt);
        TagStringReader reader = new TagStringReader(buffer);
        CompoundTag tag = reader.compound();
        if (buffer.skipWhitespace().hasMore()) {
            throw new StringifiedTagParseException("Input has trailing content", buffer.index());
        }
        return tag;
    }

    public static String serialize(Tag tag) {
        StringBuilder builder = new StringBuilder();
        TagStringWriter writer = new TagStringWriter(builder);
        writer.writeTag(tag);
        return builder.toString();
    }
}

