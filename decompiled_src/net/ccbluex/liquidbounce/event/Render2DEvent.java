/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.Event;
import net.minecraft.client.gui.ScaledResolution;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2={"Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "partialTicks", "", "scaledResolution", "Lnet/minecraft/client/gui/ScaledResolution;", "(FLnet/minecraft/client/gui/ScaledResolution;)V", "getPartialTicks", "()F", "getScaledResolution", "()Lnet/minecraft/client/gui/ScaledResolution;", "CrossSine"})
public final class Render2DEvent
extends Event {
    private final float partialTicks;
    @NotNull
    private final ScaledResolution scaledResolution;

    public Render2DEvent(float partialTicks, @NotNull ScaledResolution scaledResolution) {
        Intrinsics.checkNotNullParameter(scaledResolution, "scaledResolution");
        this.partialTicks = partialTicks;
        this.scaledResolution = scaledResolution;
    }

    public final float getPartialTicks() {
        return this.partialTicks;
    }

    @NotNull
    public final ScaledResolution getScaledResolution() {
        return this.scaledResolution;
    }
}

