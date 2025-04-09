/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import com.viaversion.viaversion.util.Either;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class StatePropertyMatcher {
    final String name;
    final Either<String, RangedMatcher> matcher;
    public static final Type<StatePropertyMatcher> TYPE = new Type<StatePropertyMatcher>(StatePropertyMatcher.class){

        @Override
        public StatePropertyMatcher read(ByteBuf buffer) {
            String name = (String)Types.STRING.read(buffer);
            if (buffer.readBoolean()) {
                String value = (String)Types.STRING.read(buffer);
                return new StatePropertyMatcher(name, Either.left(value));
            }
            String minValue = (String)Types.OPTIONAL_STRING.read(buffer);
            String maxValue = (String)Types.OPTIONAL_STRING.read(buffer);
            return new StatePropertyMatcher(name, Either.right(new RangedMatcher(minValue, maxValue)));
        }

        @Override
        public void write(ByteBuf buffer, StatePropertyMatcher value) {
            Types.STRING.write(buffer, value.name);
            if (value.matcher.isLeft()) {
                buffer.writeBoolean(true);
                Types.STRING.write(buffer, value.matcher.left());
            } else {
                buffer.writeBoolean(false);
                Types.OPTIONAL_STRING.write(buffer, value.matcher.right().minValue());
                Types.OPTIONAL_STRING.write(buffer, value.matcher.right().maxValue());
            }
        }
    };
    public static final Type<StatePropertyMatcher[]> ARRAY_TYPE = new ArrayType<StatePropertyMatcher>(TYPE);

    public StatePropertyMatcher(String name, Either<String, RangedMatcher> matcher) {
        this.name = name;
        this.matcher = matcher;
    }

    public String name() {
        return this.name;
    }

    public Either<String, RangedMatcher> matcher() {
        return this.matcher;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof StatePropertyMatcher)) {
            return false;
        }
        StatePropertyMatcher statePropertyMatcher = (StatePropertyMatcher)object;
        return Objects.equals(this.name, statePropertyMatcher.name) && Objects.equals(this.matcher, statePropertyMatcher.matcher);
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.name)) * 31 + Objects.hashCode(this.matcher);
    }

    public String toString() {
        return String.format("%s[name=%s, matcher=%s]", this.getClass().getSimpleName(), Objects.toString(this.name), Objects.toString(this.matcher));
    }

    public static final class RangedMatcher {
        final @Nullable String minValue;
        final @Nullable String maxValue;

        public RangedMatcher(@Nullable String minValue, @Nullable String maxValue) {
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        public @Nullable String minValue() {
            return this.minValue;
        }

        public @Nullable String maxValue() {
            return this.maxValue;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof RangedMatcher)) {
                return false;
            }
            RangedMatcher rangedMatcher = (RangedMatcher)object;
            return Objects.equals(this.minValue, rangedMatcher.minValue) && Objects.equals(this.maxValue, rangedMatcher.maxValue);
        }

        public int hashCode() {
            return (0 * 31 + Objects.hashCode(this.minValue)) * 31 + Objects.hashCode(this.maxValue);
        }

        public String toString() {
            return String.format("%s[minValue=%s, maxValue=%s]", this.getClass().getSimpleName(), Objects.toString(this.minValue), Objects.toString(this.maxValue));
        }
    }
}

