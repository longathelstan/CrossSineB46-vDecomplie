/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer;

import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.IntCache;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.NewBiomeGenBase;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayer;

public class GenLayerTemperature
extends GenLayer {
    public GenLayerTemperature(GenLayer genlayer) {
        super(0L);
        this.parent = genlayer;
    }

    @Override
    public int[] getInts(int i, int j, int k, int l) {
        int[] ai = this.parent.getInts(i, j, k, l);
        int[] ai1 = IntCache.getIntCache(k * l);
        for (int i1 = 0; i1 < k * l; ++i1) {
            ai1[i1] = NewBiomeGenBase.BIOME_LIST[ai[i1]].getIntTemperature();
        }
        return ai1;
    }
}

