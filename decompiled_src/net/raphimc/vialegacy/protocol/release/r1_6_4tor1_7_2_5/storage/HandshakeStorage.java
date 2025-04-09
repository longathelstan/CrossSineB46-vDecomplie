/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class HandshakeStorage
implements StorableObject {
    private final String hostname;
    private final int port;

    public HandshakeStorage(String hostName, int port) {
        this.hostname = hostName;
        this.port = port;
    }

    public String getHostname() {
        return this.hostname;
    }

    public int getPort() {
        return this.port;
    }
}

