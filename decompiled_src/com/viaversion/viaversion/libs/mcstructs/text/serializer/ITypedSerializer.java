/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.serializer;

public interface ITypedSerializer<T, O> {
    public T serialize(O var1);

    public O deserialize(T var1);
}

