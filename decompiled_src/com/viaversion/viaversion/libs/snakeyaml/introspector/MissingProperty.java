/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.introspector;

import com.viaversion.viaversion.libs.snakeyaml.introspector.Property;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

public class MissingProperty
extends Property {
    public MissingProperty(String name) {
        super(name, Object.class);
    }

    @Override
    public Class<?>[] getActualTypeArguments() {
        return new Class[0];
    }

    @Override
    public void set(Object object, Object value) throws Exception {
    }

    @Override
    public Object get(Object object) {
        return object;
    }

    @Override
    public List<Annotation> getAnnotations() {
        return Collections.emptyList();
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return null;
    }
}

