/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.Event;
import net.minecraft.util.MovementInput;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004\u00a8\u0006\b"}, d2={"Lnet/ccbluex/liquidbounce/event/MovementInputEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "original", "Lnet/minecraft/util/MovementInput;", "(Lnet/minecraft/util/MovementInput;)V", "getOriginal", "()Lnet/minecraft/util/MovementInput;", "setOriginal", "CrossSine"})
public final class MovementInputEvent
extends Event {
    @NotNull
    private MovementInput original;

    public MovementInputEvent(@NotNull MovementInput original) {
        Intrinsics.checkNotNullParameter(original, "original");
        this.original = original;
    }

    @NotNull
    public final MovementInput getOriginal() {
        return this.original;
    }

    public final void setOriginal(@NotNull MovementInput movementInput) {
        Intrinsics.checkNotNullParameter(movementInput, "<set-?>");
        this.original = movementInput;
    }
}

