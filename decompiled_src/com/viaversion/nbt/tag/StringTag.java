/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.tag;

import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.stringified.SNBT;
import com.viaversion.nbt.tag.Tag;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class StringTag
implements Tag {
    public static final int ID = 8;
    private String value;

    public StringTag() {
        this("");
    }

    public StringTag(String value) {
        if (value == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = value;
    }

    public static StringTag read(DataInput in, TagLimiter tagLimiter) throws IOException {
        String value = in.readUTF();
        tagLimiter.countBytes(2 * value.length());
        return new StringTag(value);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String asRawString() {
        return this.value;
    }

    public void setValue(String value) {
        if (value == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = value;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        StringTag stringTag = (StringTag)o;
        return this.value.equals(stringTag.value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public StringTag copy() {
        return new StringTag(this.value);
    }

    @Override
    public int getTagId() {
        return 8;
    }

    public String toString() {
        return SNBT.serialize(this);
    }
}

