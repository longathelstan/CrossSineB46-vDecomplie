/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer;

import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.IntCache;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.NewBiomeGenBase;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayer;

public class GenLayerShore_r1_1
extends GenLayer {
    public GenLayerShore_r1_1(long l, GenLayer genlayer) {
        super(l);
        this.parent = genlayer;
    }

    @Override
    public int[] getInts(int i, int j, int k, int l) {
        int[] ai = this.parent.getInts(i - 1, j - 1, k + 2, l + 2);
        int[] ai1 = IntCache.getIntCache(k * l);
        for (int i1 = 0; i1 < l; ++i1) {
            for (int j1 = 0; j1 < k; ++j1) {
                this.initChunkSeed(j1 + i, i1 + j);
                int k1 = ai[j1 + 1 + (i1 + 1) * (k + 2)];
                if (k1 == NewBiomeGenBase.mushroomIsland.biomeID) {
                    int l1 = ai[j1 + 1 + (i1 + 1 - 1) * (k + 2)];
                    int k2 = ai[j1 + 1 + 1 + (i1 + 1) * (k + 2)];
                    int j3 = ai[j1 + 1 - 1 + (i1 + 1) * (k + 2)];
                    int i4 = ai[j1 + 1 + (i1 + 1 + 1) * (k + 2)];
                    if (l1 == NewBiomeGenBase.ocean.biomeID || k2 == NewBiomeGenBase.ocean.biomeID || j3 == NewBiomeGenBase.ocean.biomeID || i4 == NewBiomeGenBase.ocean.biomeID) {
                        ai1[j1 + i1 * k] = NewBiomeGenBase.mushroomIslandShore.biomeID;
                        continue;
                    }
                    ai1[j1 + i1 * k] = k1;
                    continue;
                }
                if (k1 != NewBiomeGenBase.ocean.biomeID && k1 != NewBiomeGenBase.river.biomeID && k1 != NewBiomeGenBase.swampland.biomeID && k1 != NewBiomeGenBase.extremeHills.biomeID) {
                    int i2 = ai[j1 + 1 + (i1 + 1 - 1) * (k + 2)];
                    int l2 = ai[j1 + 1 + 1 + (i1 + 1) * (k + 2)];
                    int k3 = ai[j1 + 1 - 1 + (i1 + 1) * (k + 2)];
                    int j4 = ai[j1 + 1 + (i1 + 1 + 1) * (k + 2)];
                    if (i2 == NewBiomeGenBase.ocean.biomeID || l2 == NewBiomeGenBase.ocean.biomeID || k3 == NewBiomeGenBase.ocean.biomeID || j4 == NewBiomeGenBase.ocean.biomeID) {
                        ai1[j1 + i1 * k] = NewBiomeGenBase.beach.biomeID;
                        continue;
                    }
                    ai1[j1 + i1 * k] = k1;
                    continue;
                }
                if (k1 == NewBiomeGenBase.extremeHills.biomeID) {
                    int j2 = ai[j1 + 1 + (i1 + 1 - 1) * (k + 2)];
                    int i3 = ai[j1 + 1 + 1 + (i1 + 1) * (k + 2)];
                    int l3 = ai[j1 + 1 - 1 + (i1 + 1) * (k + 2)];
                    int k4 = ai[j1 + 1 + (i1 + 1 + 1) * (k + 2)];
                    if (j2 != NewBiomeGenBase.extremeHills.biomeID || i3 != NewBiomeGenBase.extremeHills.biomeID || l3 != NewBiomeGenBase.extremeHills.biomeID || k4 != NewBiomeGenBase.extremeHills.biomeID) {
                        ai1[j1 + i1 * k] = NewBiomeGenBase.extremeHillsEdge.biomeID;
                        continue;
                    }
                    ai1[j1 + i1 * k] = k1;
                    continue;
                }
                ai1[j1 + i1 * k] = k1;
            }
        }
        return ai1;
    }
}

