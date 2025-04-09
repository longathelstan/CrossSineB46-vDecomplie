/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.velocity.platform;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.configuration.AbstractViaConfig;
import com.viaversion.viaversion.velocity.platform.VelocityViaInjector;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class VelocityViaConfig
extends AbstractViaConfig {
    private int velocityPingInterval;
    private boolean velocityPingSave;
    private Map<String, Integer> velocityServerProtocols;

    public VelocityViaConfig(File folder, Logger logger) {
        super(new File(folder, "config.yml"), logger);
    }

    @Override
    protected void loadFields() {
        super.loadFields();
        this.velocityPingInterval = this.getInt("velocity-ping-interval", 60);
        this.velocityPingSave = this.getBoolean("velocity-ping-save", true);
        this.velocityServerProtocols = this.get("velocity-servers", new HashMap());
    }

    @Override
    protected void handleConfig(Map<String, Object> config) {
        Map<String, Integer> servers;
        Object object = config.get("velocity-servers");
        if (object instanceof Map) {
            Map velocityServers = (Map)object;
            servers = velocityServers;
        } else {
            servers = new HashMap();
        }
        for (Map.Entry entry : new HashSet(servers.entrySet())) {
            if (entry.getValue() instanceof Integer) continue;
            Object v = entry.getValue();
            if (v instanceof String) {
                String protocol = (String)v;
                ProtocolVersion found = ProtocolVersion.getClosest(protocol);
                if (found != null) {
                    servers.put((String)entry.getKey(), found.getVersion());
                    continue;
                }
                servers.remove(entry.getKey());
                continue;
            }
            servers.remove(entry.getKey());
        }
        if (!servers.containsKey("default")) {
            try {
                servers.put("default", VelocityViaInjector.getLowestSupportedProtocolVersion());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        config.put("velocity-servers", servers);
    }

    @Override
    public List<String> getUnsupportedOptions() {
        return BUKKIT_ONLY_OPTIONS;
    }

    public int getVelocityPingInterval() {
        return this.velocityPingInterval;
    }

    public boolean isVelocityPingSave() {
        return this.velocityPingSave;
    }

    public Map<String, Integer> getVelocityServerProtocols() {
        return this.velocityServerProtocols;
    }
}

