/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_19_1to1_19_3.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class NonceStorage1_19_3
implements StorableObject {
    private final byte @Nullable [] nonce;

    public NonceStorage1_19_3(byte @Nullable [] nonce) {
        this.nonce = nonce;
    }

    public byte @Nullable [] nonce() {
        return this.nonce;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof NonceStorage1_19_3)) {
            return false;
        }
        NonceStorage1_19_3 nonceStorage1_19_3 = (NonceStorage1_19_3)object;
        return Objects.equals(this.nonce, nonceStorage1_19_3.nonce);
    }

    public int hashCode() {
        return 0 * 31 + Objects.hashCode(this.nonce);
    }

    public String toString() {
        return String.format("%s[nonce=%s]", this.getClass().getSimpleName(), Objects.toString(this.nonce));
    }
}

