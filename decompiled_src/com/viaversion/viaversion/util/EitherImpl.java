/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.util.Either;
import java.util.Objects;

public class EitherImpl<X, Y>
implements Either<X, Y> {
    private final X left;
    private final Y right;

    protected EitherImpl(X left, Y value) {
        this.left = left;
        this.right = value;
        Preconditions.checkArgument((left == null || value == null ? 1 : 0) != 0, (Object)"Either.left and Either.right are both present");
        Preconditions.checkArgument((left != null || value != null ? 1 : 0) != 0, (Object)"Either.left and Either.right are both null");
    }

    @Override
    public boolean isLeft() {
        return this.left != null;
    }

    @Override
    public boolean isRight() {
        return this.right != null;
    }

    @Override
    public X left() {
        return this.left;
    }

    @Override
    public Y right() {
        return this.right;
    }

    public String toString() {
        Y y = this.right;
        X x = this.left;
        return "Either{" + x + ", " + y + "}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        EitherImpl pair = (EitherImpl)o;
        if (!Objects.equals(this.left, pair.left)) {
            return false;
        }
        return Objects.equals(this.right, pair.right);
    }

    public int hashCode() {
        int result = this.left != null ? this.left.hashCode() : 0;
        result = 31 * result + (this.right != null ? this.right.hashCode() : 0);
        return result;
    }
}

