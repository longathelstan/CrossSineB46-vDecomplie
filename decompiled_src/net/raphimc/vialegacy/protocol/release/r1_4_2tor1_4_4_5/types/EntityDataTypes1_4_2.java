/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.Types1_4_2;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;

public enum EntityDataTypes1_4_2 implements EntityDataType
{
    BYTE(0, Types.BYTE),
    SHORT(1, Types.SHORT),
    INT(2, Types.INT),
    FLOAT(3, Types.FLOAT),
    STRING(4, Types1_6_4.STRING),
    ITEM(5, Types1_4_2.NBTLESS_ITEM),
    BLOCK_POSITION(6, Types.VECTOR);

    private final int typeID;
    private final Type<?> type;

    private EntityDataTypes1_4_2(int typeID, Type<?> type) {
        this.typeID = typeID;
        this.type = type;
    }

    public static EntityDataTypes1_4_2 byId(int id) {
        return EntityDataTypes1_4_2.values()[id];
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

