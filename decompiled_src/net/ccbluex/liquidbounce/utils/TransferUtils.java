/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/utils/TransferUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "noMotionSet", "", "getNoMotionSet", "()Z", "setNoMotionSet", "(Z)V", "silentConfirm", "getSilentConfirm", "setSilentConfirm", "CrossSine"})
public final class TransferUtils
extends MinecraftInstance {
    @NotNull
    public static final TransferUtils INSTANCE = new TransferUtils();
    private static boolean silentConfirm;
    private static boolean noMotionSet;

    private TransferUtils() {
    }

    public final boolean getSilentConfirm() {
        return silentConfirm;
    }

    public final void setSilentConfirm(boolean bl) {
        silentConfirm = bl;
    }

    public final boolean getNoMotionSet() {
        return noMotionSet;
    }

    public final void setNoMotionSet(boolean bl) {
        noMotionSet = bl;
    }
}

