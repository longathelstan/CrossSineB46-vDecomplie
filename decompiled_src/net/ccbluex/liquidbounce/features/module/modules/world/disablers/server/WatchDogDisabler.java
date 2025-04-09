/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.disablers.server;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.modules.client.impl.DynamicIsland;
import net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.animation.Animation;
import net.ccbluex.liquidbounce.utils.animation.Easing;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0017H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/server/WatchDogDisabler;", "Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/DisablerMode;", "()V", "animation", "Lnet/ccbluex/liquidbounce/utils/animation/Animation;", "done", "", "dotCount", "", "flagged", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "onDisable", "", "onEnable", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"})
public final class WatchDogDisabler
extends DisablerMode {
    private int flagged;
    private boolean done;
    @NotNull
    private Animation animation = new Animation(Easing.LINEAR, 250L);
    private int dotCount;
    @NotNull
    private TimerMS timer = new TimerMS();

    public WatchDogDisabler() {
        super("WatchDog");
    }

    @Override
    public void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.isPre()) {
            if (this.done || MinecraftInstance.mc.field_71439_g == null || MinecraftInstance.mc.field_71439_g.field_70173_aa < 20) {
                return;
            }
            if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                    MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
                }
            } else if (PlayerUtils.getOffGroundTicks() >= 9) {
                if (PlayerUtils.getOffGroundTicks() % 2 == 0) {
                    event.setZ(event.getZ() + (double)RandomUtils.INSTANCE.nextFloat(0.09f, 0.12f));
                }
                MovementUtils.INSTANCE.resetMotion(true);
            }
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof S08PacketPlayerPosLook && !this.done) {
            int n = this.flagged;
            this.flagged = n + 1;
            if (this.flagged == 20) {
                this.done = true;
                this.flagged = 0;
                DynamicIsland.INSTANCE.setDisabler(null);
                this.done = true;
            }
        }
    }

    @Override
    public void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.done = false;
        this.flagged = 0;
        this.animation.value = 0.0;
    }

    @Override
    public void onDisable() {
        this.done = false;
    }

    @Override
    public void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.animation.run(this.flagged);
        if (!this.done) {
            DynamicIsland.INSTANCE.setDisabler(Float.valueOf((float)this.animation.value / 20.0f));
        }
        if (this.timer.hasTimePassed(2500L)) {
            int n = this.dotCount;
            this.dotCount = n + 1;
        }
        if (this.dotCount >= 4 && this.timer.hasTimePassed(2500L)) {
            this.dotCount = 0;
        }
    }

    @Override
    public void onEnable() {
        this.done = false;
        this.flagged = 0;
    }
}

