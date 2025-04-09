/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.connection;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.UUID;

public class ProtocolInfoImpl
implements ProtocolInfo {
    private State clientState = State.HANDSHAKE;
    private State serverState = State.HANDSHAKE;
    private ProtocolVersion serverProtocolVersion = ProtocolVersion.unknown;
    private ProtocolVersion protocolVersion = ProtocolVersion.unknown;
    private String username;
    private UUID uuid;
    private ProtocolPipeline pipeline;

    @Override
    public State getClientState() {
        return this.clientState;
    }

    @Override
    public void setClientState(State clientState) {
        if (Via.getManager().debugHandler().enabled()) {
            UUID uUID = this.uuid;
            State state = clientState;
            State state2 = this.clientState;
            Via.getPlatform().getLogger().info("Client state changed from " + (Object)((Object)state2) + " to " + (Object)((Object)state) + " for " + uUID);
        }
        this.clientState = clientState;
    }

    @Override
    public State getServerState() {
        return this.serverState;
    }

    @Override
    public void setServerState(State serverState) {
        if (Via.getManager().debugHandler().enabled()) {
            UUID uUID = this.uuid;
            State state = serverState;
            State state2 = this.serverState;
            Via.getPlatform().getLogger().info("Server state changed from " + (Object)((Object)state2) + " to " + (Object)((Object)state) + " for " + uUID);
        }
        this.serverState = serverState;
    }

    @Override
    public ProtocolVersion protocolVersion() {
        return this.protocolVersion;
    }

    @Override
    public void setProtocolVersion(ProtocolVersion protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    @Override
    public ProtocolVersion serverProtocolVersion() {
        return this.serverProtocolVersion;
    }

    @Override
    public void setServerProtocolVersion(ProtocolVersion serverProtocolVersion) {
        this.serverProtocolVersion = serverProtocolVersion;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public ProtocolPipeline getPipeline() {
        return this.pipeline;
    }

    @Override
    public void setPipeline(ProtocolPipeline pipeline) {
        this.pipeline = pipeline;
    }

    public String toString() {
        UUID uUID = this.uuid;
        String string = this.username;
        ProtocolVersion protocolVersion = this.serverProtocolVersion;
        ProtocolVersion protocolVersion2 = this.protocolVersion;
        State state = this.serverState;
        State state2 = this.clientState;
        return "ProtocolInfo{clientState=" + (Object)((Object)state2) + ", serverState=" + (Object)((Object)state) + ", protocolVersion=" + protocolVersion2 + ", serverProtocolVersion=" + protocolVersion + ", username='" + string + "', uuid=" + uUID + "}";
    }
}

