/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.events;

import com.viaversion.viaversion.libs.snakeyaml.error.Mark;
import com.viaversion.viaversion.libs.snakeyaml.events.Event;

public abstract class CollectionEndEvent
extends Event {
    public CollectionEndEvent(Mark startMark, Mark endMark) {
        super(startMark, endMark);
    }
}

