/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.font.CFontRenderer;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002JJ\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015JJ\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015JJ\u0010\u0016\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015JJ\u0010\u0016\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015J\u001d\u0010\u0017\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0018\u00a2\u0006\u0002\u0010\u0019J \u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u0013H\u0002R \u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2={"Lnet/ccbluex/liquidbounce/utils/FontUtils;", "", "()V", "cache", "", "Lkotlin/Pair;", "", "Lnet/minecraft/client/gui/FontRenderer;", "drawGradientCenterString", "", "fontRenderer", "Lnet/ccbluex/liquidbounce/font/CFontRenderer;", "text", "x", "", "y", "colorStart", "colorEnd", "gradientRange", "", "shadow", "", "drawGradientString", "getAllFontDetails", "", "()[Lkotlin/Pair;", "lerpColor", "ratio", "CrossSine"})
public final class FontUtils {
    @NotNull
    public static final FontUtils INSTANCE = new FontUtils();
    @NotNull
    private static final List<Pair<String, FontRenderer>> cache = new ArrayList();

    private FontUtils() {
    }

    public final void drawGradientString(@NotNull CFontRenderer fontRenderer, @NotNull String text, int x, int y, int colorStart, int colorEnd, float gradientRange, boolean shadow) {
        Intrinsics.checkNotNullParameter(fontRenderer, "fontRenderer");
        Intrinsics.checkNotNullParameter(text, "text");
        int posX = x;
        int n = 0;
        int n2 = text.length();
        while (n < n2) {
            int i = n++;
            float ratio = RangesKt.coerceIn((float)i / (float)(text.length() - 1) * gradientRange, 0.0f, 1.0f);
            int color = this.lerpColor(colorStart, colorEnd, ratio);
            Color shadowColor = new Color(-16777216, true);
            if (shadow) {
                fontRenderer.drawString(String.valueOf(text.charAt(i)), (float)posX + 1.0f, (float)y + 1.0f, shadowColor.getRGB());
            }
            fontRenderer.drawString(String.valueOf(text.charAt(i)), posX, y, color);
            posX += fontRenderer.getStringWidth(String.valueOf(text.charAt(i)));
        }
        GlStateManager.func_179117_G();
    }

    public static /* synthetic */ void drawGradientString$default(FontUtils fontUtils, CFontRenderer cFontRenderer, String string, int n, int n2, int n3, int n4, float f, boolean bl, int n5, Object object) {
        if ((n5 & 0x40) != 0) {
            f = 1.0f;
        }
        if ((n5 & 0x80) != 0) {
            bl = false;
        }
        fontUtils.drawGradientString(cFontRenderer, string, n, n2, n3, n4, f, bl);
    }

    public final void drawGradientString(@NotNull FontRenderer fontRenderer, @NotNull String text, int x, int y, int colorStart, int colorEnd, float gradientRange, boolean shadow) {
        Intrinsics.checkNotNullParameter(fontRenderer, "fontRenderer");
        Intrinsics.checkNotNullParameter(text, "text");
        int posX = x;
        int n = 0;
        int n2 = text.length();
        while (n < n2) {
            int i = n++;
            float ratio = RangesKt.coerceIn((float)i / (float)(text.length() - 1) * gradientRange, 0.0f, 1.0f);
            int color = this.lerpColor(colorStart, colorEnd, ratio);
            Color shadowColor = new Color(-16777216, true);
            if (shadow) {
                fontRenderer.func_78276_b(String.valueOf(text.charAt(i)), posX + 1, y + 1, shadowColor.getRGB());
            }
            fontRenderer.func_78276_b(String.valueOf(text.charAt(i)), posX, y, color);
            posX += fontRenderer.func_78256_a(String.valueOf(text.charAt(i)));
        }
        GlStateManager.func_179117_G();
    }

    public static /* synthetic */ void drawGradientString$default(FontUtils fontUtils, FontRenderer fontRenderer, String string, int n, int n2, int n3, int n4, float f, boolean bl, int n5, Object object) {
        if ((n5 & 0x40) != 0) {
            f = 1.0f;
        }
        if ((n5 & 0x80) != 0) {
            bl = false;
        }
        fontUtils.drawGradientString(fontRenderer, string, n, n2, n3, n4, f, bl);
    }

    public final void drawGradientCenterString(@NotNull CFontRenderer fontRenderer, @NotNull String text, int x, int y, int colorStart, int colorEnd, float gradientRange, boolean shadow) {
        Intrinsics.checkNotNullParameter(fontRenderer, "fontRenderer");
        Intrinsics.checkNotNullParameter(text, "text");
        int posX = x;
        int n = 0;
        int n2 = text.length();
        while (n < n2) {
            int i = n++;
            float ratio = RangesKt.coerceIn((float)i / (float)(text.length() - 1) * gradientRange, 0.0f, 1.0f);
            int color = this.lerpColor(colorStart, colorEnd, ratio);
            Color shadowColor = new Color(-16777216, true);
            if (shadow) {
                fontRenderer.drawString(String.valueOf(text.charAt(i)), (float)(posX - fontRenderer.getStringWidth(text) / 2) + 1.0f, (float)y + 1.0f, shadowColor.getRGB());
            }
            fontRenderer.drawString(String.valueOf(text.charAt(i)), (float)(posX - fontRenderer.getStringWidth(text) / 2) + 1.0f, (float)y + 1.0f, color);
            posX += fontRenderer.getStringWidth(String.valueOf(text.charAt(i)));
        }
        GlStateManager.func_179117_G();
    }

