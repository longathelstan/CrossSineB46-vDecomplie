/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import net.ccbluex.liquidbounce.injection.forge.mixins.block.MixinBlock;
import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.minecraft.block.BlockLadder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value={BlockLadder.class})
public abstract class MixinBlockLadder
extends MixinBlock {
    @ModifyConstant(method={"setBlockBoundsBasedOnState"}, constant={@Constant(floatValue=0.125f)})
    private float ViaVersion_LadderBB(float constant) {
        if (ProtocolFixer.newerThan1_8()) {
            return 0.1875f;
        }
        return 0.125f;
    }
}

