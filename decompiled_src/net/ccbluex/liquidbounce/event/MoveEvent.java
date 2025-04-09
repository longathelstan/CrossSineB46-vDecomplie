/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.CancellableEvent;
import net.ccbluex.liquidbounce.event.EventState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u001a\u001a\u00020\u000eJ\u0006\u0010\u001b\u001a\u00020\u001cJ\u0006\u0010\u001d\u001a\u00020\u001cR\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0013\"\u0004\b\u0017\u0010\u0015R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0013\"\u0004\b\u0019\u0010\u0015\u00a8\u0006\u001e"}, d2={"Lnet/ccbluex/liquidbounce/event/MoveEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "x", "", "y", "z", "(DDD)V", "eventState", "Lnet/ccbluex/liquidbounce/event/EventState;", "getEventState", "()Lnet/ccbluex/liquidbounce/event/EventState;", "setEventState", "(Lnet/ccbluex/liquidbounce/event/EventState;)V", "isSafeWalk", "", "()Z", "setSafeWalk", "(Z)V", "getX", "()D", "setX", "(D)V", "getY", "setY", "getZ", "setZ", "isPre", "zero", "", "zeroXZ", "CrossSine"})
public final class MoveEvent
extends CancellableEvent {
    private double x;
    private double y;
    private double z;
    private boolean isSafeWalk;
    @NotNull
    private EventState eventState;

    public MoveEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.eventState = EventState.PRE;
    }

    public final double getX() {
        return this.x;
    }

    public final void setX(double d) {
        this.x = d;
    }

    public final double getY() {
        return this.y;
    }

    public final void setY(double d) {
        this.y = d;
    }

    public final double getZ() {
        return this.z;
    }

    public final void setZ(double d) {
        this.z = d;
    }

    public final boolean isSafeWalk() {
        return this.isSafeWalk;
    }

    public final void setSafeWalk(boolean bl) {
        this.isSafeWalk = bl;
    }

    @NotNull
    public final EventState getEventState() {
        return this.eventState;
    }

    public final void setEventState(@NotNull EventState eventState) {
        Intrinsics.checkNotNullParameter((Object)eventState, "<set-?>");
        this.eventState = eventState;
    }

    public final boolean isPre() {
        return this.eventState == EventState.PRE;
    }

    public final void zero() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public final void zeroXZ() {
        this.x = 0.0;
        this.z = 0.0;
    }
}

