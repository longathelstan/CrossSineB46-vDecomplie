/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.gui.ClickGUIModule;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\nR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000e\u00a8\u0006\u001c"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/components/StateBox;", "", "()V", "name", "", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "radius", "", "getRadius", "()F", "setRadius", "(F)V", "state", "", "getState", "()Z", "setState", "(Z)V", "width", "getWidth", "setWidth", "onDraw", "", "x", "y", "CrossSine"})
public final class StateBox {
    @NotNull
    private String name = "";
    private boolean state;
    private float width;
    private float radius;

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final void setName(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.name = string;
    }

    public final boolean getState() {
        return this.state;
    }

    public final void setState(boolean bl) {
        this.state = bl;
    }

    public final float getWidth() {
        return this.width;
    }

    public final void setWidth(float f) {
        this.width = f;
    }

    public final float getRadius() {
        return this.radius;
    }

    public final void setRadius(float f) {
        this.radius = f;
    }

    public final void onDraw(float x, float y) {
        float f = (Boolean)ClickGUIModule.fastRenderValue.get() != false ? x + (this.state ? (float)Fonts.font40SemiBold.func_78256_a(this.name) + 13.0f : 7.0f) : (this.width = AnimationUtils.animate(x + (this.state ? (float)Fonts.font40SemiBold.func_78256_a(this.name) + 13.0f : 7.0f), this.width, 0.55f * (float)RenderUtils.deltaTime * 0.025f));
        this.radius = ((Boolean)ClickGUIModule.fastRenderValue.get()).booleanValue() ? (this.state ? 4.0f : 0.0f) : AnimationUtils.animate(this.state ? 4.0f : 0.0f, this.radius, 0.1f * (float)RenderUtils.deltaTime * 0.025f);
        RenderUtils.drawBloomRoundedRect(x + 7.0f, y + 6.0f, this.width, y + 6.0f + (float)Fonts.font40SemiBold.field_78288_b, this.radius, 1.5f, ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 0, 150, false, 4, null), RenderUtils.ShaderBloom.BOTH);
    }
}

