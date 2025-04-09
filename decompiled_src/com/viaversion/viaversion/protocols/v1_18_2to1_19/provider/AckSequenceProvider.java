/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_18_2to1_19.provider;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.packet.ClientboundPackets1_19;

public class AckSequenceProvider
implements Provider {
    public void handleSequence(UserConnection connection, int sequence) {
        PacketWrapper ackPacket = PacketWrapper.create(ClientboundPackets1_19.BLOCK_CHANGED_ACK, connection);
        ackPacket.write(Types.VAR_INT, sequence);
        ackPacket.send(Protocol1_18_2To1_19.class);
    }
}

