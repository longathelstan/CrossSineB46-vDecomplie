/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.provider;

import com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.provider.TransferProvider;
import com.viaversion.viaversion.api.connection.UserConnection;

final class NoopTransferProvider
implements TransferProvider {
    NoopTransferProvider() {
    }

    @Override
    public void connectToServer(UserConnection connection, String host, int port) {
    }
}

