/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntArrayMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIntPair;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class Object2IntArrayMap<K>
extends AbstractObject2IntMap<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    protected transient Object[] key;
    protected transient int[] value;
    protected int size;
    protected transient Object2IntMap.FastEntrySet<K> entries;
    protected transient ObjectSet<K> keys;
    protected transient IntCollection values;

    public Object2IntArrayMap(Object[] key, int[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }

    public Object2IntArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }

    public Object2IntArrayMap(int capacity) {
        this.key = new Object[capacity];
        this.value = new int[capacity];
    }

    public Object2IntArrayMap(Object2IntMap<K> m2) {
        this(m2.size());
        int i = 0;
        for (Object2IntMap.Entry entry : m2.object2IntEntrySet()) {
            this.key[i] = entry.getKey();
            this.value[i] = entry.getIntValue();
            ++i;
        }
        this.size = i;
    }

    public Object2IntArrayMap(Map<? extends K, ? extends Integer> m2) {
        this(m2.size());
        int i = 0;
        for (Map.Entry<K, Integer> e : m2.entrySet()) {
            this.key[i] = e.getKey();
            this.value[i] = e.getValue();
            ++i;
        }
        this.size = i;
    }

    public Object2IntArrayMap(Object[] key, int[] value, int size) {
        this.key = key;
        this.value = value;
        this.size = size;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
        if (size > key.length) {
            throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
        }
    }

    public Object2IntMap.FastEntrySet<K> object2IntEntrySet() {
        if (this.entries == null) {
            this.entries = new EntrySet();
        }
        return this.entries;
    }

    private int findKey(Object k) {
        Object[] key = this.key;
        int i = this.size;
        while (i-- != 0) {
            if (!Objects.equals(key[i], k)) continue;
            return i;
        }
        return -1;
    }

    @Override
    public int getInt(Object k) {
        Object[] key = this.key;
        int i = this.size;
        while (i-- != 0) {
            if (!Objects.equals(key[i], k)) continue;
            return this.value[i];
        }
        return this.defRetValue;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        Object[] key = this.key;
        int i = this.size;
        while (i-- != 0) {
            key[i] = null;
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(Object k) {
        return this.findKey(k) != -1;
    }

    @Override
    public boolean containsValue(int v) {
        int[] value = this.value;
        int i = this.size;
        while (i-- != 0) {
            if (value[i] != v) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int put(K k, int v) {
        int oldKey = this.findKey(k);
        if (oldKey != -1) {
            int oldValue = this.value[oldKey];
            this.value[oldKey] = v;
            return oldValue;
        }
        if (this.size == this.key.length) {
            Object[] newKey = new Object[this.size == 0 ? 2 : this.size * 2];
            int[] newValue = new int[this.size == 0 ? 2 : this.size * 2];
            int i = this.size;
            while (i-- != 0) {
                newKey[i] = this.key[i];
                newValue[i] = this.value[i];
            }
            this.key = newKey;
            this.value = newValue;
        }
        this.key[this.size] = k;
        this.value[this.size] = v;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public int removeInt(Object k) {
        int oldPos = this.findKey(k);
        if (oldPos == -1) {
            return this.defRetValue;
        }
        int oldValue = this.value[oldPos];
        int tail = this.size - oldPos - 1;
        System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
        System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
        --this.size;
        this.key[this.size] = null;
        return oldValue;
    }

    @Override
    public ObjectSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    @Override
    public IntCollection values() {
        if (this.values == null) {
            this.values = new ValuesCollection();
        }
        return this.values;
    }

    public Object2IntArrayMap<K> clone() {
        Object2IntArrayMap c;
        try {
            c = (Object2IntArrayMap)super.clone();
        }
        catch (CloneNotSupportedException cantHappen) {
            throw new InternalError();
        }
        c.key = (Object[])this.key.clone();
        c.value = (int[])this.value.clone();
        c.entries = null;
        c.keys = null;
        c.values = null;
        return c;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        Object[] key = this.key;
        int[] value = this.value;
        int max = this.size;
        for (int i = 0; i < max; ++i) {
            s.writeObject(key[i]);
            s.writeInt(value[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.key = new Object[this.size];
        Object[] key = this.key;
        this.value = new int[this.size];
        int[] value = this.value;
        for (int i = 0; i < this.size; ++i) {
            key[i] = s.readObject();
            value[i] = s.readInt();
        }
    }

    private final class EntrySet
    extends AbstractObjectSet<Object2IntMap.Entry<K>>
    implements Object2IntMap.FastEntrySet<K> {
        private EntrySet() {
        }

        @Override
        public ObjectIterator<Object2IntMap.Entry<K>> iterator() {
            return new ObjectIterator<Object2IntMap.Entry<K>>(){
                private MapEntry entry;
                int curr = -1;
                int next = 0;

                @Override
                public boolean hasNext() {
                    return this.next < Object2IntArrayMap.this.size;
                }

                @Override
                public Object2IntMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next++;
                    this.entry = new MapEntry(this.curr);
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Object2IntArrayMap.this.size-- - this.next--;
                    System.arraycopy(Object2IntArrayMap.this.key, this.next + 1, Object2IntArrayMap.this.key, this.next, tail);
                    System.arraycopy(Object2IntArrayMap.this.value, this.next + 1, Object2IntArrayMap.this.value, this.next, tail);
                    this.entry.index = -1;
                    Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
                }

                @Override
                public int skip(int n) {
                    if (n < 0) {
                        throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                    }
                    n = Math.min(n, Object2IntArrayMap.this.size - this.next);
                    this.next += n;
                    if (n != 0) {
                        this.curr = this.next - 1;
                    }
                    return n;
                }

                @Override
                public void forEachRemaining(Consumer<? super Object2IntMap.Entry<K>> action) {
                    int max = Object2IntArrayMap.this.size;
                    while (this.next < max) {
                        ++this.next;
                        this.curr = this.curr;
                        this.entry = new MapEntry(this.curr);
                        action.accept(this.entry);
                    }
                }
            };
        }

        @Override
        public ObjectIterator<Object2IntMap.Entry<K>> fastIterator() {
            return new ObjectIterator<Object2IntMap.Entry<K>>(){
                private MapEntry entry;
                int next;
                int curr;
                {
                    this.entry = new MapEntry();
                    this.next = 0;
                    this.curr = -1;
                }

                @Override
                public boolean hasNext() {
                    return this.next < Object2IntArrayMap.this.size;
                }

                @Override
                public Object2IntMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next++;
                    this.entry.index = this.curr;
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int tail = Object2IntArrayMap.this.size-- - this.next--;
                    System.arraycopy(Object2IntArrayMap.this.key, this.next + 1, Object2IntArrayMap.this.key, this.next, tail);
                    System.arraycopy(Object2IntArrayMap.this.value, this.next + 1, Object2IntArrayMap.this.value, this.next, tail);
                    this.entry.index = -1;
                    Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
                }

                @Override
                public int skip(int n) {
                    if (n < 0) {
                        throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                    }
                    n = Math.min(n, Object2IntArrayMap.this.size - this.next);
                    this.next += n;
                    if (n != 0) {
                        this.curr = this.next - 1;
                    }
                    return n;
                }

                @Override
                public void forEachRemaining(Consumer<? super Object2IntMap.Entry<K>> action) {
                    int max = Object2IntArrayMap.this.size;
                    while (this.next < max) {
                        ++this.next;
                        this.entry.index = this.curr = this.curr;
                        action.accept(this.entry);
                    }
                }
            };
        }

        @Override
        public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
            return new EntrySetSpliterator(0, Object2IntArrayMap.this.size);
        }

        @Override
        public void forEach(Consumer<? super Object2IntMap.Entry<K>> action) {
            int max = Object2IntArrayMap.this.size;
            for (int i = 0; i < max; ++i) {
                action.accept(new MapEntry(i));
            }
        }

        @Override
        public void fastForEach(Consumer<? super Object2IntMap.Entry<K>> action) {
            MapEntry entry = new MapEntry();
            int i = 0;
            int max = Object2IntArrayMap.this.size;
            while (i < max) {
                entry.index = i++;
                action.accept(entry);
            }
        }

        @Override
        public int size() {
            return Object2IntArrayMap.this.size;
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            Object k = e.getKey();
            return Object2IntArrayMap.this.containsKey(k) && Object2IntArrayMap.this.getInt(k) == ((Integer)e.getValue()).intValue();
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            if (e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            Object k = e.getKey();
            int v = (Integer)e.getValue();
            int oldPos = Object2IntArrayMap.this.findKey(k);
            if (oldPos == -1 || v != Object2IntArrayMap.this.value[oldPos]) {
                return false;
            }
            int tail = Object2IntArrayMap.this.size - oldPos - 1;
            System.arraycopy(Object2IntArrayMap.this.key, oldPos + 1, Object2IntArrayMap.this.key, oldPos, tail);
            System.arraycopy(Object2IntArrayMap.this.value, oldPos + 1, Object2IntArrayMap.this.value, oldPos, tail);
            --Object2IntArrayMap.this.size;
            Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
            return true;
        }

        final class EntrySetSpliterator
        extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Object2IntMap.Entry<K>>
        implements ObjectSpliterator<Object2IntMap.Entry<K>> {
            EntrySetSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
            }

            @Override
            public int characteristics() {
                return 16465;
            }

            @Override
            protected final Object2IntMap.Entry<K> get(int location) {
                return new MapEntry(location);
            }

            protected final com.viaversion.viaversion.libs.fastutil.objects.Object2IntArrayMap$EntrySet.EntrySetSpliterator makeForSplit(int pos, int maxPos) {
                return new EntrySetSpliterator(pos, maxPos);
            }
        }
    }

    private final class KeySet
    extends AbstractObjectSet<K> {
        private KeySet() {
        }

        @Override
        public boolean contains(Object k) {
            return Object2IntArrayMap.this.findKey(k) != -1;
        }

        @Override
        public boolean remove(Object k) {
            int oldPos = Object2IntArrayMap.this.findKey(k);
            if (oldPos == -1) {
                return false;
            }
            int tail = Object2IntArrayMap.this.size - oldPos - 1;
            System.arraycopy(Object2IntArrayMap.this.key, oldPos + 1, Object2IntArrayMap.this.key, oldPos, tail);
            System.arraycopy(Object2IntArrayMap.this.value, oldPos + 1, Object2IntArrayMap.this.value, oldPos, tail);
            --Object2IntArrayMap.this.size;
            Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
            return true;
        }

        @Override
        public ObjectIterator<K> iterator() {
            return new ObjectIterator<K>(){
                int pos = 0;

                @Override
                public boolean hasNext() {
                    return this.pos < Object2IntArrayMap.this.size;
                }

                @Override
                public K next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return Object2IntArrayMap.this.key[this.pos++];
                }

                @Override
                public void remove() {
                    if (this.pos == 0) {
                        throw new IllegalStateException();
                    }
                    int tail = Object2IntArrayMap.this.size - this.pos;
                    System.arraycopy(Object2IntArrayMap.this.key, this.pos, Object2IntArrayMap.this.key, this.pos - 1, tail);
                    System.arraycopy(Object2IntArrayMap.this.value, this.pos, Object2IntArrayMap.this.value, this.pos - 1, tail);
                    --Object2IntArrayMap.this.size;
                    --this.pos;
                    Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
                }

                @Override
                public void forEachRemaining(Consumer<? super K> action) {
                    Object[] key = Object2IntArrayMap.this.key;
                    int max = Object2IntArrayMap.this.size;
                    while (this.pos < max) {
                        action.accept(key[this.pos++]);
                    }
                }
            };
        }

        @Override
        public ObjectSpliterator<K> spliterator() {
            return new KeySetSpliterator(0, Object2IntArrayMap.this.size);
        }

        @Override
        public void forEach(Consumer<? super K> action) {
            Object[] key = Object2IntArrayMap.this.key;
            int max = Object2IntArrayMap.this.size;
            for (int i = 0; i < max; ++i) {
                action.accept(key[i]);
            }
        }

        @Override
        public int size() {
            return Object2IntArrayMap.this.size;
        }

        @Override
        public void clear() {
            Object2IntArrayMap.this.clear();
        }

        final class KeySetSpliterator
        extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<K>
        implements ObjectSpliterator<K> {
            KeySetSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
            }

            @Override
            public int characteristics() {
                return 16465;
            }

            @Override
            protected final K get(int location) {
                return Object2IntArrayMap.this.key[location];
            }

            protected final com.viaversion.viaversion.libs.fastutil.objects.Object2IntArrayMap$KeySet.KeySetSpliterator makeForSplit(int pos, int maxPos) {
                return new KeySetSpliterator(pos, maxPos);
            }

            @Override
            public void forEachRemaining(Consumer<? super K> action) {
                Object[] key = Object2IntArrayMap.this.key;
                int max = Object2IntArrayMap.this.size;
                while (this.pos < max) {
                    action.accept(key[this.pos++]);
                }
            }
        }
    }

    private final class ValuesCollection
    extends AbstractIntCollection {
        private ValuesCollection() {
        }

        @Override
        public boolean contains(int v) {
            return Object2IntArrayMap.this.containsValue(v);
        }

        @Override
        public IntIterator iterator() {
            return new IntIterator(){
                int pos = 0;

                @Override
                public boolean hasNext() {
                    return this.pos < Object2IntArrayMap.this.size;
                }

                @Override
                public int nextInt() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return Object2IntArrayMap.this.value[this.pos++];
                }

                @Override
                public void remove() {
                    if (this.pos == 0) {
                        throw new IllegalStateException();
                    }
                    int tail = Object2IntArrayMap.this.size - this.pos;
                    System.arraycopy(Object2IntArrayMap.this.key, this.pos, Object2IntArrayMap.this.key, this.pos - 1, tail);
                    System.arraycopy(Object2IntArrayMap.this.value, this.pos, Object2IntArrayMap.this.value, this.pos - 1, tail);
                    --Object2IntArrayMap.this.size;
                    --this.pos;
                    Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
                }

                @Override
                public void forEachRemaining(IntConsumer action) {
                    int[] value = Object2IntArrayMap.this.value;
                    int max = Object2IntArrayMap.this.size;
                    while (this.pos < max) {
                        action.accept(value[this.pos++]);
                    }
                }
            };
        }

        @Override
        public IntSpliterator spliterator() {
            return new ValuesSpliterator(0, Object2IntArrayMap.this.size);
        }

        @Override
        public void forEach(IntConsumer action) {
            int[] value = Object2IntArrayMap.this.value;
            int max = Object2IntArrayMap.this.size;
            for (int i = 0; i < max; ++i) {
                action.accept(value[i]);
            }
        }

        @Override
        public int size() {
            return Object2IntArrayMap.this.size;
        }

        @Override
        public void clear() {
            Object2IntArrayMap.this.clear();
        }

        final class ValuesSpliterator
        extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator
        implements IntSpliterator {
            ValuesSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
            }

            @Override
            public int characteristics() {
                return 16720;
            }

            @Override
            protected final int get(int location) {
                return Object2IntArrayMap.this.value[location];
            }

            protected final com.viaversion.viaversion.libs.fastutil.objects.Object2IntArrayMap$ValuesCollection.ValuesSpliterator makeForSplit(int pos, int maxPos) {
                return new ValuesSpliterator(pos, maxPos);
            }

            @Override
            public void forEachRemaining(IntConsumer action) {
                int[] value = Object2IntArrayMap.this.value;
                int max = Object2IntArrayMap.this.size;
                while (this.pos < max) {
                    action.accept(value[this.pos++]);
                }
            }
        }
    }

    private final class MapEntry
    implements Object2IntMap.Entry<K>,
    Map.Entry<K, Integer>,
    ObjectIntPair<K> {
        int index;

        MapEntry() {
        }

        MapEntry(int index2) {
            this.index = index2;
        }

        @Override
        public K getKey() {
            return Object2IntArrayMap.this.key[this.index];
        }

        @Override
        public K left() {
            return Object2IntArrayMap.this.key[this.index];
        }

        @Override
        public int getIntValue() {
            return Object2IntArrayMap.this.value[this.index];
        }

        @Override
        public int rightInt() {
            return Object2IntArrayMap.this.value[this.index];
        }

        @Override
        public int setValue(int v) {
            int oldValue = Object2IntArrayMap.this.value[this.index];
            Object2IntArrayMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override
        public ObjectIntPair<K> right(int v) {
            Object2IntArrayMap.this.value[this.index] = v;
            return this;
        }

        @Override
        @Deprecated
        public Integer getValue() {
            return Object2IntArrayMap.this.value[this.index];
        }

        @Override
        @Deprecated
        public Integer setValue(Integer v) {
            return this.setValue((int)v);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            return Objects.equals(Object2IntArrayMap.this.key[this.index], e.getKey()) && Object2IntArrayMap.this.value[this.index] == (Integer)e.getValue();
        }

        @Override
        public int hashCode() {
            return (Object2IntArrayMap.this.key[this.index] == null ? 0 : Object2IntArrayMap.this.key[this.index].hashCode()) ^ Object2IntArrayMap.this.value[this.index];
        }

        public String toString() {
            return Object2IntArrayMap.this.key[this.index] + "=>" + Object2IntArrayMap.this.value[this.index];
        }
    }
}

