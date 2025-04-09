/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.client;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.SessionUtils;
import net.ccbluex.liquidbounce.utils.StatisticsUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

@ModuleInfo(name="SessionInfo", category=ModuleCategory.CLIENT, loadConfig=false)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0018\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0006\"\u0004\b\u0015\u0010\bR\u001a\u0010\u0016\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0006\"\u0004\b\u0018\u0010\bR\u001a\u0010\u0019\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0006\"\u0004\b\u001b\u0010\bR\u001a\u0010\u001c\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0006\"\u0004\b\u001e\u0010\bR\u001a\u0010\u001f\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0006\"\u0004\b!\u0010\bR\u001a\u0010\"\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0006\"\u0004\b$\u0010\b\u00a8\u0006)"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/client/SessionInfo;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "dragOffsetX", "", "getDragOffsetX", "()F", "setDragOffsetX", "(F)V", "dragOffsetY", "getDragOffsetY", "setDragOffsetY", "draging", "", "getDraging", "()Z", "setDraging", "(Z)V", "outlineProgress", "posX", "getPosX", "setPosX", "posY", "getPosY", "setPosY", "ux2_size", "getUx2_size", "setUx2_size", "ux_size", "getUx_size", "setUx_size", "uy2_size", "getUy2_size", "setUy2_size", "uy_size", "getUy_size", "setUy_size", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"})
public final class SessionInfo
extends Module {
    @NotNull
    public static final SessionInfo INSTANCE = new SessionInfo();
    private static boolean draging;
    private static float posY;
    private static float posX;
    private static float dragOffsetX;
    private static float dragOffsetY;
    private static float ux_size;
    private static float uy_size;
    private static float ux2_size;
    private static float uy2_size;
    private static float outlineProgress;

    private SessionInfo() {
    }

    public final boolean getDraging() {
        return draging;
    }

    public final void setDraging(boolean bl) {
        draging = bl;
    }

    public final float getPosY() {
        return posY;
    }

    public final void setPosY(float f) {
        posY = f;
    }

    public final float getPosX() {
        return posX;
    }

    public final void setPosX(float f) {
        posX = f;
    }

    public final float getDragOffsetX() {
        return dragOffsetX;
    }

    public final void setDragOffsetX(float f) {
        dragOffsetX = f;
    }

    public final float getDragOffsetY() {
        return dragOffsetY;
    }

    public final void setDragOffsetY(float f) {
        dragOffsetY = f;
    }

    public final float getUx_size() {
        return ux_size;
    }

    public final void setUx_size(float f) {
        ux_size = f;
    }

    public final float getUy_size() {
        return uy_size;
    }

    public final void setUy_size(float f) {
        uy_size = f;
    }

    public final float getUx2_size() {
        return ux2_size;
    }

    public final void setUx2_size(float f) {
        ux2_size = f;
    }

    public final float getUy2_size() {
        return uy2_size;
    }

    public final void setUy2_size(float f) {
        uy2_size = f;
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        int mouseY;
        Intrinsics.checkNotNullParameter(event, "event");
        outlineProgress += 0.00375f * (float)RenderUtils.deltaTime * (MinecraftInstance.mc.field_71462_r instanceof GuiChat && MouseUtils.mouseWithinBounds(Mouse.getX() * MinecraftInstance.mc.field_71462_r.field_146294_l / MinecraftInstance.mc.field_71443_c, MinecraftInstance.mc.field_71462_r.field_146295_m - Mouse.getY() * MinecraftInstance.mc.field_71462_r.field_146295_m / MinecraftInstance.mc.field_71440_d - 1, ux_size, uy_size, ux2_size, uy2_size) ? 1.0f : -1.0f);
        outlineProgress = RangesKt.coerceIn(outlineProgress, 0.0f, 1.0f);
        GlStateManager.func_179094_E();
        RenderUtils.drawBloomRoundedRect(posX, posY, posX + 150.0f, posY + 65.0f, 8.0f, 8.0f, new Color(0, 0, 0, 255), RenderUtils.ShaderBloom.BOTH);
        RenderUtils.customRoundedinf(posX, posY, posX + 150.0f, posY + 20.0f, 8.0f, 8.0f, 0.0f, 0.0f, new Color(40, 40, 40, 255).getRGB());
        RenderUtils.customRoundedinf(posX, posY + 20.0f, posX + 150.0f, posY + 65.0f, 0.0f, 0.0f, 8.0f, 8.0f, new Color(15, 15, 15, 255).getRGB());
        Fonts.SFBold50.drawCenteredString("Session Info", posX + (float)75, posY + (float)5, Color.WHITE.getRGB());
        Fonts.SFBold35.drawString(Intrinsics.stringPlus("Kill: ", StatisticsUtils.getKills()), posX + 5.0f, posY + 27.0f, new Color(100, 100, 100).getRGB());
        Fonts.SFBold35.drawString(Intrinsics.stringPlus("Session Time: ", SessionUtils.getFormatSessionTime()), posX + 5.0f, posY + 40.0f, new Color(100, 100, 100).getRGB());
        Fonts.SFBold35.drawString(Intrinsics.stringPlus("Username: ", MinecraftInstance.mc.field_71439_g.func_70005_c_()), posX + 5.0f, posY + 53.0f, new Color(100, 100, 100).getRGB());
        GlStateManager.func_179117_G();
        GlStateManager.func_179121_F();
        GlStateManager.func_179094_E();
        RenderUtils.drawRoundedOutline(ux_size, uy_size, ux2_size, uy2_size, 7.0f, 2.5f, new Color(255, 255, 255, (int)((float)255 * outlineProgress)).getRGB());
        GlStateManager.func_179117_G();
        GlStateManager.func_179121_F();
        int mouseX = MinecraftInstance.mc.field_71462_r == null ? 0 : Mouse.getX() * MinecraftInstance.mc.field_71462_r.field_146294_l / MinecraftInstance.mc.field_71443_c;
        int n = mouseY = MinecraftInstance.mc.field_71462_r == null ? 0 : MinecraftInstance.mc.field_71462_r.field_146295_m - Mouse.getY() * MinecraftInstance.mc.field_71462_r.field_146295_m / MinecraftInstance.mc.field_71440_d - 1;
        if (draging) {
            posX = (float)mouseX - dragOffsetX;
            posY = (float)mouseY - dragOffsetY;
            if (!Mouse.isButtonDown((int)0) || MinecraftInstance.mc.field_71462_r == null) {
                draging = false;
            }
        }
        ux_size = posX;
        uy_size = posY;
        ux2_size = posX + 150.0f;
        uy2_size = posY + 65.0f;
    }
}

