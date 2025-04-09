/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_19_3to1_19_1.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.data.BackwardsMappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.Protocol1_19_1To1_19_3;
import com.viaversion.viaversion.util.Key;

public final class BackwardsMappingData1_19_3
extends BackwardsMappingData {
    private final Object2IntMap<String> mappedSounds = new Object2IntOpenHashMap<String>();

    public BackwardsMappingData1_19_3() {
        super("1.19.3", "1.19", Protocol1_19_1To1_19_3.class);
        this.mappedSounds.defaultReturnValue(-1);
    }

    @Override
    protected void loadExtras(CompoundTag data) {
        super.loadExtras(data);
        JsonArray sounds = BackwardsMappingDataLoader.INSTANCE.loadData("sounds-1.19.json").getAsJsonArray("sounds");
        int i = 0;
        for (JsonElement sound : sounds) {
            this.mappedSounds.put(sound.getAsString(), i++);
        }
    }

    public int mappedSound(String sound) {
        return this.mappedSounds.getInt(Key.stripMinecraftNamespace(sound));
    }
}

