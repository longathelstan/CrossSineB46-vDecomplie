/*
 * Decompiled with CFR 0.152.
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PointFilter;
import java.io.Serializable;

public class WeaveFilter
extends PointFilter
implements Serializable {
    static final long serialVersionUID = 4847932412277504482L;
    private float xWidth = 16.0f;
    private float yWidth = 16.0f;
    private float xGap = 6.0f;
    private float yGap = 6.0f;
    private int rows = 4;
    private int cols = 4;
    private int rgbX = -32640;
    private int rgbY = -8355585;
    private boolean useImageColors = true;
    private boolean roundThreads = false;
    private boolean shadeCrossings = true;
    public int[][] matrix = new int[][]{{0, 1, 0, 1}, {1, 0, 1, 0}, {0, 1, 0, 1}, {1, 0, 1, 0}};

    public void setXGap(float xGap) {
        this.xGap = xGap;
    }

    public void setXWidth(float xWidth) {
        this.xWidth = xWidth;
    }

    public float getXWidth() {
        return this.xWidth;
    }

    public void setYWidth(float yWidth) {
        this.yWidth = yWidth;
    }

    public float getYWidth() {
        return this.yWidth;
    }

    public float getXGap() {
        return this.xGap;
    }

    public void setYGap(float yGap) {
        this.yGap = yGap;
    }

    public float getYGap() {
        return this.yGap;
    }

    public void setCrossings(int[][] matrix) {
        this.matrix = matrix;
    }

    public int[][] getCrossings() {
        return this.matrix;
    }

    public void setUseImageColors(boolean useImageColors) {
        this.useImageColors = useImageColors;
    }

    public boolean getUseImageColors() {
        return this.useImageColors;
    }

    public void setRoundThreads(boolean roundThreads) {
        this.roundThreads = roundThreads;
    }

    public boolean getRoundThreads() {
        return this.roundThreads;
    }

    public void setShadeCrossings(boolean shadeCrossings) {
        this.shadeCrossings = shadeCrossings;
    }

    public boolean getShadeCrossings() {
        return this.shadeCrossings;
    }

    public int filterRGB(int x, int y, int rgb) {
        int v;
        int lrgbX;
        int lrgbY;
        float cY;
        float cX;
        float dY;
        float dX;
        boolean inY;
        x = (int)((float)x + (this.xWidth + this.xGap / 2.0f));
        y = (int)((float)y + (this.yWidth + this.yGap / 2.0f));
        float nx = ImageMath.mod((float)x, this.xWidth + this.xGap);
        float ny = ImageMath.mod((float)y, this.yWidth + this.yGap);
        int ix = (int)((float)x / (this.xWidth + this.xGap));
        int iy = (int)((float)y / (this.yWidth + this.yGap));
        boolean inX = nx < this.xWidth;
        boolean bl = inY = ny < this.yWidth;
        if (this.roundThreads) {
            dX = Math.abs(this.xWidth / 2.0f - nx) / this.xWidth / 2.0f;
            dY = Math.abs(this.yWidth / 2.0f - ny) / this.yWidth / 2.0f;
        } else {
            dY = 0.0f;
            dX = 0.0f;
        }
        if (this.shadeCrossings) {
            cX = ImageMath.smoothStep(this.xWidth / 2.0f, this.xWidth / 2.0f + this.xGap, Math.abs(this.xWidth / 2.0f - nx));
            cY = ImageMath.smoothStep(this.yWidth / 2.0f, this.yWidth / 2.0f + this.yGap, Math.abs(this.yWidth / 2.0f - ny));
        } else {
            cY = 0.0f;
            cX = 0.0f;
        }
        if (this.useImageColors) {
            lrgbX = lrgbY = rgb;
        } else {
            lrgbX = this.rgbX;
            lrgbY = this.rgbY;
        }
        int ixc = ix % this.cols;
        int iyr = iy % this.rows;
        int m2 = this.matrix[iyr][ixc];
        if (inX) {
            if (inY) {
                v = m2 == 1 ? lrgbX : lrgbY;
                v = ImageMath.mixColors(2.0f * (m2 == 1 ? dX : dY), v, -16777216);
            } else {
                if (this.shadeCrossings) {
                    if (m2 != this.matrix[(iy + 1) % this.rows][ixc]) {
                        if (m2 == 0) {
                            cY = 1.0f - cY;
                        }
                        lrgbX = ImageMath.mixColors(cY *= 0.5f, lrgbX, -16777216);
                    } else if (m2 == 0) {
                        lrgbX = ImageMath.mixColors(0.5f, lrgbX, -16777216);
                    }
                }
                v = ImageMath.mixColors(2.0f * dX, lrgbX, -16777216);
            }
        } else if (inY) {
            if (this.shadeCrossings) {
                if (m2 != this.matrix[iyr][(ix + 1) % this.cols]) {
                    if (m2 == 1) {
                        cX = 1.0f - cX;
                    }
                    lrgbY = ImageMath.mixColors(cX *= 0.5f, lrgbY, -16777216);
                } else if (m2 == 1) {
                    lrgbY = ImageMath.mixColors(0.5f, lrgbY, -16777216);
                }
            }
            v = ImageMath.mixColors(2.0f * dY, lrgbY, -16777216);
        } else {
            v = 0;
        }
        return v;
    }

    public String toString() {
        return "Texture/Weave...";
    }
}

