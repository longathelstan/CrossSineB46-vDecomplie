/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.client.renderer.entity.layers.LayerArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={LayerArrow.class})
public class MixinLayerArrow {
    @Redirect(method={"doRenderLayer"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/RenderHelper;disableStandardItemLighting()V"))
    private void removeDisable() {
    }

    @Redirect(method={"doRenderLayer"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/RenderHelper;enableStandardItemLighting()V"))
    private void removeEnable() {
    }
}

