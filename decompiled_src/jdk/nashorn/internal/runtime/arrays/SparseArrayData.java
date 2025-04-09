/*
 * Decompiled with CFR 0.152.
 */
package jdk.nashorn.internal.runtime.arrays;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;

class SparseArrayData
extends ArrayData {
    static final int MAX_DENSE_LENGTH = 131072;
    private ArrayData underlying;
    private final long maxDenseLength;
    private TreeMap<Long, Object> sparseMap;

    SparseArrayData(ArrayData underlying, long length) {
        this(underlying, length, new TreeMap<Long, Object>());
    }

    private SparseArrayData(ArrayData underlying, long length, TreeMap<Long, Object> sparseMap) {
        super(length);
        assert (underlying.length() <= length);
        this.underlying = underlying;
        this.maxDenseLength = underlying.length();
        this.sparseMap = sparseMap;
    }

    @Override
    public ArrayData copy() {
        return new SparseArrayData(this.underlying.copy(), this.length(), new TreeMap<Long, Object>((SortedMap<Long, Object>)this.sparseMap));
    }

    @Override
    public Object[] asObjectArray() {
        Map.Entry<Long, Object> entry;
        long key;
        int len = (int)Math.min(this.length(), Integer.MAX_VALUE);
        int underlyingLength = (int)Math.min((long)len, this.underlying.length());
        Object[] objArray = new Object[len];
        for (int i = 0; i < underlyingLength; ++i) {
            objArray[i] = this.underlying.getObject(i);
        }
        Arrays.fill(objArray, underlyingLength, len, ScriptRuntime.UNDEFINED);
        Iterator<Map.Entry<Long, Object>> iterator2 = this.sparseMap.entrySet().iterator();
        while (iterator2.hasNext() && (key = (entry = iterator2.next()).getKey().longValue()) < Integer.MAX_VALUE) {
            objArray[(int)key] = entry.getValue();
        }
        return objArray;
    }

    @Override
    public ArrayData shiftLeft(int by) {
        this.underlying = this.underlying.shiftLeft(by);
        TreeMap<Long, Object> newSparseMap = new TreeMap<Long, Object>();
        for (Map.Entry<Long, Object> entry : this.sparseMap.entrySet()) {
            long newIndex = entry.getKey() - (long)by;
            if (newIndex < 0L) continue;
            if (newIndex < this.maxDenseLength) {
                long oldLength = this.underlying.length();
                this.underlying = this.underlying.ensure(newIndex).set((int)newIndex, entry.getValue(), false).safeDelete(oldLength, newIndex - 1L, false);
                continue;
            }
            newSparseMap.put(newIndex, entry.getValue());
        }
        this.sparseMap = newSparseMap;
        this.setLength(Math.max(this.length() - (long)by, 0L));
        return this.sparseMap.isEmpty() ? this.underlying : this;
    }

    @Override
    public ArrayData shiftRight(int by) {
        TreeMap<Long, Object> newSparseMap = new TreeMap<Long, Object>();
        long len = this.underlying.length();
        if (len + (long)by > this.maxDenseLength) {
            long tempLength;
            for (long i = tempLength = Math.max(0L, this.maxDenseLength - (long)by); i < len; ++i) {
                if (!this.underlying.has((int)i)) continue;
                newSparseMap.put(i + (long)by, this.underlying.getObject((int)i));
            }
            this.underlying = this.underlying.shrink((int)tempLength);
            this.underlying.setLength(tempLength);
        }
        this.underlying = this.underlying.shiftRight(by);
        for (Map.Entry<Long, Object> entry : this.sparseMap.entrySet()) {
            long newIndex = entry.getKey() + (long)by;
            newSparseMap.put(newIndex, entry.getValue());
        }
        this.sparseMap = newSparseMap;
        this.setLength(this.length() + (long)by);
        return this;
    }

    @Override
    public ArrayData ensure(long safeIndex) {
        if (safeIndex >= this.length()) {
            this.setLength(safeIndex + 1L);
        }
        return this;
    }

    @Override
    public ArrayData shrink(long newLength) {
        if (newLength < this.underlying.length()) {
            this.underlying = this.underlying.shrink(newLength);
            this.underlying.setLength(newLength);
            this.sparseMap.clear();
            this.setLength(newLength);
        }
        this.sparseMap.subMap(newLength, Long.MAX_VALUE).clear();
        this.setLength(newLength);
        return this;
    }

    @Override
    public ArrayData set(int index2, Object value, boolean strict) {
        if (index2 >= 0 && (long)index2 < this.maxDenseLength) {
            long oldLength = this.underlying.length();
            this.underlying = this.underlying.ensure(index2).set(index2, value, strict).safeDelete(oldLength, index2 - 1, strict);
            this.setLength(Math.max(this.underlying.length(), this.length()));
        } else {
            Long longIndex = SparseArrayData.indexToKey(index2);
            this.sparseMap.put(longIndex, value);
            this.setLength(Math.max(longIndex + 1L, this.length()));
        }
        return this;
    }

    @Override
    public ArrayData set(int index2, int value, boolean strict) {
        if (index2 >= 0 && (long)index2 < this.maxDenseLength) {
            long oldLength = this.underlying.length();
            this.underlying = this.underlying.ensure(index2).set(index2, value, strict).safeDelete(oldLength, index2 - 1, strict);
            this.setLength(Math.max(this.underlying.length(), this.length()));
        } else {
            Long longIndex = SparseArrayData.indexToKey(index2);
            this.sparseMap.put(longIndex, value);
            this.setLength(Math.max(longIndex + 1L, this.length()));
        }
        return this;
    }

    @Override
    public ArrayData set(int index2, double value, boolean strict) {
        if (index2 >= 0 && (long)index2 < this.maxDenseLength) {
            long oldLength = this.underlying.length();
            this.underlying = this.underlying.ensure(index2).set(index2, value, strict).safeDelete(oldLength, index2 - 1, strict);
            this.setLength(Math.max(this.underlying.length(), this.length()));
        } else {
            Long longIndex = SparseArrayData.indexToKey(index2);
            this.sparseMap.put(longIndex, value);
            this.setLength(Math.max(longIndex + 1L, this.length()));
        }
        return this;
    }

    @Override
    public ArrayData setEmpty(int index2) {
        this.underlying.setEmpty(index2);
        return this;
    }

    @Override
    public ArrayData setEmpty(long lo, long hi) {
        this.underlying.setEmpty(lo, hi);
        return this;
    }

    @Override
    public Type getOptimisticType() {
        return this.underlying.getOptimisticType();
    }

    @Override
    public int getInt(int index2) {
        if (index2 >= 0 && (long)index2 < this.maxDenseLength) {
            return this.underlying.getInt(index2);
        }
        return JSType.toInt32(this.sparseMap.get(SparseArrayData.indexToKey(index2)));
    }

    @Override
    public int getIntOptimistic(int index2, int programPoint) {
        if (index2 >= 0 && (long)index2 < this.maxDenseLength) {
            return this.underlying.getIntOptimistic(index2, programPoint);
        }
        return JSType.toInt32Optimistic(this.sparseMap.get(SparseArrayData.indexToKey(index2)), programPoint);
    }

    @Override
    public double getDouble(int index2) {
        if (index2 >= 0 && (long)index2 < this.maxDenseLength) {
            return this.underlying.getDouble(index2);
        }
        return JSType.toNumber(this.sparseMap.get(SparseArrayData.indexToKey(index2)));
    }

    @Override
    public double getDoubleOptimistic(int index2, int programPoint) {
        if (index2 >= 0 && (long)index2 < this.maxDenseLength) {
            return this.underlying.getDouble(index2);
        }
        return JSType.toNumberOptimistic(this.sparseMap.get(SparseArrayData.indexToKey(index2)), programPoint);
    }

    @Override
    public Object getObject(int index2) {
        if (index2 >= 0 && (long)index2 < this.maxDenseLength) {
            return this.underlying.getObject(index2);
        }
        Long key = SparseArrayData.indexToKey(index2);
        if (this.sparseMap.containsKey(key)) {
            return this.sparseMap.get(key);
        }
        return ScriptRuntime.UNDEFINED;
    }

    @Override
    public boolean has(int index2) {
        if (index2 >= 0 && (long)index2 < this.maxDenseLength) {
            return (long)index2 < this.underlying.length() && this.underlying.has(index2);
        }
        return this.sparseMap.containsKey(SparseArrayData.indexToKey(index2));
    }

    @Override
    public ArrayData delete(int index2) {
        if (index2 >= 0 && (long)index2 < this.maxDenseLength) {
            if ((long)index2 < this.underlying.length()) {
                this.underlying = this.underlying.delete(index2);
            }
        } else {
            this.sparseMap.remove(SparseArrayData.indexToKey(index2));
        }
        return this;
    }

    @Override
    public ArrayData delete(long fromIndex, long toIndex) {
        if (fromIndex < this.maxDenseLength && fromIndex < this.underlying.length()) {
            this.underlying = this.underlying.delete(fromIndex, Math.min(toIndex, this.underlying.length() - 1L));
        }
        if (toIndex >= this.maxDenseLength) {
            this.sparseMap.subMap(fromIndex, true, toIndex, true).clear();
        }
        return this;
    }

    private static Long indexToKey(int index2) {
        return ArrayIndex.toLongIndex(index2);
    }

    @Override
    public ArrayData convert(Class<?> type) {
        this.underlying = this.underlying.convert(type);
        return this;
    }

    @Override
    public Object pop() {
        long len = this.length();
        long underlyingLen = this.underlying.length();
        if (len == 0L) {
            return ScriptRuntime.UNDEFINED;
        }
        if (len == underlyingLen) {
            Object result = this.underlying.pop();
            this.setLength(this.underlying.length());
            return result;
        }
        this.setLength(len - 1L);
        Long key = len - 1L;
        return this.sparseMap.containsKey(key) ? this.sparseMap.remove(key) : ScriptRuntime.UNDEFINED;
    }

    @Override
    public ArrayData slice(long from, long to) {
        assert (to <= this.length());
        long start = from < 0L ? from + this.length() : from;
        long newLength = to - start;
        long underlyingLength = this.underlying.length();
        if (start >= 0L && to <= this.maxDenseLength) {
            if (newLength <= underlyingLength) {
                return this.underlying.slice(from, to);
            }
            return this.underlying.slice(from, to).ensure(newLength - 1L).delete(underlyingLength, newLength);
        }
        ArrayData sliced = EMPTY_ARRAY;
        sliced = sliced.ensure(newLength - 1L);
        long i = start;
        while (i < to) {
            if (this.has((int)i)) {
                sliced = sliced.set((int)(i - start), this.getObject((int)i), false);
            }
            i = this.nextIndex(i);
        }
        assert (sliced.length() == newLength);
        return sliced;
    }

    @Override
    public long nextIndex(long index2) {
        if (index2 < this.underlying.length() - 1L) {
            return this.underlying.nextIndex(index2);
        }
        Long nextKey = this.sparseMap.higherKey(index2);
        if (nextKey != null) {
            return nextKey;
        }
        return this.length();
    }
}

