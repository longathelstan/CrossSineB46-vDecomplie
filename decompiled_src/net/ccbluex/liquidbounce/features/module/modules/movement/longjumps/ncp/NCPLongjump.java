/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.ncp;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.LongJumpMode;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\rH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/ncp/NCPLongjump;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/LongJumpMode;", "()V", "canBoost", "", "ncpBoostValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onEnable", "", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class NCPLongjump
extends LongJumpMode {
    @NotNull
    private final FloatValue ncpBoostValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Boost"), 4.25f, 1.0f, 10.0f);
    private boolean canBoost;

    public NCPLongjump() {
        super("NCP");
    }

    @Override
    public void onEnable() {
        this.canBoost = true;
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
        }
        MovementUtils.INSTANCE.strafe(MovementUtils.INSTANCE.getSpeed() * (this.canBoost ? ((Number)this.ncpBoostValue.get()).floatValue() : 1.0f));
        if (this.canBoost) {
            this.canBoost = false;
        }
    }

    @Override
    public void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.canBoost = true;
    }
}

