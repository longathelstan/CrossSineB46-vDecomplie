/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type;

import com.viaversion.viaversion.api.type.ByteBufReader;
import com.viaversion.viaversion.api.type.ByteBufWriter;
import com.viaversion.viaversion.util.Either;
import io.netty.buffer.ByteBuf;

public abstract class Type<T>
implements ByteBufReader<T>,
ByteBufWriter<T> {
    private final Class<? super T> outputClass;
    private final String typeName;

    protected Type(Class<? super T> outputClass) {
        this(null, outputClass);
    }

    protected Type(String typeName, Class<? super T> outputClass) {
        this.outputClass = outputClass;
        this.typeName = typeName;
    }

    public Class<? super T> getOutputClass() {
        return this.outputClass;
    }

    public String getTypeName() {
        return this.typeName != null ? this.typeName : this.getClass().getSimpleName();
    }

    public Class<? extends Type> getBaseClass() {
        return this.getClass();
    }

    public String toString() {
        return this.getTypeName();
    }

    public static <X, Y> Either<X, Y> readEither(ByteBuf buf, Type<X> leftType, Type<Y> rightType) {
        if (buf.readBoolean()) {
            return Either.left(leftType.read(buf));
        }
        return Either.right(rightType.read(buf));
    }

    public static <X, Y> void writeEither(ByteBuf buf, Either<X, Y> value, Type<X> leftType, Type<Y> rightType) {
        if (value.isLeft()) {
            buf.writeBoolean(true);
            leftType.write(buf, value.left());
        } else {
            buf.writeBoolean(false);
            rightType.write(buf, value.right());
        }
    }
}

