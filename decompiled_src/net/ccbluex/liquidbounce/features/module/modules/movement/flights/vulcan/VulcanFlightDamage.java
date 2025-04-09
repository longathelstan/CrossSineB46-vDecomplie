/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.vulcan;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010#\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020$H\u0016J\u0010\u0010&\u001a\u00020$2\u0006\u0010'\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020$2\u0006\u0010'\u001a\u00020*H\u0016J\u0006\u0010+\u001a\u00020\u0012R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/vulcan/VulcanFlightDamage;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "dmgJumpCount", "", "getDmgJumpCount", "()I", "setDmgJumpCount", "(I)V", "flyTicks", "getFlyTicks", "setFlyTicks", "flyTimerValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "instantDmgValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "isDamaged", "", "()Z", "setDamaged", "(Z)V", "isStarted", "lastSentX", "", "lastSentY", "lastSentZ", "lastTickX", "lastTickY", "lastTickZ", "noMotionSet", "onlyDamageValue", "selfDamageValue", "vanillaValue", "waitFlag", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "runSelfDamageCore", "CrossSine"})
public final class VulcanFlightDamage
extends FlightMode {
    @NotNull
    private final BoolValue onlyDamageValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "OnlyDamage"), true);
    @NotNull
    private final BoolValue selfDamageValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "SelfDamage"), true);
    @NotNull
    private final BoolValue instantDmgValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "InstantDamage"), true);
    @NotNull
    private final BoolValue vanillaValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "Vanilla"), false);
    @NotNull
    private final Value<Float> flyTimerValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Timer"), 0.05f, 0.02f, 0.15f).displayable(new Function0<Boolean>(this){
        final /* synthetic */ VulcanFlightDamage this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)VulcanFlightDamage.access$getVanillaValue$p(this.this$0).get() == false;
        }
    });
    private boolean waitFlag;
    private boolean isStarted;
    private boolean isDamaged;
    private int dmgJumpCount;
    private int flyTicks;
    private double lastSentX;
    private double lastSentY;
    private double lastSentZ;
    private double lastTickX;
    private double lastTickY;
    private double lastTickZ;
    private boolean noMotionSet;

    public VulcanFlightDamage() {
        super("VulcanDamage");
    }

    public final boolean isDamaged() {
        return this.isDamaged;
    }

    public final void setDamaged(boolean bl) {
        this.isDamaged = bl;
    }

    public final int getDmgJumpCount() {
        return this.dmgJumpCount;
    }

    public final void setDmgJumpCount(int n) {
        this.dmgJumpCount = n;
    }

    public final int getFlyTicks() {
        return this.flyTicks;
    }

    public final void setFlyTicks(int n) {
        this.flyTicks = n;
    }

    public final boolean runSelfDamageCore() {
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        if (!((Boolean)this.onlyDamageValue.get()).booleanValue() || !((Boolean)this.selfDamageValue.get()).booleanValue()) {
            if (((Boolean)this.onlyDamageValue.get()).booleanValue()) {
                if (MinecraftInstance.mc.field_71439_g.field_70737_aN > 0 || this.isDamaged) {
                    this.isDamaged = true;
                    this.dmgJumpCount = 999;
                    return false;
                }
                return true;
            }
            this.isDamaged = true;
            this.dmgJumpCount = 999;
            return false;
        }
        if (this.isDamaged) {
            this.dmgJumpCount = 999;
            return false;
        }
        MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0f;
        if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            if (this.dmgJumpCount >= 4) {
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
                this.isDamaged = true;
                this.dmgJumpCount = 999;
                return false;
            }
            int n = this.dmgJumpCount;
            this.dmgJumpCount = n + 1;
            MovementUtils.INSTANCE.resetMotion(true);
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
        }
        MovementUtils.INSTANCE.resetMotion(false);
        return true;
    }

    @Override
    public void onEnable() {
        this.flyTicks = 0;
        this.waitFlag = false;
        this.isStarted = false;
        this.isDamaged = false;
        this.dmgJumpCount = 0;
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        if (((Boolean)this.instantDmgValue.get()).booleanValue()) {
            this.dmgJumpCount = 11451;
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - 0.0784, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)0.42f, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.7531999805212, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.4199999868869781, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.7531999805212, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 2.0, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 2.419999986886978, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 2.7531999805212, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 3.00133597911214, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 3.00133597911214, MinecraftInstance.mc.field_71439_g.field_70161_v);
            this.waitFlag = true;
        } else {
            this.runSelfDamageCore();
        }
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!((Boolean)this.instantDmgValue.get()).booleanValue() && this.runSelfDamageCore()) {
            return;
        }
        if (((Boolean)this.instantDmgValue.get()).booleanValue() && this.dmgJumpCount == 11451) {
            if (!this.isStarted) {
                return;
            }
            this.isStarted = false;
            this.waitFlag = false;
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
            this.dmgJumpCount = 999;
        }
        MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0f;
        if (!this.isStarted && !this.waitFlag) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - 0.0784, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            this.waitFlag = true;
        }
        if (this.isStarted) {
            if (((Boolean)this.vanillaValue.get()).booleanValue()) {
                MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
                if (!MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d()) {
                    MovementUtils.INSTANCE.resetMotion(true);
                    if (MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
                        MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
                    }
                }
            } else {
                MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.flyTimerValue.get()).floatValue();
                MovementUtils.INSTANCE.resetMotion(true);
            }
            int n = this.flyTicks;
            this.flyTicks = n + 1;
            if (this.flyTicks > 4) {
                this.flyTicks = 4;
            }
            MovementUtils.INSTANCE.strafe((Boolean)this.vanillaValue.get() != false ? 0.99f : 9.795f + (float)this.flyTicks * 0.05f);
        }
    }

    @Override
    public void onDisable() {
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        MovementUtils.INSTANCE.resetMotion(true);
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        short transUID;
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer && this.waitFlag) {
            event.cancelEvent();
        }
        if (packet instanceof C03PacketPlayer && this.dmgJumpCount < 4 && ((Boolean)this.selfDamageValue.get()).booleanValue()) {
            ((C03PacketPlayer)packet).field_149474_g = false;
        }
        if (this.isStarted && ((Boolean)this.vanillaValue.get()).booleanValue()) {
            if (packet instanceof C03PacketPlayer && (packet instanceof C03PacketPlayer.C04PacketPlayerPosition || packet instanceof C03PacketPlayer.C06PacketPlayerPosLook)) {
                double deltaX = ((C03PacketPlayer)packet).field_149479_a - this.lastSentX;
                double deltaY = ((C03PacketPlayer)packet).field_149477_b - this.lastSentY;
                double deltaZ = ((C03PacketPlayer)packet).field_149478_c - this.lastSentZ;
                if (Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) > 10.0) {
                    PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.lastTickX, this.lastTickY, this.lastTickZ, false)));
                    this.lastSentX = this.lastTickX;
                    this.lastSentY = this.lastTickY;
                    this.lastSentZ = this.lastTickZ;
                }
                this.lastTickX = ((C03PacketPlayer)packet).field_149479_a;
                this.lastTickY = ((C03PacketPlayer)packet).field_149477_b;
                this.lastTickZ = ((C03PacketPlayer)packet).field_149478_c;
                event.cancelEvent();
            } else if (packet instanceof C03PacketPlayer) {
                event.cancelEvent();
            }
        }
        if (packet instanceof S08PacketPlayerPosLook && this.waitFlag && !((Boolean)this.vanillaValue.get()).booleanValue()) {
            this.isStarted = true;
            this.waitFlag = false;
            if (((Boolean)this.instantDmgValue.get()).booleanValue()) {
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(((S08PacketPlayerPosLook)packet).field_148940_a, ((S08PacketPlayerPosLook)packet).field_148938_b, ((S08PacketPlayerPosLook)packet).field_148939_c, ((S08PacketPlayerPosLook)packet).field_148936_d, ((S08PacketPlayerPosLook)packet).field_148937_e, false)));
            }
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
            this.flyTicks = 0;
        } else if (packet instanceof S08PacketPlayerPosLook && ((Boolean)this.vanillaValue.get()).booleanValue()) {
            this.lastSentX = ((S08PacketPlayerPosLook)packet).field_148940_a;
            this.lastSentY = ((S08PacketPlayerPosLook)packet).field_148938_b;
            this.lastSentZ = ((S08PacketPlayerPosLook)packet).field_148939_c;
            this.waitFlag = false;
            if (!((Boolean)this.instantDmgValue.get()).booleanValue()) {
                event.cancelEvent();
            }
            this.noMotionSet = true;
            PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(((S08PacketPlayerPosLook)packet).field_148940_a, ((S08PacketPlayerPosLook)packet).field_148938_b, ((S08PacketPlayerPosLook)packet).field_148939_c, ((S08PacketPlayerPosLook)packet).field_148936_d, ((S08PacketPlayerPosLook)packet).field_148937_e, false)));
            this.isStarted = true;
        }
        if (packet instanceof C0FPacketConfirmTransaction && (transUID = ((C0FPacketConfirmTransaction)packet).field_149534_b) >= -31767 && transUID <= -30769) {
            event.cancelEvent();
            PacketUtils.sendPacketNoEvent(packet);
        }
    }

    public static final /* synthetic */ BoolValue access$getVanillaValue$p(VulcanFlightDamage $this) {
        return $this.vanillaValue;
    }
}

