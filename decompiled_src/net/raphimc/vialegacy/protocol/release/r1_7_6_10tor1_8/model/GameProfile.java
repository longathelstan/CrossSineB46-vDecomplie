/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import net.raphimc.vialegacy.api.util.UuidUtil;

public class GameProfile {
    public static final GameProfile NULL = new GameProfile();
    public String userName;
    public UUID uuid;
    public Map<String, List<Property>> properties = new HashMap<String, List<Property>>();
    final UUID offlineUuid;

    GameProfile() {
        this.offlineUuid = new UUID(0L, 0L);
    }

    public GameProfile(String userName) {
        if (userName == null) {
            throw new IllegalStateException("Username can't be null");
        }
        this.userName = userName;
        this.offlineUuid = this.uuid = UuidUtil.createOfflinePlayerUuid(userName);
    }

    public GameProfile(String userName, UUID uuid) {
        if (userName == null || uuid == null) {
            throw new IllegalStateException("Username and UUID can't be null");
        }
        this.userName = userName;
        this.uuid = uuid;
        this.offlineUuid = UuidUtil.createOfflinePlayerUuid(userName);
    }

    public void addProperty(Property property) {
        this.properties.computeIfAbsent(property.key, k -> new ArrayList()).add(property);
    }

    public List<Property> getAllProperties() {
        return this.properties.values().stream().reduce((p1, p2) -> {
            ArrayList merge = new ArrayList();
            merge.addAll(p1);
            merge.addAll(p2);
            return merge;
        }).orElseGet(ArrayList::new);
    }

    public boolean isOffline() {
        return this.offlineUuid.equals(this.uuid);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        GameProfile that = (GameProfile)o;
        return Objects.equals(this.userName, that.userName) && Objects.equals(this.uuid, that.uuid) && Objects.equals(this.properties, that.properties);
    }

    public int hashCode() {
        return Objects.hash(this.userName, this.uuid, this.properties);
    }

    public String toString() {
        UUID uUID = this.uuid;
        String string = this.userName;
        return "GameProfile{userName='" + string + "', uuid=" + uUID + "}";
    }

    public static class Property {
        public String key;
        public String value;
        public String signature;

        public Property(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public Property(String key, String value, String signature) {
            this(key, value);
            this.signature = signature;
        }
    }
}

