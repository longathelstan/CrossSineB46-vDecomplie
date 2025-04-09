/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractReference2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSets;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectFunctions;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectMaps;
import com.viaversion.viaversion.libs.fastutil.objects.ReferenceSet;
import com.viaversion.viaversion.libs.fastutil.objects.ReferenceSets;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class Reference2ObjectMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Reference2ObjectMaps() {
    }

    public static <K, V> ObjectIterator<Reference2ObjectMap.Entry<K, V>> fastIterator(Reference2ObjectMap<K, V> map) {
        ObjectSet<Reference2ObjectMap.Entry<K, V>> entries = map.reference2ObjectEntrySet();
        return entries instanceof Reference2ObjectMap.FastEntrySet ? ((Reference2ObjectMap.FastEntrySet)entries).fastIterator() : entries.iterator();
    }

    public static <K, V> void fastForEach(Reference2ObjectMap<K, V> map, Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer) {
        ObjectSet<Reference2ObjectMap.Entry<K, V>> entries = map.reference2ObjectEntrySet();
        if (entries instanceof Reference2ObjectMap.FastEntrySet) {
            ((Reference2ObjectMap.FastEntrySet)entries).fastForEach(consumer);
        } else {
            entries.forEach(consumer);
        }
    }

    public static <K, V> ObjectIterable<Reference2ObjectMap.Entry<K, V>> fastIterable(Reference2ObjectMap<K, V> map) {
        final ObjectSet<Reference2ObjectMap.Entry<K, V>> entries = map.reference2ObjectEntrySet();
        return entries instanceof Reference2ObjectMap.FastEntrySet ? new ObjectIterable<Reference2ObjectMap.Entry<K, V>>(){

            @Override
            public ObjectIterator<Reference2ObjectMap.Entry<K, V>> iterator() {
                return ((Reference2ObjectMap.FastEntrySet)entries).fastIterator();
            }

            @Override
            public ObjectSpliterator<Reference2ObjectMap.Entry<K, V>> spliterator() {
                return entries.spliterator();
            }

            @Override
            public void forEach(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer) {
                ((Reference2ObjectMap.FastEntrySet)entries).fastForEach(consumer);
            }
        } : entries;
    }

    public static <K, V> Reference2ObjectMap<K, V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K, V> Reference2ObjectMap<K, V> singleton(K key, V value) {
        return new Singleton<K, V>(key, value);
    }

    public static <K, V> Reference2ObjectMap<K, V> synchronize(Reference2ObjectMap<K, V> m2) {
        return new SynchronizedMap(m2);
    }

    public static <K, V> Reference2ObjectMap<K, V> synchronize(Reference2ObjectMap<K, V> m2, Object sync) {
        return new SynchronizedMap(m2, sync);
    }

    public static <K, V> Reference2ObjectMap<K, V> unmodifiable(Reference2ObjectMap<? extends K, ? extends V> m2) {
        return new UnmodifiableMap(m2);
    }

    public static class EmptyMap<K, V>
    extends Reference2ObjectFunctions.EmptyFunction<K, V>
    implements Reference2ObjectMap<K, V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(Object v) {
            return false;
        }

        @Override
        public V getOrDefault(Object key, V defaultValue) {
            return defaultValue;
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ReferenceSet<K> keySet() {
            return ReferenceSets.EMPTY_SET;
        }

        @Override
        public ObjectCollection<V> values() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V> consumer) {
        }

        @Override
        public Object clone() {
            return EMPTY_MAP;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map)) {
                return false;
            }
            return ((Map)o).isEmpty();
        }

        @Override
        public String toString() {
            return "{}";
        }
    }

    public static class Singleton<K, V>
    extends Reference2ObjectFunctions.Singleton<K, V>
    implements Reference2ObjectMap<K, V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Reference2ObjectMap.Entry<K, V>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient ObjectCollection<V> values;

        protected Singleton(K key, V value) {
            super(key, value);
        }

        @Override
        public boolean containsValue(Object v) {
            return Objects.equals(this.value, v);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractReference2ObjectMap.BasicEntry<Object, Object>(this.key, this.value));
            }
            return this.entries;
        }

        @Override
        public ObjectSet<Map.Entry<K, V>> entrySet() {
            return this.reference2ObjectEntrySet();
        }

        @Override
        public ReferenceSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public ObjectCollection<V> values() {
            if (this.values == null) {
                this.values = ObjectSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this.key) ^ (this.value == null ? 0 : this.value.hashCode());
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Map)) {
                return false;
            }
            Map m2 = (Map)o;
            if (m2.size() != 1) {
                return false;
            }
            return m2.entrySet().iterator().next().equals(this.entrySet().iterator().next());
        }

        public String toString() {
            return "{" + this.key + "=>" + this.value + "}";
        }
    }
}

