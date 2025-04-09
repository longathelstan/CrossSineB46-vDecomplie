/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.BedAura;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.TitleValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.animation.Animation;
import net.ccbluex.liquidbounce.utils.animation.Easing;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.extensions.BlockExtensionKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBed;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="BedAura", category=ModuleCategory.WORLD)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J(\u00108\u001a\u0004\u0018\u00010\"2\b\b\u0002\u00109\u001a\u00020\u001c2\b\b\u0002\u0010:\u001a\u00020\u001c2\b\b\u0002\u0010;\u001a\u00020\u001cH\u0002J\n\u0010<\u001a\u0004\u0018\u00010\"H\u0002J\b\u0010=\u001a\u00020>H\u0016J\b\u0010?\u001a\u00020>H\u0016J\u0012\u0010@\u001a\u00020>2\b\u0010A\u001a\u0004\u0018\u00010BH\u0007J\u0012\u0010C\u001a\u00020>2\b\u0010A\u001a\u0004\u0018\u00010DH\u0007J\b\u0010E\u001a\u00020>H\u0002J\u0010\u0010F\u001a\u00020>2\u0006\u0010G\u001a\u00020\"H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010#\u001a\u0004\u0018\u00010\"X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u000e\u0010(\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00020\u001c0,X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010-\u001a\b\u0012\u0004\u0012\u00020\u001c0,X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001c0,X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u00100\u001a\u0002018VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b2\u00103R\u000e\u00104\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000206X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006H"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/BedAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "animation", "Lnet/ccbluex/liquidbounce/utils/animation/Animation;", "getAnimation", "()Lnet/ccbluex/liquidbounce/utils/animation/Animation;", "setAnimation", "(Lnet/ccbluex/liquidbounce/utils/animation/Animation;)V", "bestSlot", "", "blockHitDelay", "breakSpeed", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "currentDamage", "", "getCurrentDamage", "()F", "setCurrentDamage", "(F)V", "damageRender", "delay", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "getDelay", "()Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "setDelay", "(Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;)V", "hasAir", "", "ignoreGround", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "ignoreSlow", "isRealBlock", "oldPos", "Lnet/minecraft/util/BlockPos;", "pos", "getPos", "()Lnet/minecraft/util/BlockPos;", "setPos", "(Lnet/minecraft/util/BlockPos;)V", "rangeValue", "renderPos", "snapRotation", "spoofItem", "Lnet/ccbluex/liquidbounce/features/value/Value;", "surroundingsValue", "swapValue", "swingValue", "tag", "", "getTag", "()Ljava/lang/String;", "throughWall", "title1", "Lnet/ccbluex/liquidbounce/features/value/TitleValue;", "toolValue", "find", "limit", "head", "foot", "findNearBlock", "onDisable", "", "onEnable", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "setSlot", "tool", "blockPos", "CrossSine"})
public final class BedAura
extends Module {
    @NotNull
    public static final BedAura INSTANCE = new BedAura();
    @NotNull
    private static final FloatValue rangeValue = new FloatValue("Range", 5.0f, 1.0f, 7.0f);
    @NotNull
    private static final BoolValue swingValue = new BoolValue("Swing", false);
    @NotNull
    private static final FloatValue breakSpeed = new FloatValue("BreakSpeed", 1.0f, 1.0f, 2.0f);
    @NotNull
    private static final BoolValue snapRotation = new BoolValue("Snap-Rotation", false);
    @NotNull
    private static final BoolValue ignoreSlow = new BoolValue("IgnoreSlow", false);
    @NotNull
    private static final BoolValue ignoreGround = new BoolValue("IgnoreGround", false);
    @NotNull
    private static final BoolValue throughWall = new BoolValue("ThroughWall", false);
    @NotNull
    private static final Value<Boolean> surroundingsValue = new BoolValue("Surroundings", false).displayable(surroundingsValue.1.INSTANCE);
    @NotNull
    private static final BoolValue toolValue = new BoolValue("AutoTool", false);
    @NotNull
    private static final Value<Boolean> swapValue = new BoolValue("Swap", false).displayable(swapValue.1.INSTANCE);
    @NotNull
    private static final Value<Boolean> spoofItem = new BoolValue("SpoofItem", false).displayable(spoofItem.1.INSTANCE);
    @NotNull
    private static final TitleValue title1 = new TitleValue("Visual :");
    @NotNull
    private static final BoolValue renderPos = new BoolValue("Render-Pos", false);
    @Nullable
    private static BlockPos pos;
    @Nullable
    private static BlockPos oldPos;
    private static int blockHitDelay;
    private static boolean isRealBlock;
    private static float currentDamage;
    private static float damageRender;
    @NotNull
    private static Animation animation;
    @NotNull
    private static TimerMS delay;
    private static int bestSlot;
    private static boolean hasAir;

    private BedAura() {
    }

    @Nullable
    public final BlockPos getPos() {
        return pos;
    }

    public final void setPos(@Nullable BlockPos blockPos) {
        pos = blockPos;
    }

    public final float getCurrentDamage() {
        return currentDamage;
    }

    public final void setCurrentDamage(float f) {
        currentDamage = f;
    }

    @NotNull
    public final Animation getAnimation() {
        return animation;
    }

    public final void setAnimation(@NotNull Animation animation) {
        Intrinsics.checkNotNullParameter(animation, "<set-?>");
        BedAura.animation = animation;
    }

    @NotNull
    public final TimerMS getDelay() {
        return delay;
    }

    public final void setDelay(@NotNull TimerMS timerMS) {
        Intrinsics.checkNotNullParameter(timerMS, "<set-?>");
        delay = timerMS;
    }

    @Override
    public void onEnable() {
        pos = null;
        currentDamage = 0.0f;
        damageRender = 0.0f;
        hasAir = false;
    }

    @Override
    public void onDisable() {
        pos = null;
        currentDamage = 0.0f;
        damageRender = 0.0f;
        hasAir = false;
        if (spoofItem.get().booleanValue()) {
            SlotUtils.INSTANCE.stopSet();
        }
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        Vec3 eyes2;
        MovingObjectPosition rayTraceResult;
        block46: {
            block45: {
                if (pos == null || Block.func_149682_b((Block)BlockUtils.getBlock(pos)) != 26) break block45;
                BlockPos blockPos = pos;
                Intrinsics.checkNotNull(blockPos);
                if (!(BlockUtils.getCenterDistance(blockPos) > (double)((Number)rangeValue.get()).floatValue())) break block46;
            }
            if (currentDamage == 0.0f || currentDamage >= 1.0f) {
                BlockPos blockPos = pos = !hasAir && (Boolean)throughWall.get() != false && surroundingsValue.get() != false ? this.findNearBlock() : BedAura.find$default(this, false, false, false, 7, null);
            }
        }
        if (pos == null) {
            currentDamage = 0.0f;
            return;
        }
        BlockPos blockPos = pos;
        if (blockPos == null) {
            return;
        }
        BlockPos currentPos = blockPos;
        VecRotation vecRotation = RotationUtils.faceBlock(currentPos);
        if (vecRotation == null) {
            return;
        }
        VecRotation rotations = vecRotation;
        if (!((Boolean)snapRotation.get()).booleanValue()) {
            RotationUtils.setTargetRotation(rotations.getRotation(), 1);
        }
        boolean surroundings = false;
        if (!((Boolean)throughWall.get()).booleanValue() && (rayTraceResult = MinecraftInstance.mc.field_71441_e.func_147447_a(eyes2 = MinecraftInstance.mc.field_71439_g.func_174824_e(1.0f), rotations.getVec(), false, false, false)) != null) {
            BlockPos blockPos2 = rayTraceResult.func_178782_a();
            if (blockPos2 == null) {
                return;
            }
            BlockPos blockPos3 = blockPos2;
            Block block = BlockExtensionKt.getBlock(blockPos3);
            if (block instanceof BlockBed) {
                if (!Intrinsics.areEqual(currentPos, blockPos3)) {
                    pos = blockPos3;
                    currentDamage = 0.0f;
                }
                currentPos = blockPos3;
                VecRotation vecRotation2 = RotationUtils.faceBlock(currentPos);
                if (vecRotation2 == null) {
                    return;
                }
                rotations = vecRotation2;
            } else if (!(block instanceof BlockAir)) {
                if (!Intrinsics.areEqual(currentPos, blockPos3)) {
                    surroundings = true;
                    if (currentDamage == 0.0f || currentDamage >= 1.0f) {
                        pos = blockPos3;
                    }
                }
                BlockPos blockPos4 = pos;
                if (blockPos4 == null) {
                    return;
                }
                currentPos = blockPos4;
                VecRotation vecRotation3 = RotationUtils.faceBlock(currentPos);
                if (vecRotation3 == null) {
                    return;
                }
                rotations = vecRotation3;
            }
        }
        if (oldPos != null && !Intrinsics.areEqual(oldPos, currentPos)) {
            currentDamage = 0.0f;
        }
        oldPos = currentPos;
        if (blockHitDelay > 0) {
            int eyes2 = blockHitDelay;
            blockHitDelay = eyes2 + -1;
            return;
        }
        if (((Boolean)throughWall.get()).booleanValue() && surroundingsValue.get().booleanValue()) {
            BlockPos blockPos5 = oldPos;
            Intrinsics.checkNotNull(blockPos5);
            if (!(BlockUtils.getBlock(blockPos5) instanceof BlockAir)) {
                hasAir = false;
            }
        }
        this.tool(currentPos);
        if (surroundings || !isRealBlock) {
            if (((Boolean)toolValue.get()).booleanValue() && !swapValue.get().booleanValue()) {
                this.setSlot();
            }
            Block block = BlockExtensionKt.getBlock(currentPos);
            if (block == null) {
                return;
            }
            Block block2 = block;
            if (!(currentDamage == 0.0f) && !(currentDamage == 1.0f)) {
                delay.reset();
            }
            if (currentDamage == 0.0f) {
                damageRender = 0.0f;
                if (((Boolean)toolValue.get()).booleanValue() && swapValue.get().booleanValue()) {
                    this.setSlot();
                }
                if (((Boolean)snapRotation.get()).booleanValue()) {
                    RotationUtils.setTargetRotation(rotations.getRotation(), 1);
                }
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, currentPos, EnumFacing.DOWN));
                SlotUtils.INSTANCE.stopSet();
                if (MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75098_d || block2.func_180647_a((EntityPlayer)MinecraftInstance.mc.field_71439_g, (World)MinecraftInstance.mc.field_71441_e, pos) >= 1.0f) {
                    if (((Boolean)swingValue.get()).booleanValue()) {
                        PlayerUtils.INSTANCE.swing();
                    }
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0APacketAnimation());
                    MinecraftInstance.mc.field_71442_b.func_178888_a(pos, EnumFacing.DOWN);
                    if (Block.func_149682_b((Block)BlockUtils.getBlock(currentPos)) == 26) {
                        damageRender = 0.0f;
                    }
                    currentDamage = 0.0f;
                    pos = null;
                    return;
                }
            }
            if (((Boolean)swingValue.get()).booleanValue()) {
                PlayerUtils.INSTANCE.swing();
            }
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0APacketAnimation());
            Block block3 = BlockUtils.getBlock(currentPos);
            Intrinsics.checkNotNull(block3);
            ItemStack itemStack = (Boolean)toolValue.get() != false && swapValue.get() != false && bestSlot != -1 ? MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(bestSlot) : MinecraftInstance.mc.field_71439_g.func_70694_bm();
            Block block4 = BlockUtils.getBlock(currentPos);
            Intrinsics.checkNotNull(block4);
            damageRender += BlockUtils.INSTANCE.getBlockHardness(block4, (Boolean)toolValue.get() != false && swapValue.get() != false && bestSlot != -1 ? MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(bestSlot) : MinecraftInstance.mc.field_71439_g.func_70694_bm(), (Boolean)ignoreSlow.get(), (Boolean)ignoreGround.get()) * ((Number)breakSpeed.get()).floatValue();
            MinecraftInstance.mc.field_71441_e.func_175715_c(MinecraftInstance.mc.field_71439_g.func_145782_y(), currentPos, (int)((currentDamage += BlockUtils.INSTANCE.getBlockHardness(block3, itemStack, (Boolean)ignoreSlow.get(), (Boolean)ignoreGround.get()) * ((Number)breakSpeed.get()).floatValue()) * 10.0f) - 1);
            if (currentDamage >= 1.0f) {
                if (((Boolean)snapRotation.get()).booleanValue()) {
                    RotationUtils.setTargetRotation(rotations.getRotation(), 1);
                }
                if (((Boolean)toolValue.get()).booleanValue() && swapValue.get().booleanValue()) {
                    this.setSlot();
                }
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, currentPos, EnumFacing.DOWN));
                SlotUtils.INSTANCE.stopSet();
                if (surroundingsValue.get().booleanValue() && ((Boolean)throughWall.get()).booleanValue()) {
                    if (Block.func_149682_b((Block)BlockUtils.getBlock(currentPos)) != 26 && !hasAir) {
                        hasAir = true;
                    }
                    if (Block.func_149682_b((Block)BlockUtils.getBlock(currentPos)) == 26 && hasAir) {
                        hasAir = false;
                    }
                }
                MinecraftInstance.mc.field_71442_b.func_178888_a(currentPos, EnumFacing.DOWN);
                blockHitDelay = 4;
                currentDamage = 0.0f;
                if (Block.func_149682_b((Block)BlockUtils.getBlock(currentPos)) == 26) {
                    damageRender = 0.0f;
                    BedAura.animation.value = damageRender;
                }
                pos = null;
            }
        } else if (MinecraftInstance.mc.field_71442_b.func_178890_a(MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71441_e, MinecraftInstance.mc.field_71439_g.func_70694_bm(), pos, EnumFacing.DOWN, new Vec3((double)currentPos.func_177958_n(), (double)currentPos.func_177956_o(), (double)currentPos.func_177952_p()))) {
            if (((Boolean)swingValue.get()).booleanValue()) {
                PlayerUtils.INSTANCE.swing();
            }
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0APacketAnimation());
            blockHitDelay = 4;
            currentDamage = 0.0f;
            pos = null;
        }
    }

    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent event) {
        animation.run(damageRender);
        BedAura.animation.value = RangesKt.coerceIn(BedAura.animation.value, 0.0, 1.0);
        if (pos != null && ((Boolean)renderPos.get()).booleanValue()) {
            GlStateManager.func_179094_E();
            BlockPos blockPos = pos;
            Intrinsics.checkNotNull(blockPos);
            RenderUtils.drawBlockBox(blockPos, ClientTheme.getColor$default(ClientTheme.INSTANCE, 0, false, 3, null), true, false, 2.0f, (float)BedAura.animation.value);
            GlStateManager.func_179117_G();
            GlStateManager.func_179121_F();
        }
    }

    private final BlockPos find(boolean limit, boolean head, boolean foot) {
        int radius = (int)((Number)rangeValue.get()).floatValue() + 1;
        BlockPos nearestBlock = null;
        double closestBed = Double.MAX_VALUE;
        int n = -radius;
        if (n <= radius) {
            int x;
            do {
                int y;
                x = n++;
                int n2 = -radius;
                if (n2 > radius) continue;
                do {
                    int z;
                    y = n2++;
                    int n3 = -radius;
                    if (n3 > radius) continue;
                    do {
                        Block block;
                        BlockPos blockPos;
                        if (BlockUtils.getBlock(blockPos = new BlockPos((int)MinecraftInstance.mc.field_71439_g.field_70165_t + x, (int)MinecraftInstance.mc.field_71439_g.field_70163_u + y, (int)MinecraftInstance.mc.field_71439_g.field_70161_v + (z = n3++))) == null) continue;
                        double distance = MinecraftInstance.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n() + 0.5, (double)blockPos.func_177956_o() + 0.5, (double)blockPos.func_177952_p() + 0.5);
                        if (Block.func_149682_b((Block)block) != 26) continue;
                        if (limit) {
                            if (MinecraftInstance.mc.field_71441_e.func_180495_p(blockPos).func_177229_b((IProperty)BlockBed.field_176472_a) == BlockBed.EnumPartType.HEAD && head) {
                                if (!(distance < closestBed)) continue;
                                closestBed = distance;
                                nearestBlock = blockPos;
                                continue;
                            }
                            if (MinecraftInstance.mc.field_71441_e.func_180495_p(blockPos).func_177229_b((IProperty)BlockBed.field_176472_a) != BlockBed.EnumPartType.FOOT || !foot || !(distance < closestBed)) continue;
                            closestBed = distance;
                            nearestBlock = blockPos;
                            continue;
                        }
                        if (!(distance < closestBed)) continue;
                        closestBed = distance;
                        nearestBlock = blockPos;
                    } while (z != radius);
                } while (y != radius);
            } while (x != radius);
        }
        return nearestBlock;
    }

    static /* synthetic */ BlockPos find$default(BedAura bedAura, boolean bl, boolean bl2, boolean bl3, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        if ((n & 2) != 0) {
            bl2 = false;
        }
        if ((n & 4) != 0) {
            bl3 = false;
        }
        return bedAura.find(bl, bl2, bl3);
    }

    private final BlockPos findNearBlock() {
        BlockPos closestPos = null;
        double closestDistance = Double.MAX_VALUE;
        BlockPos blockPos = BedAura.find$default(this, false, false, false, 7, null);
        if (blockPos == null) {
            return null;
        }
        BlockPos bedPos = blockPos;
        BlockPos blockPos2 = this.find(true, true, false);
        if (blockPos2 == null) {
            return null;
        }
        BlockPos bedPosHead = blockPos2;
        BlockPos blockPos3 = this.find(true, false, true);
        if (blockPos3 == null) {
            return null;
        }
        BlockPos bedPosFoot = blockPos3;
        for (EnumFacing direction : EnumFacing.values()) {
            if (direction == EnumFacing.DOWN) continue;
            BlockPos targetPos = bedPos.func_177972_a(direction);
            BlockPos targetPosHead = bedPosHead.func_177972_a(direction);
            BlockPos targetPosFoot = bedPosFoot.func_177972_a(direction);
            if (BlockUtils.getBlock(targetPosHead) instanceof BlockAir) {
                hasAir = true;
                return null;
            }
            if (!(BlockUtils.getBlock(targetPosFoot) instanceof BlockAir)) continue;
            hasAir = true;
            return null;
        }
        return closestPos;
    }

    private final void tool(BlockPos blockPos) {
        float bestSpeed = 1.0f;
        Block block = MinecraftInstance.mc.field_71441_e.func_180495_p(blockPos).func_177230_c();
        int n = 0;
        while (n < 9) {
            ItemStack item;
            float speed;
            int i;
            if (MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i = n++) == null || !((speed = item.func_150997_a(block)) > bestSpeed)) continue;
            bestSpeed = speed;
            bestSlot = i;
        }
    }

    private final void setSlot() {
        if (bestSlot != -1) {
            SlotUtils.INSTANCE.setSlot(bestSlot, spoofItem.get(), this.getName());
        }
    }

    @Override
    @NotNull
    public String getTag() {
        return swapValue.get() != false ? "Swap" : ((Boolean)throughWall.get() != false ? "Blatant" : "Legit");
    }

    public static final /* synthetic */ BoolValue access$getThroughWall$p() {
        return throughWall;
    }

    public static final /* synthetic */ BoolValue access$getToolValue$p() {
        return toolValue;
    }

    static {
        animation = new Animation(Easing.LINEAR, 40L);
        delay = new TimerMS();
        bestSlot = -1;
    }
}

