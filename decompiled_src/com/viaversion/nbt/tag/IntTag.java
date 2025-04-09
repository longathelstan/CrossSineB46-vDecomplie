/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.tag;

import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.stringified.SNBT;
import com.viaversion.nbt.tag.NumberTag;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class IntTag
implements NumberTag {
    public static final int ID = 3;
    private final int value;

    public IntTag() {
        this(0);
    }

    public IntTag(int value) {
        this.value = value;
    }

    public static IntTag read(DataInput in, TagLimiter tagLimiter) throws IOException {
        tagLimiter.countInt();
        return new IntTag(in.readInt());
    }

    @Override
    @Deprecated
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String asRawString() {
        return Integer.toString(this.value);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        IntTag intTag = (IntTag)o;
        return this.value == intTag.value;
    }

    public int hashCode() {
        return this.value;
    }

    @Override
    public byte asByte() {
        return (byte)this.value;
    }

    @Override
    public short asShort() {
        return (short)this.value;
    }

    @Override
    public int asInt() {
        return this.value;
    }

    @Override
    public long asLong() {
        return this.value;
    }

    @Override
    public float asFloat() {
        return this.value;
    }

    @Override
    public double asDouble() {
        return this.value;
    }

    @Override
    public int getTagId() {
        return 3;
    }

    public String toString() {
        return SNBT.serialize(this);
    }
}

