/*
 * Decompiled with CFR 0.152.
 */
package com.jhlabs.composite;

import com.jhlabs.composite.RGBComposite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;

public final class HardLightComposite
extends RGBComposite {
    public HardLightComposite(float alpha) {
        super(alpha);
    }

    public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
        return new Context(this.extraAlpha, srcColorModel, dstColorModel);
    }

    static class Context
    extends RGBComposite.RGBCompositeContext {
        public Context(float alpha, ColorModel srcColorModel, ColorModel dstColorModel) {
            super(alpha, srcColorModel, dstColorModel);
        }

        public void composeRGB(int[] src, int[] dst, float alpha) {
            int w = src.length;
            for (int i = 0; i < w; i += 4) {
                int sr = src[i];
                int dir = dst[i];
                int sg = src[i + 1];
                int dig = dst[i + 1];
                int sb = src[i + 2];
                int dib = dst[i + 2];
                int sa = src[i + 3];
                int dia = dst[i + 3];
                int dor = sr > 127 ? 255 - 2 * RGBComposite.RGBCompositeContext.multiply255(255 - sr, 255 - dir) : 2 * RGBComposite.RGBCompositeContext.multiply255(sr, dir);
                int dog = sg > 127 ? 255 - 2 * RGBComposite.RGBCompositeContext.multiply255(255 - sg, 255 - dig) : 2 * RGBComposite.RGBCompositeContext.multiply255(sg, dig);
                int dob = sb > 127 ? 255 - 2 * RGBComposite.RGBCompositeContext.multiply255(255 - sb, 255 - dib) : 2 * RGBComposite.RGBCompositeContext.multiply255(sb, dib);
                float a = alpha * (float)sa / 255.0f;
                float ac = 1.0f - a;
                dst[i] = (int)(a * (float)dor + ac * (float)dir);
                dst[i + 1] = (int)(a * (float)dog + ac * (float)dig);
                dst[i + 2] = (int)(a * (float)dob + ac * (float)dib);
                dst[i + 3] = (int)((float)sa * alpha + (float)dia * ac);
            }
        }
    }
}

