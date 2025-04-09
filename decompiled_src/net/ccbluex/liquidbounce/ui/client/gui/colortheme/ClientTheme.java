/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui.colortheme;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.modules.client.CustomClientColor;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.extensions.ColorExtensionKt;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u000b\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0017J(\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0017J \u0010\u001b\u001a\u00020\u00142\u0006\u0010\u000b\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0017J \u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0017R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0019\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001e"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/colortheme/ClientTheme;", "", "()V", "ClientColorMode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getClientColorMode", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "fadespeed", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "getFadespeed", "()Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "index", "getIndex", "mode", "", "", "getMode", "()[Ljava/lang/String;", "[Ljava/lang/String;", "getColor", "Ljava/awt/Color;", "", "customColor", "", "getColorFromName", "name", "alpha", "getColorWithAlpha", "setColor", "type", "CrossSine"})
public final class ClientTheme {
    @NotNull
    public static final ClientTheme INSTANCE = new ClientTheme();
    @NotNull
    private static final String[] mode;
    @NotNull
    private static final ListValue ClientColorMode;
    @NotNull
    private static final IntegerValue fadespeed;
    @NotNull
    private static final IntegerValue index;

    private ClientTheme() {
    }

    @NotNull
    public final String[] getMode() {
        return mode;
    }

    @NotNull
    public final ListValue getClientColorMode() {
        return ClientColorMode;
    }

    @NotNull
    public final IntegerValue getFadespeed() {
        return fadespeed;
    }

    @NotNull
    public final IntegerValue getIndex() {
        return index;
    }

    @NotNull
    public final Color setColor(boolean type, int alpha, boolean customColor) {
        Color color;
        String mode2;
        if (CustomClientColor.INSTANCE.getState() && customColor) {
            return CustomClientColor.INSTANCE.getColor(alpha);
        }
        String string = ((String)ClientColorMode.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (mode2 = string) {
            case "cherry": {
                if (type) {
                    color = new Color(215, 171, 168, alpha);
                    break;
                }
                color = new Color(206, 58, 98, alpha);
                break;
            }
            case "water": {
                if (type) {
                    color = new Color(108, 170, 207, alpha);
                    break;
                }
                color = new Color(35, 69, 148, alpha);
                break;
            }
            case "magic": {
                if (type) {
                    color = new Color(255, 180, 255, alpha);
                    break;
                }
                color = new Color(192, 67, 255, alpha);
                break;
            }
            case "darknight": {
                if (type) {
                    color = new Color(203, 200, 204, alpha);
                    break;
                }
                color = new Color(93, 95, 95, alpha);
                break;
            }
            case "sun": {
                if (type) {
                    color = new Color(252, 205, 44, alpha);
                    break;
                }
                color = new Color(255, 143, 0, alpha);
                break;
            }
            case "flower": {
                if (type) {
                    color = new Color(182, 140, 195, alpha);
                    break;
                }
                color = new Color(184, 85, 199, alpha);
                break;
            }
            case "tree": {
                if (type) {
                    color = new Color(76, 255, 102, alpha);
                    break;
                }
                color = new Color(18, 155, 38, alpha);
                break;
            }
            case "loyoi": {
                if (type) {
                    color = new Color(255, 131, 124, alpha);
                    break;
                }
                color = new Color(255, 131, 0, alpha);
                break;
            }
            case "soniga": {
                if (type) {
                    color = new Color(100, 255, 255, alpha);
                    break;
                }
                color = new Color(255, 100, 255, alpha);
                break;
            }
            case "may": {
                if (type) {
                    color = new Color(255, 255, 255, alpha);
                    break;
                }
                color = new Color(255, 80, 255, alpha);
                break;
            }
            case "mint": {
                if (type) {
                    color = new Color(85, 255, 255, alpha);
                    break;
                }
                color = new Color(85, 255, 140, alpha);
                break;
            }
            case "cero": {
                if (type) {
                    color = new Color(170, 255, 170, alpha);
                    break;
                }
                color = new Color(170, 0, 170, alpha);
                break;
            }
            case "azure": {
                if (type) {
                    color = new Color(0, 180, 255, alpha);
                    break;
                }
                color = new Color(0, 90, 255, alpha);
                break;
            }
            case "pumpkin": {
                if (type) {
                    color = new Color(241, 166, 98, alpha);
                    break;
                }
                color = new Color(255, 216, 169, alpha);
                break;
            }
            case "polarized": {
                if (type) {
                    color = new Color(173, 239, 209, alpha);
                    break;
                }
                color = new Color(0, 32, 64, alpha);
                break;
            }
            case "sundae": {
                if (type) {
                    color = new Color(206, 74, 126, alpha);
                    break;
                }
                color = new Color(28, 28, 27, alpha);
                break;
            }
            case "terminal": {
                if (type) {
                    color = new Color(15, 155, 15, alpha);
                    break;
                }
                color = new Color(25, 30, 25, alpha);
                break;
            }
            case "coral": {
                if (type) {
                    color = new Color(244, 168, 150, alpha);
                    break;
                }
                color = new Color(52, 133, 151, alpha);
                break;
            }
            case "fire": {
                if (type) {
                    color = new Color(255, 45, 30, alpha);
                    break;
                }
                color = new Color(255, 123, 15, alpha);
                break;
            }
            case "aqua": {
                if (type) {
                    color = new Color(80, 255, 255, alpha);
                    break;
                }
                color = new Color(80, 190, 255, alpha);
                break;
            }
            case "peony": {
                if (type) {
                    color = new Color(255, 120, 255, alpha);
                    break;
                }
                color = new Color(255, 190, 255, alpha);
                break;
            }
            case "blaze": {
                if (type) {
                    color = new Color(255, 0, 0, alpha);
                    break;
                }
                color = new Color(255, 100, 100, alpha);
                break;
            }
            case "astolfo": {
                if (type) {
                    color = ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(0, 1.0f, 0.6f, -((double)((Number)fadespeed.get()).intValue())), alpha);
                    break;
                }
                color = ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(90, 1.0f, 0.6f, -((double)((Number)fadespeed.get()).intValue())), alpha);
                break;
            }
            case "rainbow": {
                if (type) {
                    color = ColorExtensionKt.setAlpha(ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(0, 1.0f, 1.0f, -((double)((Number)fadespeed.get()).intValue())), alpha), alpha);
                    break;
                }
                color = ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(90, 1.0f, 1.0f, -((double)((Number)fadespeed.get()).intValue())), alpha);
                break;
            }
            default: {
                color = new Color(-1);
            }
        }
        Color color2 = color;
        return color2;
    }

    public static /* synthetic */ Color setColor$default(ClientTheme clientTheme, boolean bl, int n, boolean bl2, int n2, Object object) {
        if ((n2 & 4) != 0) {
            bl2 = true;
        }
        return clientTheme.setColor(bl, n, bl2);
    }

    @NotNull
    public final Color getColor(int index2, boolean customColor) {
        if (CustomClientColor.INSTANCE.getState() && customColor) {
            return CustomClientColor.INSTANCE.getColor();
        }
        if (ClientColorMode.equals("Rainbow")) {
            return ColorUtils.skyRainbow(index2 * ((Number)index.get()).intValue(), 1.0f, 1.0f, -((double)((Number)fadespeed.get()).intValue()));
        }
        if (ClientColorMode.equals("Astolfo")) {
            return ColorUtils.skyRainbow(index2 * ((Number)index.get()).intValue(), 1.0f, 0.6f, -((double)((Number)fadespeed.get()).intValue()));
        }
        Pair[] pairArray = ((String)ClientColorMode.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(pairArray, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        Pair[] mode2 = pairArray;
        pairArray = new Pair[]{TuplesKt.to("cherry", new Pair<Color, Color>(new Color(206, 58, 98), new Color(215, 171, 168))), TuplesKt.to("water", new Pair<Color, Color>(new Color(35, 69, 148), new Color(108, 170, 207))), TuplesKt.to("magic", new Pair<Color, Color>(new Color(255, 180, 255), new Color(181, 139, 194))), TuplesKt.to("tree", new Pair<Color, Color>(new Color(18, 155, 38), new Color(76, 255, 102))), TuplesKt.to("darknight", new Pair<Color, Color>(new Color(93, 95, 95), new Color(203, 200, 204))), TuplesKt.to("sun", new Pair<Color, Color>(new Color(255, 143, 0), new Color(252, 205, 44))), TuplesKt.to("flower", new Pair<Color, Color>(new Color(184, 85, 199), new Color(182, 140, 195))), TuplesKt.to("loyoi", new Pair<Color, Color>(new Color(255, 131, 0), new Color(255, 131, 124))), TuplesKt.to("soniga", new Pair<Color, Color>(new Color(255, 100, 255), new Color(100, 255, 255))), TuplesKt.to("may", new Pair<Color, Color>(new Color(255, 80, 255), new Color(255, 255, 255))), TuplesKt.to("mint", new Pair<Color, Color>(new Color(85, 255, 140), new Color(85, 255, 255))), TuplesKt.to("cero", new Pair<Color, Color>(new Color(170, 0, 170), new Color(170, 255, 170))), TuplesKt.to("azure", new Pair<Color, Color>(new Color(0, 90, 255), new Color(0, 180, 255))), TuplesKt.to("pumpkin", new Pair<Color, Color>(new Color(255, 216, 169), new Color(241, 166, 98))), TuplesKt.to("polarized", new Pair<Color, Color>(new Color(0, 32, 64), new Color(173, 239, 209))), TuplesKt.to("sundae", new Pair<Color, Color>(new Color(28, 28, 27), new Color(206, 74, 126))), TuplesKt.to("terminal", new Pair<Color, Color>(new Color(25, 30, 25), new Color(15, 155, 15))), TuplesKt.to("coral", new Pair<Color, Color>(new Color(52, 133, 151), new Color(244, 168, 150))), TuplesKt.to("fire", new Pair<Color, Color>(new Color(255, 45, 30), new Color(255, 123, 15))), TuplesKt.to("aqua", new Pair<Color, Color>(new Color(80, 255, 255), new Color(80, 190, 255))), TuplesKt.to("peony", new Pair<Color, Color>(new Color(255, 120, 255), new Color(255, 190, 255))), TuplesKt.to("blaze", new Pair<Color, Color>(new Color(255, 0, 0), new Color(255, 100, 100)))};
        Map colorMap2 = MapsKt.mapOf(pairArray);
        Pair colorPair = (Pair)colorMap2.get(mode2);
        return colorPair != null ? ColorUtils.INSTANCE.mixColors((Color)colorPair.getFirst(), (Color)colorPair.getSecond(), ((Number)fadespeed.get()).doubleValue() / 5.0, index2 * ((Number)index.get()).intValue()) : new Color(-1);
    }

    public static /* synthetic */ Color getColor$default(ClientTheme clientTheme, int n, boolean bl, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 0;
        }
        if ((n2 & 2) != 0) {
            bl = true;
        }
        return clientTheme.getColor(n, bl);
    }

    @NotNull
    public final Color getColorFromName(@NotNull String name, int index2, int alpha, boolean customColor) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (CustomClientColor.INSTANCE.getState() && customColor) {
            return CustomClientColor.INSTANCE.getColor();
        }
        Pair[] pairArray = new Pair[]{TuplesKt.to("cherry", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(206, 58, 98), new Color(215, 171, 168), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("water", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(35, 69, 148), new Color(108, 170, 207), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("magic", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 180, 255), new Color(181, 139, 194), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("tree", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(18, 155, 38), new Color(76, 255, 102), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("darknight", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(93, 95, 95), new Color(203, 200, 204), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("sun", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 143, 0), new Color(252, 205, 44), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("flower", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(184, 85, 199), new Color(182, 140, 195), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("loyoi", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 131, 0), new Color(255, 131, 124), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("soniga", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 100, 255), new Color(100, 255, 255), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("may", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 80, 255), new Color(255, 255, 255), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("mint", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(85, 255, 140), new Color(85, 255, 255), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("cero", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(170, 0, 170), new Color(170, 255, 170), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("azure", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(0, 90, 255), new Color(0, 180, 255), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("rainbow", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(this.$index * ((Number)this.this$0.getIndex().get()).intValue(), 1.0f, 1.0f, fadeSpeed * (double)-5), this.$alpha);
            }
        }), TuplesKt.to("astolfo", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(this.$index * ((Number)this.this$0.getIndex().get()).intValue(), 1.0f, 0.6f, fadeSpeed * (double)-5), this.$alpha);
            }
        }), TuplesKt.to("pumpkin", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 216, 169), new Color(241, 166, 98), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("polarized", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(0, 32, 64), new Color(173, 239, 209), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("sundae", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(28, 28, 27), new Color(206, 74, 126), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("terminal", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(25, 30, 25), new Color(15, 155, 15), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("coral", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(52, 133, 151), new Color(244, 168, 150), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("fire", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 45, 30), new Color(255, 123, 15), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("aqua", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(80, 255, 255), new Color(80, 190, 255), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("peony", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 120, 255), new Color(255, 190, 255), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        }), TuplesKt.to("blaze", new Function1<Double, Color>(index2, this, alpha){
            final /* synthetic */ int $index;
            final /* synthetic */ ClientTheme this$0;
            final /* synthetic */ int $alpha;
            {
                this.$index = $index;
                this.this$0 = $receiver;
                this.$alpha = $alpha;
                super(1);
            }

            @NotNull
            public final Color invoke(double fadeSpeed) {
                return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 0, 0), new Color(255, 100, 100), fadeSpeed, this.$index * ((Number)this.this$0.getIndex().get()).intValue()), this.$alpha);
            }
        })};
        Map colorMap2 = MapsKt.mapOf(pairArray);
        double fadeSpeed = ((Number)fadespeed.get()).doubleValue() / 5.0;
        String string = name.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        Function1 function1 = (Function1)colorMap2.get(string);
        Color color = function1 == null ? null : (Color)function1.invoke(fadeSpeed);
        if (color == null) {
            color = new Color(-1);
        }
        return color;
    }

    public static /* synthetic */ Color getColorFromName$default(ClientTheme clientTheme, String string, int n, int n2, boolean bl, int n3, Object object) {
        if ((n3 & 8) != 0) {
            bl = true;
        }
        return clientTheme.getColorFromName(string, n, n2, bl);
    }

    @NotNull
    public final Color getColorWithAlpha(int index2, int alpha, boolean customColor) {
        Color color;
        if (CustomClientColor.INSTANCE.getState() && customColor) {
            return CustomClientColor.INSTANCE.getColor(alpha);
        }
        double fadeSpeed = ((Number)fadespeed.get()).doubleValue() / 5.0;
        String string = ((String)ClientColorMode.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "cherry": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(206, 58, 98), new Color(215, 171, 168), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "water": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(35, 69, 148), new Color(108, 170, 207), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "magic": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 180, 255), new Color(181, 139, 194), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "tree": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(18, 155, 38), new Color(76, 255, 102), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "darknight": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(93, 95, 95), new Color(203, 200, 204), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "sun": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 143, 0), new Color(252, 205, 44), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "flower": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(184, 85, 199), new Color(182, 140, 195), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "loyoi": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 131, 0), new Color(255, 131, 124), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "soniga": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 100, 255), new Color(100, 255, 255), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "may": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 80, 255), new Color(255, 255, 255), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "mint": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(85, 255, 180), new Color(85, 255, 255), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "cero": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(170, 0, 170), new Color(170, 255, 170), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "azure": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(0, 90, 255), new Color(0, 180, 255), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "rainbow": {
                color = ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(index2 * ((Number)index.get()).intValue(), 1.0f, 1.0f, fadeSpeed * (double)-5), alpha);
                break;
            }
            case "astolfo": {
                color = ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(index2 * ((Number)index.get()).intValue(), 1.0f, 0.6f, fadeSpeed * (double)-5), alpha);
                break;
            }
            case "pumpkin": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 216, 169), new Color(241, 166, 98), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "polarized": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(0, 32, 64), new Color(173, 239, 209), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "sundae": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(28, 28, 27), new Color(206, 74, 126), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "terminal": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(25, 30, 25), new Color(15, 155, 15), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "coral": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(52, 133, 151), new Color(244, 168, 150), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "fire": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 45, 30), new Color(255, 123, 15), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "aqua": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(80, 255, 255), new Color(80, 190, 255), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "peony": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 120, 255), new Color(255, 190, 255), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            case "blaze": {
                color = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 0, 0), new Color(255, 100, 100), fadeSpeed, index2 * ((Number)index.get()).intValue()), alpha);
                break;
            }
            default: {
                color = new Color(-1);
            }
        }
        return color;
    }

    public static /* synthetic */ Color getColorWithAlpha$default(ClientTheme clientTheme, int n, int n2, boolean bl, int n3, Object object) {
        if ((n3 & 4) != 0) {
            bl = true;
        }
        return clientTheme.getColorWithAlpha(n, n2, bl);
    }

    static {
        String[] stringArray = new String[]{"Cherry", "Water", "Magic", "DarkNight", "Sun", "Tree", "Flower", "Loyoi", "Soniga", "May", "Mint", "Cero", "Azure", "Rainbow", "Astolfo", "Pumpkin", "Polarized", "Sundae", "Terminal", "Coral", "Fire", "Aqua", "Peony", "Blaze"};
        mode = stringArray;
        ClientColorMode = new ListValue("ColorMode", mode, "Cherry");
        fadespeed = new IntegerValue("Fade-speed", 1, 1, 10);
        index = new IntegerValue("index", 1, 1, 10);
    }
}

