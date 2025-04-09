/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiDownloadTerrain.class})
public abstract class MixinGuiDownloadTerrain
extends MixinGuiScreen {
    @Inject(method={"initGui"}, at={@At(value="RETURN")})
    private void injectDisconnectButton(CallbackInfo ci) {
        this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120 + 12, I18n.func_135052_a((String)"gui.cancel", (Object[])new Object[0])));
    }

    @Override
    protected void injectedActionPerformed(GuiButton button) {
        if (button.field_146127_k == 0) {
            boolean flag = this.field_146297_k.func_71387_A();
            boolean flag1 = this.field_146297_k.func_181540_al();
            button.field_146124_l = false;
            this.field_146297_k.field_71441_e.func_72882_A();
            this.field_146297_k.func_71403_a(null);
        }
    }
}

