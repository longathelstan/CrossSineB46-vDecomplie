/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={RenderPlayer.class})
public class MixinRenderPlayer {
    @Redirect(method={"renderRightArm"}, at=@At(value="FIELD", target="Lnet/minecraft/client/model/ModelPlayer;isSneak:Z", ordinal=0))
    private void resetArmState(ModelPlayer modelPlayer, boolean value) {
        modelPlayer.field_78117_n = false;
        modelPlayer.field_78093_q = false;
    }
}

