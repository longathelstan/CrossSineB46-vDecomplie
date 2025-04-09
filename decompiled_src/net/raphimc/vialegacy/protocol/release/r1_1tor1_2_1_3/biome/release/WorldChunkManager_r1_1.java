/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release;

import com.viaversion.viaversion.api.connection.UserConnection;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.IWorldChunkManager;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.BiomeCache;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.IntCache;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.NewBiomeGenBase;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayer;

public class WorldChunkManager_r1_1
implements IWorldChunkManager {
    private GenLayer biomeIndexLayer;
    private GenLayer temperatureLayer;
    private GenLayer rainfallLayer;
    private final BiomeCache biomeCache;

    protected WorldChunkManager_r1_1() {
        IntCache.resetEverything();
        this.biomeCache = new BiomeCache(this);
    }

    public WorldChunkManager_r1_1(UserConnection user, long seed) {
        this();
        GenLayer[] agenlayer = GenLayer.func_35497_a(user, seed);
        this.biomeIndexLayer = agenlayer[1];
        this.temperatureLayer = agenlayer[2];
        this.rainfallLayer = agenlayer[3];
    }

    @Override
    public byte[] getBiomeDataAt(int chunkX, int chunkZ) {
        byte[] biomeData = new byte[256];
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                biomeData[z << 4 | x] = (byte)this.getBiomeGenAt((int)(chunkX * 16 + x), (int)(chunkZ * 16 + z)).biomeID;
            }
        }
        return biomeData;
    }

    public NewBiomeGenBase getBiomeGenAt(int i, int j) {
        return this.biomeCache.getBiomeGenAt(i, j);
    }

    public NewBiomeGenBase[] getBiomeGenAt(NewBiomeGenBase[] abiomegenbase, int i, int j, int k, int l, boolean flag) {
        IntCache.resetIntCache();
        if (abiomegenbase == null || abiomegenbase.length < k * l) {
            abiomegenbase = new NewBiomeGenBase[k * l];
        }
        if (flag && k == 16 && l == 16 && (i & 0xF) == 0 && (j & 0xF) == 0) {
            NewBiomeGenBase[] abiomegenbase1 = this.biomeCache.getCachedBiomes(i, j);
            System.arraycopy(abiomegenbase1, 0, abiomegenbase, 0, k * l);
            return abiomegenbase;
        }
        int[] ai = this.biomeIndexLayer.getInts(i, j, k, l);
        for (int i1 = 0; i1 < k * l; ++i1) {
            abiomegenbase[i1] = NewBiomeGenBase.BIOME_LIST[ai[i1]];
        }
        return abiomegenbase;
    }

    public float[] getRainfall(float[] af, int i, int j, int k, int l) {
        IntCache.resetIntCache();
        if (af == null || af.length < k * l) {
            af = new float[k * l];
        }
        int[] ai = this.rainfallLayer.getInts(i, j, k, l);
        for (int i1 = 0; i1 < k * l; ++i1) {
            float f = (float)ai[i1] / 65536.0f;
            if (f > 1.0f) {
                f = 1.0f;
            }
            af[i1] = f;
        }
        return af;
    }

    public float[] getTemperatures(float[] af, int i, int j, int k, int l) {
        IntCache.resetIntCache();
        if (af == null || af.length < k * l) {
            af = new float[k * l];
        }
        int[] ai = this.temperatureLayer.getInts(i, j, k, l);
        for (int i1 = 0; i1 < k * l; ++i1) {
            float f = (float)ai[i1] / 65536.0f;
            if (f > 1.0f) {
                f = 1.0f;
            }
            af[i1] = f;
        }
        return af;
    }

    public void cleanupCache() {
        this.biomeCache.cleanupCache();
    }
}

