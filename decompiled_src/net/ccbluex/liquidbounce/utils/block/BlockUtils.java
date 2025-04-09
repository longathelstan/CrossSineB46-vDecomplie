/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bJ\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J&\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0014\u0010\u0015\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\u000f0\u0016H\u0007J&\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0014\u0010\u0015\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\u000f0\u0016H\u0007J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0007J\u0014\u0010\u001b\u001a\u0004\u0018\u00010\t2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\u0014\u0010\u001b\u001a\u0004\u0018\u00010\t2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0019H\u0007J(\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\t2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u000fJ\u0010\u0010#\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u0007H\u0007J\u000e\u0010%\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u0007J\u001d\u0010&\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050'\u00a2\u0006\u0002\u0010(J\u0010\u0010)\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0014\u0010*\u001a\u0004\u0018\u00010+2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\u0012\u0010,\u001a\u00020-2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J,\u0010.\u001a\u00020\u001d2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010\u001e\u001a\u0004\u0018\u00010\t2\u0006\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u000fH\u0002J\u0006\u0010/\u001a\u00020\u000fJ\u0010\u0010/\u001a\u00020\u000f2\u0006\u00100\u001a\u00020\u0014H\u0007J\u0012\u00101\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\u0012\u00102\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\u001c\u00103\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\t042\u0006\u00105\u001a\u00020\u0007H\u0007R \u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00066"}, d2={"Lnet/ccbluex/liquidbounce/utils/block/BlockUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "blockNames", "", "Lkotlin/Pair;", "", "", "blockRelativeToPlayer", "Lnet/minecraft/block/Block;", "offsetX", "", "offsetY", "offsetZ", "canBeClicked", "", "blockPos", "Lnet/minecraft/util/BlockPos;", "collideBlock", "axisAlignedBB", "Lnet/minecraft/util/AxisAlignedBB;", "collide", "Lkotlin/Function1;", "collideBlockIntersects", "floorVec3", "Lnet/minecraft/util/Vec3;", "vec3", "getBlock", "getBlockHardness", "", "block", "itemStack", "Lnet/minecraft/item/ItemStack;", "ignoreSlow", "ignoreGround", "getBlockName", "id", "getBlockName2", "getBlockNamesAndIDs", "", "()[Lkotlin/Pair;", "getCenterDistance", "getMaterial", "Lnet/minecraft/block/material/Material;", "getState", "Lnet/minecraft/block/state/IBlockState;", "getToolDigEfficiency", "insideBlock", "bb", "isFullBlock", "isReplaceable", "searchBlocks", "", "radius", "CrossSine"})
public final class BlockUtils
extends MinecraftInstance {
    @NotNull
    public static final BlockUtils INSTANCE = new BlockUtils();
    @NotNull
    private static final List<Pair<String, Integer>> blockNames = new ArrayList();

    private BlockUtils() {
    }

    @NotNull
    public final Block blockRelativeToPlayer(double offsetX, double offsetY, double offsetZ) {
        Block block = MinecraftInstance.mc.field_71441_e.func_180495_p(new BlockPos((Entity)MinecraftInstance.mc.field_71439_g).func_177963_a(offsetX, offsetY, offsetZ)).func_177230_c();
        Intrinsics.checkNotNullExpressionValue(block, "mc.theWorld.getBlockStat\u2026 offsetY, offsetZ)).block");
        return block;
    }

    public final boolean insideBlock() {
        if (MinecraftInstance.mc.field_71439_g.field_70173_aa < 5) {
            return false;
        }
        AxisAlignedBB axisAlignedBB = MinecraftInstance.mc.field_71439_g.func_174813_aQ();
        Intrinsics.checkNotNullExpressionValue(axisAlignedBB, "mc.thePlayer.entityBoundingBox");
        return BlockUtils.insideBlock(axisAlignedBB);
    }

    @JvmStatic
    public static final boolean insideBlock(@NotNull AxisAlignedBB bb) {
        Intrinsics.checkNotNullParameter(bb, "bb");
        WorldClient world = MinecraftInstance.mc.field_71441_e;
        int n = MathHelper.func_76128_c((double)bb.field_72340_a);
        int n2 = MathHelper.func_76128_c((double)bb.field_72336_d) + 1;
        while (n < n2) {
            int x = n++;
            int n3 = MathHelper.func_76128_c((double)bb.field_72338_b);
            int n4 = MathHelper.func_76128_c((double)bb.field_72337_e) + 1;
            while (n3 < n4) {
                int y = n3++;
                int n5 = MathHelper.func_76128_c((double)bb.field_72339_c);
                int n6 = MathHelper.func_76128_c((double)bb.field_72334_f) + 1;
                while (n5 < n6) {
                    AxisAlignedBB axisAlignedBB;
                    int z = n5++;
                    Block block = world.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
                    AxisAlignedBB boundingBox = null;
                    if (block == null || block instanceof BlockAir) continue;
                    AxisAlignedBB it = axisAlignedBB = block.func_180640_a((World)world, new BlockPos(x, y, z), world.func_180495_p(new BlockPos(x, y, z)));
                    boolean bl = false;
                    boundingBox = it;
                    if (axisAlignedBB == null || !bb.func_72326_a(boundingBox)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public final float getBlockHardness(@NotNull Block block, @Nullable ItemStack itemStack, boolean ignoreSlow, boolean ignoreGround) {
        Intrinsics.checkNotNullParameter(block, "block");
        float getBlockHardness = block.func_176195_g((World)MinecraftInstance.mc.field_71441_e, null);
        if (getBlockHardness < 0.0f) {
            return 0.0f;
        }
        return block.func_149688_o().func_76229_l() || itemStack != null && itemStack.func_150998_b(block) ? this.getToolDigEfficiency(itemStack, block, ignoreSlow, ignoreGround) / getBlockHardness / 30.0f : this.getToolDigEfficiency(itemStack, block, ignoreSlow, ignoreGround) / getBlockHardness / 100.0f;
    }

    private final float getToolDigEfficiency(ItemStack itemStack, Block block, boolean ignoreSlow, boolean ignoreGround) {
        int getEnchantmentLevel;
        float n;
        ItemStack itemStack2 = itemStack;
        float f = n = itemStack2 == null ? 1.0f : itemStack2.func_77973_b().func_150893_a(itemStack, block);
        if (n > 1.0f && (getEnchantmentLevel = EnchantmentHelper.func_77506_a((int)Enchantment.field_77349_p.field_77352_x, (ItemStack)itemStack)) > 0 && itemStack != null) {
            n += (float)(getEnchantmentLevel * getEnchantmentLevel + 1);
        }
        if (MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76422_e)) {
            n *= 1.0f + (float)(MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76422_e).func_76458_c() + 1) * 0.2f;
        }
        if (!ignoreSlow) {
            if (MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76419_f)) {
                float f2;
                switch (MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76419_f).func_76458_c()) {
                    case 0: {
                        f2 = 0.3f;
                        break;
                    }
                    case 1: {
                        f2 = 0.09f;
                        break;
                    }
                    case 2: {
                        f2 = 0.0027f;
                        break;
                    }
                    default: {
                        f2 = 8.1E-4f;
                    }
                }
                float n2 = f2;
                n *= n2;
            }
            if (MinecraftInstance.mc.field_71439_g.func_70055_a(Material.field_151586_h) && !EnchantmentHelper.func_77510_g((EntityLivingBase)((EntityLivingBase)MinecraftInstance.mc.field_71439_g))) {
                n /= 5.0f;
            }
            if (!MinecraftInstance.mc.field_71439_g.field_70122_E && !ignoreGround) {
                n /= 5.0f;
            }
        }
        return n;
    }

    @JvmStatic
    @Nullable
    public static final Block getBlock(@Nullable Vec3 vec3) {
        return BlockUtils.getBlock(new BlockPos(vec3));
    }

    @JvmStatic
    @Nullable
    public static final Block getBlock(@Nullable BlockPos blockPos) {
        Object object;
        WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
        if (worldClient == null) {
            object = null;
        } else {
            IBlockState iBlockState = worldClient.func_180495_p(blockPos);
            object = iBlockState == null ? null : iBlockState.func_177230_c();
        }
        return object;
    }

    @JvmStatic
    @Nullable
    public static final Material getMaterial(@Nullable BlockPos blockPos) {
        Block block = BlockUtils.getBlock(blockPos);
        return block == null ? null : block.func_149688_o();
    }

    @JvmStatic
    public static final boolean isReplaceable(@Nullable BlockPos blockPos) {
        boolean bl;
        Material material = BlockUtils.getMaterial(blockPos);
        return material == null ? false : (bl = material.func_76222_j());
    }

    @JvmStatic
    @NotNull
    public static final IBlockState getState(@Nullable BlockPos blockPos) {
        IBlockState iBlockState = MinecraftInstance.mc.field_71441_e.func_180495_p(blockPos);
        Intrinsics.checkNotNullExpressionValue(iBlockState, "mc.theWorld.getBlockState(blockPos)");
        return iBlockState;
    }

    @JvmStatic
    public static final boolean canBeClicked(@Nullable BlockPos blockPos) {
        boolean bl;
        Block block = BlockUtils.getBlock(blockPos);
        boolean bl2 = block == null ? false : (bl = block.func_176209_a(BlockUtils.getState(blockPos), false));
        return bl2 && MinecraftInstance.mc.field_71441_e.func_175723_af().func_177746_a(blockPos);
    }

    @JvmStatic
    @NotNull
    public static final String getBlockName(int id) {
        String string = Block.func_149729_e((int)id).func_149732_F();
        Intrinsics.checkNotNullExpressionValue(string, "getBlockById(id).localizedName");
        return string;
    }

    @JvmStatic
    public static final boolean isFullBlock(@Nullable BlockPos blockPos) {
        AxisAlignedBB axisAlignedBB;
        Block block = BlockUtils.getBlock(blockPos);
        AxisAlignedBB axisAlignedBB2 = block == null ? null : (axisAlignedBB = block.func_180640_a((World)MinecraftInstance.mc.field_71441_e, blockPos, BlockUtils.getState(blockPos)));
        if (axisAlignedBB == null) {
            return false;
        }
        AxisAlignedBB axisAlignedBB3 = axisAlignedBB;
        return axisAlignedBB3.field_72336_d - axisAlignedBB3.field_72340_a == 1.0 && axisAlignedBB3.field_72337_e - axisAlignedBB3.field_72338_b == 1.0 && axisAlignedBB3.field_72334_f - axisAlignedBB3.field_72339_c == 1.0;
    }

    @JvmStatic
    public static final double getCenterDistance(@NotNull BlockPos blockPos) {
        Intrinsics.checkNotNullParameter(blockPos, "blockPos");
        return MinecraftInstance.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n() + 0.5, (double)blockPos.func_177956_o() + 0.5, (double)blockPos.func_177952_p() + 0.5);
    }

    @JvmStatic
    @NotNull
    public static final Map<BlockPos, Block> searchBlocks(int radius) {
        Map blocks = new LinkedHashMap();
        int n = -radius + 1;
        int n2 = radius;
        if (n <= n2) {
            int x;
            do {
                int y;
                x = n2--;
                int n3 = -radius + 1;
                int n4 = radius;
                if (n3 > n4) continue;
                do {
                    int z;
                    y = n4--;
                    int n5 = -radius + 1;
                    int n6 = radius;
                    if (n5 > n6) continue;
                    do {
                        Block block;
                        z = n6--;
                        BlockPos blockPos = new BlockPos((int)MinecraftInstance.mc.field_71439_g.field_70165_t + x, (int)MinecraftInstance.mc.field_71439_g.field_70163_u + y, (int)MinecraftInstance.mc.field_71439_g.field_70161_v + z);
                        if (BlockUtils.getBlock(blockPos) == null) continue;
                        blocks.put(blockPos, block);
                    } while (z != n5);
                } while (y != n3);
            } while (x != n);
        }
        return blocks;
    }

    @JvmStatic
    public static final boolean collideBlock(@NotNull AxisAlignedBB axisAlignedBB, @NotNull Function1<? super Block, Boolean> collide) {
        Intrinsics.checkNotNullParameter(axisAlignedBB, "axisAlignedBB");
        Intrinsics.checkNotNullParameter(collide, "collide");
        int n = MathHelper.func_76128_c((double)MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72340_a);
        int n2 = MathHelper.func_76128_c((double)MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72336_d) + 1;
        while (n < n2) {
            int x = n++;
            int n3 = MathHelper.func_76128_c((double)MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72339_c);
            int n4 = MathHelper.func_76128_c((double)MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72334_f) + 1;
            while (n3 < n4) {
                int z = n3++;
                Block block = BlockUtils.getBlock(new BlockPos((double)x, axisAlignedBB.field_72338_b, (double)z));
                if (collide.invoke((Block)block).booleanValue()) continue;
                return false;
            }
        }
        return true;
    }

    @NotNull
    public final Pair<String, Integer>[] getBlockNamesAndIDs() {
        if (blockNames.isEmpty()) {
            int n = 0;
            while (n < 32769) {
                int id;
                Block block;
                if ((block = Block.func_149729_e((int)(id = n++))) == Blocks.field_150350_a) continue;
                CharSequence charSequence = block.getRegistryName();
                Intrinsics.checkNotNullExpressionValue(charSequence, "block.registryName");
                charSequence = charSequence;
                Regex regex = new Regex("^minecraft:");
                String string = "";
                blockNames.add(TuplesKt.to(regex.replace(charSequence, string), id));
            }
            List<Pair<String, Integer>> $this$sortBy$iv = blockNames;
            boolean $i$f$sortBy = false;
            if ($this$sortBy$iv.size() > 1) {
                CollectionsKt.sortWith($this$sortBy$iv, new Comparator(){

                    public final int compare(T a, T b) {
                        Pair it = (Pair)a;
                        boolean bl = false;
                        Comparable comparable = (Comparable)((Object)((String)it.getFirst()));
                        it = (Pair)b;
                        Comparable comparable2 = comparable;
                        bl = false;
                        return ComparisonsKt.compareValues(comparable2, (Comparable)((Object)((String)it.getFirst())));
                    }
                });
            }
        }
        Collection $this$toTypedArray$iv = blockNames;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        Pair[] pairArray = thisCollection$iv.toArray(new Pair[0]);
        if (pairArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        return pairArray;
    }

    @JvmStatic
    public static final boolean collideBlockIntersects(@NotNull AxisAlignedBB axisAlignedBB, @NotNull Function1<? super Block, Boolean> collide) {
        Intrinsics.checkNotNullParameter(axisAlignedBB, "axisAlignedBB");
        Intrinsics.checkNotNullParameter(collide, "collide");
        int n = MathHelper.func_76128_c((double)MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72340_a);
        int n2 = MathHelper.func_76128_c((double)MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72336_d) + 1;
        while (n < n2) {
            int x = n++;
            int n3 = MathHelper.func_76128_c((double)MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72339_c);
            int n4 = MathHelper.func_76128_c((double)MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72334_f) + 1;
            while (n3 < n4) {
                AxisAlignedBB axisAlignedBB2;
                int z = n3++;
                BlockPos blockPos = new BlockPos((double)x, axisAlignedBB.field_72338_b, (double)z);
                Block block = BlockUtils.getBlock(blockPos);
                if (!collide.invoke((Block)block).booleanValue()) continue;
                Block block2 = block;
                AxisAlignedBB axisAlignedBB3 = block2 == null ? null : (axisAlignedBB2 = block2.func_180640_a((World)MinecraftInstance.mc.field_71441_e, blockPos, BlockUtils.getState(blockPos)));
                if (axisAlignedBB2 == null) continue;
                AxisAlignedBB boundingBox = axisAlignedBB2;
                if (!MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72326_a(boundingBox)) continue;
                return true;
            }
        }
        return false;
    }

    @NotNull
    public final String getBlockName2(int id) {
        CharSequence charSequence = Block.func_149729_e((int)id).getRegistryName();
        Intrinsics.checkNotNullExpressionValue(charSequence, "getBlockById(id).registryName");
        charSequence = charSequence;
        Regex regex = new Regex("^minecraft:");
        String string = "";
        return regex.replace(charSequence, string);
    }

    @JvmStatic
    @NotNull
    public static final Vec3 floorVec3(@NotNull Vec3 vec3) {
        Intrinsics.checkNotNullParameter(vec3, "vec3");
        return new Vec3(Math.floor(vec3.field_72450_a), Math.floor(vec3.field_72448_b), Math.floor(vec3.field_72449_c));
    }
}

