/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.disablers.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0017\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/other/C08PacketPlayerBlockPlacementDisabler;", "Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/DisablerMode;", "()V", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class C08PacketPlayerBlockPlacementDisabler
extends DisablerMode {
    public C08PacketPlayerBlockPlacementDisabler() {
        super("C08PacketPlayerBlockPlacement");
    }

    @Override
    @EventTarget
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C08PacketPlayerBlockPlacement) {
            event.cancelEvent();
        }
    }
}

