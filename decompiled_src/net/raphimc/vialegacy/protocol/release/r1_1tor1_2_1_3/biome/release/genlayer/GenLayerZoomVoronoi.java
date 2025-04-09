/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer;

import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.IntCache;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayer;

public class GenLayerZoomVoronoi
extends GenLayer {
    public GenLayerZoomVoronoi(long l, GenLayer genlayer) {
        super(l);
        this.parent = genlayer;
    }

    @Override
    public int[] getInts(int i, int j, int k, int l) {
        int byte0 = 2;
        int i1 = 1 << byte0;
        int j1 = (i -= 2) >> byte0;
        int k1 = (j -= 2) >> byte0;
        int l1 = (k >> byte0) + 3;
        int i2 = (l >> byte0) + 3;
        int[] ai = this.parent.getInts(j1, k1, l1, i2);
        int j2 = l1 << byte0;
        int k2 = i2 << byte0;
        int[] ai1 = IntCache.getIntCache(j2 * k2);
        for (int l2 = 0; l2 < i2 - 1; ++l2) {
            int i3 = ai[0 + (l2 + 0) * l1];
            int k3 = ai[0 + (l2 + 1) * l1];
            for (int l3 = 0; l3 < l1 - 1; ++l3) {
                double d = (double)i1 * 0.9;
                this.initChunkSeed(l3 + j1 << byte0, l2 + k1 << byte0);
                double d1 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * d;
                double d2 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * d;
                this.initChunkSeed(l3 + j1 + 1 << byte0, l2 + k1 << byte0);
                double d3 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * d + (double)i1;
                double d4 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * d;
                this.initChunkSeed(l3 + j1 << byte0, l2 + k1 + 1 << byte0);
                double d5 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * d;
                double d6 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * d + (double)i1;
                this.initChunkSeed(l3 + j1 + 1 << byte0, l2 + k1 + 1 << byte0);
                double d7 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * d + (double)i1;
                double d8 = ((double)this.nextInt(1024) / 1024.0 - 0.5) * d + (double)i1;
                int i4 = ai[l3 + 1 + (l2 + 0) * l1];
                int j4 = ai[l3 + 1 + (l2 + 1) * l1];
                for (int k4 = 0; k4 < i1; ++k4) {
                    int l4 = ((l2 << byte0) + k4) * j2 + (l3 << byte0);
                    for (int i5 = 0; i5 < i1; ++i5) {
                        double d9 = ((double)k4 - d2) * ((double)k4 - d2) + ((double)i5 - d1) * ((double)i5 - d1);
                        double d10 = ((double)k4 - d4) * ((double)k4 - d4) + ((double)i5 - d3) * ((double)i5 - d3);
                        double d11 = ((double)k4 - d6) * ((double)k4 - d6) + ((double)i5 - d5) * ((double)i5 - d5);
                        double d12 = ((double)k4 - d8) * ((double)k4 - d8) + ((double)i5 - d7) * ((double)i5 - d7);
                        ai1[l4++] = d9 < d10 && d9 < d11 && d9 < d12 ? i3 : (d10 < d9 && d10 < d11 && d10 < d12 ? i4 : (d11 < d9 && d11 < d10 && d11 < d12 ? k3 : j4));
                    }
                }
                i3 = i4;
                k3 = j4;
            }
        }
        int[] ai2 = IntCache.getIntCache(k * l);
        for (int j3 = 0; j3 < l; ++j3) {
            System.arraycopy(ai1, (j3 + (j & i1 - 1)) * (l1 << byte0) + (i & i1 - 1), ai2, j3 * k, k);
        }
        return ai2;
    }
}

