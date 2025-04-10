/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractReferenceSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import com.viaversion.viaversion.libs.fastutil.objects.ReferenceArraySet;
import com.viaversion.viaversion.libs.fastutil.objects.ReferenceCollections;
import com.viaversion.viaversion.libs.fastutil.objects.ReferenceSet;
import com.viaversion.viaversion.libs.fastutil.objects.ReferenceSets;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ReferenceSets {
    static final int ARRAY_SET_CUTOFF = 4;
    public static final EmptySet EMPTY_SET = new EmptySet();
    static final ReferenceSet UNMODIFIABLE_EMPTY_SET = ReferenceSets.unmodifiable(new ReferenceArraySet(ObjectArrays.EMPTY_ARRAY));

    private ReferenceSets() {
    }

    public static <K> ReferenceSet<K> emptySet() {
        return EMPTY_SET;
    }

    public static <K> ReferenceSet<K> singleton(K element) {
        return new Singleton<K>(element);
    }

    public static <K> ReferenceSet<K> synchronize(ReferenceSet<K> s) {
        return new SynchronizedSet(s);
    }

    public static <K> ReferenceSet<K> synchronize(ReferenceSet<K> s, Object sync) {
        return new SynchronizedSet(s, sync);
    }

    public static <K> ReferenceSet<K> unmodifiable(ReferenceSet<? extends K> s) {
        return new UnmodifiableSet(s);
    }

    public static class EmptySet<K>
    extends ReferenceCollections.EmptyCollection<K>
    implements ReferenceSet<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public boolean remove(Object ok) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return EMPTY_SET;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Set && ((Set)o).isEmpty();
        }

        private Object readResolve() {
            return EMPTY_SET;
        }
    }

    public static class Singleton<K>
    extends AbstractReferenceSet<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final K element;

        protected Singleton(K element) {
            this.element = element;
        }

        @Override
        public boolean contains(Object k) {
            return k == this.element;
        }

        @Override
        public boolean remove(Object k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectListIterator<K> iterator() {
            return ObjectIterators.singleton(this.element);
        }

        @Override
        public ObjectSpliterator<K> spliterator() {
            return ObjectSpliterators.singleton(this.element);
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public Object[] toArray() {
            return new Object[]{this.element};
        }

        @Override
        public void forEach(Consumer<? super K> action) {
            action.accept(this.element);
        }

        @Override
        public boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeIf(Predicate<? super K> filter) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }
    }
}

