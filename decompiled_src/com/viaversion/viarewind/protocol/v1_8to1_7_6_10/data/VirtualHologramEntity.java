/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.data;

import com.viaversion.viarewind.api.minecraft.entitydata.EntityDataTypes1_7_6_10;
import com.viaversion.viarewind.api.minecraft.math.AABB;
import com.viaversion.viarewind.api.minecraft.math.Vector3d;
import com.viaversion.viarewind.api.type.version.Types1_7_6_10;
import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.Protocol1_8To1_7_6_10;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.data.EntityDataIndex1_7_6_10;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.rewriter.EntityPacketRewriter1_8;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_8;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.TrackedEntityImpl;
import com.viaversion.viaversion.rewriter.entitydata.EntityDataHandlerEventImpl;
import java.util.ArrayList;
import java.util.List;

public class VirtualHologramEntity {
    final List<EntityData> entityDataTracker = new ArrayList<EntityData>();
    double locX;
    double locY;
    double locZ;
    final UserConnection user;
    final int entityId;
    int[] entityIds = null;
    State currentState = null;
    String name = null;
    float yaw;
    float pitch;
    float headYaw;
    boolean small = false;
    boolean marker = false;

    public VirtualHologramEntity(UserConnection user, int entityId) {
        this.user = user;
        this.entityId = entityId;
    }

    public void setPosition(double x, double y, double z) {
        if (x == this.locX && y == this.locY && z == this.locZ) {
            return;
        }
        this.locX = x;
        this.locY = y;
        this.locZ = z;
        this.updateLocation(false);
    }

    public void setRelativePosition(double x, double y, double z) {
        if (x == 0.0 && y == 0.0 && z == 0.0) {
            return;
        }
        this.locX += x;
        this.locY += y;
        this.locZ += z;
        this.updateLocation(false);
    }

    public void setRotation(float yaw, float pitch) {
        if (this.yaw == yaw || this.headYaw == yaw || this.pitch == pitch) {
            return;
        }
        this.yaw = yaw;
        this.headYaw = yaw;
        this.pitch = pitch;
        this.updateLocation(false);
    }

    public void setHeadYaw(float yaw) {
        if (this.headYaw == yaw) {
            return;
        }
        this.headYaw = yaw;
        this.updateLocation(false);
    }

    public void syncState(EntityPacketRewriter1_8 entityRewriter, List<EntityData> entityDataList) {
        for (EntityData entityData : entityDataList) {
            this.entityDataTracker.removeIf(m2 -> m2.id() == entityData.id());
            this.entityDataTracker.add(entityData);
        }
        int flags = 0;
        int armorStandFlags = 0;
        for (EntityData entityData : this.entityDataTracker) {
            if (entityData.id() == 0 && entityData.dataType() == EntityDataTypes1_8.BYTE) {
                flags = ((Number)entityData.getValue()).byteValue();
                continue;
            }
            if (entityData.id() == 2 && entityData.dataType() == EntityDataTypes1_8.STRING) {
                this.name = entityData.getValue().toString();
                if (this.name == null || !this.name.isEmpty()) continue;
                this.name = null;
                continue;
            }
            if (entityData.id() != 10 || entityData.dataType() != EntityDataTypes1_8.BYTE) continue;
            armorStandFlags = ((Number)entityData.getValue()).byteValue();
        }
        boolean invisible = (flags & 0x20) != 0;
        this.small = armorStandFlags & true;
        this.marker = (armorStandFlags & 0x10) != 0;
        State prevState = this.currentState;
        this.currentState = invisible && this.name != null ? State.HOLOGRAM : State.ZOMBIE;
        if (this.currentState != prevState) {
            this.deleteEntity();
            this.sendSpawnPacket(entityRewriter);
        } else {
            this.sendEntityDataUpdate(entityRewriter);
            this.updateLocation(false);
        }
    }

