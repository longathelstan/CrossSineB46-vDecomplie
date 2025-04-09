/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public final class SecureChatStorage
implements StorableObject {
    private boolean enforcesSecureChat;

    public void setEnforcesSecureChat(boolean enforcesSecureChat) {
        this.enforcesSecureChat = enforcesSecureChat;
    }

    public boolean enforcesSecureChat() {
        return this.enforcesSecureChat;
    }
}

