/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui;

import java.awt.Color;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.ParticleUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0005H\u0016J \u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0012H\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R*\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\bj\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0005`\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R*\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\bj\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0005`\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R*\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\bj\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0005`\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R*\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\bj\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0005`\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/GuiMainMenu;", "Lnet/minecraft/client/gui/GuiScreen;", "Lnet/minecraft/client/gui/GuiYesNoCallback;", "()V", "currentX", "", "currentY", "hoverAnimMap", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "hoverTextAlphaMap", "hoverYOffsetMap", "radiusAnimMap", "time", "drawScreen", "", "mouseX", "", "mouseY", "partialTicks", "mouseClicked", "mouseButton", "CrossSine"})
public final class GuiMainMenu
extends GuiScreen
implements GuiYesNoCallback {
    @NotNull
    private final HashMap<String, Float> hoverAnimMap = new HashMap();
    @NotNull
    private final HashMap<String, Float> radiusAnimMap = new HashMap();
    @NotNull
    private final HashMap<String, Float> hoverTextAlphaMap = new HashMap();
    @NotNull
    private final HashMap<String, Float> hoverYOffsetMap = new HashMap();
    private float time;
    private float currentX;
    private float currentY;

    /*
     * Unable to fully structure code
     */
    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        this.time += partialTicks;
        h = this.field_146295_m;
        w = this.field_146294_l;
        xDiff = ((float)(mouseX - h / 2) - this.currentX) / (float)new ScaledResolution(this.field_146297_k).func_78325_e();
        yDiff = ((float)(mouseY - w / 2) - this.currentY) / (float)new ScaledResolution(this.field_146297_k).func_78325_e();
        this.currentX += xDiff * 0.3f;
        this.currentY += yDiff * 0.3f;
        GlStateManager.func_179109_b((float)(this.currentX / 30.0f), (float)(this.currentY / 15.0f), (float)0.0f);
        RenderUtils.drawImage(new ResourceLocation("crosssine/background.png"), -30, -30, this.field_146294_l + 60, this.field_146295_m + 60);
        GlStateManager.func_179109_b((float)(-this.currentX / 30.0f), (float)(-this.currentY / 15.0f), (float)0.0f);
        ParticleUtils.INSTANCE.drawParticles(mouseX, mouseY);
        Fonts.font40SemiBold.drawString("by shxp3", 2.0f, (float)(this.field_146295_m - Fonts.font40SemiBold.getHeight()) - 1.0f, Color.white.getRGB());
        var9_8 = new String[]{"singleplayer", "multiplayer", "settings", "altmanager", "quit"};
        buttons = var9_8;
        iconWidth = 25;
        iconHeight = 25;
        var11_12 = 0;
        var12_13 = buttons.length;
        while (var11_12 < var12_13) {
            index = var11_12;
            name = buttons[var11_12];
            ++var11_12;
            iconX = this.field_146294_l / 5 * index + this.field_146294_l / 7 / 2;
            iconY = this.field_146295_m / 2;
            v0 = iconX <= mouseX ? mouseX <= iconX + iconWidth : false;
            if (!v0) ** GOTO lbl-1000
            v1 = iconY <= mouseY ? mouseY <= iconY + iconHeight : false;
            if (v1) {
                v2 = true;
            } else lbl-1000:
            // 2 sources

            {
                v2 = false;
            }
            isHovered = v2;
            animationSpeed = 0.3f;
            var20_22 = this.hoverAnimMap.getOrDefault(name, Float.valueOf(0.0f));
            Intrinsics.checkNotNullExpressionValue(var20_22, "hoverAnimMap.getOrDefault(name, 0f)");
            prevAnim = ((Number)var20_22).floatValue();
            targetLength = isHovered != false ? (float)iconWidth + 4.0f : 0.0f;
            updatedAnim = prevAnim + (targetLength - prevAnim) * animationSpeed;
            var22_25 = this.hoverAnimMap;
            var23_27 = Float.valueOf(updatedAnim);
            var22_25.put(name, var23_27);
            var23_27 = this.radiusAnimMap.getOrDefault(name, Float.valueOf(0.0f));
            Intrinsics.checkNotNullExpressionValue(var23_27, "radiusAnimMap.getOrDefault(name, 0f)");
            prevRadius = ((Number)var23_27).floatValue();
            targetRadius = isHovered != false ? 1.0f : 0.0f;
            updatedRadius = prevRadius + (targetRadius - prevRadius) * animationSpeed;
            var25_30 = this.radiusAnimMap;
            var26_32 = Float.valueOf(updatedRadius);
            var25_30.put(name, var26_32);
            var26_32 = this.hoverTextAlphaMap.getOrDefault(name, Float.valueOf(0.0f));
            Intrinsics.checkNotNullExpressionValue(var26_32, "hoverTextAlphaMap.getOrDefault(name, 0f)");
            prevAlpha = ((Number)var26_32).floatValue();
            targetAlpha = isHovered != false ? 255.0f : 0.0f;
            updatedAlpha = prevAlpha + (targetAlpha - prevAlpha) * 0.2f;
            var28_35 = this.hoverTextAlphaMap;
            var29_37 = Float.valueOf(updatedAlpha);
            var28_35.put(name, var29_37);
            var29_37 = this.hoverYOffsetMap.getOrDefault(name, Float.valueOf(0.0f));
            Intrinsics.checkNotNullExpressionValue(var29_37, "hoverYOffsetMap.getOrDefault(name, 0f)");
            currentOffset = ((Number)var29_37).floatValue();
            targetOffset = isHovered != false ? (float)Math.sin(((double)this.time * 0.05 + (double)index) * 3.141592653589793) * 4.0f : 0.0f;
            updatedOffset = currentOffset + (targetOffset - currentOffset) * 0.08f;
            var31_39 = this.hoverYOffsetMap;
            var32_42 = Float.valueOf(updatedOffset);
            var31_39.put(name, var32_42);
            if (updatedAnim > 0.5f) {
                animX = (float)iconX + (float)iconWidth / 2.0f - updatedAnim / 2.0f;
                barY1 = (float)(iconY + iconHeight) + 2.0f;
                barY2 = barY1 + 2.0f;
                RenderUtils.drawRoundedRect(animX, barY1, animX + updatedAnim, barY2, updatedRadius, new Color(255, 255, 255).getRGB());
            }
            RenderUtils.drawImage(new ResourceLocation("crosssine/ui/icons/" + name + ".png"), iconX - (Intrinsics.areEqual(name, "multiplayer") != false ? 5 : 0), (int)((float)iconY + updatedOffset - (float)(Intrinsics.areEqual(name, "multiplayer") != false ? 5 : 0)), iconWidth + (Intrinsics.areEqual(name, "multiplayer") != false ? 10 : 0), iconHeight + (Intrinsics.areEqual(name, "multiplayer") != false ? 10 : 0));
            if (!(updatedAlpha > 10.0f)) continue;
            textAlpha = (int)RangesKt.coerceIn(updatedAlpha, 0.0f, 255.0f);
            textColor = textAlpha << 24 | 0xFFFFFF;
            var34_47 = name;
            if (((CharSequence)var34_47).length() > 0) {
                it = var34_47.charAt(0);
                $i$a$-replaceFirstCharWithChar-GuiMainMenu$drawScreen$text$1 = false;
                var35_48 = Character.toUpperCase(it);
                var36_49 = var34_47;
                var37_51 = 1;
                var38_52 = var36_49.substring(var37_51);
                Intrinsics.checkNotNullExpressionValue(var38_52, "this as java.lang.String).substring(startIndex)");
                var36_49 = var38_52;
                v3 = var35_48 + var36_49;
            } else {
                v3 = var34_47;
            }
            text = v3;
            Fonts.font40SemiBold.func_175065_a(text, iconX + iconWidth / 2 - Fonts.font40SemiBold.func_78256_a(text) / 2, (float)(iconY - 10) + updatedOffset, textColor, true);
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        String name;
        boolean bl;
        boolean isHovered;
        String[] stringArray = new String[]{"singleplayer", "multiplayer", "settings", "altmanager", "quit"};
        String[] buttons = stringArray;
        int iconWidth = 25;
        int iconHeight = 25;
        int n = 0;
        int n2 = buttons.length;
        do {
            if (n >= n2) {
                super.func_73864_a(mouseX, mouseY, mouseButton);
                return;
            }
            int index2 = n;
            name = buttons[n];
            ++n;
            int iconX = this.field_146294_l / 5 * index2 + this.field_146294_l / 7 / 2;
            int iconY = this.field_146295_m / 2;
            boolean bl2 = iconX <= mouseX ? mouseX <= iconX + iconWidth : false;
            if (bl2) {
                boolean bl3 = iconY <= mouseY ? mouseY <= iconY + iconHeight : false;
                if (bl3) {
                    bl = true;
                    continue;
                }
            }
            bl = false;
        } while (!(isHovered = bl));
        switch (name) {
            case "singleplayer": {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiSelectWorld((GuiScreen)this));
                return;
            }
            case "multiplayer": {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiMultiplayer((GuiScreen)this));
                return;
            }
            case "settings": {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiOptions((GuiScreen)this, this.field_146297_k.field_71474_y));
                return;
            }
            case "altmanager": {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiAltManager(this));
                return;
            }
            case "quit": {
                this.field_146297_k.func_71400_g();
                return;
            }
        }
    }
}

