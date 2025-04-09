/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractReference2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterable;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSets;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectMaps;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectSortedMap;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectSortedMaps;
import com.viaversion.viaversion.libs.fastutil.objects.ReferenceSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ReferenceSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

public final class Reference2ObjectSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Reference2ObjectSortedMaps() {
    }

    public static <K> Comparator<? super Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
        return (x, y) -> comparator.compare((Object)x.getKey(), (Object)y.getKey());
    }

    public static <K, V> ObjectBidirectionalIterator<Reference2ObjectMap.Entry<K, V>> fastIterator(Reference2ObjectSortedMap<K, V> map) {
        ObjectSet entries = map.reference2ObjectEntrySet();
        return entries instanceof Reference2ObjectSortedMap.FastSortedEntrySet ? ((Reference2ObjectSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
    }

    public static <K, V> ObjectBidirectionalIterable<Reference2ObjectMap.Entry<K, V>> fastIterable(Reference2ObjectSortedMap<K, V> map) {
        ObjectSet entries = map.reference2ObjectEntrySet();
        return entries instanceof Reference2ObjectSortedMap.FastSortedEntrySet ? ((Reference2ObjectSortedMap.FastSortedEntrySet)entries)::fastIterator : entries;
    }

    public static <K, V> Reference2ObjectSortedMap<K, V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K, V> Reference2ObjectSortedMap<K, V> singleton(K key, V value) {
        return new Singleton<K, V>(key, value);
    }

    public static <K, V> Reference2ObjectSortedMap<K, V> singleton(K key, V value, Comparator<? super K> comparator) {
        return new Singleton<K, V>(key, value, comparator);
    }

    public static <K, V> Reference2ObjectSortedMap<K, V> synchronize(Reference2ObjectSortedMap<K, V> m2) {
        return new SynchronizedSortedMap(m2);
    }

    public static <K, V> Reference2ObjectSortedMap<K, V> synchronize(Reference2ObjectSortedMap<K, V> m2, Object sync) {
        return new SynchronizedSortedMap(m2, sync);
    }

    public static <K, V> Reference2ObjectSortedMap<K, V> unmodifiable(Reference2ObjectSortedMap<K, ? extends V> m2) {
        return new UnmodifiableSortedMap(m2);
    }

    public static class EmptySortedMap<K, V>
    extends Reference2ObjectMaps.EmptyMap<K, V>
    implements Reference2ObjectSortedMap<K, V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySortedMap() {
        }

        @Override
        public Comparator<? super K> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            return ReferenceSortedSets.EMPTY_SET;
        }

        @Override
        public Reference2ObjectSortedMap<K, V> subMap(K from, K to) {
            return EMPTY_MAP;
        }

        @Override
        public Reference2ObjectSortedMap<K, V> headMap(K to) {
            return EMPTY_MAP;
        }

        @Override
        public Reference2ObjectSortedMap<K, V> tailMap(K from) {
            return EMPTY_MAP;
        }

        @Override
        public K firstKey() {
            throw new NoSuchElementException();
        }

        @Override
        public K lastKey() {
            throw new NoSuchElementException();
        }
    }

    public static class Singleton<K, V>
    extends Reference2ObjectMaps.Singleton<K, V>
    implements Reference2ObjectSortedMap<K, V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Comparator<? super K> comparator;

        protected Singleton(K key, V value, Comparator<? super K> comparator) {
            super(key, value);
            this.comparator = comparator;
        }

        protected Singleton(K key, V value) {
            this(key, value, null);
        }

        final int compare(K k1, K k2) {
            return this.comparator == null ? ((Comparable)k1).compareTo(k2) : this.comparator.compare(k1, k2);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractReference2ObjectMap.BasicEntry<Object, Object>(this.key, this.value), Reference2ObjectSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
            return this.reference2ObjectEntrySet();
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSortedSets.singleton(this.key, this.comparator);
            }
            return (ReferenceSortedSet)this.keys;
        }

        @Override
        public Reference2ObjectSortedMap<K, V> subMap(K from, K to) {
            if (this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Reference2ObjectSortedMap<K, V> headMap(K to) {
            if (this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Reference2ObjectSortedMap<K, V> tailMap(K from) {
            if (this.compare(from, this.key) <= 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public K firstKey() {
            return (K)this.key;
        }

        @Override
        public K lastKey() {
            return (K)this.key;
        }
    }
}

