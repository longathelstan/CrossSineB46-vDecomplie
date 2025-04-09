/*
 * Decompiled with CFR 0.152.
 */
package jdk.nashorn.internal.runtime.arrays;

import java.lang.reflect.Array;
import jdk.nashorn.internal.runtime.BitVector;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.ArrayFilter;
import jdk.nashorn.internal.runtime.arrays.SparseArrayData;

final class UndefinedArrayFilter
extends ArrayFilter {
    private final BitVector undefined;

    UndefinedArrayFilter(ArrayData underlying) {
        super(underlying);
        this.undefined = new BitVector(underlying.length());
    }

    @Override
    public ArrayData copy() {
        UndefinedArrayFilter copy = new UndefinedArrayFilter(this.underlying.copy());
        copy.getUndefined().copy(this.undefined);
        return copy;
    }

    @Override
    public Object[] asObjectArray() {
        Object[] value = super.asObjectArray();
        for (int i = 0; i < value.length; ++i) {
            if (!this.undefined.isSet(i)) continue;
            value[i] = ScriptRuntime.UNDEFINED;
        }
        return value;
    }

    @Override
    public Object asArrayOfType(Class<?> componentType) {
        Object value = super.asArrayOfType(componentType);
        Object undefValue = UndefinedArrayFilter.convertUndefinedValue(componentType);
        int l = Array.getLength(value);
        for (int i = 0; i < l; ++i) {
            if (!this.undefined.isSet(i)) continue;
            Array.set(value, i, undefValue);
        }
        return value;
    }

    @Override
    public ArrayData shiftLeft(int by) {
        super.shiftLeft(by);
        this.undefined.shiftLeft(by, this.length());
        return this;
    }

    @Override
    public ArrayData shiftRight(int by) {
        super.shiftRight(by);
        this.undefined.shiftRight(by, this.length());
        return this;
    }

    @Override
    public ArrayData ensure(long safeIndex) {
        if (safeIndex >= 131072L && safeIndex >= this.length()) {
            return new SparseArrayData(this, safeIndex + 1L);
        }
        super.ensure(safeIndex);
        this.undefined.resize(this.length());
        return this;
    }

    @Override
    public ArrayData shrink(long newLength) {
        super.shrink(newLength);
        this.undefined.resize(this.length());
        return this;
    }

    @Override
    public ArrayData set(int index2, Object value, boolean strict) {
        this.undefined.clear(index2);
        if (value == ScriptRuntime.UNDEFINED) {
            this.undefined.set(index2);
            return this;
        }
        return super.set(index2, value, strict);
    }

    @Override
    public ArrayData set(int index2, int value, boolean strict) {
        this.undefined.clear(index2);
        return super.set(index2, value, strict);
    }

    @Override
    public ArrayData set(int index2, double value, boolean strict) {
        this.undefined.clear(index2);
        return super.set(index2, value, strict);
    }

    @Override
    public int getInt(int index2) {
        if (this.undefined.isSet(index2)) {
            return 0;
        }
        return super.getInt(index2);
    }

    @Override
    public int getIntOptimistic(int index2, int programPoint) {
        if (this.undefined.isSet(index2)) {
            throw new UnwarrantedOptimismException(ScriptRuntime.UNDEFINED, programPoint);
        }
        return super.getIntOptimistic(index2, programPoint);
    }

    @Override
    public double getDouble(int index2) {
        if (this.undefined.isSet(index2)) {
            return Double.NaN;
        }
        return super.getDouble(index2);
    }

    @Override
    public double getDoubleOptimistic(int index2, int programPoint) {
        if (this.undefined.isSet(index2)) {
            throw new UnwarrantedOptimismException(ScriptRuntime.UNDEFINED, programPoint);
        }
        return super.getDoubleOptimistic(index2, programPoint);
    }

    @Override
    public Object getObject(int index2) {
        if (this.undefined.isSet(index2)) {
            return ScriptRuntime.UNDEFINED;
        }
        return super.getObject(index2);
    }

    @Override
    public ArrayData delete(int index2) {
        this.undefined.clear(index2);
        return super.delete(index2);
    }

    @Override
    public Object pop() {
        long index2 = this.length() - 1L;
        if (super.has((int)index2)) {
            boolean isUndefined = this.undefined.isSet(index2);
            Object value = super.pop();
            return isUndefined ? ScriptRuntime.UNDEFINED : value;
        }
        return super.pop();
    }

    @Override
    public ArrayData slice(long from, long to) {
        ArrayData newArray = this.underlying.slice(from, to);
        UndefinedArrayFilter newFilter = new UndefinedArrayFilter(newArray);
        newFilter.getUndefined().copy(this.undefined);
        newFilter.getUndefined().shiftLeft(from, newFilter.length());
        return newFilter;
    }

    private BitVector getUndefined() {
        return this.undefined;
    }
}

