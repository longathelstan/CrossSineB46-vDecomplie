/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.provider.BackwardsBlockEntityProvider;

public class BedHandler
implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
    @Override
    public CompoundTag transform(int blockId, CompoundTag tag) {
        int offset = blockId - 748;
        int color = offset >> 4;
        tag.putInt("color", color);
        return tag;
    }
}

