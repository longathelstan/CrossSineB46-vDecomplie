/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.math;

import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.GlobalBlockPosition;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class GlobalBlockPositionType
extends Type<GlobalBlockPosition> {
    public GlobalBlockPositionType() {
        super(GlobalBlockPosition.class);
    }

    @Override
    public GlobalBlockPosition read(ByteBuf buffer) {
        String dimension = (String)Types.STRING.read(buffer);
        return ((BlockPosition)Types.BLOCK_POSITION1_14.read(buffer)).withDimension(dimension);
    }

    @Override
    public void write(ByteBuf buffer, GlobalBlockPosition object) {
        Types.STRING.write(buffer, object.dimension());
        Types.BLOCK_POSITION1_14.write(buffer, object);
    }

    public static final class OptionalGlobalPositionType
    extends OptionalType<GlobalBlockPosition> {
        public OptionalGlobalPositionType() {
            super(Types.GLOBAL_POSITION);
        }
    }
}

