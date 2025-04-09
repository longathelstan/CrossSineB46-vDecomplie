/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.entitydata;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.Type;

public interface EntityDataType {
    public Type type();

    public int typeId();

    public static EntityDataType create(int typeId, Type<?> type) {
        return new EntityDataTypeImpl(typeId, type);
    }

    public static final class EntityDataTypeImpl
    implements EntityDataType {
        final int typeId;
        final Type<?> type;

        EntityDataTypeImpl(int typeId, Type<?> type) {
            Preconditions.checkNotNull(type);
            this.typeId = typeId;
            this.type = type;
        }

        @Override
        public int typeId() {
            return this.typeId;
        }

        @Override
        public Type<?> type() {
            return this.type;
        }

        public String toString() {
            Type<?> type = this.type;
            int n = this.typeId;
            return "EntityDataTypeImpl{typeId=" + n + ", type=" + type + "}";
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            EntityDataTypeImpl dataType = (EntityDataTypeImpl)o;
            if (this.typeId != dataType.typeId) {
                return false;
            }
            return this.type.equals(dataType.type);
        }

        public int hashCode() {
            int result = this.typeId;
            result = 31 * result + this.type.hashCode();
            return result;
        }
    }
}

