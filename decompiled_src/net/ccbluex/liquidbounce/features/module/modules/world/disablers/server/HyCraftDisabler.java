/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.disablers.server;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S3EPacketTeams;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/server/HyCraftDisabler;", "Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/DisablerMode;", "()V", "invalidPosition", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "invalidTeams", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class HyCraftDisabler
extends DisablerMode {
    @NotNull
    private final BoolValue invalidTeams = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "BlockTeams"), false);
    @NotNull
    private final BoolValue invalidPosition = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "BlockInvalidPosition"), true);

    public HyCraftDisabler() {
        super("HyCraftCrash");
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof S3EPacketTeams && ((Boolean)this.invalidTeams.get()).booleanValue()) {
            this.getDisabler().debugMessage("Blocked Invalid S3EPacketTeams");
            event.cancelEvent();
        }
        if (packet instanceof S08PacketPlayerPosLook && ((Boolean)this.invalidPosition.get()).booleanValue()) {
            double x = ((S08PacketPlayerPosLook)packet).field_148940_a;
            double y = ((S08PacketPlayerPosLook)packet).field_148938_b;
            double z = ((S08PacketPlayerPosLook)packet).field_148939_c;
            if (x >= 200000.0 || x <= -200000.0 || y >= 200000.0 || y <= -200000.0 || z >= 200000.0 || z <= -200000.0) {
                MinecraftInstance.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(x, y, z, ((S08PacketPlayerPosLook)packet).func_148931_f(), ((S08PacketPlayerPosLook)packet).func_148930_g(), false));
                this.getDisabler().debugMessage("Blocked Invalid S08PacketPlayerPosLook");
                event.cancelEvent();
            }
        }
    }
}

