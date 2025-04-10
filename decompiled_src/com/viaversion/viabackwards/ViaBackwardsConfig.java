/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards;

import com.viaversion.viaversion.util.Config;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ViaBackwardsConfig
extends Config
implements com.viaversion.viabackwards.api.ViaBackwardsConfig {
    private boolean addCustomEnchantsToLore;
    private boolean addTeamColorToPrefix;
    private boolean fix1_13FacePlayer;
    private boolean alwaysShowOriginalMobName;
    private boolean fix1_13FormattedInventoryTitles;
    private boolean handlePingsAsInvAcknowledgements;
    private boolean bedrockAtY0;
    private boolean sculkShriekersToCryingObsidian;
    private boolean suppressEmulationWarnings;

    public ViaBackwardsConfig(File configFile, Logger logger) {
        super(configFile, logger);
    }

    @Override
    public void reload() {
        super.reload();
        this.loadFields();
    }

    private void loadFields() {
        this.addCustomEnchantsToLore = this.getBoolean("add-custom-enchants-into-lore", true);
        this.addTeamColorToPrefix = this.getBoolean("add-teamcolor-to-prefix", true);
        this.fix1_13FacePlayer = this.getBoolean("fix-1_13-face-player", false);
        this.fix1_13FormattedInventoryTitles = this.getBoolean("fix-formatted-inventory-titles", true);
        this.alwaysShowOriginalMobName = this.getBoolean("always-show-original-mob-name", true);
        this.handlePingsAsInvAcknowledgements = this.getBoolean("handle-pings-as-inv-acknowledgements", false);
        this.bedrockAtY0 = this.getBoolean("bedrock-at-y-0", false);
        this.sculkShriekersToCryingObsidian = this.getBoolean("sculk-shriekers-to-crying-obsidian", false);
        this.suppressEmulationWarnings = this.getBoolean("suppress-emulation-warnings", false);
    }

    @Override
    public boolean addCustomEnchantsToLore() {
        return this.addCustomEnchantsToLore;
    }

    @Override
    public boolean addTeamColorTo1_13Prefix() {
        return this.addTeamColorToPrefix;
    }

    @Override
    public boolean isFix1_13FacePlayer() {
        return this.fix1_13FacePlayer;
    }

    @Override
    public boolean fix1_13FormattedInventoryTitle() {
        return this.fix1_13FormattedInventoryTitles;
    }

    @Override
    public boolean alwaysShowOriginalMobName() {
        return this.alwaysShowOriginalMobName;
    }

    @Override
    public boolean handlePingsAsInvAcknowledgements() {
        return this.handlePingsAsInvAcknowledgements || Boolean.getBoolean("com.viaversion.handlePingsAsInvAcknowledgements");
    }

    @Override
    public boolean bedrockAtY0() {
        return this.bedrockAtY0;
    }

    @Override
    public boolean sculkShriekerToCryingObsidian() {
        return this.sculkShriekersToCryingObsidian;
    }

    @Override
    public boolean suppressEmulationWarnings() {
        return this.suppressEmulationWarnings;
    }

    @Override
    public URL getDefaultConfigURL() {
        return this.getClass().getClassLoader().getResource("assets/viabackwards/config.yml");
    }

    @Override
    public InputStream getDefaultConfigInputStream() {
        return this.getClass().getClassLoader().getResourceAsStream("assets/viabackwards/config.yml");
    }

    @Override
    protected void handleConfig(Map<String, Object> map) {
    }

    @Override
    public List<String> getUnsupportedOptions() {
        return Collections.emptyList();
    }
}

