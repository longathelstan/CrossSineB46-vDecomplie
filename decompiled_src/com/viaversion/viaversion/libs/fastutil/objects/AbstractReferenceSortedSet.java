/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractReferenceSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ReferenceSortedSet;

public abstract class AbstractReferenceSortedSet<K>
extends AbstractReferenceSet<K>
implements ReferenceSortedSet<K> {
    protected AbstractReferenceSortedSet() {
    }

    @Override
    public abstract ObjectBidirectionalIterator<K> iterator();
}

