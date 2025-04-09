/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={Gui.class})
public abstract class MixinGui {
    @Shadow
    @Final
    public static ResourceLocation field_110324_m = new ResourceLocation("textures/gui/icons.png");
    @Shadow
    protected float field_73735_i;

    @Shadow
    public abstract void func_73729_b(int var1, int var2, int var3, int var4, int var5, int var6);
}

