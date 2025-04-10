/*
 * Decompiled with CFR 0.152.
 */
package kotlin.jvm.internal;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.FloatIterator;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lkotlin/jvm/internal/ArrayFloatIterator;", "Lkotlin/collections/FloatIterator;", "array", "", "([F)V", "index", "", "hasNext", "", "nextFloat", "", "kotlin-stdlib"})
final class ArrayFloatIterator
extends FloatIterator {
    @NotNull
    private final float[] array;
    private int index;

    public ArrayFloatIterator(@NotNull float[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return this.index < this.array.length;
    }

    @Override
    public float nextFloat() {
        float f;
        try {
            ArrayFloatIterator arrayFloatIterator = this;
            int n = arrayFloatIterator.index;
            arrayFloatIterator.index = n + 1;
            f = this.array[n];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            ArrayFloatIterator arrayFloatIterator = this;
            --arrayFloatIterator.index;
            throw new NoSuchElementException(e.getMessage());
        }
        return f;
    }
}

