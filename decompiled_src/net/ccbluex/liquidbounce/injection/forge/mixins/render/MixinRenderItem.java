/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.visual.Glint;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={RenderItem.class})
public abstract class MixinRenderItem {
    @Shadow
    protected abstract void func_175035_a(IBakedModel var1, int var2);

    @Shadow
    protected abstract void func_175036_a(IBakedModel var1, ItemStack var2);

    @Shadow
    protected abstract void func_180451_a(IBakedModel var1);

    @Redirect(method={"renderEffect"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;I)V"))
    private void renderModel(RenderItem renderItem, IBakedModel model, int color) {
        Glint glint = CrossSine.moduleManager.getModule(Glint.class);
        this.func_175035_a(model, glint.getState() ? glint.getColor().getRGB() : -8372020);
    }
}

