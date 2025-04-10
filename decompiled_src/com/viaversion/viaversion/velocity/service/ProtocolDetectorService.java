/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.velocity.service;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.platform.AbstractProtocolDetectorService;
import com.viaversion.viaversion.velocity.platform.VelocityViaConfig;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;

public final class ProtocolDetectorService
extends AbstractProtocolDetectorService {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void probeAllServers() {
        Collection servers = VelocityPlugin.PROXY.getAllServers();
        HashSet<String> serverNames = new HashSet<String>(servers.size());
        for (RegisteredServer server : servers) {
            this.probeServer(server);
            serverNames.add(server.getServerInfo().getName());
        }
        this.lock.writeLock().lock();
        try {
            this.detectedProtocolIds.keySet().retainAll(serverNames);
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    public void probeServer(RegisteredServer server) {
        String serverName = server.getServerInfo().getName();
        server.ping().thenAccept(serverPing -> {
            if (serverPing == null || serverPing.getVersion() == null) {
                return;
            }
            ProtocolVersion oldProtocolVersion = this.serverProtocolVersion(serverName);
            if (oldProtocolVersion.isKnown() && oldProtocolVersion.getVersion() == serverPing.getVersion().getProtocol()) {
                return;
            }
            this.setProtocolVersion(serverName, serverPing.getVersion().getProtocol());
            VelocityViaConfig config = (VelocityViaConfig)Via.getConfig();
            if (config.isVelocityPingSave()) {
                Map<String, Integer> servers = this.configuredServers();
                Integer protocol = servers.get(serverName);
                if (protocol != null && protocol.intValue() == serverPing.getVersion().getProtocol()) {
                    return;
                }
                ConfigurationProvider configurationProvider = Via.getManager().getConfigurationProvider();
                synchronized (configurationProvider) {
                    servers.put(serverName, serverPing.getVersion().getProtocol());
                }
                config.save();
            }
        });
    }

    @Override
    protected Map<String, Integer> configuredServers() {
        return ((VelocityViaConfig)Via.getConfig()).getVelocityServerProtocols();
    }

    @Override
    protected ProtocolVersion lowestSupportedProtocolVersion() {
        try {
            return Via.getManager().getInjector().getServerProtocolVersion();
        }
        catch (Exception e) {
            Via.getPlatform().getLogger().log(Level.WARNING, "Failed to get lowest supported protocol version", e);
            return ProtocolVersion.v1_8;
        }
    }
}

