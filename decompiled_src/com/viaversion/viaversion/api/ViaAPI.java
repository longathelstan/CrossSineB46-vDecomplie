/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.LegacyViaAPI;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import io.netty.buffer.ByteBuf;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ViaAPI<T> {
    default public int majorVersion() {
        return 5;
    }

    default public int apiVersion() {
        return 26;
    }

    public ServerProtocolVersion getServerVersion();

    default public int getPlayerVersion(T player) {
        return this.getPlayerProtocolVersion(player).getVersion();
    }

    public ProtocolVersion getPlayerProtocolVersion(T var1);

    default public int getPlayerVersion(UUID uuid) {
        return this.getPlayerProtocolVersion(uuid).getVersion();
    }

    public ProtocolVersion getPlayerProtocolVersion(UUID var1);

    public boolean isInjected(UUID var1);

    public @Nullable UserConnection getConnection(UUID var1);

    public String getVersion();

    public void sendRawPacket(T var1, ByteBuf var2);

    public void sendRawPacket(UUID var1, ByteBuf var2);

    @Deprecated
    default public SortedSet<Integer> getSupportedVersions() {
        return this.getSupportedProtocolVersions().stream().map(ProtocolVersion::getVersion).collect(Collectors.toCollection(TreeSet::new));
    }

    @Deprecated
    default public SortedSet<Integer> getFullSupportedVersions() {
        return this.getFullSupportedProtocolVersions().stream().map(ProtocolVersion::getVersion).collect(Collectors.toCollection(TreeSet::new));
    }

    public SortedSet<ProtocolVersion> getSupportedProtocolVersions();

    public SortedSet<ProtocolVersion> getFullSupportedProtocolVersions();

    public LegacyViaAPI<T> legacyAPI();
}

