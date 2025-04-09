/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.components;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.ColorManager;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.utils.extensions.ColorExtensionKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J.\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004J\u001e\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/components/Slider;", "", "()V", "smooth", "", "value", "onDraw", "", "x", "y", "width", "accentColor", "Ljava/awt/Color;", "anim", "setValue", "desired", "min", "max", "CrossSine"})
public final class Slider {
    private float smooth;
    private float value;

    public final void onDraw(float x, float y, float width, @NotNull Color accentColor, float anim) {
        Intrinsics.checkNotNullParameter(accentColor, "accentColor");
        this.smooth = AnimHelperKt.animSmooth(this.smooth, this.value, 0.5f);
        RenderUtils.drawRoundedRect(x - 1.0f, y - 1.5f - 2.5f * anim, width + 2.0f, 3.0f + 5.0f * anim, 1.0f + 2.0f * anim, ColorManager.INSTANCE.getUnusedSlider().getRGB(), 1.0f, Color.WHITE.getRGB());
        RenderUtils.drawRoundedRect(x - 1.0f, y - 1.5f - 2.5f * anim, x + width * (this.smooth / 100.0f) + 1.0f, y + 1.5f + 2.5f * anim, 1.4f + 2.0f * anim, ColorExtensionKt.setAlpha(accentColor, 100).getRGB());
        RenderUtils.drawFilledCircle(x + width * (this.smooth / 100.0f), y, 4.0f + 2.5f * anim, Color.WHITE);
        RenderUtils.drawFilledCircle(x + width * (this.smooth / 100.0f), y, 3.0f + 2.5f * anim, accentColor);
    }

    public final void setValue(float desired, float min, float max) {
        this.value = (desired - min) / (max - min) * 100.0f;
    }
}

