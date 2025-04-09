/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.visual.XRay;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={BlockModelRenderer.class})
public class MixinBlockModelRenderer {
    @Inject(method={"renderModelAmbientOcclusion"}, at={@At(value="HEAD")}, cancellable=true)
    private void renderModelAmbientOcclusion(IBlockAccess blockAccessIn, IBakedModel modelIn, Block blockIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSide, CallbackInfoReturnable<Boolean> booleanCallbackInfoReturnable) {
        XRay xray = CrossSine.moduleManager.getModule(XRay.class);
        if (xray.getState() && !xray.getXrayBlocks().contains(blockIn)) {
            booleanCallbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method={"renderModelStandard"}, at={@At(value="HEAD")}, cancellable=true)
    private void renderModelStandard(IBlockAccess blockAccessIn, IBakedModel modelIn, Block blockIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSides, CallbackInfoReturnable<Boolean> booleanCallbackInfoReturnable) {
        XRay xray = CrossSine.moduleManager.getModule(XRay.class);
        if (xray.getState() && !xray.getXrayBlocks().contains(blockIn)) {
            booleanCallbackInfoReturnable.setReturnValue(false);
        }
    }
}

