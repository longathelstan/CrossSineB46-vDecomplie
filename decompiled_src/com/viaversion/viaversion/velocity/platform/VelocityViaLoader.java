/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.velocity.platform;

import com.velocitypowered.api.plugin.PluginContainer;
import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.BossBarProvider;
import com.viaversion.viaversion.velocity.listeners.UpdateListener;
import com.viaversion.viaversion.velocity.platform.VelocityViaConfig;
import com.viaversion.viaversion.velocity.providers.VelocityBossBarProvider;
import com.viaversion.viaversion.velocity.providers.VelocityVersionProvider;

public class VelocityViaLoader
implements ViaPlatformLoader {
    @Override
    public void load() {
        Object plugin = VelocityPlugin.PROXY.getPluginManager().getPlugin("viaversion").flatMap(PluginContainer::getInstance).get();
        ViaProviders providers = Via.getManager().getProviders();
        ProtocolVersion protocolVersion = Via.getAPI().getServerVersion().lowestSupportedProtocolVersion();
        if (protocolVersion.olderThan(ProtocolVersion.v1_9)) {
            providers.use(BossBarProvider.class, new VelocityBossBarProvider());
        }
        providers.use(VersionProvider.class, new VelocityVersionProvider());
        VelocityPlugin.PROXY.getEventManager().register(plugin, (Object)new UpdateListener());
        int pingInterval = ((VelocityViaConfig)Via.getPlatform().getConf()).getVelocityPingInterval();
        if (pingInterval > 0) {
            Via.getPlatform().runRepeatingAsync(() -> Via.proxyPlatform().protocolDetectorService().probeAllServers(), (long)pingInterval * 20L);
        }
    }

    @Override
    public void unload() {
    }
}

