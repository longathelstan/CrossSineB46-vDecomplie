/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.client;

import java.awt.Color;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.features.module.modules.client.KeyStrokes;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker;
import net.ccbluex.liquidbounce.features.module.modules.combat.BlockHit;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.RightClicker;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.render.BlurUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

@ModuleInfo(name="KeyStrokes", category=ModuleCategory.CLIENT, loadConfig=false)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0004\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010@\u001a\u00020\u00112\u0006\u0010A\u001a\u00020\u0011H\u0002J\u0010\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020EH\u0007JP\u0010F\u001a\u00020C2\u0006\u0010G\u001a\u00020\u001c2\u0006\u0010H\u001a\u00020\b2\u0006\u0010I\u001a\u00020\b2\u0006\u0010\"\u001a\u00020\b2\u0006\u0010%\u001a\u00020\b2\u0006\u0010J\u001a\u00020\b2\u0006\u0010K\u001a\u00020\b2\u0006\u0010L\u001a\u00020\b2\u0006\u0010M\u001a\u00020NH\u0002J\u0010\u0010O\u001a\u00020\u00112\u0006\u0010A\u001a\u00020\u0011H\u0002J\u0018\u0010P\u001a\u00020C2\u0006\u0010A\u001a\u00020\u001c2\u0006\u0010Q\u001a\u00020\u0011H\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\b0\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00110\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\"\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\n\"\u0004\b$\u0010\fR\u001a\u0010%\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\n\"\u0004\b'\u0010\fR\u0011\u0010(\u001a\u00020)\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00110\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010 R\u0011\u0010.\u001a\u00020\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u0019R\u0011\u00100\u001a\u00020\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\u0019R\u0011\u00102\u001a\u00020)\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010+R\u001a\u00104\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\n\"\u0004\b6\u0010\fR\u001a\u00107\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b8\u0010\n\"\u0004\b9\u0010\fR\u001a\u0010:\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u0010\n\"\u0004\b<\u0010\fR\u001a\u0010=\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b>\u0010\n\"\u0004\b?\u0010\f\u00a8\u0006R"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/client/KeyStrokes;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "backgroundAlpha", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "getBackgroundAlpha", "()Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "dragOffsetX", "", "getDragOffsetX", "()F", "setDragOffsetX", "(F)V", "dragOffsetY", "getDragOffsetY", "setDragOffsetY", "draging", "", "getDraging", "()Z", "setDraging", "(Z)V", "keyColor", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getKeyColor", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "keyStates", "", "", "lineSpace", "Lnet/ccbluex/liquidbounce/features/value/Value;", "getLineSpace", "()Lnet/ccbluex/liquidbounce/features/value/Value;", "outlineProgress", "posX", "getPosX", "setPosX", "posY", "getPosY", "setPosY", "roundValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "getRoundValue", "()Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "showCPS", "getShowCPS", "showMouse", "getShowMouse", "showSpace", "getShowSpace", "sizeBox", "getSizeBox", "ux2_size", "getUx2_size", "setUx2_size", "ux_size", "getUx_size", "setUx_size", "uy2_size", "getUy2_size", "setUy2_size", "uy_size", "getUy_size", "setUy_size", "leftClick", "key", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "renderKey", "keyString", "textPosX", "textPosY", "size", "size2", "keyTick", "index", "", "rightClick", "updateKeyState", "isPressed", "CrossSine"})
public final class KeyStrokes
extends Module {
    @NotNull
    public static final KeyStrokes INSTANCE = new KeyStrokes();
    @NotNull
    private static final BoolValue keyColor = new BoolValue("Key Rainbow Color", false);
    @NotNull
    private static final BoolValue showMouse = new BoolValue("Show Mouse", false);
    @NotNull
    private static final Value<Boolean> showCPS = new BoolValue("Show CPS", false).displayable(showCPS.1.INSTANCE);
    @NotNull
    private static final BoolValue showSpace = new BoolValue("Show Space", true);
    @NotNull
    private static final Value<Boolean> lineSpace = new BoolValue("Line Space", false).displayable(lineSpace.1.INSTANCE);
    @NotNull
    private static final FloatValue roundValue = new FloatValue("Rounded Size", 6.0f, 0.0f, 6.0f);
    @NotNull
    private static final IntegerValue backgroundAlpha = new IntegerValue("Background Alpha", 180, 0, 255);
    @NotNull
    private static final FloatValue sizeBox = new FloatValue("Size Box", 1.0f, 0.5f, 1.0f);
    @NotNull
    private static final Map<String, Float> keyStates;
    private static boolean draging;
    private static float posX;
    private static float posY;
    private static float dragOffsetX;
    private static float dragOffsetY;
    private static float ux_size;
    private static float uy_size;
    private static float ux2_size;
    private static float uy2_size;
    private static float outlineProgress;

    private KeyStrokes() {
    }

    @NotNull
    public final BoolValue getKeyColor() {
        return keyColor;
    }

    @NotNull
    public final BoolValue getShowMouse() {
        return showMouse;
    }

    @NotNull
    public final Value<Boolean> getShowCPS() {
        return showCPS;
    }

    @NotNull
    public final BoolValue getShowSpace() {
        return showSpace;
    }

    @NotNull
    public final Value<Boolean> getLineSpace() {
        return lineSpace;
    }

    @NotNull
    public final FloatValue getRoundValue() {
        return roundValue;
    }

    @NotNull
    public final IntegerValue getBackgroundAlpha() {
        return backgroundAlpha;
    }

    @NotNull
    public final FloatValue getSizeBox() {
        return sizeBox;
    }

    public final boolean getDraging() {
        return draging;
    }

    public final void setDraging(boolean bl) {
        draging = bl;
    }

    public final float getPosX() {
        return posX;
    }

    public final void setPosX(float f) {
        posX = f;
    }

    public final float getPosY() {
        return posY;
    }

    public final void setPosY(float f) {
        posY = f;
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
        this.updateKeyState("w", MinecraftInstance.mc.field_71474_y.field_74351_w.func_151470_d());
        this.updateKeyState("a", MinecraftInstance.mc.field_71474_y.field_74370_x.func_151470_d());
        this.updateKeyState("s", MinecraftInstance.mc.field_71474_y.field_74368_y.func_151470_d());
        this.updateKeyState("d", MinecraftInstance.mc.field_71474_y.field_74366_z.func_151470_d());
        this.updateKeyState("space", MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d());
        this.updateKeyState("lmb", this.leftClick(MinecraftInstance.mc.field_71474_y.field_74312_F.func_151470_d()));
        this.updateKeyState("rmb", this.rightClick(MinecraftInstance.mc.field_71474_y.field_74313_G.func_151470_d()));
        Float f = keyStates.get("w");
        Intrinsics.checkNotNull(f);
        this.renderKey("W", 16.5f, 13.0f, 33.0f, 0.0f, 65.0f, 32.0f, ((Number)f).floatValue(), 90);
        Float f2 = keyStates.get("a");
        Intrinsics.checkNotNull(f2);
        this.renderKey("A", 16.5f, 13.0f, 0.0f, 33.0f, 32.0f, 65.0f, ((Number)f2).floatValue(), 0);
        Float f3 = keyStates.get("s");
        Intrinsics.checkNotNull(f3);
        this.renderKey("S", 16.5f, 13.0f, 33.0f, 33.0f, 65.0f, 65.0f, ((Number)f3).floatValue(), 90);
        Float f4 = keyStates.get("d");
        Intrinsics.checkNotNull(f4);
        this.renderKey("D", 16.5f, 13.0f, 66.0f, 33.0f, 98.0f, 65.0f, ((Number)f4).floatValue(), 180);
        outlineProgress += 0.00375f * (float)RenderUtils.deltaTime * (MinecraftInstance.mc.field_71462_r instanceof GuiChat && MouseUtils.mouseWithinBounds(Mouse.getX() * MinecraftInstance.mc.field_71462_r.field_146294_l / MinecraftInstance.mc.field_71443_c, MinecraftInstance.mc.field_71462_r.field_146295_m - Mouse.getY() * MinecraftInstance.mc.field_71462_r.field_146295_m / MinecraftInstance.mc.field_71440_d - 1, ux_size, uy_size, ux2_size, uy2_size) ? 1.0f : -1.0f);
        outlineProgress = RangesKt.coerceIn(outlineProgress, 0.0f, 1.0f);
        float baseY = 66.0f;
        if (((Boolean)showMouse.get()).booleanValue()) {
            String string = CPSCounter.getCPS(CPSCounter.MouseButton.LEFT) == 0 || showCPS.get() == false ? "LMB" : String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.LEFT));
            Float f5 = keyStates.get("lmb");
            Intrinsics.checkNotNull(f5);
            this.renderKey(string, 25.0f, 13.0f, 0.0f, baseY, 48.0f, baseY + 32.0f, ((Number)f5).floatValue(), 0);
            String string2 = CPSCounter.getCPS(CPSCounter.MouseButton.RIGHT) == 0 || showCPS.get() == false ? "RMB" : String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.RIGHT));
            Float f6 = keyStates.get("rmb");
            Intrinsics.checkNotNull(f6);
            this.renderKey(string2, 25.0f, 13.0f, 49.0f, baseY, 98.0f, baseY + 32.0f, ((Number)f6).floatValue(), 180);
        }
        if (((Boolean)showSpace.get()).booleanValue()) {
            float spaceY = baseY + ((Boolean)showMouse.get() != false ? 33.0f : 0.0f);
            String string = lineSpace.get() != false ? "-" : "SPACE";
            Float f7 = keyStates.get("space");
            Intrinsics.checkNotNull(f7);
            this.renderKey(string, 49.0f, 4.175f, 0.0f, spaceY, 98.0f, spaceY + 19.0f, ((Number)f7).floatValue(), 90);
        }
        float result = (Boolean)showMouse.get() != false && (Boolean)showSpace.get() != false ? 118.0f : ((Boolean)showMouse.get() != false ? 98.0f : ((Boolean)showSpace.get() != false ? 85.0f : 65.0f));
        GlStateManager.func_179094_E();
        RenderUtils.drawRoundedOutline(ux_size, uy_size, ux2_size, uy2_size, 7.0f, 2.5f, new Color(255, 255, 255, (int)((float)255 * outlineProgress)).getRGB());
        GlStateManager.func_179117_G();
        GlStateManager.func_179121_F();
        ux_size = posX;
        uy_size = posY;
        ux2_size = posX + 98.0f * ((Number)sizeBox.get()).floatValue();
        uy2_size = posY + result * ((Number)sizeBox.get()).floatValue();
        int mouseX = MinecraftInstance.mc.field_71462_r == null ? 0 : Mouse.getX() * MinecraftInstance.mc.field_71462_r.field_146294_l / MinecraftInstance.mc.field_71443_c;
        int n = mouseY = MinecraftInstance.mc.field_71462_r == null ? 0 : MinecraftInstance.mc.field_71462_r.field_146295_m - Mouse.getY() * MinecraftInstance.mc.field_71462_r.field_146295_m / MinecraftInstance.mc.field_71440_d - 1;
        if (draging) {
            posX = (float)mouseX - dragOffsetX;
            posY = (float)mouseY - dragOffsetY;
            if (!Mouse.isButtonDown((int)0) || MinecraftInstance.mc.field_71462_r == null) {
                draging = false;
            }
        }
    }

    private final void updateKeyState(String key, boolean isPressed) {
        Map<String, Float> map = keyStates;
        Float f = keyStates.get(key);
        Intrinsics.checkNotNull(f);
        Float f2 = Float.valueOf(((Number)f).floatValue() + 0.03f * (float)RenderUtils.deltaTime * (isPressed ? 1.0f : -1.0f));
        map.put(key, f2);
        map = keyStates;
        Float f3 = keyStates.get(key);
        Intrinsics.checkNotNull(f3);
        f2 = Float.valueOf(RangesKt.coerceIn(((Number)f3).floatValue(), 0.0f, 1.0f));
        map.put(key, f2);
    }

    private final boolean leftClick(boolean key) {
        return MinecraftInstance.mc.field_71462_r != null ? false : (KillAura.INSTANCE.getState() && KillAura.INSTANCE.getCurrentTarget() != null || SilentAura.INSTANCE.getState() && SilentAura.INSTANCE.getTarget() != null || AutoClicker.INSTANCE.getState() && AutoClicker.INSTANCE.getCanLeftClick() ? MouseUtils.INSTANCE.getLeftClicked() : key);
    }

    private final boolean rightClick(boolean key) {
        return MinecraftInstance.mc.field_71462_r != null ? false : (BlockHit.INSTANCE.getState() || Scaffold.INSTANCE.getState() || RightClicker.INSTANCE.getState() && RightClicker.INSTANCE.getCanRightClick() ? MouseUtils.INSTANCE.getRightClicked() : key);
    }

    private final void renderKey(String keyString, float textPosX, float textPosY, float posX, float posY, float size, float size2, float keyTick, int index2) {
        float adjustedPosX = posX * ((Number)sizeBox.get()).floatValue();
        float adjustedPosY = posY * ((Number)sizeBox.get()).floatValue();
        float adjustedSizeX = size * ((Number)sizeBox.get()).floatValue();
        float adjustedSizeY = size2 * ((Number)sizeBox.get()).floatValue();
        float adjustedTextPosX = textPosX * ((Number)sizeBox.get()).floatValue();
        float adjustedTextPosY = textPosY * ((Number)sizeBox.get()).floatValue();
        int color = (int)((float)255 * RangesKt.coerceIn(keyTick, 0.1f, 1.0f));
        int rectColor = new Color(color, color, color, ((Number)backgroundAlpha.get()).intValue()).getRGB();
        int textColor = (Boolean)keyColor.get() != false ? new Color(RangesKt.coerceIn(ClientTheme.getColor$default(ClientTheme.INSTANCE, index2, false, 2, null).getRed() - color, 0, 255), RangesKt.coerceIn(ClientTheme.getColor$default(ClientTheme.INSTANCE, index2, false, 2, null).getGreen() - color, 0, 255), RangesKt.coerceIn(ClientTheme.getColor$default(ClientTheme.INSTANCE, index2, false, 2, null).getBlue() - color, 0, 255)).getRGB() : new Color(255 - color, 255 - color, 255 - color, 255).getRGB();
        GlStateManager.func_179094_E();
        if (((Boolean)Interface.INSTANCE.getShaderValue().get()).booleanValue()) {
            BlurUtils.blurAreaRounded(adjustedPosX + KeyStrokes.posX, adjustedPosY + KeyStrokes.posY, adjustedSizeX + KeyStrokes.posX, adjustedSizeY + KeyStrokes.posY, ((Number)roundValue.get()).floatValue(), 10.0f);
        }
        RenderUtils.drawRoundedRect(adjustedPosX + KeyStrokes.posX, adjustedPosY + KeyStrokes.posY, adjustedSizeX + KeyStrokes.posX, adjustedSizeY + KeyStrokes.posY, ((Number)roundValue.get()).floatValue(), rectColor);
        Fonts.SFBold40.drawCenteredString(keyString, adjustedPosX + KeyStrokes.posX + adjustedTextPosX, adjustedPosY + KeyStrokes.posY + adjustedTextPosY, textColor, true);
        GlStateManager.func_179121_F();
        GlStateManager.func_179117_G();
    }

    static {
        Pair[] pairArray = new Pair[]{TuplesKt.to("w", Float.valueOf(0.0f)), TuplesKt.to("a", Float.valueOf(0.0f)), TuplesKt.to("s", Float.valueOf(0.0f)), TuplesKt.to("d", Float.valueOf(0.0f)), TuplesKt.to("space", Float.valueOf(0.0f)), TuplesKt.to("lmb", Float.valueOf(0.0f)), TuplesKt.to("rmb", Float.valueOf(0.0f))};
        keyStates = MapsKt.mutableMapOf(pairArray);
    }
}

