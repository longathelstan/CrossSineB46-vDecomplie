/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.rewriter.entitydata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface EntityDataHandlerEvent {
    public UserConnection user();

    public int entityId();

    public @Nullable TrackedEntity trackedEntity();

    default public @Nullable EntityType entityType() {
        return this.trackedEntity() != null ? this.trackedEntity().entityType() : null;
    }

    default public int index() {
        return this.data().id();
    }

    default public void setIndex(int index2) {
        this.data().setId(index2);
    }

    public EntityData data();

    public void cancel();

    public boolean cancelled();

    public @Nullable EntityData dataAtIndex(int var1);

    public List<EntityData> dataList();

    public @Nullable List<EntityData> extraData();

    default public boolean hasExtraData() {
        return this.extraData() != null;
    }

    public void createExtraData(EntityData var1);
}

