/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_8to1_9.provider;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.MovementTracker;
import java.util.logging.Level;

public class MovementTransmitterProvider
implements Provider {
    public void sendPlayer(UserConnection userConnection) {
        if (userConnection.getProtocolInfo().getClientState() != State.PLAY || userConnection.getEntityTracker(Protocol1_8To1_9.class).clientEntityId() == -1) {
            return;
        }
        MovementTracker movementTracker = userConnection.get(MovementTracker.class);
        movementTracker.incrementIdlePacket();
        try {
            PacketWrapper playerMovement = PacketWrapper.create(ServerboundPackets1_8.MOVE_PLAYER_STATUS_ONLY, userConnection);
            playerMovement.write(Types.BOOLEAN, movementTracker.isGround());
            playerMovement.scheduleSendToServer(Protocol1_8To1_9.class);
        }
        catch (Throwable e) {
            Via.getPlatform().getLogger().log(Level.WARNING, "Failed to send player movement packet", e);
        }
    }
}

