/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.api;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.ViaRewindConfig;
import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.Protocol1_7_6_10To1_7_2_5;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.Protocol1_8To1_7_6_10;
import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.io.File;
import java.util.logging.Logger;

public interface ViaRewindPlatform {
    public static final String VERSION = "4.0.3-SNAPSHOT";
    public static final String IMPL_VERSION = "git-ViaRewind-4.0.3-SNAPSHOT:74ebb09";

    default public void init(File configFile) {
        ViaRewindConfig config = new ViaRewindConfig(configFile, this.getLogger());
        config.reload();
        Via.getManager().getConfigurationProvider().register(config);
        ViaRewind.init(this, config);
        Via.getManager().getSubPlatforms().add(IMPL_VERSION);
        this.getLogger().info("Registering protocols...");
        ProtocolManager protocolManager = Via.getManager().getProtocolManager();
        protocolManager.registerProtocol((Protocol)new Protocol1_7_6_10To1_7_2_5(), ProtocolVersion.v1_7_2, ProtocolVersion.v1_7_6);
        protocolManager.registerProtocol((Protocol)new Protocol1_8To1_7_6_10(), ProtocolVersion.v1_7_6, ProtocolVersion.v1_8);
        protocolManager.registerProtocol((Protocol)new Protocol1_9To1_8(), ProtocolVersion.v1_8, ProtocolVersion.v1_9);
    }

    public Logger getLogger();

    public File getDataFolder();
}

