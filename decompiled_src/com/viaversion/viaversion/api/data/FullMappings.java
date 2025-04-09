/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.BiMappings;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface FullMappings
extends BiMappings {
    public int id(String var1);

    public int mappedId(String var1);

    public @Nullable String identifier(int var1);

    public @Nullable String mappedIdentifier(int var1);

    public @Nullable String mappedIdentifier(String var1);

    @Override
    public FullMappings inverse();
}

