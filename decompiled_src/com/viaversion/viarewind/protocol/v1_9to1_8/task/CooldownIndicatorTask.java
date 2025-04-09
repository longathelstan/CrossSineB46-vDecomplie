/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_9to1_8.task;

import com.viaversion.viarewind.protocol.v1_9to1_8.storage.CooldownStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;

public class CooldownIndicatorTask
implements Runnable {
    @Override
    public void run() {
        for (UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
            connection.get(CooldownStorage.class).tick(connection);
        }
    }
}

