/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.viaaprilfools.api;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.protocol.RedirectProtocolVersion;
import java.util.ArrayList;
import java.util.List;

public class AprilFoolsProtocolVersion {
    public static final List<ProtocolVersion> PROTOCOLS = new ArrayList<ProtocolVersion>();
    public static final List<ProtocolVersion> SNAPSHOTS_PROTOCOLS = new ArrayList<ProtocolVersion>();
    public static final List<ProtocolVersion> APRIL_FOOLS_PROTOCOLS = new ArrayList<ProtocolVersion>();
    public static final ProtocolVersion s3d_shareware = AprilFoolsProtocolVersion.registerAprilFools(1, "3D Shareware", ProtocolVersion.v1_13_2);
    public static final ProtocolVersion s20w14infinite = AprilFoolsProtocolVersion.registerAprilFools(709, "20w14infinite", ProtocolVersion.v1_15_2);
    public static final ProtocolVersion sCombatTest8c = AprilFoolsProtocolVersion.registerSnapshot(803, "Combat Test 8c", ProtocolVersion.v1_16_1);

    private static ProtocolVersion registerSnapshot(int version, String name, ProtocolVersion origin) {
        RedirectProtocolVersion protocolVersion = new RedirectProtocolVersion(version, name, origin);
        ProtocolVersion.register(protocolVersion);
        PROTOCOLS.add(protocolVersion);
        SNAPSHOTS_PROTOCOLS.add(protocolVersion);
        return protocolVersion;
    }

    private static ProtocolVersion registerAprilFools(int version, String name, ProtocolVersion origin) {
        RedirectProtocolVersion protocolVersion = new RedirectProtocolVersion(version, name, origin);
        ProtocolVersion.register(protocolVersion);
        PROTOCOLS.add(protocolVersion);
        APRIL_FOOLS_PROTOCOLS.add(protocolVersion);
        return protocolVersion;
    }
}

