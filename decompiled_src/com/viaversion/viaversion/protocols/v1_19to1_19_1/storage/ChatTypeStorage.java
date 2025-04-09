/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_19to1_19_1.storage;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.Protocol1_18_2To1_19;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ChatTypeStorage
implements StorableObject {
    private final Int2ObjectMap<CompoundTag> chatTypes = new Int2ObjectOpenHashMap<CompoundTag>();

    public @Nullable CompoundTag chatType(int id) {
        return this.chatTypes.isEmpty() ? Protocol1_18_2To1_19.MAPPINGS.chatType(id) : (CompoundTag)this.chatTypes.get(id);
    }

    public void addChatType(int id, CompoundTag chatType) {
        this.chatTypes.put(id, chatType);
    }

    public void clear() {
        this.chatTypes.clear();
    }

    @Override
    public boolean clearOnServerSwitch() {
        return false;
    }
}

