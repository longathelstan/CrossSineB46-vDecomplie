/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.timer;

import kotlin.Metadata;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\u0004J\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u000f\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u0010"}, d2={"Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "", "()V", "time", "", "getTime", "()J", "setTime", "(J)V", "hasTimeLeft", "MS", "hasTimePassed", "", "reset", "", "timePassed", "CrossSine"})
public final class MSTimer {
    private long time = -1L;

    public final long getTime() {
        return this.time;
    }

    public final void setTime(long l) {
        this.time = l;
    }

    public final boolean hasTimePassed(long MS) {
        return System.currentTimeMillis() >= this.time + MS;
    }

    public final long hasTimeLeft(long MS) {
        return MS + this.time - System.currentTimeMillis();
    }

    public final long timePassed() {
        return System.currentTimeMillis() - this.time;
    }

    public final void reset() {
        this.time = System.currentTimeMillis();
    }
}

