/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.PreUpdateEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.BlockHit;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.BlinkUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="BlockHit", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0007J\u0010\u0010(\u001a\u00020%2\u0006\u0010&\u001a\u00020)H\u0007J\u0010\u0010*\u001a\u00020%2\u0006\u0010&\u001a\u00020+H\u0007J\u0010\u0010,\u001a\u00020%2\u0006\u0010&\u001a\u00020-H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00040\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\u00020\u001d8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/BlockHit;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blocked", "", "canBlock", "getCanBlock", "()Z", "setCanBlock", "(Z)V", "chanceValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "clickBlock", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "cpsValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "hitCount", "hurtTime", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "msValue", "onEntity", "onSA", "onlyCombo", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "packetLag", "rightDown", "tag", "", "getTag", "()Ljava/lang/String;", "target", "Lnet/minecraft/entity/EntityLivingBase;", "timerMS", "unBlocked", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPreUpdate", "Lnet/ccbluex/liquidbounce/event/PreUpdateEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"})
public final class BlockHit
extends Module {
    @NotNull
    public static final BlockHit INSTANCE = new BlockHit();
    @NotNull
    private static final ListValue modeValue;
    @NotNull
    private static final Value<Integer> msValue;
    @NotNull
    private static final Value<Boolean> packetLag;
    @NotNull
    private static final Value<Boolean> rightDown;
    @NotNull
    private static final Value<Boolean> onSA;
    @NotNull
    private static final Value<Boolean> onEntity;
    @NotNull
    private static final Value<Integer> cpsValue;
    @NotNull
    private static final BoolValue onlyCombo;
    @NotNull
    private static final IntegerValue chanceValue;
    private static boolean canBlock;
    private static boolean blocked;
    private static boolean unBlocked;
    private static int hurtTime;
    @NotNull
    private static final TimerMS timerMS;
    @NotNull
    private static final TimerMS clickBlock;
    @Nullable
    private static EntityLivingBase target;
    private static int hitCount;

    private BlockHit() {
    }

    public final boolean getCanBlock() {
        return canBlock;
    }

    public final void setCanBlock(boolean bl) {
        canBlock = bl;
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (EntityUtils.INSTANCE.isSelected(event.getTargetEntity(), true)) {
            target = (EntityLivingBase)event.getTargetEntity();
            if (hurtTime == 0) {
                hurtTime = 10;
                int n = hitCount;
                hitCount = n + 1;
            }
            if (modeValue.equals("Lag") && !canBlock) {
                canBlock = true;
                timerMS.reset();
            }
        }
    }

    @EventTarget
    public final void onPreUpdate(@NotNull PreUpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (hurtTime > 0) {
            hurtTime += -1;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70737_aN >= 9) {
            hitCount = 0;
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)packet).func_149412_c() == MinecraftInstance.mc.field_71439_g.func_145782_y()) {
            hitCount = 0;
        }
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.func_70694_bm() == null || !(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword)) {
            MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
            MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
            canBlock = false;
            return;
        }
        if (!MinecraftInstance.mc.field_71474_y.field_74312_F.func_151470_d() || SilentAura.INSTANCE.getState() && SilentAura.INSTANCE.getTarget() == null) {
            MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
            MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
            canBlock = false;
            return;
        }
        if (((Boolean)onlyCombo.get()).booleanValue() && hitCount < 2) {
            MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
            MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
            canBlock = false;
            return;
        }
        if (RandomUtils.nextInt(1, 100) > ((Number)chanceValue.get()).intValue()) {
            MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
            MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
            canBlock = false;
            return;
        }
        if (modeValue.equals("Predict")) {
            if (target != null) {
                float adjustedAngle;
                float playerYaw = MinecraftInstance.mc.field_71439_g.field_70177_z;
                EntityLivingBase entityLivingBase = target;
                Intrinsics.checkNotNull(entityLivingBase);
                float targetYaw = entityLivingBase.field_70177_z;
                float angleDiff = Math.abs(playerYaw - targetYaw) % (float)360;
                float f = adjustedAngle = angleDiff > 180.0f ? (float)360 - angleDiff : angleDiff;
                if (adjustedAngle < 90.0f) {
                    MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                    MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
                    canBlock = false;
                    return;
                }
            }
            if (hurtTime >= RandomUtils.nextInt(8, 9)) {
                MouseUtils.INSTANCE.setRightClicked(true);
                MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = true;
            } else {
                MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
                MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
            }
        }
        if (modeValue.equals("Manual")) {
            if (MinecraftInstance.mc.field_71462_r != null) {
                return;
            }
            if (MinecraftInstance.mc.field_71439_g.func_70694_bm() == null || !(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword)) {
                MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
                canBlock = false;
                return;
            }
            if (rightDown.get().booleanValue() && !GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G)) {
                MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
                canBlock = false;
                return;
            }
            if (!rightDown.get().booleanValue() && GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G)) {
                MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
                canBlock = false;
                return;
            }
            if (!MinecraftInstance.mc.field_71474_y.field_74312_F.func_151470_d() && !onSA.get().booleanValue()) {
                MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
                canBlock = false;
                return;
            }
            if (onSA.get().booleanValue() && SilentAura.INSTANCE.getState() && SilentAura.INSTANCE.getTarget() == null) {
                MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
                canBlock = false;
                return;
            }
            if (onEntity.get().booleanValue() && (MinecraftInstance.mc.field_71476_x == null || MinecraftInstance.mc.field_71476_x.field_72308_g == null)) {
                MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
                canBlock = false;
                return;
            }
            if (RandomUtils.nextInt(0, 100) > ((Number)chanceValue.get()).intValue()) {
                MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
                canBlock = false;
                return;
            }
            if (rightDown.get().booleanValue() && GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G)) {
                MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = false;
            }
            if (timerMS.hasTimePassed(TimeUtils.INSTANCE.randomClickDelay(((Number)cpsValue.get()).intValue(), ((Number)cpsValue.get()).intValue()))) {
                MouseUtils.INSTANCE.setRightClicked(true);
                canBlock = true;
                KeyBinding.func_74507_a((int)MinecraftInstance.mc.field_71474_y.field_74313_G.func_151463_i());
                timerMS.reset();
            } else {
                MouseUtils.INSTANCE.setRightClicked(false);
            }
        }
        if (modeValue.equals("Lag")) {
            if (MinecraftInstance.mc.field_71462_r != null) {
                return;
            }
            if (MinecraftInstance.mc.field_71439_g.func_70694_bm() == null || !(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword)) {
                MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
                if (canBlock) {
                    BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                    canBlock = false;
                }
                if (blocked) {
                    if (packetLag.get().booleanValue()) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
                    } else {
                        MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                    }
                    blocked = false;
                }
                return;
            }
            if (GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G)) {
                MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
                if (canBlock) {
                    BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                    canBlock = false;
                }
                if (blocked) {
                    if (packetLag.get().booleanValue()) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
                    } else {
                        MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                    }
                    blocked = false;
                }
                return;
            }
            if (RandomUtils.nextInt(0, 100) > ((Number)chanceValue.get()).intValue()) {
                MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
                if (canBlock) {
                    BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                    canBlock = false;
                }
                if (blocked) {
                    if (packetLag.get().booleanValue()) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
                    } else {
                        MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                    }
                    blocked = false;
                }
                return;
            }
            if (canBlock) {
                if (!timerMS.hasTimePassed(((Number)msValue.get()).intValue()) && !blocked) {
                    if (packetLag.get().booleanValue()) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g()));
                    } else {
                        MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = true;
                    }
                    BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, null);
                    clickBlock.reset();
                    blocked = true;
                } else if (!timerMS.hasTimePassed(((Number)msValue.get()).intValue()) && blocked && !unBlocked && (packetLag.get().booleanValue() || clickBlock.hasTimePassed(20L))) {
                    if (packetLag.get().booleanValue()) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
                    } else {
                        MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                    }
                    unBlocked = true;
                } else if (timerMS.hasTimePassed(((Number)msValue.get()).intValue()) && blocked && unBlocked) {
                    BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                    blocked = false;
                    canBlock = false;
                    unBlocked = false;
                }
            }
        }
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)modeValue.get() + (modeValue.equals("Lag") ? "" + ' ' + ((Number)msValue.get()).intValue() + "MS" : "") + ' ' + ((Number)chanceValue.get()).intValue() + '%';
    }

    public static final /* synthetic */ void access$setHurtTime$p(int n) {
        hurtTime = n;
    }

    public static final /* synthetic */ ListValue access$getModeValue$p() {
        return modeValue;
    }

    static {
        String[] stringArray = new String[]{"Manual", "Predict", "Lag"};
        String[] stringArray2 = stringArray;
        modeValue = new ListValue(stringArray2){

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
                Intrinsics.checkNotNullParameter(oldValue, "oldValue");
                Intrinsics.checkNotNullParameter(newValue, "newValue");
                BlockHit.access$setHurtTime$p(0);
                BlockHit.INSTANCE.setCanBlock(false);
            }
        };
        msValue = new IntegerValue("Lag-MS", 100, 1, 1500).displayable(msValue.1.INSTANCE);
        packetLag = new BoolValue("Lag-Packet", false).displayable(packetLag.1.INSTANCE);
        rightDown = new BoolValue("onRightDown", false).displayable(rightDown.1.INSTANCE);
        onSA = new BoolValue("AllowSilentAura", false).displayable(onSA.1.INSTANCE);
        onEntity = new BoolValue("OnLookingEntity", false).displayable(onEntity.1.INSTANCE);
        cpsValue = new IntegerValue("CPS", 15, 1, 20).displayable(cpsValue.1.INSTANCE);
        onlyCombo = new BoolValue("OnlyCombo", false);
        chanceValue = new IntegerValue("Chance", 100, 1, 100);
        timerMS = new TimerMS();
        clickBlock = new TimerMS();
    }
}

