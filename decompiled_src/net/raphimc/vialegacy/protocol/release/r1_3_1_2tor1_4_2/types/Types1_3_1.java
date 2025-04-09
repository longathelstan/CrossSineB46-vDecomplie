/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;
import java.util.List;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types.EntityDataType;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types.NbtLessItemType;

public class Types1_3_1 {
    public static final Type<Item> NBTLESS_ITEM = new NbtLessItemType();
    public static final Type<EntityData> ENTITY_DATA = new EntityDataType();
    public static final Type<List<EntityData>> ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
}

