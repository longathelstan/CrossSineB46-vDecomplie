/*
 * Decompiled with CFR 0.152.
 */
package com.jhlabs.image;

import com.jhlabs.image.BinaryFilter;
import java.awt.Rectangle;

public class ErodeFilter
extends BinaryFilter {
    protected int threshold = 2;

    public ErodeFilter() {
        this.newColor = -1;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getThreshold() {
        return this.threshold;
    }

    protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
        int[] outPixels = new int[width * height];
        for (int i = 0; i < this.iterations; ++i) {
            int index2 = 0;
            if (i > 0) {
                int[] t = inPixels;
                inPixels = outPixels;
                outPixels = t;
            }
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    int pixel = inPixels[y * width + x];
                    if (this.blackFunction.isBlack(pixel)) {
                        int neighbours = 0;
                        for (int dy = -1; dy <= 1; ++dy) {
                            int iy = y + dy;
                            if (0 > iy || iy >= height) continue;
                            int ioffset = iy * width;
                            for (int dx = -1; dx <= 1; ++dx) {
                                int rgb;
                                int ix = x + dx;
                                if (dy == 0 && dx == 0 || 0 > ix || ix >= width || this.blackFunction.isBlack(rgb = inPixels[ioffset + ix])) continue;
                                ++neighbours;
                            }
                        }
                        if (neighbours >= this.threshold) {
                            pixel = this.colormap != null ? this.colormap.getColor((float)i / (float)this.iterations) : this.newColor;
                        }
                    }
                    outPixels[index2++] = pixel;
                }
            }
        }
        return outPixels;
    }

    public String toString() {
        return "Binary/Erode...";
    }
}

