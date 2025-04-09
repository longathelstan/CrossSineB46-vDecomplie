/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.provider;

import com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.provider.NoopTransferProvider;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;

@FunctionalInterface
public interface TransferProvider
extends Provider {
    public static final TransferProvider NOOP = new NoopTransferProvider();

    public void connectToServer(UserConnection var1, String var2, int var3);
}

