/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.phases.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.phases.PhaseMode;
import net.ccbluex.liquidbounce.utils.BlinkUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/other/WatchDogPhase;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/PhaseMode;", "()V", "startBlink", "", "timerMS", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class WatchDogPhase
extends PhaseMode {
    private boolean startBlink;
    @NotNull
    private TimerMS timerMS = new TimerMS();

    public WatchDogPhase() {
        super("WatchDog");
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        String chat;
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof S02PacketChat && Intrinsics.areEqual(chat = ((S02PacketChat)packet).func_148915_c().func_150260_c(), "The games start in 3 seconds!")) {
            this.timerMS.reset();
            this.startBlink = true;
            BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, null);
            MinecraftInstance.mc.field_71441_e.func_175698_g(new BlockPos((Entity)MinecraftInstance.mc.field_71439_g).func_177977_b());
        }
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.timerMS.hasTimePassed(3400L)) {
            BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
            this.startBlink = false;
        }
    }
}

