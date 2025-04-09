/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap;
import io.netty.buffer.ByteBuf;
import java.util.Map;
import java.util.Objects;

public final class BlockStateProperties {
    final Map<String, String> properties;
    public static final Type<BlockStateProperties> TYPE = new Type<BlockStateProperties>(BlockStateProperties.class){

        @Override
        public BlockStateProperties read(ByteBuf buffer) {
            int size = Types.VAR_INT.readPrimitive(buffer);
            Object2ObjectOpenHashMap<String, String> properties = new Object2ObjectOpenHashMap<String, String>(size);
            for (int i = 0; i < size; ++i) {
                properties.put((String)Types.STRING.read(buffer), (String)Types.STRING.read(buffer));
            }
            return new BlockStateProperties(properties);
        }

        @Override
        public void write(ByteBuf buffer, BlockStateProperties value) {
            Types.VAR_INT.writePrimitive(buffer, value.properties.size());
            for (Map.Entry<String, String> entry : value.properties.entrySet()) {
                Types.STRING.write(buffer, entry.getKey());
                Types.STRING.write(buffer, entry.getValue());
            }
        }
    };

    public BlockStateProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, String> properties() {
        return this.properties;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BlockStateProperties)) {
            return false;
        }
        BlockStateProperties blockStateProperties = (BlockStateProperties)object;
        return Objects.equals(this.properties, blockStateProperties.properties);
    }

    public int hashCode() {
        return 0 * 31 + Objects.hashCode(this.properties);
    }

    public String toString() {
        return String.format("%s[properties=%s]", this.getClass().getSimpleName(), Objects.toString(this.properties));
    }
}

