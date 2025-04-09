/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractReference2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractReferenceSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectMaps;
import com.viaversion.viaversion.libs.fastutil.objects.ReferenceSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractReference2ObjectMap<K, V>
extends AbstractReference2ObjectFunction<K, V>
implements Reference2ObjectMap<K, V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractReference2ObjectMap() {
    }

    @Override
    public boolean containsKey(Object k) {
        Iterator i = this.reference2ObjectEntrySet().iterator();
        while (i.hasNext()) {
            if (((Reference2ObjectMap.Entry)i.next()).getKey() != k) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object v) {
        Iterator i = this.reference2ObjectEntrySet().iterator();
        while (i.hasNext()) {
            if (((Reference2ObjectMap.Entry)i.next()).getValue() != v) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ReferenceSet<K> keySet() {
        return new AbstractReferenceSet<K>(){

            @Override
            public boolean contains(Object k) {
                return AbstractReference2ObjectMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractReference2ObjectMap.this.size();
            }

            @Override
            public void clear() {
                AbstractReference2ObjectMap.this.clear();
            }

            @Override
            public ObjectIterator<K> iterator() {
                return new ObjectIterator<K>(){
                    private final ObjectIterator<Reference2ObjectMap.Entry<K, V>> i;
                    {
                        this.i = Reference2ObjectMaps.fastIterator(AbstractReference2ObjectMap.this);
                    }

                    @Override
                    public K next() {
                        return ((Reference2ObjectMap.Entry)this.i.next()).getKey();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }

                    @Override
                    public void remove() {
                        this.i.remove();
                    }

                    @Override
                    public void forEachRemaining(Consumer<? super K> action) {
                        this.i.forEachRemaining((? super E entry) -> action.accept((Object)entry.getKey()));
                    }
                };
            }

            @Override
            public ObjectSpliterator<K> spliterator() {
                return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(AbstractReference2ObjectMap.this), 65);
            }
        };
    }

    @Override
    public ObjectCollection<V> values() {
        return new AbstractObjectCollection<V>(){

            @Override
            public boolean contains(Object k) {
                return AbstractReference2ObjectMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractReference2ObjectMap.this.size();
            }

            @Override
            public void clear() {
                AbstractReference2ObjectMap.this.clear();
            }

            @Override
            public ObjectIterator<V> iterator() {
                return new ObjectIterator<V>(){
                    private final ObjectIterator<Reference2ObjectMap.Entry<K, V>> i;
                    {
                        this.i = Reference2ObjectMaps.fastIterator(AbstractReference2ObjectMap.this);
                    }

                    @Override
                    public V next() {
                        return ((Reference2ObjectMap.Entry)this.i.next()).getValue();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }

                    @Override
                    public void remove() {
                        this.i.remove();
                    }

                    @Override
                    public void forEachRemaining(Consumer<? super V> action) {
                        this.i.forEachRemaining((? super E entry) -> action.accept((Object)entry.getValue()));
                    }
                };
            }

            @Override
            public ObjectSpliterator<V> spliterator() {
                return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(AbstractReference2ObjectMap.this), 64);
            }
        };
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m2) {
        if (m2 instanceof Reference2ObjectMap) {
            ObjectIterator i = Reference2ObjectMaps.fastIterator((Reference2ObjectMap)m2);
            while (i.hasNext()) {
                Reference2ObjectMap.Entry e = (Reference2ObjectMap.Entry)i.next();
                this.put(e.getKey(), e.getValue());
            }
        } else {
            int n = m2.size();
            Iterator<Map.Entry<K, V>> i = m2.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<K, V> e = i.next();
                this.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int h = 0;
        int n = this.size();
        ObjectIterator i = Reference2ObjectMaps.fastIterator(this);
        while (n-- != 0) {
            h += ((Reference2ObjectMap.Entry)i.next()).hashCode();
        }
        return h;
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
        if (m2.size() != this.size()) {
            return false;
        }
        return this.reference2ObjectEntrySet().containsAll(m2.entrySet());
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        ObjectIterator i = Reference2ObjectMaps.fastIterator(this);
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            Reference2ObjectMap.Entry e = (Reference2ObjectMap.Entry)i.next();
            if (this == e.getKey()) {
                s.append("(this map)");
            } else {
                s.append(String.valueOf(e.getKey()));
            }
            s.append("=>");
            if (this == e.getValue()) {
                s.append("(this map)");
                continue;
            }
            s.append(String.valueOf(e.getValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static abstract class BasicEntrySet<K, V>
    extends AbstractObjectSet<Reference2ObjectMap.Entry<K, V>> {
        protected final Reference2ObjectMap<K, V> map;

        public BasicEntrySet(Reference2ObjectMap<K, V> map) {
            this.map = map;
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Reference2ObjectMap.Entry) {
                Reference2ObjectMap.Entry e = (Reference2ObjectMap.Entry)o;
                Object k = e.getKey();
                return this.map.containsKey(k) && Objects.equals(this.map.get(k), e.getValue());
            }
            Map.Entry e = (Map.Entry)o;
            Object k = e.getKey();
            Object value = e.getValue();
            return this.map.containsKey(k) && Objects.equals(this.map.get(k), value);
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Reference2ObjectMap.Entry) {
                Reference2ObjectMap.Entry e = (Reference2ObjectMap.Entry)o;
                return this.map.remove(e.getKey(), e.getValue());
            }
            Map.Entry e = (Map.Entry)o;
            Object k = e.getKey();
            Object v = e.getValue();
            return this.map.remove(k, v);
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public ObjectSpliterator<Reference2ObjectMap.Entry<K, V>> spliterator() {
            return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.map), 65);
        }
    }

    public static class BasicEntry<K, V>
    implements Reference2ObjectMap.Entry<K, V> {
        protected K key;
        protected V value;

        public BasicEntry() {
        }

        public BasicEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Reference2ObjectMap.Entry) {
                Reference2ObjectMap.Entry e = (Reference2ObjectMap.Entry)o;
                return this.key == e.getKey() && Objects.equals(this.value, e.getValue());
            }
            Map.Entry e = (Map.Entry)o;
            Object key = e.getKey();
            Object value = e.getValue();
            return this.key == key && Objects.equals(this.value, value);
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this.key) ^ (this.value == null ? 0 : this.value.hashCode());
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

