/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.rewriter;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.gson.JsonElement;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ComponentRewriter {
    public void processTag(UserConnection var1, @Nullable Tag var2);

    public void processText(UserConnection var1, @Nullable JsonElement var2);
}

