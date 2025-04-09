/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Reach", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\u0014R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0015"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Reach;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "ReachMax", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "getReachMax", "()Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "ReachMin", "getReachMin", "tag", "", "getTag", "()Ljava/lang/String;", "throughWall", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getThroughWall", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "call", "", "getReach", "", "CrossSine"})
public final class Reach
extends Module {
    @NotNull
    public static final Reach INSTANCE = new Reach();
    @NotNull
    private static final FloatValue ReachMax = new FloatValue(){

        protected void onChanged(float oldValue, float newValue) {
            float v = ((Number)Reach.INSTANCE.getReachMin().get()).floatValue();
            if (v > newValue) {
                this.set(Float.valueOf(v));
            }
        }
    };
    @NotNull
    private static final FloatValue ReachMin = new FloatValue(){

        protected void onChanged(float oldValue, float newValue) {
            float v = ((Number)Reach.INSTANCE.getReachMax().get()).floatValue();
            if (v < newValue) {
                this.set(Float.valueOf(v));
            }
        }
    };
    @NotNull
    private static final BoolValue throughWall = new BoolValue("Through-Wall", false);

    private Reach() {
    }

    @NotNull
    public final FloatValue getReachMax() {
        return ReachMax;
    }

    @NotNull
    public final FloatValue getReachMin() {
        return ReachMin;
    }

    @NotNull
    public final BoolValue getThroughWall() {
        return throughWall;
    }

    public final double getReach() {
        double min = RangesKt.coerceAtMost(((Number)ReachMin.get()).floatValue(), ((Number)ReachMax.get()).floatValue());
        double max = RangesKt.coerceAtLeast(((Number)ReachMin.get()).floatValue(), ((Number)ReachMax.get()).floatValue());
        return Math.random() * (max - min) + min;
    }

    @Override
    @NotNull
    public String getTag() {
        return new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.ENGLISH)).format(ReachMax.get()) + " - " + new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.ENGLISH)).format(ReachMin.get());
    }

    public final boolean call() {
        BlockPos p;
        if (!((Boolean)throughWall.get()).booleanValue() && MinecraftInstance.mc.field_71476_x != null && MinecraftInstance.mc.field_71476_x != null && (p = MinecraftInstance.mc.field_71476_x.func_178782_a()) != null && !Intrinsics.areEqual(MinecraftInstance.mc.field_71441_e.func_180495_p(p).func_177230_c(), Blocks.field_150350_a)) {
            return false;
        }
        double r = this.getReach();
        Object[] o = PlayerUtils.INSTANCE.getEntity(r, 0.0);
        if (o == null) {
            return false;
        }
        Object object = o[0];
        if (object == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.entity.Entity");
        }
        Entity en = (Entity)object;
        Object object2 = o[1];
        if (object2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.util.Vec3");
        }
        MinecraftInstance.mc.field_71476_x = new MovingObjectPosition(en, (Vec3)object2);
        MinecraftInstance.mc.field_147125_j = en;
        return true;
    }
}

