/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.components.Slider;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.ValueElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J@\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0016J0\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0007H\u0016J0\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0007H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/impl/IntElement;", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/ValueElement;", "", "savedValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "(Lnet/ccbluex/liquidbounce/features/value/IntegerValue;)V", "anim", "", "dragged", "", "getSavedValue", "()Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "slider", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/components/Slider;", "drawElement", "mouseX", "mouseY", "x", "y", "width", "bgColor", "Ljava/awt/Color;", "accentColor", "onClick", "", "onRelease", "CrossSine"})
public final class IntElement
extends ValueElement<Integer> {
    @NotNull
    private final IntegerValue savedValue;
    @NotNull
    private final Slider slider;
    private boolean dragged;
    private float anim;

    public IntElement(@NotNull IntegerValue savedValue) {
        Intrinsics.checkNotNullParameter(savedValue, "savedValue");
        super(savedValue);
        this.savedValue = savedValue;
        this.slider = new Slider();
    }

    @NotNull
    public final IntegerValue getSavedValue() {
        return this.savedValue;
    }

    @Override
    public float drawElement(int mouseX, int mouseY, float x, float y, float width, @NotNull Color bgColor, @NotNull Color accentColor) {
        Intrinsics.checkNotNullParameter(bgColor, "bgColor");
        Intrinsics.checkNotNullParameter(accentColor, "accentColor");
        int valueDisplay = Fonts.font40SemiBold.func_78256_a(String.valueOf((float)this.savedValue.getMaximum() + 0.01f));
        float nameLength = (float)Fonts.font40SemiBold.func_78256_a(this.getValue().getName()) - 5.0f;
        float sliderWidth = width - 50.0f - nameLength - (float)valueDisplay;
        float startPoint = x + width - 20.0f - sliderWidth - (float)valueDisplay;
        this.anim = AnimHelperKt.animSmooth(this.anim, this.dragged ? 1.0f : 0.0f, 0.25f);
        float percent = (float)EaseUtils.INSTANCE.easeInSine(this.anim);
        if (this.dragged) {
            this.savedValue.set(Float.valueOf(RangesKt.coerceIn((float)this.savedValue.getMinimum() + (float)(this.savedValue.getMaximum() - this.savedValue.getMinimum()) / sliderWidth * ((float)mouseX - startPoint), (float)this.savedValue.getMinimum(), (float)this.savedValue.getMaximum())));
        }
        Fonts.font40SemiBold.func_175063_a(this.getValue().getName(), x + 10.0f, y + 10.0f - (float)Fonts.font40SemiBold.field_78288_b / 2.0f + 2.0f, -1);
        this.slider.setValue(RangesKt.coerceIn(((Number)this.savedValue.get()).intValue(), this.savedValue.getMinimum(), this.savedValue.getMaximum()), this.savedValue.getMinimum(), this.savedValue.getMaximum());
        this.slider.onDraw(x + width - 20.0f - sliderWidth - (float)valueDisplay, y + 11.0f, sliderWidth, accentColor, percent);
        Fonts.font40SemiBold.func_175063_a(String.valueOf(((Number)this.savedValue.get()).intValue()), x + width - (float)valueDisplay - 10.0f, y + 10.0f - (float)Fonts.font40SemiBold.field_78288_b / 2.0f + 2.0f, -1);
        return this.getValueHeight();
    }

    @Override
    public void onClick(int mouseX, int mouseY, float x, float y, float width) {
        float endPoint;
        int valueDisplay = Fonts.font40SemiBold.func_78256_a(String.valueOf((float)this.savedValue.getMaximum() + 0.01f));
        float nameLength = (float)Fonts.font40SemiBold.func_78256_a(this.getValue().getName()) - 5.0f;
        float sliderWidth = width - 50.0f - nameLength - (float)valueDisplay;
        float startPoint = x + width - 30.0f - sliderWidth - (float)valueDisplay;
        if (MouseUtils.mouseWithinBounds(mouseX, mouseY, startPoint, y + 5.0f, endPoint = x + width - 10.0f - (float)valueDisplay, y + 15.0f)) {
            this.dragged = true;
        }
    }

    @Override
    public void onRelease(int mouseX, int mouseY, float x, float y, float width) {
        if (this.dragged) {
            this.dragged = false;
        }
    }
}

