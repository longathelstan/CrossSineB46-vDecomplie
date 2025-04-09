/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="MovementFix", category=ModuleCategory.MOVEMENT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004J\b\u0010\u0019\u001a\u00020\u0016H\u0016J\u0010\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0016\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001eR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0006\"\u0004\b\f\u0010\bR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001f"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/MovementFix;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "doFix", "", "getDoFix", "()Z", "setDoFix", "(Z)V", "isOverwrite", "silentFix", "getSilentFix", "setSilentFix", "silentFixValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "tick", "", "getTick", "()I", "setTick", "(I)V", "applyForceStrafe", "", "isSilent", "runStrafeFix", "onDisable", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "runStrafeFixLoop", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "CrossSine"})
public final class MovementFix
extends Module {
    @NotNull
    public static final MovementFix INSTANCE = new MovementFix();
    @NotNull
    private static final BoolValue silentFixValue = new BoolValue("Silent", true);
    private static boolean silentFix;
    private static boolean doFix;
    private static boolean isOverwrite;
    private static int tick;

    private MovementFix() {
    }

    public final boolean getSilentFix() {
        return silentFix;
    }

    public final void setSilentFix(boolean bl) {
        silentFix = bl;
    }

    public final boolean getDoFix() {
        return doFix;
    }

    public final void setDoFix(boolean bl) {
        doFix = bl;
    }

    public final int getTick() {
        return tick;
    }

    public final void setTick(int n) {
        tick = n;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!isOverwrite) {
            silentFix = (Boolean)silentFixValue.get();
            doFix = true;
        }
    }

    @Override
    public void onDisable() {
        doFix = false;
    }

    public final void applyForceStrafe(boolean isSilent, boolean runStrafeFix) {
        silentFix = isSilent;
        doFix = runStrafeFix;
        isOverwrite = true;
        tick = 2;
    }

    public final void runStrafeFixLoop(boolean isSilent, @NotNull StrafeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.isCancelled()) {
            return;
        }
        Rotation rotation = RotationUtils.targetRotation;
        if (rotation == null) {
            return;
        }
        float yaw = rotation.component1();
        float strafe = event.getStrafe();
        float forward = event.getForward();
        float friction = event.getFriction();
        float factor = strafe * strafe + forward * forward;
        int angleDiff = (int)(((double)MathHelper.func_76142_g((float)(MinecraftInstance.mc.field_71439_g.field_70177_z - yaw - 22.5f - 135.0f)) + 180.0) / 45.0);
        float calcYaw = isSilent ? yaw + 45.0f * (float)angleDiff : yaw;
        float calcMoveDir = RangesKt.coerceAtLeast(Math.abs(strafe), Math.abs(forward));
        calcMoveDir *= calcMoveDir;
        float calcMultiplier = MathHelper.func_76129_c((float)(calcMoveDir / RangesKt.coerceAtMost(1.0f, calcMoveDir * 2.0f)));
        if (isSilent) {
            switch (angleDiff) {
                case 1: 
                case 3: 
                case 5: 
                case 7: 
                case 9: {
                    if (!(!((double)Math.abs(forward) > 0.005) && !((double)Math.abs(strafe) > 0.005) || (double)Math.abs(forward) > 0.005 && (double)Math.abs(strafe) > 0.005)) {
                        friction /= calcMultiplier;
                        break;
                    }
                    if (!((double)Math.abs(forward) > 0.005) || !((double)Math.abs(strafe) > 0.005)) break;
                    friction *= calcMultiplier;
                }
            }
        }
        if (factor >= 1.0E-4f) {
            if ((factor = MathHelper.func_76129_c((float)factor)) < 1.0f) {
                factor = 1.0f;
            }
            factor = friction / factor;
            float yawSin = MathHelper.func_76126_a((float)((float)((double)calcYaw * Math.PI / (double)180.0f)));
            float yawCos = MathHelper.func_76134_b((float)((float)((double)calcYaw * Math.PI / (double)180.0f)));
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70159_w += (double)((strafe *= factor) * yawCos - (forward *= factor) * yawSin);
            entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70179_y += (double)(forward * yawCos + strafe * yawSin);
        }
        event.cancelEvent();
    }
}

