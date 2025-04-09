/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiOptionSlider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={GuiOptionSlider.class})
public class MixinGuiOptionSlider {
    @Redirect(method={"mouseDragged"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiOptionSlider;drawTexturedModalRect(IIIIII)V"), require=2)
    public void redirectedDrawRect(GuiOptionSlider guiSlider, int x, int y, int textureX, int textureY, int width, int height) {
        Gui.func_73734_a((int)x, (int)(y - 2), (int)(x + width), (int)(y + height + 2), (int)-12103232);
    }
}

