/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_9to1_8.storage;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_9;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;
import java.util.List;
import java.util.Map;

public class EntityTracker1_9
extends EntityTrackerBase {
    private final Int2ObjectMap<IntList> vehicles = new Int2ObjectOpenHashMap<IntList>();
    private final Int2ObjectMap<Vector> offsets = new Int2ObjectOpenHashMap<Vector>();
    private final Int2IntMap status = new Int2IntOpenHashMap();

    public EntityTracker1_9(UserConnection connection) {
        super(connection, EntityTypes1_9.EntityType.PLAYER);
    }

    @Override
    public void removeEntity(int id) {
        this.vehicles.remove(id);
        this.offsets.remove(id);
        this.status.remove(id);
        this.vehicles.forEach((vehicle, passengers) -> passengers.rem(id));
        this.vehicles.int2ObjectEntrySet().removeIf(entry -> ((IntList)entry.getValue()).isEmpty());
        super.removeEntity(id);
    }

    public void resetEntityOffset(int id) {
        this.offsets.remove(id);
    }

    public Vector getEntityOffset(int id) {
        return (Vector)this.offsets.get(id);
    }

    public void setEntityOffset(int id, Vector offset) {
        this.offsets.put(id, offset);
    }

    public IntList getPassengers(int id) {
        return this.vehicles.getOrDefault(id, (IntList)new IntArrayList());
    }

    public void setPassengers(int id, IntList passengers) {
        this.vehicles.put(id, passengers);
    }

    public boolean isInsideVehicle(int id) {
        for (List vehicle : this.vehicles.values()) {
            if (!vehicle.contains(id)) continue;
            return true;
        }
        return false;
    }

    public int getVehicle(int passenger) {
        for (Map.Entry entry : this.vehicles.int2ObjectEntrySet()) {
            if (!((IntList)entry.getValue()).contains(passenger)) continue;
            return (Integer)entry.getKey();
        }
        return -1;
    }

    public Int2IntMap getStatus() {
        return this.status;
    }
}

