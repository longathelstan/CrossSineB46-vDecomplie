/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20to1_19_4.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import java.util.Objects;

public final class BackSignEditStorage
implements StorableObject {
    private final BlockPosition position;

    public BackSignEditStorage(BlockPosition position) {
        this.position = position;
    }

    public BlockPosition position() {
        return this.position;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BackSignEditStorage)) {
            return false;
        }
        BackSignEditStorage backSignEditStorage = (BackSignEditStorage)object;
        return Objects.equals(this.position, backSignEditStorage.position);
    }

    public int hashCode() {
        return 0 * 31 + Objects.hashCode(this.position);
    }

    public String toString() {
        return String.format("%s[position=%s]", this.getClass().getSimpleName(), Objects.toString(this.position));
    }
}

