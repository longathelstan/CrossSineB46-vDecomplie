/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.parser;

import com.viaversion.viaversion.libs.snakeyaml.events.Event;

public interface Parser {
    public boolean checkEvent(Event.ID var1);

    public Event peekEvent();

    public Event getEvent();
}

