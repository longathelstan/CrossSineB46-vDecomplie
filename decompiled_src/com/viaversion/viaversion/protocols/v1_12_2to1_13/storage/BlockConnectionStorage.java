/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.storage;

import com.google.common.collect.EvictingQueue;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BlockConnectionStorage
implements StorableObject {
    static Constructor<?> fastUtilLongObjectHashMap;
    final Map<Long, SectionData> blockStorage = this.createLongObjectMap();
    final Queue<BlockPosition> modified = EvictingQueue.create((int)5);
    long lastIndex = -1L;
    SectionData lastSection;

    public static void init() {
    }

    public void store(int x, int y, int z, int blockState) {
        long index2 = BlockConnectionStorage.getChunkSectionIndex(x, y, z);
        SectionData section = this.getSection(index2);
        if (section == null) {
            if (blockState == 0) {
                return;
            }
            section = new SectionData();
            this.blockStorage.put(index2, section);
            this.lastSection = section;
            this.lastIndex = index2;
        }
        section.setBlockAt(x, y, z, blockState);
    }

    public int get(int x, int y, int z) {
        long pair = BlockConnectionStorage.getChunkSectionIndex(x, y, z);
        SectionData section = this.getSection(pair);
        if (section == null) {
            return 0;
        }
        return section.blockAt(x, y, z);
    }

    public void remove(int x, int y, int z) {
        long index2 = BlockConnectionStorage.getChunkSectionIndex(x, y, z);
        SectionData section = this.getSection(index2);
        if (section == null) {
            return;
        }
        section.setBlockAt(x, y, z, 0);
        if (section.nonEmptyBlocks() == 0) {
            this.removeSection(index2);
        }
    }

    public void markModified(BlockPosition pos) {
        if (!this.modified.contains(pos)) {
            this.modified.add(pos);
        }
    }

    public boolean recentlyModified(BlockPosition pos) {
        for (BlockPosition p : this.modified) {
            if (Math.abs(pos.x() - p.x()) + Math.abs(pos.y() - p.y()) + Math.abs(pos.z() - p.z()) > 2) continue;
            return true;
        }
        return false;
    }

    public void clear() {
        this.blockStorage.clear();
        this.lastSection = null;
        this.lastIndex = -1L;
        this.modified.clear();
    }

    public void unloadChunk(int x, int z) {
        for (int y = 0; y < 16; ++y) {
            this.unloadSection(x, y, z);
        }
    }

    public void unloadSection(int x, int y, int z) {
        this.removeSection(BlockConnectionStorage.getChunkSectionIndex(x << 4, y << 4, z << 4));
    }

    @Nullable SectionData getSection(long index2) {
        if (this.lastIndex == index2) {
            return this.lastSection;
        }
        this.lastIndex = index2;
        this.lastSection = this.blockStorage.get(index2);
        return this.lastSection;
    }

    void removeSection(long index2) {
        this.blockStorage.remove(index2);
        if (this.lastIndex == index2) {
            this.lastIndex = -1L;
            this.lastSection = null;
        }
    }

    static long getChunkSectionIndex(int x, int y, int z) {
        return ((long)(x >> 4) & 0x3FFFFFFL) << 38 | ((long)(y >> 4) & 0xFFFL) << 26 | (long)(z >> 4) & 0x3FFFFFFL;
    }

    <T> Map<Long, T> createLongObjectMap() {
        if (fastUtilLongObjectHashMap != null) {
            try {
                return (Map)fastUtilLongObjectHashMap.newInstance(new Object[0]);
            }
            catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return new HashMap();
    }

    static {
        try {
            String className = "it" + ".unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap";
            fastUtilLongObjectHashMap = Class.forName(className).getConstructor(new Class[0]);
            Via.getPlatform().getLogger().info("Using FastUtil Long2ObjectOpenHashMap for block connections");
        }
        catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
            // empty catch block
        }
    }

    private static final class SectionData {
        final short[] blockStates = new short[4096];
        short nonEmptyBlocks;

        SectionData() {
        }

        public int blockAt(int x, int y, int z) {
            return this.blockStates[SectionData.encodeBlockPos(x, y, z)];
        }

        public void setBlockAt(int x, int y, int z, int blockState) {
            int index2 = SectionData.encodeBlockPos(x, y, z);
            if (blockState == this.blockStates[index2]) {
                return;
            }
            this.blockStates[index2] = (short)blockState;
            this.nonEmptyBlocks = blockState == 0 ? (short)(this.nonEmptyBlocks - 1) : (short)(this.nonEmptyBlocks + 1);
        }

        public short nonEmptyBlocks() {
            return this.nonEmptyBlocks;
        }

        static int encodeBlockPos(int x, int y, int z) {
            return (y & 0xF) << 8 | (x & 0xF) << 4 | z & 0xF;
        }
    }
}

