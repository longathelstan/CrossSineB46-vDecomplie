/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_19to1_19_1.data;

import com.viaversion.viaversion.libs.gson.JsonElement;
import java.util.Objects;

public final class ChatDecorationResult {
    private final JsonElement content;
    private final boolean overlay;

    public ChatDecorationResult(JsonElement content, boolean overlay) {
        this.content = content;
        this.overlay = overlay;
    }

    public JsonElement content() {
        return this.content;
    }

    public boolean overlay() {
        return this.overlay;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ChatDecorationResult)) {
            return false;
        }
        ChatDecorationResult chatDecorationResult = (ChatDecorationResult)object;
        return Objects.equals(this.content, chatDecorationResult.content) && this.overlay == chatDecorationResult.overlay;
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.content)) * 31 + Boolean.hashCode(this.overlay);
    }

    public String toString() {
        return String.format("%s[content=%s, overlay=%s]", this.getClass().getSimpleName(), Objects.toString(this.content), Boolean.toString(this.overlay));
    }
}

