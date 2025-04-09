/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.beta;

import java.util.Random;
import net.raphimc.vialegacy.api.model.ChunkCoord;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.IWorldChunkManager;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.beta.NoiseGeneratorOctaves2;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.beta.OldBiomeGenBase;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.NewBiomeGenBase;

public class WorldChunkManager_b1_7
implements IWorldChunkManager {
    private final NoiseGeneratorOctaves2 field_4194_e;
    private final NoiseGeneratorOctaves2 field_4193_f;
    private final NoiseGeneratorOctaves2 field_4192_g;
    public double[] temperature;
    public double[] humidity;
    public double[] field_4196_c;
    public OldBiomeGenBase[] field_4195_d;

    public WorldChunkManager_b1_7(long seed) {
        this.field_4194_e = new NoiseGeneratorOctaves2(new Random(seed * 9871L), 4);
        this.field_4193_f = new NoiseGeneratorOctaves2(new Random(seed * 39811L), 4);
        this.field_4192_g = new NoiseGeneratorOctaves2(new Random(seed * 543321L), 2);
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

    public NewBiomeGenBase getBiomeGenAtChunkCoord(ChunkCoord chunkcoordintpair) {
        return this.getBiomeGenAt(chunkcoordintpair.chunkX << 4, chunkcoordintpair.chunkZ << 4);
    }

    public NewBiomeGenBase getBiomeGenAt(int i, int j) {
        OldBiomeGenBase oldBiomeGenBase = this.func_4069_a(i, j, 1, 1)[0];
        if (oldBiomeGenBase.equals(OldBiomeGenBase.rainforest)) {
            return NewBiomeGenBase.jungle;
        }
        if (oldBiomeGenBase.equals(OldBiomeGenBase.swampland)) {
            return NewBiomeGenBase.swampland;
        }
        if (oldBiomeGenBase.equals(OldBiomeGenBase.seasonalForest)) {
            return NewBiomeGenBase.forest;
        }
        if (oldBiomeGenBase.equals(OldBiomeGenBase.forest)) {
            return NewBiomeGenBase.forest;
        }
        if (oldBiomeGenBase.equals(OldBiomeGenBase.savanna)) {
            return NewBiomeGenBase.savanna;
        }
        if (oldBiomeGenBase.equals(OldBiomeGenBase.shrubland)) {
            return NewBiomeGenBase.mutatedJungleEdge;
        }
        if (oldBiomeGenBase.equals(OldBiomeGenBase.taiga)) {
            return NewBiomeGenBase.taiga;
        }
        if (oldBiomeGenBase.equals(OldBiomeGenBase.desert)) {
            return NewBiomeGenBase.desert;
        }
        if (oldBiomeGenBase.equals(OldBiomeGenBase.plains)) {
            return NewBiomeGenBase.plains;
        }
        if (oldBiomeGenBase.equals(OldBiomeGenBase.iceDesert)) {
            return NewBiomeGenBase.icePlains;
        }
        if (oldBiomeGenBase.equals(OldBiomeGenBase.tundra)) {
            return NewBiomeGenBase.icePlains;
        }
        if (oldBiomeGenBase.equals(OldBiomeGenBase.hell)) {
            return NewBiomeGenBase.hell;
        }
        if (oldBiomeGenBase.equals(OldBiomeGenBase.sky)) {
            return NewBiomeGenBase.sky;
        }
        return NewBiomeGenBase.plains;
    }

    public double getTemperature(int i, int j) {
        this.temperature = this.field_4194_e.func_4112_a(this.temperature, i, j, 1, 1, 0.025f, 0.025f, 0.5);
        return this.temperature[0];
    }

    public OldBiomeGenBase[] func_4069_a(int i, int j, int k, int l) {
        this.field_4195_d = this.loadBlockGeneratorData(this.field_4195_d, i, j, k, l);
        return this.field_4195_d;
    }

    public double[] getTemperatures(double[] ad, int i, int j, int k, int l) {
        if (ad == null || ad.length < k * l) {
            ad = new double[k * l];
        }
        ad = this.field_4194_e.func_4112_a(ad, i, j, k, l, 0.025f, 0.025f, 0.25);
        this.field_4196_c = this.field_4192_g.func_4112_a(this.field_4196_c, i, j, k, l, 0.25, 0.25, 0.5882352941176471);
        int i1 = 0;
        for (int j1 = 0; j1 < k; ++j1) {
            for (int k1 = 0; k1 < l; ++k1) {
                double d = this.field_4196_c[i1] * 1.1 + 0.5;
                double d1 = 0.01;
                double d2 = 1.0 - d1;
                double d3 = (ad[i1] * 0.15 + 0.7) * d2 + d * d1;
                if ((d3 = 1.0 - (1.0 - d3) * (1.0 - d3)) < 0.0) {
                    d3 = 0.0;
                }
                if (d3 > 1.0) {
                    d3 = 1.0;
                }
                ad[i1] = d3;
                ++i1;
            }
        }
        return ad;
    }

    public OldBiomeGenBase[] loadBlockGeneratorData(OldBiomeGenBase[] abiomegenbase, int i, int j, int k, int l) {
        if (abiomegenbase == null || abiomegenbase.length < k * l) {
            abiomegenbase = new OldBiomeGenBase[k * l];
        }
        this.temperature = this.field_4194_e.func_4112_a(this.temperature, i, j, k, k, 0.025f, 0.025f, 0.25);
        this.humidity = this.field_4193_f.func_4112_a(this.humidity, i, j, k, k, 0.05f, 0.05f, 0.3333333333333333);
        this.field_4196_c = this.field_4192_g.func_4112_a(this.field_4196_c, i, j, k, k, 0.25, 0.25, 0.5882352941176471);
        int i1 = 0;
        for (int j1 = 0; j1 < k; ++j1) {
            for (int k1 = 0; k1 < l; ++k1) {
                double d = this.field_4196_c[i1] * 1.1 + 0.5;
                double d1 = 0.01;
                double d2 = 1.0 - d1;
                double d3 = (this.temperature[i1] * 0.15 + 0.7) * d2 + d * d1;
                d1 = 0.002;
                d2 = 1.0 - d1;
                double d4 = (this.humidity[i1] * 0.15 + 0.5) * d2 + d * d1;
                if ((d3 = 1.0 - (1.0 - d3) * (1.0 - d3)) < 0.0) {
                    d3 = 0.0;
                }
                if (d4 < 0.0) {
                    d4 = 0.0;
                }
                if (d3 > 1.0) {
                    d3 = 1.0;
                }
                if (d4 > 1.0) {
                    d4 = 1.0;
                }
                this.temperature[i1] = d3;
                this.humidity[i1] = d4;
                abiomegenbase[i1++] = OldBiomeGenBase.getBiomeFromLookup(d3, d4);
            }
        }
        return abiomegenbase;
    }
}

