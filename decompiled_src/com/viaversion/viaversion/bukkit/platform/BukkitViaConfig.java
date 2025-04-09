/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.configuration.AbstractViaConfig;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class BukkitViaConfig
extends AbstractViaConfig {
    private boolean quickMoveActionFix;
    private boolean hitboxFix1_9;
    private boolean hitboxFix1_14;
    private String blockConnectionMethod;
    private boolean armorToggleFix;
    private boolean registerUserConnectionOnJoin;
    private boolean useNewDeathMessages;
    private boolean itemCache;
    private boolean nmsPlayerTicking;

    public BukkitViaConfig(File folder, Logger logger) {
        super(new File(folder, "config.yml"), logger);
    }

    @Override
    protected void loadFields() {
        super.loadFields();
        this.registerUserConnectionOnJoin = this.getBoolean("register-userconnections-on-join", true);
        this.quickMoveActionFix = this.getBoolean("quick-move-action-fix", false);
        this.hitboxFix1_9 = this.getBoolean("change-1_9-hitbox", false);
        this.hitboxFix1_14 = this.getBoolean("change-1_14-hitbox", false);
        this.blockConnectionMethod = this.getString("blockconnection-method", "packet");
        this.armorToggleFix = this.getBoolean("armor-toggle-fix", true);
        this.useNewDeathMessages = this.getBoolean("use-new-deathmessages", true);
        this.itemCache = this.getBoolean("item-cache", true);
        this.nmsPlayerTicking = this.getBoolean("nms-player-ticking", true);
    }

    @Override
    protected void handleConfig(Map<String, Object> config) {
    }

    @Override
    public boolean shouldRegisterUserConnectionOnJoin() {
        return this.registerUserConnectionOnJoin;
    }

    @Override
    public boolean is1_12QuickMoveActionFix() {
        return this.quickMoveActionFix;
    }

    @Override
    public boolean is1_9HitboxFix() {
        return this.hitboxFix1_9;
    }

    @Override
    public boolean is1_14HitboxFix() {
        return this.hitboxFix1_14;
    }

    @Override
    public String getBlockConnectionMethod() {
        return this.blockConnectionMethod;
    }

    @Override
    public boolean isArmorToggleFix() {
        return this.armorToggleFix;
    }

    @Override
    public boolean isShowNewDeathMessages() {
        return this.useNewDeathMessages;
    }

    @Override
    public boolean isItemCache() {
        return this.itemCache;
    }

    @Override
    public boolean isNMSPlayerTicking() {
        return this.nmsPlayerTicking;
    }

    @Override
    public List<String> getUnsupportedOptions() {
        return VELOCITY_ONLY_OPTIONS;
    }
}

