/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.misc.YouTubeThumbnailRenderer;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J>\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/VideoElement;", "", "()V", "clicked", "", "thumbnail", "Lnet/ccbluex/liquidbounce/utils/misc/YouTubeThumbnailRenderer;", "draw", "", "url", "", "mouseX", "", "mouseY", "x", "", "y", "width", "height", "CrossSine"})
public final class VideoElement {
    @NotNull
    private final YouTubeThumbnailRenderer thumbnail = new YouTubeThumbnailRenderer();
    private boolean clicked;

    public final void draw(@NotNull String url, int mouseX, int mouseY, float x, float y, int width, int height) {
        Intrinsics.checkNotNullParameter(url, "url");
        RenderUtils.drawBloomRoundedRect(x, y, x + (float)width, y + (float)height, 8.0f, 8.0f, Color.BLACK, RenderUtils.ShaderBloom.BOTH);
        GL11.glPushMatrix();
        Stencil.write(false);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        RenderUtils.fastRoundedRect(x, y, x + (float)width, y + (float)height, 7.0f);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        Stencil.erase(true);
        this.thumbnail.loadThumbnailAsync(url);
        this.thumbnail.drawThumbnail((int)x, (int)y, width, height);
        if (Mouse.isButtonDown((int)0) && !this.clicked) {
            if (MouseUtils.mouseWithinBounds(mouseX, mouseY, x, y, x + (float)width, y + (float)height)) {
                HttpUtils.INSTANCE.openWebpage(Intrinsics.stringPlus("https://www.youtube.com/watch?v=", url));
            }
            this.clicked = true;
        }
        if (!Mouse.isButtonDown((int)0) && this.clicked) {
            this.clicked = false;
        }
        GlStateManager.func_179117_G();
        Stencil.dispose();
        GL11.glPopMatrix();
        RenderUtils.drawRoundedOutline(x, y, x + (float)width, y + (float)height, 15.0f, 4.0f, new Color(50, 50, 50).getRGB());
        RenderUtils.drawRoundedRect(x, y, x + (float)width, y + (float)height, 10.0f, new Color(0, 0, 0, MouseUtils.mouseWithinBounds(mouseX, mouseY, x, y, x + (float)width, y + (float)height) ? 120 : 0).getRGB());
        GlStateManager.func_179117_G();
    }
}

