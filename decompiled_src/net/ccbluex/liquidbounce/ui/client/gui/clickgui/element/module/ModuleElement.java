/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.FontValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.KeyBindValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.TextValue;
import net.ccbluex.liquidbounce.features.value.TitleValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.ClickGui;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.ValueElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.BooleanElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.FloatElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.FontElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.IntElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.KeyBindElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.ListElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.TextElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.TitleElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J>\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u00062\u0006\u0010#\u001a\u00020$J>\u0010%\u001a\u00020&2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010'\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0006J\u0016\u0010(\u001a\u00020\r2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u001dJ6\u0010,\u001a\u00020&2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0006J\u0006\u0010-\u001a\u00020\rJ\u0006\u0010.\u001a\u00020&R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0018\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001a0\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/ModuleElement;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "module", "Lnet/ccbluex/liquidbounce/features/module/Module;", "(Lnet/ccbluex/liquidbounce/features/module/Module;)V", "animHeight", "", "getAnimHeight", "()F", "setAnimHeight", "(F)V", "animPercent", "expanded", "", "getExpanded", "()Z", "setExpanded", "(Z)V", "fadeKeybind", "listeningToKey", "getModule", "()Lnet/ccbluex/liquidbounce/features/module/Module;", "smooth", "smoothHovered", "valueElements", "", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/ValueElement;", "drawElement", "mouseX", "", "mouseY", "x", "y", "width", "height", "accentColor", "Ljava/awt/Color;", "handleClick", "", "mouseButton", "handleKeyTyped", "typed", "", "code", "handleRelease", "listeningKeybind", "resetState", "CrossSine"})
public final class ModuleElement
extends MinecraftInstance {
    @NotNull
    private final Module module;
    @NotNull
    private final List<ValueElement<?>> valueElements;
    private float smooth;
    private float smoothHovered;
    private float animHeight;
    private float fadeKeybind;
    private float animPercent;
    private boolean listeningToKey;
    private boolean expanded;

    public ModuleElement(@NotNull Module module) {
        Intrinsics.checkNotNullParameter(module, "module");
        this.module = module;
        this.valueElements = new ArrayList();
        for (Value<?> value : this.module.getValues()) {
            if (value instanceof BoolValue) {
                this.valueElements.add(new BooleanElement((BoolValue)value));
            }
            if (value instanceof ListValue) {
                this.valueElements.add(new ListElement((ListValue)value));
            }
            if (value instanceof IntegerValue) {
                this.valueElements.add(new IntElement((IntegerValue)value));
            }
            if (value instanceof FloatValue) {
                this.valueElements.add(new FloatElement((FloatValue)value));
            }
            if (value instanceof FontValue) {
                this.valueElements.add(new FontElement((FontValue)value));
            }
            if (value instanceof TitleValue) {
                this.valueElements.add(new TitleElement((TitleValue)value));
            }
            if (value instanceof TextValue) {
                this.valueElements.add(new TextElement((TextValue)value));
            }
            if (!(value instanceof KeyBindValue)) continue;
            this.valueElements.add(new KeyBindElement((KeyBindValue)value));
        }
    }

    @NotNull
    public final Module getModule() {
        return this.module;
    }

    public final float getAnimHeight() {
        return this.animHeight;
    }

    public final void setAnimHeight(float f) {
        this.animHeight = f;
    }

    public final boolean getExpanded() {
        return this.expanded;
    }

    public final void setExpanded(boolean bl) {
        this.expanded = bl;
    }

    public final float drawElement(int mouseX, int mouseY, float x, float y, float width, float height, @NotNull Color accentColor) {
        Intrinsics.checkNotNullParameter(accentColor, "accentColor");
        this.animPercent = AnimHelperKt.animSmooth(this.animPercent, this.expanded ? 100.0f : 0.0f, 0.5f);
        this.smooth = AnimHelperKt.animLinear(this.smooth, (this.module.getState() ? 0.3f : -0.3f) * (float)RenderUtils.deltaTime * 0.025f, 0.0f, 1.0f);
        this.smoothHovered = AnimHelperKt.animLinear(this.smoothHovered, (MouseUtils.mouseWithinBounds(mouseX, mouseY, x + 10.0f, y + 5.0f, x + width - 10.0f, y + height - 5.0f) ? 0.15f : -0.15f) * (float)RenderUtils.deltaTime * 0.025f, 0.0f, 1.0f);
        float easeSmooth = (float)EaseUtils.INSTANCE.easeOutCirc(this.smooth);
        float expectedHeight = 0.0f;
        for (ValueElement<?> ve : this.valueElements) {
            if (!ve.isDisplayable()) continue;
            expectedHeight += ve.getValueHeight();
        }
        this.animHeight = this.animPercent / 100.0f * (expectedHeight + 10.0f);
        Stencil.write(true);
        GlStateManager.func_179094_E();
        RenderUtils.drawBloomGradientRoundedRect(x + 10.0f, y + 5.0f, x + width - 10.0f, y + height + this.animHeight - 5.0f, 6.0f, 0.5f, new Color(25 + (int)((float)20 * this.smoothHovered), 25 + (int)((float)20 * this.smoothHovered), 25 + (int)((float)20 * this.smoothHovered)), new Color(35 - (int)((float)20 * this.smoothHovered), 35 - (int)((float)20 * this.smoothHovered), 35 - (int)((float)20 * this.smoothHovered)), RenderUtils.ShaderBloom.BOTH);
        if (this.smooth > 0.0f) {
            RenderUtils.drawRoundedGradientOutlineCorner(x + 10.0f + width / (float)2 - width / (float)2 * easeSmooth, y + 5.0f + ((height + this.animHeight - 5.0f) / (float)2 - (height + this.animHeight - 5.0f) / (float)2 * easeSmooth), x + width - 10.0f - width / (float)2 + width / (float)2 * easeSmooth, y + height + this.animHeight - 5.0f - (height + this.animHeight - 5.0f) / (float)2 + (height + this.animHeight - 5.0f) / (float)2 * easeSmooth, 3.0f, 8.0f * easeSmooth, ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 0, 255, false, 4, null).getRGB(), ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 90, 255, false, 4, null).getRGB(), ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 180, 255, false, 4, null).getRGB(), ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 270, 255, false, 4, null).getRGB());
        }
        GlStateManager.func_179121_F();
        GlStateManager.func_179117_G();
        Stencil.erase(true);
        Fonts.font40SemiBold.func_175063_a(this.module.getName(), x + 18.5f, y + 4.0f + height / 2.0f - (float)Fonts.font40SemiBold.field_78288_b + 3.0f, -1);
        String keyName = this.listeningToKey ? "Listening" : Keyboard.getKeyName((int)this.module.getKeyBind());
        float f = x + 25.0f + (float)Fonts.font40SemiBold.func_78256_a(this.module.getName());
        float f2 = y + height / 2.0f - (float)Fonts.font40SemiBold.field_78288_b + 4.5f;
        float f3 = x + 35.0f + (float)Fonts.font40SemiBold.func_78256_a(this.module.getName());
        Intrinsics.checkNotNullExpressionValue(keyName, "keyName");
        this.fadeKeybind = MouseUtils.mouseWithinBounds(mouseX, mouseY, f, f2, f3 + (float)Fonts.font24SemiBold.func_78256_a(keyName), y + 2.5f + height / 2.0f) ? RangesKt.coerceIn(this.fadeKeybind + 0.1f * (float)RenderUtils.deltaTime * 0.025f, 0.0f, 1.0f) : RangesKt.coerceIn(this.fadeKeybind - 0.1f * (float)RenderUtils.deltaTime * 0.025f, 0.0f, 1.0f);
        RenderUtils.drawRoundedRect(x + 25.0f + (float)Fonts.font40SemiBold.func_78256_a(this.module.getName()), y + height / 2.0f - (float)Fonts.font40SemiBold.field_78288_b + 6.5f, x + 35.0f + (float)Fonts.font40SemiBold.func_78256_a(this.module.getName()) + (float)Fonts.font24SemiBold.func_78256_a(keyName), y + 4.5f + height / 2.0f, 2.0f, BlendUtils.blend(new Color(-12237499), new Color(-13290187), this.fadeKeybind).getRGB());
        Fonts.font24SemiBold.func_175063_a(keyName, x + 30.5f + (float)Fonts.font40SemiBold.func_78256_a(this.module.getName()), y + height / 2.0f - (float)Fonts.font40SemiBold.field_78288_b + 10.0f, -1);
        if (this.expanded || this.animHeight > 0.0f) {
            float startYPos = y + height;
            for (ValueElement<?> ve : this.valueElements) {
                if (!ve.isDisplayable()) continue;
                startYPos += ve.drawElement(mouseX, mouseY, x + 10.0f, startYPos, width - 20.0f, new Color(-14342875), accentColor);
            }
        }
        Stencil.dispose();
        return height + this.animHeight;
    }

    public final void handleClick(int mouseX, int mouseY, int mouseButton, float x, float y, float width, float height) {
        if (this.listeningToKey) {
            this.resetState();
            return;
        }
        String keyName = this.listeningToKey ? "Listening" : Keyboard.getKeyName((int)this.module.getKeyBind());
        float f = x + 25.0f + (float)Fonts.font40SemiBold.func_78256_a(this.module.getName());
        float f2 = y + height / 2.0f - (float)Fonts.font40SemiBold.field_78288_b + 4.5f;
        float f3 = x + 35.0f + (float)Fonts.font40SemiBold.func_78256_a(this.module.getName());
        Intrinsics.checkNotNullExpressionValue(keyName, "keyName");
        if (MouseUtils.mouseWithinBounds(mouseX, mouseY, f, f2, f3 + (float)Fonts.font24SemiBold.func_78256_a(keyName), y + 2.5f + height / 2.0f)) {
            this.listeningToKey = true;
            ClickGui.Companion.getInstance().setCant(true);
            return;
        }
        if (MouseUtils.mouseWithinBounds(mouseX, mouseY, x + 10.0f, y + 5.0f, x + width - 10.0f, y + height - 5.0f)) {
            if (mouseButton == 0) {
                this.module.toggle();
            } else if (!((Collection)this.module.getValues()).isEmpty() && mouseButton == 1) {
                boolean bl = this.expanded = !this.expanded;
            }
        }
        if (this.expanded) {
            float startY = y + height;
            for (ValueElement<?> ve : this.valueElements) {
                if (!ve.isDisplayable()) continue;
                ve.onClick(mouseX, mouseY, x + 10.0f, startY, width - 20.0f);
                startY += ve.getValueHeight();
            }
        }
    }

    public final void handleRelease(int mouseX, int mouseY, float x, float y, float width, float height) {
        if (this.expanded) {
            float startY = y + height;
            for (ValueElement<?> ve : this.valueElements) {
                if (!ve.isDisplayable()) continue;
                ve.onRelease(mouseX, mouseY, x + 10.0f, startY, width - 20.0f);
                startY += ve.getValueHeight();
            }
        }
    }

    public final boolean handleKeyTyped(char typed, int code) {
        if (this.listeningToKey) {
            if (code == 1) {
                this.module.setKeyBind(0);
                this.listeningToKey = false;
                ClickGui.Companion.getInstance().setCant(false);
            } else {
                this.module.setKeyBind(code);
                this.listeningToKey = false;
                ClickGui.Companion.getInstance().setCant(false);
            }
            return true;
        }
        if (this.expanded) {
            for (ValueElement<?> ve : this.valueElements) {
                if (!ve.isDisplayable() || !ve.onKeyPress(typed, code)) continue;
                return true;
            }
        }
        return false;
    }

    public final boolean listeningKeybind() {
        return this.listeningToKey;
    }

    public final void resetState() {
        this.listeningToKey = false;
        ClickGui.Companion.getInstance().setCant(false);
    }
}

