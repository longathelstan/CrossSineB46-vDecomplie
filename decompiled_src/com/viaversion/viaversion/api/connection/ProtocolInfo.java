/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.connection;

import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ProtocolInfo {
    public State getClientState();

    public State getServerState();

    default public State getState(Direction direction) {
        return direction == Direction.CLIENTBOUND ? this.getServerState() : this.getClientState();
    }

    default public void setState(State state) {
        this.setClientState(state);
        this.setServerState(state);
    }

    public void setClientState(State var1);

    public void setServerState(State var1);

    public ProtocolVersion protocolVersion();

    public void setProtocolVersion(ProtocolVersion var1);

    public ProtocolVersion serverProtocolVersion();

    public void setServerProtocolVersion(ProtocolVersion var1);

    @Deprecated
    default public int getProtocolVersion() {
        return this.protocolVersion() != null ? this.protocolVersion().getVersion() : -1;
    }

    @Deprecated
    default public int getServerProtocolVersion() {
        return this.serverProtocolVersion() != null ? this.serverProtocolVersion().getVersion() : -1;
    }

    public @Nullable String getUsername();

    public void setUsername(String var1);

    public @Nullable UUID getUuid();

    public void setUuid(UUID var1);

    public ProtocolPipeline getPipeline();

    public void setPipeline(ProtocolPipeline var1);
}

