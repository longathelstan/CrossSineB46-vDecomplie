/*
 * Decompiled with CFR 0.152.
 */
package com.jhlabs.image;

import com.jhlabs.image.BinaryFilter;
import java.awt.Rectangle;

public class OutlineFilter
extends BinaryFilter {
    public OutlineFilter() {
        this.newColor = -1;
    }

    protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
        int index2 = 0;
        int[] outPixels = new int[width * height];
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
                            int ix = x + dx;
                            if ((dy != 0 || dx != 0) && 0 <= ix && ix < width) {
                                int rgb = inPixels[ioffset + ix];
                                if (!this.blackFunction.isBlack(rgb)) continue;
                                ++neighbours;
                                continue;
                            }
                            ++neighbours;
                        }
                    }
                    if (neighbours == 9) {
                        pixel = this.newColor;
                    }
                }
                outPixels[index2++] = pixel;
            }
        }
        return outPixels;
    }

    public String toString() {
        return "Binary/Outline...";
    }
}

