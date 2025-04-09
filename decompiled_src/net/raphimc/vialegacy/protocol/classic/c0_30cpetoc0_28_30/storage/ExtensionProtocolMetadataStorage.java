/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.EnumMap;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.data.ClassicProtocolExtension;

public class ExtensionProtocolMetadataStorage
implements StorableObject {
    private String serverSoftwareName = "classic";
    private short extensionCount = (short)-1;
    private short receivedExtensions = 0;
    private final EnumMap<ClassicProtocolExtension, Integer> serverExtensions = new EnumMap(ClassicProtocolExtension.class);

    public void setServerSoftwareName(String serverSoftwareName) {
        if (serverSoftwareName.isEmpty()) {
            return;
        }
        this.serverSoftwareName = serverSoftwareName;
    }

    public String getServerSoftwareName() {
        return this.serverSoftwareName;
    }

    public void setExtensionCount(short extensionCount) {
        this.extensionCount = extensionCount;
    }

    public short getExtensionCount() {
        return this.extensionCount;
    }

    public void incrementReceivedExtensions() {
        this.receivedExtensions = (short)(this.receivedExtensions + 1);
    }

    public short getReceivedExtensions() {
        return this.receivedExtensions;
    }

    public void addServerExtension(ClassicProtocolExtension extension, int version) {
        this.serverExtensions.put(extension, version);
    }

    public boolean hasServerExtension(ClassicProtocolExtension extension, int ... versions) {
        Integer extensionVersion = this.serverExtensions.get((Object)extension);
        if (extensionVersion == null) {
            return false;
        }
        if (versions.length == 0) {
            return true;
        }
        for (int version : versions) {
            if (version != extensionVersion) continue;
            return true;
        }
        return false;
    }
}

