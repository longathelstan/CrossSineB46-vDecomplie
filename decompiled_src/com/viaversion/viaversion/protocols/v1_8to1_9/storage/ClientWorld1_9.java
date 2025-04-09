/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_8to1_9.storage;

import com.google.common.collect.Sets;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import java.util.Set;

public class ClientWorld1_9
extends ClientWorld {
    private final Set<Long> loadedChunks = Sets.newConcurrentHashSet();

    public static long toLong(int msw, int lsw) {
        return ((long)msw << 32) + (long)lsw + 0x80000000L;
    }

    public Set<Long> getLoadedChunks() {
        return this.loadedChunks;
    }
}

