/*
 * Decompiled with CFR 0.152.
 */
package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.ArrayFilter;

final class NonExtensibleArrayFilter
extends ArrayFilter {
    NonExtensibleArrayFilter(ArrayData underlying) {
        super(underlying);
    }

    @Override
    public ArrayData copy() {
        return new NonExtensibleArrayFilter(this.underlying.copy());
    }

    @Override
    public ArrayData slice(long from, long to) {
        return new NonExtensibleArrayFilter(this.underlying.slice(from, to));
    }

    private ArrayData extensionCheck(boolean strict, int index2) {
        if (!strict) {
            return this;
        }
        throw ECMAErrors.typeError(Global.instance(), "object.non.extensible", String.valueOf(index2), ScriptRuntime.safeToString(this));
    }

    @Override
    public ArrayData set(int index2, Object value, boolean strict) {
        if (this.has(index2)) {
            return this.underlying.set(index2, value, strict);
        }
        return this.extensionCheck(strict, index2);
    }

    @Override
    public ArrayData set(int index2, int value, boolean strict) {
        if (this.has(index2)) {
            return this.underlying.set(index2, value, strict);
        }
        return this.extensionCheck(strict, index2);
    }

    @Override
    public ArrayData set(int index2, double value, boolean strict) {
        if (this.has(index2)) {
            return this.underlying.set(index2, value, strict);
        }
        return this.extensionCheck(strict, index2);
    }
}

