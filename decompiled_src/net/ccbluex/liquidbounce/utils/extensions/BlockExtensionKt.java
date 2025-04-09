/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.extensions;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=2, xi=48, d1={"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0003\u001a*\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00012\u0014\u0010\t\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0004\u0012\u00020\u00070\nH\u0086\b\u00f8\u0001\u0000\u001a\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0003\u001a\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000b0\u00102\u0006\u0010\u0012\u001a\u00020\u0003\u001a\u0012\u0010\u0013\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0015\u001a\f\u0010\u0016\u001a\u0004\u0018\u00010\u000b*\u00020\u0011\u001a\n\u0010\u0017\u001a\u00020\u0015*\u00020\u0011\u001a\f\u0010\u0018\u001a\u0004\u0018\u00010\u0019*\u00020\u0011\u001a\n\u0010\u001a\u001a\u00020\u001b*\u00020\u0011\u001a\u0011\u0010\u001c\u001a\u0004\u0018\u00010\u0007*\u00020\u0011\u00a2\u0006\u0002\u0010\u001d\u001a\n\u0010\u001e\u001a\u00020\u0007*\u00020\u0011\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\u001f"}, d2={"AxisAlignedBB", "Lnet/minecraft/util/AxisAlignedBB;", "x", "", "y", "z", "collideBlock", "", "aabb", "predicate", "Lkotlin/Function1;", "Lnet/minecraft/block/Block;", "getBlockName", "", "id", "searchBlocks", "", "Lnet/minecraft/util/BlockPos;", "radius", "down", "height", "", "getBlock", "getCenterDistance", "getMaterial", "Lnet/minecraft/block/material/Material;", "getVec", "Lnet/minecraft/util/Vec3;", "isFullBlock", "(Lnet/minecraft/util/BlockPos;)Ljava/lang/Boolean;", "isReplaceable", "CrossSine"})
public final class BlockExtensionKt {
    @Nullable
    public static final Block getBlock(@NotNull BlockPos $this$getBlock) {
        Intrinsics.checkNotNullParameter($this$getBlock, "<this>");
        return BlockUtils.getBlock($this$getBlock);
    }

    @NotNull
    public static final Vec3 getVec(@NotNull BlockPos $this$getVec) {
        Intrinsics.checkNotNullParameter($this$getVec, "<this>");
        return new Vec3((double)$this$getVec.func_177958_n() + 0.5, (double)$this$getVec.func_177956_o() + 0.5, (double)$this$getVec.func_177952_p() + 0.5);
    }

    @Nullable
    public static final Material getMaterial(@NotNull BlockPos $this$getMaterial) {
        Intrinsics.checkNotNullParameter($this$getMaterial, "<this>");
        Block block = BlockExtensionKt.getBlock($this$getMaterial);
        return block == null ? null : block.func_149688_o();
    }

    @Nullable
    public static final Boolean isFullBlock(@NotNull BlockPos $this$isFullBlock) {
        Intrinsics.checkNotNullParameter($this$isFullBlock, "<this>");
        Block block = BlockExtensionKt.getBlock($this$isFullBlock);
        return block == null ? null : Boolean.valueOf(block.func_149730_j());
    }

    public static final boolean isReplaceable(@NotNull BlockPos $this$isReplaceable) {
        boolean bl;
        Intrinsics.checkNotNullParameter($this$isReplaceable, "<this>");
        Material material = BlockExtensionKt.getMaterial($this$isReplaceable);
        return material == null ? false : (bl = material.func_76222_j());
    }

    public static final double getCenterDistance(@NotNull BlockPos $this$getCenterDistance) {
        Intrinsics.checkNotNullParameter($this$getCenterDistance, "<this>");
        return ClientUtils.INSTANCE.getMc().field_71439_g.func_70011_f((double)$this$getCenterDistance.func_177958_n() + 0.5, (double)$this$getCenterDistance.func_177956_o() + 0.5, (double)$this$getCenterDistance.func_177952_p() + 0.5);
    }

    @NotNull
    public static final AxisAlignedBB AxisAlignedBB(int x, int y, int z) {
        return new AxisAlignedBB((double)x, (double)y, (double)z, (double)x + 1.0, (double)y + 1.0, (double)z + 1.0);
    }

    @NotNull
    public static final AxisAlignedBB down(@NotNull AxisAlignedBB $this$down, double height) {
        Intrinsics.checkNotNullParameter($this$down, "<this>");
        return new AxisAlignedBB($this$down.field_72340_a, $this$down.field_72338_b, $this$down.field_72339_c, $this$down.field_72336_d, $this$down.field_72337_e - height, $this$down.field_72334_f);
    }

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
                        BlockPos blockPos;
                        if (BlockExtensionKt.getBlock(blockPos = new BlockPos((int)ClientUtils.INSTANCE.getMc().field_71439_g.field_70165_t + x, (int)ClientUtils.INSTANCE.getMc().field_71439_g.field_70163_u + y, (int)ClientUtils.INSTANCE.getMc().field_71439_g.field_70161_v + (z = n6--))) == null) continue;
                        blocks.put(blockPos, block);
                    } while (z != n5);
                } while (y != n3);
            } while (x != n);
        }
        return blocks;
    }

    @NotNull
    public static final String getBlockName(int id) {
        String string = Block.func_149729_e((int)id).func_149732_F();
        Intrinsics.checkNotNullExpressionValue(string, "getBlockById(id).localizedName");
        return string;
    }

    public static final boolean collideBlock(@NotNull AxisAlignedBB aabb, @NotNull Function1<? super Block, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(aabb, "aabb");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        boolean $i$f$collideBlock = false;
        int n = MathHelper.func_76128_c((double)aabb.field_72340_a);
        int n2 = MathHelper.func_76128_c((double)aabb.field_72336_d) + 1;
        while (n < n2) {
            int x = n++;
            int n3 = MathHelper.func_76128_c((double)aabb.field_72339_c);
            int n4 = MathHelper.func_76128_c((double)aabb.field_72334_f) + 1;
            while (n3 < n4) {
                int z;
                Block block;
                if (predicate.invoke((Block)(block = BlockExtensionKt.getBlock(new BlockPos((double)x, aabb.field_72338_b, (double)(z = n3++))))).booleanValue()) continue;
                return false;
            }
        }
        return true;
    }
}

