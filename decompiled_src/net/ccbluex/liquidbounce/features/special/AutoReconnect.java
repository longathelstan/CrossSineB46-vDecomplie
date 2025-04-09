/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.special;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R$\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0011"}, d2={"Lnet/ccbluex/liquidbounce/features/special/AutoReconnect;", "", "()V", "MAX", "", "MIN", "value", "delay", "getDelay", "()I", "setDelay", "(I)V", "isEnabled", "", "()Z", "setEnabled", "(Z)V", "CrossSine"})
public final class AutoReconnect {
    @NotNull
    public static final AutoReconnect INSTANCE = new AutoReconnect();
    public static final int MAX = 60000;
    public static final int MIN = 1000;
    private static boolean isEnabled = true;
    private static int delay = 5000;

    private AutoReconnect() {
    }

    public final boolean isEnabled() {
        return isEnabled;
    }

    public final void setEnabled(boolean bl) {
        isEnabled = bl;
    }

    public final int getDelay() {
        return delay;
    }

    public final void setDelay(int value) {
        isEnabled = value < 60000;
        delay = value;
    }
}

