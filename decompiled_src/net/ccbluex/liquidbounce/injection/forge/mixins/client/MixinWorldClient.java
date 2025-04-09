/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.visual.TrueSight;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value={WorldClient.class})
public class MixinWorldClient {
    @ModifyVariable(method={"doVoidFogParticles"}, at=@At(value="INVOKE", target="Lnet/minecraft/block/Block;randomDisplayTick(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V", shift=At.Shift.AFTER), ordinal=0)
    private boolean handleBarriers(boolean flag) {
        TrueSight trueSight = CrossSine.moduleManager.getModule(TrueSight.class);
        return flag || trueSight.getState() && (Boolean)trueSight.getBarriersValue().get() != false;
    }
}

