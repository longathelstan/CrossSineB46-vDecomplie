/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.ViaBackwardsConfig;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_10to1_9_3.Protocol1_10To1_9_3;
import com.viaversion.viabackwards.protocol.v1_11_1to1_11.Protocol1_11_1To1_11;
import com.viaversion.viabackwards.protocol.v1_11to1_10.Protocol1_11To1_10;
import com.viaversion.viabackwards.protocol.v1_12_1to1_12.Protocol1_12_1To1_12;
import com.viaversion.viabackwards.protocol.v1_12_2to1_12_1.Protocol1_12_2To1_12_1;
import com.viaversion.viabackwards.protocol.v1_12to1_11_1.Protocol1_12To1_11_1;
import com.viaversion.viabackwards.protocol.v1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viabackwards.protocol.v1_13_2to1_13_1.Protocol1_13_2To1_13_1;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viabackwards.protocol.v1_14_1to1_14.Protocol1_14_1To1_14;
import com.viaversion.viabackwards.protocol.v1_14_2to1_14_1.Protocol1_14_2To1_14_1;
import com.viaversion.viabackwards.protocol.v1_14_3to1_14_2.Protocol1_14_3To1_14_2;
import com.viaversion.viabackwards.protocol.v1_14_4to1_14_3.Protocol1_14_4To1_14_3;
import com.viaversion.viabackwards.protocol.v1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viabackwards.protocol.v1_15_1to1_15.Protocol1_15_1To1_15;
import com.viaversion.viabackwards.protocol.v1_15_2to1_15_1.Protocol1_15_2To1_15_1;
import com.viaversion.viabackwards.protocol.v1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viabackwards.protocol.v1_16_1to1_16.Protocol1_16_1To1_16;
import com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viabackwards.protocol.v1_16_3to1_16_2.Protocol1_16_3To1_16_2;
import com.viaversion.viabackwards.protocol.v1_16_4to1_16_3.Protocol1_16_4To1_16_3;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viabackwards.protocol.v1_17_1to1_17.Protocol1_17_1To1_17;
import com.viaversion.viabackwards.protocol.v1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viabackwards.protocol.v1_18_2to1_18.Protocol1_18_2To1_18;
import com.viaversion.viabackwards.protocol.v1_18to1_17_1.Protocol1_18To1_17_1;
import com.viaversion.viabackwards.protocol.v1_19_1to1_19.Protocol1_19_1To1_19;
import com.viaversion.viabackwards.protocol.v1_19_3to1_19_1.Protocol1_19_3To1_19_1;
import com.viaversion.viabackwards.protocol.v1_19_4to1_19_3.Protocol1_19_4To1_19_3;
import com.viaversion.viabackwards.protocol.v1_19to1_18_2.Protocol1_19To1_18_2;
import com.viaversion.viabackwards.protocol.v1_20_2to1_20.Protocol1_20_2To1_20;
import com.viaversion.viabackwards.protocol.v1_20_3to1_20_2.Protocol1_20_3To1_20_2;
import com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.Protocol1_20_5To1_20_3;
import com.viaversion.viabackwards.protocol.v1_20to1_19_4.Protocol1_20To1_19_4;
import com.viaversion.viabackwards.protocol.v1_21to1_20_5.Protocol1_21To1_20_5;
import com.viaversion.viabackwards.protocol.v1_9_1to1_9.Protocol1_9_1To1_9;
import com.viaversion.viabackwards.protocol.v1_9_3to1_9_1.Protocol1_9_3To1_9_1;
import com.viaversion.viabackwards.utils.VersionInfo;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.update.Version;
import java.io.File;
import java.util.Arrays;
import java.util.logging.Logger;

public interface ViaBackwardsPlatform {
    public static final String MINIMUM_VV_VERSION = "5.0.4";

