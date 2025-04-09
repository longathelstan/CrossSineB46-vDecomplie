/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={GuiSlider.class})
public class MixinGuiSlider {
    @Redirect(method={"mouseDragged"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiSlider;drawTexturedModalRect(IIIIII)V"), require=2)
    public void redirectedDrawRect(GuiSlider guiSlider, int x, int y, int textureX, int textureY, int width, int height) {
        Gui.func_73734_a((int)x, (int)(y - 2), (int)(x + width), (int)(y + height + 2), (int)-12103232);
    }
}

