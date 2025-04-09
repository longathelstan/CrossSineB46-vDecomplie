/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.extensions;

import java.util.LinkedList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=2, xi=48, d1={"\u0000J\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u001a\u0010\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b*\u00020\u0001\u001a(\u0010\n\u001a\u00020\u0003*\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u001a\u0012\u0010\u0010\u001a\u00020\u0011*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0003\u001a\u001c\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00150\u0014*\u00020\u0011\u001a\n\u0010\u0016\u001a\u00020\u0015*\u00020\u0015\u001a\n\u0010\u0017\u001a\u00020\u0003*\u00020\u0015\u001a\n\u0010\u0018\u001a\u00020\u0011*\u00020\u0019\u00a8\u0006\u001a"}, d2={"expands", "Lnet/minecraft/util/AxisAlignedBB;", "v", "", "modifyYDown", "", "modifyYUp", "getBlockStatesIncluded", "", "Lnet/minecraft/block/state/IBlockState;", "getLookingTargetRange", "thePlayer", "Lnet/minecraft/client/entity/EntityPlayerSP;", "rotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "range", "multiply", "Lnet/minecraft/util/Vec3;", "value", "toFloatTriple", "Lkotlin/Triple;", "", "toRadians", "toRadiansD", "toVec", "Lnet/minecraft/util/BlockPos;", "CrossSine"})
public final class OtherExtensionKt {
    @NotNull
    public static final Vec3 multiply(@NotNull Vec3 $this$multiply, double value) {
        Intrinsics.checkNotNullParameter($this$multiply, "<this>");
        return new Vec3($this$multiply.field_72450_a * value, $this$multiply.field_72448_b * value, $this$multiply.field_72449_c * value);
    }

    public static final double getLookingTargetRange(@NotNull AxisAlignedBB $this$getLookingTargetRange, @NotNull EntityPlayerSP thePlayer, @Nullable Rotation rotation, double range) {
        Intrinsics.checkNotNullParameter($this$getLookingTargetRange, "<this>");
        Intrinsics.checkNotNullParameter(thePlayer, "thePlayer");
        Vec3 eyes = thePlayer.func_174824_e(1.0f);
        Rotation rotation2 = rotation;
        if (rotation2 == null) {
            rotation2 = RotationUtils.targetRotation;
        }
        MovingObjectPosition movingObjectPosition = $this$getLookingTargetRange.func_72327_a(eyes, OtherExtensionKt.multiply(rotation2.toDirection(), range).func_178787_e(eyes));
        if (movingObjectPosition == null) {
            return Double.MAX_VALUE;
        }
        MovingObjectPosition movingObj = movingObjectPosition;
        return movingObj.field_72307_f.func_72438_d(eyes);
    }

    public static /* synthetic */ double getLookingTargetRange$default(AxisAlignedBB axisAlignedBB, EntityPlayerSP entityPlayerSP, Rotation rotation, double d, int n, Object object) {
        if ((n & 2) != 0) {
            rotation = null;
        }
        if ((n & 4) != 0) {
            d = 6.0;
        }
        return OtherExtensionKt.getLookingTargetRange(axisAlignedBB, entityPlayerSP, rotation, d);
    }

    @NotNull
    public static final Vec3 toVec(@NotNull BlockPos $this$toVec) {
        Intrinsics.checkNotNullParameter($this$toVec, "<this>");
        return new Vec3((Vec3i)$this$toVec);
    }

    @NotNull
    public static final Triple<Float, Float, Float> toFloatTriple(@NotNull Vec3 $this$toFloatTriple) {
        Intrinsics.checkNotNullParameter($this$toFloatTriple, "<this>");
        return new Triple<Float, Float, Float>(Float.valueOf((float)$this$toFloatTriple.field_72450_a), Float.valueOf((float)$this$toFloatTriple.field_72448_b), Float.valueOf((float)$this$toFloatTriple.field_72449_c));
    }

    @NotNull
    public static final AxisAlignedBB expands(@NotNull AxisAlignedBB $this$expands, double v, boolean modifyYDown, boolean modifyYUp) {
        Intrinsics.checkNotNullParameter($this$expands, "<this>");
        return new AxisAlignedBB($this$expands.field_72340_a - v, $this$expands.field_72338_b - (modifyYDown ? v : 0.0), $this$expands.field_72339_c - v, $this$expands.field_72336_d + v, $this$expands.field_72337_e + (modifyYUp ? v : 0.0), $this$expands.field_72334_f + v);
    }

    public static /* synthetic */ AxisAlignedBB expands$default(AxisAlignedBB axisAlignedBB, double d, boolean bl, boolean bl2, int n, Object object) {
        if ((n & 2) != 0) {
            bl = true;
        }
        if ((n & 4) != 0) {
            bl2 = true;
        }
        return OtherExtensionKt.expands(axisAlignedBB, d, bl, bl2);
    }

    @NotNull
    public static final List<IBlockState> getBlockStatesIncluded(@NotNull AxisAlignedBB $this$getBlockStatesIncluded) {
        Intrinsics.checkNotNullParameter($this$getBlockStatesIncluded, "<this>");
        LinkedList<IBlockState> tmpArr = new LinkedList<IBlockState>();
        int minX = MathHelper.func_76128_c((double)$this$getBlockStatesIncluded.field_72340_a);
        int minY = MathHelper.func_76128_c((double)$this$getBlockStatesIncluded.field_72338_b);
        int minZ = MathHelper.func_76128_c((double)$this$getBlockStatesIncluded.field_72339_c);
        int maxX = MathHelper.func_76128_c((double)$this$getBlockStatesIncluded.field_72336_d);
        int maxY = MathHelper.func_76128_c((double)$this$getBlockStatesIncluded.field_72337_e);
        int maxZ = MathHelper.func_76128_c((double)$this$getBlockStatesIncluded.field_72334_f);
        Minecraft mc = Minecraft.func_71410_x();
        BlockPos.MutableBlockPos mbp = new BlockPos.MutableBlockPos(minX, minY, minZ);
        int n = minX;
        if (n <= maxX) {
            int x;
            do {
                int y;
                x = n++;
                int n2 = minY;
                if (n2 > maxY) continue;
                do {
                    int z;
                    y = n2++;
                    int n3 = maxZ;
                    if (n3 > maxX) continue;
                    do {
                        z = n3++;
                        mbp.func_181079_c(x, y, z);
                        if (mc.field_71441_e.func_175623_d((BlockPos)mbp)) continue;
                        tmpArr.add(mc.field_71441_e.func_180495_p((BlockPos)mbp));
                    } while (z != maxX);
                } while (y != maxY);
            } while (x != maxX);
        }
        return tmpArr;
    }

    public static final float toRadians(float $this$toRadians) {
        return $this$toRadians * ((float)Math.PI / 180);
    }

    public static final double toRadiansD(float $this$toRadiansD) {
        return OtherExtensionKt.toRadians($this$toRadiansD);
    }
}

