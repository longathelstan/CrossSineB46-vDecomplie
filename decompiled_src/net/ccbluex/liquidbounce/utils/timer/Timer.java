/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.timer;

import net.minecraft.util.MathHelper;

public final class Timer {
    private long lastMS = 0L;
    private long previousTime = -1L;

    public boolean sleep(long time) {
        if (this.time() >= time) {
            this.reset();
            return true;
        }
        return false;
    }

    public boolean check(float milliseconds) {
        return (float)(System.currentTimeMillis() - this.previousTime) >= milliseconds;
    }

    public boolean delay(double milliseconds) {
        return (double)MathHelper.func_76131_a((float)(this.getCurrentMS() - this.lastMS), (float)0.0f, (float)((float)milliseconds)) >= milliseconds;
    }

    public void reset() {
        this.previousTime = System.currentTimeMillis();
        this.lastMS = this.getCurrentMS();
    }

    public long time() {
        return System.nanoTime() / 1000000L - this.lastMS;
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public double getLastDelay() {
        return this.getCurrentMS() - this.getLastMS();
    }

    public long getLastMS() {
        return this.lastMS;
    }
}

