/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.watchdog;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.potion.Potion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0006\u001a\u00020\u0007J\b\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u0005\u00a8\u0006\r"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/watchdog/WatchDog;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "offGround", "", "Ljava/lang/Integer;", "getSpeed", "", "onEnable", "", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "CrossSine"})
public final class WatchDog
extends SpeedMode {
    @Nullable
    private Integer offGround;

    public WatchDog() {
        super("WatchDog");
    }

    @Override
    public void onEnable() {
        this.offGround = null;
    }

    @Override
    public void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MovementUtils.INSTANCE.isMoving() && MinecraftInstance.mc.field_71439_g.field_70122_E) {
            MovementUtils.INSTANCE.setMotion(this.getSpeed());
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.4191;
        }
    }

    public final float getSpeed() {
        if (MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
            if (MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c() == 0) {
                return 0.5f;
            }
            if (MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c() == 1) {
                return 0.53f;
            }
        }
        return 0.48f;
    }
}

