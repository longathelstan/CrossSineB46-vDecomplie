/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release;

import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.IntCacheInstance;

public class IntCache {
    private static final ThreadLocal<IntCacheInstance> INT_CACHE = ThreadLocal.withInitial(IntCacheInstance::new);

    public static int[] getIntCache(int i) {
        return INT_CACHE.get().getIntCache(i);
    }

    public static void resetIntCache() {
        INT_CACHE.get().resetIntCache();
    }

    public static void resetEverything() {
        INT_CACHE.get().resetEverything();
    }
}

