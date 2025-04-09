/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialoader.util;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProtocolVersionList {
    public static List<ProtocolVersion> getProtocolsNewToOld() {
        ArrayList<ProtocolVersion> protocolVersions = new ArrayList<ProtocolVersion>(ProtocolVersion.getProtocols());
        Collections.reverse(protocolVersions);
        return Collections.unmodifiableList(protocolVersions);
    }
}

