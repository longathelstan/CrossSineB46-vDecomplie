/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.data.EntityNameMappings1_12_2;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.provider.BackwardsBlockEntityProvider;

public class SpawnerHandler
implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
    @Override
    public CompoundTag transform(int blockId, CompoundTag tag) {
        StringTag idTag;
        CompoundTag dataTag = tag.getCompoundTag("SpawnData");
        if (dataTag != null && (idTag = dataTag.getStringTag("id")) != null) {
            idTag.setValue(EntityNameMappings1_12_2.rewrite(idTag.getValue()));
        }
        return tag;
    }
}

