/*
 * Decompiled with CFR 0.152.
 */
package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.SealedArrayFilter;

final class FrozenArrayFilter
extends SealedArrayFilter {
    FrozenArrayFilter(ArrayData underlying) {
        super(underlying);
    }

    @Override
    public ArrayData copy() {
        return this;
    }

    @Override
    public PropertyDescriptor getDescriptor(Global global, int index2) {
        return global.newDataDescriptor(this.getObject(index2), false, true, false);
    }

    @Override
    public ArrayData set(int index2, int value, boolean strict) {
        if (strict) {
            throw ECMAErrors.typeError("cant.set.property", Integer.toString(index2), "frozen array");
        }
        return this;
    }

    @Override
    public ArrayData set(int index2, double value, boolean strict) {
        if (strict) {
            throw ECMAErrors.typeError("cant.set.property", Integer.toString(index2), "frozen array");
        }
        return this;
    }

    @Override
    public ArrayData set(int index2, Object value, boolean strict) {
        if (strict) {
            throw ECMAErrors.typeError("cant.set.property", Integer.toString(index2), "frozen array");
        }
        return this;
    }

    @Override
    public ArrayData push(boolean strict, Object ... items) {
        return this;
    }

    @Override
    public Object pop() {
        int len = (int)this.underlying.length();
        return len == 0 ? ScriptRuntime.UNDEFINED : this.underlying.getObject(len - 1);
    }
}

