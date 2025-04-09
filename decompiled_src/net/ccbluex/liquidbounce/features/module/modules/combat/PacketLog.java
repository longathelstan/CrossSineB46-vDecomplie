/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.Packet;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/PacketLog;", "", "packet", "Lnet/minecraft/network/Packet;", "time", "", "(Lnet/minecraft/network/Packet;J)V", "getPacket", "()Lnet/minecraft/network/Packet;", "getTime", "()J", "CrossSine"})
public final class PacketLog {
    @NotNull
    private final Packet<?> packet;
    private final long time;

    public PacketLog(@NotNull Packet<?> packet, long time) {
        Intrinsics.checkNotNullParameter(packet, "packet");
        this.packet = packet;
        this.time = time;
    }

    @NotNull
    public final Packet<?> getPacket() {
        return this.packet;
    }

    public final long getTime() {
        return this.time;
    }
}

