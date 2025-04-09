/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.math;

import com.viaversion.viaversion.api.minecraft.EulerAngle;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class EulerAngleType
extends Type<EulerAngle> {
    public EulerAngleType() {
        super(EulerAngle.class);
    }

    @Override
    public EulerAngle read(ByteBuf buffer) {
        float x = Types.FLOAT.readPrimitive(buffer);
        float y = Types.FLOAT.readPrimitive(buffer);
        float z = Types.FLOAT.readPrimitive(buffer);
        return new EulerAngle(x, y, z);
    }

    @Override
    public void write(ByteBuf buffer, EulerAngle object) {
        Types.FLOAT.writePrimitive(buffer, object.x());
        Types.FLOAT.writePrimitive(buffer, object.y());
        Types.FLOAT.writePrimitive(buffer, object.z());
    }
}

