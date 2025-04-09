/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.tag;

import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.Tag;

public interface NumberArrayTag
extends Tag {
    public int length();

    public ListTag<? extends NumberTag> toListTag();

    @Override
    public NumberArrayTag copy();
}

