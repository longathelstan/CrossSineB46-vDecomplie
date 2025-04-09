/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_9to1_8.storage;

import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PlayerPositionTracker
implements StorableObject {
    private final Queue<PacketWrapper> animations = new ConcurrentLinkedQueue<PacketWrapper>();
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;
    private boolean onGround;
    private int confirmId = -1;

    public void setPos(double x, double y, double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }

    public void sendAnimations() {
        PacketWrapper wrapper;
        while ((wrapper = this.animations.poll()) != null) {
            wrapper.sendToServer(Protocol1_9To1_8.class);
        }
    }

    public void queueAnimation(PacketWrapper wrapper) {
        this.animations.add(wrapper);
    }

    public void setYaw(float yaw) {
        this.yaw = yaw % 360.0f;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch % 360.0f;
    }

    public double getPosX() {
        return this.posX;
    }

    public double getPosY() {
        return this.posY;
    }

    public double getPosZ() {
        return this.posZ;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public int getConfirmId() {
        return this.confirmId;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setConfirmId(int confirmId) {
        this.confirmId = confirmId;
    }
}

