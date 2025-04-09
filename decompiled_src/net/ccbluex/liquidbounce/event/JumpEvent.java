/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.event.CancellableEvent;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\r\"\u0004\b\u0011\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lnet/ccbluex/liquidbounce/event/JumpEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "motion", "", "boosting", "", "yaw", "(FZF)V", "getBoosting", "()Z", "setBoosting", "(Z)V", "getMotion", "()F", "setMotion", "(F)V", "getYaw", "setYaw", "CrossSine"})
public final class JumpEvent
extends CancellableEvent {
    private float motion;
    private boolean boosting;
    private float yaw;

    public JumpEvent(float motion, boolean boosting, float yaw) {
        this.motion = motion;
        this.boosting = boosting;
        this.yaw = yaw;
    }

    public final float getMotion() {
        return this.motion;
    }

    public final void setMotion(float f) {
        this.motion = f;
    }

    public final boolean getBoosting() {
        return this.boosting;
    }

    public final void setBoosting(boolean bl) {
        this.boosting = bl;
    }

    public final float getYaw() {
        return this.yaw;
    }

    public final void setYaw(float f) {
        this.yaw = f;
    }
}

