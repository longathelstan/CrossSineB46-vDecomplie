/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\b"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/other/VulcanVelocity;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/VelocityMode;", "()V", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onVelocityPacket", "CrossSine"})
public final class VulcanVelocity
extends VelocityMode {
    public VulcanVelocity() {
        super("Vulcan");
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        short transUID;
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> p = event.getPacket();
        if (p instanceof C0FPacketConfirmTransaction && (transUID = ((C0FPacketConfirmTransaction)p).field_149534_b) >= -31767 && transUID <= -30769) {
            event.cancelEvent();
        }
    }

    @Override
    public void onVelocityPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        event.cancelEvent();
    }
}

