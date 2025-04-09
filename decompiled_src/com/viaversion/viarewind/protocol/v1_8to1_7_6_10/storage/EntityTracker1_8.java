/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet.ServerboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.Protocol1_8To1_7_6_10;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.data.VirtualHologramEntity;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.PlayerSessionStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntArrayMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

public class EntityTracker1_8
extends EntityTrackerBase {
    private final Int2ObjectMap<VirtualHologramEntity> holograms = new Int2ObjectArrayMap<VirtualHologramEntity>();
    private final Int2IntMap vehicles = new Int2IntArrayMap();
    private final Int2ObjectMap<UUID> entityIdToUUID = new Int2ObjectArrayMap<UUID>();
    private final Object2IntMap<UUID> entityUUIDToId = new Object2IntOpenHashMap<UUID>();
    private final List<EntityData> entityData = new ArrayList<EntityData>();
    public Integer spectatingClientEntityId;
    private int clientEntityGameMode;

    public EntityTracker1_8(UserConnection connection) {
        super(connection, EntityTypes1_8.EntityType.PLAYER);
    }

    @Override
    public void addEntity(int id, EntityType type) {
        super.addEntity(id, type);
        if (type == EntityTypes1_8.EntityType.ARMOR_STAND) {
            this.holograms.put(id, new VirtualHologramEntity(this.user(), id));
        }
    }

    @Override
    public void removeEntity(int entityId) {
        super.removeEntity(entityId);
        if (this.entityIdToUUID.containsKey(entityId)) {
            UUID playerId = (UUID)this.entityIdToUUID.remove(entityId);
            this.entityUUIDToId.removeInt(playerId);
            this.user().get(PlayerSessionStorage.class).getPlayerEquipment().remove(playerId);
        }
    }

    @Override
    public void clearEntities() {
        super.clearEntities();
        this.vehicles.clear();
    }

    @Override
    public void setClientEntityId(int entityId) {
        if (Objects.equals(this.spectatingClientEntityId, this.clientEntityIdOrNull())) {
            this.spectatingClientEntityId = entityId;
        }
        super.setClientEntityId(entityId);
    }

    public Integer clientEntityIdOrNull() {
        return this.hasClientEntityId() ? Integer.valueOf(this.clientEntityId()) : null;
    }

    public void addPlayer(int entityId, UUID uuid) {
        this.entityUUIDToId.put(uuid, entityId);
        this.entityIdToUUID.put(entityId, uuid);
    }

    public UUID getPlayerUUID(int entityId) {
        return (UUID)this.entityIdToUUID.get(entityId);
    }

    public int getPlayerEntityId(UUID uuid) {
        return this.entityUUIDToId.getOrDefault((Object)uuid, -1);
    }

    public int getVehicle(int passengerId) {
        for (Map.Entry vehicle : this.vehicles.entrySet()) {
            if ((Integer)vehicle.getValue() != passengerId) continue;
            return (Integer)vehicle.getValue();
        }
        return -1;
    }

    public int getPassenger(int vehicleId) {
        return this.vehicles.getOrDefault(vehicleId, -1);
    }

    protected void startSneaking() {
        try {
            PacketWrapper entityAction = PacketWrapper.create(ServerboundPackets1_7_2_5.PLAYER_COMMAND, this.user());
            entityAction.write(Types.VAR_INT, this.clientEntityId());
            entityAction.write(Types.VAR_INT, 0);
            entityAction.write(Types.VAR_INT, 0);
            entityAction.sendToServer(Protocol1_8To1_7_6_10.class);
        }
        catch (Exception e) {
            ViaRewind.getPlatform().getLogger().log(Level.SEVERE, "Failed to send sneak packet", e);
        }
    }

    public void setPassenger(int vehicleId, int passengerId) {
        if (vehicleId == this.spectatingClientEntityId && this.spectatingClientEntityId.intValue() != this.clientEntityId()) {
            this.startSneaking();
            this.setSpectating(this.clientEntityId());
        }
        if (vehicleId == -1) {
            this.vehicles.remove(this.getVehicle(passengerId));
        } else if (passengerId == -1) {
            this.vehicles.remove(vehicleId);
        } else {
            this.vehicles.put(vehicleId, passengerId);
        }
    }

    protected void attachEntity(int target) {
        try {
            PacketWrapper attachEntity = PacketWrapper.create(ClientboundPackets1_8.SET_ENTITY_LINK, this.user());
            attachEntity.write(Types.INT, this.clientEntityId());
            attachEntity.write(Types.INT, target);
            attachEntity.write(Types.BOOLEAN, false);
            attachEntity.scheduleSend(Protocol1_8To1_7_6_10.class);
        }
        catch (Exception e) {
            ViaRewind.getPlatform().getLogger().log(Level.SEVERE, "Failed to send attach packet", e);
        }
    }

    public void setSpectating(int spectating) {
        if (spectating != this.clientEntityId() && this.getPassenger(spectating) != -1) {
            this.startSneaking();
            this.setSpectating(this.clientEntityId());
            return;
        }
        if (this.spectatingClientEntityId != spectating && this.spectatingClientEntityId.intValue() != this.clientEntityId()) {
            this.attachEntity(-1);
        }
        this.spectatingClientEntityId = spectating;
        if (spectating != this.clientEntityId()) {
            this.attachEntity(this.spectatingClientEntityId);
        }
    }

    public Int2ObjectMap<VirtualHologramEntity> getHolograms() {
        return this.holograms;
    }

    public boolean isSpectator() {
        return this.clientEntityGameMode == 3;
    }

    public void setClientEntityGameMode(int clientEntityGameMode) {
        this.clientEntityGameMode = clientEntityGameMode;
    }

    public void updateEntityData(List<EntityData> entityData) {
        this.entityData.removeIf(first -> entityData.stream().anyMatch(second -> first.id() == second.id()));
        for (EntityData data : entityData) {
            Object value = data.value();
            if (value instanceof Item) {
                Item item = (Item)value;
                this.entityData.add(new EntityData(data.id(), data.dataType(), item.copy()));
                continue;
            }
            this.entityData.add(new EntityData(data.id(), data.dataType(), value));
        }
    }

    public List<EntityData> getEntityData() {
        return this.entityData;
    }
}

