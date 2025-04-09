/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.BiomeCacheBlock;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.NewBiomeGenBase;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.WorldChunkManager_r1_1;

public class BiomeCache {
    private final WorldChunkManager_r1_1 chunkmanager;
    private long lastCleanupTime = 0L;
    private final Map<Long, BiomeCacheBlock> cacheMap = new HashMap<Long, BiomeCacheBlock>();
    private final List<BiomeCacheBlock> cache = new ArrayList<BiomeCacheBlock>();

    public BiomeCache(WorldChunkManager_r1_1 worldchunkmanager) {
        this.chunkmanager = worldchunkmanager;
    }

    public BiomeCacheBlock getBiomeCacheBlock(int i, int j) {
        long l = (long)(i >>= 4) & 0xFFFFFFFFL | ((long)(j >>= 4) & 0xFFFFFFFFL) << 32;
        BiomeCacheBlock biomecacheblock = this.cacheMap.get(l);
        if (biomecacheblock == null) {
            biomecacheblock = new BiomeCacheBlock(this, i, j);
            this.cacheMap.put(l, biomecacheblock);
            this.cache.add(biomecacheblock);
        }
        biomecacheblock.lastAccessTime = System.currentTimeMillis();
        return biomecacheblock;
    }

    public NewBiomeGenBase getBiomeGenAt(int i, int j) {
        return this.getBiomeCacheBlock(i, j).getBiomeGenAt(i, j);
    }

    public void cleanupCache() {
        long l = System.currentTimeMillis();
        long l1 = l - this.lastCleanupTime;
        if (l1 > 7500L || l1 < 0L) {
            this.lastCleanupTime = l;
            for (int i = 0; i < this.cache.size(); ++i) {
                BiomeCacheBlock biomecacheblock = this.cache.get(i);
                long l2 = l - biomecacheblock.lastAccessTime;
                if (l2 <= 30000L && l2 >= 0L) continue;
                this.cache.remove(i--);
                long l3 = (long)biomecacheblock.xPosition & 0xFFFFFFFFL | ((long)biomecacheblock.zPosition & 0xFFFFFFFFL) << 32;
                this.cacheMap.remove(l3);
            }
        }
    }

    public NewBiomeGenBase[] getCachedBiomes(int i, int j) {
        return this.getBiomeCacheBlock((int)i, (int)j).biomes;
    }

    static WorldChunkManager_r1_1 getWorldChunkManager(BiomeCache biomecache) {
        return biomecache.chunkmanager;
    }
}

