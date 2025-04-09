/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.events.click;

import com.viaversion.viaversion.libs.mcstructs.core.utils.ToString;
import com.viaversion.viaversion.libs.mcstructs.text.events.click.ClickEventAction;
import java.util.Objects;

public class ClickEvent {
    private ClickEventAction action;
    private String value;

    public ClickEvent(ClickEventAction action, String value) {
        this.action = action;
        this.value = value;
    }

    public ClickEventAction getAction() {
        return this.action;
    }

    public ClickEvent setAction(ClickEventAction action) {
        this.action = action;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public ClickEvent setValue(String value) {
        this.value = value;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ClickEvent that = (ClickEvent)o;
        return this.action == that.action && Objects.equals(this.value, that.value);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.action, this.value});
    }

    public String toString() {
        return ToString.of(this).add("action", this.action).add("value", this.value).toString();
    }
}

