/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PreUpdateEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.HitSelect;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.module.modules.movement.MovementFix;
import net.ccbluex.liquidbounce.features.module.modules.movement.TargetStrafe;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.WorldSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ModuleInfo(name="SilentAura", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010N\u001a\u00020OH\u0002J\u0006\u0010P\u001a\u00020QJ\u0010\u0010R\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010I\u001a\u00020OH\u0002J\u0010\u0010S\u001a\u00020O2\u0006\u0010T\u001a\u00020UH\u0007J\b\u0010V\u001a\u00020OH\u0016J\b\u0010W\u001a\u00020OH\u0016J\u0010\u0010X\u001a\u00020O2\u0006\u0010T\u001a\u00020YH\u0007J\u0010\u0010Z\u001a\u00020O2\u0006\u0010T\u001a\u00020[H\u0007J\u0010\u0010\\\u001a\u00020O2\u0006\u0010T\u001a\u00020]H\u0007J\b\u0010^\u001a\u00020OH\u0002J\b\u0010_\u001a\u00020OH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0015\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u0017R\u0011\u0010\u001b\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020'0&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00020 0\u001fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020$X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020$X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00104\u001a\u000201X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000201X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000201X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u00107\u001a\b\u0012\u0004\u0012\u00020\r0\u001fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u00109\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010\u001dR\u000e\u0010;\u001a\u00020$X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020$X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u000201X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010>\u001a\b\u0012\u0004\u0012\u00020?0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010A\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020CX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u000201X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010E\u001a\u0004\u0018\u00010?8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bF\u0010GR\u001c\u0010H\u001a\u0004\u0018\u00010 X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bI\u0010J\"\u0004\bK\u0010LR\u0014\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006`"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/SilentAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "LastBlock", "", "alwaysBlock", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "attackMode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "attacked", "autoBlock", "autoBlockTime", "", "blockDelay", "blockRangeValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "blockTime", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "blockingStatus", "bypassTick", "canBlock", "getCanBlock", "()Z", "canSwing", "cancelAttack", "getCancelAttack", "discoverValue", "getDiscoverValue", "()Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "discoveredTargets", "", "Lnet/minecraft/entity/EntityLivingBase;", "entity", "Lnet/minecraft/entity/Entity;", "fovValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "getAABB", "Lkotlin/Function1;", "Lnet/minecraft/util/AxisAlignedBB;", "hitable", "hitableCheck", "hurtTime", "inRangeDiscoveredTargets", "lastCanBeSeen", "leftDelay", "leftLastSwing", "lessFix", "markValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "maxCPSValue", "minCPSValue", "movementFix", "onlyClick", "onlyWeapon", "prevTargetEntities", "random", "reachValue", "getReachValue", "rotationMaxSpeed", "rotationMinSpeed", "scaffoldCheck", "swingMode", "", "swingRange", "switchDelay", "switchTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "switchValue", "tag", "getTag", "()Ljava/lang/String;", "target", "getTarget", "()Lnet/minecraft/entity/EntityLivingBase;", "setTarget", "(Lnet/minecraft/entity/EntityLivingBase;)V", "whileMoving", "bypassBlock", "", "getReach", "", "getRot", "onAttack", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onDisable", "onEnable", "onPreUpdate", "Lnet/ccbluex/liquidbounce/event/PreUpdateEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "stopBlocking", "updateHitable", "CrossSine"})
public final class SilentAura
extends Module {
    @NotNull
    public static final SilentAura INSTANCE = new SilentAura();
    @NotNull
    private static final BoolValue onlyWeapon = new BoolValue("Only-Weapon", true);
    @NotNull
    private static final BoolValue onlyClick = new BoolValue("Mouse-Down", false);
    @NotNull
    private static final ListValue attackMode;
    @NotNull
    private static final Value<Boolean> hitableCheck;
    @NotNull
    private static final Value<String> swingMode;
    @NotNull
    private static final ListValue autoBlock;
    @NotNull
    private static final Value<Boolean> whileMoving;
    @NotNull
    private static final Value<Boolean> alwaysBlock;
    @NotNull
    private static final Value<Integer> bypassTick;
    @NotNull
    private static final Value<Integer> autoBlockTime;
    @NotNull
    private static final BoolValue switchValue;
    @NotNull
    private static final BoolValue scaffoldCheck;
    @NotNull
    private static final BoolValue markValue;
    @NotNull
    private static final BoolValue movementFix;
    @NotNull
    private static final Value<Boolean> lessFix;
    @NotNull
    private static final Value<Integer> switchDelay;
    @NotNull
    private static final FloatValue reachValue;
    @NotNull
    private static final FloatValue swingRange;
    @NotNull
    private static final FloatValue discoverValue;
    @NotNull
    private static final FloatValue blockRangeValue;
    @NotNull
    private static final IntegerValue fovValue;
    @NotNull
    private static final IntegerValue maxCPSValue;
    @NotNull
    private static final IntegerValue minCPSValue;
    @NotNull
    private static final IntegerValue rotationMaxSpeed;
    @NotNull
    private static final IntegerValue rotationMinSpeed;
    @NotNull
    private static final FloatValue random;
    @Nullable
    private static Entity entity;
    private static long leftDelay;
    private static long leftLastSwing;
    @NotNull
    private static final MSTimer switchTimer;
    @Nullable
    private static EntityLivingBase target;
    @NotNull
    private static final List<Integer> prevTargetEntities;
    @NotNull
    private static final List<EntityLivingBase> discoveredTargets;
    @NotNull
    private static final List<EntityLivingBase> inRangeDiscoveredTargets;
    private static boolean lastCanBeSeen;
    private static long blockDelay;
    private static long LastBlock;
    private static boolean blockingStatus;
    @NotNull
    private static TimerMS blockTime;
    private static boolean attacked;
    private static int hurtTime;
    private static boolean canSwing;
    private static boolean hitable;
    @NotNull
    private static final Function1<Entity, AxisAlignedBB> getAABB;

    private SilentAura() {
    }

    @NotNull
    public final FloatValue getReachValue() {
        return reachValue;
    }

    @NotNull
    public final FloatValue getDiscoverValue() {
        return discoverValue;
    }

    @Nullable
    public final EntityLivingBase getTarget() {
        return target;
    }

    public final void setTarget(@Nullable EntityLivingBase entityLivingBase) {
        target = entityLivingBase;
    }

    private final boolean getCancelAttack() {
        return (Boolean)onlyClick.get() != false && !MinecraftInstance.mc.field_71474_y.field_74312_F.field_74513_e || (Boolean)onlyWeapon.get() != false && (MinecraftInstance.mc.field_71439_g.func_70694_bm() == null || !(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword) && !(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemPickaxe) && !(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemAxe)) || (Boolean)scaffoldCheck.get() != false && Scaffold.INSTANCE.getState();
    }

    public final boolean getCanBlock() {
        return (alwaysBlock.get() != false || autoBlock.equals("Fake")) && blockingStatus;
    }

    @Override
    public void onDisable() {
        blockingStatus = false;
        entity = null;
        target = null;
        this.stopBlocking();
        prevTargetEntities.clear();
        discoveredTargets.clear();
        inRangeDiscoveredTargets.clear();
        hurtTime = 0;
        MouseUtils.INSTANCE.setLeftClicked(false);
    }

    @Override
    public void onEnable() {
        entity = null;
        lastCanBeSeen = false;
        blockingStatus = false;
        this.getTarget();
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        blockTime.reset();
        attacked = true;
        if (hurtTime == 0) {
            hurtTime = 10;
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getTarget();
        if (target != null) {
            MovementFix.INSTANCE.applyForceStrafe(lessFix.get(), (Boolean)movementFix.get());
        }
    }

    @EventTarget
    public final void onPreUpdate(@NotNull PreUpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.getCancelAttack()) {
            discoveredTargets.clear();
            inRangeDiscoveredTargets.clear();
            target = null;
        }
        this.updateHitable();
        this.getTarget();
        if (hurtTime > 0) {
            hurtTime += -1;
        }
        EntityLivingBase entityLivingBase = target;
        Intrinsics.checkNotNull(entityLivingBase);
        if (EntityUtils.INSTANCE.isSelected((Entity)entityLivingBase, true)) {
            if (attackMode.equals("Legit")) {
                if (target != null && !this.getCancelAttack()) {
                    if (!(System.currentTimeMillis() - leftLastSwing < leftDelay || HitSelect.INSTANCE.getState() && !HitSelect.INSTANCE.getMode().equals("Active") && HitSelect.INSTANCE.getCancelClick())) {
                        if (canSwing) {
                            KeyBinding.func_74507_a((int)MinecraftInstance.mc.field_71474_y.field_74312_F.func_151463_i());
                            MouseUtils.INSTANCE.setLeftClicked(true);
                        }
                        leftLastSwing = System.currentTimeMillis();
                        leftDelay = TimeUtils.INSTANCE.randomClickDelay(((Number)minCPSValue.get()).intValue(), ((Number)maxCPSValue.get()).intValue());
                    } else {
                        MouseUtils.INSTANCE.setLeftClicked(false);
                    }
                }
            } else if (target != null && !this.getCancelAttack()) {
                if (!(System.currentTimeMillis() - leftLastSwing < leftDelay || HitSelect.INSTANCE.getState() && HitSelect.INSTANCE.getCancelClick())) {
                    if (ProtocolFixer.newerThan1_8()) {
                        if (hitable) {
                            EntityLivingBase entityLivingBase2 = target;
                            Intrinsics.checkNotNull(entityLivingBase2);
                            AttackEvent attackEvent = new AttackEvent((Entity)entityLivingBase2);
                            CrossSine.INSTANCE.getEventManager().callEvent(attackEvent);
                            if (!attackEvent.isCancelled()) {
                                MinecraftInstance.mc.field_71442_b.func_78764_a((EntityPlayer)MinecraftInstance.mc.field_71439_g, entity);
                                CPSCounter.registerClick(CPSCounter.MouseButton.LEFT);
                                if (MinecraftInstance.mc.field_71442_b.field_78779_k != WorldSettings.GameType.SPECTATOR) {
                                    MinecraftInstance.mc.field_71439_g.func_71059_n((Entity)target);
                                }
                            }
                        }
                        if (canSwing) {
                            if (swingMode.equals("ClientSide")) {
                                MinecraftInstance.mc.field_71439_g.func_71038_i();
                            } else if (swingMode.equals("ServerSide")) {
                                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0APacketAnimation());
                            }
                            MouseUtils.INSTANCE.setLeftClicked(true);
                        }
                    } else {
                        if (canSwing) {
                            if (swingMode.equals("ClientSide")) {
                                MinecraftInstance.mc.field_71439_g.func_71038_i();
                            } else if (swingMode.equals("ServerSide")) {
                                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0APacketAnimation());
                            }
                            MouseUtils.INSTANCE.setLeftClicked(true);
                        }
                        if (hitable) {
                            EntityLivingBase entityLivingBase3 = target;
                            Intrinsics.checkNotNull(entityLivingBase3);
                            AttackEvent attackEvent = new AttackEvent((Entity)entityLivingBase3);
                            CrossSine.INSTANCE.getEventManager().callEvent(attackEvent);
                            if (!attackEvent.isCancelled()) {
                                MinecraftInstance.mc.field_71442_b.func_78764_a((EntityPlayer)MinecraftInstance.mc.field_71439_g, entity);
                                CPSCounter.registerClick(CPSCounter.MouseButton.LEFT);
                                if (MinecraftInstance.mc.field_71442_b.field_78779_k != WorldSettings.GameType.SPECTATOR) {
                                    MinecraftInstance.mc.field_71439_g.func_71059_n((Entity)target);
                                }
                            }
                        }
                    }
                    leftLastSwing = System.currentTimeMillis();
                    leftDelay = TimeUtils.INSTANCE.randomClickDelay(((Number)minCPSValue.get()).intValue(), ((Number)maxCPSValue.get()).intValue());
                } else if (System.currentTimeMillis() - leftLastSwing >= 30L) {
                    MouseUtils.INSTANCE.setLeftClicked(false);
                }
            }
        }
        if (((Boolean)switchValue.get()).booleanValue()) {
            if (switchTimer.hasTimePassed(((Number)switchDelay.get()).intValue())) {
                EntityLivingBase entityLivingBase4 = target;
                Intrinsics.checkNotNull(entityLivingBase4);
                prevTargetEntities.add(entityLivingBase4.func_145782_y());
                switchTimer.reset();
            }
        } else {
            EntityLivingBase entityLivingBase5 = target;
            Intrinsics.checkNotNull(entityLivingBase5);
            prevTargetEntities.add(entityLivingBase5.func_145782_y());
        }
        if (target == null || discoveredTargets.isEmpty()) {
            this.stopBlocking();
            return;
        }
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        block19: {
            Intrinsics.checkNotNullParameter(event, "event");
            if (((Boolean)markValue.get()).booleanValue() && !((Collection)discoveredTargets).isEmpty()) {
                EntityLivingBase entityLivingBase = target;
                Intrinsics.checkNotNull(entityLivingBase);
                KillAura.INSTANCE.draw(entityLivingBase, event);
                GlStateManager.func_179117_G();
            }
            EntityLivingBase entityLivingBase = target;
            Intrinsics.checkNotNull(entityLivingBase);
            if (!EntityUtils.INSTANCE.isSelected((Entity)entityLivingBase, true) || target == null) break block19;
            float f = MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)target);
            float f2 = attackMode.equals("Legit") ? ((Number)blockRangeValue.get()).floatValue() : ((Number)reachValue.get()).floatValue();
            if (!(f <= f2)) break block19;
            if (!autoBlock.equals("None") && MinecraftInstance.mc.field_71439_g.func_70632_aY() && attackMode.equals("Legit")) {
                PlayerUtils.INSTANCE.swing();
            }
            if (!(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword)) break block19;
            String string = ((String)autoBlock.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (string) {
                case "bypass": {
                    this.bypassBlock();
                    break;
                }
                case "fake": {
                    blockingStatus = true;
                    break;
                }
                case "time": {
                    if (!attacked) break;
                    blockingStatus = true;
                    MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = true;
                    MouseUtils.INSTANCE.setRightClicked(true);
                    if (!discoveredTargets.isEmpty() && !blockTime.hasTimePassed(((Number)autoBlockTime.get()).intValue())) break;
                    attacked = false;
                    MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
                    MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
                    break;
                }
                case "hurttime": {
                    if (!whileMoving.get().booleanValue() && !MovementUtils.INSTANCE.isMoving() || MovementUtils.INSTANCE.isMoving()) {
                        MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = hurtTime > 1;
                        MouseUtils.INSTANCE.setRightClicked(hurtTime > 1);
                        blockingStatus = true;
                        break;
                    }
                    this.stopBlocking();
                }
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void getTarget() {
        void $this$filterTo$iv$iv;
        void $this$filter$iv;
        int fov = ((Number)fovValue.get()).intValue();
        boolean switchMode = (Boolean)switchValue.get();
        discoveredTargets.clear();
        for (Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
            if (!(entity instanceof EntityLivingBase) || !EntityUtils.INSTANCE.isSelected(entity, true) || switchMode && prevTargetEntities.contains(((EntityLivingBase)entity).func_145782_y())) continue;
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
            double distance = EntityExtensionKt.getDistanceToEntityBox((Entity)entityPlayerSP, entity);
            double entityFov = RotationUtils.getRotationDifference(entity);
            if (!(distance <= (double)((Number)discoverValue.get()).floatValue()) || fov != 180 && !(entityFov <= (double)fov)) continue;
            discoveredTargets.add((EntityLivingBase)entity);
        }
        Iterable<Object> $this$sortBy$iv = discoveredTargets;
        boolean $i$f$sortBy = false;
        if ($this$sortBy$iv.size() > 1) {
            CollectionsKt.sortWith($this$sortBy$iv, new Comparator(){

                public final int compare(T a, T b) {
                    EntityLivingBase it = (EntityLivingBase)a;
                    boolean bl = false;
                    EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
                    Comparable comparable = Double.valueOf(EntityExtensionKt.getDistanceToEntityBox((Entity)entityPlayerSP, (Entity)it));
                    it = (EntityLivingBase)b;
                    Comparable comparable2 = comparable;
                    bl = false;
                    entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
                    return ComparisonsKt.compareValues(comparable2, EntityExtensionKt.getDistanceToEntityBox((Entity)entityPlayerSP, (Entity)it));
                }
            });
        }
        inRangeDiscoveredTargets.clear();
        $this$sortBy$iv = discoveredTargets;
        List<EntityLivingBase> list = inRangeDiscoveredTargets;
        boolean $i$f$filter = false;
        void distance = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            EntityLivingBase it = (EntityLivingBase)element$iv$iv;
            boolean bl = false;
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
            if (!(EntityExtensionKt.getDistanceToEntityBox((Entity)entityPlayerSP, (Entity)it) < 3.0)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        list.addAll((List)destination$iv$iv);
        if (inRangeDiscoveredTargets.isEmpty() && !((Collection)prevTargetEntities).isEmpty()) {
            prevTargetEntities.clear();
            this.getTarget();
            return;
        }
        for (EntityLivingBase entity : discoveredTargets) {
            if (!this.getRot((Entity)entity)) {
                boolean success = false;
                continue;
            }
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
            if (!(EntityExtensionKt.getDistanceToEntityBox((Entity)entityPlayerSP, (Entity)entity) < (double)((Number)discoverValue.get()).floatValue())) continue;
            target = entity;
            TargetStrafe targetStrafe = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
            Intrinsics.checkNotNull(targetStrafe);
            EntityLivingBase entityLivingBase = target;
            if (entityLivingBase == null) {
                return;
            }
            targetStrafe.setTargetEntity(entityLivingBase);
            TargetStrafe targetStrafe2 = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
            Intrinsics.checkNotNull(targetStrafe2);
            TargetStrafe targetStrafe3 = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
            Intrinsics.checkNotNull(targetStrafe3);
            targetStrafe2.setDoStrafe(targetStrafe3.toggleStrafe());
            return;
        }
        target = null;
        this.stopBlocking();
    }

    /*
     * Unable to fully structure code
     */
    private final void updateHitable() {
        if (SilentAura.target == null) {
            SilentAura.canSwing = false;
            SilentAura.hitable = false;
            return;
        }
        var3_1 = MinecraftInstance.mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(var3_1, "mc.thePlayer");
        v0 = SilentAura.target;
        if (v0 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.entity.Entity");
        }
        entityDist = EntityExtensionKt.getDistanceToEntityBox((Entity)var3_1, (Entity)v0);
        v1 = SilentAura.canSwing = entityDist <= (double)((Number)SilentAura.swingRange.get()).floatValue();
        if (SilentAura.hitableCheck.get().booleanValue() && SilentAura.attackMode.equals("Packet")) {
            SilentAura.hitable = entityDist <= (double)((Number)SilentAura.reachValue.get()).floatValue();
            return;
        }
        if ((float)((Number)SilentAura.rotationMaxSpeed.get()).intValue() <= 0.0f) {
            SilentAura.hitable = true;
            return;
        }
        var4_3 = MinecraftInstance.mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(var4_3, "mc.thePlayer");
        wallTrace = EntityExtensionKt.rayTraceWithServerSideRotation((Entity)var4_3, entityDist);
        if (!RotationUtils.isFaced((Entity)SilentAura.target, ((Number)SilentAura.reachValue.get()).floatValue())) ** GOTO lbl-1000
        if (entityDist < (double)((Number)SilentAura.discoverValue.get()).floatValue()) ** GOTO lbl-1000
        v2 = wallTrace;
        if ((v2 == null ? null : v2.field_72313_a) != MovingObjectPosition.MovingObjectType.BLOCK) lbl-1000:
        // 2 sources

        {
            v3 = true;
        } else lbl-1000:
        // 2 sources

        {
            v3 = false;
        }
        SilentAura.hitable = v3;
    }

    private final boolean getRot(Entity entity) {
        if (this.getCancelAttack()) {
            return false;
        }
        double entityFov = RotationUtils.getRotationDifference(RotationUtils.toRotation(RotationUtils.getCenter(EntityExtensionKt.getHitBox(entity)), true), RotationUtils.serverRotation);
        if (entityFov <= (double)MinecraftInstance.mc.field_71474_y.field_74334_X) {
            lastCanBeSeen = true;
        } else if (lastCanBeSeen) {
            lastCanBeSeen = false;
        }
        AxisAlignedBB boundingBox = getAABB.invoke(entity);
        VecRotation vecRotation = RotationUtils.calculateCenter("HalfUp", true, ((Number)random.get()).floatValue(), true, boundingBox, false, true);
        if (vecRotation == null) {
            return false;
        }
        Rotation directRotation = vecRotation.component2();
        double diffAngle = RotationUtils.getRotationDifference(RotationUtils.serverRotation, directRotation);
        if (diffAngle < 0.0) {
            diffAngle = -diffAngle;
        }
        if (diffAngle > 180.0) {
            diffAngle = 180.0;
        }
        double calculateSpeed = diffAngle / (double)360 * ((Number)rotationMaxSpeed.get()).doubleValue() + (1.0 - diffAngle / (double)360) * ((Number)rotationMinSpeed.get()).doubleValue();
        Rotation rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, directRotation, (float)calculateSpeed);
        Intrinsics.checkNotNullExpressionValue(rotation, "limitAngleChange(Rotatio\u2026calculateSpeed.toFloat())");
        Rotation rotation2 = rotation;
        RotationUtils.setFreeLookRotation(rotation2, 1, (Boolean)movementFix.get() != false && lessFix.get() == false);
        return true;
    }

    private final void bypassBlock() {
        if (System.currentTimeMillis() - LastBlock >= blockDelay) {
            KeyBinding.func_74507_a((int)MinecraftInstance.mc.field_71474_y.field_74313_G.func_151463_i());
            MouseUtils.INSTANCE.setRightClicked(true);
            LastBlock = System.currentTimeMillis();
            blockDelay = TimeUtils.INSTANCE.randomClickDelay(((Number)bypassTick.get()).intValue(), ((Number)bypassTick.get()).intValue());
        } else {
            MouseUtils.INSTANCE.setRightClicked(false);
        }
        blockingStatus = true;
    }

    private final void stopBlocking() {
        if (blockingStatus) {
            MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
            MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
            blockingStatus = false;
        }
    }

    public final double getReach() {
        return ((Number)reachValue.get()).floatValue();
    }

    @Override
    @Nullable
    public String getTag() {
        return String.valueOf(inRangeDiscoveredTargets.size());
    }

    public static final /* synthetic */ ListValue access$getAttackMode$p() {
        return attackMode;
    }

    public static final /* synthetic */ ListValue access$getAutoBlock$p() {
        return autoBlock;
    }

    public static final /* synthetic */ BoolValue access$getMovementFix$p() {
        return movementFix;
    }

    public static final /* synthetic */ BoolValue access$getSwitchValue$p() {
        return switchValue;
    }

    public static final /* synthetic */ IntegerValue access$getMinCPSValue$p() {
        return minCPSValue;
    }

    public static final /* synthetic */ IntegerValue access$getMaxCPSValue$p() {
        return maxCPSValue;
    }

    public static final /* synthetic */ IntegerValue access$getRotationMinSpeed$p() {
        return rotationMinSpeed;
    }

    public static final /* synthetic */ IntegerValue access$getRotationMaxSpeed$p() {
        return rotationMaxSpeed;
    }

    static {
        String[] stringArray = new String[]{"Legit", "Packet"};
        attackMode = new ListValue("AttackMode", stringArray, "Legit");
        hitableCheck = new BoolValue("Hitable", false).displayable(hitableCheck.1.INSTANCE);
        stringArray = new String[]{"ClientSide", "ServerSide", "None"};
        swingMode = new ListValue("SwingMode", stringArray, "ClientSide").displayable(swingMode.1.INSTANCE);
        stringArray = new String[]{"Fake", "Bypass", "HurtTime", "Time", "None"};
        autoBlock = new ListValue("Blocking", stringArray, "None");
        whileMoving = new BoolValue("WhileMoving", false).displayable(whileMoving.1.INSTANCE);
        alwaysBlock = new BoolValue("Always-Block", false).displayable(alwaysBlock.1.INSTANCE);
        bypassTick = new IntegerValue("BypassTick", 15, 1, 20).displayable(bypassTick.1.INSTANCE);
        autoBlockTime = new IntegerValue("Time-Press", 0, 0, 1000).displayable(autoBlockTime.1.INSTANCE);
        switchValue = new BoolValue("Switch-Target", false);
        scaffoldCheck = new BoolValue("Scaffold-Check", true);
        markValue = new BoolValue("Mark", false);
        movementFix = new BoolValue("MovementFix", false);
        lessFix = new BoolValue("LessFix", false).displayable(lessFix.1.INSTANCE);
        switchDelay = new IntegerValue("Switch-Delay", 140, 0, 1000).displayable(switchDelay.1.INSTANCE);
        reachValue = new FloatValue(){

            protected void onChanged(float oldValue, float newValue) {
                float minreach = ((Number)SilentAura.INSTANCE.getDiscoverValue().get()).floatValue();
                if (minreach < newValue) {
                    this.set(Float.valueOf(minreach));
                }
            }
        };
        swingRange = new FloatValue(){

            protected void onChanged(float oldValue, float newValue) {
                if (newValue > ((Number)SilentAura.INSTANCE.getDiscoverValue().get()).floatValue()) {
                    this.set(SilentAura.INSTANCE.getDiscoverValue().get());
                }
                if (newValue < ((Number)SilentAura.INSTANCE.getReachValue().get()).floatValue()) {
                    this.set(SilentAura.INSTANCE.getReachValue().get());
                }
            }
        };
        discoverValue = new FloatValue(){

            protected void onChanged(float oldValue, float newValue) {
                float maxreach = ((Number)SilentAura.INSTANCE.getReachValue().get()).floatValue();
                if (maxreach > newValue) {
                    this.set(Float.valueOf(maxreach));
                }
            }
        };
        blockRangeValue = (FloatValue)new FloatValue(){

            protected void onChanged(float oldValue, float newValue) {
                float i = ((Number)SilentAura.INSTANCE.getDiscoverValue().get()).floatValue();
                if (i < newValue) {
                    this.set(Float.valueOf(i));
                }
            }
        }.displayable(blockRangeValue.2.INSTANCE);
        fovValue = new IntegerValue("Fov", 180, 0, 180);
        maxCPSValue = new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                int minCPS = ((Number)SilentAura.access$getMinCPSValue$p().get()).intValue();
                if (minCPS > newValue) {
                    this.set(minCPS);
                }
            }
        };
        minCPSValue = new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                int maxCPS = ((Number)SilentAura.access$getMaxCPSValue$p().get()).intValue();
                if (maxCPS < newValue) {
                    this.set(maxCPS);
                }
            }
        };
        rotationMaxSpeed = new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                int minRot = ((Number)SilentAura.access$getRotationMinSpeed$p().get()).intValue();
                if (minRot > newValue) {
                    this.set(minRot);
                }
            }
        };
        rotationMinSpeed = new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                int maxRot = ((Number)SilentAura.access$getRotationMaxSpeed$p().get()).intValue();
                if (maxRot < newValue) {
                    this.set(maxRot);
                }
            }
        };
        random = new FloatValue("Random-Amount", 0.0f, 0.0f, 2.0f);
        leftDelay = 50L;
        switchTimer = new MSTimer();
        prevTargetEntities = new ArrayList();
        discoveredTargets = new ArrayList();
        inRangeDiscoveredTargets = new ArrayList();
        blockDelay = 50L;
        blockTime = new TimerMS();
        getAABB = getAABB.1.INSTANCE;
    }
}

