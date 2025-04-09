/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_19to1_18_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.GlobalBlockPosition;
import java.util.Objects;

public final class LastDeathPosition
implements StorableObject {
    private final GlobalBlockPosition position;

    public LastDeathPosition(GlobalBlockPosition position) {
        this.position = position;
    }

    public GlobalBlockPosition position() {
        return this.position;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LastDeathPosition)) {
            return false;
        }
        LastDeathPosition lastDeathPosition = (LastDeathPosition)object;
        return Objects.equals(this.position, lastDeathPosition.position);
    }

    public int hashCode() {
        return 0 * 31 + Objects.hashCode(this.position);
    }

    public String toString() {
        return String.format("%s[position=%s]", this.getClass().getSimpleName(), Objects.toString(this.position));
    }
}

