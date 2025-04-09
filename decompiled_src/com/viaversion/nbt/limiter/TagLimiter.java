/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.limiter;

import com.viaversion.nbt.limiter.NoopTagLimiter;
import com.viaversion.nbt.limiter.TagLimiterImpl;

public interface TagLimiter {
    public static TagLimiter create(int maxBytes, int maxLevels) {
        return new TagLimiterImpl(maxBytes, maxLevels);
    }

    public static TagLimiter noop() {
        return NoopTagLimiter.INSTANCE;
    }

    public void countBytes(int var1);

    public void checkLevel(int var1);

    default public void countByte() {
        this.countBytes(1);
    }

    default public void countShort() {
        this.countBytes(2);
    }

    default public void countInt() {
        this.countBytes(4);
    }

    default public void countFloat() {
        this.countBytes(8);
    }

    default public void countLong() {
        this.countBytes(8);
    }

    default public void countDouble() {
        this.countBytes(8);
    }

    public int maxBytes();

    public int maxLevels();

    public int bytes();

    public void reset();
}

