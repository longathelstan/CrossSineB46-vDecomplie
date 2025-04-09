/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerSpiderEyes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LayerSpiderEyes.class})
public class MixinLayerSpiderEyes {
    @Inject(method={"doRenderLayer(Lnet/minecraft/entity/monster/EntitySpider;FFFFFFF)V"}, at={@At(value="TAIL")})
    private void fixDepth(CallbackInfo ci) {
        GlStateManager.func_179132_a((boolean)true);
    }
}

