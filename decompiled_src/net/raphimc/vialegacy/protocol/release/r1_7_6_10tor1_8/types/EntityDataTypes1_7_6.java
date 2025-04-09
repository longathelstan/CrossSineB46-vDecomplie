/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public enum EntityDataTypes1_7_6 implements EntityDataType
{
    BYTE(0, Types.BYTE),
    SHORT(1, Types.SHORT),
    INT(2, Types.INT),
    FLOAT(3, Types.FLOAT),
    STRING(4, Types.STRING),
    ITEM(5, Types1_7_6.ITEM),
    BLOCK_POSITION(6, Types.VECTOR);

    private final int typeID;
    private final Type<?> type;

    private EntityDataTypes1_7_6(int typeID, Type<?> type) {
        this.typeID = typeID;
        this.type = type;
    }

    public static EntityDataTypes1_7_6 byId(int id) {
        return EntityDataTypes1_7_6.values()[id];
    }

    @Override
    public int typeId() {
        return this.typeID;
    }

    @Override
    public Type<?> type() {
        return this.type;
    }
}

