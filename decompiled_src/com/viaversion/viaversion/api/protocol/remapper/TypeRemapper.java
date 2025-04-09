/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueReader;
import com.viaversion.viaversion.api.protocol.remapper.ValueWriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.exception.InformativeException;
import java.util.Objects;

public final class TypeRemapper<T>
implements ValueReader<T>,
ValueWriter<T> {
    private final Type<T> type;

    public TypeRemapper(Type<T> type) {
        this.type = type;
    }

    @Override
    public T read(PacketWrapper wrapper) throws InformativeException {
        return wrapper.read(this.type);
    }

    @Override
    public void write(PacketWrapper output, T inputValue) throws InformativeException {
        output.write(this.type, inputValue);
    }

    public Type<T> type() {
        return this.type;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof TypeRemapper)) {
            return false;
        }
        TypeRemapper typeRemapper = (TypeRemapper)object;
        return Objects.equals(this.type, typeRemapper.type);
    }

    public int hashCode() {
        return 0 * 31 + Objects.hashCode(this.type);
    }

    public String toString() {
        return String.format("%s[type=%s]", this.getClass().getSimpleName(), Objects.toString(this.type));
    }
}

