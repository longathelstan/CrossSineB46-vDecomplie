/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.fastutil.objects.ReferenceReferenceImmutablePair;

public interface ReferenceReferencePair<K, V>
extends Pair<K, V> {
    public static <K, V> ReferenceReferencePair<K, V> of(K left, V right) {
        return new ReferenceReferenceImmutablePair<K, V>(left, right);
    }
}

