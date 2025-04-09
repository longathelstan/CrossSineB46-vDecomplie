/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value={GuiButtonExt.class})
public abstract class MixinGuiButtonExt
extends MixinGuiButton {
    @Overwrite
    public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
        if (!this.field_146125_m) {
            return;
        }
        if (this.buttonRenderer != null) {
            this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
            this.buttonRenderer.render(mouseX, mouseY, mc);
            mc.func_110434_K().func_110577_a(field_146122_a);
            this.func_146119_b(mc, mouseX, mouseY);
            GlStateManager.func_179117_G();
            this.buttonRenderer.drawButtonText(mc);
        } else {
            this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
            int k = this.func_146114_a(this.field_146123_n);
            GuiUtils.drawContinuousTexturedBox((ResourceLocation)field_146122_a, (int)this.field_146128_h, (int)this.field_146129_i, (int)0, (int)(46 + k * 20), (int)this.field_146120_f, (int)this.field_146121_g, (int)200, (int)20, (int)2, (int)3, (int)2, (int)2, (float)this.field_73735_i);
            this.func_146119_b(mc, mouseX, mouseY);
            int color = 0xE0E0E0;
            if (!this.field_146124_l) {
                color = 0xA0A0A0;
            } else if (this.field_146123_n) {
                color = 0xFFFFA0;
            }
            String buttonText = this.field_146126_j;
            int strWidth = mc.field_71466_p.func_78256_a(buttonText);
            int ellipsisWidth = mc.field_71466_p.func_78256_a("...");
            if (strWidth > this.field_146120_f - 6 && strWidth > ellipsisWidth) {
                buttonText = mc.field_71466_p.func_78269_a(buttonText, this.field_146120_f - 6 - ellipsisWidth).trim() + "...";
            }
            this.func_73732_a(mc.field_71466_p, buttonText, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, color);
        }
    }
}

