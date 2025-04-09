/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.timer;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006\u00a8\u0006\u000b"}, d2={"Lnet/ccbluex/liquidbounce/utils/timer/TimeUtils;", "", "()V", "randomClickDelay", "", "minCPS", "", "maxCPS", "randomDelay", "minDelay", "maxDelay", "CrossSine"})
public final class TimeUtils {
    @NotNull
    public static final TimeUtils INSTANCE = new TimeUtils();

    private TimeUtils() {
    }

    public final long randomDelay(int minDelay, int maxDelay) {
        return RandomUtils.nextInt(minDelay, maxDelay);
    }

    public final long randomClickDelay(int minCPS, int maxCPS) {
        return (long)(Math.random() * (double)(1000 / minCPS - 1000 / maxCPS + 1) + (double)(1000 / maxCPS));
    }
}

