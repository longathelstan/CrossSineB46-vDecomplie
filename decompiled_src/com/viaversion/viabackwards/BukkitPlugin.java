/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards;

import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
import com.viaversion.viabackwards.listener.BlockBreakListener;
import com.viaversion.viabackwards.listener.FireDamageListener;
import com.viaversion.viabackwards.listener.FireExtinguishListener;
import com.viaversion.viabackwards.listener.LecternInteractListener;
import com.viaversion.viabackwards.listener.PlayerItemDropListener;
import com.viaversion.viabackwards.protocol.v1_20_2to1_20.provider.AdvancementCriteriaProvider;
import com.viaversion.viabackwards.provider.BukkitAdvancementCriteriaProvider;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.io.File;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlugin
extends JavaPlugin
implements ViaBackwardsPlatform {
    public BukkitPlugin() {
        Via.getManager().addEnableListener(() -> this.init(new File(this.getDataFolder(), "config.yml")));
    }

    public void onEnable() {
        if (Via.getManager().getInjector().lateProtocolVersionSetting()) {
            Via.getPlatform().runSync(this::enable, 1L);
        } else {
            this.enable();
        }
    }

    private void enable() {
        ProtocolVersion protocolVersion = Via.getAPI().getServerVersion().highestSupportedProtocolVersion();
        if (protocolVersion.newerThanOrEqualTo(ProtocolVersion.v1_17)) {
            new PlayerItemDropListener(this).register();
        }
        if (protocolVersion.newerThanOrEqualTo(ProtocolVersion.v1_16)) {
            new FireExtinguishListener(this).register();
        }
        if (protocolVersion.newerThanOrEqualTo(ProtocolVersion.v1_14)) {
            new LecternInteractListener(this).register();
        }
        if (protocolVersion.newerThanOrEqualTo(ProtocolVersion.v1_12)) {
            new FireDamageListener(this).register();
        }
        if (protocolVersion.newerThanOrEqualTo(ProtocolVersion.v1_11)) {
            new BlockBreakListener(this).register();
        }
        ViaProviders providers = Via.getManager().getProviders();
        if (protocolVersion.newerThanOrEqualTo(ProtocolVersion.v1_20_2)) {
            providers.use(AdvancementCriteriaProvider.class, new BukkitAdvancementCriteriaProvider());
        }
    }

    @Override
    public void disable() {
        this.getPluginLoader().disablePlugin((Plugin)this);
    }
}

