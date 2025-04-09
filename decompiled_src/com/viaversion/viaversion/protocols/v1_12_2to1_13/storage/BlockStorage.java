/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import java.util.HashMap;
import java.util.Map;

public class BlockStorage
implements StorableObject {
    static final IntSet WHITELIST;
    final Map<BlockPosition, ReplacementData> blocks = new HashMap<BlockPosition, ReplacementData>();

    public void store(BlockPosition position, int block) {
        this.store(position, block, -1);
    }

    public void store(BlockPosition position, int block, int replacementId) {
        if (!WHITELIST.contains(block)) {
            return;
        }
        this.blocks.put(position, new ReplacementData(block, replacementId));
    }

    public boolean isWelcome(int block) {
        return WHITELIST.contains(block);
    }

    public boolean contains(BlockPosition position) {
        return this.blocks.containsKey(position);
    }

    public ReplacementData get(BlockPosition position) {
        return this.blocks.get(position);
    }

    public ReplacementData remove(BlockPosition position) {
        return this.blocks.remove(position);
    }

    static {
        int i;
        WHITELIST = new IntOpenHashSet(46);
        WHITELIST.add(5266);
        for (i = 0; i < 16; ++i) {
            WHITELIST.add(972 + i);
        }
        for (i = 0; i < 20; ++i) {
            WHITELIST.add(6854 + i);
        }
        for (i = 0; i < 4; ++i) {
            WHITELIST.add(7110 + i);
        }
        for (i = 0; i < 5; ++i) {
            WHITELIST.add(5447 + i);
        }
    }

    public static final class ReplacementData {
        final int original;
        int replacement;

        public ReplacementData(int original, int replacement) {
            this.original = original;
            this.replacement = replacement;
        }

        public int getOriginal() {
            return this.original;
        }

        public int getReplacement() {
            return this.replacement;
        }

        public void setReplacement(int replacement) {
            this.replacement = replacement;
        }
    }
}

