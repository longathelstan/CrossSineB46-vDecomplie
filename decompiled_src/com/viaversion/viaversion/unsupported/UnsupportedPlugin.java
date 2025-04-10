/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.unsupported;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class UnsupportedPlugin
implements UnsupportedSoftware {
    final String name;
    final List<String> identifiers;
    final String reason;

    public UnsupportedPlugin(String name, List<String> identifiers, String reason) {
        Preconditions.checkNotNull((Object)name);
        Preconditions.checkNotNull((Object)reason);
        Preconditions.checkArgument((!identifiers.isEmpty() ? 1 : 0) != 0);
        this.name = name;
        this.identifiers = Collections.unmodifiableList(identifiers);
        this.reason = reason;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public final @Nullable String match() {
        for (String identifier : this.identifiers) {
            if (!Via.getPlatform().hasPlugin(identifier)) continue;
            return identifier;
        }
        return null;
    }

    public static final class Reason {
        public static final String SECURE_CHAT_BYPASS = "Instead of doing the obvious (or nothing at all), these kinds of plugins completely break chat message handling, usually then also breaking other plugins.";
    }

    public static final class Builder {
        final List<String> identifiers = new ArrayList<String>();
        String name;
        String reason;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder addPlugin(String identifier) {
            this.identifiers.add(identifier);
            return this;
        }

        public UnsupportedPlugin build() {
            return new UnsupportedPlugin(this.name, this.identifiers, this.reason);
        }
    }
}

