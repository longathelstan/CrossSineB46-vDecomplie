/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.entitydata.types;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.minecraft.entitydata.types.AbstractEntityDataTypes;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;

public final class EntityDataTypes1_19_3
extends AbstractEntityDataTypes {
    public final EntityDataType byteType = this.add(0, Types.BYTE);
    public final EntityDataType varIntType = this.add(1, Types.VAR_INT);
    public final EntityDataType longType = this.add(2, Types.VAR_LONG);
    public final EntityDataType floatType = this.add(3, Types.FLOAT);
    public final EntityDataType stringType = this.add(4, Types.STRING);
    public final EntityDataType componentType = this.add(5, Types.COMPONENT);
    public final EntityDataType optionalComponentType = this.add(6, Types.OPTIONAL_COMPONENT);
    public final EntityDataType itemType = this.add(7, Types.ITEM1_13_2);
    public final EntityDataType booleanType = this.add(8, Types.BOOLEAN);
    public final EntityDataType rotationsType = this.add(9, Types.ROTATIONS);
    public final EntityDataType blockPositionType = this.add(10, Types.BLOCK_POSITION1_14);
    public final EntityDataType optionalBlockPositionType = this.add(11, Types.OPTIONAL_POSITION_1_14);
    public final EntityDataType directionType = this.add(12, Types.VAR_INT);
    public final EntityDataType optionalUUIDType = this.add(13, Types.OPTIONAL_UUID);
    public final EntityDataType optionalBlockStateType = this.add(14, Types.VAR_INT);
    public final EntityDataType compoundTagType = this.add(15, Types.NAMED_COMPOUND_TAG);
    public final EntityDataType particleType;
    public final EntityDataType villagerDatatType = this.add(17, Types.VILLAGER_DATA);
    public final EntityDataType optionalVarIntType = this.add(18, Types.OPTIONAL_VAR_INT);
    public final EntityDataType poseType = this.add(19, Types.VAR_INT);
    public final EntityDataType catVariantType = this.add(20, Types.VAR_INT);
    public final EntityDataType frogVariantType = this.add(21, Types.VAR_INT);
    public final EntityDataType optionalGlobalPosition = this.add(22, Types.OPTIONAL_GLOBAL_POSITION);
    public final EntityDataType paintingVariantType = this.add(23, Types.VAR_INT);

    public EntityDataTypes1_19_3(ParticleType particleType) {
        super(24);
        this.particleType = this.add(16, particleType);
    }
}

