/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.ncp;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/ncp/OldNCP2Flight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "motionValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class OldNCP2Flight
extends FlightMode {
    @NotNull
    private final FloatValue motionValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Motion"), 0.0f, 0.0f, 1.0f);

    public OldNCP2Flight() {
        super("OldNCP2");
    }

    @Override
    public void onEnable() {
        if (!MinecraftInstance.mc.field_71439_g.field_70122_E) {
            return;
        }
        int n = 65;
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int it = n3 = n2++;
            boolean bl = false;
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.049, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
        }
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.1, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
        EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
        entityPlayerSP.field_70159_w *= 0.1;
        entityPlayerSP = MinecraftInstance.mc.field_71439_g;
        entityPlayerSP.field_70179_y *= 0.1;
        MinecraftInstance.mc.field_71439_g.func_71038_i();
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        MinecraftInstance.mc.field_71439_g.field_70181_x = -((Number)this.motionValue.get()).floatValue();
        if (MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d()) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = -0.5;
        }
        MovementUtils.INSTANCE.strafe();
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer) {
            ((C03PacketPlayer)packet).field_149474_g = true;
        }
    }
}

