/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_13_2to1_14.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;

public class MappingData1_14
extends MappingDataBase {
    private IntSet motionBlocking;
    private IntSet nonFullBlocks;

    public MappingData1_14() {
        super("1.13.2", "1.14");
    }

    @Override
    public void loadExtras(CompoundTag data) {
        CompoundTag heightmap = MappingDataLoader.INSTANCE.loadNBT("heightmap-1.14.nbt");
        IntArrayTag motionBlocking = heightmap.getIntArrayTag("motionBlocking");
        this.motionBlocking = new IntOpenHashSet(motionBlocking.getValue());
        if (Via.getConfig().isNonFullBlockLightFix()) {
            IntArrayTag nonFullBlocks = heightmap.getIntArrayTag("nonFullBlocks");
            this.nonFullBlocks = new IntOpenHashSet(nonFullBlocks.getValue());
        }
    }

    public IntSet getMotionBlocking() {
        return this.motionBlocking;
    }

    public IntSet getNonFullBlocks() {
        return this.nonFullBlocks;
    }
}

