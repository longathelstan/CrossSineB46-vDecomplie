/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.rewriter.entitydata;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.rewriter.entitydata.EntityDataHandler;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class EntityDataFilter {
    final @Nullable EntityType type;
    final boolean filterFamily;
    final @Nullable EntityDataType dataType;
    final int index;
    final EntityDataHandler handler;

    public EntityDataFilter(@Nullable EntityType type, boolean filterFamily, @Nullable EntityDataType dataType, int index2, EntityDataHandler handler) {
        Preconditions.checkNotNull((Object)handler, (Object)"EntityDataHandler cannot be null");
        this.type = type;
        this.filterFamily = filterFamily;
        this.dataType = dataType;
        this.index = index2;
        this.handler = handler;
    }

    public int index() {
        return this.index;
    }

    public @Nullable EntityType type() {
        return this.type;
    }

    public @Nullable EntityDataType dataType() {
        return this.dataType;
    }

    public EntityDataHandler handler() {
        return this.handler;
    }

    public boolean filterFamily() {
        return this.filterFamily;
    }

    public boolean isFiltered(@Nullable EntityType type, EntityData entityData) {
        return !(this.index != -1 && entityData.id() != this.index || this.type != null && !this.matchesType(type) || this.dataType != null && entityData.dataType() != this.dataType);
    }

    boolean matchesType(EntityType type) {
        if (type == null) {
            return false;
        }
        return this.filterFamily ? type.isOrHasParent(this.type) : this.type == type;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        EntityDataFilter that = (EntityDataFilter)o;
        if (this.index != that.index) {
            return false;
        }
        if (this.filterFamily != that.filterFamily) {
            return false;
        }
        if (!this.handler.equals(that.handler)) {
            return false;
        }
        if (!Objects.equals(this.dataType, that.dataType)) {
            return false;
        }
        return Objects.equals(this.type, that.type);
    }

    public int hashCode() {
        int result = this.handler.hashCode();
        result = 31 * result + (this.type != null ? this.type.hashCode() : 0);
        result = 31 * result + (this.dataType != null ? this.dataType.hashCode() : 0);
        result = 31 * result + this.index;
        result = 31 * result + (this.filterFamily ? 1 : 0);
        return result;
    }

    public String toString() {
        EntityDataHandler entityDataHandler = this.handler;
        int n = this.index;
        EntityDataType entityDataType = this.dataType;
        boolean bl = this.filterFamily;
        EntityType entityType = this.type;
        return "EntityDataFilter{type=" + entityType + ", filterFamily=" + bl + ", dataType=" + entityDataType + ", index=" + n + ", handler=" + entityDataHandler + "}";
    }

    public static final class Builder {
        final EntityRewriter<?, ?> rewriter;
        EntityType type;
        EntityDataType dataType;
        int index = -1;
        boolean filterFamily;
        EntityDataHandler handler;

        public Builder(EntityRewriter<?, ?> rewriter) {
            this.rewriter = rewriter;
        }

        public Builder dataType(EntityDataType dataType) {
            Preconditions.checkArgument((this.dataType == null ? 1 : 0) != 0);
            this.dataType = dataType;
            return this;
        }

        public Builder type(EntityType type) {
            Preconditions.checkArgument((this.type == null ? 1 : 0) != 0);
            this.type = type;
            this.filterFamily = true;
            return this;
        }

        public Builder exactType(EntityType type) {
            Preconditions.checkArgument((this.type == null ? 1 : 0) != 0);
            this.type = type;
            this.filterFamily = false;
            return this;
        }

        public Builder index(int index2) {
            Preconditions.checkArgument((this.index == -1 ? 1 : 0) != 0);
            this.index = index2;
            return this;
        }

        public Builder handlerNoRegister(EntityDataHandler handler) {
            Preconditions.checkArgument((this.handler == null ? 1 : 0) != 0);
            this.handler = handler;
            return this;
        }

        public void handler(EntityDataHandler handler) {
            Preconditions.checkArgument((this.handler == null ? 1 : 0) != 0);
            this.handler = handler;
            this.register();
        }

        public void mapDataType(Int2ObjectFunction<EntityDataType> updateFunction) {
            this.handler((event, data) -> {
                EntityDataType mappedType = (EntityDataType)updateFunction.apply(data.dataType().typeId());
                if (mappedType != null) {
                    data.setDataType(mappedType);
                } else {
                    event.cancel();
                }
            });
        }

        public void cancel(int index2) {
            this.index = index2;
            this.handler((event, data) -> event.cancel());
        }

        public void toIndex(int newIndex) {
            Preconditions.checkArgument((this.index != -1 ? 1 : 0) != 0);
            this.handler((event, data) -> event.setIndex(newIndex));
        }

        public void addIndex(int index2) {
            Preconditions.checkArgument((this.index == -1 ? 1 : 0) != 0);
            this.handler((event, data) -> {
                if (event.index() >= index2) {
                    event.setIndex(event.index() + 1);
                }
            });
        }

        public void removeIndex(int index2) {
            Preconditions.checkArgument((this.index == -1 ? 1 : 0) != 0);
            this.handler((event, data) -> {
                int dataIndex = event.index();
                if (dataIndex == index2) {
                    event.cancel();
                } else if (dataIndex > index2) {
                    event.setIndex(dataIndex - 1);
                }
            });
        }

        public void register() {
            this.rewriter.registerFilter(this.build());
        }

        public EntityDataFilter build() {
            return new EntityDataFilter(this.type, this.filterFamily, this.dataType, this.index, this.handler);
        }
    }
}

