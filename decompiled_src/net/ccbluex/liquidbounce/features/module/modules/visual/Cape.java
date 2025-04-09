/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Cape", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0012B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0005R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u00058VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0013"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Cape;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "capeCache", "Ljava/util/HashMap;", "", "Lnet/ccbluex/liquidbounce/features/module/modules/visual/Cape$CapeStyle;", "Lkotlin/collections/HashMap;", "styleValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getStyleValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "tag", "getTag", "()Ljava/lang/String;", "getCapeLocation", "Lnet/minecraft/util/ResourceLocation;", "value", "CapeStyle", "CrossSine"})
public final class Cape
extends Module {
    @NotNull
    private final ListValue styleValue;
    @NotNull
    private final HashMap<String, CapeStyle> capeCache;

    public Cape() {
        String[] stringArray = new String[]{"CrossSine", "Astolfo", "Black", "Rise", "Novoline", "Styles", "None"};
        this.styleValue = new ListValue("Style", stringArray, "None");
        this.capeCache = new HashMap();
        this.setState(true);
    }

    @NotNull
    public final ListValue getStyleValue() {
        return this.styleValue;
    }

    @NotNull
    public final ResourceLocation getCapeLocation(@NotNull String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        Locale locale = Locale.getDefault();
        Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
        Object object = value.toUpperCase(locale);
        Intrinsics.checkNotNullExpressionValue(object, "this as java.lang.String).toUpperCase(locale)");
        if (this.capeCache.get(object) == null) {
            try {
                Map map = this.capeCache;
                object = Locale.getDefault();
                Intrinsics.checkNotNullExpressionValue(object, "getDefault()");
                Object object2 = value.toUpperCase((Locale)object);
                Intrinsics.checkNotNullExpressionValue(object2, "this as java.lang.String).toUpperCase(locale)");
                String string = object2;
                object2 = Locale.getDefault();
                Intrinsics.checkNotNullExpressionValue(object2, "getDefault()");
                String string2 = value.toUpperCase((Locale)object2);
                Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toUpperCase(locale)");
                map.put(string, CapeStyle.valueOf(string2));
            }
            catch (Exception e) {
                Map map = this.capeCache;
                Locale locale2 = Locale.getDefault();
                Intrinsics.checkNotNullExpressionValue(locale2, "getDefault()");
                String string = value.toUpperCase(locale2);
                Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toUpperCase(locale)");
                map.put(string, CapeStyle.CROSSSINE);
            }
        }
        locale = Locale.getDefault();
        Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
        object = value.toUpperCase(locale);
        Intrinsics.checkNotNullExpressionValue(object, "this as java.lang.String).toUpperCase(locale)");
        CapeStyle capeStyle = this.capeCache.get(object);
        Intrinsics.checkNotNull((Object)capeStyle);
        return capeStyle.getLocation();
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)this.styleValue.get();
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f\u00a8\u0006\r"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Cape$CapeStyle;", "", "location", "Lnet/minecraft/util/ResourceLocation;", "(Ljava/lang/String;ILnet/minecraft/util/ResourceLocation;)V", "getLocation", "()Lnet/minecraft/util/ResourceLocation;", "CROSSSINE", "ASTOLFO", "BLACK", "RISE", "NOVOLINE", "STYLES", "CrossSine"})
    public static final class CapeStyle
    extends Enum<CapeStyle> {
        @NotNull
        private final ResourceLocation location;
        public static final /* enum */ CapeStyle CROSSSINE = new CapeStyle(new ResourceLocation("crosssine/cape/crosssine.png"));
        public static final /* enum */ CapeStyle ASTOLFO = new CapeStyle(new ResourceLocation("crosssine/cape/astolfo.png"));
        public static final /* enum */ CapeStyle BLACK = new CapeStyle(new ResourceLocation("crosssine/cape/black.png"));
        public static final /* enum */ CapeStyle RISE = new CapeStyle(new ResourceLocation("crosssine/cape/risecape.png"));
        public static final /* enum */ CapeStyle NOVOLINE = new CapeStyle(new ResourceLocation("crosssine/cape/novoline.png"));
        public static final /* enum */ CapeStyle STYLES = new CapeStyle(new ResourceLocation("crosssine/cape/styles.png"));
        private static final /* synthetic */ CapeStyle[] $VALUES;

        private CapeStyle(ResourceLocation location) {
            this.location = location;
        }

        @NotNull
        public final ResourceLocation getLocation() {
            return this.location;
        }

        public static CapeStyle[] values() {
            return (CapeStyle[])$VALUES.clone();
        }

        public static CapeStyle valueOf(String value) {
            return Enum.valueOf(CapeStyle.class, value);
        }

        static {
            $VALUES = capeStyleArray = new CapeStyle[]{CapeStyle.CROSSSINE, CapeStyle.ASTOLFO, CapeStyle.BLACK, CapeStyle.RISE, CapeStyle.NOVOLINE, CapeStyle.STYLES};
        }
    }
}

