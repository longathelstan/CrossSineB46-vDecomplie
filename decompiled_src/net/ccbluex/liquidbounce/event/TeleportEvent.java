/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.CancellableEvent;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0015\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0002\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\n\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0015\"\u0004\b\u0019\u0010\u0017R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0011\"\u0004\b\u001b\u0010\u0013R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0015\"\u0004\b\u001d\u0010\u0017\u00a8\u0006\u001e"}, d2={"Lnet/ccbluex/liquidbounce/event/TeleportEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "packetIn", "Lnet/minecraft/network/play/client/C03PacketPlayer$C06PacketPlayerPosLook;", "x", "", "y", "z", "yaw", "", "pitch", "(Lnet/minecraft/network/play/client/C03PacketPlayer$C06PacketPlayerPosLook;DDDFF)V", "getPacketIn", "()Lnet/minecraft/network/play/client/C03PacketPlayer$C06PacketPlayerPosLook;", "setPacketIn", "(Lnet/minecraft/network/play/client/C03PacketPlayer$C06PacketPlayerPosLook;)V", "getPitch", "()F", "setPitch", "(F)V", "getX", "()D", "setX", "(D)V", "getY", "setY", "getYaw", "setYaw", "getZ", "setZ", "CrossSine"})
public final class TeleportEvent
extends CancellableEvent {
    @NotNull
    private C03PacketPlayer.C06PacketPlayerPosLook packetIn;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public TeleportEvent(@NotNull C03PacketPlayer.C06PacketPlayerPosLook packetIn, double x, double y, double z, float yaw, float pitch) {
        Intrinsics.checkNotNullParameter(packetIn, "packetIn");
        this.packetIn = packetIn;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @NotNull
    public final C03PacketPlayer.C06PacketPlayerPosLook getPacketIn() {
        return this.packetIn;
    }

    public final void setPacketIn(@NotNull C03PacketPlayer.C06PacketPlayerPosLook c06PacketPlayerPosLook) {
        Intrinsics.checkNotNullParameter(c06PacketPlayerPosLook, "<set-?>");
        this.packetIn = c06PacketPlayerPosLook;
    }

    public final double getX() {
        return this.x;
    }

    public final void setX(double d) {
        this.x = d;
    }

    public final double getY() {
        return this.y;
    }

    public final void setY(double d) {
        this.y = d;
    }

    public final double getZ() {
        return this.z;
    }

    public final void setZ(double d) {
        this.z = d;
    }

    public final float getYaw() {
        return this.yaw;
    }

    public final void setYaw(float f) {
        this.yaw = f;
    }

    public final float getPitch() {
        return this.pitch;
    }

    public final void setPitch(float f) {
        this.pitch = f;
    }
}

