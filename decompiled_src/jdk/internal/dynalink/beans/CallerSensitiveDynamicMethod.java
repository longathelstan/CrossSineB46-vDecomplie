/*
 * Decompiled with CFR 0.152.
 */
package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import jdk.internal.dynalink.beans.SingleDynamicMethod;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.beans.StaticClassIntrospector;
import jdk.internal.dynalink.support.Lookup;

class CallerSensitiveDynamicMethod
extends SingleDynamicMethod {
    private final AccessibleObject target;
    private final MethodType type;

    CallerSensitiveDynamicMethod(AccessibleObject target) {
        super(CallerSensitiveDynamicMethod.getName(target));
        this.target = target;
        this.type = CallerSensitiveDynamicMethod.getMethodType(target);
    }

    private static String getName(AccessibleObject target) {
        Member m2 = (Member)((Object)target);
        boolean constructor = m2 instanceof Constructor;
        return CallerSensitiveDynamicMethod.getMethodNameWithSignature(CallerSensitiveDynamicMethod.getMethodType(target), constructor ? m2.getName() : CallerSensitiveDynamicMethod.getClassAndMethodName(m2.getDeclaringClass(), m2.getName()), !constructor);
    }

    @Override
    MethodType getMethodType() {
        return this.type;
    }

    private static MethodType getMethodType(AccessibleObject ao) {
        boolean isMethod = ao instanceof Method;
        Class<Object> rtype = isMethod ? ((Method)ao).getReturnType() : ((Constructor)ao).getDeclaringClass();
        Class<?>[] ptypes = isMethod ? ((Method)ao).getParameterTypes() : ((Constructor)ao).getParameterTypes();
        MethodType type = MethodType.methodType(rtype, ptypes);
        Member m2 = (Member)((Object)ao);
        Class[] classArray = new Class[1];
        classArray[0] = isMethod ? (Modifier.isStatic(m2.getModifiers()) ? Object.class : m2.getDeclaringClass()) : StaticClass.class;
        return type.insertParameterTypes(0, classArray);
    }

    @Override
    boolean isVarArgs() {
        return this.target instanceof Method ? ((Method)this.target).isVarArgs() : ((Constructor)this.target).isVarArgs();
    }

    @Override
    MethodHandle getTarget(MethodHandles.Lookup lookup) {
        if (this.target instanceof Method) {
            MethodHandle mh = Lookup.unreflect(lookup, (Method)this.target);
            if (Modifier.isStatic(((Member)((Object)this.target)).getModifiers())) {
                return StaticClassIntrospector.editStaticMethodHandle(mh);
            }
            return mh;
        }
        return StaticClassIntrospector.editConstructorMethodHandle(Lookup.unreflectConstructor(lookup, (Constructor)this.target));
    }

    @Override
    boolean isConstructor() {
        return this.target instanceof Constructor;
    }
}

