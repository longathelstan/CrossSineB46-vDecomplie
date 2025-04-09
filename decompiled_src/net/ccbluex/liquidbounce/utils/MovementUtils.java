/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.visual.FreeLook;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSign;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u001d\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000bJ\u000e\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0004J\b\u0010\u0018\u001a\u00020\u0004H\u0002J\u0006\u0010\u0019\u001a\u00020\u0004J0\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020 2\b\b\u0002\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\u00142\u0006\u0010$\u001a\u00020\u0004J\u0006\u0010%\u001a\u00020\u0004J\b\u0010&\u001a\u00020\u0004H\u0007J\u000e\u0010'\u001a\u00020\u00042\u0006\u0010(\u001a\u00020)J\u0006\u0010*\u001a\u00020\u000bJ\u000e\u0010*\u001a\u00020\u000b2\u0006\u0010+\u001a\u00020\u001cJ\u0006\u0010,\u001a\u00020\"J\u000e\u0010-\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004J\u0006\u0010.\u001a\u00020\u0014J\u0006\u0010/\u001a\u000200J\u0006\u00101\u001a\u000200J\u0006\u00102\u001a\u000200J\u000e\u00103\u001a\u0002002\u0006\u00104\u001a\u00020\u0004J\u0006\u00105\u001a\u000200J\"\u00106\u001a\u00020\u00142\u0006\u00107\u001a\u0002002\b\b\u0002\u00108\u001a\u0002002\b\b\u0002\u0010\u0017\u001a\u00020\u0004J\u000e\u00106\u001a\u00020\u00142\u0006\u00109\u001a\u00020 J\u0006\u0010\n\u001a\u00020\u0004J\u000e\u0010:\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000bJ\u000e\u0010;\u001a\u00020\u00142\u0006\u0010<\u001a\u00020\u000bJ\u0006\u0010=\u001a\u00020\u0014J\u000e\u0010=\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000bJ\u000e\u0010>\u001a\u00020\u00042\u0006\u00108\u001a\u00020\u0004J\u0016\u0010>\u001a\u00020\u00042\u0006\u00108\u001a\u00020\u00042\u0006\u0010?\u001a\u00020\"J\u000e\u0010@\u001a\u00020\u00142\u0006\u0010A\u001a\u000200J\u000e\u0010B\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0004J\u0016\u0010C\u001a\u00020\u00142\u0006\u0010D\u001a\u00020\u00042\u0006\u0010E\u001a\u00020\u000bJ\u000e\u0010F\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000bJ.\u0010F\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010G\u001a\u00020\u00042\u0006\u0010H\u001a\u00020\u000b2\u0006\u0010I\u001a\u00020\u00042\u0006\u0010J\u001a\u00020\u0004J\u0006\u0010K\u001a\u00020\u0014J\u000e\u0010K\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0004J\u000e\u0010K\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000bJ\u0016\u0010K\u001a\u00020\u00142\u0006\u00109\u001a\u00020 2\u0006\u0010\u0015\u001a\u00020\u000bJ\u0006\u0010L\u001a\u00020\u0014R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\u0007R\u0011\u0010\n\u001a\u00020\u000b8F\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0011\u001a\u00020\u000b8F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\r\u00a8\u0006M"}, d2={"Lnet/ccbluex/liquidbounce/utils/MovementUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "<set-?>", "", "bps", "getBps", "()D", "direction", "getDirection", "jumpMotion", "", "getJumpMotion", "()F", "lastX", "lastY", "lastZ", "movingYaw", "getMovingYaw", "FlyBasic", "", "speed", "JumpBoost", "motionY", "calculateGround", "defaultSpeed", "doTargetStrafe", "curTarget", "Lnet/minecraft/entity/EntityLivingBase;", "direction_", "radius", "moveEvent", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "mathRadius", "", "forward", "length", "getBaseMoveSpeed", "getDirectioon", "getFallDistance", "entity", "Lnet/minecraft/entity/Entity;", "getSpeed", "e", "getSpeedAmplifier", "getSpeedWithPotionEffects", "handleVanillaKickBypass", "hasMotion", "", "isBlockUnder", "isMoving", "isOnGround", "height", "isStrafing", "jump", "checkSpeed", "motion", "event", "limitSpeed", "limitSpeedByPercent", "percent", "move", "predictedMotion", "ticks", "resetMotion", "y", "setMotion", "setMotion2", "d", "f", "setSpeed", "moveSpeed", "pseudoYaw", "pseudoStrafe", "pseudoForward", "strafe", "updateBlocksPerSecond", "CrossSine"})
public final class MovementUtils
extends MinecraftInstance {
    @NotNull
    public static final MovementUtils INSTANCE = new MovementUtils();
    private static double bps;
    private static double lastX;
    private static double lastY;
    private static double lastZ;

    private MovementUtils() {
    }

    public final void resetMotion(boolean y) {
        MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
        MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
        if (y) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
        }
    }

    public final double predictedMotion(double motion) {
        return (motion - 0.08) * (double)0.98f;
    }

    public final double getFallDistance(@NotNull Entity entity) {
        int n;
        Intrinsics.checkNotNullParameter(entity, "entity");
        double fallDist = -1.0;
        Vec3 pos = new Vec3(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
        int y = (int)Math.floor(pos.field_72448_b);
        if ((int)(pos.field_72448_b % 1.0) == 0) {
            n = y;
            y = n + -1;
        }
        if (0 <= (n = y)) {
            int i;
            do {
                i = n--;
                Block block = BlockUtils.getBlock(new BlockPos((int)Math.floor(pos.field_72450_a), i, (int)Math.floor(pos.field_72449_c)));
                if (block instanceof BlockAir || block instanceof BlockSign) continue;
                fallDist = y - i;
                break;
            } while (i != 0);
        }
        return fallDist;
    }

    public final double predictedMotion(double motion, int ticks) {
        if (ticks == 0) {
            return motion;
        }
        double predicted = motion;
        int n = 0;
        while (n < ticks) {
            int i = n++;
            predicted = (predicted - 0.08) * (double)0.98f;
        }
        return predicted;
    }

    public final float getSpeed() {
        return (float)Math.sqrt(MinecraftInstance.mc.field_71439_g.field_70159_w * MinecraftInstance.mc.field_71439_g.field_70159_w + MinecraftInstance.mc.field_71439_g.field_70179_y * MinecraftInstance.mc.field_71439_g.field_70179_y);
    }

    public final void setSpeed(float speed) {
        float currentSpeed = this.getSpeed();
        if (currentSpeed > 0.0f) {
            float factor = speed / currentSpeed;
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70159_w *= (double)factor;
            entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70179_y *= (double)factor;
        }
    }

    public final void FlyBasic(float speed) {
        EntityPlayerSP entityPlayerSP;
        if (MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
            entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70181_x += (double)speed;
        }
        if (MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d()) {
            entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70181_x -= (double)speed;
        }
        this.strafe(speed);
    }

    public final void jump(boolean checkSpeed, boolean motion, double motionY) {
        if (!(MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d() || checkSpeed && Speed.INSTANCE.getState())) {
            if (motion) {
                MinecraftInstance.mc.field_71439_g.field_70181_x = motionY;
            } else {
                MinecraftInstance.mc.field_71439_g.func_70664_aZ();
            }
        }
    }

    public static /* synthetic */ void jump$default(MovementUtils movementUtils, boolean bl, boolean bl2, double d, int n, Object object) {
        if ((n & 2) != 0) {
            bl2 = false;
        }
        if ((n & 4) != 0) {
            d = 0.42;
        }
        movementUtils.jump(bl, bl2, d);
    }

    public final void jump(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        double jumpY = MinecraftInstance.mc.field_71439_g.field_70747_aH;
        if (MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76430_j)) {
            jumpY += (double)((float)(MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76430_j).func_76458_c() + 1) * 0.1f);
        }
        MinecraftInstance.mc.field_71439_g.field_70181_x = jumpY;
        event.setY(MinecraftInstance.mc.field_71439_g.field_70181_x);
    }

    public final double getSpeedWithPotionEffects(double speed) {
        double d;
        PotionEffect potionEffect = MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76424_c);
        if (potionEffect == null) {
            d = speed;
        } else {
            double d2;
            PotionEffect it = potionEffect;
            boolean bl = false;
            d = d2 = speed * (1.0 + (double)(it.func_76458_c() + 1) * 0.2);
        }
        return d;
    }

    public final void strafe() {
        this.strafe(this.getSpeed());
    }

    public final void move() {
        this.move(this.getSpeed());
    }

    public final int getSpeedAmplifier() {
        return MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c) ? 1 + MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c() : 0;
    }

    public final boolean isMoving() {
        return MinecraftInstance.mc.field_71439_g != null && (!(MinecraftInstance.mc.field_71439_g.field_71158_b.field_78900_b == 0.0f) || !(MinecraftInstance.mc.field_71439_g.field_71158_b.field_78902_a == 0.0f));
    }

    public final boolean isStrafing() {
        return MinecraftInstance.mc.field_71439_g != null && !(MinecraftInstance.mc.field_71439_g.field_71158_b.field_78902_a == 0.0f);
    }

    public final boolean hasMotion() {
        return !(MinecraftInstance.mc.field_71439_g.field_70159_w == 0.0) && !(MinecraftInstance.mc.field_71439_g.field_70179_y == 0.0) && !(MinecraftInstance.mc.field_71439_g.field_70181_x == 0.0);
    }

    public final void strafe(float speed) {
        if (!this.isMoving()) {
            return;
        }
        MinecraftInstance.mc.field_71439_g.field_70159_w = -Math.sin(this.getDirection()) * (double)speed;
        MinecraftInstance.mc.field_71439_g.field_70179_y = Math.cos(this.getDirection()) * (double)speed;
    }

    public final void strafe(@NotNull MoveEvent event, float speed) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.isMoving()) {
            return;
        }
        event.setX(-Math.sin(this.getDirection()) * (double)speed);
        event.setZ(Math.cos(this.getDirection()) * (double)speed);
    }

    public final void strafe(double speed) {
        if (!this.isMoving()) {
            return;
        }
        MinecraftInstance.mc.field_71439_g.field_70159_w = -Math.sin(this.getDirection()) * speed;
        MinecraftInstance.mc.field_71439_g.field_70179_y = Math.cos(this.getDirection()) * speed;
    }

    public final double defaultSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.func_71410_x().field_71439_g.func_70644_a(Potion.field_76424_c)) {
            int amplifier = Minecraft.func_71410_x().field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c();
            baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }

    public final float getSpeed(@NotNull EntityLivingBase e) {
        Intrinsics.checkNotNullParameter(e, "e");
        return (float)Math.sqrt((e.field_70165_t - e.field_70169_q) * (e.field_70165_t - e.field_70169_q) + (e.field_70161_v - e.field_70166_s) * (e.field_70161_v - e.field_70166_s));
    }

    public final void doTargetStrafe(@NotNull EntityLivingBase curTarget, float direction_, float radius, @NotNull MoveEvent moveEvent, int mathRadius) {
        Intrinsics.checkNotNullParameter(curTarget, "curTarget");
        Intrinsics.checkNotNullParameter(moveEvent, "moveEvent");
        if (!this.isMoving()) {
            return;
        }
        double forward_ = 0.0;
        double strafe_ = 0.0;
        double speed_ = Math.sqrt(moveEvent.getX() * moveEvent.getX() + moveEvent.getZ() * moveEvent.getZ());
        if (speed_ <= 1.0E-4) {
            return;
        }
        double _direction = 0.0;
        if ((double)direction_ > 0.001) {
            _direction = 1.0;
        } else if ((double)direction_ < -0.001) {
            _direction = -1.0;
        }
        float curDistance = 0.01f;
        switch (mathRadius) {
            case 1: {
                curDistance = MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)curTarget);
                break;
            }
            case 0: {
                curDistance = (float)Math.sqrt((MinecraftInstance.mc.field_71439_g.field_70165_t - curTarget.field_70165_t) * (MinecraftInstance.mc.field_71439_g.field_70165_t - curTarget.field_70165_t) + (MinecraftInstance.mc.field_71439_g.field_70161_v - curTarget.field_70161_v) * (MinecraftInstance.mc.field_71439_g.field_70161_v - curTarget.field_70161_v));
            }
        }
        forward_ = (double)curDistance < (double)radius - speed_ ? -1.0 : ((double)curDistance > (double)radius + speed_ ? 1.0 : (double)(curDistance - radius) / speed_);
        if ((double)curDistance < (double)radius + speed_ * (double)2 && (double)curDistance > (double)radius - speed_ * (double)2) {
            strafe_ = 1.0;
        }
        double strafeYaw = RotationUtils.getRotationsEntity(curTarget).getYaw();
        double covert_ = Math.sqrt(forward_ * forward_ + (strafe_ *= _direction) * strafe_);
        forward_ /= covert_;
        double turnAngle = Math.toDegrees(Math.asin(strafe_ /= covert_));
        if (turnAngle > 0.0) {
            if (forward_ < 0.0) {
                turnAngle = (double)180.0f - turnAngle;
            }
        } else if (forward_ < 0.0) {
            turnAngle = (double)-180.0f - turnAngle;
        }
        strafeYaw = Math.toRadians(strafeYaw + turnAngle);
        moveEvent.setX(-Math.sin(strafeYaw) * speed_);
        moveEvent.setZ(Math.cos(strafeYaw) * speed_);
        MinecraftInstance.mc.field_71439_g.field_70159_w = moveEvent.getX();
        MinecraftInstance.mc.field_71439_g.field_70179_y = moveEvent.getZ();
    }

    public static /* synthetic */ void doTargetStrafe$default(MovementUtils movementUtils, EntityLivingBase entityLivingBase, float f, float f2, MoveEvent moveEvent, int n, int n2, Object object) {
        if ((n2 & 0x10) != 0) {
            n = 0;
        }
        movementUtils.doTargetStrafe(entityLivingBase, f, f2, moveEvent, n);
    }

    public final void move(float speed) {
        if (!this.isMoving()) {
            return;
        }
        double yaw = this.getDirection();
        EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
        entityPlayerSP.field_70159_w += -Math.sin(yaw) * (double)speed;
        entityPlayerSP = MinecraftInstance.mc.field_71439_g;
        entityPlayerSP.field_70179_y += Math.cos(yaw) * (double)speed;
    }

    public final double JumpBoost(double motionY) {
        return MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76430_j) ? motionY + (double)((float)(MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76430_j).func_76458_c() + 1) * 0.1f) : motionY;
    }

    public final double jumpMotion() {
        return this.JumpBoost(0.42);
    }

    public final void limitSpeed(float speed) {
        double yaw = this.getDirection();
        double maxXSpeed = -Math.sin(yaw) * (double)speed;
        double maxZSpeed = Math.cos(yaw) * (double)speed;
        if (MinecraftInstance.mc.field_71439_g.field_70159_w > maxZSpeed) {
            MinecraftInstance.mc.field_71439_g.field_70159_w = maxXSpeed;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70179_y > maxZSpeed) {
            MinecraftInstance.mc.field_71439_g.field_70179_y = maxZSpeed;
        }
    }

    public final void limitSpeedByPercent(float percent) {
        EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
        entityPlayerSP.field_70159_w *= (double)percent;
        entityPlayerSP = MinecraftInstance.mc.field_71439_g;
        entityPlayerSP.field_70179_y *= (double)percent;
    }

    public final void forward(double length) {
        float f;
        FreeLook freeLook = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
        Intrinsics.checkNotNull(freeLook);
        if (!freeLook.getState() && FreeLook.perspectiveToggled) {
            f = FreeLook.cameraYaw;
        } else {
            FreeLook freeLook2 = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
            Intrinsics.checkNotNull(freeLook2);
            f = freeLook2.getState() && RotationUtils.freeLookRotation != null ? FreeLook.cameraYaw2 : MinecraftInstance.mc.field_71439_g.field_70177_z;
        }
        double yaw = Math.toRadians(f);
        MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t + -Math.sin(yaw) * length, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v + Math.cos(yaw) * length);
    }

    public final double getDirection() {
        float f;
        FreeLook freeLook = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
        Intrinsics.checkNotNull(freeLook);
        if (!freeLook.getState() && FreeLook.perspectiveToggled) {
            f = FreeLook.cameraYaw;
        } else {
            FreeLook freeLook2 = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
            Intrinsics.checkNotNull(freeLook2);
            f = freeLook2.getState() && RotationUtils.freeLookRotation != null ? FreeLook.cameraYaw2 : MinecraftInstance.mc.field_71439_g.field_70177_z;
        }
        double rotationYaw = f;
        if (MinecraftInstance.mc.field_71439_g.field_70701_bs < 0.0f) {
            rotationYaw += (double)180.0f;
        }
        float forward = 1.0f;
        if (MinecraftInstance.mc.field_71439_g.field_70701_bs < 0.0f) {
            forward = -0.5f;
        } else if (MinecraftInstance.mc.field_71439_g.field_70701_bs > 0.0f) {
            forward = 0.5f;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70702_br > 0.0f) {
            rotationYaw -= (double)(90.0f * forward);
        }
        if (MinecraftInstance.mc.field_71439_g.field_70702_br < 0.0f) {
            rotationYaw += (double)(90.0f * forward);
        }
        return Math.toRadians(rotationYaw);
    }

    public final float getJumpMotion() {
        float mot = 0.42f;
        if (MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76430_j)) {
            mot += (float)(MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76430_j).func_76458_c() + 1) * 0.1f;
        }
        return mot;
    }

    public final float getMovingYaw() {
        return (float)(this.getDirection() * (double)180.0f / Math.PI);
    }

    public final double getBps() {
        return bps;
    }

    public final void setMotion(double speed) {
        float f;
        double forward = MinecraftInstance.mc.field_71439_g.field_71158_b.field_78900_b;
        double strafe = MinecraftInstance.mc.field_71439_g.field_71158_b.field_78902_a;
        FreeLook freeLook = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
        Intrinsics.checkNotNull(freeLook);
        if (!freeLook.getState() && FreeLook.perspectiveToggled) {
            f = FreeLook.cameraYaw;
        } else {
            FreeLook freeLook2 = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
            Intrinsics.checkNotNull(freeLook2);
            f = freeLook2.getState() && RotationUtils.freeLookRotation != null ? FreeLook.cameraYaw2 : MinecraftInstance.mc.field_71439_g.field_70177_z;
        }
        double yaw = f;
        if (forward == 0.0 && strafe == 0.0) {
            MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
        } else {
            if (!(forward == 0.0)) {
                if (strafe > 0.0) {
                    yaw += (double)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (double)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            double cos = Math.cos(Math.toRadians(yaw + (double)90.0f));
            double sin = Math.sin(Math.toRadians(yaw + (double)90.0f));
            MinecraftInstance.mc.field_71439_g.field_70159_w = forward * speed * cos + strafe * speed * sin;
            MinecraftInstance.mc.field_71439_g.field_70179_y = forward * speed * sin - strafe * speed * cos;
        }
    }

    public final void updateBlocksPerSecond() {
        if (MinecraftInstance.mc.field_71439_g == null || MinecraftInstance.mc.field_71439_g.field_70173_aa < 1) {
            bps = 0.0;
        }
        double distance = MinecraftInstance.mc.field_71439_g.func_70011_f(lastX, lastY, lastZ);
        lastX = MinecraftInstance.mc.field_71439_g.field_70165_t;
        lastY = MinecraftInstance.mc.field_71439_g.field_70163_u;
        lastZ = MinecraftInstance.mc.field_71439_g.field_70161_v;
        bps = distance * (double)((float)20 * MinecraftInstance.mc.field_71428_T.field_74278_d);
    }

    public final void setSpeed(@NotNull MoveEvent moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        Intrinsics.checkNotNullParameter(moveEvent, "moveEvent");
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (forward == 0.0 && strafe == 0.0) {
            moveEvent.setZ(0.0);
            moveEvent.setX(0.0);
        } else {
            if (!(forward == 0.0)) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            double cos = Math.cos(Math.toRadians(yaw + 90.0f));
            double sin = Math.sin(Math.toRadians(yaw + 90.0f));
            moveEvent.setX(forward * moveSpeed * cos + strafe * moveSpeed * sin);
            moveEvent.setZ(forward * moveSpeed * sin - strafe * moveSpeed * cos);
        }
    }

    private final double calculateGround() {
        AxisAlignedBB playerBoundingBox = MinecraftInstance.mc.field_71439_g.func_174813_aQ();
        double blockHeight = 1.0;
        for (double ground = MinecraftInstance.mc.field_71439_g.field_70163_u; ground > 0.0; ground -= blockHeight) {
            AxisAlignedBB customBox = new AxisAlignedBB(playerBoundingBox.field_72336_d, ground + blockHeight, playerBoundingBox.field_72334_f, playerBoundingBox.field_72340_a, ground, playerBoundingBox.field_72339_c);
            if (!MinecraftInstance.mc.field_71441_e.func_72829_c(customBox)) continue;
            if (blockHeight <= 0.05) {
                return ground + blockHeight;
            }
            ground += blockHeight;
            blockHeight = 0.05;
        }
        return 0.0;
    }

    public final boolean isOnGround(double height) {
        List list = MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, -height, 0.0));
        Intrinsics.checkNotNullExpressionValue(list, "mc.theWorld.getColliding\u2026, -height, 0.0)\n        )");
        return !((Collection)list).isEmpty();
    }

    public final double getBaseMoveSpeed() {
        double baseSpeed = 0.2875;
        if (MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
            baseSpeed *= 1.0 + 0.2 * (double)(MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c() + 1);
        }
        return baseSpeed;
    }

    public final void handleVanillaKickBypass() {
        double ground = this.calculateGround();
        MovementUtils $this$handleVanillaKickBypass_u24lambda_u2d1 = this;
        boolean bl = false;
        for (double posY = MinecraftInstance.mc.field_71439_g.field_70163_u; posY > ground; posY -= 8.0) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, posY, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
            if (posY - 8.0 < ground) break;
        }
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, ground, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
        for (double posY = ground; posY < MinecraftInstance.mc.field_71439_g.field_70163_u; posY += 8.0) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, posY, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
            if (posY + 8.0 > MinecraftInstance.mc.field_71439_g.field_70163_u) break;
        }
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
    }

    @JvmStatic
    public static final double getDirectioon() {
        float f;
        FreeLook freeLook = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
        Intrinsics.checkNotNull(freeLook);
        if (!freeLook.getState() && FreeLook.perspectiveToggled) {
            f = FreeLook.cameraYaw;
        } else {
            FreeLook freeLook2 = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
            Intrinsics.checkNotNull(freeLook2);
            if (freeLook2.getState() && RotationUtils.freeLookRotation != null) {
                f = FreeLook.cameraYaw2;
            } else {
                FreeLook freeLook3 = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
                Intrinsics.checkNotNull(freeLook3);
                f = freeLook3.getState() ? FreeLook.cameraYaw : MinecraftInstance.mc.field_71439_g.field_70177_z;
            }
        }
        double rotationYaw = f;
        if (MinecraftInstance.mc.field_71439_g.field_70701_bs < 0.0f) {
            rotationYaw += (double)180.0f;
        }
        float forward = 1.0f;
        if (MinecraftInstance.mc.field_71439_g.field_70701_bs < 0.0f) {
            forward = -0.5f;
        } else if (MinecraftInstance.mc.field_71439_g.field_70701_bs > 0.0f) {
            forward = 0.5f;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70702_br > 0.0f) {
            rotationYaw -= (double)(90.0f * forward);
        }
        if (MinecraftInstance.mc.field_71439_g.field_70702_br < 0.0f) {
            rotationYaw += (double)(90.0f * forward);
        }
        return Math.toRadians(rotationYaw);
    }

    public final boolean isBlockUnder() {
        if (MinecraftInstance.mc.field_71439_g == null) {
            return false;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70163_u < 0.0) {
            return false;
        }
        for (int off = 0; off < (int)MinecraftInstance.mc.field_71439_g.field_70163_u + 2; off += 2) {
            AxisAlignedBB bb = MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, (double)(-off), 0.0);
            List list = MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, bb);
            Intrinsics.checkNotNullExpressionValue(list, "mc.theWorld.getColliding\u2026ngBoxes(mc.thePlayer, bb)");
            if (!(!((Collection)list).isEmpty())) continue;
            return true;
        }
        return false;
    }

    public final void setMotion2(double d, float f) {
        MinecraftInstance.mc.field_71439_g.field_70159_w = -Math.sin(Math.toRadians(f)) * d;
        MinecraftInstance.mc.field_71439_g.field_70179_y = Math.cos(Math.toRadians(f)) * d;
    }
}

