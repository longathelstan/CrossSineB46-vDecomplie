/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.entitydata.types;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.minecraft.entitydata.types.AbstractEntityDataTypes;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;

public final class EntityDataTypes1_20_2
extends AbstractEntityDataTypes {
    public final EntityDataType byteType = this.add(0, Types.BYTE);
    public final EntityDataType varIntType = this.add(1, Types.VAR_INT);
    public final EntityDataType longType = this.add(2, Types.VAR_LONG);
    public final EntityDataType floatType = this.add(3, Types.FLOAT);
    public final EntityDataType stringType = this.add(4, Types.STRING);
    public final EntityDataType componentType = this.add(5, Types.COMPONENT);
    public final EntityDataType optionalComponentType = this.add(6, Types.OPTIONAL_COMPONENT);
    public final EntityDataType itemType = this.add(7, Types.ITEM1_20_2);
    public final EntityDataType booleanType = this.add(8, Types.BOOLEAN);
    public final EntityDataType rotationsType = this.add(9, Types.ROTATIONS);
    public final EntityDataType blockPositionType = this.add(10, Types.BLOCK_POSITION1_14);
    public final EntityDataType optionalBlockPositionType = this.add(11, Types.OPTIONAL_POSITION_1_14);
    public final EntityDataType directionType = this.add(12, Types.VAR_INT);
    public final EntityDataType optionalUUIDType = this.add(13, Types.OPTIONAL_UUID);
    public final EntityDataType blockStateType = this.add(14, Types.VAR_INT);
    public final EntityDataType optionalBlockStateType = this.add(15, Types.VAR_INT);
    public final EntityDataType compoundTagType = this.add(16, Types.COMPOUND_TAG);
    public final EntityDataType particleType;
    public final EntityDataType villagerDatatType = this.add(18, Types.VILLAGER_DATA);
    public final EntityDataType optionalVarIntType = this.add(19, Types.OPTIONAL_VAR_INT);
    public final EntityDataType poseType = this.add(20, Types.VAR_INT);
    public final EntityDataType catVariantType = this.add(21, Types.VAR_INT);
    public final EntityDataType frogVariantType = this.add(22, Types.VAR_INT);
    public final EntityDataType optionalGlobalPosition = this.add(23, Types.OPTIONAL_GLOBAL_POSITION);
    public final EntityDataType paintingVariantType = this.add(24, Types.VAR_INT);
    public final EntityDataType snifferState = this.add(25, Types.VAR_INT);
    public final EntityDataType vector3FType = this.add(26, Types.VECTOR3F);
    public final EntityDataType quaternionType = this.add(27, Types.QUATERNION);

    public EntityDataTypes1_20_2(ParticleType particleType) {
        super(28);
        this.particleType = this.add(17, particleType);
    }
}

