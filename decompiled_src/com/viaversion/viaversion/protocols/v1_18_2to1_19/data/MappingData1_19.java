/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_18_2to1_19.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class MappingData1_19
extends MappingDataBase {
    private final Int2ObjectMap<CompoundTag> defaultChatTypes = new Int2ObjectOpenHashMap<CompoundTag>();
    private CompoundTag chatRegistry;

    public MappingData1_19() {
        super("1.18", "1.19");
    }

    @Override
    protected void loadExtras(CompoundTag daata) {
        ListTag<CompoundTag> chatTypes = MappingDataLoader.INSTANCE.loadNBTFromFile("chat-types-1.19.nbt").getListTag("values", CompoundTag.class);
        for (CompoundTag chatType : chatTypes) {
            NumberTag idTag = chatType.getNumberTag("id");
            this.defaultChatTypes.put(idTag.asInt(), chatType);
        }
        this.chatRegistry = MappingDataLoader.INSTANCE.loadNBTFromFile("chat-registry-1.19.nbt");
    }

    public @Nullable CompoundTag chatType(int id) {
        return (CompoundTag)this.defaultChatTypes.get(id);
    }

    public CompoundTag chatRegistry() {
        return this.chatRegistry.copy();
    }
}

