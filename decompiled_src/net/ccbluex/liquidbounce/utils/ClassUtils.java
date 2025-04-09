/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.value.Value;
import org.apache.logging.log4j.core.config.plugins.ResolverUtil;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u00020\u00012\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tJ)\u0010\n\u001a\u0011\u0012\r\u0012\u000b\u0012\u0002\b\u00030\f\u00a2\u0006\u0002\b\r0\u000b2\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\u000e\u001a\u00020\u0001J\u000e\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0005J4\u0010\u0011\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00120\t0\u000b\"\b\b\u0000\u0010\u0012*\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00052\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00120\tR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lnet/ccbluex/liquidbounce/utils/ClassUtils;", "", "()V", "cachedClasses", "", "", "", "getObjectInstance", "clazz", "Ljava/lang/Class;", "getValues", "", "Lnet/ccbluex/liquidbounce/features/value/Value;", "Lkotlin/internal/NoInfer;", "instance", "hasClass", "className", "resolvePackage", "T", "packagePath", "klass", "CrossSine"})
public final class ClassUtils {
    @NotNull
    public static final ClassUtils INSTANCE = new ClassUtils();
    @NotNull
    private static final Map<String, Boolean> cachedClasses = new LinkedHashMap();

    private ClassUtils() {
    }

    public final boolean hasClass(@NotNull String className) {
        boolean bl;
        Intrinsics.checkNotNullParameter(className, "className");
        if (cachedClasses.containsKey(className)) {
            Boolean bl2 = cachedClasses.get(className);
            Intrinsics.checkNotNull(bl2);
            bl = bl2;
        } else {
            boolean bl3;
            try {
                Class.forName(className);
                Map<String, Boolean> map = cachedClasses;
                Boolean bl4 = true;
                map.put(className, bl4);
                bl3 = true;
            }
            catch (ClassNotFoundException e) {
                Map<String, Boolean> map = cachedClasses;
                Boolean bl5 = false;
                map.put(className, bl5);
                bl3 = false;
            }
            bl = bl3;
        }
        return bl;
    }

    @NotNull
    public final Object getObjectInstance(@NotNull Class<?> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Field[] fieldArray = clazz.getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(fieldArray, "clazz.declaredFields");
        Object[] $this$forEach$iv = fieldArray;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Field it = (Field)element$iv;
            boolean bl = false;
            if (!it.getName().equals("INSTANCE")) continue;
            Object object = it.get(null);
            Intrinsics.checkNotNullExpressionValue(object, "it.get(null)");
            return object;
        }
        throw new IllegalAccessException("This class not a kotlin object");
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<Value<?>> getValues(@NotNull Class<?> clazz, @NotNull Object instance) {
        void $this$filterIsInstanceTo$iv$iv;
        Iterable $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(instance, "instance");
        Field[] fieldArray = clazz.getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(fieldArray, "clazz.declaredFields");
        Object[] $this$map$iv = fieldArray;
        boolean $i$f$map = false;
        Object[] objectArray = $this$map$iv;
        Collection destination$iv$iv = new ArrayList($this$map$iv.length);
        boolean $i$f$mapTo = false;
        for (void item$iv$iv : $this$mapTo$iv$iv) {
            void valueField;
            Field field = (Field)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            valueField.setAccessible(true);
            collection.add(valueField.get(instance));
        }
        Iterable $this$filterIsInstance$iv = (List)destination$iv$iv;
        boolean $i$f$filterIsInstance = false;
        $this$mapTo$iv$iv = $this$filterIsInstance$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof Value)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @NotNull
    public final <T> List<Class<? extends T>> resolvePackage(@NotNull String packagePath, @NotNull Class<T> klass) {
        Intrinsics.checkNotNullParameter(packagePath, "packagePath");
        Intrinsics.checkNotNullParameter(klass, "klass");
        ResolverUtil resolver = new ResolverUtil();
        resolver.setClassLoader(klass.getClassLoader());
        resolver.findInPackage((ResolverUtil.Test)new ResolverUtil.ClassTest(){

            public boolean matches(@NotNull Class<?> type) {
                Intrinsics.checkNotNullParameter(type, "type");
                return true;
            }
        }, packagePath);
        List list = new ArrayList();
        for (Class resolved : resolver.getClasses()) {
            Object object;
            block4: {
                Method[] methodArray = resolved.getDeclaredMethods();
                Intrinsics.checkNotNullExpressionValue(methodArray, "resolved.declaredMethods");
                for (Object object2 : (Object[])methodArray) {
                    Method it = (Method)object2;
                    boolean bl = false;
                    if (!Modifier.isNative(it.getModifiers())) continue;
                    object = object2;
                    break block4;
                }
                object = null;
            }
            Method method = (Method)object;
            if (method != null) {
                Method it = method;
                boolean bl = false;
                String klass1 = it.getDeclaringClass().getTypeName() + '.' + it.getName();
                throw new UnsatisfiedLinkError(klass1 + "\n\tat " + klass1 + "(Native Method)");
            }
            if (!klass.isAssignableFrom(resolved) || resolved.isInterface() || Modifier.isAbstract(resolved.getModifiers())) continue;
            Class clazz = resolved;
            if (clazz == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.Class<out T of net.ccbluex.liquidbounce.utils.ClassUtils.resolvePackage>");
            }
            list.add(clazz);
        }
        return list;
    }
}

