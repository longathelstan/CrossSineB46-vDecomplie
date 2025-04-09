/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.events;

import com.viaversion.viaversion.libs.snakeyaml.error.Mark;
import com.viaversion.viaversion.libs.snakeyaml.events.CollectionEndEvent;
import com.viaversion.viaversion.libs.snakeyaml.events.Event;

public final class SequenceEndEvent
extends CollectionEndEvent {
    public SequenceEndEvent(Mark startMark, Mark endMark) {
        super(startMark, endMark);
    }

    @Override
    public Event.ID getEventId() {
        return Event.ID.SequenceEnd;
    }
}

