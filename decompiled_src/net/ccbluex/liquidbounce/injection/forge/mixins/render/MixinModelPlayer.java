/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.UpdateModelEvent;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ModelPlayer.class})
public class MixinModelPlayer
extends ModelBiped {
    @Shadow
    private boolean field_178735_y;

    @ModifyConstant(method={"<init>"}, constant={@Constant(floatValue=2.5f)})
    private float fixAlexArmHeight(float original) {
        return 2.0f;
    }

    @Overwrite
    public void func_178718_a(float scale) {
        if (this.field_178735_y) {
            this.field_178723_h.field_78800_c += 0.5f;
            this.field_178723_h.func_78794_c(scale);
            this.field_178723_h.field_78798_e -= 0.5f;
        } else {
            this.field_178723_h.func_78794_c(scale);
        }
    }

    @Inject(method={"setRotationAngles"}, at={@At(value="RETURN")})
    private void revertSwordAnimation(float p_setRotationAngles_1_, float p_setRotationAngles_2_, float p_setRotationAngles_3_, float p_setRotationAngles_4_, float p_setRotationAngles_5_, float p_setRotationAngles_6_, Entity p_setRotationAngles_7_, CallbackInfo callbackInfo) {
        CrossSine.eventManager.callEvent(new UpdateModelEvent((EntityPlayer)p_setRotationAngles_7_, (ModelPlayer)this));
    }
}

