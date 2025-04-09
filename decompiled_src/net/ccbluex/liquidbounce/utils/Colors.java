/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import java.awt.Color;
import java.text.NumberFormat;

public class Colors {
    public static int getColor(int color, int a) {
        Color color1 = new Color(color);
        return new Color(color1.getRed(), color1.getGreen(), color1.getBlue(), a).getRGB();
    }

    public static int getColor(Color color) {
        return Colors.getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static double getIncremental(double val, double inc) {
        double one = 1.0 / inc;
        return (double)Math.round(val * one) / one;
    }

    public static int getColor(int brightness) {
        return Colors.getColor(brightness, brightness, brightness, 255);
    }

    public static int getColor(int red2, int green2, int blue2) {
        return Colors.getColor(red2, green2, blue2, 255);
    }

    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        if (fractions == null) {
            throw new IllegalArgumentException("Fractions can't be null");
        }
        if (colors == null) {
            throw new IllegalArgumentException("Colours can't be null");
        }
        if (fractions.length != colors.length) {
            throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
        }
        int[] indicies = Colors.getFractionIndicies(fractions, progress);
        float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
        Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};
        float max = range[1] - range[0];
        float value = progress - range[0];
        float weight = value / max;
        return Colors.blend(colorRange[0], colorRange[1], 1.0f - weight);
    }

    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float)ratio;
        float ir = 1.0f - r;
        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        float red2 = rgb1[0] * r + rgb2[0] * ir;
        float green2 = rgb1[1] * r + rgb2[1] * ir;
        float blue2 = rgb1[2] * r + rgb2[2] * ir;
        if (red2 < 0.0f) {
            red2 = 0.0f;
        } else if (red2 > 255.0f) {
            red2 = 255.0f;
        }
        if (green2 < 0.0f) {
            green2 = 0.0f;
        } else if (green2 > 255.0f) {
            green2 = 255.0f;
        }
        if (blue2 < 0.0f) {
            blue2 = 0.0f;
        } else if (blue2 > 255.0f) {
            blue2 = 255.0f;
        }
        Color color = null;
        try {
            color = new Color(red2, green2, blue2);
        }
        catch (IllegalArgumentException exp) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            System.out.println(nf.format(red2) + "; " + nf.format(green2) + "; " + nf.format(blue2));
            exp.printStackTrace();
        }
        return color;
    }

    public static Color astolfoRainbow(int delay2, int offset, int index2) {
        double d;
        double rainbowDelay = Math.ceil(System.currentTimeMillis() + (long)(delay2 * index2)) / (double)offset;
        return Color.getHSBColor((double)((float)(d / 360.0)) < 0.5 ? -((float)(rainbowDelay / 360.0)) : (float)((rainbowDelay %= 360.0) / 360.0), 0.5f, 1.0f);
    }

    public static int[] getFractionIndicies(float[] fractions, float progress) {
        int startPoint;
        int[] range = new int[2];
        for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
        }
        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }
        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }

    public static int getColor(int red2, int green2, int blue2, int alpha) {
        int color = 0;
        color |= alpha << 24;
        color |= red2 << 16;
        color |= green2 << 8;
        return color |= blue2;
    }

    public static int getRainbow(int speed, int offset) {
        float hue = (System.currentTimeMillis() + (long)offset) % (long)speed;
        return Color.getHSBColor(hue /= (float)speed, 0.4f, 1.0f).getRGB();
    }
}

