/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="FreeCam", category=ModuleCategory.PLAYER, autoDisable=EnumAutoDisableType.RESPAWN)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0013H\u0016J\u0010\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0010\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0019H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/FreeCam;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "c03SpoofValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "fakePlayer", "Lnet/minecraft/client/entity/EntityOtherPlayerMP;", "flyValue", "motionValue", "motionX", "", "motionY", "motionZ", "noClipValue", "packetCount", "", "speedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class FreeCam
extends Module {
    @NotNull
    private final FloatValue speedValue = new FloatValue("Speed", 0.8f, 0.1f, 2.0f);
    @NotNull
    private final BoolValue flyValue = new BoolValue("Fly", true);
    @NotNull
    private final BoolValue noClipValue = new BoolValue("NoClip", true);
    @NotNull
    private final BoolValue motionValue = new BoolValue("RecordMotion", true);
    @NotNull
    private final BoolValue c03SpoofValue = new BoolValue("C03Spoof", false);
    @Nullable
    private EntityOtherPlayerMP fakePlayer;
    private double motionX;
    private double motionY;
    private double motionZ;
    private int packetCount;

    @Override
    public void onEnable() {
        if (MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
        if (((Boolean)this.motionValue.get()).booleanValue()) {
            this.motionX = MinecraftInstance.mc.field_71439_g.field_70159_w;
            this.motionY = MinecraftInstance.mc.field_71439_g.field_70181_x;
            this.motionZ = MinecraftInstance.mc.field_71439_g.field_70179_y;
        } else {
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
        }
        this.packetCount = 0;
        EntityOtherPlayerMP entityOtherPlayerMP = this.fakePlayer = new EntityOtherPlayerMP((World)MinecraftInstance.mc.field_71441_e, MinecraftInstance.mc.field_71439_g.func_146103_bH());
        Intrinsics.checkNotNull(entityOtherPlayerMP);
        entityOtherPlayerMP.func_71049_a((EntityPlayer)MinecraftInstance.mc.field_71439_g, true);
        Intrinsics.checkNotNull(this.fakePlayer);
        this.fakePlayer.field_70759_as = MinecraftInstance.mc.field_71439_g.field_70759_as;
        EntityOtherPlayerMP entityOtherPlayerMP2 = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP2);
        entityOtherPlayerMP2.func_82149_j((Entity)MinecraftInstance.mc.field_71439_g);
        MinecraftInstance.mc.field_71441_e.func_73027_a(-((int)(Math.random() * (double)10000)), (Entity)this.fakePlayer);
        if (((Boolean)this.noClipValue.get()).booleanValue()) {
            MinecraftInstance.mc.field_71439_g.field_70145_X = true;
        }
    }

    @Override
    public void onDisable() {
        if (MinecraftInstance.mc.field_71439_g == null || this.fakePlayer == null) {
            return;
        }
        EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
        EntityOtherPlayerMP entityOtherPlayerMP = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP);
        double d = entityOtherPlayerMP.field_70165_t;
        EntityOtherPlayerMP entityOtherPlayerMP2 = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP2);
        double d2 = entityOtherPlayerMP2.field_70163_u;
        EntityOtherPlayerMP entityOtherPlayerMP3 = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP3);
        entityPlayerSP.func_70080_a(d, d2, entityOtherPlayerMP3.field_70161_v, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A);
        WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
        EntityOtherPlayerMP entityOtherPlayerMP4 = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP4);
        worldClient.func_73028_b(entityOtherPlayerMP4.func_145782_y());
        this.fakePlayer = null;
        MinecraftInstance.mc.field_71439_g.field_70159_w = this.motionX;
        MinecraftInstance.mc.field_71439_g.field_70181_x = this.motionY;
        MinecraftInstance.mc.field_71439_g.field_70179_y = this.motionZ;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)this.noClipValue.get()).booleanValue()) {
            MinecraftInstance.mc.field_71439_g.field_70145_X = true;
        }
        MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0f;
        if (((Boolean)this.flyValue.get()).booleanValue()) {
            EntityPlayerSP entityPlayerSP;
            float value = ((Number)this.speedValue.get()).floatValue();
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
            if (MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
                entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70181_x += (double)value;
            }
            if (MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d()) {
                entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70181_x -= (double)value;
            }
            MovementUtils.INSTANCE.strafe(value);
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (((Boolean)this.c03SpoofValue.get()).booleanValue()) {
            if (packet instanceof C03PacketPlayer.C04PacketPlayerPosition || packet instanceof C03PacketPlayer.C05PacketPlayerLook || packet instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
                if (this.packetCount >= 20) {
                    this.packetCount = 0;
                    EntityOtherPlayerMP entityOtherPlayerMP = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP);
                    double d = entityOtherPlayerMP.field_70165_t;
                    EntityOtherPlayerMP entityOtherPlayerMP2 = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP2);
                    double d2 = entityOtherPlayerMP2.field_70163_u;
                    EntityOtherPlayerMP entityOtherPlayerMP3 = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP3);
                    double d3 = entityOtherPlayerMP3.field_70161_v;
                    EntityOtherPlayerMP entityOtherPlayerMP4 = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP4);
                    float f = entityOtherPlayerMP4.field_70177_z;
                    EntityOtherPlayerMP entityOtherPlayerMP5 = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP5);
                    float f2 = entityOtherPlayerMP5.field_70125_A;
                    EntityOtherPlayerMP entityOtherPlayerMP6 = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP6);
                    PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(d, d2, d3, f, f2, entityOtherPlayerMP6.field_70122_E)));
                } else {
                    int n = this.packetCount;
                    this.packetCount = n + 1;
                    EntityOtherPlayerMP entityOtherPlayerMP = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP);
                    PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer(entityOtherPlayerMP.field_70122_E)));
                }
                event.cancelEvent();
            }
        } else if (packet instanceof C03PacketPlayer) {
            event.cancelEvent();
        }
        if (packet instanceof S08PacketPlayerPosLook) {
            EntityOtherPlayerMP entityOtherPlayerMP = this.fakePlayer;
            Intrinsics.checkNotNull(entityOtherPlayerMP);
            entityOtherPlayerMP.func_70107_b(((S08PacketPlayerPosLook)packet).field_148940_a, ((S08PacketPlayerPosLook)packet).field_148938_b, ((S08PacketPlayerPosLook)packet).field_148939_c);
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
            EntityOtherPlayerMP entityOtherPlayerMP7 = this.fakePlayer;
            Intrinsics.checkNotNull(entityOtherPlayerMP7);
            double d = entityOtherPlayerMP7.field_70165_t;
            EntityOtherPlayerMP entityOtherPlayerMP8 = this.fakePlayer;
            Intrinsics.checkNotNull(entityOtherPlayerMP8);
            double d4 = entityOtherPlayerMP8.field_70163_u;
            EntityOtherPlayerMP entityOtherPlayerMP9 = this.fakePlayer;
            Intrinsics.checkNotNull(entityOtherPlayerMP9);
            double d5 = entityOtherPlayerMP9.field_70161_v;
            EntityOtherPlayerMP entityOtherPlayerMP10 = this.fakePlayer;
            Intrinsics.checkNotNull(entityOtherPlayerMP10);
            float f = entityOtherPlayerMP10.field_70177_z;
            EntityOtherPlayerMP entityOtherPlayerMP11 = this.fakePlayer;
            Intrinsics.checkNotNull(entityOtherPlayerMP11);
            float f3 = entityOtherPlayerMP11.field_70125_A;
            EntityOtherPlayerMP entityOtherPlayerMP12 = this.fakePlayer;
            Intrinsics.checkNotNull(entityOtherPlayerMP12);
            PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(d, d4, d5, f, f3, entityOtherPlayerMP12.field_70122_E)));
            event.cancelEvent();
        }
    }
}

