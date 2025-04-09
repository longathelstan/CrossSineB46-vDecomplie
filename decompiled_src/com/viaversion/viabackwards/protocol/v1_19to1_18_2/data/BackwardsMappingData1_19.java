/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_19to1_18_2.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.data.BackwardsMappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.Protocol1_18_2To1_19;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BackwardsMappingData1_19
extends BackwardsMappingData {
    private final boolean sculkShriekerToCryingObsidian = ViaBackwards.getConfig().sculkShriekerToCryingObsidian();
    private final Int2ObjectMap<CompoundTag> defaultChatTypes = new Int2ObjectOpenHashMap<CompoundTag>();

    public BackwardsMappingData1_19() {
        super("1.19", "1.18", Protocol1_18_2To1_19.class);
    }

    @Override
    protected void loadExtras(CompoundTag data) {
        super.loadExtras(data);
        if (this.sculkShriekerToCryingObsidian) {
            this.blockMappings.setNewId(850, 750);
            for (int i = 18900; i <= 18907; ++i) {
                this.blockStateMappings.setNewId(i, 16082);
            }
        }
        ListTag<CompoundTag> chatTypes = BackwardsMappingDataLoader.INSTANCE.loadNBT("chat-types-1.19.1.nbt").getListTag("values", CompoundTag.class);
        for (CompoundTag chatType : chatTypes) {
            NumberTag idTag = chatType.getNumberTag("id");
            this.defaultChatTypes.put(idTag.asInt(), chatType);
        }
    }

    @Override
    public int getNewItemId(int id) {
        if (this.sculkShriekerToCryingObsidian && id == 329) {
            return 1065;
        }
        return super.getNewItemId(id);
    }

    public @Nullable CompoundTag chatType(int id) {
        return (CompoundTag)this.defaultChatTypes.get(id);
    }
}

