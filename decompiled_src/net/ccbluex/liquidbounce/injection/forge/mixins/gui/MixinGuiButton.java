/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.utils.GameButtonUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiButton.class})
public abstract class MixinGuiButton
extends Gui {
    @Shadow
    public int field_146128_h;
    @Shadow
    public int field_146129_i;
    @Shadow
    public int field_146120_f;
    @Shadow
    public int field_146121_g;
    @Shadow
    public boolean field_146123_n;
    @Shadow
    public boolean field_146124_l;
    @Shadow
    public boolean field_146125_m;
    @Final
    @Shadow
    protected static ResourceLocation field_146122_a;
    @Shadow
    public String field_146126_j;
    protected final GameButtonUtils buttonRenderer = new GameButtonUtils((GuiButton)this);

    @Shadow
    protected abstract void func_146119_b(Minecraft var1, int var2, int var3);

    @Shadow
    protected abstract int func_146114_a(boolean var1);

    @Inject(method={"drawButton"}, at={@At(value="HEAD")}, cancellable=true)
    public void drawButton(Minecraft mc, int mouseX, int mouseY, CallbackInfo ci) {
        if (!CrossSine.INSTANCE.getDestruced()) {
            if (!this.field_146125_m) {
                return;
            }
            this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
            this.func_146119_b(mc, mouseX, mouseY);
            this.buttonRenderer.render(mouseX, mouseY, mc);
            this.buttonRenderer.drawButtonText(mc);
            ci.cancel();
        }
    }
}

