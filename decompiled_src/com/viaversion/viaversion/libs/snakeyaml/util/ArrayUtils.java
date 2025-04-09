/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.util;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;

public class ArrayUtils {
    private ArrayUtils() {
    }

    public static <E> List<E> toUnmodifiableList(E[] elements) {
        return elements.length == 0 ? Collections.emptyList() : new UnmodifiableArrayList<E>(elements);
    }

    public static <E> List<E> toUnmodifiableCompositeList(E[] array1, E[] array2) {
        List<E> result = array1.length == 0 ? ArrayUtils.toUnmodifiableList(array2) : (array2.length == 0 ? ArrayUtils.toUnmodifiableList(array1) : new CompositeUnmodifiableArrayList<E>(array1, array2));
        return result;
    }

    private static class CompositeUnmodifiableArrayList<E>
    extends AbstractList<E> {
        private final E[] array1;
        private final E[] array2;

        CompositeUnmodifiableArrayList(E[] array1, E[] array2) {
            this.array1 = array1;
            this.array2 = array2;
        }

        @Override
        public E get(int index2) {
            E element;
            if (index2 < this.array1.length) {
                element = this.array1[index2];
            } else if (index2 - this.array1.length < this.array2.length) {
                element = this.array2[index2 - this.array1.length];
            } else {
                throw new IndexOutOfBoundsException("Index: " + index2 + ", Size: " + this.size());
            }
            return element;
        }

        @Override
        public int size() {
            return this.array1.length + this.array2.length;
        }
    }

    private static class UnmodifiableArrayList<E>
    extends AbstractList<E> {
        private final E[] array;

        UnmodifiableArrayList(E[] array) {
            this.array = array;
        }

        @Override
        public E get(int index2) {
            if (index2 >= this.array.length) {
                throw new IndexOutOfBoundsException("Index: " + index2 + ", Size: " + this.size());
            }
            return this.array[index2];
        }

        @Override
        public int size() {
            return this.array.length;
        }
    }
}

