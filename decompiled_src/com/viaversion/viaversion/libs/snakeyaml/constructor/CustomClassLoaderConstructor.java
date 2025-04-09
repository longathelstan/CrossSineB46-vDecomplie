/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.constructor;

import com.viaversion.viaversion.libs.snakeyaml.LoaderOptions;
import com.viaversion.viaversion.libs.snakeyaml.constructor.Constructor;

public class CustomClassLoaderConstructor
extends Constructor {
    private final ClassLoader loader;

    public CustomClassLoaderConstructor(ClassLoader loader, LoaderOptions loadingConfig) {
        this(Object.class, loader, loadingConfig);
    }

    public CustomClassLoaderConstructor(Class<? extends Object> theRoot, ClassLoader theLoader, LoaderOptions loadingConfig) {
        super(theRoot, loadingConfig);
        if (theLoader == null) {
            throw new NullPointerException("Loader must be provided.");
        }
        this.loader = theLoader;
    }

    @Override
    protected Class<?> getClassForName(String name) throws ClassNotFoundException {
        return Class.forName(name, true, this.loader);
    }
}

