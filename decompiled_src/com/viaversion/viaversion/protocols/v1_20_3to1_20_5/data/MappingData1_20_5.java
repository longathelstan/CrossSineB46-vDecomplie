/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.util.KeyMappings;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappingData1_20_5
extends MappingDataBase {
    private final Object2ObjectMap<String, CompoundTag> damageTypes = new Object2ObjectOpenHashMap<String, CompoundTag>();
    private KeyMappings blocks;
    private KeyMappings sounds;

    public MappingData1_20_5() {
        super("1.20.3", "1.20.5");
    }

    @Override
    protected void loadExtras(CompoundTag data) {
        super.loadExtras(data);
        CompoundTag extraMappings = MappingDataLoader.INSTANCE.loadNBT("extra-identifiers-1.20.3.nbt");
        this.blocks = new KeyMappings(extraMappings.getListTag("blocks", StringTag.class));
        this.sounds = new KeyMappings(extraMappings.getListTag("sounds", StringTag.class));
        CompoundTag damageTypes = MappingDataLoader.INSTANCE.loadNBT("damage-types-1.20.3.nbt");
        for (String key : damageTypes.keySet()) {
            this.damageTypes.put(key, damageTypes.getCompoundTag(key));
        }
    }

    public int blockId(String name) {
        return this.blocks.keyToId(name);
    }

    public @Nullable String blockName(int id) {
        return this.blocks.idToKey(id);
    }

    public int soundId(String name) {
        return this.sounds.keyToId(name);
    }

    public @Nullable String soundName(int id) {
        return this.sounds.idToKey(id);
    }

    public CompoundTag damageType(String key) {
        return ((CompoundTag)this.damageTypes.get(key)).copy();
    }

    public ObjectSet<String> damageKeys() {
        return this.damageTypes.keySet();
    }
}

