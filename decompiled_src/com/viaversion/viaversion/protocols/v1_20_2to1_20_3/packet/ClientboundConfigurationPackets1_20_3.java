/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet;

import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundPacket1_20_3;

public enum ClientboundConfigurationPackets1_20_3 implements ClientboundPacket1_20_3
{
    CUSTOM_PAYLOAD,
    DISCONNECT,
    FINISH_CONFIGURATION,
    KEEP_ALIVE,
    PING,
    REGISTRY_DATA,
    RESOURCE_PACK_POP,
    RESOURCE_PACK_PUSH,
    UPDATE_ENABLED_FEATURES,
    UPDATE_TAGS;


    @Override
    public int getId() {
        return this.ordinal();
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public State state() {
        return State.CONFIGURATION;
    }
}

