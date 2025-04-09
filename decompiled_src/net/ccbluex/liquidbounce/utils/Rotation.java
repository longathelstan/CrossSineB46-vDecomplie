/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MinecraftInstanceKt;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\b\u0018\u0000 #2\u00020\u0001:\u0001#B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0010\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0017\u001a\u00020\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u001eJ\b\u0010\u001f\u001a\u00020 H\u0016J\u0012\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\"\u001a\u00020\u0003H\u0002R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t\u00a8\u0006$"}, d2={"Lnet/ccbluex/liquidbounce/utils/Rotation;", "", "yaw", "", "pitch", "(FF)V", "getPitch", "()F", "setPitch", "(F)V", "getYaw", "setYaw", "applyStrafeToPlayer", "", "event", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "component1", "component2", "copy", "equals", "", "other", "fixedSensitivity", "sensitivity", "hashCode", "", "toDirection", "Lnet/minecraft/util/Vec3;", "toPlayer", "player", "Lnet/minecraft/entity/player/EntityPlayer;", "toString", "", "withLimitedPitch", "value", "Companion", "CrossSine"})
public final class Rotation {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private float yaw;
    private float pitch;

    public Rotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public final float getYaw() {
        return this.yaw;
    }

    public final void setYaw(float f) {
        this.yaw = f;
    }

    public final float getPitch() {
        return this.pitch;
    }

    public final void setPitch(float f) {
        this.pitch = f;
    }

    public final void toPlayer(@NotNull EntityPlayer player) {
        Intrinsics.checkNotNullParameter(player, "player");
        if (Float.isNaN(this.yaw) || Float.isNaN(this.pitch)) {
            return;
        }
        player.field_70177_z = this.yaw;
        player.field_70125_A = this.pitch;
    }

    @NotNull
    public final Rotation fixedSensitivity(float sensitivity) {
        float gcd = Rotation.Companion.getFixedAngleDelta(sensitivity);
        this.yaw = Companion.getFixedSensitivityAngle(this.yaw, RotationUtils.serverRotation.yaw, gcd);
        this.pitch = Companion.getFixedSensitivityAngle(this.pitch, RotationUtils.serverRotation.pitch, gcd);
        return Rotation.withLimitedPitch$default(this, 0.0f, 1, null);
    }

    public static /* synthetic */ Rotation fixedSensitivity$default(Rotation rotation, float f, int n, Object object) {
        if ((n & 1) != 0) {
            f = MinecraftInstance.mc.field_71474_y.field_74341_c;
        }
        return rotation.fixedSensitivity(f);
    }

    private final Rotation withLimitedPitch(float value) {
        this.pitch = RangesKt.coerceIn(this.pitch, -value, value);
        return this;
    }

    static /* synthetic */ Rotation withLimitedPitch$default(Rotation rotation, float f, int n, Object object) {
        if ((n & 1) != 0) {
            f = 90.0f;
        }
        return rotation.withLimitedPitch(f);
    }

