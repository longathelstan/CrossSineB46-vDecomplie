/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.timer;

import kotlin.Metadata;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004J\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\n\u001a\u00020\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lnet/ccbluex/liquidbounce/utils/timer/tickTimer;", "", "()V", "tick", "", "hasTimePassed", "", "ticks", "reset", "", "update", "CrossSine"})
public final class tickTimer {
    private int tick;

    public final void update() {
        int n = this.tick;
        this.tick = n + 1;
    }

    public final void reset() {
        this.tick = 0;
    }

    public final boolean hasTimePassed(int ticks) {
        return this.tick >= ticks;
    }
}

