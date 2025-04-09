/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.provider;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;

public class OldAuthProvider
implements Provider {
    public void sendAuthRequest(UserConnection user, String serverId) throws Throwable {
        throw new IllegalStateException("Online mode auth is not implemented!");
    }
}

