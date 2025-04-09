/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.modules.visual.Animations;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSlime;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001c\u001a\u00020\u001bH\u0007J\u000e\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u0004J\u001e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020\"J\r\u0010%\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0002\u0010&J%\u0010'\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010(2\u0006\u0010)\u001a\u00020\"2\u0006\u0010*\u001a\u00020\"\u00a2\u0006\u0002\u0010+J\u0006\u0010,\u001a\u00020\u001bJ\u0010\u0010-\u001a\u00020.2\b\b\u0002\u0010/\u001a\u000200J\u0006\u00101\u001a\u00020.R$\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0014\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR$\u0010\n\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0014\n\u0000\u0012\u0004\b\u000b\u0010\u0002\u001a\u0004\b\f\u0010\u0007\"\u0004\b\r\u0010\tR$\u0010\u000e\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0014\n\u0000\u0012\u0004\b\u000f\u0010\u0002\u001a\u0004\b\u0010\u0010\u0007\"\u0004\b\u0011\u0010\tR$\u0010\u0012\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0014\n\u0000\u0012\u0004\b\u0013\u0010\u0002\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\tR$\u0010\u0016\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0014\n\u0000\u0012\u0004\b\u0017\u0010\u0002\u001a\u0004\b\u0018\u0010\u0007\"\u0004\b\u0019\u0010\t\u00a8\u00062"}, d2={"Lnet/ccbluex/liquidbounce/utils/PlayerUtils;", "", "()V", "groundTicks", "", "getGroundTicks$annotations", "getGroundTicks", "()I", "setGroundTicks", "(I)V", "lastGroundTicks", "getLastGroundTicks$annotations", "getLastGroundTicks", "setLastGroundTicks", "lastOffGroundTicks", "getLastOffGroundTicks$annotations", "getLastOffGroundTicks", "setLastOffGroundTicks", "offGroundTicks", "getOffGroundTicks$annotations", "getOffGroundTicks", "setOffGroundTicks", "sinceTeleportTicks", "getSinceTeleportTicks$annotations", "getSinceTeleportTicks", "setSinceTeleportTicks", "BlockUnderPlayerIsEmpty", "", "NotNull", "blockNear", "range", "blockRelativeToPlayer", "Lnet/minecraft/block/Block;", "offsetX", "", "offsetY", "offsetZ", "findSlimeBlock", "()Ljava/lang/Integer;", "getEntity", "", "distance", "expand", "(DD)[Ljava/lang/Object;", "isOnEdge", "setCorrectBlockPos", "", "speed", "", "swing", "CrossSine"})
public final class PlayerUtils {
    @NotNull
    public static final PlayerUtils INSTANCE = new PlayerUtils();
    private static int offGroundTicks;
    private static int groundTicks;
    private static int lastOffGroundTicks;
    private static int lastGroundTicks;
    private static int sinceTeleportTicks;

    private PlayerUtils() {
    }

    public static final int getOffGroundTicks() {
        return offGroundTicks;
    }

    public static final void setOffGroundTicks(int n) {
        offGroundTicks = n;
    }

    @JvmStatic
    public static /* synthetic */ void getOffGroundTicks$annotations() {
    }

    public static final int getGroundTicks() {
        return groundTicks;
    }

    public static final void setGroundTicks(int n) {
        groundTicks = n;
    }

    @JvmStatic
    public static /* synthetic */ void getGroundTicks$annotations() {
    }

    public static final int getLastOffGroundTicks() {
        return lastOffGroundTicks;
    }

    public static final void setLastOffGroundTicks(int n) {
        lastOffGroundTicks = n;
    }

    @JvmStatic
    public static /* synthetic */ void getLastOffGroundTicks$annotations() {
    }

    public static final int getLastGroundTicks() {
        return lastGroundTicks;
    }

    public static final void setLastGroundTicks(int n) {
        lastGroundTicks = n;
    }

    @JvmStatic
    public static /* synthetic */ void getLastGroundTicks$annotations() {
    }

    public static final int getSinceTeleportTicks() {
        return sinceTeleportTicks;
    }

    public static final void setSinceTeleportTicks(int n) {
        sinceTeleportTicks = n;
    }

