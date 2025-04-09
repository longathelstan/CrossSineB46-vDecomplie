/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityTracker
implements StorableObject {
    private final Map<Integer, EntityTypes1_8.EntityType> entityMap = new ConcurrentHashMap<Integer, EntityTypes1_8.EntityType>();
    private int playerID;

    public int getPlayerID() {
        return this.playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void removeEntity(int entityId) {
        this.entityMap.remove(entityId);
    }

    public Map<Integer, EntityTypes1_8.EntityType> getTrackedEntities() {
        return this.entityMap;
    }
}

