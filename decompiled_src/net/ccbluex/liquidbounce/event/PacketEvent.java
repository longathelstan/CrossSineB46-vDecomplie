/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.CancellableEvent;
import net.minecraft.network.Packet;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\rB\u0019\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000b\u001a\u00020\fR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000e"}, d2={"Lnet/ccbluex/liquidbounce/event/PacketEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "packet", "Lnet/minecraft/network/Packet;", "type", "Lnet/ccbluex/liquidbounce/event/PacketEvent$Type;", "(Lnet/minecraft/network/Packet;Lnet/ccbluex/liquidbounce/event/PacketEvent$Type;)V", "getPacket", "()Lnet/minecraft/network/Packet;", "getType", "()Lnet/ccbluex/liquidbounce/event/PacketEvent$Type;", "isServerSide", "", "Type", "CrossSine"})
public final class PacketEvent
extends CancellableEvent {
    @NotNull
    private final Packet<?> packet;
    @NotNull
    private final Type type;

    public PacketEvent(@NotNull Packet<?> packet, @NotNull Type type) {
        Intrinsics.checkNotNullParameter(packet, "packet");
        Intrinsics.checkNotNullParameter((Object)type, "type");
        this.packet = packet;
        this.type = type;
    }

    @NotNull
    public final Packet<?> getPacket() {
        return this.packet;
    }

    @NotNull
    public final Type getType() {
        return this.type;
    }

    public final boolean isServerSide() {
        return this.type == Type.RECEIVE;
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lnet/ccbluex/liquidbounce/event/PacketEvent$Type;", "", "(Ljava/lang/String;I)V", "RECEIVE", "SEND", "CrossSine"})
    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type RECEIVE = new Type();
        public static final /* enum */ Type SEND = new Type();
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String value) {
            return Enum.valueOf(Type.class, value);
        }

        static {
            $VALUES = typeArray = new Type[]{Type.RECEIVE, Type.SEND};
        }
    }
}

