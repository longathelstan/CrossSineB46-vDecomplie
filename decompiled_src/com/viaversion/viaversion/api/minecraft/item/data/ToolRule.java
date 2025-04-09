/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.HolderSet;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ToolRule {
    final HolderSet blocks;
    final @Nullable Float speed;
    final @Nullable Boolean correctForDrops;
    public static final Type<ToolRule> TYPE = new Type<ToolRule>(ToolRule.class){

        @Override
        public ToolRule read(ByteBuf buffer) {
            HolderSet blocks = (HolderSet)Types.HOLDER_SET.read(buffer);
            Float speed = (Float)Types.OPTIONAL_FLOAT.read(buffer);
            Boolean correctForDrops = (Boolean)Types.OPTIONAL_BOOLEAN.read(buffer);
            return new ToolRule(blocks, speed, correctForDrops);
        }

        @Override
        public void write(ByteBuf buffer, ToolRule value) {
            Types.HOLDER_SET.write(buffer, value.blocks);
            Types.OPTIONAL_FLOAT.write(buffer, value.speed);
            Types.OPTIONAL_BOOLEAN.write(buffer, value.correctForDrops);
        }
    };
    public static final Type<ToolRule[]> ARRAY_TYPE = new ArrayType<ToolRule>(TYPE);

    public ToolRule(HolderSet blocks, @Nullable Float speed, @Nullable Boolean correctForDrops) {
        this.blocks = blocks;
        this.speed = speed;
        this.correctForDrops = correctForDrops;
    }

    public ToolRule rewrite(Int2IntFunction blockIdRewriter) {
        return this.blocks.hasIds() ? new ToolRule(this.blocks.rewrite(blockIdRewriter), this.speed, this.correctForDrops) : this;
    }

    public HolderSet blocks() {
        return this.blocks;
    }

    public @Nullable Float speed() {
        return this.speed;
    }

    public @Nullable Boolean correctForDrops() {
        return this.correctForDrops;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ToolRule)) {
            return false;
        }
        ToolRule toolRule = (ToolRule)object;
        return Objects.equals(this.blocks, toolRule.blocks) && Objects.equals(this.speed, toolRule.speed) && Objects.equals(this.correctForDrops, toolRule.correctForDrops);
    }

    public int hashCode() {
        return ((0 * 31 + Objects.hashCode(this.blocks)) * 31 + Objects.hashCode(this.speed)) * 31 + Objects.hashCode(this.correctForDrops);
    }

    public String toString() {
        return String.format("%s[blocks=%s, speed=%s, correctForDrops=%s]", this.getClass().getSimpleName(), Objects.toString(this.blocks), Objects.toString(this.speed), Objects.toString(this.correctForDrops));
    }
}

