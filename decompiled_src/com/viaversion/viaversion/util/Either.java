/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.util.EitherImpl;

public interface Either<X, Y> {
    public static <X, Y> Either<X, Y> left(X left) {
        Preconditions.checkNotNull(left);
        return new EitherImpl<X, Object>(left, null);
    }

    public static <X, Y> Either<X, Y> right(Y right) {
        Preconditions.checkNotNull(right);
        return new EitherImpl<Object, Y>(null, right);
    }

    public boolean isLeft();

    public boolean isRight();

    public X left();

    public Y right();
}

