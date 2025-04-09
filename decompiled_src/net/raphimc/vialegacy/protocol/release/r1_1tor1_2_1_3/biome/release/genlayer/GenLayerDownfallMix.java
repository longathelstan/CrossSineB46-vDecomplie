/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer;

import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.IntCache;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.NewBiomeGenBase;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayer;

public class GenLayerDownfallMix
extends GenLayer {
    private final GenLayer field_35507_b;
    private final int field_35508_c;

    public GenLayerDownfallMix(GenLayer genlayer, GenLayer genlayer1, int i) {
        super(0L);
        this.parent = genlayer1;
        this.field_35507_b = genlayer;
        this.field_35508_c = i;
    }

    @Override
    public int[] getInts(int i, int j, int k, int l) {
        int[] ai = this.parent.getInts(i, j, k, l);
        int[] ai1 = this.field_35507_b.getInts(i, j, k, l);
        int[] ai2 = IntCache.getIntCache(k * l);
        for (int i1 = 0; i1 < k * l; ++i1) {
            ai2[i1] = ai1[i1] + (NewBiomeGenBase.BIOME_LIST[ai[i1]].getIntRainfall() - ai1[i1]) / (this.field_35508_c + 1);
        }
        return ai2;
    }
}

