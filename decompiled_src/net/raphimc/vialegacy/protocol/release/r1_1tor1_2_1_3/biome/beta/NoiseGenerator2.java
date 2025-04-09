/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.beta;

import java.util.Random;

public class NoiseGenerator2 {
    private static final int[][] field_4296_d = new int[][]{{1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0}, {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1}, {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}};
    private final int[] field_4295_e = new int[512];
    public double field_4292_a;
    public double field_4291_b;
    public double field_4297_c;
    private static final double field_4294_f = 0.5 * (Math.sqrt(3.0) - 1.0);
    private static final double field_4293_g = (3.0 - Math.sqrt(3.0)) / 6.0;

    public NoiseGenerator2() {
        this(new Random());
    }

    public NoiseGenerator2(Random random) {
        this.field_4292_a = random.nextDouble() * 256.0;
        this.field_4291_b = random.nextDouble() * 256.0;
        this.field_4297_c = random.nextDouble() * 256.0;
        for (int i = 0; i < 256; ++i) {
            this.field_4295_e[i] = i;
        }
        for (int j = 0; j < 256; ++j) {
            int k = random.nextInt(256 - j) + j;
            int l = this.field_4295_e[j];
            this.field_4295_e[j] = this.field_4295_e[k];
            this.field_4295_e[k] = l;
            this.field_4295_e[j + 256] = this.field_4295_e[j];
        }
    }

    private static int wrap(double d) {
        return d <= 0.0 ? (int)d - 1 : (int)d;
    }

    private static double func_4156_a(int[] ai, double d, double d1) {
        return (double)ai[0] * d + (double)ai[1] * d1;
    }

    public void func_4157_a(double[] ad, double d, double d1, int i, int j, double d2, double d3, double d4) {
        int k = 0;
        for (int l = 0; l < i; ++l) {
            double d5 = (d + (double)l) * d2 + this.field_4292_a;
            for (int i1 = 0; i1 < j; ++i1) {
                double d9;
                double d8;
                double d7;
                int i2;
                int l1;
                double d13;
                double d15;
                int k1;
                double d11;
                double d6 = (d1 + (double)i1) * d3 + this.field_4291_b;
                double d10 = (d5 + d6) * field_4294_f;
                int j1 = NoiseGenerator2.wrap(d5 + d10);
                double d12 = (double)j1 - (d11 = (double)(j1 + (k1 = NoiseGenerator2.wrap(d6 + d10))) * field_4293_g);
                double d14 = d5 - d12;
                if (d14 > (d15 = d6 - (d13 = (double)k1 - d11))) {
                    l1 = 1;
                    i2 = 0;
                } else {
                    l1 = 0;
                    i2 = 1;
                }
                double d16 = d14 - (double)l1 + field_4293_g;
                double d17 = d15 - (double)i2 + field_4293_g;
                double d18 = d14 - 1.0 + 2.0 * field_4293_g;
                double d19 = d15 - 1.0 + 2.0 * field_4293_g;
                int j2 = j1 & 0xFF;
                int k2 = k1 & 0xFF;
                int l2 = this.field_4295_e[j2 + this.field_4295_e[k2]] % 12;
                int i3 = this.field_4295_e[j2 + l1 + this.field_4295_e[k2 + i2]] % 12;
                int j3 = this.field_4295_e[j2 + 1 + this.field_4295_e[k2 + 1]] % 12;
                double d20 = 0.5 - d14 * d14 - d15 * d15;
                if (d20 < 0.0) {
                    d7 = 0.0;
                } else {
                    d20 *= d20;
                    d7 = d20 * d20 * NoiseGenerator2.func_4156_a(field_4296_d[l2], d14, d15);
                }
                double d21 = 0.5 - d16 * d16 - d17 * d17;
                if (d21 < 0.0) {
                    d8 = 0.0;
                } else {
                    d21 *= d21;
                    d8 = d21 * d21 * NoiseGenerator2.func_4156_a(field_4296_d[i3], d16, d17);
                }
                double d22 = 0.5 - d18 * d18 - d19 * d19;
                if (d22 < 0.0) {
                    d9 = 0.0;
                } else {
                    d22 *= d22;
                    d9 = d22 * d22 * NoiseGenerator2.func_4156_a(field_4296_d[j3], d18, d19);
                }
                int n = k++;
                ad[n] = ad[n] + 70.0 * (d7 + d8 + d9) * d4;
            }
        }
    }
}

