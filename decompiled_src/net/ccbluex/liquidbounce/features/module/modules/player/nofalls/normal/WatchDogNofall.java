/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.normal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/normal/WatchDogNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "fallDistance", "", "fallDistanceValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "timed", "", "onMotion", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "CrossSine"})
public final class WatchDogNofall
extends NoFallMode {
    @NotNull
    private final FloatValue fallDistanceValue = new FloatValue("FallDistance", 2.8f, 0.0f, 5.0f);
    private boolean timed;
    private double fallDistance;

    public WatchDogNofall() {
        super("WatchDog");
    }

    @Override
    public void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getEventState() == EventState.PRE) {
            this.fallDistance = MinecraftInstance.mc.field_71439_g.field_70122_E ? 0.0 : (this.fallDistance += RangesKt.coerceAtLeast(-MinecraftInstance.mc.field_71439_g.field_70181_x, 0.0));
            if (!(!(this.fallDistance >= (double)((Number)this.fallDistanceValue.get()).floatValue()) || KillAura.INSTANCE.getState() && KillAura.INSTANCE.getCurrentTarget() != null || SilentAura.INSTANCE.getState() && SilentAura.INSTANCE.getTarget() != null)) {
                MinecraftInstance.mc.field_71428_T.field_74278_d = 0.5f;
                this.timed = true;
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer(true)));
                this.fallDistance = 0.0;
            } else if (this.timed) {
                MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
                this.timed = false;
            }
        }
    }
}

