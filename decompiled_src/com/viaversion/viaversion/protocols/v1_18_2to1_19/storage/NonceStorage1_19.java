/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_18_2to1_19.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.Objects;

public final class NonceStorage1_19
implements StorableObject {
    private final byte[] nonce;

    public NonceStorage1_19(byte[] nonce) {
        this.nonce = nonce;
    }

    public byte[] nonce() {
        return this.nonce;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof NonceStorage1_19)) {
            return false;
        }
        NonceStorage1_19 nonceStorage1_19 = (NonceStorage1_19)object;
        return Objects.equals(this.nonce, nonceStorage1_19.nonce);
    }

    public int hashCode() {
        return 0 * 31 + Objects.hashCode(this.nonce);
    }

    public String toString() {
        return String.format("%s[nonce=%s]", this.getClass().getSimpleName(), Objects.toString(this.nonce));
    }
}

