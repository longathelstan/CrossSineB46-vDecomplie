/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.beta;

import java.awt.Color;

public class OldBiomeGenBase {
    private static final OldBiomeGenBase[] biomeLookupTable = new OldBiomeGenBase[4096];
    public static final OldBiomeGenBase rainforest = new OldBiomeGenBase();
    public static final OldBiomeGenBase swampland = new OldBiomeGenBase();
    public static final OldBiomeGenBase seasonalForest = new OldBiomeGenBase();
    public static final OldBiomeGenBase forest = new OldBiomeGenBase();
    public static final OldBiomeGenBase savanna = new OldBiomeGenBase();
    public static final OldBiomeGenBase shrubland = new OldBiomeGenBase();
    public static final OldBiomeGenBase taiga = new OldBiomeGenBase();
    public static final OldBiomeGenBase desert = new OldBiomeGenBase();
    public static final OldBiomeGenBase plains = new OldBiomeGenBase();
    public static final OldBiomeGenBase iceDesert = new OldBiomeGenBase();
    public static final OldBiomeGenBase tundra = new OldBiomeGenBase();
    public static final OldBiomeGenBase hell = new OldBiomeGenBase();
    public static final OldBiomeGenBase sky = new OldBiomeGenBase();

    protected OldBiomeGenBase() {
    }

    public static void generateBiomeLookup() {
        for (int i = 0; i < 64; ++i) {
            for (int j = 0; j < 64; ++j) {
                OldBiomeGenBase.biomeLookupTable[i + j * 64] = OldBiomeGenBase.getBiome((float)i / 63.0f, (float)j / 63.0f);
            }
        }
    }

    public static OldBiomeGenBase getBiomeFromLookup(double d, double d1) {
        int i = (int)(d * 63.0);
        int j = (int)(d1 * 63.0);
        return biomeLookupTable[i + j * 64];
    }

    public static OldBiomeGenBase getBiome(float f, float f1) {
        f1 *= f;
        if (f < 0.1f) {
            return tundra;
        }
        if (f1 < 0.2f) {
            if (f < 0.5f) {
                return tundra;
            }
            if (f < 0.95f) {
                return savanna;
            }
            return desert;
        }
        if (f1 > 0.5f && f < 0.7f) {
            return swampland;
        }
        if (f < 0.5f) {
            return taiga;
        }
        if (f < 0.97f) {
            if (f1 < 0.35f) {
                return shrubland;
            }
            return forest;
        }
        if (f1 < 0.45f) {
            return plains;
        }
        if (f1 < 0.9f) {
            return seasonalForest;
        }
        return rainforest;
    }

    public int getSkyColorByTemp(float f) {
        if ((f /= 3.0f) < -1.0f) {
            f = -1.0f;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        return Color.getHSBColor(0.6222222f - f * 0.05f, 0.5f + f * 0.1f, 1.0f).getRGB();
    }

    static {
        OldBiomeGenBase.generateBiomeLookup();
    }
}

