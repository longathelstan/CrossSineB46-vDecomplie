/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards;

import com.google.common.base.Preconditions;
import com.viaversion.viabackwards.api.ViaBackwardsConfig;
import com.viaversion.viabackwards.api.ViaBackwardsPlatform;

public final class ViaBackwards {
    private static ViaBackwardsPlatform platform;
    private static ViaBackwardsConfig config;

    public static void init(ViaBackwardsPlatform platform, ViaBackwardsConfig config) {
        Preconditions.checkArgument((ViaBackwards.platform == null ? 1 : 0) != 0, (Object)"ViaBackwards is already initialized");
        ViaBackwards.platform = platform;
        ViaBackwards.config = config;
    }

    public static ViaBackwardsPlatform getPlatform() {
        return platform;
    }

    public static ViaBackwardsConfig getConfig() {
        return config;
    }
}

