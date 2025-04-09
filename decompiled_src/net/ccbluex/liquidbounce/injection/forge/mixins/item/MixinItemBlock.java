/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.item;

import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={ItemBlock.class})
public class MixinItemBlock
extends Item {
    @Shadow
    @Final
    public Block field_150939_a;

    @Overwrite
    public boolean func_180614_a(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        IBlockState iblockstate = worldIn.func_180495_p(pos);
        Block block = iblockstate.func_177230_c();
        if (!block.func_176200_f(worldIn, pos)) {
            pos = pos.func_177972_a(side);
        }
        if (stack.field_77994_a == 0) {
            return false;
        }
        if (!playerIn.func_175151_a(pos, side, stack)) {
            return false;
        }
        if (worldIn.func_175716_a(this.field_150939_a, pos, false, side, null, stack)) {
            int i = this.func_77647_b(stack.func_77960_j());
            IBlockState iblockstate1 = this.field_150939_a.func_180642_a(worldIn, pos, side, hitX, hitY, hitZ, i, (EntityLivingBase)playerIn);
            if (worldIn.func_180501_a(pos, iblockstate1, 3)) {
                iblockstate1 = worldIn.func_180495_p(pos);
                if (iblockstate1.func_177230_c() == this.field_150939_a) {
                    ItemBlock.func_179224_a((World)worldIn, (EntityPlayer)playerIn, (BlockPos)pos, (ItemStack)stack);
                    this.field_150939_a.func_180633_a(worldIn, pos, iblockstate1, (EntityLivingBase)playerIn, stack);
                }
                if (ProtocolFixer.newerThan1_8()) {
                    MinecraftInstance.mc.field_71441_e.func_175731_a(pos.func_177963_a(0.5, 0.5, 0.5), this.field_150939_a.field_149762_H.func_150496_b(), (this.field_150939_a.field_149762_H.func_150497_c() + 1.0f) / 2.0f, this.field_150939_a.field_149762_H.func_150494_d() * 0.8f, false);
                } else {
                    worldIn.func_72908_a((double)((float)pos.func_177958_n() + 0.5f), (double)((float)pos.func_177956_o() + 0.5f), (double)((float)pos.func_177952_p() + 0.5f), this.field_150939_a.field_149762_H.func_150496_b(), (this.field_150939_a.field_149762_H.func_150497_c() + 1.0f) / 2.0f, this.field_150939_a.field_149762_H.func_150494_d() * 0.8f);
                }
                --stack.field_77994_a;
            }
            return true;
        }
        return false;
    }
}

