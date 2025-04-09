/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ReflectionUtil {
    public static Object invokeStatic(Class<?> clazz, String method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m2 = clazz.getDeclaredMethod(method, new Class[0]);
        return m2.invoke(null, new Object[0]);
    }

    public static Object invoke(Object o, String method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m2 = o.getClass().getDeclaredMethod(method, new Class[0]);
        return m2.invoke(o, new Object[0]);
    }

    public static <T> T getStatic(Class<?> clazz, String f, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        return type.cast(field.get(null));
    }

    public static void setStatic(Class<?> clazz, String f, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        field.set(null, value);
    }

    public static <T> T getSuper(Object o, String f, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
        Field field = o.getClass().getSuperclass().getDeclaredField(f);
        field.setAccessible(true);
        return type.cast(field.get(o));
    }

    public static <T> T get(Object instance, Class<?> clazz, String f, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        return type.cast(field.get(instance));
    }

    public static <T> T get(Object o, String f, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
        Field field = o.getClass().getDeclaredField(f);
        field.setAccessible(true);
        return type.cast(field.get(o));
    }

    public static <T> T getPublic(Object o, String f, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
        Field field = o.getClass().getField(f);
        field.setAccessible(true);
        return type.cast(field.get(o));
    }

    public static void set(Object o, String f, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = o.getClass().getDeclaredField(f);
        field.setAccessible(true);
        field.set(o, value);
    }
}

