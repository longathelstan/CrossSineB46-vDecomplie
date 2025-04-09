/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.minecraft.HolderSetImpl;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;

public interface HolderSet {
    public static HolderSet of(String tagKey) {
        return new HolderSetImpl(tagKey);
    }

    public static HolderSet of(int[] ids) {
        return new HolderSetImpl(ids);
    }

    public String tagKey();

    public boolean hasTagKey();

    public int[] ids();

    public boolean hasIds();

    public HolderSet rewrite(Int2IntFunction var1);
}

