/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.ncp;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/ncp/NCPPacketFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "speedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "timerValue", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class NCPPacketFlight
extends FlightMode {
    @NotNull
    private final FloatValue timerValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Timer"), 1.1f, 1.0f, 1.3f);
    @NotNull
    private final FloatValue speedValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Speed"), 0.28f, 0.27f, 0.29f);

    public NCPPacketFlight() {
        super("NCPPacket");
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        double yaw = Math.toRadians(MinecraftInstance.mc.field_71439_g.field_70177_z);
        double x = -Math.sin(yaw) * ((Number)this.speedValue.get()).doubleValue();
        double z = Math.cos(yaw) * ((Number)this.speedValue.get()).doubleValue();
        MovementUtils.INSTANCE.resetMotion(true);
        MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.timerValue.get()).floatValue();
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t + x, MinecraftInstance.mc.field_71439_g.field_70181_x, MinecraftInstance.mc.field_71439_g.field_70179_y + z, false));
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t + x, MinecraftInstance.mc.field_71439_g.field_70181_x - (double)490, MinecraftInstance.mc.field_71439_g.field_70179_y + z, true));
        EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
        entityPlayerSP.field_70165_t += x;
        entityPlayerSP = MinecraftInstance.mc.field_71439_g;
        entityPlayerSP.field_70161_v += z;
    }
}

