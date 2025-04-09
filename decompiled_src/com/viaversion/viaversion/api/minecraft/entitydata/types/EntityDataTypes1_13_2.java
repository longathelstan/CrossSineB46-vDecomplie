/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.entitydata.types;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.minecraft.entitydata.types.AbstractEntityDataTypes;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;

public final class EntityDataTypes1_13_2
extends AbstractEntityDataTypes {
    public final EntityDataType byteType = this.add(0, Types.BYTE);
    public final EntityDataType varIntType = this.add(1, Types.VAR_INT);
    public final EntityDataType floatType = this.add(2, Types.FLOAT);
    public final EntityDataType stringType = this.add(3, Types.STRING);
    public final EntityDataType componentType = this.add(4, Types.COMPONENT);
    public final EntityDataType optionalComponentType = this.add(5, Types.OPTIONAL_COMPONENT);
    public final EntityDataType itemType = this.add(6, Types.ITEM1_13_2);
    public final EntityDataType booleanType = this.add(7, Types.BOOLEAN);
    public final EntityDataType rotationsType = this.add(8, Types.ROTATIONS);
    public final EntityDataType blockPositionType = this.add(9, Types.BLOCK_POSITION1_8);
    public final EntityDataType optionalBlockPositionType = this.add(10, Types.OPTIONAL_POSITION1_8);
    public final EntityDataType directionType = this.add(11, Types.VAR_INT);
    public final EntityDataType optionalUUIDType = this.add(12, Types.OPTIONAL_UUID);
    public final EntityDataType optionalBlockStateType = this.add(13, Types.VAR_INT);
    public final EntityDataType compoundTagType = this.add(14, Types.NAMED_COMPOUND_TAG);
    public final EntityDataType particleType;

    public EntityDataTypes1_13_2(ParticleType particleType) {
        super(16);
        this.particleType = this.add(15, particleType);
    }
}

