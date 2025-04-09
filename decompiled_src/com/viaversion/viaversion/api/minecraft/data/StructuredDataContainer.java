/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.data;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.minecraft.data.StructuredData;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectOpenHashMap;
import com.viaversion.viaversion.util.Unit;
import java.util.Map;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class StructuredDataContainer {
    private final Map<StructuredDataKey<?>, StructuredData<?>> data;
    private FullMappings lookup;
    private boolean mappedNames;

    public StructuredDataContainer(Map<StructuredDataKey<?>, StructuredData<?>> data) {
        this.data = data;
    }

    public StructuredDataContainer(StructuredData<?>[] dataArray) {
        this(new Reference2ObjectOpenHashMap(dataArray.length));
        for (StructuredData<?> data : dataArray) {
            this.data.put(data.key(), data);
        }
    }

    public StructuredDataContainer() {
        this(new Reference2ObjectOpenHashMap());
    }

    public <T> @Nullable T get(StructuredDataKey<T> key) {
        StructuredData<?> data = this.data.get(key);
        if (data == null || data.isEmpty()) {
            return null;
        }
        return (T)data.value();
    }

    public <T> @Nullable StructuredData<T> getData(StructuredDataKey<T> key) {
        return this.data.get(key);
    }

    public <T> @Nullable StructuredData<T> getNonEmptyData(StructuredDataKey<T> key) {
        StructuredData<?> data = this.data.get(key);
        return data != null && data.isPresent() ? data : null;
    }

    public <T> void set(StructuredDataKey<T> key, T value) {
        int id = this.serializerId(key);
        if (id != -1) {
            this.data.put(key, StructuredData.of(key, value, id));
        }
    }

    public void set(StructuredDataKey<Unit> key) {
        this.set(key, Unit.INSTANCE);
    }

    public void setEmpty(StructuredDataKey<?> key) {
        this.data.put(key, StructuredData.empty(key, this.serializerId(key)));
    }

    public <T> void replace(StructuredDataKey<T> key, Function<T, @Nullable T> valueMapper) {
        StructuredData<T> data = this.getNonEmptyData(key);
        if (data == null) {
            return;
        }
        T replacement = valueMapper.apply(data.value());
        if (replacement != null) {
            data.setValue(replacement);
        } else {
            this.data.remove(key);
        }
    }

    public <T> void replaceKey(StructuredDataKey<T> key, StructuredDataKey<T> toKey) {
        this.replace(key, toKey, Function.identity());
    }

    public <T, V> void replace(StructuredDataKey<T> key, StructuredDataKey<V> toKey, Function<T, @Nullable V> valueMapper) {
        StructuredData<?> data = this.data.remove(key);
        if (data == null) {
            return;
        }
        if (data.isPresent()) {
            Object value = data.value();
            V replacement = valueMapper.apply(value);
            if (replacement != null) {
                this.set(toKey, replacement);
            }
        } else {
            this.setEmpty(toKey);
        }
    }

    public void remove(StructuredDataKey<?> key) {
        this.data.remove(key);
    }

    public boolean has(StructuredDataKey<?> key) {
        return this.data.containsKey(key);
    }

    public boolean hasValue(StructuredDataKey<?> key) {
        StructuredData<?> data = this.data.get(key);
        return data != null && data.isPresent();
    }

    public boolean hasEmpty(StructuredDataKey<?> key) {
        StructuredData<?> data = this.data.get(key);
        return data != null && data.isEmpty();
    }

    public void setIdLookup(Protocol<?, ?, ?, ?> protocol, boolean mappedNames) {
        this.lookup = protocol.getMappingData().getDataComponentSerializerMappings();
        Preconditions.checkNotNull((Object)this.lookup, (Object)"Data component serializer mappings are null");
        this.mappedNames = mappedNames;
    }

    public void updateIds(Protocol<?, ?, ?, ?> protocol, Int2IntFunction rewriter) {
        for (StructuredData<?> data : this.data.values()) {
            int mappedId = rewriter.applyAsInt(data.id());
            if (mappedId == -1) continue;
            data.setId(mappedId);
        }
    }

    public StructuredDataContainer copy() {
        StructuredDataContainer copy = new StructuredDataContainer(new Reference2ObjectOpenHashMap(this.data));
        copy.lookup = this.lookup;
        return copy;
    }

    private int serializerId(StructuredDataKey<?> key) {
        int id;
        int n = id = this.mappedNames ? this.lookup.mappedId(key.identifier()) : this.lookup.id(key.identifier());
        if (id == -1) {
            StructuredDataKey<?> structuredDataKey = key;
            Via.getPlatform().getLogger().severe("Could not find item data serializer for type " + structuredDataKey);
        }
        return id;
    }

    public Map<StructuredDataKey<?>, StructuredData<?>> data() {
        return this.data;
    }

    public String toString() {
        boolean bl = this.mappedNames;
        FullMappings fullMappings = this.lookup;
        Map<StructuredDataKey<?>, StructuredData<?>> map = this.data;
        return "StructuredDataContainer{data=" + map + ", lookup=" + fullMappings + ", mappedNames=" + bl + "}";
    }
}

