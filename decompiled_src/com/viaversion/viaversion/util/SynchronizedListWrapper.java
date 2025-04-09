/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class SynchronizedListWrapper<E>
implements List<E> {
    private final List<E> list;
    private final Consumer<E> addHandler;

    public SynchronizedListWrapper(List<E> inputList, Consumer<E> addHandler) {
        this.list = inputList;
        this.addHandler = addHandler;
    }

    public List<E> originalList() {
        return this.list;
    }

    private void handleAdd(E o) {
        this.addHandler.accept(o);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int size() {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isEmpty() {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.isEmpty();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean contains(Object o) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.contains(o);
        }
    }

    @Override
    public @NonNull Iterator<E> iterator() {
        return this.listIterator();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object @NonNull [] toArray() {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.toArray();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean add(E o) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            this.handleAdd(o);
            return this.list.add(o);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean remove(Object o) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.remove(o);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            for (E o : c) {
                this.handleAdd(o);
            }
            return this.list.addAll(c);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean addAll(int index2, Collection<? extends E> c) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            for (E o : c) {
                this.handleAdd(o);
            }
            return this.list.addAll(index2, c);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clear() {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            this.list.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public E get(int index2) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.get(index2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public E set(int index2, E element) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.set(index2, element);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void add(int index2, E element) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            this.list.add(index2, element);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public E remove(int index2) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.remove(index2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int indexOf(Object o) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.indexOf(o);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int lastIndexOf(Object o) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.lastIndexOf(o);
        }
    }

    @Override
    public @NonNull ListIterator<E> listIterator() {
        return this.list.listIterator();
    }

    @Override
    public @NonNull ListIterator<E> listIterator(int index2) {
        return this.list.listIterator(index2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @NonNull List<E> subList(int fromIndex, int toIndex) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.subList(fromIndex, toIndex);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.retainAll(c);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.removeAll(c);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.containsAll(c);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T @NonNull [] toArray(T @NonNull [] a) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.toArray(a);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void sort(Comparator<? super E> c) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            this.list.sort(c);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void forEach(Consumer<? super E> consumer) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            this.list.forEach(consumer);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.removeIf(filter);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.equals(o);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int hashCode() {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.hashCode();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString() {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.toString();
        }
    }
}