    default public void init(File configFile) {
        ViaBackwardsConfig config = new ViaBackwardsConfig(configFile, this.getLogger());
        config.reload();
        Via.getManager().getConfigurationProvider().register(config);
        ViaBackwards.init(this, config);
        if (this.isOutdated()) {
            this.disable();
            return;
        }
        Via.getManager().getSubPlatforms().add(VersionInfo.getImplementationVersion());
        this.getLogger().info("Loading translations...");
        TranslatableRewriter.loadTranslatables();
        this.getLogger().info("Registering protocols...");
        ProtocolManager protocolManager = Via.getManager().getProtocolManager();
        protocolManager.registerProtocol((Protocol)new Protocol1_9_1To1_9(), ProtocolVersion.v1_9, ProtocolVersion.v1_9_1);
        protocolManager.registerProtocol((Protocol)new Protocol1_9_3To1_9_1(), Arrays.asList(ProtocolVersion.v1_9_1, ProtocolVersion.v1_9_2), ProtocolVersion.v1_9_3);
        protocolManager.registerProtocol((Protocol)new Protocol1_10To1_9_3(), ProtocolVersion.v1_9_3, ProtocolVersion.v1_10);
        protocolManager.registerProtocol((Protocol)new Protocol1_11To1_10(), ProtocolVersion.v1_10, ProtocolVersion.v1_11);
        protocolManager.registerProtocol((Protocol)new Protocol1_11_1To1_11(), ProtocolVersion.v1_11, ProtocolVersion.v1_11_1);
        protocolManager.registerProtocol((Protocol)new Protocol1_12To1_11_1(), ProtocolVersion.v1_11_1, ProtocolVersion.v1_12);
        protocolManager.registerProtocol((Protocol)new Protocol1_12_1To1_12(), ProtocolVersion.v1_12, ProtocolVersion.v1_12_1);
        protocolManager.registerProtocol((Protocol)new Protocol1_12_2To1_12_1(), ProtocolVersion.v1_12_1, ProtocolVersion.v1_12_2);
        protocolManager.registerProtocol((Protocol)new Protocol1_13To1_12_2(), ProtocolVersion.v1_12_2, ProtocolVersion.v1_13);
        protocolManager.registerProtocol((Protocol)new Protocol1_13_1To1_13(), ProtocolVersion.v1_13, ProtocolVersion.v1_13_1);
        protocolManager.registerProtocol((Protocol)new Protocol1_13_2To1_13_1(), ProtocolVersion.v1_13_1, ProtocolVersion.v1_13_2);
        protocolManager.registerProtocol((Protocol)new Protocol1_14To1_13_2(), ProtocolVersion.v1_13_2, ProtocolVersion.v1_14);
        protocolManager.registerProtocol((Protocol)new Protocol1_14_1To1_14(), ProtocolVersion.v1_14, ProtocolVersion.v1_14_1);
        protocolManager.registerProtocol((Protocol)new Protocol1_14_2To1_14_1(), ProtocolVersion.v1_14_1, ProtocolVersion.v1_14_2);
        protocolManager.registerProtocol((Protocol)new Protocol1_14_3To1_14_2(), ProtocolVersion.v1_14_2, ProtocolVersion.v1_14_3);
        protocolManager.registerProtocol((Protocol)new Protocol1_14_4To1_14_3(), ProtocolVersion.v1_14_3, ProtocolVersion.v1_14_4);
        protocolManager.registerProtocol((Protocol)new Protocol1_15To1_14_4(), ProtocolVersion.v1_14_4, ProtocolVersion.v1_15);
        protocolManager.registerProtocol((Protocol)new Protocol1_15_1To1_15(), ProtocolVersion.v1_15, ProtocolVersion.v1_15_1);
        protocolManager.registerProtocol((Protocol)new Protocol1_15_2To1_15_1(), ProtocolVersion.v1_15_1, ProtocolVersion.v1_15_2);
        protocolManager.registerProtocol((Protocol)new Protocol1_16To1_15_2(), ProtocolVersion.v1_15_2, ProtocolVersion.v1_16);
        protocolManager.registerProtocol((Protocol)new Protocol1_16_1To1_16(), ProtocolVersion.v1_16, ProtocolVersion.v1_16_1);
        protocolManager.registerProtocol((Protocol)new Protocol1_16_2To1_16_1(), ProtocolVersion.v1_16_1, ProtocolVersion.v1_16_2);
        protocolManager.registerProtocol((Protocol)new Protocol1_16_3To1_16_2(), ProtocolVersion.v1_16_2, ProtocolVersion.v1_16_3);
        protocolManager.registerProtocol((Protocol)new Protocol1_16_4To1_16_3(), ProtocolVersion.v1_16_3, ProtocolVersion.v1_16_4);
        protocolManager.registerProtocol((Protocol)new Protocol1_17To1_16_4(), ProtocolVersion.v1_16_4, ProtocolVersion.v1_17);
        protocolManager.registerProtocol((Protocol)new Protocol1_17_1To1_17(), ProtocolVersion.v1_17, ProtocolVersion.v1_17_1);
        protocolManager.registerProtocol((Protocol)new Protocol1_18To1_17_1(), ProtocolVersion.v1_17_1, ProtocolVersion.v1_18);
        protocolManager.registerProtocol((Protocol)new Protocol1_18_2To1_18(), ProtocolVersion.v1_18, ProtocolVersion.v1_18_2);
        protocolManager.registerProtocol((Protocol)new Protocol1_19To1_18_2(), ProtocolVersion.v1_18_2, ProtocolVersion.v1_19);
        protocolManager.registerProtocol((Protocol)new Protocol1_19_1To1_19(), ProtocolVersion.v1_19, ProtocolVersion.v1_19_1);
        protocolManager.registerProtocol((Protocol)new Protocol1_19_3To1_19_1(), ProtocolVersion.v1_19_1, ProtocolVersion.v1_19_3);
        protocolManager.registerProtocol((Protocol)new Protocol1_19_4To1_19_3(), ProtocolVersion.v1_19_3, ProtocolVersion.v1_19_4);
        protocolManager.registerProtocol((Protocol)new Protocol1_20To1_19_4(), ProtocolVersion.v1_19_4, ProtocolVersion.v1_20);
        protocolManager.registerProtocol((Protocol)new Protocol1_20_2To1_20(), ProtocolVersion.v1_20, ProtocolVersion.v1_20_2);
        protocolManager.registerProtocol((Protocol)new Protocol1_20_3To1_20_2(), ProtocolVersion.v1_20_2, ProtocolVersion.v1_20_3);
        protocolManager.registerProtocol((Protocol)new Protocol1_20_5To1_20_3(), ProtocolVersion.v1_20_3, ProtocolVersion.v1_20_5);
        protocolManager.registerProtocol((Protocol)new Protocol1_21To1_20_5(), ProtocolVersion.v1_20_5, ProtocolVersion.v1_21);
    }

    public Logger getLogger();

    default public boolean isOutdated() {
        String vvVersion = Via.getPlatform().getPluginVersion();
        if (vvVersion != null && new Version(vvVersion).compareTo(new Version("5.0.4--")) < 0) {
            this.getLogger().severe("================================");
            String string = vvVersion;
            this.getLogger().severe("YOUR VIAVERSION IS OUTDATED (you are running " + string + ")");
            this.getLogger().severe("PLEASE USE VIAVERSION 5.0.4 OR NEWER");
            this.getLogger().severe("LINK: https://ci.viaversion.com/");
            this.getLogger().severe("VIABACKWARDS WILL NOW DISABLE");
            this.getLogger().severe("================================");
            return true;
        }
        return false;
    }

    public void disable();

    public File getDataFolder();
}

