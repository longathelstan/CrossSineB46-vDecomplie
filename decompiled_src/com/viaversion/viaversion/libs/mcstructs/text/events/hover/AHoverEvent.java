/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.events.hover;

import com.viaversion.viaversion.libs.mcstructs.snbt.SNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.HoverEventAction;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.TextHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentSerializer;

public abstract class AHoverEvent {
    protected HoverEventAction action;

    public AHoverEvent(HoverEventAction action) {
        this.action = action;
    }

    public HoverEventAction getAction() {
        return this.action;
    }

    public abstract TextHoverEvent toLegacy(TextComponentSerializer var1, SNbtSerializer<?> var2);

    public abstract boolean equals(Object var1);

    public abstract int hashCode();

    public abstract String toString();
}

