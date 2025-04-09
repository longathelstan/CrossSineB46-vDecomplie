/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/aac/AAC4ReduceVelocity;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/VelocityMode;", "()V", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onVelocityPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class AAC4ReduceVelocity
extends VelocityMode {
    public AAC4ReduceVelocity() {
        super("AAC4Reduce");
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_70737_aN > 0 && !MinecraftInstance.mc.field_71439_g.field_70122_E && this.getVelocity().getVelocityInput() && this.getVelocity().getVelocityTimer().hasTimePassed(80L)) {
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70159_w *= 0.62;
            entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70179_y *= 0.62;
        }
        if (this.getVelocity().getVelocityInput() && (MinecraftInstance.mc.field_71439_g.field_70737_aN < 4 || MinecraftInstance.mc.field_71439_g.field_70122_E) && this.getVelocity().getVelocityTimer().hasTimePassed(120L)) {
            this.getVelocity().setVelocityInput(false);
        }
    }

    @Override
    public void onVelocityPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> p = event.getPacket();
        if (p instanceof S12PacketEntityVelocity) {
            this.getVelocity().setVelocityInput(true);
            ((S12PacketEntityVelocity)p).field_149415_b = (int)((double)((S12PacketEntityVelocity)p).func_149411_d() * 0.6);
            ((S12PacketEntityVelocity)p).field_149414_d = (int)((double)((S12PacketEntityVelocity)p).func_149409_f() * 0.6);
        }
    }
}

