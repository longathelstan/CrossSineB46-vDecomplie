/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.components;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J6\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/components/ToggleSwitch;", "", "()V", "smooth", "", "state", "", "getState", "()Z", "setState", "(Z)V", "onDraw", "", "x", "y", "width", "height", "bgColor", "Ljava/awt/Color;", "accentColor", "CrossSine"})
public final class ToggleSwitch {
    private float smooth;
    private boolean state;

    public final boolean getState() {
        return this.state;
    }

    public final void setState(boolean bl) {
        this.state = bl;
    }

    public final void onDraw(float x, float y, float width, float height, @NotNull Color bgColor, @NotNull Color accentColor) {
        Intrinsics.checkNotNullParameter(bgColor, "bgColor");
        Intrinsics.checkNotNullParameter(accentColor, "accentColor");
        this.smooth = AnimHelperKt.animLinear(this.smooth, (this.state ? 0.2f : -0.2f) * (float)RenderUtils.deltaTime * 0.045f, 0.0f, 1.0f);
        Object[] objectArray = new float[]{0.0f, 1.0f};
        float[] fArray = objectArray;
        objectArray = new Color[2];
        objectArray[0] = (float)new Color(160, 160, 160);
        objectArray[1] = (float)accentColor;
        Color borderColor = BlendUtils.blendColors(fArray, (Color[])objectArray, this.smooth);
        Object[] objectArray2 = new float[]{0.0f, 1.0f};
        float[] fArray2 = objectArray2;
        objectArray2 = new Color[2];
        objectArray2[0] = (float)bgColor;
        objectArray2[1] = (float)accentColor;
        Color mainColor = BlendUtils.blendColors(fArray2, (Color[])objectArray2, this.smooth);
        Object[] objectArray3 = new float[]{0.0f, 1.0f};
        float[] fArray3 = objectArray3;
        objectArray3 = new Color[2];
        objectArray3[0] = (float)new Color(160, 160, 160);
        objectArray3[1] = (float)bgColor;
        Color switchColor = BlendUtils.blendColors(fArray3, (Color[])objectArray3, this.smooth);
        RenderUtils.drawRoundedRect(x - 0.5f, y - 0.5f, x + width + 0.5f, y + height + 0.5f, (height + 1.0f) / 2.0f, borderColor.getRGB());
        RenderUtils.drawRoundedRect(x, y, x + width, y + height, height / 2.0f, mainColor.getRGB());
        RenderUtils.drawFilledCircle(x + (1.0f - this.smooth) * (2.0f + (height - 4.0f) / 2.0f) + this.smooth * (width - 2.0f - (height - 4.0f) / 2.0f), y + 2.0f + (height - 4.0f) / 2.0f, (height - 4.0f) / 2.0f, switchColor);
    }
}

