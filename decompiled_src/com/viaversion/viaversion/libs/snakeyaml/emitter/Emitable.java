/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.emitter;

import com.viaversion.viaversion.libs.snakeyaml.events.Event;
import java.io.IOException;

public interface Emitable {
    public void emit(Event var1) throws IOException;
}

