/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_19to1_19_1.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.data.MappingDataLoader;

public class ChatRegistry1_19_1 {
    private static final CompoundTag chatRegistry = MappingDataLoader.INSTANCE.loadNBTFromFile("chat-registry-1.19.1.nbt");

    public static CompoundTag chatRegistry() {
        return chatRegistry.copy();
    }
}

