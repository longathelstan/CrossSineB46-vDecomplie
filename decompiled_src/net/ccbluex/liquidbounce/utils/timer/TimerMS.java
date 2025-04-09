/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.timer;

public final class TimerMS {
    public long time = -1L;

    public boolean hasTimePassed(long MS) {
        return System.currentTimeMillis() >= this.time + MS;
    }

    public void reset() {
        this.time = System.currentTimeMillis();
    }

    public long getTimeNow(long MS) {
        return this.time + MS - System.currentTimeMillis();
    }
}