    public final void applyStrafeToPlayer(@NotNull StrafeEvent event) {
        float d;
        Intrinsics.checkNotNullParameter(event, "event");
        EntityPlayerSP entityPlayerSP = MinecraftInstanceKt.getMc().field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP);
        EntityPlayerSP player = entityPlayerSP;
        int dif = (int)((MathHelper.func_76142_g((float)(player.field_70177_z - this.yaw - 23.5f - (float)135)) + (float)180) / (float)45);
        float yaw = this.yaw;
        float strafe = event.getStrafe();
        float forward = event.getForward();
        float friction = event.getFriction();
        float calcForward = 0.0f;
        float calcStrafe = 0.0f;
        switch (dif) {
            case 0: {
                calcForward = forward;
                calcStrafe = strafe;
                break;
            }
            case 1: {
                calcForward += forward;
                calcStrafe -= forward;
                calcForward += strafe;
                calcStrafe += strafe;
                break;
            }
            case 2: {
                calcForward = strafe;
                calcStrafe = -forward;
                break;
            }
            case 3: {
                calcForward -= forward;
                calcStrafe -= forward;
                calcForward += strafe;
                calcStrafe -= strafe;
                break;
            }
            case 4: {
                calcForward = -forward;
                calcStrafe = -strafe;
                break;
            }
            case 5: {
                calcForward -= forward;
                calcStrafe += forward;
                calcForward -= strafe;
                calcStrafe -= strafe;
                break;
            }
            case 6: {
                calcForward = -strafe;
                calcStrafe = forward;
                break;
            }
            case 7: {
                calcForward += forward;
                calcStrafe += forward;
                calcForward -= strafe;
                calcStrafe += strafe;
            }
        }
        if (calcForward > 1.0f || calcForward < 0.9f && calcForward > 0.3f || calcForward < -1.0f || calcForward > -0.9f && calcForward < -0.3f) {
            calcForward *= 0.5f;
        }
        if (calcStrafe > 1.0f || calcStrafe < 0.9f && calcStrafe > 0.3f || calcStrafe < -1.0f || calcStrafe > -0.9f && calcStrafe < -0.3f) {
            calcStrafe *= 0.5f;
        }
        if ((d = calcStrafe * calcStrafe + calcForward * calcForward) >= 1.0E-4f) {
            if ((d = (float)Math.sqrt(d)) < 1.0f) {
                d = 1.0f;
            }
            d = friction / d;
            float yawSin = (float)Math.sin((float)((double)yaw * Math.PI / (double)180.0f));
            float yawCos = (float)Math.cos((float)((double)yaw * Math.PI / (double)180.0f));
            player.field_70159_w += (double)((calcStrafe *= d) * yawCos) - (double)(calcForward *= d) * (double)yawSin;
            player.field_70179_y += (double)(calcForward * yawCos) + (double)calcStrafe * (double)yawSin;
        }
    }

    @NotNull
    public final Vec3 toDirection() {
        float f = MathHelper.func_76134_b((float)(-this.yaw * ((float)Math.PI / 180) - (float)Math.PI));
        float f1 = MathHelper.func_76126_a((float)(-this.yaw * ((float)Math.PI / 180) - (float)Math.PI));
        float f2 = -MathHelper.func_76134_b((float)(-this.pitch * ((float)Math.PI / 180)));
        float f3 = MathHelper.func_76126_a((float)(-this.pitch * ((float)Math.PI / 180)));
        return new Vec3((double)(f1 * f2), (double)f3, (double)(f * f2));
    }

    @NotNull
    public String toString() {
        return "Rotation(yaw=" + this.yaw + ", pitch=" + this.pitch + ')';
    }

    public final float component1() {
        return this.yaw;
    }

    public final float component2() {
        return this.pitch;
    }

    @NotNull
    public final Rotation copy(float yaw, float pitch) {
        return new Rotation(yaw, pitch);
    }

    public static /* synthetic */ Rotation copy$default(Rotation rotation, float f, float f2, int n, Object object) {
        if ((n & 1) != 0) {
            f = rotation.yaw;
        }
        if ((n & 2) != 0) {
            f2 = rotation.pitch;
        }
        return rotation.copy(f, f2);
    }

    public int hashCode() {
        int result = Float.hashCode(this.yaw);
        result = result * 31 + Float.hashCode(this.pitch);
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Rotation)) {
            return false;
        }
        Rotation rotation = (Rotation)other;
        if (!Intrinsics.areEqual((Object)Float.valueOf(this.yaw), (Object)Float.valueOf(rotation.yaw))) {
            return false;
        }
        return Intrinsics.areEqual((Object)Float.valueOf(this.pitch), (Object)Float.valueOf(rotation.pitch));
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0004H\u0002J\"\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\u00042\b\b\u0002\u0010\t\u001a\u00020\u0004\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/utils/Rotation$Companion;", "", "()V", "getFixedAngleDelta", "", "sensitivity", "getFixedSensitivityAngle", "targetAngle", "startAngle", "gcd", "CrossSine"})
    public static final class Companion {
        private Companion() {
        }

        private final float getFixedAngleDelta(float sensitivity) {
            return (float)Math.pow(sensitivity * 0.6f + 0.2f, 3) * 1.2f;
        }

        static /* synthetic */ float getFixedAngleDelta$default(Companion companion, float f, int n, Object object) {
            if ((n & 1) != 0) {
                f = MinecraftInstance.mc.field_71474_y.field_74341_c;
            }
            return companion.getFixedAngleDelta(f);
        }

        public final float getFixedSensitivityAngle(float targetAngle, float startAngle, float gcd) {
            return startAngle + (float)MathKt.roundToInt((targetAngle - startAngle) / gcd) * gcd;
        }

        public static /* synthetic */ float getFixedSensitivityAngle$default(Companion companion, float f, float f2, float f3, int n, Object object) {
            if ((n & 2) != 0) {
                f2 = 0.0f;
            }
            if ((n & 4) != 0) {
                f3 = net.ccbluex.liquidbounce.utils.Rotation$Companion.getFixedAngleDelta$default(companion, 0.0f, 1, null);
            }
            return companion.getFixedSensitivityAngle(f, f2, f3);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

