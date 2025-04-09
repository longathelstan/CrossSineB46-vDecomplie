/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.Objects;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.visual.Cape;
import net.ccbluex.liquidbounce.features.module.modules.visual.NoFov;
import net.ccbluex.liquidbounce.injection.forge.mixins.entity.MixinEntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={AbstractClientPlayer.class})
public abstract class MixinAbstractClientPlayer
extends MixinEntityPlayer {
    @Inject(method={"getFovModifier"}, at={@At(value="HEAD")}, cancellable=true)
    private void getFovModifier(CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (NoFov.INSTANCE.getState()) {
            float newFOV = ((Float)NoFov.INSTANCE.getFov().get()).floatValue();
            if (!this.func_71039_bw()) {
                callbackInfoReturnable.setReturnValue(Float.valueOf(newFOV));
                return;
            }
            if (this.func_71011_bu().func_77973_b() != Items.field_151031_f) {
                callbackInfoReturnable.setReturnValue(Float.valueOf(newFOV));
                return;
            }
            int i = this.func_71057_bx();
            float f1 = (float)i / 20.0f;
            f1 = f1 > 1.0f ? 1.0f : f1 * f1;
            callbackInfoReturnable.setReturnValue(Float.valueOf(newFOV *= 1.0f - f1 * 0.15f));
        }
    }

    @Inject(method={"getLocationCape"}, at={@At(value="HEAD")}, cancellable=true)
    private void getCape(CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
        Cape cape = CrossSine.moduleManager.getModule(Cape.class);
        if (cape.getState() && Objects.equals(this.func_146103_bH().getName(), Minecraft.func_71410_x().field_71439_g.func_146103_bH().getName())) {
            callbackInfoReturnable.setReturnValue(cape.getCapeLocation((String)cape.getStyleValue().get()));
        }
    }
}