    @JvmStatic
    public static /* synthetic */ void getSinceTeleportTicks$annotations() {
    }

    @NotNull
    public final Block blockRelativeToPlayer(double offsetX, double offsetY, double offsetZ) {
        Block block = BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t + offsetX, MinecraftInstance.mc.field_71439_g.field_70163_u + offsetY, MinecraftInstance.mc.field_71439_g.field_70161_v + offsetZ));
        Intrinsics.checkNotNull(block);
        return block;
    }

    public final boolean blockNear(int range) {
        int n = -range;
        if (n <= range) {
            int x;
            do {
                int y;
                x = n++;
                int n2 = -range;
                if (n2 > range) continue;
                do {
                    int z;
                    y = n2++;
                    int n3 = -range;
                    if (n3 > range) continue;
                    do {
                        Block block;
                        BlockAir blockAir = (block = this.blockRelativeToPlayer(x, y, z = n3++)) instanceof BlockAir ? (BlockAir)block : null;
                        if (blockAir == null) {
                            return true;
                        }
                        Block block2 = (Block)blockAir;
                    } while (z != range);
                } while (y != range);
            } while (x != range);
        }
        return false;
    }

    @Nullable
    public final Integer findSlimeBlock() {
        int n = 0;
        while (n < 9) {
            int i;
            ItemStack itemStack;
            if ((itemStack = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i = n++)) == null || itemStack.func_77973_b() == null || !(itemStack.func_77973_b() instanceof ItemBlock)) continue;
            Item item = itemStack.func_77973_b();
            if (item == null) {
                throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemBlock");
            }
            ItemBlock block = (ItemBlock)item;
            if (!(block.field_150939_a instanceof BlockSlime)) continue;
            return i;
        }
        return -1;
    }

    public final void swing() {
        EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
        EntityPlayerSP player = entityPlayerSP;
        float swingEnd = (float)(player.func_70644_a(Potion.field_76422_e) ? 6 - (1 + player.func_70660_b(Potion.field_76422_e).func_76458_c()) : (player.func_70644_a(Potion.field_76419_f) ? 6 + (1 + player.func_70660_b(Potion.field_76419_f).func_76458_c()) * 2 : 6)) * (Animations.INSTANCE.getState() ? ((Number)Animations.INSTANCE.getSwingSpeedValue().get()).floatValue() : 1.0f);
        if (!player.field_82175_bq || (float)player.field_110158_av >= swingEnd / (float)2 || player.field_110158_av < 0) {
            player.field_110158_av = -1;
            player.field_82175_bq = true;
        }
    }

    public final void setCorrectBlockPos(float speed) {
        double x = MinecraftInstance.mc.field_71439_g.field_70165_t;
        double z = MinecraftInstance.mc.field_71439_g.field_70161_v;
        double targetX = Math.floor(x) + 0.5;
        double targetZ = Math.floor(z) + 0.5;
        double deltaX = targetX - x;
        double deltaZ = targetZ - z;
        double threshold = 0.01;
        if (Math.abs(deltaX) > threshold || Math.abs(deltaZ) > threshold) {
            MinecraftInstance.mc.field_71439_g.field_70159_w = deltaX * (double)speed;
            MinecraftInstance.mc.field_71439_g.field_70179_y = deltaZ * (double)speed;
        } else {
            MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
        }
    }

    public static /* synthetic */ void setCorrectBlockPos$default(PlayerUtils playerUtils, float f, int n, Object object) {
        if ((n & 1) != 0) {
            f = 1.0f;
        }
        playerUtils.setCorrectBlockPos(f);
    }

    public final boolean isOnEdge() {
        return MinecraftInstance.mc.field_71439_g.field_70122_E && !MinecraftInstance.mc.field_71439_g.func_70093_af() && !MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d() && !MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d() && MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, -0.5, 0.0).func_72314_b(-0.001, 0.0, -0.001)).isEmpty();
    }

    public final boolean BlockUnderPlayerIsEmpty() {
        return MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(MinecraftInstance.mc.field_71439_g.field_70159_w / 3.5, -1.0, MinecraftInstance.mc.field_71439_g.field_70179_y / 3.5)).isEmpty();
    }

    @Nullable
    public final Object[] getEntity(double distance, double expand) {
        Entity var2 = MinecraftInstance.mc.func_175606_aa();
        Entity entity = null;
        if (var2 != null && MinecraftInstance.mc.field_71441_e != null) {
            double var3;
            MinecraftInstance.mc.field_71424_I.func_76320_a("pick");
            double var5 = var3 = distance;
            Vec3 vec3 = var2.func_174824_e(0.0f);
            Intrinsics.checkNotNullExpressionValue(vec3, "var2.getPositionEyes(0.0f)");
            Vec3 var7 = vec3;
            Vec3 vec32 = var2.func_70676_i(0.0f);
            Intrinsics.checkNotNullExpressionValue(vec32, "var2.getLook(0.0f)");
            Vec3 var8 = vec32;
            Vec3 vec33 = var7.func_72441_c(var8.field_72450_a * var3, var8.field_72448_b * var3, var8.field_72449_c * var3);
            Intrinsics.checkNotNullExpressionValue(vec33, "var7.addVector(var8.xCoo\u2026var3, var8.zCoord * var3)");
            Vec3 var9 = vec33;
            Vec3 var10 = null;
            float var11 = 1.0f;
            List list = MinecraftInstance.mc.field_71441_e.func_72839_b(var2, var2.func_174813_aQ().func_72321_a(var8.field_72450_a * var3, var8.field_72448_b * var3, var8.field_72449_c * var3).func_72314_b((double)var11, (double)var11, (double)var11));
            Intrinsics.checkNotNullExpressionValue(list, "mc.theWorld.getEntitiesW\u2026toDouble())\n            )");
            List var12 = list;
            double var13 = var5;
            int n = 0;
            int n2 = var12.size();
            while (n < n2) {
                double var20;
                int var15;
                Entity var16;
                if (!(var16 = (Entity)var12.get(var15 = n++)).func_70067_L()) continue;
                float var17 = var16.func_70111_Y();
                AxisAlignedBB axisAlignedBB = var16.func_174813_aQ().func_72314_b((double)var17, (double)var17, (double)var17);
                Intrinsics.checkNotNullExpressionValue(axisAlignedBB, "var16.entityBoundingBox.\u2026uble(), var17.toDouble())");
                AxisAlignedBB var18 = axisAlignedBB;
                axisAlignedBB = var18.func_72314_b(expand, expand, expand);
                Intrinsics.checkNotNullExpressionValue(axisAlignedBB, "var18.expand(expand, expand, expand)");
                var18 = axisAlignedBB;
                MovingObjectPosition var19 = var18.func_72327_a(var7, var9);
                if (var18.func_72318_a(var7)) {
                    Vec3 vec34;
                    if (!(0.0 < var13) && !(var13 == 0.0)) continue;
                    entity = var16;
                    MovingObjectPosition movingObjectPosition = var19;
                    if (movingObjectPosition == null) {
                        vec34 = var7;
                    } else {
                        vec34 = movingObjectPosition.field_72307_f;
                        if (vec34 == null) {
                            vec34 = var7;
                        }
                    }
                    var10 = vec34;
                    var13 = 0.0;
                    continue;
                }
                if (var19 == null || !((var20 = var7.func_72438_d(var19.field_72307_f)) < var13) && !(var13 == 0.0)) continue;
                if (var16 == var2.field_70154_o && !var2.canRiderInteract()) {
                    if (!(var13 == 0.0)) continue;
                    entity = var16;
                    var10 = var19.field_72307_f;
                    continue;
                }
                entity = var16;
                var10 = var19.field_72307_f;
                var13 = var20;
            }
            if (var13 < var5 && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
                entity = null;
            }
            MinecraftInstance.mc.field_71424_I.func_76319_b();
            if (entity == null || var10 == null) {
                return null;
            }
            Object[] objectArray = new Object[]{entity, var10};
            return objectArray;
        }
        return null;
    }

    @JvmStatic
    public static final boolean NotNull() {
        return MinecraftInstance.mc.field_71439_g != null && MinecraftInstance.mc.field_71441_e != null;
    }
}

