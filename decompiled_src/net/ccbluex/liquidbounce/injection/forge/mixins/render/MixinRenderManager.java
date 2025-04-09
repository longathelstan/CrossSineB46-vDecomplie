/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.visual.FreeLook;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={RenderManager.class})
public class MixinRenderManager {
    @Redirect(method={"cacheActiveRenderInfo"}, at=@At(value="FIELD", target="Lnet/minecraft/client/renderer/entity/RenderManager;playerViewX:F", opcode=181))
    public void getPlayerViewX(RenderManager renderManager, float value) {
        renderManager.field_78732_j = !CrossSine.moduleManager.getModule(FreeLook.class).getState() && FreeLook.perspectiveToggled ? FreeLook.cameraPitch : (CrossSine.moduleManager.getModule(FreeLook.class).getState() && RotationUtils.freeLookRotation != null ? FreeLook.cameraPitch2 : (CrossSine.moduleManager.getModule(FreeLook.class).getState() ? FreeLook.cameraPitch : value));
    }

    @Redirect(method={"cacheActiveRenderInfo"}, at=@At(value="FIELD", target="Lnet/minecraft/client/renderer/entity/RenderManager;playerViewY:F", opcode=181))
    public void getPlayerViewY(RenderManager renderManager, float value) {
        renderManager.field_78735_i = !CrossSine.moduleManager.getModule(FreeLook.class).getState() && FreeLook.perspectiveToggled ? FreeLook.cameraYaw : (CrossSine.moduleManager.getModule(FreeLook.class).getState() && RotationUtils.freeLookRotation != null ? FreeLook.cameraYaw2 : (CrossSine.moduleManager.getModule(FreeLook.class).getState() ? FreeLook.cameraYaw : value));
    }
}

