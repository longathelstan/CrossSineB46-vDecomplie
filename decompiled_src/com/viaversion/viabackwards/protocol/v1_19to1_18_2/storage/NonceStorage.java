/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_19to1_18_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class NonceStorage
implements StorableObject {
    private final byte @Nullable [] nonce;

    public NonceStorage(byte @Nullable [] nonce) {
        this.nonce = nonce;
    }

    public byte @Nullable [] nonce() {
        return this.nonce;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof NonceStorage)) {
            return false;
        }
        NonceStorage nonceStorage = (NonceStorage)object;
        return Objects.equals(this.nonce, nonceStorage.nonce);
    }

    public int hashCode() {
        return 0 * 31 + Objects.hashCode(this.nonce);
    }

    public String toString() {
        return String.format("%s[nonce=%s]", this.getClass().getSimpleName(), Objects.toString(this.nonce));
    }
}

