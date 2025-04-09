/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.rewriter.entitydata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.rewriter.entitydata.EntityDataHandlerEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EntityDataHandlerEventImpl
implements EntityDataHandlerEvent {
    private final UserConnection connection;
    private final TrackedEntity trackedEntity;
    private final int entityId;
    private final List<EntityData> dataList;
    private final EntityData data;
    private List<EntityData> extraData;
    private boolean cancel;

    public EntityDataHandlerEventImpl(UserConnection connection, @Nullable TrackedEntity trackedEntity, int entityId, EntityData data, List<EntityData> dataList) {
        this.connection = connection;
        this.trackedEntity = trackedEntity;
        this.entityId = entityId;
        this.data = data;
        this.dataList = dataList;
    }

    @Override
    public UserConnection user() {
        return this.connection;
    }

    @Override
    public int entityId() {
        return this.entityId;
    }

    @Override
    public @Nullable TrackedEntity trackedEntity() {
        return this.trackedEntity;
    }

    @Override
    public EntityData data() {
        return this.data;
    }

    @Override
    public void cancel() {
        this.cancel = true;
    }

    @Override
    public boolean cancelled() {
        return this.cancel;
    }

    @Override
    public @Nullable EntityData dataAtIndex(int index2) {
        for (EntityData data : this.dataList) {
            if (index2 != data.id()) continue;
            return data;
        }
        return null;
    }

    @Override
    public List<EntityData> dataList() {
        return Collections.unmodifiableList(this.dataList);
    }

    @Override
    public @Nullable List<EntityData> extraData() {
        return this.extraData;
    }

    @Override
    public void createExtraData(EntityData entityData) {
        if (this.extraData == null) {
            this.extraData = new ArrayList<EntityData>();
        }
        this.extraData.add(entityData);
    }
}

