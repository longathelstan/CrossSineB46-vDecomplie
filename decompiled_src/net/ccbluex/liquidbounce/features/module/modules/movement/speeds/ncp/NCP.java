/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\b\u0010\n\u001a\u00020\bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/ncp/NCP;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "damageboostvalue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "timerValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onDisable", "", "onEnable", "onUpdate", "CrossSine"})
public final class NCP
extends SpeedMode {
    @NotNull
    private final BoolValue damageboostvalue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "DamageBoost"), false);
    @NotNull
    private final FloatValue timerValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Timer"), 1.088f, 1.0f, 1.1f);

    public NCP() {
        super("NCP");
    }

    @Override
    public void onEnable() {
        MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.timerValue.get()).floatValue();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.2f;
        MinecraftInstance.mc.field_71439_g.func_70016_h(MinecraftInstance.mc.field_71439_g.field_70159_w / (double)3, MinecraftInstance.mc.field_71439_g.field_70181_x, MinecraftInstance.mc.field_71439_g.field_70179_y / (double)3);
        super.onDisable();
    }

    @Override
    public void onUpdate() {
        if (((Boolean)this.damageboostvalue.get()).booleanValue() && MinecraftInstance.mc.field_71439_g.field_70737_aN > 0) {
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70159_w *= 1.018 - Math.random() / (double)100;
            entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70179_y *= 1.018 - Math.random() / (double)100;
        }
        if (MovementUtils.INSTANCE.isMoving()) {
            MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.02f;
            if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                MovementUtils.jump$default(MovementUtils.INSTANCE, false, false, 0.0, 6, null);
            }
            MovementUtils.INSTANCE.strafe(Math.max(MovementUtils.INSTANCE.getSpeed(), (float)MovementUtils.INSTANCE.getSpeedWithPotionEffects(0.27)));
        } else {
            MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
        }
    }
}

