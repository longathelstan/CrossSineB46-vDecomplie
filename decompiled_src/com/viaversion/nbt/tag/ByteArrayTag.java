/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.tag;

import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.stringified.SNBT;
import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberArrayTag;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public final class ByteArrayTag
implements NumberArrayTag {
    public static final int ID = 7;
    private static final byte[] EMPTY_ARRAY = new byte[0];
    private byte[] value;

    public ByteArrayTag() {
        this(EMPTY_ARRAY);
    }

    public ByteArrayTag(byte[] value) {
        if (value == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = value;
    }

    public static ByteArrayTag read(DataInput in, TagLimiter tagLimiter) throws IOException {
        tagLimiter.countInt();
        byte[] value = new byte[in.readInt()];
        tagLimiter.countBytes(value.length);
        in.readFully(value);
        return new ByteArrayTag(value);
    }

    public byte[] getValue() {
        return this.value;
    }

    @Override
    public String asRawString() {
        return Arrays.toString(this.value);
    }

    public void setValue(byte[] value) {
        if (value == null) {
            return;
        }
        this.value = value;
    }

    public byte get(int index2) {
        return this.value[index2];
    }

    public void set(int index2, byte value) {
        this.value[index2] = value;
    }

    @Override
    public int length() {
        return this.value.length;
    }

    public ListTag<ByteTag> toListTag() {
        ListTag<ByteTag> list = new ListTag<ByteTag>(ByteTag.class);
        for (byte b : this.value) {
            list.add(new ByteTag(b));
        }
        return list;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.value.length);
        out.write(this.value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ByteArrayTag that = (ByteArrayTag)o;
        return Arrays.equals(this.value, that.value);
    }

    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override
    public ByteArrayTag copy() {
        return new ByteArrayTag((byte[])this.value.clone());
    }

    @Override
    public int getTagId() {
        return 7;
    }

    public String toString() {
        return SNBT.serialize(this);
    }
}

