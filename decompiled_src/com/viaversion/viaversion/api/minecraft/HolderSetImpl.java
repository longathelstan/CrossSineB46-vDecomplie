/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.minecraft.HolderSet;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import com.viaversion.viaversion.util.EitherImpl;

final class HolderSetImpl
extends EitherImpl<String, int[]>
implements HolderSet {
    HolderSetImpl(String tagKey) {
        super(tagKey, null);
    }

    HolderSetImpl(int[] ids) {
        super(null, ids);
    }

    @Override
    public String tagKey() {
        return (String)this.left();
    }

    @Override
    public boolean hasTagKey() {
        return this.isLeft();
    }

    @Override
    public int[] ids() {
        return (int[])this.right();
    }

    @Override
    public boolean hasIds() {
        return this.isRight();
    }

    @Override
    public HolderSet rewrite(Int2IntFunction idRewriter) {
        if (this.hasTagKey()) {
            return this;
        }
        int[] ids = this.ids();
        int[] mappedIds = new int[ids.length];
        for (int i = 0; i < mappedIds.length; ++i) {
            mappedIds[i] = idRewriter.applyAsInt(ids[i]);
        }
        return new HolderSetImpl(mappedIds);
    }
}

