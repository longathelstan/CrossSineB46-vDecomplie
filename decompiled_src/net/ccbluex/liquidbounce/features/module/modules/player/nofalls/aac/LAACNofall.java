/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\rH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/aac/LAACNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "jumped", "", "onEnable", "", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onNoFall", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onUpdate", "CrossSine"})
public final class LAACNofall
extends NoFallMode {
    private boolean jumped;

    public LAACNofall() {
        super("LAAC");
    }

    @Override
    public void onEnable() {
        this.jumped = false;
    }

    @Override
    public void onNoFall(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!(this.jumped || !MinecraftInstance.mc.field_71439_g.field_70122_E || MinecraftInstance.mc.field_71439_g.func_70617_f_() || MinecraftInstance.mc.field_71439_g.func_70090_H() || MinecraftInstance.mc.field_71439_g.field_70134_J)) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = -6.0;
        }
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            this.jumped = false;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70181_x > 0.0) {
            this.jumped = true;
        }
    }

    @Override
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!(this.jumped || MinecraftInstance.mc.field_71439_g.field_70122_E || MinecraftInstance.mc.field_71439_g.func_70617_f_() || MinecraftInstance.mc.field_71439_g.func_70090_H() || MinecraftInstance.mc.field_71439_g.field_70134_J || !(MinecraftInstance.mc.field_71439_g.field_70181_x < 0.0))) {
            event.setX(0.0);
            event.setZ(0.0);
        }
    }

    @Override
    public void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.jumped = true;
    }
}

