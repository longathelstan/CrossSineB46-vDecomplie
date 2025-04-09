/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.disablers.other;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.BlinkUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0018H\u0016J\u0010\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020 H\u0016J\b\u0010!\u001a\u00020\u0018H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/other/VulcanCombatDisabler;", "Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/DisablerMode;", "()V", "compDecValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "currentBuffer", "", "currentDec", "currentDelay", "currentTrans", "decDelayMaxValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "decDelayMinValue", "decTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "dynamicValue", "lagTimer", "minBuffValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "noC0BValue", "runReset", "", "statDecValue", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "updateLagTime", "CrossSine"})
public final class VulcanCombatDisabler
extends DisablerMode {
    @NotNull
    private final BoolValue compDecValue = new BoolValue("VulcanDecrease", true);
    @NotNull
    private final Value<Integer> statDecValue = new IntegerValue("VulcanDecreaseDelay", 1500, 500, 2500).displayable(new Function0<Boolean>(this){
        final /* synthetic */ VulcanCombatDisabler this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)VulcanCombatDisabler.access$getCompDecValue$p(this.this$0).get();
        }
    });
    @NotNull
    private final BoolValue dynamicValue = new BoolValue("VulcanDynamicDelay", true);
    @NotNull
    private final Value<Integer> decDelayMinValue = new IntegerValue("VulcanMinDelay", 4500, 2000, 8000).displayable(new Function0<Boolean>(this){
        final /* synthetic */ VulcanCombatDisabler this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)VulcanCombatDisabler.access$getDynamicValue$p(this.this$0).get();
        }
    });
    @NotNull
    private final Value<Integer> decDelayMaxValue = new IntegerValue("VulcanMaxDelay", 5500, 2000, 8000).displayable(new Function0<Boolean>(this){
        final /* synthetic */ VulcanCombatDisabler this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)VulcanCombatDisabler.access$getDynamicValue$p(this.this$0).get();
        }
    });
    @NotNull
    private final IntegerValue minBuffValue = new IntegerValue("VulcanMinBuff", 5, 0, 12);
    @NotNull
    private final BoolValue noC0BValue = new BoolValue("NoC0BPacket", false);
    private int currentTrans;
    private int currentDelay = 5000;
    private int currentBuffer = 4;
    private int currentDec = -1;
    @NotNull
    private final MSTimer lagTimer = new MSTimer();
    @NotNull
    private final MSTimer decTimer = new MSTimer();
    private boolean runReset;

    public VulcanCombatDisabler() {
        super("VulcanCombat");
    }

    @Override
    public void onEnable() {
        this.updateLagTime();
    }

    @Override
    public void onDisable() {
        this.updateLagTime();
        BlinkUtils.releasePacket$default(BlinkUtils.INSTANCE, "C0FPacketConfirmTransaction", false, 0, 0, 14, null);
        BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, false, false, false, false, false, false, false, false, false, 2031, null);
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.runReset) {
            this.runReset = false;
            PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SPRINTING)));
        }
        if (this.lagTimer.hasTimePassed(this.currentDelay) && BlinkUtils.INSTANCE.bufferSize("C0FPacketConfirmTransaction") > this.currentBuffer) {
            this.updateLagTime();
            BlinkUtils.releasePacket$default(BlinkUtils.INSTANCE, "C0FPacketConfirmTransaction", false, 0, this.currentBuffer, 6, null);
            this.getDisabler().debugMessage("C0F-PingTickCounter RELEASE");
        }
        if (this.decTimer.hasTimePassed(this.currentDec) && this.currentDec > 0) {
            BlinkUtils.releasePacket$default(BlinkUtils.INSTANCE, "C0FPacketConfirmTransaction", false, 1, 0, 10, null);
            this.getDisabler().debugMessage("C0F-PingTickCounter DECREASE");
            this.decTimer.reset();
        }
    }

    @Override
    public void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        BlinkUtils.clearPacket$default(BlinkUtils.INSTANCE, "C0FPacketConfirmTransaction", false, 0, 6, null);
        this.currentTrans = 0;
        this.updateLagTime();
        this.runReset = (Boolean)this.noC0BValue.get();
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C0BPacketEntityAction && ((Boolean)this.noC0BValue.get()).booleanValue()) {
            event.cancelEvent();
            this.getDisabler().debugMessage("C0B-EntityAction CANCELLED");
        }
        if (packet instanceof C0FPacketConfirmTransaction && this.getDisabler().getState()) {
            BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, false, false, false, false, false, false, false, false, false, 2031, null);
            short transUID = ((C0FPacketConfirmTransaction)packet).field_149534_b;
            if (transUID >= -25767 && transUID <= -24769) {
                BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, false, false, true, false, false, false, false, false, false, 2031, null);
                this.getDisabler().debugMessage(Intrinsics.stringPlus("C0F-PingTickCounter IN ", BlinkUtils.INSTANCE.bufferSize("C0FPacketConfirmTransaction")));
            } else if (transUID == -30000) {
                BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, false, false, true, false, false, false, false, false, false, 2031, null);
                this.getDisabler().debugMessage(Intrinsics.stringPlus("C0F-OnSpawn IN ", BlinkUtils.INSTANCE.bufferSize("C0FPacketConfirmTransaction")));
            }
        }
    }

    private final void updateLagTime() {
        this.decTimer.reset();
        this.lagTimer.reset();
        this.currentDelay = (Boolean)this.dynamicValue.get() != false ? Random.Default.nextInt(((Number)this.decDelayMinValue.get()).intValue(), ((Number)this.decDelayMaxValue.get()).intValue()) : 5000;
        this.currentDec = (Boolean)this.compDecValue.get() != false ? ((Number)this.statDecValue.get()).intValue() : -1;
        this.currentBuffer = ((Number)this.minBuffValue.get()).intValue();
    }

    public static final /* synthetic */ BoolValue access$getCompDecValue$p(VulcanCombatDisabler $this) {
        return $this.compDecValue;
    }

    public static final /* synthetic */ BoolValue access$getDynamicValue$p(VulcanCombatDisabler $this) {
        return $this.dynamicValue;
    }
}

