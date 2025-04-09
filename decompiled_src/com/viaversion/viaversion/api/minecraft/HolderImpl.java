/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;

final class HolderImpl<T>
implements Holder<T> {
    private final T value;
    private final int id;

    HolderImpl(int id) {
        Preconditions.checkArgument((id >= 0 ? 1 : 0) != 0, (Object)"id cannot be negative");
        this.value = null;
        this.id = id;
    }

    HolderImpl(T value) {
        this.value = value;
        this.id = -1;
    }

    @Override
    public boolean isDirect() {
        return this.id == -1;
    }

    @Override
    public boolean hasId() {
        return this.id != -1;
    }

    @Override
    public T value() {
        Preconditions.checkArgument((boolean)this.isDirect(), (Object)"Holder is not direct");
        return this.value;
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public Holder<T> updateId(Int2IntFunction rewriteFunction) {
        if (this.isDirect()) {
            return this;
        }
        int rewrittenId = rewriteFunction.applyAsInt(this.id);
        if (rewrittenId == this.id) {
            return this;
        }
        if (rewrittenId == -1) {
            throw new IllegalArgumentException("Received invalid id in updateId");
        }
        return Holder.of(rewrittenId);
    }

    public String toString() {
        int n = this.id;
        T t = this.value;
        return "HolderImpl{value=" + t + ", id=" + n + "}";
    }
}

