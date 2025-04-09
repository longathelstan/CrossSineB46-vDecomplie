/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.minecraft.VillagerData;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class VillagerDataType
extends Type<VillagerData> {
    public VillagerDataType() {
        super(VillagerData.class);
    }

    @Override
    public VillagerData read(ByteBuf buffer) {
        return new VillagerData(Types.VAR_INT.readPrimitive(buffer), Types.VAR_INT.readPrimitive(buffer), Types.VAR_INT.readPrimitive(buffer));
    }

    @Override
    public void write(ByteBuf buffer, VillagerData object) {
        Types.VAR_INT.writePrimitive(buffer, object.type());
        Types.VAR_INT.writePrimitive(buffer, object.profession());
        Types.VAR_INT.writePrimitive(buffer, object.level());
    }
}

