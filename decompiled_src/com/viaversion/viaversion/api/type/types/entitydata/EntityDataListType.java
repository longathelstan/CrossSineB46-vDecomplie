/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.entitydata;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListTypeTemplate;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

public final class EntityDataListType
extends EntityDataListTypeTemplate {
    private final Type<EntityData> type;

    public EntityDataListType(Type<EntityData> type) {
        Preconditions.checkNotNull(type);
        this.type = type;
    }

    @Override
    public List<EntityData> read(ByteBuf buffer) {
        EntityData data;
        ArrayList<EntityData> list = new ArrayList<EntityData>();
        do {
            if ((data = (EntityData)this.type.read(buffer)) == null) continue;
            list.add(data);
        } while (data != null);
        return list;
    }

    @Override
    public void write(ByteBuf buffer, List<EntityData> object) {
        for (EntityData data : object) {
            this.type.write(buffer, data);
        }
        this.type.write(buffer, null);
    }
}

