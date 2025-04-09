/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.visual.Glint;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value={LayerArmorBase.class})
public abstract class MixinLayerArmorBase
implements LayerRenderer<EntityLivingBase> {
    @ModifyArgs(method={"renderGlint"}, slice=@Slice(from=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;disableLighting()V", ordinal=0)), at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V", ordinal=0), require=1, allow=1)
    private void renderGlint(Args args2) {
        Glint glint = CrossSine.moduleManager.getModule(Glint.class);
        if (glint.getState()) {
            int n = glint.getColor().getRGB();
            args2.set(0, Float.valueOf((float)(n >> 16 & 0xFF) / 255.0f));
            args2.set(1, Float.valueOf((float)(n >> 8 & 0xFF) / 255.0f));
            args2.set(2, Float.valueOf((float)(n & 0xFF) / 255.0f));
            args2.set(3, Float.valueOf((float)(n >> 24 & 0xFF) / 255.0f));
        }
    }
}

