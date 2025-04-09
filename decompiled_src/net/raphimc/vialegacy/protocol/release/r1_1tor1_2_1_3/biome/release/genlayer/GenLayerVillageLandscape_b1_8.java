/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer;

import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.IntCache;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.NewBiomeGenBase;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayer;

public class GenLayerVillageLandscape_b1_8
extends GenLayer {
    private final NewBiomeGenBase[] allowedBiomes = new NewBiomeGenBase[]{NewBiomeGenBase.desert, NewBiomeGenBase.forest, NewBiomeGenBase.extremeHills, NewBiomeGenBase.swampland, NewBiomeGenBase.plains, NewBiomeGenBase.taiga};

    public GenLayerVillageLandscape_b1_8(long l, GenLayer genlayer) {
        super(l);
        this.parent = genlayer;
    }

    @Override
    public int[] getInts(int i, int j, int k, int l) {
        int[] ai = this.parent.getInts(i, j, k, l);
        int[] ai1 = IntCache.getIntCache(k * l);
        for (int i1 = 0; i1 < l; ++i1) {
            for (int j1 = 0; j1 < k; ++j1) {
                this.initChunkSeed(j1 + i, i1 + j);
                ai1[j1 + i1 * k] = ai[j1 + i1 * k] <= 0 ? 0 : this.allowedBiomes[this.nextInt((int)this.allowedBiomes.length)].biomeID;
            }
        }
        return ai1;
    }
}

