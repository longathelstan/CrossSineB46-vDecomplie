/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.disablers.server;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/server/HeirteirACDisabler;", "Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/DisablerMode;", "()V", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class HeirteirACDisabler
extends DisablerMode {
    public HeirteirACDisabler() {
        super("HeirteirAC");
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_70173_aa < 120) {
            return;
        }
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)490, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        double z;
        double y;
        double x;
        double diff;
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (MinecraftInstance.mc.field_71439_g.field_70173_aa < 120) {
            return;
        }
        if (packet instanceof S08PacketPlayerPosLook && (diff = Math.sqrt((x = ((S08PacketPlayerPosLook)packet).field_148940_a - MinecraftInstance.mc.field_71439_g.field_70165_t) * x + (y = ((S08PacketPlayerPosLook)packet).field_148938_b - MinecraftInstance.mc.field_71439_g.field_70163_u) * y + (z = ((S08PacketPlayerPosLook)packet).field_148939_c - MinecraftInstance.mc.field_71439_g.field_70161_v) * z)) < 12.0) {
            event.cancelEvent();
            PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(((S08PacketPlayerPosLook)packet).field_148940_a, ((S08PacketPlayerPosLook)packet).field_148938_b, ((S08PacketPlayerPosLook)packet).field_148939_c, ((S08PacketPlayerPosLook)packet).func_148931_f(), ((S08PacketPlayerPosLook)packet).func_148930_g(), !MinecraftInstance.mc.field_71441_e.func_175623_d(new BlockPos(((S08PacketPlayerPosLook)packet).field_148940_a, ((S08PacketPlayerPosLook)packet).field_148938_b - 0.1, ((S08PacketPlayerPosLook)packet).field_148939_c)))));
        }
    }
}

