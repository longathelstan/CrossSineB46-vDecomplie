/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction;
import com.viaversion.viaversion.util.IdAndData;
import java.util.Objects;

public final class ClassicBlockRemapper
implements StorableObject {
    private final Int2ObjectFunction<IdAndData> mapper;
    private final Object2IntFunction<IdAndData> reverseMapper;

    public ClassicBlockRemapper(Int2ObjectFunction<IdAndData> mapper, Object2IntFunction<IdAndData> reverseMapper) {
        this.mapper = mapper;
        this.reverseMapper = reverseMapper;
    }

    public Int2ObjectFunction<IdAndData> mapper() {
        return this.mapper;
    }

    public Object2IntFunction<IdAndData> reverseMapper() {
        return this.reverseMapper;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ClassicBlockRemapper)) {
            return false;
        }
        ClassicBlockRemapper classicBlockRemapper = (ClassicBlockRemapper)object;
        return Objects.equals(this.mapper, classicBlockRemapper.mapper) && Objects.equals(this.reverseMapper, classicBlockRemapper.reverseMapper);
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.mapper)) * 31 + Objects.hashCode(this.reverseMapper);
    }

    public String toString() {
        return String.format("%s[mapper=%s, reverseMapper=%s]", this.getClass().getSimpleName(), Objects.toString(this.mapper), Objects.toString(this.reverseMapper));
    }
}

