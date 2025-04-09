/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.script.api.global;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\b"}, d2={"Lnet/ccbluex/liquidbounce/script/api/global/Chat;", "", "()V", "alert", "", "message", "", "print", "CrossSine"})
public final class Chat {
    @NotNull
    public static final Chat INSTANCE = new Chat();

    private Chat() {
    }

    @JvmStatic
    public static final void print(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        ClientUtils.INSTANCE.displayChatMessage(message);
    }

    @JvmStatic
    public static final void alert(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        ClientUtils.INSTANCE.displayAlert(message);
    }
}

