/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_4_0_1tob1_5_0_2.types;

import com.viaversion.viaversion.api.type.types.entitydata.OldEntityDataType;
import net.raphimc.vialegacy.protocol.beta.b1_4_0_1tob1_5_0_2.types.EntityDataTypesb1_4;

public class EntityDataType
extends OldEntityDataType {
    @Override
    protected com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType getType(int index2) {
        return EntityDataTypesb1_4.byId(index2);
    }
}

