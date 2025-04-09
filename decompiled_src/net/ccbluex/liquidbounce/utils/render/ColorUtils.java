/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.render;

import com.ibm.icu.text.NumberFormat;
import java.awt.Color;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.util.ChatAllowedCharacters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u0014\n\u0002\b\u0018\n\u0002\u0010\u0002\n\u0002\b\u000e\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J \u0010\u0010\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014J\u0018\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\rJ \u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\rH\u0007J\u001e\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u000fJ\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u00172\u0006\u0010 \u001a\u00020\rH\u0007J\u0016\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u000fJ\u0010\u0010%\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000bH\u0007J \u0010&\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020\u000f2\u0006\u0010(\u001a\u00020\u000f2\b\b\u0002\u0010\u0018\u001a\u00020\rJ\u0018\u0010)\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J%\u0010*\u001a\u0004\u0018\u00010\u00142\u0006\u0010+\u001a\u00020\u00142\u0006\u0010,\u001a\u00020\u00142\u0006\u0010-\u001a\u00020\u0014\u00a2\u0006\u0002\u0010.J\"\u0010/\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u00100\u001a\u00020\u000fH\u0007J \u00101\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u00100\u001a\u00020\u000fJ\u001e\u00102\u001a\u00020\u000f2\u0006\u0010+\u001a\u00020\u000f2\u0006\u0010,\u001a\u00020\u000f2\u0006\u0010-\u001a\u00020\u0014J \u00103\u001a\u00020\r2\u0006\u0010+\u001a\u00020\r2\u0006\u0010,\u001a\u00020\r2\u0006\u0010-\u001a\u00020\u0014H\u0007J&\u00104\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u00105\u001a\u00020\u00142\u0006\u00106\u001a\u00020\rJ \u00107\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\u000fH\u0007J\u0018\u00107\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0010\u00107\u001a\u00020\u000b2\u0006\u00106\u001a\u00020\tH\u0007J\u000e\u00108\u001a\u00020\u00172\u0006\u00109\u001a\u00020\u0017J\u0018\u0010:\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u000fH\u0007J\u0018\u0010:\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\rH\u0007J\u0010\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\rH\u0007J(\u0010>\u001a\u00020\u000b2\u0006\u0010?\u001a\u00020\r2\u0006\u0010@\u001a\u00020\u000f2\u0006\u0010A\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u0014H\u0007J(\u0010B\u001a\u00020\u000b2\u0006\u0010C\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010D\u001a\u00020\u000f2\u0006\u0010E\u001a\u00020\u000fH\u0007J\u0010\u0010F\u001a\u00020\u00172\u0006\u0010G\u001a\u00020\u0017H\u0007J\u0010\u0010H\u001a\u00020\u00172\u0006\u0010I\u001a\u00020\u0017H\u0007R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006J"}, d2={"Lnet/ccbluex/liquidbounce/utils/render/ColorUtils;", "", "()V", "COLOR_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "hexColors", "", "startTime", "", "astolfo", "Ljava/awt/Color;", "index", "", "speed", "", "blend", "color1", "color2", "ratio", "", "colorCode", "code", "", "alpha", "fade", "color", "count", "getColor", "hueoffset", "saturation", "brightness", "n", "getFraction", "fractions", "", "progress", "getOppositeColor", "healthColor", "hp", "maxHP", "hslRainbow", "interpolate", "oldValue", "newValue", "interpolationValue", "(DDD)Ljava/lang/Double;", "interpolateColorC", "amount", "interpolateColorHue", "interpolateFloat", "interpolateInt", "mixColors", "ms", "offset", "rainbow", "randomMagicText", "text", "reAlpha", "setColour", "", "colour", "skyRainbow", "var2", "bright", "st", "slowlyRainbow", "time", "qd", "sq", "stripColor", "input", "translateAlternateColorCodes", "textToTranslate", "CrossSine"})
public final class ColorUtils {
    @NotNull
    public static final ColorUtils INSTANCE = new ColorUtils();
    private static final Pattern COLOR_PATTERN = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
    private static final long startTime = System.currentTimeMillis();
    @JvmField
    @NotNull
    public static final int[] hexColors = new int[16];

    private ColorUtils() {
    }

    @JvmStatic
    @NotNull
    public static final String stripColor(@NotNull String input) {
        Intrinsics.checkNotNullParameter(input, "input");
        String string = COLOR_PATTERN.matcher(input).replaceAll("");
        Intrinsics.checkNotNullExpressionValue(string, "COLOR_PATTERN.matcher(input).replaceAll(\"\")");
        return string;
    }

    @JvmStatic
    @NotNull
    public static final String translateAlternateColorCodes(@NotNull String textToTranslate) {
        Intrinsics.checkNotNullParameter(textToTranslate, "textToTranslate");
        char[] cArray = textToTranslate.toCharArray();
        Intrinsics.checkNotNullExpressionValue(cArray, "this as java.lang.String).toCharArray()");
        char[] chars = cArray;
        int n = 0;
        int n2 = chars.length - 1;
        while (n < n2) {
            int i;
            if (chars[i = n++] != '&' || !StringsKt.contains((CharSequence)"0123456789AaBbCcDdEeFfKkLlMmNnOoRr", chars[i + 1], true)) continue;
            chars[i] = 167;
            chars[i + 1] = Character.toLowerCase(chars[i + 1]);
        }
        return new String(chars);
    }

    @NotNull
    public final String randomMagicText(@NotNull String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        StringBuilder stringBuilder = new StringBuilder();
        String allowedCharacters = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000";
        char[] cArray = text.toCharArray();
        Intrinsics.checkNotNullExpressionValue(cArray, "this as java.lang.String).toCharArray()");
        for (char c : cArray) {
            if (!ChatAllowedCharacters.func_71566_a((char)c)) continue;
            int index2 = new Random().nextInt(allowedCharacters.length());
            char[] cArray2 = allowedCharacters.toCharArray();
            Intrinsics.checkNotNullExpressionValue(cArray2, "this as java.lang.String).toCharArray()");
            stringBuilder.append(cArray2[index2]);
        }
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "stringBuilder.toString()");
        return string;
    }

    @JvmStatic
    @NotNull
    public static final Color getOppositeColor(@NotNull Color color) {
        Intrinsics.checkNotNullParameter(color, "color");
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), color.getAlpha());
    }

    @NotNull
    public final Color colorCode(@NotNull String code, int alpha) {
        Intrinsics.checkNotNullParameter(code, "code");
        String string = code.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "0": {
                return new Color(0, 0, 0, alpha);
            }
            case "1": {
                return new Color(0, 0, 170, alpha);
            }
            case "2": {
                return new Color(0, 170, 0, alpha);
            }
            case "3": {
                return new Color(0, 170, 170, alpha);
            }
            case "4": {
                return new Color(170, 0, 0, alpha);
            }
            case "5": {
                return new Color(170, 0, 170, alpha);
            }
            case "6": {
                return new Color(255, 170, 0, alpha);
            }
            case "7": {
                return new Color(170, 170, 170, alpha);
            }
            case "8": {
                return new Color(85, 85, 85, alpha);
            }
            case "9": {
                return new Color(85, 85, 255, alpha);
            }
            case "a": {
                return new Color(85, 255, 85, alpha);
            }
            case "b": {
                return new Color(85, 255, 255, alpha);
            }
            case "c": {
                return new Color(255, 85, 85, alpha);
            }
            case "d": {
                return new Color(255, 85, 255, alpha);
            }
            case "e": {
                return new Color(255, 255, 85, alpha);
            }
        }
        return new Color(255, 255, 255, alpha);
    }

    public static /* synthetic */ Color colorCode$default(ColorUtils colorUtils, String string, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 255;
        }
        return colorUtils.colorCode(string, n);
    }

    @Nullable
    public final Color blend(@NotNull Color color1, @NotNull Color color2, double ratio) {
        Intrinsics.checkNotNullParameter(color1, "color1");
        Intrinsics.checkNotNullParameter(color2, "color2");
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
        Color color3 = null;
        try {
            color3 = new Color(red2, green2, blue2);
        }
        catch (IllegalArgumentException exp) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            exp.printStackTrace();
        }
        return color3;
    }

    @NotNull
    public final int[] getFraction(@NotNull float[] fractions, float progress) {
        Intrinsics.checkNotNullParameter(fractions, "fractions");
        int startPoint = 0;
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

    public final int getColor(float hueoffset, float saturation, float brightness) {
        float speed = 4500.0f;
        float hue = (float)(System.currentTimeMillis() % (long)((int)speed)) / speed;
        return Color.HSBtoRGB(hue - hueoffset / (float)54, saturation, brightness);
    }

    @JvmStatic
    public static final void setColour(int colour) {
        float a = (float)(colour >> 24 & 0xFF) / 255.0f;
        float r = (float)(colour >> 16 & 0xFF) / 255.0f;
        float g = (float)(colour >> 8 & 0xFF) / 255.0f;
        float b = (float)(colour & 0xFF) / 255.0f;
        GL11.glColor4f((float)r, (float)g, (float)b, (float)a);
    }

    @JvmStatic
    @Nullable
    public static final String getColor(int n) {
        if (n != 1) {
            if (n == 2) {
                return "\u00a7a";
            }
            if (n == 3) {
                return "\u00a73";
            }
            if (n == 4) {
                return "\u00a74";
            }
            if (n >= 5) {
                return "\u00a7e";
            }
        }
        return "\u00a7f";
    }

    @JvmStatic
    @NotNull
    public static final Color astolfo(int index2, float speed) {
        Color color = Color.getHSBColor(Math.abs((float)((int)(System.currentTimeMillis() - startTime) - index2 * 200) / speed % (float)2 - 1.0f) * 0.3f + 0.55f, 0.55f, 1.0f);
        Intrinsics.checkNotNullExpressionValue(color, "getHSBColor((abs(((((Sys\u2026.3F)) + 0.55F, 0.55F, 1F)");
        return color;
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(int index2, float speed) {
        Color color = Color.getHSBColor(Math.abs((float)((int)(System.currentTimeMillis() - startTime) - index2 * 200) / speed % (float)2 - 1.0f) * 0.3f + 0.55f, 1.0f, 1.0f);
        Intrinsics.checkNotNullExpressionValue(color, "getHSBColor((abs(((((Sys\u2026 (0.3F)) + 0.55F, 1F, 1F)");
        return color;
    }

    @Nullable
    public final Double interpolate(double oldValue, double newValue, double interpolationValue) {
        return oldValue + (newValue - oldValue) * interpolationValue;
    }

    public final float interpolateFloat(float oldValue, float newValue, double interpolationValue) {
        Double d = this.interpolate(oldValue, newValue, (float)interpolationValue);
        Intrinsics.checkNotNull(d);
        return (float)d.doubleValue();
    }

    @Nullable
    public final Color interpolateColorHue(@NotNull Color color1, @NotNull Color color2, float amount) {
        Intrinsics.checkNotNullParameter(color1, "color1");
        Intrinsics.checkNotNullParameter(color2, "color2");
        float amount2 = amount;
        amount2 = Math.min(1.0f, Math.max(0.0f, amount2));
        float[] color1HSB = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
        float[] color2HSB = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);
        Color resultColor = Color.getHSBColor(this.interpolateFloat(color1HSB[0], color2HSB[0], amount2), this.interpolateFloat(color1HSB[1], color2HSB[1], amount2), this.interpolateFloat(color1HSB[2], color2HSB[2], amount2));
        return new Color(resultColor.getRed(), resultColor.getGreen(), resultColor.getBlue(), ColorUtils.interpolateInt(color1.getAlpha(), color2.getAlpha(), amount2));
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(long offset) {
        Color currentColor = new Color(Color.HSBtoRGB((float)(System.nanoTime() + offset) / 1.0E10f % 1.0f, 1.0f, 1.0f));
        return new Color((float)currentColor.getRed() / 255.0f * 1.0f, (float)currentColor.getGreen() / 255.0f * 1.0f, (float)currentColor.getBlue() / 255.0f * 1.0f, (float)currentColor.getAlpha() / 255.0f);
    }

    @JvmStatic
    public static final int interpolateInt(int oldValue, int newValue, double interpolationValue) {
        Double d = INSTANCE.interpolate(oldValue, newValue, (float)interpolationValue);
        Intrinsics.checkNotNull(d);
        return (int)d.doubleValue();
    }

    @JvmStatic
    @Nullable
    public static final Color interpolateColorC(@NotNull Color color1, @NotNull Color color2, float amount) {
        Intrinsics.checkNotNullParameter(color1, "color1");
        Intrinsics.checkNotNullParameter(color2, "color2");
        float amount2 = amount;
        amount2 = Math.min(1.0f, Math.max(0.0f, amount2));
        return new Color(ColorUtils.interpolateInt(color1.getRed(), color2.getRed(), amount2), ColorUtils.interpolateInt(color1.getGreen(), color2.getGreen(), amount2), ColorUtils.interpolateInt(color1.getBlue(), color2.getBlue(), amount2), ColorUtils.interpolateInt(color1.getAlpha(), color2.getAlpha(), amount2));
    }

    @JvmStatic
    @NotNull
    public static final Color reAlpha(@NotNull Color color, int alpha) {
        Intrinsics.checkNotNullParameter(color, "color");
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color reAlpha(@NotNull Color color, float alpha) {
        Intrinsics.checkNotNullParameter(color, "color");
        return new Color((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, alpha);
    }

    @JvmStatic
    @NotNull
    public static final Color slowlyRainbow(long time, int count, float qd, float sq) {
        Color color = new Color(Color.HSBtoRGB(((float)time + (float)count * -3000000.0f) / (float)2 / 1.0E9f, qd, sq));
        return new Color((float)color.getRed() / 255.0f * 1.0f, (float)color.getGreen() / 255.0f * 1.0f, (float)color.getBlue() / 255.0f * 1.0f, (float)color.getAlpha() / 255.0f);
    }

    @JvmStatic
    @NotNull
    public static final Color rainbow(int index2, double speed, float saturation) {
        double hue = (double)index2 * speed % (double)360 / (double)360;
        Color color = Color.getHSBColor((float)hue, Math.abs(RangesKt.coerceIn(saturation, 0.0f, 1.0f)), 1.0f);
        Intrinsics.checkNotNullExpressionValue(color, "getHSBColor(hue.toFloat(\u2026on.coerceIn(0f, 1f)), 1f)");
        return color;
    }

    @JvmStatic
    @NotNull
    public static final Color skyRainbow(int var2, float bright, float st, double speed) {
        double d;
        double v1 = 0.0;
        v1 = Math.ceil((double)System.currentTimeMillis() / speed + (double)((long)var2 * 109L)) / (double)5;
        double it = d = 360.0;
        boolean bl = false;
        Color color = Color.getHSBColor(d / 360.0 < 0.5 ? -((float)(v1 / 360.0)) : (float)((v1 %= it) / 360.0), st, bright);
        Intrinsics.checkNotNullExpressionValue(color, "getHSBColor(if ((360.0.a\u2026.toFloat() }, st, bright)");
        return color;
    }

    @JvmStatic
    @NotNull
    public static final Color fade(@NotNull Color color, int index2, int count) {
        Intrinsics.checkNotNullParameter(color, "color");
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0f + (float)index2 / (float)count * 2.0f) % 2.0f - 1.0f);
        brightness = 0.5f + 0.5f * brightness;
        hsb[2] = brightness % 2.0f;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    @NotNull
    public final Color healthColor(float hp, float maxHP, int alpha) {
        int pct = (int)(hp / maxHP * 255.0f);
        int n = 255 - pct;
        int n2 = 255;
        n = Math.min(n, n2);
        n2 = 0;
        int n3 = Math.max(n, n2);
        n = 255;
        n = Math.min(pct, n);
        n2 = 0;
        return new Color(n3, Math.max(n, n2), 0, alpha);
    }

    public static /* synthetic */ Color healthColor$default(ColorUtils colorUtils, float f, float f2, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = 255;
        }
        return colorUtils.healthColor(f, f2, n);
    }

    @NotNull
    public final Color mixColors(@NotNull Color color1, @NotNull Color color2, double ms, int offset) {
        Intrinsics.checkNotNullParameter(color1, "color1");
        Intrinsics.checkNotNullParameter(color2, "color2");
        double timer = (double)System.currentTimeMillis() / 1.0E8 * ms * 400000.0;
        double percent = (Math.sin(timer + (double)((float)offset * 0.55f)) + 1.0) * (double)0.5f;
        double inverse_percent = 1.0 - percent;
        int redPart = (int)((double)color1.getRed() * percent + (double)color2.getRed() * inverse_percent);
        int greenPart = (int)((double)color1.getGreen() * percent + (double)color2.getGreen() * inverse_percent);
        int bluePart = (int)((double)color1.getBlue() * percent + (double)color2.getBlue() * inverse_percent);
        return new Color(redPart, greenPart, bluePart);
    }

    @JvmStatic
    @NotNull
    public static final Color hslRainbow(int index2, float speed) {
        Color color = Color.getHSBColor(Math.abs((float)((int)(System.currentTimeMillis() - startTime) - index2 * 200) / speed % (float)2 - 1.0f), 1.0f, 1.0f);
        Intrinsics.checkNotNullExpressionValue(color, "getHSBColor((abs(((((Sys\u2026peed) % 2) - 1)), 1F, 1F)");
        return color;
    }

    static {
        int n = 16;
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int i = n3 = n2++;
            boolean bl = false;
            int baseColor = (i >> 3 & 1) * 85;
            int red2 = (i >> 2 & 1) * 170 + baseColor + (i == 6 ? 85 : 0);
            int green2 = (i >> 1 & 1) * 170 + baseColor;
            int blue2 = (i & 1) * 170 + baseColor;
            ColorUtils.hexColors[i] = (red2 & 0xFF) << 16 | (green2 & 0xFF) << 8 | blue2 & 0xFF;
        }
    }
}

