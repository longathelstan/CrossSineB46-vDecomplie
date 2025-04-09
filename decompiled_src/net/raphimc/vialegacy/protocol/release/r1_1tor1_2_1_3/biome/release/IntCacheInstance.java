/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release;

import java.util.ArrayList;
import java.util.List;

public class IntCacheInstance {
    private int intCacheSize = 256;
    private List<int[]> freeSmallArrays = new ArrayList<int[]>();
    private List<int[]> inUseSmallArrays = new ArrayList<int[]>();
    private List<int[]> freeLargeArrays = new ArrayList<int[]>();
    private List<int[]> inUseLargeArrays = new ArrayList<int[]>();

    public int[] getIntCache(int i) {
        if (i <= 256) {
            if (this.freeSmallArrays.isEmpty()) {
                int[] ai = new int[256];
                this.inUseSmallArrays.add(ai);
                return ai;
            }
            int[] ai1 = this.freeSmallArrays.remove(this.freeSmallArrays.size() - 1);
            this.inUseSmallArrays.add(ai1);
            return ai1;
        }
        if (i > this.intCacheSize) {
            this.intCacheSize = i;
            this.freeLargeArrays.clear();
            this.inUseLargeArrays.clear();
            int[] ai2 = new int[this.intCacheSize];
            this.inUseLargeArrays.add(ai2);
            return ai2;
        }
        if (this.freeLargeArrays.isEmpty()) {
            int[] ai3 = new int[this.intCacheSize];
            this.inUseLargeArrays.add(ai3);
            return ai3;
        }
        int[] ai4 = this.freeLargeArrays.remove(this.freeLargeArrays.size() - 1);
        this.inUseLargeArrays.add(ai4);
        return ai4;
    }

    public void resetIntCache() {
        if (!this.freeLargeArrays.isEmpty()) {
            this.freeLargeArrays.remove(this.freeLargeArrays.size() - 1);
        }
        if (!this.freeSmallArrays.isEmpty()) {
            this.freeSmallArrays.remove(this.freeSmallArrays.size() - 1);
        }
        this.freeLargeArrays.addAll(this.inUseLargeArrays);
        this.freeSmallArrays.addAll(this.inUseSmallArrays);
        this.inUseLargeArrays.clear();
        this.inUseSmallArrays.clear();
    }

    public void resetEverything() {
        this.intCacheSize = 256;
        this.freeSmallArrays = new ArrayList<int[]>();
        this.inUseSmallArrays = new ArrayList<int[]>();
        this.freeLargeArrays = new ArrayList<int[]>();
        this.inUseLargeArrays = new ArrayList<int[]>();
    }
}

