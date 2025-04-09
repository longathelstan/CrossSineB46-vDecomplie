/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.api;

import com.viaversion.viaversion.api.configuration.Config;

public interface ViaRewindConfig
extends Config {
    public CooldownIndicator getCooldownIndicator();

    public boolean isReplaceAdventureMode();

    public boolean isReplaceParticles();

    public int getMaxBookPages();

    public int getMaxBookPageSize();

    public boolean isEmulateWorldBorder();

    public boolean alwaysShowOriginalMobName();

    public String getWorldBorderParticle();

    public boolean isEnableOffhand();

    public String getOffhandCommand();

    public boolean emulateLevitationEffect();

    public boolean handlePlayerCombatPacket();

    public static enum CooldownIndicator {
        TITLE,
        ACTION_BAR,
        BOSS_BAR,
        DISABLED;

    }
}

