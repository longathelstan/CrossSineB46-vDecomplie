/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.provider;

import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.CompressionStatusTracker;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;
import io.netty.channel.ChannelHandler;

public abstract class CompressionHandlerProvider
implements Provider {
    public abstract void onHandleLoginCompressionPacket(UserConnection var1, int var2);

    public abstract void onTransformPacket(UserConnection var1);

    public abstract ChannelHandler getEncoder(int var1);

    public abstract ChannelHandler getDecoder(int var1);

    public boolean isCompressionEnabled(UserConnection user) {
        return user.get(CompressionStatusTracker.class).removeCompression;
    }

    public void setCompressionEnabled(UserConnection user, boolean enabled) {
        user.get(CompressionStatusTracker.class).removeCompression = enabled;
    }
}

