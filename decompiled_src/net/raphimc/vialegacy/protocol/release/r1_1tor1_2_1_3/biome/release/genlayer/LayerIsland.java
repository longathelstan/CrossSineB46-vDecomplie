/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer;

import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.IntCache;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayer;

public class LayerIsland
extends GenLayer {
    public LayerIsland(long l) {
        super(l);
    }

    @Override
    public int[] getInts(int i, int j, int k, int l) {
        int[] ai = IntCache.getIntCache(k * l);
        for (int i1 = 0; i1 < l; ++i1) {
            for (int j1 = 0; j1 < k; ++j1) {
                this.initChunkSeed(i + j1, j + i1);
                ai[j1 + i1 * k] = this.nextInt(10) != 0 ? 0 : 1;
            }
        }
        if (i > -k && i <= 0 && j > -l && j <= 0) {
            ai[-i + -j * k] = 1;
        }
        return ai;
    }
}

