/*
 * Decompiled with CFR 0.152.
 */
package jdk.nashorn.internal.runtime.arrays;

import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.ArrayFilter;

final class LengthNotWritableFilter
extends ArrayFilter {
    private final SortedMap<Long, Object> extraElements;

    LengthNotWritableFilter(ArrayData underlying) {
        this(underlying, new TreeMap<Long, Object>());
    }

    private LengthNotWritableFilter(ArrayData underlying, SortedMap<Long, Object> extraElements) {
        super(underlying);
        this.extraElements = extraElements;
    }

    @Override
    public ArrayData copy() {
        return new LengthNotWritableFilter(this.underlying.copy(), new TreeMap<Long, Object>(this.extraElements));
    }

    @Override
    public boolean has(int index2) {
        return super.has(index2) || this.extraElements.containsKey(index2);
    }

    @Override
    public void setLength(long length) {
    }

    @Override
    public ArrayData ensure(long index2) {
        return this;
    }

    @Override
    public ArrayData slice(long from, long to) {
        return new LengthNotWritableFilter(this.underlying.slice(from, to), this.extraElements.subMap(from, to));
    }

    private boolean checkAdd(long index2, Object value) {
        if (index2 >= this.length()) {
            this.extraElements.put(index2, value);
            return true;
        }
        return false;
    }

    private Object get(long index2) {
        Object obj = this.extraElements.get(index2);
        if (obj == null) {
            return ScriptRuntime.UNDEFINED;
        }
        return obj;
    }

    @Override
    public int getInt(int index2) {
        if ((long)index2 >= this.length()) {
            return JSType.toInt32(this.get(index2));
        }
        return this.underlying.getInt(index2);
    }

    @Override
    public int getIntOptimistic(int index2, int programPoint) {
        if ((long)index2 >= this.length()) {
            return JSType.toInt32Optimistic(this.get(index2), programPoint);
        }
        return this.underlying.getIntOptimistic(index2, programPoint);
    }

    @Override
    public double getDouble(int index2) {
        if ((long)index2 >= this.length()) {
            return JSType.toNumber(this.get(index2));
        }
        return this.underlying.getDouble(index2);
    }

    @Override
    public double getDoubleOptimistic(int index2, int programPoint) {
        if ((long)index2 >= this.length()) {
            return JSType.toNumberOptimistic(this.get(index2), programPoint);
        }
        return this.underlying.getDoubleOptimistic(index2, programPoint);
    }

    @Override
    public Object getObject(int index2) {
        if ((long)index2 >= this.length()) {
            return this.get(index2);
        }
        return this.underlying.getObject(index2);
    }

    @Override
    public ArrayData set(int index2, Object value, boolean strict) {
        if (this.checkAdd(index2, value)) {
            return this;
        }
        this.underlying = this.underlying.set(index2, value, strict);
        return this;
    }

    @Override
    public ArrayData set(int index2, int value, boolean strict) {
        if (this.checkAdd(index2, value)) {
            return this;
        }
        this.underlying = this.underlying.set(index2, value, strict);
        return this;
    }

    @Override
    public ArrayData set(int index2, double value, boolean strict) {
        if (this.checkAdd(index2, value)) {
            return this;
        }
        this.underlying = this.underlying.set(index2, value, strict);
        return this;
    }

    @Override
    public ArrayData delete(int index2) {
        this.extraElements.remove(index2);
        this.underlying = this.underlying.delete(index2);
        return this;
    }

    @Override
    public ArrayData delete(long fromIndex, long toIndex) {
        Iterator<Long> iter = this.extraElements.keySet().iterator();
        while (iter.hasNext()) {
            long next = iter.next();
            if (next >= fromIndex && next <= toIndex) {
                iter.remove();
            }
            if (next <= toIndex) continue;
            break;
        }
        this.underlying = this.underlying.delete(fromIndex, toIndex);
        return this;
    }

    @Override
    public Iterator<Long> indexIterator() {
        List<Long> keys2 = this.computeIteratorKeys();
        keys2.addAll(this.extraElements.keySet());
        return keys2.iterator();
    }
}

