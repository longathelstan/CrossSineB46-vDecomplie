/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.aac;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0016H\u0016J\u0010\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0016H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/aac/AAC520VanillaFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "flyClip", "", "flyStart", "nextFlag", "packetModeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "packets", "", "Lnet/minecraft/network/play/client/C03PacketPlayer;", "purseValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "smoothValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "speedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "useC04Value", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "sendPackets", "CrossSine"})
public final class AAC520VanillaFlight
extends FlightMode {
    @NotNull
    private final FloatValue speedValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Speed"), 2.0f, 0.0f, 5.0f);
    @NotNull
    private final BoolValue smoothValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "Smooth"), false);
    @NotNull
    private final IntegerValue purseValue = new IntegerValue(Intrinsics.stringPlus(this.getValuePrefix(), "Purse"), 7, 3, 20);
    @NotNull
    private final ListValue packetModeValue;
    @NotNull
    private final BoolValue useC04Value;
    @NotNull
    private final List<C03PacketPlayer> packets;
    @NotNull
    private final MSTimer timer;
    private boolean nextFlag;
    private boolean flyClip;
    private boolean flyStart;

    public AAC520VanillaFlight() {
        super("AAC5.2.0-Vanilla");
        String[] stringArray = new String[]{"Old", "Rise"};
        this.packetModeValue = new ListValue(Intrinsics.stringPlus(this.getValuePrefix(), "PacketMode"), stringArray, "Old");
        this.useC04Value = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "UseC04"), false);
        this.packets = new ArrayList();
        this.timer = new MSTimer();
    }

    @Override
    public void onEnable() {
        if (MinecraftInstance.mc.func_71356_B()) {
            this.getFlight().setState(false);
            return;
        }
        this.packets.clear();
        this.nextFlag = false;
        this.flyClip = false;
        this.flyStart = false;
        this.timer.reset();
        if (((Boolean)this.smoothValue.get()).booleanValue()) {
            MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.42, MinecraftInstance.mc.field_71439_g.field_70161_v);
        }
    }

    @Override
    public void onDisable() {
        this.sendPackets();
        this.packets.clear();
        MinecraftInstance.mc.field_71439_g.field_70145_X = false;
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        EntityPlayerSP entityPlayerSP;
        Intrinsics.checkNotNullParameter(event, "event");
        this.getFlight().setAntiDesync(true);
        boolean bl = MinecraftInstance.mc.field_71439_g.field_70145_X = !MovementUtils.INSTANCE.isMoving();
        if (((Boolean)this.smoothValue.get()).booleanValue()) {
            if (!this.timer.hasTimePassed(1000L) || !this.flyStart) {
                MovementUtils.INSTANCE.resetMotion(true);
                MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0f;
                MinecraftInstance.mc.field_71428_T.field_74278_d = 0.32f;
                return;
            }
            if (!this.flyClip) {
                MinecraftInstance.mc.field_71428_T.field_74278_d = 0.19f;
            } else {
                this.flyClip = false;
                MinecraftInstance.mc.field_71428_T.field_74278_d = 1.2f;
            }
        }
        MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
        MovementUtils.INSTANCE.resetMotion(true);
        if (MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
            entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70181_x += ((Number)this.speedValue.get()).doubleValue() * 0.5;
        }
        if (MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d()) {
            entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70181_x -= ((Number)this.speedValue.get()).doubleValue() * 0.5;
        }
        MovementUtils.INSTANCE.strafe(((Number)this.speedValue.get()).floatValue());
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof S08PacketPlayerPosLook) {
            this.flyStart = true;
            if (this.timer.hasTimePassed(2000L)) {
                this.flyClip = true;
                MinecraftInstance.mc.field_71428_T.field_74278_d = 1.3f;
            }
            this.nextFlag = true;
        } else if (packet instanceof C03PacketPlayer) {
            double f = (double)MinecraftInstance.mc.field_71439_g.field_70130_N / 2.0;
            if (((C03PacketPlayer)packet).field_149477_b < 1145.1400191981) {
                if (MinecraftInstance.mc.field_71441_e.func_72829_c(new AxisAlignedBB(((C03PacketPlayer)packet).field_149479_a - f, ((C03PacketPlayer)packet).field_149477_b, ((C03PacketPlayer)packet).field_149478_c - f, ((C03PacketPlayer)packet).field_149479_a + f, ((C03PacketPlayer)packet).field_149477_b + (double)MinecraftInstance.mc.field_71439_g.field_70131_O, ((C03PacketPlayer)packet).field_149478_c + f))) {
                    return;
                }
                this.packets.add((C03PacketPlayer)packet);
                this.nextFlag = false;
                event.cancelEvent();
                if ((!((Boolean)this.smoothValue.get()).booleanValue() || this.timer.hasTimePassed(1000L)) && this.packets.size() > ((Number)this.purseValue.get()).intValue()) {
                    this.sendPackets();
                }
            }
        }
    }

    private final void sendPackets() {
        float yaw = MinecraftInstance.mc.field_71439_g.field_70177_z;
        float pitch = MinecraftInstance.mc.field_71439_g.field_70125_A;
        if (Intrinsics.areEqual(this.packetModeValue.get(), "Old")) {
            for (C03PacketPlayer packet : this.packets) {
                if (!packet.func_149466_j()) continue;
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)packet));
                if (packet.func_149463_k()) {
                    yaw = packet.field_149476_e;
                    pitch = packet.field_149473_f;
                }
                if (((Boolean)this.useC04Value.get()).booleanValue()) {
                    PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C04PacketPlayerPosition(packet.field_149479_a, 1.0E308, packet.field_149478_c, true)));
                    PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C04PacketPlayerPosition(packet.field_149479_a, packet.field_149477_b, packet.field_149478_c, true)));
                    continue;
                }
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(packet.field_149479_a, 1.0E308, packet.field_149478_c, yaw, pitch, true)));
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(packet.field_149479_a, packet.field_149477_b, packet.field_149478_c, yaw, pitch, true)));
            }
        } else {
            for (C03PacketPlayer packet : this.packets) {
                if (!packet.func_149466_j()) continue;
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)packet));
                if (packet.func_149463_k()) {
                    yaw = packet.field_149476_e;
                    pitch = packet.field_149473_f;
                }
                if (((Boolean)this.useC04Value.get()).booleanValue()) {
                    PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C04PacketPlayerPosition(packet.field_149479_a, -1.0E159, packet.field_149478_c + (double)10, true)));
                    PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C04PacketPlayerPosition(packet.field_149479_a, packet.field_149477_b, packet.field_149478_c, true)));
                    continue;
                }
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(packet.field_149479_a, -1.0E159, packet.field_149478_c + (double)10, yaw, pitch, true)));
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(packet.field_149479_a, packet.field_149477_b, packet.field_149478_c, yaw, pitch, true)));
            }
        }
        this.packets.clear();
    }
}

