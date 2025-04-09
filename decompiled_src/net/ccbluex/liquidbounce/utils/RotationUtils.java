/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import java.util.Objects;
import java.util.Random;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.modules.client.RotationModule;
import net.ccbluex.liquidbounce.features.module.modules.visual.FreeLook;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RaycastUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import org.jetbrains.annotations.NotNull;

public final class RotationUtils
extends MinecraftInstance
implements Listenable {
    private static Random random = new Random();
    private static int keepLength;
    private static int revTick;
    public static float prevHeadPitch;
    public static float headPitch;
    public static Float playerYaw;
    public static double smoothYaw;
    public static double smoothPitch;
    public static Rotation targetRotation;
    public static Rotation freeLookRotation;
    public static Rotation fakeRotation;
    public static Rotation serverRotation;
    public static boolean keepCurrentRotation;
    private static double x;
    private static double y;
    private static double z;
    private static final double SMOOTHING_FACTOR = 0.25;

    public static VecRotation faceBlock(BlockPos blockPos) {
        if (blockPos == null) {
            return null;
        }
        VecRotation vecRotation = null;
        for (double xSearch = 0.1; xSearch < 0.9; xSearch += 0.1) {
            for (double ySearch = 0.1; ySearch < 0.9; ySearch += 0.1) {
                for (double zSearch = 0.1; zSearch < 0.9; zSearch += 0.1) {
                    Vec3 eyesPos = new Vec3(RotationUtils.mc.field_71439_g.field_70165_t, RotationUtils.mc.field_71439_g.func_174813_aQ().field_72338_b + (double)RotationUtils.mc.field_71439_g.func_70047_e(), RotationUtils.mc.field_71439_g.field_70161_v);
                    Vec3 posVec = new Vec3((Vec3i)blockPos).func_72441_c(xSearch, ySearch, zSearch);
                    double dist = eyesPos.func_72438_d(posVec);
                    double diffX = posVec.field_72450_a - eyesPos.field_72450_a;
                    double diffY = posVec.field_72448_b - eyesPos.field_72448_b;
                    double diffZ = posVec.field_72449_c - eyesPos.field_72449_c;
                    double diffXZ = MathHelper.func_76133_a((double)(diffX * diffX + diffZ * diffZ));
                    Rotation rotation = new Rotation(MathHelper.func_76142_g((float)((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f)), MathHelper.func_76142_g((float)((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ))))));
                    Vec3 rotationVector = RotationUtils.getVectorForRotation(rotation);
                    Vec3 vector = eyesPos.func_72441_c(rotationVector.field_72450_a * dist, rotationVector.field_72448_b * dist, rotationVector.field_72449_c * dist);
                    MovingObjectPosition obj = RotationUtils.mc.field_71441_e.func_147447_a(eyesPos, vector, false, false, true);
                    if (obj.field_72313_a != MovingObjectPosition.MovingObjectType.BLOCK) continue;
                    VecRotation currentVec = new VecRotation(posVec, rotation);
                    if (vecRotation != null && !(RotationUtils.getRotationDifference(currentVec.getRotation()) < RotationUtils.getRotationDifference(vecRotation.getRotation()))) continue;
                    vecRotation = currentVec;
                }
            }
        }
        return vecRotation;
    }

    public static Rotation getRotationsEntity(EntityLivingBase entity) {
        return RotationUtils.getRotations(entity.field_70165_t, entity.field_70163_u + (double)entity.func_70047_e() - 0.4, entity.field_70161_v);
    }

    public static Rotation getRotationsNonLivingEntity(Entity entity) {
        return RotationUtils.getRotations(entity.field_70165_t, entity.field_70163_u + (entity.func_174813_aQ().field_72337_e - entity.func_174813_aQ().field_72338_b) * 0.5, entity.field_70161_v);
    }

    public static void faceBow(Entity target, boolean silent, boolean predict, float predictSize) {
        EntityPlayerSP player = RotationUtils.mc.field_71439_g;
        double posX = target.field_70165_t + (predict ? (target.field_70165_t - target.field_70169_q) * (double)predictSize : 0.0) - (player.field_70165_t + (predict ? player.field_70165_t - player.field_70169_q : 0.0));
        double posY = target.func_174813_aQ().field_72338_b + (predict ? (target.func_174813_aQ().field_72338_b - target.field_70167_r) * (double)predictSize : 0.0) + (double)target.func_70047_e() - 0.15 - (player.func_174813_aQ().field_72338_b + (predict ? player.field_70163_u - player.field_70167_r : 0.0)) - (double)player.func_70047_e();
        double posZ = target.field_70161_v + (predict ? (target.field_70161_v - target.field_70166_s) * (double)predictSize : 0.0) - (player.field_70161_v + (predict ? player.field_70161_v - player.field_70166_s : 0.0));
        double posSqrt = Math.sqrt(posX * posX + posZ * posZ);
        float velocity = (float)player.func_71057_bx() / 20.0f;
        if ((velocity = (velocity * velocity + velocity * 2.0f) / 3.0f) > 1.0f) {
            velocity = 1.0f;
        }
        Rotation rotation = new Rotation((float)(Math.atan2(posZ, posX) * 180.0 / Math.PI) - 90.0f, (float)(-Math.toDegrees(Math.atan(((double)(velocity * velocity) - Math.sqrt((double)(velocity * velocity * velocity * velocity) - (double)0.006f * ((double)0.006f * (posSqrt * posSqrt) + 2.0 * posY * (double)(velocity * velocity)))) / ((double)0.006f * posSqrt)))));
        if (silent) {
            RotationUtils.setTargetRotation(rotation, 0);
        } else {
            RotationUtils.limitAngleChange(new Rotation(player.field_70177_z, player.field_70125_A), rotation, 10 + new Random().nextInt(6)).toPlayer((EntityPlayer)RotationUtils.mc.field_71439_g);
        }
    }

    public static Rotation toRotation(Vec3 vec, boolean predict) {
        Vec3 eyesPos = new Vec3(RotationUtils.mc.field_71439_g.field_70165_t, RotationUtils.mc.field_71439_g.func_174813_aQ().field_72338_b + (double)RotationUtils.mc.field_71439_g.func_70047_e(), RotationUtils.mc.field_71439_g.field_70161_v);
        if (predict) {
            if (RotationUtils.mc.field_71439_g.field_70122_E) {
                eyesPos.func_72441_c(RotationUtils.mc.field_71439_g.field_70159_w, 0.0, RotationUtils.mc.field_71439_g.field_70179_y);
            } else {
                eyesPos.func_72441_c(RotationUtils.mc.field_71439_g.field_70159_w, RotationUtils.mc.field_71439_g.field_70181_x, RotationUtils.mc.field_71439_g.field_70179_y);
            }
        }
        double diffX = vec.field_72450_a - eyesPos.field_72450_a;
        double diffY = vec.field_72448_b - eyesPos.field_72448_b;
        double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
        return new Rotation(MathHelper.func_76142_g((float)((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f)), MathHelper.func_76142_g((float)((float)(-Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ)))))));
    }

    public static Vec3 getCenter(AxisAlignedBB bb) {
        return new Vec3(bb.field_72340_a + (bb.field_72336_d - bb.field_72340_a) * 0.5, bb.field_72338_b + (bb.field_72337_e - bb.field_72338_b) * 0.5, bb.field_72339_c + (bb.field_72334_f - bb.field_72339_c) * 0.5);
    }

    public static VecRotation calculateCenter(String calMode, Boolean randMode, double randomRange, boolean legitRandom, AxisAlignedBB bb, boolean predict, boolean throughWalls) {
        VecRotation vecRotation = null;
        double xMin = 0.15;
        double xMax = 0.85;
        double xDist = 0.1;
        double yMin = 0.15;
        double yMax = 1.0;
        double yDist = 0.1;
        double zMin = 0.15;
        double zMax = 0.85;
        double zDist = 0.1;
        Vec3 curVec3 = null;
        switch (calMode) {
            case "HalfUp": {
                xMin = 0.4;
                xMax = 0.9;
                xDist = 0.1;
                yMin = 0.3;
                yMax = 0.9;
                yDist = 0.1;
                zMin = 0.4;
                zMax = 0.9;
                zDist = 0.1;
                break;
            }
            case "CenterSimple": {
                xMin = 0.45;
                xMax = 0.55;
                xDist = 0.0125;
                yMin = 0.65;
                yMax = 0.75;
                yDist = 0.0125;
                zMin = 0.45;
                zMax = 0.55;
                zDist = 0.0125;
                break;
            }
            case "CenterLine": {
                xMin = 0.45;
                xMax = 0.451;
                xDist = 0.0125;
                yMin = 0.5;
                yMax = 0.9;
                yDist = 0.1;
                zMin = 0.45;
                zMax = 0.451;
                zDist = 0.0125;
                break;
            }
            case "CenterHead": {
                xMin = 0.45;
                xMax = 0.55;
                xDist = 0.0125;
                yMin = 0.85;
                yMax = 0.95;
                yDist = 0.1;
                zMin = 0.45;
                zMax = 0.55;
                zDist = 0.0125;
                break;
            }
            case "CenterBody": {
                xMin = 0.45;
                xMax = 0.55;
                xDist = 0.0125;
                yMin = 0.7;
                yMax = 0.95;
                yDist = 0.1;
                zMin = 0.45;
                zMax = 0.55;
                zDist = 0.0125;
                break;
            }
            case "LockHead": {
                xMin = 0.549;
                xMax = 0.55;
                xDist = 0.0125;
                yMin = 0.949;
                yMax = 0.95;
                yDist = 0.1;
                zMin = 0.549;
                zMax = 0.55;
                zDist = 0.0125;
            }
        }
        for (double xSearch = xMin; xSearch < xMax; xSearch += xDist) {
            for (double ySearch = yMin; ySearch < yMax; ySearch += yDist) {
                for (double zSearch = zMin; zSearch < zMax; zSearch += zDist) {
                    Vec3 vec3 = new Vec3(bb.field_72340_a + (bb.field_72336_d - bb.field_72340_a) * xSearch, bb.field_72338_b + (bb.field_72337_e - bb.field_72338_b) * ySearch, bb.field_72339_c + (bb.field_72334_f - bb.field_72339_c) * zSearch);
                    Rotation rotation = RotationUtils.toRotation(vec3, predict);
                    if (!throughWalls && !RotationUtils.isVisible(vec3)) continue;
                    VecRotation currentVec = new VecRotation(vec3, rotation);
                    if (vecRotation != null && !(RotationUtils.getRotationDifference(currentVec.getRotation()) < RotationUtils.getRotationDifference(vecRotation.getRotation()))) continue;
                    vecRotation = currentVec;
                    curVec3 = vec3;
                }
            }
        }
        if (vecRotation == null || !randMode.booleanValue()) {
            return vecRotation;
        }
        double rand1 = random.nextDouble();
        double rand2 = random.nextDouble();
        double rand3 = random.nextDouble();
        double xRange = bb.field_72336_d - bb.field_72340_a;
        double yRange = bb.field_72337_e - bb.field_72338_b;
        double zRange = bb.field_72334_f - bb.field_72339_c;
        double minRange = 999999.0;
        if (xRange <= minRange) {
            minRange = xRange;
        }
        if (yRange <= minRange) {
            minRange = yRange;
        }
        if (zRange <= minRange) {
            minRange = zRange;
        }
        rand1 = rand1 * minRange * randomRange;
        rand2 = rand2 * minRange * randomRange;
        rand3 = rand3 * minRange * randomRange;
        double xPrecent = minRange * randomRange / xRange;
        double yPrecent = minRange * randomRange / yRange;
        double zPrecent = minRange * randomRange / zRange;
        Vec3 randomVec3 = legitRandom ? new Vec3(curVec3.field_72450_a, curVec3.field_72448_b - yPrecent * (curVec3.field_72448_b - bb.field_72338_b) + rand2, curVec3.field_72449_c) : new Vec3(curVec3.field_72450_a - xPrecent * (curVec3.field_72450_a - bb.field_72340_a) + rand1, curVec3.field_72448_b - yPrecent * (curVec3.field_72448_b - bb.field_72338_b) + rand2, curVec3.field_72449_c - zPrecent * (curVec3.field_72449_c - bb.field_72339_c) + rand3);
        Rotation randomRotation = RotationUtils.toRotation(randomVec3, predict);
        vecRotation = new VecRotation(randomVec3, randomRotation);
        return vecRotation;
    }

    public static double getRotationDifference(Entity entity) {
        Rotation rotation = RotationUtils.toRotation(RotationUtils.getCenter(entity.func_174813_aQ()), true);
        return RotationUtils.getRotationDifference(rotation, new Rotation(RotationUtils.mc.field_71439_g.field_70177_z, RotationUtils.mc.field_71439_g.field_70125_A));
    }

    public static double getRotationDifference(Rotation rotation) {
        return serverRotation == null ? 0.0 : RotationUtils.getRotationDifference(rotation, serverRotation);
    }

    public static double getRotationDifference(Rotation a, Rotation b) {
        return Math.hypot(RotationUtils.getAngleDifference(a.getYaw(), b.getYaw()), a.getPitch() - b.getPitch());
    }

    @NotNull
    public static Rotation limitAngleChange(Rotation currentRotation, Rotation targetRotation, float turnSpeed) {
        float yawDifference = RotationUtils.getAngleDifference(targetRotation.getYaw(), currentRotation.getYaw());
        float pitchDifference = RotationUtils.getAngleDifference(targetRotation.getPitch(), currentRotation.getPitch());
        return new Rotation(currentRotation.getYaw() + (yawDifference > turnSpeed ? turnSpeed : Math.max(yawDifference, -turnSpeed)), currentRotation.getPitch() + (pitchDifference > turnSpeed ? turnSpeed : Math.max(pitchDifference, -turnSpeed)));
    }

    public static float getAngleDifference(float a, float b) {
        return ((a - b) % 360.0f + 540.0f) % 360.0f - 180.0f;
    }

    public static Vec3 getVectorForRotation(Rotation rotation) {
        float yawCos = MathHelper.func_76134_b((float)(-rotation.getYaw() * ((float)Math.PI / 180) - (float)Math.PI));
        float yawSin = MathHelper.func_76126_a((float)(-rotation.getYaw() * ((float)Math.PI / 180) - (float)Math.PI));
        float pitchCos = -MathHelper.func_76134_b((float)(-rotation.getPitch() * ((float)Math.PI / 180)));
        float pitchSin = MathHelper.func_76126_a((float)(-rotation.getPitch() * ((float)Math.PI / 180)));
        return new Vec3((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos));
    }

    public static boolean isFaced(Entity targetEntity, double blockReachDistance) {
        return RaycastUtils.raycastEntity(blockReachDistance, entity -> entity == targetEntity) != null;
    }

    public static boolean isVisible(Vec3 vec3) {
        Vec3 eyesPos = new Vec3(RotationUtils.mc.field_71439_g.field_70165_t, RotationUtils.mc.field_71439_g.func_174813_aQ().field_72338_b + (double)RotationUtils.mc.field_71439_g.func_70047_e(), RotationUtils.mc.field_71439_g.field_70161_v);
        return RotationUtils.mc.field_71441_e.func_72933_a(eyesPos, vec3) == null;
    }

    public static Rotation getFaceRotation(EnumFacing face, BlockPos blockPos) {
        Vec3i faceVec = face.func_176730_m();
        Vec3 blockFaceVec = new Vec3((double)faceVec.func_177958_n() * 0.5, (double)faceVec.func_177956_o() * 0.5, (double)faceVec.func_177952_p() * 0.5);
        blockFaceVec = blockFaceVec.func_178787_e(new Vec3((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p()));
        blockFaceVec = blockFaceVec.func_72441_c(0.5, 0.5, 0.5);
        return RotationUtils.getRotations(blockFaceVec);
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        if (serverRotation != null) {
            smoothYaw = (Boolean)RotationModule.INSTANCE.getSmoothValue().get() == false || targetRotation == null ? (double)serverRotation.getYaw() : smoothYaw * 0.75 + (double)serverRotation.getYaw() * 0.25;
            smoothPitch = (Boolean)RotationModule.INSTANCE.getSmoothValue().get() == false || targetRotation == null ? RenderUtils.interpolate(headPitch, prevHeadPitch, RotationUtils.mc.field_71428_T.field_74281_c) : smoothPitch * 0.75 + (double)headPitch * 0.25;
        }
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if (RotationUtils.rotating() && --keepLength <= 0) {
            if (revTick > 0) {
                --revTick;
                RotationUtils.reset();
            } else {
                RotationUtils.reset();
            }
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer) {
            C03PacketPlayer packetPlayer = (C03PacketPlayer)packet;
            if (!(targetRotation == null || keepCurrentRotation || targetRotation.getYaw() == serverRotation.getYaw() && targetRotation.getPitch() == serverRotation.getPitch())) {
                packetPlayer.field_149476_e = targetRotation.getYaw();
                packetPlayer.field_149473_f = targetRotation.getPitch();
                packetPlayer.field_149481_i = true;
            }
            if (packetPlayer.field_149481_i) {
                serverRotation = new Rotation(packetPlayer.field_149476_e, packetPlayer.field_149473_f);
            }
        }
        if (packet instanceof C03PacketPlayer.C06PacketPlayerPosLook || packet instanceof C03PacketPlayer.C05PacketPlayerLook) {
            playerYaw = Float.valueOf(((C03PacketPlayer)packet).field_149476_e);
        }
    }

    @EventTarget
    public void onMotion(MotionEvent event) {
        prevHeadPitch = headPitch;
        headPitch = serverRotation.getPitch();
        RotationUtils.mc.field_71439_g.field_70759_as = fakeRotation != null ? fakeRotation.getYaw() : (float)smoothYaw;
    }

    public static void setFakeRotation(Rotation fakeRotation, Rotation rotation) {
        if (Double.isNaN(rotation.getYaw()) || Double.isNaN(rotation.getPitch()) || rotation.getPitch() > 90.0f || rotation.getPitch() < -90.0f) {
            return;
        }
        RotationUtils.fakeRotation = fakeRotation.fixedSensitivity(RotationUtils.mc.field_71474_y.field_74341_c);
        targetRotation = rotation.fixedSensitivity(RotationUtils.mc.field_71474_y.field_74341_c);
        keepLength = 1;
        revTick = 0;
    }

    public static void setFakeRotationReverse(Rotation fakeRotation, Rotation rotation, int keepLenght, int revTick) {
        if (Double.isNaN(rotation.getYaw()) || Double.isNaN(rotation.getPitch()) || rotation.getPitch() > 90.0f || rotation.getPitch() < -90.0f) {
            return;
        }
        RotationUtils.fakeRotation = fakeRotation.fixedSensitivity(RotationUtils.mc.field_71474_y.field_74341_c);
        targetRotation = rotation.fixedSensitivity(RotationUtils.mc.field_71474_y.field_74341_c);
        keepLength = keepLenght;
        RotationUtils.revTick = revTick + 1;
    }

    public static void setTargetRotation(Rotation rotation) {
        if (Double.isNaN(rotation.getYaw()) || Double.isNaN(rotation.getPitch()) || rotation.getPitch() > 90.0f || rotation.getPitch() < -90.0f) {
            return;
        }
        targetRotation = rotation.fixedSensitivity(RotationUtils.mc.field_71474_y.field_74341_c);
        keepLength = 1;
        revTick = 0;
    }

    public static void setTargetRotation(Rotation rotation, int keepLength) {
        if (Double.isNaN(rotation.getYaw()) || Double.isNaN(rotation.getPitch()) || rotation.getPitch() > 90.0f || rotation.getPitch() < -90.0f) {
            return;
        }
        targetRotation = rotation.fixedSensitivity(RotationUtils.mc.field_71474_y.field_74341_c);
        RotationUtils.keepLength = keepLength;
        revTick = 0;
    }

    public static void setFreeLookRotation(Rotation rotation, int keepLength, boolean moveCorrect) {
        if (Double.isNaN(rotation.getYaw()) || Double.isNaN(rotation.getPitch()) || rotation.getPitch() > 90.0f || rotation.getPitch() < -90.0f) {
            return;
        }
        if (!FreeLook.isEnabled) {
            Objects.requireNonNull(CrossSine.moduleManager.getModule(FreeLook.class)).enable(moveCorrect);
        }
        freeLookRotation = rotation.fixedSensitivity(RotationUtils.mc.field_71474_y.field_74341_c);
        freeLookRotation.toPlayer((EntityPlayer)RotationUtils.mc.field_71439_g);
        RotationUtils.keepLength = keepLength;
    }

    public static void setTargetRotationReverse(Rotation rotation, int keepLength, int revTick) {
        if (Double.isNaN(rotation.getYaw()) || Double.isNaN(rotation.getPitch()) || rotation.getPitch() > 90.0f || rotation.getPitch() < -90.0f) {
            return;
        }
        targetRotation = rotation.fixedSensitivity(RotationUtils.mc.field_71474_y.field_74341_c);
        RotationUtils.keepLength = keepLength;
        RotationUtils.revTick = revTick + 1;
    }

    public static void reset() {
        keepLength = 0;
        if (revTick > 0) {
            targetRotation = new Rotation(targetRotation.getYaw() - RotationUtils.getAngleDifference(targetRotation.getYaw(), RotationUtils.mc.field_71439_g.field_70177_z) / (float)revTick, targetRotation.getPitch() - RotationUtils.getAngleDifference(targetRotation.getPitch(), RotationUtils.mc.field_71439_g.field_70125_A) / (float)revTick);
            fakeRotation = new Rotation(fakeRotation.getYaw() - RotationUtils.getAngleDifference(fakeRotation.getYaw(), RotationUtils.mc.field_71439_g.field_70177_z) / (float)revTick, fakeRotation.getPitch() - RotationUtils.getAngleDifference(fakeRotation.getPitch(), RotationUtils.mc.field_71439_g.field_70125_A) / (float)revTick);
        } else {
            targetRotation = null;
            fakeRotation = null;
        }
        if (freeLookRotation != null) {
            freeLookRotation = null;
            if (!Objects.requireNonNull(CrossSine.moduleManager.getModule(FreeLook.class)).getState()) {
                Objects.requireNonNull(CrossSine.moduleManager.getModule(FreeLook.class)).disable();
            }
        }
    }

    public static Rotation getRotations(double posX, double posY, double posZ) {
        EntityPlayerSP player = RotationUtils.mc.field_71439_g;
        double x = posX - player.field_70165_t;
        double y = posY - (player.field_70163_u + (double)player.func_70047_e());
        double z = posZ - player.field_70161_v;
        double dist = MathHelper.func_76133_a((double)(x * x + z * z));
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(y, dist) * 180.0 / Math.PI));
        return new Rotation(yaw, pitch);
    }

    public static Rotation getRotations(Vec3 pos) {
        EntityPlayerSP player = RotationUtils.mc.field_71439_g;
        double x = pos.field_72450_a - player.field_70165_t;
        double y = pos.field_72448_b - (player.field_70163_u + (double)player.func_70047_e());
        double z = pos.field_72449_c - player.field_70161_v;
        double dist = MathHelper.func_76133_a((double)(x * x + z * z));
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(y, dist) * 180.0 / Math.PI));
        return new Rotation(yaw, pitch);
    }

    public static Rotation rotationSmooth(Rotation currentRotation, Rotation targetRotation, float smooth) {
        return new Rotation(currentRotation.getYaw() + (targetRotation.getYaw() - currentRotation.getYaw()) / smooth, currentRotation.getPitch() + (targetRotation.getPitch() - currentRotation.getPitch()) / smooth);
    }

    @Override
    public boolean handleEvents() {
        return true;
    }

    public static boolean rotating() {
        return targetRotation != null || freeLookRotation != null;
    }

    static {
        serverRotation = new Rotation(0.0f, 0.0f);
        keepCurrentRotation = false;
        x = random.nextDouble();
        y = random.nextDouble();
        z = random.nextDouble();
    }
}

