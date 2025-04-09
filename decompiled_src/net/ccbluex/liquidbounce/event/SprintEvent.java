/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.event.CancellableEvent;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004\u00a8\u0006\b"}, d2={"Lnet/ccbluex/liquidbounce/event/SprintEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "sprint", "", "(Z)V", "getSprint", "()Z", "setSprint", "CrossSine"})
public final class SprintEvent
extends CancellableEvent {
    private boolean sprint;

    public SprintEvent(boolean sprint) {
        this.sprint = sprint;
    }

    public final boolean getSprint() {
        return this.sprint;
    }

    public final void setSprint(boolean bl) {
        this.sprint = bl;
    }
}

