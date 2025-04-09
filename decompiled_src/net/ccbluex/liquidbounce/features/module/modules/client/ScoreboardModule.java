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
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

@ModuleInfo(name="Scoreboard", category=ModuleCategory.CLIENT, defaultOn=true, loadConfig=false)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u001e\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010/\u001a\u000200J\u0010\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\n\"\u0004\b\u0019\u0010\fR\u001a\u0010\u001a\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\n\"\u0004\b\u001c\u0010\fR\u0011\u0010\u001d\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0006R\u0011\u0010\u001f\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0006R\u001a\u0010#\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\n\"\u0004\b%\u0010\fR\u001a\u0010&\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\n\"\u0004\b(\u0010\fR\u001a\u0010)\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\n\"\u0004\b+\u0010\fR\u001a\u0010,\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\n\"\u0004\b.\u0010\f\u00a8\u00065"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/client/ScoreboardModule;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "changedDomain", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getChangedDomain", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "dragOffsetX", "", "getDragOffsetX", "()F", "setDragOffsetX", "(F)V", "dragOffsetY", "getDragOffsetY", "setDragOffsetY", "draging", "", "getDraging", "()Z", "setDraging", "(Z)V", "outlineProgress", "posX", "getPosX", "setPosX", "posY", "getPosY", "setPosY", "roundMode", "getRoundMode", "showNumber", "getShowNumber", "textShadow", "getTextShadow", "ux2_size", "getUx2_size", "setUx2_size", "ux_size", "getUx_size", "setUx_size", "uy2_size", "getUy2_size", "setUy2_size", "uy_size", "getUy_size", "setUy_size", "getAlpha", "", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"})
public final class ScoreboardModule
extends Module {
    @NotNull
    public static final ScoreboardModule INSTANCE = new ScoreboardModule();
    @NotNull
    private static final BoolValue textShadow = new BoolValue("Text-Shadow", false);
    @NotNull
    private static final BoolValue showNumber = new BoolValue("Show-Number", true);
    @NotNull
    private static final BoolValue changedDomain = new BoolValue("Changed-Domain", false);
    @NotNull
    private static final BoolValue roundMode = new BoolValue("RoundedMode", true);
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

    private ScoreboardModule() {
    }

    @NotNull
    public final BoolValue getTextShadow() {
        return textShadow;
    }

    @NotNull
    public final BoolValue getShowNumber() {
        return showNumber;
    }

    @NotNull
    public final BoolValue getChangedDomain() {
        return changedDomain;
    }

    @NotNull
    public final BoolValue getRoundMode() {
        return roundMode;
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
        int mouseX = MinecraftInstance.mc.field_71462_r == null ? 0 : Mouse.getX() * MinecraftInstance.mc.field_71462_r.field_146294_l / MinecraftInstance.mc.field_71443_c;
        int n = mouseY = MinecraftInstance.mc.field_71462_r == null ? 0 : MinecraftInstance.mc.field_71462_r.field_146295_m - Mouse.getY() * MinecraftInstance.mc.field_71462_r.field_146295_m / MinecraftInstance.mc.field_71440_d - 1;
        if (draging) {
            posX = (float)mouseX - dragOffsetX;
            posY = (float)mouseY - dragOffsetY;
            if (!Mouse.isButtonDown((int)0) || MinecraftInstance.mc.field_71462_r == null) {
                draging = false;
            }
        }
        outlineProgress += 0.00375f * (float)RenderUtils.deltaTime * (MinecraftInstance.mc.field_71462_r instanceof GuiChat && MouseUtils.mouseWithinBounds(mouseX, mouseY, ux_size, uy_size, ux2_size, uy2_size) ? 1.0f : -1.0f);
        outlineProgress = RangesKt.coerceIn(outlineProgress, 0.0f, 1.0f);
        GlStateManager.func_179094_E();
        if (outlineProgress > 0.0f) {
            RenderUtils.drawRoundedOutline(ux_size, uy_size, ux2_size, uy2_size, 7.0f, 2.5f, new Color(255, 255, 255, (int)((float)255 * outlineProgress)).getRGB());
        }
        GlStateManager.func_179121_F();
        GlStateManager.func_179117_G();
    }

    public final int getAlpha() {
        return 150;
    }

    static {
        posX = -5.0f;
    }
}

