/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.model;

import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.util.IdAndData;

public class PendingBlockEntry {
    private final BlockPosition position;
    private final IdAndData block;
    private int countdown = 80;

    public PendingBlockEntry(BlockPosition position, IdAndData block) {
        this.position = position;
        this.block = block;
    }

    public BlockPosition getPosition() {
        return this.position;
    }

    public IdAndData getBlock() {
        return this.block;
    }

    public boolean decrementAndCheckIsExpired() {
        return --this.countdown <= 0;
    }
}

