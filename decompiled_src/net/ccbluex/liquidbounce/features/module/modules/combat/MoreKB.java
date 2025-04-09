/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.SprintEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="MoreKB", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0007J\b\u0010\u001a\u001a\u00020\u0017H\u0016J\u0010\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u001cH\u0007J\u0010\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u001eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u001f"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/MoreKB;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "cancelSprint", "", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "onlyGroundValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "onlyMoveValue", "tag", "", "getTag", "()Ljava/lang/String;", "ticks", "", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onDisable", "onSprint", "Lnet/ccbluex/liquidbounce/event/SprintEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class MoreKB
extends Module {
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final BoolValue onlyMoveValue;
    @NotNull
    private final BoolValue onlyGroundValue;
    @NotNull
    private final IntegerValue delayValue;
    private int ticks;
    private boolean cancelSprint;
    @NotNull
    private final MSTimer timer;

    public MoreKB() {
        String[] stringArray = new String[]{"Legit", "LegitFast", "STap", "Sneak", "Packet"};
        this.modeValue = new ListValue("Mode", stringArray, "Legit");
        this.onlyMoveValue = new BoolValue("OnlyMove", true);
        this.onlyGroundValue = new BoolValue("OnlyGround", false);
        this.delayValue = new IntegerValue("Delay", 0, 0, 500);
        this.timer = new MSTimer();
    }

    @NotNull
    public final MSTimer getTimer() {
        return this.timer;
    }

    @Override
    public void onDisable() {
        this.ticks = 0;
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getTargetEntity() instanceof EntityLivingBase) {
            if (!this.timer.hasTimePassed(((Number)this.delayValue.get()).intValue()) || !MovementUtils.INSTANCE.isMoving() && ((Boolean)this.onlyMoveValue.get()).booleanValue() || !MinecraftInstance.mc.field_71439_g.field_70122_E && ((Boolean)this.onlyGroundValue.get()).booleanValue()) {
                return;
            }
            if (this.ticks == 0) {
                this.ticks = 2;
            }
            if (this.modeValue.equals("LegitFast")) {
                this.cancelSprint = true;
            }
            this.timer.reset();
        }
    }

    @EventTarget
    public final void onSprint(@NotNull SprintEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.modeValue.equals("LegitFast") && this.cancelSprint) {
            event.cancelEvent();
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.modeValue.equals("Legit")) {
            if (this.ticks == 2) {
                MinecraftInstance.mc.field_71474_y.field_74351_w.field_74513_e = false;
                this.ticks = 1;
            } else if (this.ticks == 1) {
                MinecraftInstance.mc.field_71474_y.field_74351_w.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74351_w);
                this.ticks = 0;
            }
        }
        if (this.modeValue.equals("LegitFast") && this.cancelSprint) {
            this.cancelSprint = false;
        }
        if (this.modeValue.equals("STap")) {
            if (this.ticks == 2) {
                MinecraftInstance.mc.field_71474_y.field_74351_w.field_74513_e = false;
                MinecraftInstance.mc.field_71474_y.field_74368_y.field_74513_e = true;
                this.ticks = 1;
            } else if (this.ticks == 1) {
                MinecraftInstance.mc.field_71474_y.field_74351_w.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74351_w);
                MinecraftInstance.mc.field_71474_y.field_74368_y.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74351_w);
                this.ticks = 0;
            }
        }
        if (this.modeValue.equals("Sneak")) {
            if (this.ticks == 2) {
                MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = true;
                this.ticks = 1;
            } else if (this.ticks == 1) {
                MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74311_E);
                this.ticks = 0;
            }
        }
        if (this.modeValue.equals("Packet")) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SPRINTING));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, C0BPacketEntityAction.Action.START_SPRINTING));
        }
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)this.modeValue.get();
    }
}

