/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_9to1_8.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viarewind.api.data.RewindMappingData;
import com.viaversion.viarewind.api.data.RewindMappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectList;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;

public final class RewindMappingData1_8
extends RewindMappingData {
    private final ObjectList<String> sounds = new ObjectArrayList<String>();

    public RewindMappingData1_8() {
        super("1.9.4", "1.8");
    }

    @Override
    protected void loadExtras(CompoundTag data) {
        super.loadExtras(data);
        JsonArray sounds = RewindMappingDataLoader.INSTANCE.loadData("sounds-1.9.json").getAsJsonArray("sounds");
        for (JsonElement sound : sounds) {
            this.sounds.add(sound.getAsString());
        }
    }

    public String soundName(int soundId) {
        return (String)this.sounds.get(soundId);
    }
}

