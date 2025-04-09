/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.matrix;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.entity.EntityPlayerSP;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/matrix/MatrixReduceVelocity;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/VelocityMode;", "()V", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class MatrixReduceVelocity
extends VelocityMode {
    public MatrixReduceVelocity() {
        super("MatrixReduce");
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_70737_aN > 0) {
            if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                EntityPlayerSP entityPlayerSP;
                if (MinecraftInstance.mc.field_71439_g.field_70737_aN <= 6) {
                    entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP.field_70159_w *= 0.7;
                    entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP.field_70179_y *= 0.7;
                }
                if (MinecraftInstance.mc.field_71439_g.field_70737_aN <= 5) {
                    entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP.field_70159_w *= 0.8;
                    entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP.field_70179_y *= 0.8;
                }
            } else if (MinecraftInstance.mc.field_71439_g.field_70737_aN <= 10) {
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70159_w *= 0.6;
                entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70179_y *= 0.6;
            }
        }
    }
}

