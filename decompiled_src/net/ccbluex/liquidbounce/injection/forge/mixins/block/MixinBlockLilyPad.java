/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value={BlockLilyPad.class})
public abstract class MixinBlockLilyPad
extends BlockBush {
    @Overwrite
    public AxisAlignedBB func_180640_a(World worldIn, BlockPos pos, IBlockState state) {
        if (ProtocolFixer.newerThan1_8()) {
            return new AxisAlignedBB((double)pos.func_177958_n() + 0.0625, (double)pos.func_177956_o() + 0.0, (double)pos.func_177952_p() + 0.0625, (double)pos.func_177958_n() + 0.9375, (double)pos.func_177956_o() + 0.09375, (double)pos.func_177952_p() + 0.9375);
        }
        return new AxisAlignedBB((double)pos.func_177958_n() + 0.0, (double)pos.func_177956_o() + 0.0, (double)pos.func_177952_p() + 0.0, (double)pos.func_177958_n() + 1.0, (double)pos.func_177956_o() + 0.015625, (double)pos.func_177952_p() + 1.0);
    }
}

