/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.watchdog;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.minecraft.potion.Potion;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/watchdog/WatchDogLowHop;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "getSpeed", "", "onUpdate", "", "CrossSine"})
public final class WatchDogLowHop
extends SpeedMode {
    public WatchDogLowHop() {
        super("WatchDogLowHop");
    }

    @Override
    public void onUpdate() {
        if (!(!MovementUtils.INSTANCE.isMoving() || Scaffold.INSTANCE.getState() && Scaffold.INSTANCE.getTowerStatus())) {
            if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                MovementUtils.INSTANCE.setMotion(this.getSpeed());
                MinecraftInstance.mc.field_71439_g.field_70181_x = 0.4191;
            }
            if (PlayerUtils.getOffGroundTicks() == 5 && MinecraftInstance.mc.field_71439_g.field_70737_aN == 0) {
                MinecraftInstance.mc.field_71439_g.field_70181_x = MovementUtils.INSTANCE.predictedMotion(MinecraftInstance.mc.field_71439_g.field_70181_x, 2);
            }
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

