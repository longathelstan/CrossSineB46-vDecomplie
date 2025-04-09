/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.tag;

import com.viaversion.nbt.tag.Tag;

public interface NumberTag
extends Tag {
    @Override
    public Number getValue();

    public byte asByte();

    public short asShort();

    public int asInt();

    public long asLong();

    public float asFloat();

    public double asDouble();

    default public boolean asBoolean() {
        return this.asByte() != 0;
    }

    @Override
    default public NumberTag copy() {
        return this;
    }
}