    void updateLocation(boolean remount) {
        if (this.entityIds == null) {
            return;
        }
        if (this.currentState == State.ZOMBIE) {
            this.teleportEntity(this.entityId, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            PacketWrapper entityHeadLook = PacketWrapper.create(ClientboundPackets1_7_2_5.ROTATE_HEAD, this.user);
            entityHeadLook.write(Types.INT, this.entityId);
            entityHeadLook.write(Types.BYTE, (byte)(this.headYaw / 360.0f * 256.0f));
            entityHeadLook.send(Protocol1_8To1_7_6_10.class);
        } else if (this.currentState == State.HOLOGRAM) {
            if (remount) {
                PacketWrapper detach = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_ENTITY_LINK, this.user);
                detach.write(Types.INT, this.entityIds[1]);
                detach.write(Types.INT, -1);
                detach.write(Types.BOOLEAN, false);
                detach.send(Protocol1_8To1_7_6_10.class);
            }
            this.teleportEntity(this.entityIds[0], this.locX, this.locY + (this.marker ? 54.85 : (this.small ? 56.0 : 57.0)) - 0.16, this.locZ, 0.0f, 0.0f);
            if (remount) {
                this.teleportEntity(this.entityIds[1], this.locX, this.locY + 56.75, this.locZ, 0.0f, 0.0f);
                PacketWrapper attach = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_ENTITY_LINK, null, this.user);
                attach.write(Types.INT, this.entityIds[1]);
                attach.write(Types.INT, this.entityIds[0]);
                attach.write(Types.BOOLEAN, false);
                attach.send(Protocol1_8To1_7_6_10.class);
            }
        }
    }

    protected void teleportEntity(int entityId, double x, double y, double z, float yaw, float pitch) {
        PacketWrapper entityTeleport = PacketWrapper.create(ClientboundPackets1_7_2_5.TELEPORT_ENTITY, this.user);
        entityTeleport.write(Types.INT, entityId);
        entityTeleport.write(Types.INT, (int)(x * 32.0));
        entityTeleport.write(Types.INT, (int)(y * 32.0));
        entityTeleport.write(Types.INT, (int)(z * 32.0));
        entityTeleport.write(Types.BYTE, (byte)(yaw / 360.0f * 256.0f));
        entityTeleport.write(Types.BYTE, (byte)(pitch / 360.0f * 256.0f));
        entityTeleport.send(Protocol1_8To1_7_6_10.class);
    }

    protected void spawnEntity(int entityId, int type, double locX, double locY, double locZ) {
        PacketWrapper addMob = PacketWrapper.create(ClientboundPackets1_7_2_5.ADD_MOB, this.user);
        addMob.write(Types.VAR_INT, entityId);
        addMob.write(Types.UNSIGNED_BYTE, (short)type);
        addMob.write(Types.INT, (int)(locX * 32.0));
        addMob.write(Types.INT, (int)(locY * 32.0));
        addMob.write(Types.INT, (int)(locZ * 32.0));
        addMob.write(Types.BYTE, (byte)0);
        addMob.write(Types.BYTE, (byte)0);
        addMob.write(Types.BYTE, (byte)0);
        addMob.write(Types.SHORT, (short)0);
        addMob.write(Types.SHORT, (short)0);
        addMob.write(Types.SHORT, (short)0);
        addMob.write(Types1_7_6_10.ENTITY_DATA_LIST, new ArrayList());
        addMob.send(Protocol1_8To1_7_6_10.class);
    }

    public void sendEntityDataUpdate(EntityPacketRewriter1_8 entityRewriter) {
        if (this.entityIds == null) {
            return;
        }
        PacketWrapper setEntityData = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_ENTITY_DATA, this.user);
        if (this.currentState == State.ZOMBIE) {
            this.writeZombieMeta(entityRewriter, setEntityData);
        } else if (this.currentState == State.HOLOGRAM) {
            this.writeHologramMeta(setEntityData);
        } else {
            return;
        }
        setEntityData.send(Protocol1_8To1_7_6_10.class);
    }

    void writeZombieMeta(EntityPacketRewriter1_8 entityRewriter, PacketWrapper wrapper) {
        wrapper.write(Types.INT, this.entityIds[0]);
        ArrayList<EntityData> entityDataList = new ArrayList<EntityData>();
        for (EntityData entityData : this.entityDataTracker) {
            if (entityData.id() < 0 || entityData.id() > 9) continue;
            entityDataList.add(new EntityData(entityData.id(), entityData.dataType(), entityData.getValue()));
        }
        if (this.small) {
            entityDataList.add(new EntityData(12, EntityDataTypes1_8.BYTE, (byte)1));
        }
        for (EntityData entityData : entityDataList.toArray(new EntityData[0])) {
            EntityDataHandlerEventImpl event = new EntityDataHandlerEventImpl(wrapper.user(), new TrackedEntityImpl(EntityTypes1_8.EntityType.ZOMBIE), -1, entityData, entityDataList);
            try {
                entityRewriter.handleEntityData(event, entityData);
            }
            catch (Exception e) {
                entityDataList.remove(entityData);
                break;
            }
            if (!event.cancelled()) continue;
            entityDataList.remove(entityData);
            break;
        }
        wrapper.write(Types1_7_6_10.ENTITY_DATA_LIST, entityDataList);
    }

    void writeHologramMeta(PacketWrapper wrapper) {
        wrapper.write(Types.INT, this.entityIds[1]);
        ArrayList<EntityData> entityDataList = new ArrayList<EntityData>();
        entityDataList.add(new EntityData(EntityDataIndex1_7_6_10.ABSTRACT_AGEABLE_AGE.getIndex(), EntityDataTypes1_7_6_10.INT, -1700000));
        entityDataList.add(new EntityData(EntityDataIndex1_7_6_10.LIVING_ENTITY_BASE_NAME_TAG.getIndex(), EntityDataTypes1_7_6_10.STRING, this.name));
        entityDataList.add(new EntityData(EntityDataIndex1_7_6_10.LIVING_ENTITY_BASE_NAME_TAG_VISIBILITY.getIndex(), EntityDataTypes1_7_6_10.BYTE, (byte)1));
        wrapper.write(Types1_7_6_10.ENTITY_DATA_LIST, entityDataList);
    }

    public void sendSpawnPacket(EntityPacketRewriter1_8 entityRewriter) {
        if (this.entityIds != null) {
            this.deleteEntity();
        }
        if (this.currentState == State.ZOMBIE) {
            this.spawnEntity(this.entityId, EntityTypes1_8.EntityType.ZOMBIE.getId(), this.locX, this.locY, this.locZ);
            this.entityIds = new int[]{this.entityId};
        } else if (this.currentState == State.HOLOGRAM) {
            int[] entityIds = new int[]{this.entityId, this.additionalEntityId()};
            PacketWrapper spawnSkull = PacketWrapper.create(ClientboundPackets1_7_2_5.ADD_ENTITY, this.user);
            spawnSkull.write(Types.VAR_INT, entityIds[0]);
            spawnSkull.write(Types.BYTE, (byte)66);
            spawnSkull.write(Types.INT, (int)(this.locX * 32.0));
            spawnSkull.write(Types.INT, (int)(this.locY * 32.0));
            spawnSkull.write(Types.INT, (int)(this.locZ * 32.0));
            spawnSkull.write(Types.BYTE, (byte)0);
            spawnSkull.write(Types.BYTE, (byte)0);
            spawnSkull.write(Types.INT, 0);
            spawnSkull.send(Protocol1_8To1_7_6_10.class);
            this.spawnEntity(entityIds[1], EntityTypes1_8.EntityType.HORSE.getId(), this.locX, this.locY, this.locZ);
            this.entityIds = entityIds;
        }
        this.sendEntityDataUpdate(entityRewriter);
        this.updateLocation(true);
    }

    public AABB getBoundingBox() {
        double width = this.small ? 0.25 : 0.5;
        double height = this.small ? 0.9875 : 1.975;
        Vector3d min = new Vector3d(this.locX - width / 2.0, this.locY, this.locZ - width / 2.0);
        Vector3d max = new Vector3d(this.locX + width / 2.0, this.locY + height, this.locZ + width / 2.0);
        return new AABB(min, max);
    }

    int additionalEntityId() {
        return 2147467647 - this.entityId;
    }

    public void deleteEntity() {
        if (this.entityIds == null) {
            return;
        }
        PacketWrapper despawn = PacketWrapper.create(ClientboundPackets1_7_2_5.REMOVE_ENTITIES, this.user);
        despawn.write(Types.BYTE, (byte)this.entityIds.length);
        for (int id : this.entityIds) {
            despawn.write(Types.INT, id);
        }
        this.entityIds = null;
        despawn.send(Protocol1_8To1_7_6_10.class);
    }

    private static enum State {
        HOLOGRAM,
        ZOMBIE;

    }
}

