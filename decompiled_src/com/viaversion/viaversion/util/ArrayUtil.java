/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import java.util.Arrays;

public final class ArrayUtil {
    public static <T> T[] add(T[] array, T element) {
        int length = array.length;
        T[] newArray = Arrays.copyOf(array, length + 1);
        newArray[length] = element;
        return newArray;
    }

    @SafeVarargs
    public static <T> T[] add(T[] array, T ... elements) {
        int length = array.length;
        T[] newArray = Arrays.copyOf(array, length + elements.length);
        System.arraycopy(elements, 0, newArray, length, elements.length);
        return newArray;
    }

    public static <T> T[] remove(T[] array, int index2) {
        T[] newArray = Arrays.copyOf(array, array.length - 1);
        System.arraycopy(array, index2 + 1, newArray, index2, newArray.length - index2);
        return newArray;
    }
}

