/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_19to1_18_2.storage;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.protocol.v1_19to1_18_2.Protocol1_19To1_18_2;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.util.Key;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class DimensionRegistryStorage
implements StorableObject {
    private final Map<String, CompoundTag> dimensions = new HashMap<String, CompoundTag>();
    private final Int2ObjectMap<CompoundTag> chatTypes = new Int2ObjectOpenHashMap<CompoundTag>();

    public @Nullable CompoundTag dimension(String dimensionKey) {
        CompoundTag compoundTag = this.dimensions.get(Key.stripMinecraftNamespace(dimensionKey));
        return compoundTag != null ? compoundTag.copy() : null;
    }

    public void addDimension(String dimensionKey, CompoundTag dimension) {
        this.dimensions.put(dimensionKey, dimension);
    }

    public @Nullable CompoundTag chatType(int id) {
        return this.chatTypes.isEmpty() ? Protocol1_19To1_18_2.MAPPINGS.chatType(id) : (CompoundTag)this.chatTypes.get(id);
    }

    public void addChatType(int id, CompoundTag chatType) {
        this.chatTypes.put(id, chatType);
    }

    public void clear() {
        this.dimensions.clear();
        this.chatTypes.clear();
    }

    @Override
    public boolean clearOnServerSwitch() {
        return false;
    }
}

