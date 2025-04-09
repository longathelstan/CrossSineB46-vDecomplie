/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.viaaprilfools.platform;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.io.File;
import java.util.logging.Logger;
import net.raphimc.viaaprilfools.ViaAprilFools;
import net.raphimc.viaaprilfools.ViaAprilFoolsConfig;
import net.raphimc.viaaprilfools.api.AprilFoolsProtocolVersion;
import net.raphimc.viaaprilfools.protocol.s20w14infinitetov1_16.Protocol20w14infiniteTo1_16;
import net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14.Protocol3D_SharewareTo1_14;
import net.raphimc.viaaprilfools.protocol.scombattest8ctov1_16_2.ProtocolCombatTest8cTo1_16_2;
import net.raphimc.viaaprilfools.protocol.v1_14tos3d_shareware.Protocol1_14To3D_Shareware;
import net.raphimc.viaaprilfools.protocol.v1_16_2toscombattest8c.Protocol1_16_2ToCombatTest8c;

public interface ViaAprilFoolsPlatform {
    default public void init(File configFile) {
        ViaAprilFoolsConfig config = new ViaAprilFoolsConfig(configFile, this.getLogger());
        config.reload();
        ViaAprilFools.init(this, config);
        Via.getManager().getConfigurationProvider().register(config);
        Via.getManager().getSubPlatforms().add("git-ViaAprilFools-3.0.2-SNAPSHOT:c0f975c");
        ProtocolManager protocolManager = Via.getManager().getProtocolManager();
        protocolManager.registerProtocol((Protocol)new Protocol3D_SharewareTo1_14(), ProtocolVersion.v1_14, AprilFoolsProtocolVersion.s3d_shareware);
        protocolManager.registerProtocol((Protocol)new Protocol1_14To3D_Shareware(), AprilFoolsProtocolVersion.s3d_shareware, ProtocolVersion.v1_14);
        protocolManager.registerProtocol((Protocol)new Protocol20w14infiniteTo1_16(), ProtocolVersion.v1_16, AprilFoolsProtocolVersion.s20w14infinite);
        protocolManager.registerProtocol((Protocol)new ProtocolCombatTest8cTo1_16_2(), ProtocolVersion.v1_16_2, AprilFoolsProtocolVersion.sCombatTest8c);
        protocolManager.registerProtocol((Protocol)new Protocol1_16_2ToCombatTest8c(), AprilFoolsProtocolVersion.sCombatTest8c, ProtocolVersion.v1_16_2);
    }

    public Logger getLogger();

    public File getDataFolder();
}

