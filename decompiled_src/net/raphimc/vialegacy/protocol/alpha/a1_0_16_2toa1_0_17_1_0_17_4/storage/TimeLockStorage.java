/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.alpha.a1_0_16_2toa1_0_17_1_0_17_4.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class TimeLockStorage
implements StorableObject {
    private long time;

    public TimeLockStorage(long time) {
        this.time = time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return this.time;
    }
}

