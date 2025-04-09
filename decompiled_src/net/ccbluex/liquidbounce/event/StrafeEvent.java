/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.event.CancellableEvent;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2={"Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "strafe", "", "forward", "friction", "yaw", "(FFFF)V", "getForward", "()F", "getFriction", "getStrafe", "getYaw", "setYaw", "(F)V", "CrossSine"})
public final class StrafeEvent
extends CancellableEvent {
    private final float strafe;
    private final float forward;
    private final float friction;
    private float yaw;

    public StrafeEvent(float strafe, float forward, float friction, float yaw) {
        this.strafe = strafe;
        this.forward = forward;
        this.friction = friction;
        this.yaw = yaw;
    }

    public final float getStrafe() {
        return this.strafe;
    }

    public final float getForward() {
        return this.forward;
    }

    public final float getFriction() {
        return this.friction;
    }

    public final float getYaw() {
        return this.yaw;
    }

    public final void setYaw(float f) {
        this.yaw = f;
    }
}

