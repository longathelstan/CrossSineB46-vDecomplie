/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_17_1to1_18.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ChunkLightStorage
implements StorableObject {
    final Map<Long, ChunkLight> lightPackets = new HashMap<Long, ChunkLight>();
    final Set<Long> loadedChunks = new HashSet<Long>();

    public void storeLight(int x, int z, ChunkLight chunkLight) {
        this.lightPackets.put(this.getChunkSectionIndex(x, z), chunkLight);
    }

    public @Nullable ChunkLight removeLight(int x, int z) {
        return this.lightPackets.remove(this.getChunkSectionIndex(x, z));
    }

    public @Nullable ChunkLight getLight(int x, int z) {
        return this.lightPackets.get(this.getChunkSectionIndex(x, z));
    }

    public boolean addLoadedChunk(int x, int z) {
        return this.loadedChunks.add(this.getChunkSectionIndex(x, z));
    }

    public boolean isLoaded(int x, int z) {
        return this.loadedChunks.contains(this.getChunkSectionIndex(x, z));
    }

    public void clear(int x, int z) {
        long index2 = this.getChunkSectionIndex(x, z);
        this.lightPackets.remove(index2);
        this.loadedChunks.remove(index2);
    }

    public void clear() {
        this.loadedChunks.clear();
        this.lightPackets.clear();
    }

    long getChunkSectionIndex(int x, int z) {
        return ((long)x & 0x3FFFFFFL) << 38 | (long)z & 0x3FFFFFFL;
    }

    public static final class ChunkLight {
        final boolean trustEdges;
        final long[] skyLightMask;
        final long[] blockLightMask;
        final long[] emptySkyLightMask;
        final long[] emptyBlockLightMask;
        final byte[][] skyLight;
        final byte[][] blockLight;

        public ChunkLight(boolean trustEdges, long[] skyLightMask, long[] blockLightMask, long[] emptySkyLightMask, long[] emptyBlockLightMask, byte[][] skyLight, byte[][] blockLight) {
            this.trustEdges = trustEdges;
            this.skyLightMask = skyLightMask;
            this.blockLightMask = blockLightMask;
            this.emptySkyLightMask = emptySkyLightMask;
            this.emptyBlockLightMask = emptyBlockLightMask;
            this.skyLight = skyLight;
            this.blockLight = blockLight;
        }

        public boolean trustEdges() {
            return this.trustEdges;
        }

        public long[] skyLightMask() {
            return this.skyLightMask;
        }

        public long[] blockLightMask() {
            return this.blockLightMask;
        }

        public long[] emptySkyLightMask() {
            return this.emptySkyLightMask;
        }

        public long[] emptyBlockLightMask() {
            return this.emptyBlockLightMask;
        }

        public byte[][] skyLight() {
            return this.skyLight;
        }

        public byte[][] blockLight() {
            return this.blockLight;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof ChunkLight)) {
                return false;
            }
            ChunkLight chunkLight = (ChunkLight)object;
            return this.trustEdges == chunkLight.trustEdges && Objects.equals(this.skyLightMask, chunkLight.skyLightMask) && Objects.equals(this.blockLightMask, chunkLight.blockLightMask) && Objects.equals(this.emptySkyLightMask, chunkLight.emptySkyLightMask) && Objects.equals(this.emptyBlockLightMask, chunkLight.emptyBlockLightMask) && Objects.equals(this.skyLight, chunkLight.skyLight) && Objects.equals(this.blockLight, chunkLight.blockLight);
        }

        public int hashCode() {
            return ((((((0 * 31 + Boolean.hashCode(this.trustEdges)) * 31 + Objects.hashCode(this.skyLightMask)) * 31 + Objects.hashCode(this.blockLightMask)) * 31 + Objects.hashCode(this.emptySkyLightMask)) * 31 + Objects.hashCode(this.emptyBlockLightMask)) * 31 + Objects.hashCode(this.skyLight)) * 31 + Objects.hashCode(this.blockLight);
        }

        public String toString() {
            return String.format("%s[trustEdges=%s, skyLightMask=%s, blockLightMask=%s, emptySkyLightMask=%s, emptyBlockLightMask=%s, skyLight=%s, blockLight=%s]", this.getClass().getSimpleName(), Boolean.toString(this.trustEdges), Objects.toString(this.skyLightMask), Objects.toString(this.blockLightMask), Objects.toString(this.emptySkyLightMask), Objects.toString(this.emptyBlockLightMask), Objects.toString(this.skyLight), Objects.toString(this.blockLight));
        }
    }
}

