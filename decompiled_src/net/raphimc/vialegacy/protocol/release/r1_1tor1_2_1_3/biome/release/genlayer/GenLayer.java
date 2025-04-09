/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer;

import com.viaversion.viaversion.api.connection.UserConnection;
import net.raphimc.vialegacy.api.LegacyProtocolVersion;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerDownfall;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerDownfallMix;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerHills;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerIsland_b1_8;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerIsland_r1_0;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerMushroomIsland;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerRiver;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerRiverInit;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerRiverMix;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerShore_r1_0;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerShore_r1_1;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerSmooth;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerSmoothZoom;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerSnow;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerSwampRivers;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerTemperature;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerTemperatureMix;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerVillageLandscape_b1_8;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerVillageLandscape_r1_0;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerZoom;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerZoomFuzzy;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.GenLayerZoomVoronoi;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer.LayerIsland;

public abstract class GenLayer {
    private long worldGenSeed;
    protected GenLayer parent;
    private long chunkSeed;
    private long baseSeed;

    public static GenLayer[] func_35497_a(UserConnection user, long seed) {
        GenLayer obj = new LayerIsland(1L);
        obj = new GenLayerZoomFuzzy(2000L, obj);
        obj = user.getProtocolInfo().serverProtocolVersion().newerThanOrEqualTo(LegacyProtocolVersion.r1_0_0tor1_0_1) ? new GenLayerIsland_r1_0(1L, obj) : new GenLayerIsland_b1_8(1L, obj);
        obj = new GenLayerZoom(2001L, obj);
        obj = user.getProtocolInfo().serverProtocolVersion().newerThanOrEqualTo(LegacyProtocolVersion.r1_0_0tor1_0_1) ? new GenLayerIsland_r1_0(2L, obj) : new GenLayerIsland_b1_8(2L, obj);
        if (user.getProtocolInfo().serverProtocolVersion().newerThanOrEqualTo(LegacyProtocolVersion.r1_0_0tor1_0_1)) {
            obj = new GenLayerSnow(2L, obj);
        }
        obj = new GenLayerZoom(2002L, obj);
        obj = user.getProtocolInfo().serverProtocolVersion().newerThanOrEqualTo(LegacyProtocolVersion.r1_0_0tor1_0_1) ? new GenLayerIsland_r1_0(3L, obj) : new GenLayerIsland_b1_8(3L, obj);
        obj = new GenLayerZoom(2003L, obj);
        if (user.getProtocolInfo().serverProtocolVersion().newerThanOrEqualTo(LegacyProtocolVersion.r1_0_0tor1_0_1)) {
            obj = new GenLayerIsland_r1_0(4L, obj);
            obj = new GenLayerMushroomIsland(5L, obj);
        } else {
            obj = new GenLayerIsland_b1_8(3L, obj);
            obj = new GenLayerZoom(2004L, obj);
            obj = new GenLayerIsland_b1_8(3L, obj);
        }
        int byte0 = 4;
        GenLayer obj1 = obj;
        obj1 = GenLayerZoom.func_35515_a(1000L, obj1, 0);
        obj1 = new GenLayerRiverInit(100L, obj1);
        obj1 = GenLayerZoom.func_35515_a(1000L, obj1, byte0 + 2);
        obj1 = new GenLayerRiver(1L, obj1);
        obj1 = new GenLayerSmooth(1000L, obj1);
        GenLayer obj2 = obj;
        obj2 = GenLayerZoom.func_35515_a(1000L, obj2, 0);
        obj2 = user.getProtocolInfo().serverProtocolVersion().newerThanOrEqualTo(LegacyProtocolVersion.r1_0_0tor1_0_1) ? new GenLayerVillageLandscape_r1_0(200L, obj2) : new GenLayerVillageLandscape_b1_8(200L, obj2);
        obj2 = GenLayerZoom.func_35515_a(1000L, obj2, 2);
        if (user.getProtocolInfo().serverProtocolVersion().newerThanOrEqualTo(LegacyProtocolVersion.r1_1)) {
            obj2 = new GenLayerHills(1000L, obj2);
        }
        GenLayer obj3 = new GenLayerTemperature(obj2);
        GenLayer obj4 = new GenLayerDownfall(obj2);
        for (int i = 0; i < byte0; ++i) {
            obj2 = new GenLayerZoom(1000 + i, obj2);
            if (user.getProtocolInfo().serverProtocolVersion().newerThanOrEqualTo(LegacyProtocolVersion.r1_1)) {
                if (i == 0) {
                    obj2 = new GenLayerIsland_r1_0(3L, obj2);
                }
                if (i == 1) {
                    obj2 = new GenLayerShore_r1_1(1000L, obj2);
                }
                if (i == 1) {
                    obj2 = new GenLayerSwampRivers(1000L, obj2);
                }
            } else {
                if (i == 0) {
                    obj2 = user.getProtocolInfo().serverProtocolVersion().newerThanOrEqualTo(LegacyProtocolVersion.r1_0_0tor1_0_1) ? new GenLayerIsland_r1_0(3L, obj2) : new GenLayerIsland_b1_8(3L, obj2);
                }
                if (user.getProtocolInfo().serverProtocolVersion().newerThanOrEqualTo(LegacyProtocolVersion.r1_0_0tor1_0_1) && i == 0) {
                    obj2 = new GenLayerShore_r1_0(1000L, obj2);
                }
            }
            obj3 = new GenLayerSmoothZoom(1000 + i, obj3);
            obj3 = new GenLayerTemperatureMix(obj3, obj2, i);
            obj4 = new GenLayerSmoothZoom(1000 + i, obj4);
            obj4 = new GenLayerDownfallMix(obj4, obj2, i);
        }
        obj2 = new GenLayerSmooth(1000L, obj2);
        obj2 = new GenLayerRiverMix(100L, obj2, obj1);
        GenLayerRiverMix genlayerrivermix = (GenLayerRiverMix)obj2;
        obj3 = GenLayerSmoothZoom.func_35517_a(1000L, obj3, 2);
        obj4 = GenLayerSmoothZoom.func_35517_a(1000L, obj4, 2);
        GenLayerZoomVoronoi genlayerzoomvoronoi = new GenLayerZoomVoronoi(10L, obj2);
        obj2.initWorldGenSeed(seed);
        obj3.initWorldGenSeed(seed);
        obj4.initWorldGenSeed(seed);
        genlayerzoomvoronoi.initWorldGenSeed(seed);
        return new GenLayer[]{obj2, genlayerzoomvoronoi, obj3, obj4, genlayerrivermix};
    }

    public GenLayer(long l) {
        this.baseSeed = l;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += l;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += l;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += l;
    }

    public void initWorldGenSeed(long l) {
        this.worldGenSeed = l;
        if (this.parent != null) {
            this.parent.initWorldGenSeed(l);
        }
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
    }

    public void initChunkSeed(long l, long l1) {
        this.chunkSeed = this.worldGenSeed;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += l;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += l1;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += l;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += l1;
    }

    protected int nextInt(int i) {
        int j = (int)((this.chunkSeed >> 24) % (long)i);
        if (j < 0) {
            j += i;
        }
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.worldGenSeed;
        return j;
    }

    public abstract int[] getInts(int var1, int var2, int var3, int var4);
}

