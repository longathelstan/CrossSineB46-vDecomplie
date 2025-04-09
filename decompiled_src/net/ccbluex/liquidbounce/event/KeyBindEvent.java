/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.event.Event;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/event/KeyBindEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "()V", "code", "", "getCode", "()I", "setCode", "(I)V", "setKey", "", "key", "CrossSine"})
public final class KeyBindEvent
extends Event {
    private int code;

    public final int getCode() {
        return this.code;
    }

    public final void setCode(int n) {
        this.code = n;
    }

    public final void setKey(int key) {
        this.code = key;
    }
}

