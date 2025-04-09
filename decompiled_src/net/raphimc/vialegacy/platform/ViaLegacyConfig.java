/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.platform;

import com.viaversion.viaversion.api.configuration.Config;

public interface ViaLegacyConfig
extends Config {
    public boolean isDynamicOnground();

    public boolean isIgnoreLong1_8ChannelNames();

    public boolean isLegacySkullLoading();

    public boolean isLegacySkinLoading();

    public boolean isSoundEmulation();

    public boolean isOldBiomes();

    public boolean enableB1_7_3Sprinting();

    public int getClassicChunkRange();

    public boolean enableClassicFly();
}

