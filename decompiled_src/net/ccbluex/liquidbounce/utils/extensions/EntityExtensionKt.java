/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.extensions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.vecmath.Vector3d;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MathUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.extensions.OtherExtensionKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=2, xi=48, d1={"\u0000\u0084\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\u0016\u0010)\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u00012\u0006\u0010+\u001a\u00020\u0012\u001a\u0012\u0010,\u001a\u00020-*\u00020\u00012\u0006\u0010.\u001a\u00020\u0012\u001a\u0012\u0010/\u001a\u00020-*\u00020\u00022\u0006\u00100\u001a\u00020\u0002\u001a\"\u00101\u001a\b\u0012\u0004\u0012\u00020\u000202*\u0002032\u0006\u00100\u001a\u00020\u00022\b\b\u0002\u00104\u001a\u00020-\u001a\n\u00105\u001a\u00020\u0001*\u000206\u001a*\u00107\u001a\u00020-*\u00020\u00022\b\b\u0002\u00100\u001a\u00020\u00022\n\b\u0002\u00108\u001a\u0004\u0018\u0001092\b\b\u0002\u0010:\u001a\u00020-\u001a$\u0010;\u001a\u0004\u0018\u00010<*\u00020\u00022\u0006\u0010=\u001a\u00020-2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006\u001a$\u0010>\u001a\u0004\u0018\u00010<*\u00020\u00022\u0006\u0010=\u001a\u00020-2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u001c\u0010>\u001a\u0004\u0018\u00010<*\u00020\u00022\u0006\u0010=\u001a\u00020-2\u0006\u00108\u001a\u000209\u001a\u0014\u0010?\u001a\u0004\u0018\u00010<*\u00020\u00022\u0006\u0010=\u001a\u00020-\u001a\u0012\u0010@\u001a\u00020A*\u00020\b2\u0006\u0010B\u001a\u00020C\u001a'\u0010D\u001a\u00020E*\u00020\b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010F\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"(\u0010\u0007\u001a\u00020\u0006*\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\"(\u0010\u000e\u001a\u00020\u0006*\u00020\b2\u0006\u0010\r\u001a\u00020\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\n\"\u0004\b\u0010\u0010\f\"\u0015\u0010\u0011\u001a\u00020\u0012*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\"\u0015\u0010\u0015\u001a\u00020\u0006*\u00020\u00168F\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018\"\u0015\u0010\u0019\u001a\u00020\u001a*\u00020\u00168F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c\"\u0015\u0010\u001d\u001a\u00020\u0012*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010\u0014\"\u0015\u0010\u001f\u001a\u00020\u0006*\u00020\u00168F\u00a2\u0006\u0006\u001a\u0004\b \u0010\u0018\"\u0015\u0010!\u001a\u00020\"*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b#\u0010$\"\u0015\u0010%\u001a\u00020&*\u00020\u00168F\u00a2\u0006\u0006\u001a\u0004\b'\u0010(\u00a8\u0006G"}, d2={"eyesLoc", "Lnet/minecraft/util/Vec3;", "Lnet/minecraft/entity/Entity;", "getEyesLoc", "(Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/Vec3;", "pitch", "", "fixedSensitivityPitch", "Lnet/minecraft/client/entity/EntityPlayerSP;", "getFixedSensitivityPitch", "(Lnet/minecraft/client/entity/EntityPlayerSP;)F", "setFixedSensitivityPitch", "(Lnet/minecraft/client/entity/EntityPlayerSP;F)V", "yaw", "fixedSensitivityYaw", "getFixedSensitivityYaw", "setFixedSensitivityYaw", "hitBox", "Lnet/minecraft/util/AxisAlignedBB;", "getHitBox", "(Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/AxisAlignedBB;", "hurtPercent", "Lnet/minecraft/entity/EntityLivingBase;", "getHurtPercent", "(Lnet/minecraft/entity/EntityLivingBase;)F", "ping", "", "getPing", "(Lnet/minecraft/entity/EntityLivingBase;)I", "renderBoundingBox", "getRenderBoundingBox", "renderHurtTime", "getRenderHurtTime", "renderPos", "Ljavax/vecmath/Vector3d;", "getRenderPos", "(Lnet/minecraft/entity/Entity;)Ljavax/vecmath/Vector3d;", "skin", "Lnet/minecraft/util/ResourceLocation;", "getSkin", "(Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/util/ResourceLocation;", "getNearestPointBB", "eye", "box", "distanceTo", "", "bb", "getDistanceToEntityBox", "entity", "getEntitiesInRadius", "", "Lnet/minecraft/world/World;", "radius", "getEyeVec3", "Lnet/minecraft/entity/player/EntityPlayer;", "getLookDistanceToEntityBox", "rotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "range", "rayTraceCustom", "Lnet/minecraft/util/MovingObjectPosition;", "blockReachDistance", "rayTraceWithCustomRotation", "rayTraceWithServerSideRotation", "sendUseItem", "", "stack", "Lnet/minecraft/item/ItemStack;", "setFixedSensitivityAngles", "", "(Lnet/minecraft/client/entity/EntityPlayerSP;Ljava/lang/Float;Ljava/lang/Float;)V", "CrossSine"})
public final class EntityExtensionKt {
    public static final double getDistanceToEntityBox(@NotNull Entity $this$getDistanceToEntityBox, @NotNull Entity entity) {
        Intrinsics.checkNotNullParameter($this$getDistanceToEntityBox, "<this>");
        Intrinsics.checkNotNullParameter(entity, "entity");
        Vec3 eyes = $this$getDistanceToEntityBox.func_174824_e(0.0f);
        Intrinsics.checkNotNullExpressionValue(eyes, "eyes");
        Vec3 pos = EntityExtensionKt.getNearestPointBB(eyes, EntityExtensionKt.getHitBox(entity));
        return eyes.func_72438_d(pos);
    }

    @NotNull
    public static final Vec3 getNearestPointBB(@NotNull Vec3 eye, @NotNull AxisAlignedBB box) {
        Intrinsics.checkNotNullParameter(eye, "eye");
        Intrinsics.checkNotNullParameter(box, "box");
        double[] dArray = new double[]{eye.field_72450_a, eye.field_72448_b, eye.field_72449_c};
        double[] origin = dArray;
        double[] dArray2 = new double[]{box.field_72340_a, box.field_72338_b, box.field_72339_c};
        double[] destMins = dArray2;
        double[] dArray3 = new double[]{box.field_72336_d, box.field_72337_e, box.field_72334_f};
        double[] destMaxs = dArray3;
        int n = 0;
        while (n < 3) {
            int i;
            if (origin[i = n++] > destMaxs[i]) {
                origin[i] = destMaxs[i];
                continue;
            }
            if (!(origin[i] < destMins[i])) continue;
            origin[i] = destMins[i];
        }
        return new Vec3(origin[0], origin[1], origin[2]);
    }

    @Nullable
    public static final MovingObjectPosition rayTraceWithCustomRotation(@NotNull Entity $this$rayTraceWithCustomRotation, double blockReachDistance, float yaw, float pitch) {
        Intrinsics.checkNotNullParameter($this$rayTraceWithCustomRotation, "<this>");
        Vec3 vec3 = $this$rayTraceWithCustomRotation.func_174824_e(1.0f);
        Vec3 vec31 = $this$rayTraceWithCustomRotation.func_174806_f(pitch, yaw);
        Vec3 vec32 = vec3.func_72441_c(vec31.field_72450_a * blockReachDistance, vec31.field_72448_b * blockReachDistance, vec31.field_72449_c * blockReachDistance);
        return $this$rayTraceWithCustomRotation.field_70170_p.func_147447_a(vec3, vec32, false, false, true);
    }

    @Nullable
    public static final MovingObjectPosition rayTraceWithCustomRotation(@NotNull Entity $this$rayTraceWithCustomRotation, double blockReachDistance, @NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter($this$rayTraceWithCustomRotation, "<this>");
        Intrinsics.checkNotNullParameter(rotation, "rotation");
        return EntityExtensionKt.rayTraceWithCustomRotation($this$rayTraceWithCustomRotation, blockReachDistance, rotation.getYaw(), rotation.getPitch());
    }

    @Nullable
    public static final MovingObjectPosition rayTraceWithServerSideRotation(@NotNull Entity $this$rayTraceWithServerSideRotation, double blockReachDistance) {
        Intrinsics.checkNotNullParameter($this$rayTraceWithServerSideRotation, "<this>");
        Rotation rotation = RotationUtils.serverRotation;
        Intrinsics.checkNotNullExpressionValue(rotation, "serverRotation");
        return EntityExtensionKt.rayTraceWithCustomRotation($this$rayTraceWithServerSideRotation, blockReachDistance, rotation);
    }

    public static final void setFixedSensitivityAngles(@NotNull EntityPlayerSP $this$setFixedSensitivityAngles, @Nullable Float yaw, @Nullable Float pitch) {
        Intrinsics.checkNotNullParameter($this$setFixedSensitivityAngles, "<this>");
        if (yaw != null) {
            EntityExtensionKt.setFixedSensitivityYaw($this$setFixedSensitivityAngles, yaw.floatValue());
        }
        if (pitch != null) {
            EntityExtensionKt.setFixedSensitivityPitch($this$setFixedSensitivityAngles, pitch.floatValue());
        }
    }

    public static /* synthetic */ void setFixedSensitivityAngles$default(EntityPlayerSP entityPlayerSP, Float f, Float f2, int n, Object object) {
        if ((n & 1) != 0) {
            f = null;
        }
        if ((n & 2) != 0) {
            f2 = null;
        }
        EntityExtensionKt.setFixedSensitivityAngles(entityPlayerSP, f, f2);
    }

    public static final float getFixedSensitivityYaw(@NotNull EntityPlayerSP $this$fixedSensitivityYaw) {
        Intrinsics.checkNotNullParameter($this$fixedSensitivityYaw, "<this>");
        return Rotation.Companion.getFixedSensitivityAngle$default(Rotation.Companion, ClientUtils.INSTANCE.getMc().field_71439_g.field_70177_z, 0.0f, 0.0f, 6, null);
    }

    public static final void setFixedSensitivityYaw(@NotNull EntityPlayerSP $this$fixedSensitivityYaw, float yaw) {
        Intrinsics.checkNotNullParameter($this$fixedSensitivityYaw, "<this>");
        $this$fixedSensitivityYaw.field_70177_z = Rotation.Companion.getFixedSensitivityAngle$default(Rotation.Companion, yaw, $this$fixedSensitivityYaw.field_70177_z, 0.0f, 4, null);
    }

    public static final float getFixedSensitivityPitch(@NotNull EntityPlayerSP $this$fixedSensitivityPitch) {
        Intrinsics.checkNotNullParameter($this$fixedSensitivityPitch, "<this>");
        return Rotation.Companion.getFixedSensitivityAngle$default(Rotation.Companion, $this$fixedSensitivityPitch.field_70125_A, 0.0f, 0.0f, 6, null);
    }

    public static final void setFixedSensitivityPitch(@NotNull EntityPlayerSP $this$fixedSensitivityPitch, float pitch) {
        Intrinsics.checkNotNullParameter($this$fixedSensitivityPitch, "<this>");
        $this$fixedSensitivityPitch.field_70125_A = Rotation.Companion.getFixedSensitivityAngle$default(Rotation.Companion, RangesKt.coerceIn(pitch, -90.0f, 90.0f), $this$fixedSensitivityPitch.field_70125_A, 0.0f, 4, null);
    }

    public static final boolean sendUseItem(@NotNull EntityPlayerSP $this$sendUseItem, @NotNull ItemStack stack) {
        boolean bl;
        Intrinsics.checkNotNullParameter($this$sendUseItem, "<this>");
        Intrinsics.checkNotNullParameter(stack, "stack");
        if (ClientUtils.INSTANCE.getMc().field_71442_b.func_78747_a()) {
            return false;
        }
        PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C08PacketPlayerBlockPlacement(stack)));
        int prevSize = stack.field_77994_a;
        ItemStack newStack = stack.func_77957_a($this$sendUseItem.field_70170_p, (EntityPlayer)$this$sendUseItem);
        if (!Intrinsics.areEqual(newStack, stack) || newStack.field_77994_a != prevSize) {
            if (newStack.field_77994_a <= 0) {
                ClientUtils.INSTANCE.getMc().field_71439_g.field_71071_by.field_70462_a[ClientUtils.INSTANCE.getMc().field_71439_g.field_71071_by.field_70461_c] = null;
                ForgeEventFactory.onPlayerDestroyItem((EntityPlayer)((EntityPlayer)ClientUtils.INSTANCE.getMc().field_71439_g), (ItemStack)newStack);
            } else {
                ClientUtils.INSTANCE.getMc().field_71439_g.field_71071_by.field_70462_a[ClientUtils.INSTANCE.getMc().field_71439_g.field_71071_by.field_70461_c] = newStack;
            }
            bl = true;
        } else {
            bl = false;
        }
        return bl;
    }

    @NotNull
    public static final Vec3 getEyeVec3(@NotNull EntityPlayer $this$getEyeVec3) {
        Intrinsics.checkNotNullParameter($this$getEyeVec3, "<this>");
        return new Vec3($this$getEyeVec3.field_70165_t, $this$getEyeVec3.func_174813_aQ().field_72338_b + (double)$this$getEyeVec3.func_70047_e(), $this$getEyeVec3.field_70161_v);
    }

    public static final float getRenderHurtTime(@NotNull EntityLivingBase $this$renderHurtTime) {
        Intrinsics.checkNotNullParameter($this$renderHurtTime, "<this>");
        return (float)$this$renderHurtTime.field_70737_aN - ($this$renderHurtTime.field_70737_aN != 0 ? Minecraft.func_71410_x().field_71428_T.field_74281_c : 0.0f);
    }

    public static final float getHurtPercent(@NotNull EntityLivingBase $this$hurtPercent) {
        Intrinsics.checkNotNullParameter($this$hurtPercent, "<this>");
        return EntityExtensionKt.getRenderHurtTime($this$hurtPercent) / (float)10;
    }

    @NotNull
    public static final ResourceLocation getSkin(@NotNull EntityLivingBase $this$skin) {
        ResourceLocation resourceLocation;
        Object object;
        Intrinsics.checkNotNullParameter($this$skin, "<this>");
        if ($this$skin instanceof EntityPlayer) {
            NetworkPlayerInfo networkPlayerInfo = Minecraft.func_71410_x().func_147114_u().func_175102_a(((EntityPlayer)$this$skin).func_110124_au());
            object = networkPlayerInfo == null ? null : networkPlayerInfo.func_178837_g();
        } else {
            object = resourceLocation = (ResourceLocation)null;
        }
        if (object == null) {
            ResourceLocation resourceLocation2 = DefaultPlayerSkin.func_177335_a();
            Intrinsics.checkNotNullExpressionValue(resourceLocation2, "getDefaultSkinLegacy()");
            resourceLocation = resourceLocation2;
        }
        return resourceLocation;
    }

    public static final int getPing(@NotNull EntityLivingBase $this$ping) {
        Integer n;
        Intrinsics.checkNotNullParameter($this$ping, "<this>");
        if ($this$ping instanceof EntityPlayer) {
            NetworkPlayerInfo networkPlayerInfo = Minecraft.func_71410_x().func_147114_u().func_175102_a(((EntityPlayer)$this$ping).func_110124_au());
            if (networkPlayerInfo == null) {
                n = null;
            } else {
                int n2 = networkPlayerInfo.func_178853_c();
                n = RangesKt.coerceAtLeast(n2, 0);
            }
        } else {
            n = null;
        }
        return n == null ? -1 : n;
    }

    @NotNull
    public static final Vector3d getRenderPos(@NotNull Entity $this$renderPos) {
        Intrinsics.checkNotNullParameter($this$renderPos, "<this>");
        double x = MathUtils.INSTANCE.interpolate($this$renderPos.field_70142_S, $this$renderPos.field_70165_t, ClientUtils.INSTANCE.getMc().field_71428_T.field_74281_c) - ClientUtils.INSTANCE.getMc().func_175598_ae().field_78730_l;
        double y = MathUtils.INSTANCE.interpolate($this$renderPos.field_70137_T, $this$renderPos.field_70163_u, ClientUtils.INSTANCE.getMc().field_71428_T.field_74281_c) - ClientUtils.INSTANCE.getMc().func_175598_ae().field_78731_m;
        double z = MathUtils.INSTANCE.interpolate($this$renderPos.field_70136_U, $this$renderPos.field_70161_v, ClientUtils.INSTANCE.getMc().field_71428_T.field_74281_c) - ClientUtils.INSTANCE.getMc().func_175598_ae().field_78728_n;
        return new Vector3d(x, y, z);
    }

    @NotNull
    public static final AxisAlignedBB getRenderBoundingBox(@NotNull Entity $this$renderBoundingBox) {
        Intrinsics.checkNotNullParameter($this$renderBoundingBox, "<this>");
        AxisAlignedBB axisAlignedBB = $this$renderBoundingBox.func_174813_aQ().func_72317_d(-$this$renderBoundingBox.field_70165_t, -$this$renderBoundingBox.field_70163_u, -$this$renderBoundingBox.field_70161_v).func_72317_d(EntityExtensionKt.getRenderPos((Entity)$this$renderBoundingBox).x, EntityExtensionKt.getRenderPos((Entity)$this$renderBoundingBox).y, EntityExtensionKt.getRenderPos((Entity)$this$renderBoundingBox).z);
        Intrinsics.checkNotNullExpressionValue(axisAlignedBB, "this.entityBoundingBox\n \u2026rPos.y, this.renderPos.z)");
        return axisAlignedBB;
    }

    @NotNull
    public static final AxisAlignedBB getHitBox(@NotNull Entity $this$hitBox) {
        Intrinsics.checkNotNullParameter($this$hitBox, "<this>");
        double borderSize = $this$hitBox.func_70111_Y();
        AxisAlignedBB axisAlignedBB = $this$hitBox.func_174813_aQ().func_72314_b(borderSize, borderSize, borderSize);
        Intrinsics.checkNotNullExpressionValue(axisAlignedBB, "entityBoundingBox.expand\u2026, borderSize, borderSize)");
        return axisAlignedBB;
    }

    @Nullable
    public static final MovingObjectPosition rayTraceCustom(@NotNull Entity $this$rayTraceCustom, double blockReachDistance, float yaw, float pitch) {
        Intrinsics.checkNotNullParameter($this$rayTraceCustom, "<this>");
        Vec3 vec3 = ClientUtils.INSTANCE.getMc().field_71439_g.func_174824_e(1.0f);
        Vec3 vec31 = ClientUtils.INSTANCE.getMc().field_71439_g.func_174806_f(yaw, pitch);
        Vec3 vec32 = vec3.func_72441_c(vec31.field_72450_a * blockReachDistance, vec31.field_72448_b * blockReachDistance, vec31.field_72449_c * blockReachDistance);
        return ClientUtils.INSTANCE.getMc().field_71441_e.func_147447_a(vec3, vec32, false, false, true);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final List<Entity> getEntitiesInRadius(@NotNull World $this$getEntitiesInRadius, @NotNull Entity entity, double radius) {
        Intrinsics.checkNotNullParameter($this$getEntitiesInRadius, "<this>");
        Intrinsics.checkNotNullParameter(entity, "entity");
        AxisAlignedBB box = entity.func_174813_aQ().func_72314_b(radius, radius, radius);
        int chunkMinX = (int)Math.floor(box.field_72340_a * 0.0625);
        int chunkMaxX = (int)Math.ceil(box.field_72336_d * 0.0625);
        int chunkMinZ = (int)Math.floor(box.field_72339_c * 0.0625);
        int chunkMaxZ = (int)Math.ceil(box.field_72334_f * 0.0625);
        List entities = new ArrayList();
        Iterable $this$forEach$iv = new IntRange(chunkMinX, chunkMaxX);
        boolean $i$f$forEach = false;
        Iterator iterator2 = $this$forEach$iv.iterator();
        while (iterator2.hasNext()) {
            void $this$forEach$iv2;
            int element$iv;
            int x = element$iv = ((IntIterator)iterator2).nextInt();
            boolean bl = false;
            Sequence sequence = SequencesKt.filter(SequencesKt.map(CollectionsKt.asSequence(new IntRange(chunkMinZ, chunkMaxZ)), (Function1)new Function1<Integer, Chunk>($this$getEntitiesInRadius, x){
                final /* synthetic */ World $this_getEntitiesInRadius;
                final /* synthetic */ int $x;
                {
                    this.$this_getEntitiesInRadius = $receiver;
                    this.$x = $x;
                    super(1);
                }

                public final Chunk invoke(int z) {
                    return this.$this_getEntitiesInRadius.func_72964_e(this.$x, z);
                }
            }), getEntitiesInRadius.1.2.INSTANCE);
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                Chunk it = (Chunk)element$iv2;
                boolean bl2 = false;
                it.func_177414_a(entity, box, entities, null);
            }
        }
        return entities;
    }

    public static /* synthetic */ List getEntitiesInRadius$default(World world, Entity entity, double d, int n, Object object) {
        if ((n & 2) != 0) {
            d = 16.0;
        }
        return EntityExtensionKt.getEntitiesInRadius(world, entity, d);
    }

    public static final double getLookDistanceToEntityBox(@NotNull Entity $this$getLookDistanceToEntityBox, @NotNull Entity entity, @Nullable Rotation rotation, double range) {
        double d;
        Intrinsics.checkNotNullParameter($this$getLookDistanceToEntityBox, "<this>");
        Intrinsics.checkNotNullParameter(entity, "entity");
        Vec3 eyes = $this$getLookDistanceToEntityBox.func_174824_e(1.0f);
        Rotation rotation2 = rotation;
        if (rotation2 == null) {
            rotation2 = RotationUtils.targetRotation;
        }
        Vec3 end = OtherExtensionKt.multiply(rotation2.toDirection(), range).func_178787_e(eyes);
        MovingObjectPosition movingObjectPosition = entity.func_174813_aQ().func_72327_a(eyes, end);
        if (movingObjectPosition == null) {
            d = Double.MAX_VALUE;
        } else {
            double d2;
            Vec3 vec3 = movingObjectPosition.field_72307_f;
            d = vec3 == null ? Double.MAX_VALUE : (d2 = vec3.func_72438_d(eyes));
        }
        return d;
    }

    public static /* synthetic */ double getLookDistanceToEntityBox$default(Entity entity, Entity entity2, Rotation rotation, double d, int n, Object object) {
        if ((n & 1) != 0) {
            entity2 = entity;
        }
        if ((n & 2) != 0) {
            rotation = null;
        }
        if ((n & 4) != 0) {
            d = 10.0;
        }
        return EntityExtensionKt.getLookDistanceToEntityBox(entity, entity2, rotation, d);
    }

    @NotNull
    public static final Vec3 getEyesLoc(@NotNull Entity $this$eyesLoc) {
        Intrinsics.checkNotNullParameter($this$eyesLoc, "<this>");
        Vec3 vec3 = $this$eyesLoc.func_174824_e(1.0f);
        Intrinsics.checkNotNullExpressionValue(vec3, "getPositionEyes(1f)");
        return vec3;
    }

    public static final double distanceTo(@NotNull Vec3 $this$distanceTo, @NotNull AxisAlignedBB bb) {
        Intrinsics.checkNotNullParameter($this$distanceTo, "<this>");
        Intrinsics.checkNotNullParameter(bb, "bb");
        Vec3 pos = EntityExtensionKt.getNearestPointBB($this$distanceTo, bb);
        double xDist = Math.abs(pos.field_72450_a - $this$distanceTo.field_72450_a);
        double yDist = Math.abs(pos.field_72448_b - $this$distanceTo.field_72448_b);
        double zDist = Math.abs(pos.field_72449_c - $this$distanceTo.field_72449_c);
        return Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2) + Math.pow(zDist, 2));
    }
}

