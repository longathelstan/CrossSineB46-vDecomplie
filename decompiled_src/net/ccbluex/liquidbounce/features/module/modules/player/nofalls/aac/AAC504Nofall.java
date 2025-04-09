/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/aac/AAC504Nofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "isDmgFalling", "", "onNoFall", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class AAC504Nofall
extends NoFallMode {
    private boolean isDmgFalling;

    public AAC504Nofall() {
        super("AAC5.0.4");
    }

    @Override
    public void onNoFall(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_70143_R > 3.0f) {
            this.isDmgFalling = true;
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof C03PacketPlayer && this.isDmgFalling && ((C03PacketPlayer)event.getPacket()).field_149474_g && MinecraftInstance.mc.field_71439_g.field_70122_E) {
            this.isDmgFalling = false;
            ((C03PacketPlayer)event.getPacket()).field_149474_g = true;
            MinecraftInstance.mc.field_71439_g.field_70122_E = false;
            Packet<?> packet = event.getPacket();
            ((C03PacketPlayer)packet).field_149477_b += 1.0;
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(((C03PacketPlayer)event.getPacket()).field_149479_a, ((C03PacketPlayer)event.getPacket()).field_149477_b - 1.0784, ((C03PacketPlayer)event.getPacket()).field_149478_c, false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(((C03PacketPlayer)event.getPacket()).field_149479_a, ((C03PacketPlayer)event.getPacket()).field_149477_b - 0.5, ((C03PacketPlayer)event.getPacket()).field_149478_c, true));
        }
    }
}

