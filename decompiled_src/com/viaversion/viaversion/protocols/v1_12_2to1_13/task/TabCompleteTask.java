/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.task;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.storage.TabCompleteTracker;

public class TabCompleteTask
implements Runnable {
    @Override
    public void run() {
        for (UserConnection info : Via.getManager().getConnectionManager().getConnections()) {
            if (info.getProtocolInfo() == null || !info.getProtocolInfo().getPipeline().contains(Protocol1_12_2To1_13.class) || !info.getChannel().isOpen()) continue;
            info.get(TabCompleteTracker.class).sendPacketToServer(info);
        }
    }
}

