/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer;

import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.IntCache;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.NewBiomeGenBase;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayer;

public class GenLayerRiverMix
extends GenLayer {
    private final GenLayer field_35512_b;
    private final GenLayer field_35513_c;

    public GenLayerRiverMix(long l, GenLayer genlayer, GenLayer genlayer1) {
        super(l);
        this.field_35512_b = genlayer;
        this.field_35513_c = genlayer1;
    }

    @Override
    public void initWorldGenSeed(long l) {
        this.field_35512_b.initWorldGenSeed(l);
        this.field_35513_c.initWorldGenSeed(l);
        super.initWorldGenSeed(l);
    }

    @Override
    public int[] getInts(int i, int j, int k, int l) {
        int[] ai = this.field_35512_b.getInts(i, j, k, l);
        int[] ai1 = this.field_35513_c.getInts(i, j, k, l);
        int[] ai2 = IntCache.getIntCache(k * l);
        for (int i1 = 0; i1 < k * l; ++i1) {
            if (ai[i1] == NewBiomeGenBase.ocean.biomeID) {
                ai2[i1] = ai[i1];
                continue;
            }
            if (ai1[i1] >= 0) {
                if (ai[i1] == NewBiomeGenBase.icePlains.biomeID) {
                    ai2[i1] = NewBiomeGenBase.frozenRiver.biomeID;
                    continue;
                }
                if (ai[i1] == NewBiomeGenBase.mushroomIsland.biomeID || ai[i1] == NewBiomeGenBase.mushroomIslandShore.biomeID) {
                    ai2[i1] = NewBiomeGenBase.mushroomIslandShore.biomeID;
                    continue;
                }
                ai2[i1] = ai1[i1];
                continue;
            }
            ai2[i1] = ai[i1];
        }
        return ai2;
    }
}

