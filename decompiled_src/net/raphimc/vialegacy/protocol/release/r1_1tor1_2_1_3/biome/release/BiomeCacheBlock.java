/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release;

import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.BiomeCache;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.NewBiomeGenBase;

public class BiomeCacheBlock {
    public NewBiomeGenBase[] biomes;
    public int xPosition;
    public int zPosition;
    public long lastAccessTime;
    final BiomeCache biomeCache;

    public BiomeCacheBlock(BiomeCache biomecache, int i, int j) {
        this.biomeCache = biomecache;
        this.biomes = new NewBiomeGenBase[256];
        this.xPosition = i;
        this.zPosition = j;
        BiomeCache.getWorldChunkManager(biomecache).getBiomeGenAt(this.biomes, i << 4, j << 4, 16, 16, false);
    }

    public NewBiomeGenBase getBiomeGenAt(int i, int j) {
        return this.biomes[i & 0xF | (j & 0xF) << 4];
    }
}