    public static /* synthetic */ void drawGradientCenterString$default(FontUtils fontUtils, CFontRenderer cFontRenderer, String string, int n, int n2, int n3, int n4, float f, boolean bl, int n5, Object object) {
        if ((n5 & 0x40) != 0) {
            f = 1.0f;
        }
        if ((n5 & 0x80) != 0) {
            bl = false;
        }
        fontUtils.drawGradientCenterString(cFontRenderer, string, n, n2, n3, n4, f, bl);
    }

    public final void drawGradientCenterString(@NotNull FontRenderer fontRenderer, @NotNull String text, int x, int y, int colorStart, int colorEnd, float gradientRange, boolean shadow) {
        Intrinsics.checkNotNullParameter(fontRenderer, "fontRenderer");
        Intrinsics.checkNotNullParameter(text, "text");
        int posX = x;
        int n = 0;
        int n2 = text.length();
        while (n < n2) {
            int i = n++;
            float ratio = RangesKt.coerceIn((float)i / (float)(text.length() - 1) * gradientRange, 0.0f, 1.0f);
            int color = this.lerpColor(colorStart, colorEnd, ratio);
            Color shadowColor = new Color(-16777216, true);
            if (shadow) {
                fontRenderer.func_78276_b(String.valueOf(text.charAt(i)), posX - fontRenderer.func_78256_a(text) / 2 + 1, y + 1, shadowColor.getRGB());
            }
            fontRenderer.func_78276_b(String.valueOf(text.charAt(i)), posX - fontRenderer.func_78256_a(text) / 2, y, color);
            posX += fontRenderer.func_78256_a(String.valueOf(text.charAt(i)));
        }
        GlStateManager.func_179117_G();
    }

    public static /* synthetic */ void drawGradientCenterString$default(FontUtils fontUtils, FontRenderer fontRenderer, String string, int n, int n2, int n3, int n4, float f, boolean bl, int n5, Object object) {
        if ((n5 & 0x40) != 0) {
            f = 1.0f;
        }
        if ((n5 & 0x80) != 0) {
            bl = false;
        }
        fontUtils.drawGradientCenterString(fontRenderer, string, n, n2, n3, n4, f, bl);
    }

    private final int lerpColor(int colorStart, int colorEnd, float ratio) {
        int startAlpha = colorStart >> 24 & 0xFF;
        int startRed = colorStart >> 16 & 0xFF;
        int startGreen = colorStart >> 8 & 0xFF;
        int startBlue = colorStart & 0xFF;
        int endAlpha = colorEnd >> 24 & 0xFF;
        int endRed = colorEnd >> 16 & 0xFF;
        int endGreen = colorEnd >> 8 & 0xFF;
        int endBlue = colorEnd & 0xFF;
        int alpha = (int)((float)startAlpha + ratio * (float)(endAlpha - startAlpha));
        int red2 = (int)((float)startRed + ratio * (float)(endRed - startRed));
        int green2 = (int)((float)startGreen + ratio * (float)(endGreen - startGreen));
        int blue2 = (int)((float)startBlue + ratio * (float)(endBlue - startBlue));
        return alpha << 24 | red2 << 16 | green2 << 8 | blue2;
    }

    @NotNull
    public final Pair<String, FontRenderer>[] getAllFontDetails() {
        if (cache.size() == 0) {
            cache.clear();
            for (FontRenderer fontOfFonts : Fonts.getFonts()) {
                Object[] details;
                if (Fonts.getFontDetails(fontOfFonts) == null) continue;
                String name = details[0].toString();
                int size = Integer.parseInt(details[1].toString());
                String format = name + ' ' + size;
                cache.add(TuplesKt.to(format, fontOfFonts));
            }
            List<Pair<String, FontRenderer>> $this$sortBy$iv = cache;
            boolean $i$f$sortBy = false;
            if ($this$sortBy$iv.size() > 1) {
                CollectionsKt.sortWith($this$sortBy$iv, new Comparator(){

                    public final int compare(T a, T b) {
                        Pair it = (Pair)a;
                        boolean bl = false;
                        Comparable comparable = (Comparable)((Object)((String)it.getFirst()));
                        it = (Pair)b;
                        Comparable comparable2 = comparable;
                        bl = false;
                        return ComparisonsKt.compareValues(comparable2, (Comparable)((Object)((String)it.getFirst())));
                    }
                });
            }
        }
        Collection $this$toTypedArray$iv = cache;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        Pair[] pairArray = thisCollection$iv.toArray(new Pair[0]);
        if (pairArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        return pairArray;
    }
}

