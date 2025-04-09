/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0006\u0010\u001f\u001a\u00020\u001bR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001a\u0010\u000e\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\b\"\u0004\b\u0010\u0010\nR\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0014\"\u0004\b\u0019\u0010\u0016\u00a8\u0006 "}, d2={"Lnet/ccbluex/liquidbounce/utils/FakePlayer;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "fakePlayer", "Lnet/minecraft/client/entity/EntityOtherPlayerMP;", "posX", "", "getPosX", "()D", "setPosX", "(D)V", "posY", "getPosY", "setPosY", "posZ", "getPosZ", "setPosZ", "rotationPitch", "", "getRotationPitch", "()F", "setRotationPitch", "(F)V", "rotationYaw", "getRotationYaw", "setRotationYaw", "disable", "", "setup", "entity", "Lnet/minecraft/entity/player/EntityPlayer;", "update", "CrossSine"})
public final class FakePlayer
extends MinecraftInstance {
    private double posX;
    private double posY;
    private double posZ;
    private float rotationPitch;
    private float rotationYaw;
    @Nullable
    private EntityOtherPlayerMP fakePlayer;

    public final double getPosX() {
        return this.posX;
    }

    public final void setPosX(double d) {
        this.posX = d;
    }

    public final double getPosY() {
        return this.posY;
    }

    public final void setPosY(double d) {
        this.posY = d;
    }

    public final double getPosZ() {
        return this.posZ;
    }

    public final void setPosZ(double d) {
        this.posZ = d;
    }

    public final float getRotationPitch() {
        return this.rotationPitch;
    }

    public final void setRotationPitch(float f) {
        this.rotationPitch = f;
    }

    public final float getRotationYaw() {
        return this.rotationYaw;
    }

    public final void setRotationYaw(float f) {
        this.rotationYaw = f;
    }

    public final void setup(@NotNull EntityPlayer entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        if (this.fakePlayer == null) {
            EntityOtherPlayerMP entityOtherPlayerMP = this.fakePlayer = new EntityOtherPlayerMP((World)MinecraftInstance.mc.field_71441_e, entity.func_146103_bH());
            Intrinsics.checkNotNull(entityOtherPlayerMP);
            entityOtherPlayerMP.func_71049_a(entity, true);
            EntityOtherPlayerMP entityOtherPlayerMP2 = this.fakePlayer;
            Intrinsics.checkNotNull(entityOtherPlayerMP2);
            entityOtherPlayerMP2.func_82149_j((Entity)entity);
            Intrinsics.checkNotNull(this.fakePlayer);
            this.fakePlayer.field_70759_as = entity.field_70759_as;
            MinecraftInstance.mc.field_71441_e.func_73027_a(-1337, (Entity)this.fakePlayer);
        }
    }

    public final void update() {
        EntityOtherPlayerMP entityOtherPlayerMP = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP);
        if (!(this.posX == entityOtherPlayerMP.field_70165_t)) {
            Intrinsics.checkNotNull(this.fakePlayer);
            this.fakePlayer.field_70165_t = this.posX;
        }
        EntityOtherPlayerMP entityOtherPlayerMP2 = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP2);
        if (!(this.posY == entityOtherPlayerMP2.field_70163_u)) {
            Intrinsics.checkNotNull(this.fakePlayer);
            this.fakePlayer.field_70163_u = this.posY;
        }
        EntityOtherPlayerMP entityOtherPlayerMP3 = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP3);
        if (!(this.posZ == entityOtherPlayerMP3.field_70161_v)) {
            Intrinsics.checkNotNull(this.fakePlayer);
            this.fakePlayer.field_70161_v = this.posZ;
        }
        EntityOtherPlayerMP entityOtherPlayerMP4 = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP4);
        if (!(this.rotationYaw == entityOtherPlayerMP4.field_70759_as)) {
            Intrinsics.checkNotNull(this.fakePlayer);
            this.fakePlayer.field_70759_as = this.rotationYaw;
        }
        EntityOtherPlayerMP entityOtherPlayerMP5 = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP5);
        if (!(this.rotationYaw == entityOtherPlayerMP5.field_70177_z)) {
            Intrinsics.checkNotNull(this.fakePlayer);
            this.fakePlayer.field_70177_z = this.rotationYaw;
        }
        EntityOtherPlayerMP entityOtherPlayerMP6 = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP6);
        if (!(this.rotationPitch == entityOtherPlayerMP6.field_70125_A)) {
            Intrinsics.checkNotNull(this.fakePlayer);
            this.fakePlayer.field_70125_A = this.rotationPitch;
        }
    }

    public final void disable() {
        if (this.fakePlayer != null) {
            WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
            EntityOtherPlayerMP entityOtherPlayerMP = this.fakePlayer;
            Intrinsics.checkNotNull(entityOtherPlayerMP);
            worldClient.func_73028_b(entityOtherPlayerMP.func_145782_y());
            this.fakePlayer = null;
        }
    }
}

