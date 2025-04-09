/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release;

public class NewBiomeGenBase {
    public static final NewBiomeGenBase[] BIOME_LIST = new NewBiomeGenBase[256];
    public static final NewBiomeGenBase ocean = new NewBiomeGenBase(0);
    public static final NewBiomeGenBase plains = new NewBiomeGenBase(1).setTemperatureRainfall(0.8f, 0.4f);
    public static final NewBiomeGenBase desert = new NewBiomeGenBase(2).setTemperatureRainfall(2.0f, 0.0f);
    public static final NewBiomeGenBase extremeHills = new NewBiomeGenBase(3).setTemperatureRainfall(0.2f, 0.3f);
    public static final NewBiomeGenBase forest = new NewBiomeGenBase(4).setTemperatureRainfall(0.7f, 0.8f);
    public static final NewBiomeGenBase taiga = new NewBiomeGenBase(5).setTemperatureRainfall(0.05f, 0.8f);
    public static final NewBiomeGenBase swampland = new NewBiomeGenBase(6).setTemperatureRainfall(0.8f, 0.9f);
    public static final NewBiomeGenBase river = new NewBiomeGenBase(7);
    public static final NewBiomeGenBase hell = new NewBiomeGenBase(8).setTemperatureRainfall(2.0f, 0.0f);
    public static final NewBiomeGenBase sky = new NewBiomeGenBase(9);
    public static final NewBiomeGenBase frozenOcean = new NewBiomeGenBase(10).setTemperatureRainfall(0.0f, 0.5f);
    public static final NewBiomeGenBase frozenRiver = new NewBiomeGenBase(11).setTemperatureRainfall(0.0f, 0.5f);
    public static final NewBiomeGenBase icePlains = new NewBiomeGenBase(12);
    public static final NewBiomeGenBase iceMountains = new NewBiomeGenBase(13).setTemperatureRainfall(0.0f, 0.5f);
    public static final NewBiomeGenBase mushroomIsland = new NewBiomeGenBase(14).setTemperatureRainfall(0.9f, 1.0f);
    public static final NewBiomeGenBase mushroomIslandShore = new NewBiomeGenBase(15).setTemperatureRainfall(0.9f, 1.0f);
    public static final NewBiomeGenBase beach = new NewBiomeGenBase(16).setTemperatureRainfall(0.8f, 0.4f);
    public static final NewBiomeGenBase desertHills = new NewBiomeGenBase(17).setTemperatureRainfall(2.0f, 0.0f);
    public static final NewBiomeGenBase forestHills = new NewBiomeGenBase(18).setTemperatureRainfall(0.7f, 0.8f);
    public static final NewBiomeGenBase taigaHills = new NewBiomeGenBase(19).setTemperatureRainfall(0.05f, 0.8f);
    public static final NewBiomeGenBase extremeHillsEdge = new NewBiomeGenBase(20).setTemperatureRainfall(0.2f, 0.3f);
    public static final NewBiomeGenBase jungle = new NewBiomeGenBase(21);
    public static final NewBiomeGenBase cold_taiga = new NewBiomeGenBase(30);
    public static final NewBiomeGenBase savanna = new NewBiomeGenBase(35);
    public static final NewBiomeGenBase mutatedJungleEdge = new NewBiomeGenBase(151);
    public final int biomeID;
    public float temperature;
    public float rainfall;

    protected NewBiomeGenBase(int i) {
        this.biomeID = i;
        if (i <= 20) {
            NewBiomeGenBase.BIOME_LIST[i] = this;
        }
    }

    private NewBiomeGenBase setTemperatureRainfall(float f, float f1) {
        if (f > 0.1f && f < 0.2f) {
            throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
        }
        this.temperature = f;
        this.rainfall = f1;
        return this;
    }

    public final int getIntRainfall() {
        return (int)(this.rainfall * 65536.0f);
    }

    public final int getIntTemperature() {
        return (int)(this.temperature * 65536.0f);
    }
}

