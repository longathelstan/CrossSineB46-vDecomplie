/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;
import java.util.List;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.EntityDataType;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.StringType;

public class Types1_6_4 {
    public static final Type<String> STRING = new StringType();
    public static final Type<EntityData> ENTITY_DATA = new EntityDataType();
    public static final Type<List<EntityData>> ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
}

