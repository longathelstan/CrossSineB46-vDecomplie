/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.util.Pair;

public class NoteBlockStorage
implements StorableObject {
    private static final int MAX_NOTE_ID = 24;
    private final Object2IntMap<BlockPosition> noteBlockUpdates = new Object2IntOpenHashMap<BlockPosition>();

    public void storeNoteBlockUpdate(BlockPosition position, int blockStateId) {
        this.noteBlockUpdates.put(position, blockStateId);
    }

    public Pair<Integer, Integer> getNoteBlockUpdate(BlockPosition position) {
        if (!this.noteBlockUpdates.containsKey(position)) {
            return null;
        }
        int relativeBlockState = this.noteBlockUpdates.removeInt(position) - 249;
        return new Pair<Integer, Integer>((relativeBlockState /= 2) / 24 + 1, relativeBlockState % 24 + 1);
    }

    public void clear() {
        this.noteBlockUpdates.clear();
    }
}

