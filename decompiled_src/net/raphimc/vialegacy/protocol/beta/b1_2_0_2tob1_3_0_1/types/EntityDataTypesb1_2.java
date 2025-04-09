/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.types;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.types.Typesb1_7_0_3;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types.Types1_3_1;

public enum EntityDataTypesb1_2 implements EntityDataType
{
    BYTE(0, Types.BYTE),
    SHORT(1, Types.SHORT),
    INT(2, Types.INT),
    FLOAT(3, Types.FLOAT),
    STRING(4, Typesb1_7_0_3.STRING),
    ITEM(5, Types1_3_1.NBTLESS_ITEM);

    private final int typeID;
    private final Type<?> type;

    private EntityDataTypesb1_2(int typeID, Type<?> type) {
        this.typeID = typeID;
        this.type = type;
    }

    public static EntityDataTypesb1_2 byId(int id) {
        return EntityDataTypesb1_2.values()[id];
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

