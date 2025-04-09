/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.TeleportEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="NoRotate", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\rH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/NoRotate;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "pitch", "", "teleport", "", "yaw", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onTeleport", "Lnet/ccbluex/liquidbounce/event/TeleportEvent;", "CrossSine"})
public final class NoRotate
extends Module {
    private float yaw;
    private float pitch;
    private boolean teleport;

    @EventTarget
    public final void onTeleport(@NotNull TeleportEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.yaw = event.getYaw();
        this.pitch = event.getPitch();
        event.setYaw(MinecraftInstance.mc.field_71439_g.field_70177_z);
        event.setPitch(MinecraftInstance.mc.field_71439_g.field_70125_A);
        this.teleport = true;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (this.teleport && packet instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
            ((C03PacketPlayer.C06PacketPlayerPosLook)packet).field_149476_e = this.yaw;
            ((C03PacketPlayer.C06PacketPlayerPosLook)packet).field_149473_f = this.pitch;
            this.teleport = false;
        }
    }
}

