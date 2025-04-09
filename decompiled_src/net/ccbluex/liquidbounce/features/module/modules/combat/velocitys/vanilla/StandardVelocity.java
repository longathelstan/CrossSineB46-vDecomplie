/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.vanilla;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/vanilla/StandardVelocity;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/VelocityMode;", "()V", "onVelocityPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class StandardVelocity
extends VelocityMode {
    public StandardVelocity() {
        super("Standard");
    }

    @Override
    public void onVelocityPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> p = event.getPacket();
        if (p instanceof S12PacketEntityVelocity && RandomUtils.nextInt(1, 100) <= ((Number)this.getVelocity().getChance().get()).intValue()) {
            int h = ((Number)this.getVelocity().getHorizontal().get()).intValue();
            int v = ((Number)this.getVelocity().getVertical().get()).intValue();
            if (h == 0 && v == 0) {
                event.cancelEvent();
            }
            ((S12PacketEntityVelocity)p).field_149415_b = ((S12PacketEntityVelocity)p).func_149411_d() * h / 100;
            ((S12PacketEntityVelocity)p).field_149416_c = ((S12PacketEntityVelocity)p).func_149410_e() * v / 100;
            ((S12PacketEntityVelocity)p).field_149414_d = ((S12PacketEntityVelocity)p).func_149409_f() * h / 100;
        }
    }
}

