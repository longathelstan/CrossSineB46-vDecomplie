/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.tasks.v1_18_2to1_19;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.packet.ClientboundPackets1_19;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.storage.SequenceStorage;
import java.util.logging.Level;

public final class AckSequenceTask
implements Runnable {
    private final UserConnection connection;
    private final SequenceStorage sequenceStorage;

    public AckSequenceTask(UserConnection connection, SequenceStorage sequenceStorage) {
        this.connection = connection;
        this.sequenceStorage = sequenceStorage;
    }

    @Override
    public void run() {
        int sequence = this.sequenceStorage.setSequenceId(-1);
        try {
            PacketWrapper ackPacket = PacketWrapper.create(ClientboundPackets1_19.BLOCK_CHANGED_ACK, this.connection);
            ackPacket.write(Types.VAR_INT, sequence);
            ackPacket.scheduleSend(Protocol1_18_2To1_19.class);
        }
        catch (Exception e) {
            Via.getPlatform().getLogger().log(Level.WARNING, "Failed to send block changed ack packet", e);
        }
    }
}

