/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={GuiOptions.class})
public class MixinGuiOptions
extends GuiScreen {
    public void func_146281_b() {
        this.field_146297_k.field_71474_y.func_74303_b();
    }
}

