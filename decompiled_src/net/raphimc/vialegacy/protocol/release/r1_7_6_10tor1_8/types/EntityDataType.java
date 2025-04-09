/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types;

import com.viaversion.viaversion.api.type.types.entitydata.OldEntityDataType;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.EntityDataTypes1_7_6;

public class EntityDataType
extends OldEntityDataType {
    @Override
    protected com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType getType(int index2) {
        return EntityDataTypes1_7_6.byId(index2);
    }
}

