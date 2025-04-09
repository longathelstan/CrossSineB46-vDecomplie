/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.HolderSet;
import com.viaversion.viaversion.api.minecraft.item.data.StatePropertyMatcher;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BlockPredicate {
    final @Nullable HolderSet holderSet;
    final StatePropertyMatcher @Nullable [] propertyMatchers;
    final @Nullable CompoundTag tag;
    public static final Type<BlockPredicate> TYPE = new Type<BlockPredicate>(BlockPredicate.class){

        @Override
        public BlockPredicate read(ByteBuf buffer) {
            HolderSet holders = (HolderSet)Types.OPTIONAL_HOLDER_SET.read(buffer);
            StatePropertyMatcher[] propertyMatchers = buffer.readBoolean() ? (StatePropertyMatcher[])StatePropertyMatcher.ARRAY_TYPE.read(buffer) : null;
            CompoundTag tag = (CompoundTag)Types.OPTIONAL_COMPOUND_TAG.read(buffer);
            return new BlockPredicate(holders, propertyMatchers, tag);
        }

        @Override
        public void write(ByteBuf buffer, BlockPredicate value) {
            Types.OPTIONAL_HOLDER_SET.write(buffer, value.holderSet);
            buffer.writeBoolean(value.propertyMatchers != null);
            if (value.propertyMatchers != null) {
                StatePropertyMatcher.ARRAY_TYPE.write(buffer, value.propertyMatchers);
            }
            Types.OPTIONAL_COMPOUND_TAG.write(buffer, value.tag);
        }
    };
    public static final Type<BlockPredicate[]> ARRAY_TYPE = new ArrayType<BlockPredicate>(TYPE);

    public BlockPredicate(@Nullable HolderSet holderSet, StatePropertyMatcher @Nullable [] propertyMatchers, @Nullable CompoundTag tag) {
        this.holderSet = holderSet;
        this.propertyMatchers = propertyMatchers;
        this.tag = tag;
    }

    public BlockPredicate rewrite(Int2IntFunction blockIdRewriter) {
        if (this.holderSet == null || this.holderSet.hasTagKey()) {
            return this;
        }
        HolderSet updatedHolders = this.holderSet.rewrite(blockIdRewriter);
        return new BlockPredicate(updatedHolders, this.propertyMatchers, this.tag);
    }

    public @Nullable HolderSet holderSet() {
        return this.holderSet;
    }

    public StatePropertyMatcher @Nullable [] propertyMatchers() {
        return this.propertyMatchers;
    }

    public @Nullable CompoundTag tag() {
        return this.tag;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BlockPredicate)) {
            return false;
        }
        BlockPredicate blockPredicate = (BlockPredicate)object;
        return Objects.equals(this.holderSet, blockPredicate.holderSet) && Objects.equals(this.propertyMatchers, blockPredicate.propertyMatchers) && Objects.equals(this.tag, blockPredicate.tag);
    }

    public int hashCode() {
        return ((0 * 31 + Objects.hashCode(this.holderSet)) * 31 + Objects.hashCode(this.propertyMatchers)) * 31 + Objects.hashCode(this.tag);
    }

    public String toString() {
        return String.format("%s[holderSet=%s, propertyMatchers=%s, tag=%s]", this.getClass().getSimpleName(), Objects.toString(this.holderSet), Objects.toString(this.propertyMatchers), Objects.toString(this.tag));
    }
}

