/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.data.entity;

import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;

public interface TrackedEntity {
    public EntityType entityType();

    public StoredEntityData data();

    public boolean hasData();

    public boolean hasSentEntityData();

    public void sentEntityData(boolean var1);
}

