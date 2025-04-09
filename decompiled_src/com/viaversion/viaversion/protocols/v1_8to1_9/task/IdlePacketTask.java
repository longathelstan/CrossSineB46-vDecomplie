/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_8to1_9.task;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.MovementTransmitterProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.MovementTracker;

public class IdlePacketTask
implements Runnable {
    @Override
    public void run() {
        for (UserConnection info : Via.getManager().getConnectionManager().getConnections()) {
            long nextIdleUpdate;
            MovementTracker movementTracker;
            ProtocolInfo protocolInfo = info.getProtocolInfo();
            if (protocolInfo == null || !protocolInfo.getPipeline().contains(Protocol1_8To1_9.class) || (movementTracker = info.get(MovementTracker.class)) == null || (nextIdleUpdate = movementTracker.getNextIdlePacket()) > System.currentTimeMillis() || !info.getChannel().isOpen()) continue;
            Via.getManager().getProviders().get(MovementTransmitterProvider.class).sendPlayer(info);
        }
    }
}

