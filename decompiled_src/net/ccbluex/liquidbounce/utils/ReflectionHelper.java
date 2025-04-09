/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public final class ReflectionHelper {
    private static final Map<String, MethodHandle> HANDLE_CACHE = new HashMap<String, MethodHandle>();

    private ReflectionHelper() {
    }

    public static MethodHandle lookupStaticFieldHandle(Class<?> targetClass, String fieldName) {
        try {
            Field f = targetClass.getDeclaredField(fieldName);
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }
            return MethodHandles.lookup().unreflectGetter(f);
        }
        catch (IllegalAccessException | NoSuchFieldException ignored) {
            return null;
        }
    }

    public static Object invokeOrNull(MethodHandle handle) {
        if (handle == null) {
            return null;
        }
        try {
            return handle.invoke(null);
        }
        catch (Throwable ignored) {
            return null;
        }
    }

    public static Object getStaticField(Class<?> targetClass, String fieldName) {
        MethodHandle handle;
        String cacheName = targetClass.getTypeName() + "." + fieldName;
        if (HANDLE_CACHE.containsKey(cacheName)) {
            handle = HANDLE_CACHE.get(cacheName);
        } else {
            handle = ReflectionHelper.lookupStaticFieldHandle(targetClass, fieldName);
            HANDLE_CACHE.put(cacheName, handle);
        }
        return ReflectionHelper.invokeOrNull(handle);
    }

    public static <E> Method findMethod(Class<? super E> clazz, E instance, String[] methodNames, Class<?> ... methodTypes) {
        Exception failed = null;
        String[] var5 = methodNames;
        int var6 = methodNames.length;
        for (int var7 = 0; var7 < var6; ++var7) {
            String methodName = var5[var7];
            try {
                Method m2 = clazz.getDeclaredMethod(methodName, methodTypes);
                m2.setAccessible(true);
                return m2;
            }
            catch (Exception var10) {
                failed = var10;
                continue;
            }
        }
        throw new ReflectionHelper.UnableToFindMethodException(methodNames, failed);
    }
}

