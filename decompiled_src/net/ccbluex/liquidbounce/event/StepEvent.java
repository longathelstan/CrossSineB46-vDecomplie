/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.Event;
import net.ccbluex.liquidbounce.event.EventState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\u00a8\u0006\r"}, d2={"Lnet/ccbluex/liquidbounce/event/StepEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "stepHeight", "", "eventState", "Lnet/ccbluex/liquidbounce/event/EventState;", "(FLnet/ccbluex/liquidbounce/event/EventState;)V", "getEventState", "()Lnet/ccbluex/liquidbounce/event/EventState;", "getStepHeight", "()F", "setStepHeight", "(F)V", "CrossSine"})
public final class StepEvent
extends Event {
    private float stepHeight;
    @NotNull
    private final EventState eventState;

    public StepEvent(float stepHeight, @NotNull EventState eventState) {
        Intrinsics.checkNotNullParameter((Object)eventState, "eventState");
        this.stepHeight = stepHeight;
        this.eventState = eventState;
    }

    public final float getStepHeight() {
        return this.stepHeight;
    }

    public final void setStepHeight(float f) {
        this.stepHeight = f;
    }

    @NotNull
    public final EventState getEventState() {
        return this.eventState;
    }
}

