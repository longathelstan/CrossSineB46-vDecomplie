/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.BackTrack;
import net.ccbluex.liquidbounce.features.module.modules.combat.PacketLog;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name="BackTrack", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010,\u001a\u00020-2\b\b\u0002\u0010.\u001a\u00020\u0007H\u0002J\u0010\u0010/\u001a\u00020-2\u0006\u00100\u001a\u00020\u0007H\u0002J \u00101\u001a\u00020-2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002032\u0006\u00105\u001a\u000203H\u0002J\b\u00106\u001a\u000203H\u0002J\b\u00107\u001a\u000203H\u0002J\b\u00108\u001a\u000203H\u0002J\b\u00109\u001a\u00020-H\u0002J\u0010\u0010:\u001a\u00020-2\u0006\u0010;\u001a\u00020<H\u0007J\b\u0010=\u001a\u00020-H\u0016J\u0010\u0010>\u001a\u00020-2\u0006\u0010;\u001a\u00020?H\u0007J\u0010\u0010@\u001a\u00020-2\u0006\u0010;\u001a\u00020\u001eH\u0007J\u0010\u0010A\u001a\u00020-2\u0006\u0010;\u001a\u00020BH\u0007J\u0010\u0010C\u001a\u00020-2\u0006\u0010;\u001a\u00020DH\u0007J\u0010\u0010E\u001a\u00020-2\u0006\u0010;\u001a\u00020FH\u0007J\b\u0010G\u001a\u00020-H\u0002J\b\u0010H\u001a\u00020-H\u0002J\b\u0010I\u001a\u00020-H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\u00020#8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b$\u0010%R\u001c\u0010&\u001a\u0004\u0018\u00010\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u000e\u0010+\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006J"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/BackTrack;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attackTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "lagMode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "lagTimer", "lagged", "", "lastTarget", "Lnet/minecraft/entity/player/EntityPlayer;", "lastUpdateTime", "", "lerpPos", "Lnet/minecraft/util/Vec3;", "maxDistance", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "minDistance", "nextDelayValue", "packetList", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/PacketLog;", "packetPosValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "packets", "Ljava/util/LinkedList;", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "playerModel", "realPos", "sizeTick", "tag", "", "getTag", "()Ljava/lang/String;", "target", "getTarget", "()Lnet/minecraft/entity/player/EntityPlayer;", "setTarget", "(Lnet/minecraft/entity/player/EntityPlayer;)V", "targetPos", "clearPacket", "", "time", "clearPacketTick", "size", "drawEsp", "x", "", "y", "z", "getX", "getY", "getZ", "interpolatePosition", "onAttack", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onDisable", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "onPreUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "reset", "startDrawing", "stopDrawing", "CrossSine"})
public final class BackTrack
extends Module {
    @NotNull
    public static final BackTrack INSTANCE = new BackTrack();
    @NotNull
    private static final ListValue lagMode;
    @NotNull
    private static final Value<Integer> delayValue;
    @NotNull
    private static final Value<Integer> nextDelayValue;
    @NotNull
    private static final FloatValue maxDistance;
    @NotNull
    private static final FloatValue minDistance;
    @NotNull
    private static final Value<Integer> sizeTick;
    @NotNull
    private static final Value<Boolean> playerModel;
    @NotNull
    private static final BoolValue packetPosValue;
    @NotNull
    private static final ConcurrentLinkedQueue<PacketLog> packetList;
    @NotNull
    private static final LinkedList<PacketEvent> packets;
    @Nullable
    private static EntityPlayer target;
    @Nullable
    private static EntityPlayer lastTarget;
    @NotNull
    private static Vec3 realPos;
    @NotNull
    private static Vec3 lerpPos;
    @NotNull
    private static Vec3 targetPos;
    @NotNull
    private static TimerMS attackTimer;
    private static boolean lagged;
    @NotNull
    private static TimerMS lagTimer;
    private static long lastUpdateTime;

    private BackTrack() {
    }

    @Nullable
    public final EntityPlayer getTarget() {
        return target;
    }

    public final void setTarget(@Nullable EntityPlayer entityPlayer) {
        target = entityPlayer;
    }

    @Override
    public void onDisable() {
        this.reset();
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        attackTimer.reset();
        Entity entity = event.getTargetEntity();
        if (EntityUtils.INSTANCE.isSelected(entity, true) && entity instanceof EntityPlayer && ((EntityPlayer)entity).func_145782_y() != 1337 && !Intrinsics.areEqual(target, entity)) {
            lastTarget = target;
            target = (EntityPlayer)entity;
            Vec3 vec3 = ((EntityPlayer)entity).func_174791_d();
            Intrinsics.checkNotNullExpressionValue(vec3, "entity.positionVector");
            targetPos = vec3;
            vec3 = ((EntityPlayer)entity).func_174791_d();
            Intrinsics.checkNotNullExpressionValue(vec3, "entity.positionVector");
            realPos = vec3;
            vec3 = ((EntityPlayer)entity).func_174791_d();
            Intrinsics.checkNotNullExpressionValue(vec3, "entity.positionVector");
            lerpPos = vec3;
            float f = ((Number)minDistance.get()).floatValue();
            float f2 = ((Number)maxDistance.get()).floatValue();
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
            Entity entity2 = (Entity)entityPlayerSP;
            EntityPlayer entityPlayer = target;
            Intrinsics.checkNotNull(entityPlayer);
            double d = EntityExtensionKt.getDistanceToEntityBox(entity2, (Entity)entityPlayer);
            boolean bl = (double)f <= d ? d <= (double)f2 : false;
            if (bl && !lagged) {
                lagged = true;
                lagTimer.reset();
            }
        }
    }

    @EventTarget
    public final void onPreUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (lagged) {
            float f = ((Number)minDistance.get()).floatValue();
            float f2 = ((Number)maxDistance.get()).floatValue();
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
            Entity entity = (Entity)entityPlayerSP;
            EntityPlayer entityPlayer = target;
            Intrinsics.checkNotNull(entityPlayer);
            double d = EntityExtensionKt.getDistanceToEntityBox(entity, (Entity)entityPlayer);
            if (!((double)f <= d ? d <= (double)f2 : false) || lagTimer.hasTimePassed(((Number)nextDelayValue.get()).intValue())) {
                lagged = false;
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g == null || MinecraftInstance.mc.field_71441_e == null) {
            return;
        }
        Packet<?> packet = event.getPacket();
        if (packet instanceof S03PacketTimeUpdate) {
            return;
        }
        if (packet instanceof S01PacketJoinGame || packet instanceof S07PacketRespawn) {
            if (lagMode.equals("LegitReach") || lagMode.equals("Automatic")) {
                this.clearPacket(0);
            } else {
                this.clearPacketTick(0);
            }
            return;
        }
        if (!event.isCancelled()) {
            Packet<?> packet2 = packet.getClass().getSimpleName();
            Intrinsics.checkNotNullExpressionValue(packet2, "packet.javaClass.simpleName");
            if (StringsKt.startsWith((String)packet2, "S", true)) {
                if (lagMode.equals("Automatic") || !attackTimer.hasTimePassed(1100L)) {
                    if (lagMode.equals("LegitReach") || lagMode.equals("Automatic") && lagged) {
                        packetList.add(new PacketLog(packet, System.currentTimeMillis()));
                    } else {
                        packets.add(event);
                    }
                    event.cancelEvent();
                }
                if ((packet2 = packet) instanceof S14PacketEntity) {
                    if (target != null) {
                        int n = ((S14PacketEntity)packet).func_149065_a((World)MinecraftInstance.mc.field_71441_e).func_145782_y();
                        EntityPlayer entityPlayer = target;
                        Intrinsics.checkNotNull(entityPlayer);
                        if (n == entityPlayer.func_145782_y()) {
                            Vec3 vec3 = targetPos.func_72441_c((double)((S14PacketEntity)packet).func_149062_c() / 32.0, (double)((S14PacketEntity)packet).func_149061_d() / 32.0, (double)((S14PacketEntity)packet).func_149064_e() / 32.0);
                            Intrinsics.checkNotNullExpressionValue(vec3, "targetPos.addVector(\n   \u2026                        )");
                            targetPos = vec3;
                            vec3 = realPos.func_72441_c((double)((S14PacketEntity)packet).func_149062_c() / 32.0, (double)((S14PacketEntity)packet).func_149061_d() / 32.0, (double)((S14PacketEntity)packet).func_149064_e() / 32.0);
                            Intrinsics.checkNotNullExpressionValue(vec3, "realPos.addVector(\n     \u2026                        )");
                            realPos = vec3;
                        }
                    }
                } else if (packet2 instanceof S18PacketEntityTeleport && target != null) {
                    int n = ((S18PacketEntityTeleport)packet).func_149451_c();
                    EntityPlayer entityPlayer = target;
                    Intrinsics.checkNotNull(entityPlayer);
                    if (n == entityPlayer.func_145782_y()) {
                        targetPos = new Vec3((double)((S18PacketEntityTeleport)packet).func_149449_d() / 32.0, (double)((S18PacketEntityTeleport)packet).func_149448_e() / 32.0, (double)((S18PacketEntityTeleport)packet).func_149446_f() / 32.0);
                        realPos = new Vec3((double)((S18PacketEntityTeleport)packet).func_149449_d() / 32.0, (double)((S18PacketEntityTeleport)packet).func_149448_e() / 32.0, (double)((S18PacketEntityTeleport)packet).func_149446_f() / 32.0);
                    }
                }
            }
        }
    }

    private final void reset() {
        target = null;
        lastTarget = null;
        lerpPos = new Vec3(0.0, 0.0, 0.0);
        this.clearPacket(0);
        this.clearPacketTick(0);
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.reset();
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getEventState() == EventState.POST) {
            if (lagMode.equals("Automatic")) {
                BackTrack.clearPacket$default(this, 0, 1, null);
            }
            if (lagMode.equals("LegitReach")) {
                if (target == null || attackTimer.hasTimePassed(1000L)) {
                    this.clearPacket(0);
                } else {
                    BackTrack.clearPacket$default(this, 0, 1, null);
                }
            } else if (target == null || attackTimer.hasTimePassed(1000L)) {
                this.clearPacketTick(0);
            } else {
                this.clearPacketTick(((Number)sizeTick.get()).intValue());
            }
        }
    }

    private final void clearPacket(int time) {
        if (packetList.isEmpty()) {
            return;
        }
        for (PacketLog packet : packetList) {
            if (time != 0 && System.currentTimeMillis() <= packet.getTime() + (long)time) continue;
            Packet<?> p = packet.getPacket();
            p.func_148833_a((INetHandler)MinecraftInstance.mc.func_147114_u());
            packetList.remove(packet);
        }
    }

    static /* synthetic */ void clearPacket$default(BackTrack backTrack, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = ((Number)delayValue.get()).intValue();
        }
        backTrack.clearPacket(n);
    }

    private final void clearPacketTick(int size) {
        if (packets.isEmpty()) {
            return;
        }
        while (packets.size() > size) {
            if (packets.pollFirst() == null) continue;
            try {
                PacketEvent event;
                Packet<?> packet = event.getPacket();
                packet.func_148833_a((INetHandler)MinecraftInstance.mc.func_147114_u());
            }
            catch (Exception exception) {}
        }
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (lagMode.equals("Automatic") && !lagged) {
            return;
        }
        if (target != null) {
            if (playerModel.get().booleanValue()) {
                GL11.glPushMatrix();
                RenderManager renderManager = MinecraftInstance.mc.func_175598_ae();
                Entity entity = (Entity)target;
                double d = this.getX() - MinecraftInstance.mc.func_175598_ae().field_78725_b;
                double d2 = this.getY() - MinecraftInstance.mc.func_175598_ae().field_78726_c;
                double d3 = this.getZ() - MinecraftInstance.mc.func_175598_ae().field_78723_d;
                EntityPlayer entityPlayer = target;
                Intrinsics.checkNotNull(entityPlayer);
                renderManager.func_147939_a(entity, d, d2, d3, entityPlayer.field_70177_z, event.getPartialTicks(), true);
                GL11.glPopMatrix();
                GlStateManager.func_179117_G();
            } else {
                this.startDrawing();
                this.drawEsp(this.getX(), this.getY(), this.getZ());
                this.stopDrawing();
            }
        }
        if (target != null) {
            this.interpolatePosition();
        }
    }

    private final void interpolatePosition() {
        long currentTime = System.nanoTime();
        double deltaTime = (double)(currentTime - lastUpdateTime) / 1.0E9;
        lastUpdateTime = currentTime;
        int lerpSpeed = 7;
        double lerpAmount = (double)lerpSpeed * deltaTime;
        double clampedLerpAmount = RangesKt.coerceIn(lerpAmount, 0.0, 1.0);
        lerpPos = new Vec3(BackTrack.lerpPos.field_72450_a + (BackTrack.targetPos.field_72450_a - BackTrack.lerpPos.field_72450_a) * clampedLerpAmount, BackTrack.lerpPos.field_72448_b + (BackTrack.targetPos.field_72448_b - BackTrack.lerpPos.field_72448_b) * clampedLerpAmount, BackTrack.lerpPos.field_72449_c + (BackTrack.targetPos.field_72449_c - BackTrack.lerpPos.field_72449_c) * clampedLerpAmount);
    }

    private final void startDrawing() {
        GL11.glPushMatrix();
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
    }

    private final void stopDrawing() {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glPopMatrix();
    }

    private final double getX() {
        return (Boolean)packetPosValue.get() != false ? BackTrack.realPos.field_72450_a : BackTrack.lerpPos.field_72450_a;
    }

    private final double getY() {
        return (Boolean)packetPosValue.get() != false ? BackTrack.realPos.field_72448_b : BackTrack.lerpPos.field_72448_b;
    }

    private final double getZ() {
        return (Boolean)packetPosValue.get() != false ? BackTrack.realPos.field_72449_c : BackTrack.lerpPos.field_72449_c;
    }

    private final void drawEsp(double x, double y, double z) {
        RenderUtils.glColor(ClientTheme.INSTANCE.getColorWithAlpha(0, 100, true));
        RenderUtils.drawBoundingBlock(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(-MinecraftInstance.mc.field_71439_g.field_70165_t, -MinecraftInstance.mc.field_71439_g.field_70163_u, -MinecraftInstance.mc.field_71439_g.field_70161_v).func_72317_d(x, y, z).func_72314_b(0.04, 0.04, 0.04));
        GlStateManager.func_179117_G();
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)lagMode.get() + ' ' + (lagMode.equals("LegitReach") || lagMode.equals("Automatic") ? ((Number)delayValue.get()).intValue() + " MS" : ((Number)sizeTick.get()).intValue() + " Tick");
    }

    public static final /* synthetic */ void access$reset(BackTrack $this) {
        $this.reset();
    }

    public static final /* synthetic */ ListValue access$getLagMode$p() {
        return lagMode;
    }

    public static final /* synthetic */ FloatValue access$getMinDistance$p() {
        return minDistance;
    }

    public static final /* synthetic */ FloatValue access$getMaxDistance$p() {
        return maxDistance;
    }

    static {
        String[] stringArray = new String[]{"LegitReach", "Automatic", "LagSize"};
        String[] stringArray2 = stringArray;
        lagMode = new ListValue(stringArray2){

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
                Intrinsics.checkNotNullParameter(oldValue, "oldValue");
                Intrinsics.checkNotNullParameter(newValue, "newValue");
                BackTrack.access$reset(BackTrack.INSTANCE);
            }
        };
        delayValue = new IntegerValue("Ping-MS", 350, 0, 2000).displayable(delayValue.1.INSTANCE);
        nextDelayValue = new IntegerValue("NextDelay-MS", 500, 0, 5000).displayable(nextDelayValue.1.INSTANCE);
        maxDistance = (FloatValue)new FloatValue(){

            protected void onChanged(float oldValue, float newValue) {
                if (newValue <= ((Number)BackTrack.access$getMinDistance$p().get()).floatValue()) {
                    this.set(BackTrack.access$getMinDistance$p().get());
                }
            }
        }.displayable(maxDistance.2.INSTANCE);
        minDistance = (FloatValue)new FloatValue(){

            protected void onChanged(float oldValue, float newValue) {
                if (newValue >= ((Number)BackTrack.access$getMaxDistance$p().get()).floatValue()) {
                    this.set(BackTrack.access$getMaxDistance$p().get());
                }
            }
        }.displayable(minDistance.2.INSTANCE);
        sizeTick = new IntegerValue("Size", 60, 10, 100).displayable(sizeTick.1.INSTANCE);
        playerModel = new BoolValue("PlayerModel", true).displayable(playerModel.1.INSTANCE);
        packetPosValue = new BoolValue("UsePacketPos", false);
        packetList = new ConcurrentLinkedQueue();
        packets = new LinkedList();
        realPos = new Vec3(0.0, 0.0, 0.0);
        lerpPos = new Vec3(0.0, 0.0, 0.0);
        targetPos = new Vec3(0.0, 0.0, 0.0);
        attackTimer = new TimerMS();
        lagTimer = new TimerMS();
        lastUpdateTime = System.nanoTime();
    }
}

