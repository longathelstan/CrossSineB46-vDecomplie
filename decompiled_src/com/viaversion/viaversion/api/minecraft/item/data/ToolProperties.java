/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.item.data.ToolRule;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class ToolProperties {
    final ToolRule[] rules;
    final float defaultMiningSpeed;
    final int damagePerBlock;
    public static final Type<ToolProperties> TYPE = new Type<ToolProperties>(ToolProperties.class){

        @Override
        public ToolProperties read(ByteBuf buffer) {
            ToolRule[] rules = (ToolRule[])ToolRule.ARRAY_TYPE.read(buffer);
            float defaultMiningSpeed = buffer.readFloat();
            int damagePerBlock = Types.VAR_INT.readPrimitive(buffer);
            return new ToolProperties(rules, defaultMiningSpeed, damagePerBlock);
        }

        @Override
        public void write(ByteBuf buffer, ToolProperties value) {
            ToolRule.ARRAY_TYPE.write(buffer, value.rules());
            buffer.writeFloat(value.defaultMiningSpeed());
            Types.VAR_INT.writePrimitive(buffer, value.damagePerBlock());
        }
    };

    public ToolProperties(ToolRule[] rules, float defaultMiningSpeed, int damagePerBlock) {
        this.rules = rules;
        this.defaultMiningSpeed = defaultMiningSpeed;
        this.damagePerBlock = damagePerBlock;
    }

    public ToolProperties rewrite(Int2IntFunction blockIdRewriter) {
        ToolRule[] rules = new ToolRule[this.rules.length];
        for (int i = 0; i < rules.length; ++i) {
            rules[i] = this.rules[i].rewrite(blockIdRewriter);
        }
        return new ToolProperties(rules, this.defaultMiningSpeed, this.damagePerBlock);
    }

    public ToolRule[] rules() {
        return this.rules;
    }

    public float defaultMiningSpeed() {
        return this.defaultMiningSpeed;
    }

    public int damagePerBlock() {
        return this.damagePerBlock;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ToolProperties)) {
            return false;
        }
        ToolProperties toolProperties = (ToolProperties)object;
        return Objects.equals(this.rules, toolProperties.rules) && Float.compare(this.defaultMiningSpeed, toolProperties.defaultMiningSpeed) == 0 && this.damagePerBlock == toolProperties.damagePerBlock;
    }

    public int hashCode() {
        return ((0 * 31 + Objects.hashCode(this.rules)) * 31 + Float.hashCode(this.defaultMiningSpeed)) * 31 + Integer.hashCode(this.damagePerBlock);
    }

    public String toString() {
        return String.format("%s[rules=%s, defaultMiningSpeed=%s, damagePerBlock=%s]", this.getClass().getSimpleName(), Objects.toString(this.rules), Float.toString(this.defaultMiningSpeed), Integer.toString(this.damagePerBlock));
    }
}

