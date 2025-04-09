/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.value.TitleValue;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.ValueElement;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J@\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J0\u0010\u0011\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0007H\u0016\u00a8\u0006\u0013"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/impl/TitleElement;", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/ValueElement;", "", "value", "Lnet/ccbluex/liquidbounce/features/value/TitleValue;", "(Lnet/ccbluex/liquidbounce/features/value/TitleValue;)V", "drawElement", "", "mouseX", "", "mouseY", "x", "y", "width", "bgColor", "Ljava/awt/Color;", "accentColor", "onClick", "", "CrossSine"})
public final class TitleElement
extends ValueElement<String> {
    public TitleElement(@NotNull TitleValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        super(value);
    }

    @Override
    public float drawElement(int mouseX, int mouseY, float x, float y, float width, @NotNull Color bgColor, @NotNull Color accentColor) {
        Intrinsics.checkNotNullParameter(bgColor, "bgColor");
        Intrinsics.checkNotNullParameter(accentColor, "accentColor");
        Fonts.font50SemiBold.func_175063_a(this.getValue().getName(), x + 10.0f, y + 10.0f - (float)Fonts.font50SemiBold.field_78288_b / 2.0f + 2.0f, -1);
        return this.getValueHeight();
    }

    @Override
    public void onClick(int mouseX, int mouseY, float x, float y, float width) {
    }
}

