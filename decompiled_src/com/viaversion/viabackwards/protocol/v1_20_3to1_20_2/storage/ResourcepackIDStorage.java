/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20_3to1_20_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.Objects;
import java.util.UUID;

public final class ResourcepackIDStorage
implements StorableObject {
    private final UUID uuid;

    public ResourcepackIDStorage(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean clearOnServerSwitch() {
        return false;
    }

    public UUID uuid() {
        return this.uuid;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ResourcepackIDStorage)) {
            return false;
        }
        ResourcepackIDStorage resourcepackIDStorage = (ResourcepackIDStorage)object;
        return Objects.equals(this.uuid, resourcepackIDStorage.uuid);
    }

    public int hashCode() {
        return 0 * 31 + Objects.hashCode(this.uuid);
    }

    public String toString() {
        return String.format("%s[uuid=%s]", this.getClass().getSimpleName(), Objects.toString(this.uuid));
    }
}

