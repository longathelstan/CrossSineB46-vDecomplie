/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.client.ArrayListModule;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.FontValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="ArrayList", category=ModuleCategory.CLIENT, loadConfig=false)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010#\u001a\u00020\t2\u0006\u0010$\u001a\u00020\t2\u0006\u0010%\u001a\u00020\tH\u0002J\u0010\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)H\u0002J\u0010\u0010*\u001a\u00020\u00152\u0006\u0010+\u001a\u00020\u0001H\u0002J\u0010\u0010,\u001a\u00020\u00152\u0006\u0010-\u001a\u00020\u0001H\u0002J\u0010\u0010.\u001a\u00020/2\u0006\u00100\u001a\u000201H\u0007J\u0010\u00102\u001a\u00020/2\u0006\u00100\u001a\u000203H\u0007J\u0010\u00104\u001a\u00020\u001c2\u0006\u0010-\u001a\u00020\u0001H\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0006R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000bR\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0019\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000fR\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u000bR\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00150\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u000bR\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001c0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u000bR\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00065"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/client/ArrayListModule;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "animationMode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getAnimationMode", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "animationSpeed", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "getAnimationSpeed", "()Lnet/ccbluex/liquidbounce/features/value/Value;", "backgroundValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getBackgroundValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "caseValue", "getCaseValue", "fontValue", "Lnet/ccbluex/liquidbounce/features/value/FontValue;", "modeList", "", "getModeList", "modules", "", "noRender", "getNoRender", "rectRound", "", "getRectRound", "rectValue", "getRectValue", "rounded", "getRounded", "sortedModules", "calculateRadius", "prevWidth", "currentWidth", "getColor", "Ljava/awt/Color;", "index", "", "getModName", "mod", "getModuleTag", "module", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "shouldExpect", "CrossSine"})
public final class ArrayListModule
extends Module {
    @NotNull
    public static final ArrayListModule INSTANCE = new ArrayListModule();
    @NotNull
    private static final BoolValue backgroundValue = new BoolValue("Background", false);
    @NotNull
    private static final Value<Boolean> rounded = new BoolValue("Rounded", false).displayable(rounded.1.INSTANCE);
    @NotNull
    private static final Value<String> modeList;
    @NotNull
    private static final Value<Boolean> rectRound;
    @NotNull
    private static final Value<String> rectValue;
    @NotNull
    private static final ListValue animationMode;
    @NotNull
    private static final Value<Float> animationSpeed;
    @NotNull
    private static final ListValue caseValue;
    @NotNull
    private static final BoolValue noRender;
    @NotNull
    private static final FontValue fontValue;
    @NotNull
    private static List<? extends Module> modules;
    @NotNull
    private static List<? extends Module> sortedModules;

    private ArrayListModule() {
    }

    @NotNull
    public final BoolValue getBackgroundValue() {
        return backgroundValue;
    }

    @NotNull
    public final Value<Boolean> getRounded() {
        return rounded;
    }

    @NotNull
    public final Value<String> getModeList() {
        return modeList;
    }

    @NotNull
    public final Value<Boolean> getRectRound() {
        return rectRound;
    }

    @NotNull
    public final Value<String> getRectValue() {
        return rectValue;
    }

    @NotNull
    public final ListValue getAnimationMode() {
        return animationMode;
    }

    @NotNull
    public final Value<Float> getAnimationSpeed() {
        return animationSpeed;
    }

    @NotNull
    public final ListValue getCaseValue() {
        return caseValue;
    }

    @NotNull
    public final BoolValue getNoRender() {
        return noRender;
    }

    /*
     * WARNING - void declaration
     */
    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter(event, "event");
        Iterable $this$filter$iv = CrossSine.INSTANCE.getModuleManager().getModules();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Module it = (Module)element$iv$iv;
            boolean bl = false;
            if (!(it.getArray() && !INSTANCE.shouldExpect(it) && it.getSlide() > 0.0f)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$sortedBy$iv = (List)destination$iv$iv;
        boolean $i$f$sortedBy = false;
        modules = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator(){

            public final int compare(T a, T b) {
                Module it = (Module)a;
                boolean bl = false;
                Comparable comparable = Integer.valueOf(-((FontRenderer)ArrayListModule.access$getFontValue$p().get()).func_78256_a(ArrayListModule.access$getModName(ArrayListModule.INSTANCE, it)));
                it = (Module)b;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues(comparable2, -((FontRenderer)ArrayListModule.access$getFontValue$p().get()).func_78256_a(ArrayListModule.access$getModName(ArrayListModule.INSTANCE, it)));
            }
        });
        $this$sortedBy$iv = CrossSine.INSTANCE.getModuleManager().getModules();
        $i$f$sortedBy = false;
        sortedModules = CollectionsKt.toList(CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator(){

            public final int compare(T a, T b) {
                Module it = (Module)a;
                boolean bl = false;
                Comparable comparable = Integer.valueOf(-((FontRenderer)ArrayListModule.access$getFontValue$p().get()).func_78256_a(ArrayListModule.access$getModName(ArrayListModule.INSTANCE, it)));
                it = (Module)b;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues(comparable2, -((FontRenderer)ArrayListModule.access$getFontValue$p().get()).func_78256_a(ArrayListModule.access$getModName(ArrayListModule.INSTANCE, it)));
            }
        }));
    }

    /*
     * WARNING - void declaration
     */
    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        FontRenderer fontRenderer = (FontRenderer)fontValue.get();
        int delta = RenderUtils.deltaTime;
        float textHeight = (float)((FontRenderer)ArrayListModule.fontValue.get()).field_78288_b + 3.0f;
        float textY = rounded.get() != false && (Boolean)backgroundValue.get() != false ? 2.2f : 1.3f;
        int inx = 0;
        for (Module module : sortedModules) {
            if (module.getArray() && !this.shouldExpect(module)) {
                String displayString = this.getModName(module);
                int width = fontRenderer.func_78256_a(displayString);
                if (module.getState() || !(module.getZoom() == 0.0f)) {
                    if (module.getState()) {
                        module.setZoom(module.getZoom() + (1.0f - module.getZoom()) * 0.2f);
                    } else if (module.getZoom() > 0.0f) {
                        module.setZoom(module.getZoom() + (0.0f - module.getZoom()) * 0.2f);
                    }
                }
                if (module.getState() || !(module.getSlide() == 0.0f)) {
                    if (module.getState()) {
                        module.setSlide(module.getSlide() + ((float)width - module.getSlide()) * ((Number)animationSpeed.get()).floatValue());
                        module.setSlideStep((float)delta / 1.0f);
                    } else if (module.getSlide() > 0.0f) {
                        module.setSlide(module.getSlide() + ((float)(-width) - module.getSlide()) * ((Number)animationSpeed.get()).floatValue());
                        module.setSlideStep(0.0f);
                    }
                }
                module.setZoom(RangesKt.coerceIn(module.getZoom(), 0.0f, 1.0f));
                module.setSlide(RangesKt.coerceIn(module.getSlide(), 0.0f, (float)width));
                module.setSlideStep(RangesKt.coerceIn(module.getSlideStep(), 0.0f, (float)width));
            }
            float yPos = (textHeight + (rounded.get() != false && (Boolean)backgroundValue.get() != false && modeList.equals("Modern") ? 1.5f : 0.0f)) * (float)inx;
            if (module.getArray() && !this.shouldExpect(module) && module.getSlide() > 0.0f) {
                if (!animationMode.equals("None")) {
                    module.setArrayY(module.getArrayY() + (yPos - module.getArrayY()) * ((Number)animationSpeed.get()).floatValue());
                } else {
                    module.setArrayY(yPos);
                }
                int n = inx;
                inx = n + 1;
                continue;
            }
            module.setArrayY(yPos);
        }
        Iterable $this$forEachIndexed$iv = modules;
        boolean bl = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            void module;
            int n = index$iv;
            index$iv = n + 1;
            if (n < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Module module2 = (Module)item$iv;
            int index2 = n;
            boolean bl2 = false;
            String displayString = INSTANCE.getModName((Module)module);
            float xPos = (float)new ScaledResolution(MinecraftInstance.mc).func_78326_a() - (INSTANCE.getAnimationMode().equals("Slide") ? module.getSlide() : (float)((FontRenderer)fontValue.get()).func_78256_a(displayString)) - (float)2;
            float currentWidth = ((FontRenderer)fontValue.get()).func_78256_a(displayString);
            float nextWidth = index2 < modules.size() - 1 ? (float)((FontRenderer)fontValue.get()).func_78256_a(INSTANCE.getModName(modules.get(index2 + 1))) : currentWidth;
            float topLeftRadius = index2 == 0 ? 4.0f : 0.0f;
            float topRightRadius = index2 == 0 && INSTANCE.getRectRound().get() == false ? 4.0f : 0.0f;
            float bottomLeftRadius = index2 == modules.size() - 1 ? 4.0f : INSTANCE.calculateRadius(currentWidth, nextWidth);
            float bottomRightRadius = index2 == modules.size() - 1 && INSTANCE.getRectRound().get() == false ? 4.0f : 0.0f;
            GlStateManager.func_179094_E();
            if (INSTANCE.getAnimationMode().equals("Zoom")) {
                GlStateManager.func_179109_b((float)(xPos + (float)fontRenderer.func_78256_a(displayString) / 2.0f), (float)(module.getArrayY() + textY + ((Boolean)INSTANCE.getBackgroundValue().get() != false && INSTANCE.getRounded().get() != false ? 5.0f : (StringsKt.equals(INSTANCE.getRectValue().get(), "Outline", true) ? 1.0f : 0.0f)) + (float)fontRenderer.field_78288_b / 2.0f), (float)0.0f);
                GlStateManager.func_179152_a((float)module.getZoom(), (float)module.getZoom(), (float)1.0f);
                GlStateManager.func_179109_b((float)(-(xPos + (float)fontRenderer.func_78256_a(displayString) / 2.0f)), (float)(-(module.getArrayY() + textY + ((Boolean)INSTANCE.getBackgroundValue().get() != false && INSTANCE.getRounded().get() != false ? 5.0f : (StringsKt.equals(INSTANCE.getRectValue().get(), "Outline", true) ? 1.0f : 0.0f)) + (float)fontRenderer.field_78288_b / 2.0f)), (float)0.0f);
            }
            if (((Boolean)INSTANCE.getBackgroundValue().get()).booleanValue()) {
                if (INSTANCE.getRounded().get().booleanValue()) {
                    if (INSTANCE.getModeList().equals("Modern")) {
                        RenderUtils.customRoundedinf((float)((int)xPos) - 3.0f - (float)(INSTANCE.getRectRound().get() != false ? 5 : 4), module.getArrayY() + (float)5, (float)new ScaledResolution(MinecraftInstance.mc).func_78326_a() - (float)2, module.getArrayY() + textHeight + (float)5, 4.0f, INSTANCE.getRectRound().get() != false ? 0.0f : 4.0f, INSTANCE.getRectRound().get() != false ? 0.0f : 4.0f, 4.0f, new Color(0, 0, 0, 150).getRGB());
                    } else {
                        RenderUtils.customRoundedinf((float)((int)xPos) - 3.0f - (float)(INSTANCE.getRectRound().get() != false ? 5 : 4), module.getArrayY() + (float)5, (float)new ScaledResolution(MinecraftInstance.mc).func_78326_a() - (float)2, module.getArrayY() + textHeight + (float)5, topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius, new Color(0, 0, 0, 150).getRGB());
                    }
                } else {
                    RenderUtils.drawRect((float)new ScaledResolution(MinecraftInstance.mc).func_78326_a(), module.getArrayY() + (StringsKt.equals(INSTANCE.getRectValue().get(), "Outline", true) ? 1.0f : 0.0f), (float)((int)xPos) - (StringsKt.equals(INSTANCE.getRectValue().get(), "right", true) ? 3.0f : 2.0f), module.getArrayY() + textHeight + (StringsKt.equals(INSTANCE.getRectValue().get(), "Outline", true) ? 1.0f : 0.0f), new Color(0, 0, 0, 150).getRGB());
                }
            }
            fontRenderer.func_175065_a(displayString, xPos - (float)(((Boolean)INSTANCE.getBackgroundValue().get()).booleanValue() && INSTANCE.getRounded().get().booleanValue() ? (INSTANCE.getRectRound().get().booleanValue() ? 4 : 3) : (StringsKt.equals(INSTANCE.getRectValue().get(), "right", true) ? 1 : 0)), module.getArrayY() + textY + ((Boolean)INSTANCE.getBackgroundValue().get() != false && INSTANCE.getRounded().get() != false ? 5.0f : (StringsKt.equals(INSTANCE.getRectValue().get(), "Outline", true) ? 1.0f : 0.0f)), INSTANCE.getColor(index2).getRGB(), true);
            if (((Boolean)INSTANCE.getBackgroundValue().get()).booleanValue() && INSTANCE.getRounded().get().booleanValue() && INSTANCE.getRectRound().get().booleanValue()) {
                RenderUtils.drawRoundedRect(xPos - 1.4f + (float)((FontRenderer)fontValue.get()).func_78256_a(displayString), module.getArrayY() + 5.0f, xPos + (float)((FontRenderer)fontValue.get()).func_78256_a(displayString), module.getArrayY() + textHeight + 5.0f, INSTANCE.getModeList().equals("Normal") ? 0.0f : 1.0f, INSTANCE.getColor(index2).getRGB());
                GlStateManager.func_179117_G();
            } else if (!(StringsKt.equals(INSTANCE.getRectValue().get(), "none", true) || INSTANCE.getRounded().get().booleanValue() && ((Boolean)INSTANCE.getBackgroundValue().get()).booleanValue())) {
                int rectColor = INSTANCE.getColor(index2).getRGB();
                if (StringsKt.equals(INSTANCE.getRectValue().get(), "left", true)) {
                    RenderUtils.drawRect(xPos - (float)3, module.getArrayY(), xPos - (float)2, module.getArrayY() + textHeight, rectColor);
                } else if (StringsKt.equals(INSTANCE.getRectValue().get(), "right", true)) {
                    RenderUtils.drawRect((float)new ScaledResolution(MinecraftInstance.mc).func_78326_a() - 1.0f, module.getArrayY(), (float)new ScaledResolution(MinecraftInstance.mc).func_78326_a(), module.getArrayY() + textHeight, rectColor);
                } else if (StringsKt.equals(INSTANCE.getRectValue().get(), "outline", true)) {
                    RenderUtils.drawRect((float)new ScaledResolution(MinecraftInstance.mc).func_78326_a() - 1.0f, module.getArrayY() - 1.0f, (float)new ScaledResolution(MinecraftInstance.mc).func_78326_a(), module.getArrayY() + textHeight, rectColor);
                    RenderUtils.drawRect(xPos - (float)3, module.getArrayY(), xPos - (float)2, module.getArrayY() + textHeight, rectColor);
                    if (!Intrinsics.areEqual(module, modules.get(0))) {
                        String displayStrings = INSTANCE.getModName(modules.get(index2 - 1));
                        RenderUtils.drawRect(xPos - (float)3 - (float)(fontRenderer.func_78256_a(displayStrings) - fontRenderer.func_78256_a(displayString)), module.getArrayY(), xPos - (float)2, module.getArrayY() + 1.0f, rectColor);
                        if (Intrinsics.areEqual(module, modules.get(modules.size() - 1))) {
                            RenderUtils.drawRect(xPos - (float)3, module.getArrayY() + textHeight, (float)new ScaledResolution(MinecraftInstance.mc).func_78326_a(), module.getArrayY() + textHeight + 1.0f, rectColor);
                        }
                    } else {
                        RenderUtils.drawRect(xPos - (float)3, module.getArrayY() + 1.0f, (float)new ScaledResolution(MinecraftInstance.mc).func_78326_a(), module.getArrayY(), rectColor);
                    }
                } else if (StringsKt.equals(INSTANCE.getRectValue().get(), "special", true)) {
                    if (Intrinsics.areEqual(module, modules.get(0))) {
                        RenderUtils.drawRect(xPos - (float)2, module.getArrayY(), (float)new ScaledResolution(MinecraftInstance.mc).func_78326_a(), module.getArrayY() - 1.0f, rectColor);
                    }
                    if (Intrinsics.areEqual(module, modules.get(modules.size() - 1))) {
                        RenderUtils.drawRect(xPos - (float)2, module.getArrayY() + textHeight, (float)new ScaledResolution(MinecraftInstance.mc).func_78326_a(), module.getArrayY() + textHeight + 1.0f, rectColor);
                    }
                } else if (StringsKt.equals(INSTANCE.getRectValue().get(), "top", true) && Intrinsics.areEqual(module, modules.get(0))) {
                    RenderUtils.drawRect(xPos - (float)2, module.getArrayY(), (float)new ScaledResolution(MinecraftInstance.mc).func_78326_a(), module.getArrayY() - 1.0f, rectColor);
                }
            }
            GlStateManager.func_179121_F();
        }
    }

    private final float calculateRadius(float prevWidth, float currentWidth) {
        float diff = Math.abs(prevWidth - currentWidth);
        return RangesKt.coerceIn(diff, 0.0f, 5.0f);
    }

    private final String getModuleTag(Module module) {
        if (module.getTag() == null) {
            return "";
        }
        String string = module.getTag();
        Intrinsics.checkNotNull(string);
        if (StringsKt.contains$default((CharSequence)string, "\u00a7", false, 2, null)) {
            String string2 = module.getTag();
            Intrinsics.checkNotNull(string2);
            return string2;
        }
        return Intrinsics.stringPlus("\u00a77 ", module.getTag());
    }

    private final String getModName(Module mod) {
        String displayName = Intrinsics.stringPlus(mod.getLocalizedName(), this.getModuleTag(mod));
        String string = ((String)caseValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String string2 = string;
        if (Intrinsics.areEqual(string2, "lower")) {
            string = displayName.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            displayName = string;
        } else if (Intrinsics.areEqual(string2, "upper")) {
            string = displayName.toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toUpperCase(Locale.ROOT)");
            displayName = string;
        }
        return displayName;
    }

    private final boolean shouldExpect(Module module) {
        return (Boolean)noRender.get() != false && module.getCategory() == ModuleCategory.VISUAL || Intrinsics.areEqual(module.getName(), "CustomClientColor") || Intrinsics.areEqual(module.getName(), "ArrayList") || Intrinsics.areEqual(module.getName(), "ChatManager") || Intrinsics.areEqual(module.getName(), "Scoreboard") || Intrinsics.areEqual(module.getName(), "TargetHUD") || Intrinsics.areEqual(module.getName(), "Interface") || Intrinsics.areEqual(module.getName(), "KeyStrokes");
    }

    private final Color getColor(int index2) {
        return ClientTheme.getColor$default(ClientTheme.INSTANCE, index2, false, 2, null);
    }

    public static final /* synthetic */ FontValue access$getFontValue$p() {
        return fontValue;
    }

    public static final /* synthetic */ String access$getModName(ArrayListModule $this, Module mod) {
        return $this.getModName(mod);
    }

    static {
        String[] stringArray = new String[]{"Normal", "Modern"};
        modeList = new ListValue("RoundedMode", stringArray, "Normal").displayable(modeList.1.INSTANCE);
        rectRound = new BoolValue("Rect", false).displayable(rectRound.1.INSTANCE);
        stringArray = new String[]{"None", "Left", "Right", "Outline", "Special", "Top"};
        rectValue = new ListValue("RectMode", stringArray, "Outline").displayable(rectValue.1.INSTANCE);
        stringArray = new String[]{"Slide", "Zoom", "None"};
        animationMode = new ListValue("AnimationMode", stringArray, "Slide");
        animationSpeed = new FloatValue("AnimationSpeed", 0.2f, 0.1f, 0.5f).displayable(animationSpeed.1.INSTANCE);
        stringArray = new String[]{"None", "Lower", "Upper"};
        caseValue = new ListValue("Case", stringArray, "None");
        noRender = new BoolValue("NoRenderModule", false);
        stringArray = Fonts.minecraftFont;
        Intrinsics.checkNotNullExpressionValue(stringArray, "minecraftFont");
        fontValue = new FontValue("Font", (FontRenderer)stringArray);
        modules = CollectionsKt.emptyList();
        sortedModules = CollectionsKt.emptyList();
    }
}

