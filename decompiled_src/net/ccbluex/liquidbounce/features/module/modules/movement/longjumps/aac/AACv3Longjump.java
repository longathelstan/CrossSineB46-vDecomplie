/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.LongJumpMode;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/aac/AACv3Longjump;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/LongJumpMode;", "()V", "teleported", "", "tpdistance", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onEnable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class AACv3Longjump
extends LongJumpMode {
    @NotNull
    private final FloatValue tpdistance = new FloatValue("TpDistance", 3.0f, 1.0f, 6.0f);
    private boolean teleported;

    public AACv3Longjump() {
        super("AACv3");
    }

    @Override
    public void onEnable() {
        this.teleported = false;
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_70143_R > 0.5f && !this.teleported) {
            double value = ((Number)this.tpdistance.get()).floatValue();
            double x = 0.0;
            double z = 0.0;
            EnumFacing enumFacing = MinecraftInstance.mc.field_71439_g.func_174811_aO();
            switch (enumFacing == null ? -1 : WhenMappings.$EnumSwitchMapping$0[enumFacing.ordinal()]) {
                case 1: {
                    z = -value;
                    break;
                }
                case 2: {
                    x = value;
                    break;
                }
                case 3: {
                    z = value;
                    break;
                }
                case 4: {
                    x = -value;
                }
            }
            MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t + x, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v + z);
            this.teleported = true;
        }
    }

    @Metadata(mv={1, 6, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[EnumFacing.values().length];
            nArray[EnumFacing.NORTH.ordinal()] = 1;
            nArray[EnumFacing.EAST.ordinal()] = 2;
            nArray[EnumFacing.SOUTH.ordinal()] = 3;
            nArray[EnumFacing.WEST.ordinal()] = 4;
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

