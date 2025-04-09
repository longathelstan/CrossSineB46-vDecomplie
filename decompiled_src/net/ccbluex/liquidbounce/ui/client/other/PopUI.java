/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.other;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.ui.client.gui.ClickGUIModule;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\f\n\u0002\b\u000b\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\fH\u0016J\b\u0010\u001d\u001a\u00020\u001aH\u0016J\u0018\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0012H\u0016J&\u0010\"\u001a\u00020\u001a2\u0006\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u0012J\u0016\u0010%\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0012J\u0016\u0010&\u001a\u00020\u001a2\u0006\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u0012J.\u0010'\u001a\u00020\u001a2\u0006\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010(\u001a\u00020\u0012J\b\u0010)\u001a\u00020\u001aH\u0016J \u0010*\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\f2\u0006\u0010(\u001a\u00020\u0012H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u0012X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u0012X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006+"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/other/PopUI;", "", "title", "", "(Ljava/lang/String;)V", "animatingOut", "", "getAnimatingOut", "()Z", "setAnimatingOut", "(Z)V", "animationProgress", "", "getAnimationProgress", "()F", "setAnimationProgress", "(F)V", "baseHeight", "", "getBaseHeight", "()I", "baseWidth", "getBaseWidth", "getTitle", "()Ljava/lang/String;", "click", "", "mouseX", "mouseY", "close", "key", "typedChar", "", "keyCode", "onClick", "width", "height", "onKey", "onRender", "onStroll", "wheel", "render", "scroll", "CrossSine"})
public class PopUI {
    @NotNull
    private final String title;
    private final int baseWidth;
    private final int baseHeight;
    private float animationProgress;
    private boolean animatingOut;

    public PopUI(@NotNull String title2) {
        Intrinsics.checkNotNullParameter(title2, "title");
        this.title = title2;
        this.baseWidth = 150;
        this.baseHeight = 210;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public final int getBaseWidth() {
        return this.baseWidth;
    }

    public final int getBaseHeight() {
        return this.baseHeight;
    }

    public final float getAnimationProgress() {
        return this.animationProgress;
    }

    public final void setAnimationProgress(float f) {
        this.animationProgress = f;
    }

    public final boolean getAnimatingOut() {
        return this.animatingOut;
    }

    public final void setAnimatingOut(boolean bl) {
        this.animatingOut = bl;
    }

    public final void onRender(int width, int height) {
        GL11.glPushMatrix();
        RenderUtils.drawRect(0.0f, 0.0f, (float)width, (float)height, new Color(0, 0, 0, 50).getRGB());
        float scaleFactor = (float)EaseUtils.easeOutBack(this.animationProgress);
        float centerX = (float)width / 2.0f;
        float centerY = (float)height / 2.0f;
        GL11.glTranslatef((float)centerX, (float)centerY, (float)0.0f);
        GL11.glScalef((float)scaleFactor, (float)scaleFactor, (float)1.0f);
        GL11.glTranslatef((float)((float)(-this.baseWidth) / 2.0f), (float)((float)(-this.baseHeight) / 2.0f), (float)0.0f);
        RenderUtils.drawRect(0.0f, 0.0f, (float)this.baseWidth, (float)this.baseHeight, Color.WHITE.getRGB());
        Fonts.SFApple40.drawString(this.title, 8.0f, 8.0f, Color.DARK_GRAY.getRGB());
        this.render();
        GL11.glPopMatrix();
        this.animationProgress += (Boolean)ClickGUIModule.fastRenderValue.get() != false ? 1.0f : 0.001875f * (float)RenderUtils.deltaTime * (this.animatingOut ? -1.0f : 1.0f);
        this.animationProgress = RangesKt.coerceIn(this.animationProgress, 0.0f, 1.0f);
    }

    public final void onClick(int width, int height, int mouseX, int mouseY) {
        float scale = (float)width * 0.2f / (float)this.baseWidth;
        float scaledMouseX = ((float)mouseX - (float)width * 0.4f) / scale;
        float scaledMouseY = ((float)mouseY - (float)height * 0.3f) / scale;
        if (scaledMouseX > 0.0f && scaledMouseY > 0.0f && scaledMouseX < (float)this.baseWidth && scaledMouseY < (float)this.baseHeight) {
            this.click(scaledMouseX, scaledMouseY);
        } else {
            this.close();
        }
    }

    public final void onStroll(int width, int height, int mouseX, int mouseY, int wheel) {
        float scale = (float)width * 0.2f / (float)this.baseWidth;
        float scaledMouseX = ((float)mouseX - (float)width * 0.4f) / scale;
        float scaledMouseY = ((float)mouseY - (float)height * 0.3f) / scale;
        if (scaledMouseX > 0.0f && scaledMouseY > 0.0f && scaledMouseX < (float)this.baseWidth && scaledMouseY < (float)this.baseHeight) {
            this.scroll(scaledMouseX, scaledMouseY, wheel);
        }
    }

    public final void onKey(char typedChar, int keyCode) {
        this.key(typedChar, keyCode);
    }

    public void render() {
    }

    public void key(char typedChar, int keyCode) {
    }

    public void close() {
    }

    public void click(float mouseX, float mouseY) {
    }

    public void scroll(float mouseX, float mouseY, int wheel) {
    }
}

