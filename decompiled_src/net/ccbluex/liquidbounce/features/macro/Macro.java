/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.macro;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000b\u001a\u00020\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lnet/ccbluex/liquidbounce/features/macro/Macro;", "", "key", "", "command", "", "(ILjava/lang/String;)V", "getCommand", "()Ljava/lang/String;", "getKey", "()I", "exec", "", "CrossSine"})
public final class Macro {
    private final int key;
    @NotNull
    private final String command;

    public Macro(int key, @NotNull String command) {
        Intrinsics.checkNotNullParameter(command, "command");
        this.key = key;
        this.command = command;
    }

    public final int getKey() {
        return this.key;
    }

    @NotNull
    public final String getCommand() {
        return this.command;
    }

    public final void exec() {
        CrossSine.INSTANCE.getCommandManager().executeCommands(this.command);
    }
}

