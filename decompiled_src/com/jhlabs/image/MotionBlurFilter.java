/*
 * Decompiled with CFR 0.152.
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PixelUtils;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class MotionBlurFilter
extends AbstractBufferedImageOp {
    public static final int LINEAR = 0;
    public static final int RADIAL = 1;
    public static final int ZOOM = 2;
    private float angle = 0.0f;
    private float falloff = 1.0f;
    private float distance = 1.0f;
    private float zoom = 0.0f;
    private float rotation = 0.0f;
    private boolean wrapEdges = false;

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public float getZoom() {
        return this.zoom;
    }

    public void setWrapEdges(boolean wrapEdges) {
        this.wrapEdges = wrapEdges;
    }

    public boolean getWrapEdges() {
        return this.wrapEdges;
    }

    public BufferedImage filter(BufferedImage src, BufferedImage dst) {
        int width = src.getWidth();
        int height = src.getHeight();
        if (dst == null) {
            dst = this.createCompatibleDestImage(src, null);
        }
        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];
        this.getRGB(src, 0, 0, width, height, inPixels);
        float sinAngle = (float)Math.sin(this.angle);
        float cosAngle = (float)Math.cos(this.angle);
        int cx = width / 2;
        int cy = height / 2;
        int index2 = 0;
        float imageRadius = (float)Math.sqrt(cx * cx + cy * cy);
        float translateX = (float)((double)this.distance * Math.cos(this.angle));
        float translateY = (float)((double)this.distance * -Math.sin(this.angle));
        float maxDistance2 = this.distance + Math.abs(this.rotation * imageRadius) + this.zoom * imageRadius;
        int repetitions = (int)maxDistance2;
        AffineTransform t = new AffineTransform();
        Point2D.Float p = new Point2D.Float();
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int a = 0;
                int r = 0;
                int g = 0;
                int b = 0;
                int count = 0;
                for (int i = 0; i < repetitions; ++i) {
                    int newX = x;
                    int newY = y;
                    float f = (float)i / (float)repetitions;
                    p.x = x;
                    p.y = y;
                    t.setToIdentity();
                    t.translate((float)cx + f * translateX, (float)cy + f * translateY);
                    float s = 1.0f - this.zoom * f;
                    t.scale(s, s);
                    if (this.rotation != 0.0f) {
                        t.rotate(-this.rotation * f);
                    }
                    t.translate(-cx, -cy);
                    t.transform(p, p);
                    newX = (int)p.x;
                    newY = (int)p.y;
                    if (newX < 0 || newX >= width) {
                        if (!this.wrapEdges) break;
                        newX = ImageMath.mod(newX, width);
                    }
                    if (newY < 0 || newY >= height) {
                        if (!this.wrapEdges) break;
                        newY = ImageMath.mod(newY, height);
                    }
                    ++count;
                    int rgb = inPixels[newY * width + newX];
                    a += rgb >> 24 & 0xFF;
                    r += rgb >> 16 & 0xFF;
                    g += rgb >> 8 & 0xFF;
                    b += rgb & 0xFF;
                }
                if (count == 0) {
                    outPixels[index2] = inPixels[index2];
                } else {
                    a = PixelUtils.clamp(a / count);
                    r = PixelUtils.clamp(r / count);
                    g = PixelUtils.clamp(g / count);
                    b = PixelUtils.clamp(b / count);
                    outPixels[index2] = a << 24 | r << 16 | g << 8 | b;
                }
                ++index2;
            }
        }
        this.setRGB(dst, 0, 0, width, height, outPixels);
        return dst;
    }

    public String toString() {
        return "Blur/Motion Blur...";
    }
}

