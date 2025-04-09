/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.minecraft.item.data.Filterable;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class FilterableComponent
extends Filterable<Tag> {
    public static final Type<FilterableComponent> TYPE = new Filterable.FilterableType<Tag, FilterableComponent>(Types.TAG, Types.OPTIONAL_TAG, FilterableComponent.class){

        @Override
        protected FilterableComponent create(Tag raw, Tag filtered) {
            return new FilterableComponent(raw, filtered);
        }
    };
    public static final Type<FilterableComponent[]> ARRAY_TYPE = new ArrayType<FilterableComponent>(TYPE);

    public FilterableComponent(Tag raw, @Nullable Tag filtered) {
        super(raw, filtered);
    }
}

