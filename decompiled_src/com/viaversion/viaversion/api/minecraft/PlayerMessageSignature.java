/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import java.util.Objects;
import java.util.UUID;

public final class PlayerMessageSignature {
    private final UUID uuid;
    private final byte[] signatureBytes;

    public PlayerMessageSignature(UUID uuid, byte[] signatureBytes) {
        this.uuid = uuid;
        this.signatureBytes = signatureBytes;
    }

    public UUID uuid() {
        return this.uuid;
    }

    public byte[] signatureBytes() {
        return this.signatureBytes;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PlayerMessageSignature)) {
            return false;
        }
        PlayerMessageSignature playerMessageSignature = (PlayerMessageSignature)object;
        return Objects.equals(this.uuid, playerMessageSignature.uuid) && Objects.equals(this.signatureBytes, playerMessageSignature.signatureBytes);
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.uuid)) * 31 + Objects.hashCode(this.signatureBytes);
    }

    public String toString() {
        return String.format("%s[uuid=%s, signatureBytes=%s]", this.getClass().getSimpleName(), Objects.toString(this.uuid), Objects.toString(this.signatureBytes));
    }
}

