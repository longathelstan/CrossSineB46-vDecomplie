/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={World.class})
public abstract class MixinWorld
implements IBlockAccess {
    @Inject(method={"destroyBlock"}, at={@At(value="HEAD")}, cancellable=true)
    private void destroyBlock(BlockPos p_destroyBlock_1_, boolean p_destroyBlock_2_, CallbackInfoReturnable<Boolean> ci) {
        IBlockState iblockstate = MinecraftInstance.mc.field_71441_e.func_180495_p(p_destroyBlock_1_);
        Block block = iblockstate.func_177230_c();
        MinecraftInstance.mc.field_71441_e.func_175718_b(2001, p_destroyBlock_1_, Block.func_176210_f((IBlockState)iblockstate));
        if (block.func_149688_o() == Material.field_151579_a) {
            ci.setReturnValue(false);
        } else {
            if (p_destroyBlock_2_) {
                block.func_176226_b((World)MinecraftInstance.mc.field_71441_e, p_destroyBlock_1_, iblockstate, 0);
            }
            ci.setReturnValue(MinecraftInstance.mc.field_71441_e.func_180501_a(p_destroyBlock_1_, Blocks.field_150350_a.func_176223_P(), 3));
        }
    }
}

