/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer;

import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.IntCache;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayer;

public class GenLayerSmooth
extends GenLayer {
    public GenLayerSmooth(long l, GenLayer genlayer) {
        super(l);
        this.parent = genlayer;
    }

    @Override
    public int[] getInts(int i, int j, int k, int l) {
        int i1 = i - 1;
        int j1 = j - 1;
        int k1 = k + 2;
        int l1 = l + 2;
        int[] ai = this.parent.getInts(i1, j1, k1, l1);
        int[] ai1 = IntCache.getIntCache(k * l);
        for (int i2 = 0; i2 < l; ++i2) {
            for (int j2 = 0; j2 < k; ++j2) {
                int k2 = ai[j2 + 0 + (i2 + 1) * k1];
                int l2 = ai[j2 + 2 + (i2 + 1) * k1];
                int i3 = ai[j2 + 1 + (i2 + 0) * k1];
                int j3 = ai[j2 + 1 + (i2 + 2) * k1];
                int k3 = ai[j2 + 1 + (i2 + 1) * k1];
                if (k2 == l2 && i3 == j3) {
                    this.initChunkSeed(j2 + i, i2 + j);
                    k3 = this.nextInt(2) == 0 ? k2 : i3;
                } else {
                    if (k2 == l2) {
                        k3 = k2;
                    }
                    if (i3 == j3) {
                        k3 = i3;
                    }
                }
                ai1[j2 + i2 * k] = k3;
            }
        }
        return ai1;
    }
}

