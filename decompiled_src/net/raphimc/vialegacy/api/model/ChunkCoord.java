/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.api.model;

import java.util.Objects;

public class ChunkCoord {
    public int chunkX;
    public int chunkZ;

    public ChunkCoord(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public ChunkCoord(long pos) {
        this.chunkX = (int)pos;
        this.chunkZ = (int)(pos >> 32);
    }

    public long toLong() {
        return ChunkCoord.toLong(this.chunkX, this.chunkZ);
    }

    public static long toLong(int chunkX, int chunkZ) {
        return (long)chunkX & 0xFFFFFFFFL | ((long)chunkZ & 0xFFFFFFFFL) << 32;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ChunkCoord that = (ChunkCoord)o;
        return this.chunkX == that.chunkX && this.chunkZ == that.chunkZ;
    }

    public int hashCode() {
        return Objects.hash(this.chunkX, this.chunkZ);
    }

    public String toString() {
        int n = this.chunkZ;
        int n2 = this.chunkX;
        return "ChunkCoord{chunkX=" + n2 + ", chunkZ=" + n + "}";
    }
}

