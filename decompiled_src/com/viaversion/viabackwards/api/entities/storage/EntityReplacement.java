/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.entities.storage.WrappedEntityData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.util.ComponentUtil;
import java.util.Locale;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EntityReplacement {
    final BackwardsProtocol<?, ?, ?, ?> protocol;
    final int id;
    final int replacementId;
    final String key;
    ComponentType componentType = ComponentType.NONE;
    EntityDataCreator defaultData;

    public EntityReplacement(BackwardsProtocol<?, ?, ?, ?> protocol, EntityType type, int replacementId) {
        this(protocol, type.name(), type.getId(), replacementId);
    }

    public EntityReplacement(BackwardsProtocol<?, ?, ?, ?> protocol, String key, int id, int replacementId) {
        this.protocol = protocol;
        this.id = id;
        this.replacementId = replacementId;
        this.key = key.toLowerCase(Locale.ROOT);
    }

    public EntityReplacement jsonName() {
        this.componentType = ComponentType.JSON;
        return this;
    }

    public EntityReplacement tagName() {
        this.componentType = ComponentType.TAG;
        return this;
    }

    public EntityReplacement plainName() {
        this.componentType = ComponentType.PLAIN;
        return this;
    }

    public EntityReplacement spawnEntityData(EntityDataCreator handler) {
        this.defaultData = handler;
        return this;
    }

    public boolean hasBaseData() {
        return this.defaultData != null;
    }

    public int typeId() {
        return this.id;
    }

    public @Nullable Object entityName() {
        if (this.componentType == ComponentType.NONE) {
            return null;
        }
        String name = this.protocol.getMappingData().mappedEntityName(this.key);
        if (name == null) {
            return null;
        }
        if (this.componentType == ComponentType.JSON) {
            return ComponentUtil.legacyToJson(name);
        }
        if (this.componentType == ComponentType.TAG) {
            return new StringTag(name);
        }
        return name;
    }

    public int replacementId() {
        return this.replacementId;
    }

    public @Nullable EntityDataCreator defaultData() {
        return this.defaultData;
    }

    public boolean isObjectType() {
        return false;
    }

    public int objectData() {
        return -1;
    }

    public String toString() {
        EntityDataCreator entityDataCreator = this.defaultData;
        ComponentType componentType = this.componentType;
        String string = this.key;
        int n = this.replacementId;
        int n2 = this.id;
        BackwardsProtocol<?, ?, ?, ?> backwardsProtocol = this.protocol;
        return "EntityReplacement{protocol=" + backwardsProtocol + ", id=" + n2 + ", replacementId=" + n + ", key='" + string + "', componentType=" + (Object)((Object)componentType) + ", defaultData=" + entityDataCreator + "}";
    }

    private static enum ComponentType {
        PLAIN,
        JSON,
        TAG,
        NONE;

    }

    @FunctionalInterface
    public static interface EntityDataCreator {
        public void createData(WrappedEntityData var1);
    }
}

