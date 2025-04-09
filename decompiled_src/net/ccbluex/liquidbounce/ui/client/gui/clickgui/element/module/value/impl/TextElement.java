/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.value.TextValue;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.ClickGui;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.ValueElement;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J@\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016J0\u0010\u0012\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\u0016J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\nH\u0016R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/impl/TextElement;", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/ValueElement;", "", "value", "Lnet/ccbluex/liquidbounce/features/value/TextValue;", "(Lnet/ccbluex/liquidbounce/features/value/TextValue;)V", "inputText", "drawElement", "", "mouseX", "", "mouseY", "x", "y", "width", "bgColor", "Ljava/awt/Color;", "accentColor", "onClick", "", "onKeyPress", "", "typed", "", "keyCode", "CrossSine"})
public final class TextElement
extends ValueElement<String> {
    @NotNull
    private String inputText;

    public TextElement(@NotNull TextValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        super(value);
        this.inputText = (String)value.getValue();
    }

    @Override
    public float drawElement(int mouseX, int mouseY, float x, float y, float width, @NotNull Color bgColor, @NotNull Color accentColor) {
        Intrinsics.checkNotNullParameter(bgColor, "bgColor");
        Intrinsics.checkNotNullParameter(accentColor, "accentColor");
        if (ClickGui.Companion.getInstance().getTextEditing()) {
            Fonts.font40SemiBold.func_175063_a(this.getValue().getName() + " : " + this.inputText + '_', x + 10.0f, y + 10.0f - (float)Fonts.font40SemiBold.field_78288_b / 2.0f + 2.0f, -1);
        } else {
            Fonts.font40SemiBold.func_175063_a(Intrinsics.stringPlus(this.getValue().getName(), " : "), x + 10.0f, y + 10.0f - (float)Fonts.font40SemiBold.field_78288_b / 2.0f + 2.0f, -1);
            Fonts.font40SemiBold.func_175063_a(Intrinsics.areEqual(this.getValue().getValue(), "") && !ClickGui.Companion.getInstance().getTextEditing() ? "NO TEXT FOUND" : (String)this.getValue().getValue(), x + 10.0f + (float)Fonts.font40SemiBold.func_78256_a(Intrinsics.stringPlus(this.getValue().getName(), " : ")), y + 10.0f - (float)Fonts.font40SemiBold.field_78288_b / 2.0f + 2.0f, Intrinsics.areEqual(this.getValue().getValue(), "") && !ClickGui.Companion.getInstance().getTextEditing() ? Color.RED.getRGB() : -1);
        }
        return this.getValueHeight();
    }

    @Override
    public void onClick(int mouseX, int mouseY, float x, float y, float width) {
        int textWidth = Fonts.font40SemiBold.func_78256_a(Intrinsics.areEqual(this.getValue().getValue(), "") ? "NO TEXT FOUND" : (String)this.getValue().getValue());
        float textX = x + 15.0f + (float)Fonts.font40SemiBold.func_78256_a(this.getValue().getName());
        float textY = y + 10.0f - (float)Fonts.font40SemiBold.field_78288_b / 2.0f + 2.0f;
        int textHeight = Fonts.font40SemiBold.field_78288_b;
        if ((float)mouseX >= textX && (float)mouseX <= textX + (float)textWidth && (float)mouseY >= textY && (float)mouseY <= textY + (float)textHeight) {
            ClickGui.Companion.getInstance().setTextEditing(!ClickGui.Companion.getInstance().getTextEditing());
            if (ClickGui.Companion.getInstance().getTextEditing()) {
                this.inputText = (String)this.getValue().getValue();
                Keyboard.enableRepeatEvents((boolean)true);
            } else {
                this.getValue().setValue(this.inputText);
                Keyboard.enableRepeatEvents((boolean)false);
            }
        }
    }

    @Override
    public boolean onKeyPress(char typed, int keyCode) {
        if (ClickGui.Companion.getInstance().getTextEditing()) {
            if (keyCode == 28 || keyCode == 1) {
                ClickGui.Companion.getInstance().setTextEditing(false);
                this.getValue().setValue(this.inputText);
                Keyboard.enableRepeatEvents((boolean)false);
                return true;
            }
            if (keyCode == 14) {
                if (((CharSequence)this.inputText).length() > 0) {
                    String string = this.inputText.substring(0, this.inputText.length() - 1);
                    Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String\u2026ing(startIndex, endIndex)");
                    this.inputText = string;
                }
                return true;
            }
            if (!Character.isISOControl(typed)) {
                this.inputText = Intrinsics.stringPlus(this.inputText, Character.valueOf(typed));
                return true;
            }
        }
        return false;
    }
}

