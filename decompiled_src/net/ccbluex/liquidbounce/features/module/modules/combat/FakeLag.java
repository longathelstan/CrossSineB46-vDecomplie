/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.PreUpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.FakeLag;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.BlinkUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="FakeLag", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\n\u0010\u001c\u001a\u0004\u0018\u00010\u0019H\u0002J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0007J\b\u0010!\u001a\u00020\u001eH\u0016J\u0010\u0010\"\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020#H\u0007J\u0010\u0010$\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020%H\u0007J\u0010\u0010&\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020'H\u0007J\b\u0010(\u001a\u00020\u001eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/FakeLag;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attackTick", "", "attackTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "freeze", "freezeMS", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "hurtTime", "lagged", "", "maxRangeValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "minRangeValue", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "target", "Lnet/minecraft/entity/player/EntityPlayer;", "targetPos", "Lnet/minecraft/util/Vec3;", "findTarget", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onDisable", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPreUpdate", "Lnet/ccbluex/liquidbounce/event/PreUpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "reset", "CrossSine"})
public final class FakeLag
extends Module {
    @NotNull
    public static final FakeLag INSTANCE = new FakeLag();
    @NotNull
    private static final ListValue modeValue;
    @NotNull
    private static final IntegerValue freezeMS;
    @NotNull
    private static final FloatValue maxRangeValue;
    @NotNull
    private static final FloatValue minRangeValue;
    private static boolean lagged;
    @NotNull
    private static TimerMS freeze;
    @Nullable
    private static EntityPlayer target;
    @Nullable
    private static Vec3 targetPos;
    private static int hurtTime;
    private static int attackTick;
    @NotNull
    private static final TimerMS attackTimer;

    private FakeLag() {
    }

    @NotNull
    public final ListValue getModeValue() {
        return modeValue;
    }

    @Override
    public void onDisable() {
        if (!Blink.INSTANCE.getState()) {
            this.reset();
            BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
        }
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        EntityPlayer target = (EntityPlayer)event.getTargetEntity();
        if (!Intrinsics.areEqual(target, FakeLag.target)) {
            FakeLag.target = target;
            hurtTime = 10;
        }
        attackTick = 0;
        if (hurtTime == 0) {
            hurtTime = 10;
        }
        attackTimer.reset();
        if (modeValue.equals("DelayAttack") && (double)MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)FakeLag.target) >= 3.2 && !lagged) {
            lagged = true;
            freeze.reset();
        }
    }

    @EventTarget
    public final void onPreUpdate(@NotNull PreUpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (Blink.INSTANCE.getState()) {
            return;
        }
        int n = attackTick;
        attackTick = n + 1;
        if (target == null) {
            target = this.findTarget();
        }
        if (modeValue.equals("Dynamic")) {
            if (target != null && attackTick > 0) {
                if (freeze.hasTimePassed(((Number)freezeMS.get()).intValue()) && lagged) {
                    lagged = false;
                    BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                    target = null;
                    freeze.reset();
                }
                float distance = MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)target);
                float f = ((Number)minRangeValue.get()).floatValue();
                boolean bl = distance <= ((Number)maxRangeValue.get()).floatValue() ? f <= distance : false;
                if (bl && !lagged) {
                    lagged = true;
                    BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, null);
                    freeze.reset();
                }
            } else if (lagged || attackTick == 0) {
                BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                lagged = false;
            }
        } else if (modeValue.equals("Normal")) {
            if (!attackTimer.hasTimePassed(500L) || freeze.hasTimePassed(((Number)freezeMS.get()).intValue()) || MinecraftInstance.mc.field_71439_g.func_70694_bm() != null && (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemFood || MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemPotion && !ItemPotion.func_77831_g((int)MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77960_j()) || MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBucketMilk) && MinecraftInstance.mc.field_71439_g.func_71039_bw()) {
                BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                freeze.reset();
            } else {
                BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, null);
            }
        } else if (modeValue.equals("DelayAttack")) {
            if (lagged) {
                if (!freeze.hasTimePassed(((Number)freezeMS.get()).intValue())) {
                    BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, null);
                } else {
                    BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                    lagged = false;
                }
            }
            if ((double)MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)target) < 2.5 && lagged) {
                BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                lagged = false;
            }
        }
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.reset();
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof S08PacketPlayerPosLook) {
            this.reset();
        }
        if (!event.isCancelled()) {
            Packet<?> packet2 = packet.getClass().getSimpleName();
            Intrinsics.checkNotNullExpressionValue(packet2, "packet.javaClass.simpleName");
            if (StringsKt.startsWith((String)packet2, "S", true)) {
                packet2 = packet;
                if (packet2 instanceof S14PacketEntity) {
                    if (target != null) {
                        int n = ((S14PacketEntity)packet).func_149065_a((World)MinecraftInstance.mc.field_71441_e).func_145782_y();
                        EntityPlayer entityPlayer = target;
                        Intrinsics.checkNotNull(entityPlayer);
                        if (n == entityPlayer.func_145782_y()) {
                            Vec3 vec3 = targetPos;
                            Intrinsics.checkNotNull(vec3);
                            targetPos = vec3.func_72441_c((double)((S14PacketEntity)packet).func_149062_c() / 32.0, (double)((S14PacketEntity)packet).func_149061_d() / 32.0, (double)((S14PacketEntity)packet).func_149064_e() / 32.0);
                        }
                    }
                } else if (packet2 instanceof S18PacketEntityTeleport && target != null) {
                    int n = ((S18PacketEntityTeleport)packet).func_149451_c();
                    EntityPlayer entityPlayer = target;
                    Intrinsics.checkNotNull(entityPlayer);
                    if (n == entityPlayer.func_145782_y()) {
                        targetPos = new Vec3((double)((S18PacketEntityTeleport)packet).func_149449_d() / 32.0, (double)((S18PacketEntityTeleport)packet).func_149448_e() / 32.0, (double)((S18PacketEntityTeleport)packet).func_149446_f() / 32.0);
                    }
                }
            }
        }
        if (packet instanceof S08PacketPlayerPosLook) {
            this.reset();
        }
    }

    private final void reset() {
        lagged = false;
        target = this.findTarget();
        BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
        targetPos = null;
        freeze.reset();
    }

    private final EntityPlayer findTarget() {
        float closetDistance = Float.MAX_VALUE;
        EntityPlayer target = null;
        for (Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
            Intrinsics.checkNotNullExpressionValue(entity, "entity");
            if (!EntityUtils.INSTANCE.isSelected(entity, true)) continue;
            float distance = MinecraftInstance.mc.field_71439_g.func_70032_d(entity);
            if (Intrinsics.areEqual(entity, MinecraftInstance.mc.field_71439_g) || !(entity instanceof EntityPlayer) || !(distance < closetDistance)) continue;
            closetDistance = distance;
            target = (EntityPlayer)entity;
        }
        return target;
    }

    @Override
    @NotNull
    public String getTag() {
        return (lagged ? "\u00a7C " : "") + (String)modeValue.get() + ' ' + ((Number)freezeMS.get()).intValue() + "MS";
    }

    public static final /* synthetic */ FloatValue access$getMinRangeValue$p() {
        return minRangeValue;
    }

    public static final /* synthetic */ FloatValue access$getMaxRangeValue$p() {
        return maxRangeValue;
    }

    static {
        String[] stringArray = new String[]{"Dynamic", "Normal", "DelayAttack"};
        modeValue = new ListValue("Mode", stringArray, "Dynamic");
        freezeMS = new IntegerValue("FreezeMS", 350, 100, 500);
        maxRangeValue = (FloatValue)new FloatValue(){

            protected void onChange(float oldValue, float newValue) {
                if (((Number)FakeLag.access$getMinRangeValue$p().get()).floatValue() > newValue) {
                    this.set(FakeLag.access$getMinRangeValue$p().get());
                }
            }
        }.displayable(maxRangeValue.2.INSTANCE);
        minRangeValue = (FloatValue)new FloatValue(){

            protected void onChange(float oldValue, float newValue) {
                if (((Number)FakeLag.access$getMaxRangeValue$p().get()).floatValue() < newValue) {
                    this.set(FakeLag.access$getMaxRangeValue$p().get());
                }
            }
        }.displayable(minRangeValue.2.INSTANCE);
        freeze = new TimerMS();
        attackTimer = new TimerMS();
    }
}

