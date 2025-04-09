/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types;

import com.viaversion.viaversion.api.type.types.entitydata.OldEntityDataType;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.EntityDataTypes1_4_2;

public class EntityDataType
extends OldEntityDataType {
    @Override
    protected com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType getType(int index2) {
        return EntityDataTypes1_4_2.byId(index2);
    }
}

