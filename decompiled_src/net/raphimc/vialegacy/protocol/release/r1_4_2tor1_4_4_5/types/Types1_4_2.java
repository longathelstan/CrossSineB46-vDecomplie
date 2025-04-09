/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;
import java.util.List;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.EntityDataType;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.NbtLessItemType;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.UnsignedByteByteArrayType;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.ItemArrayType;

public class Types1_4_2 {
    public static final Type<byte[]> UNSIGNED_BYTE_BYTE_ARRAY = new UnsignedByteByteArrayType();
    public static final Type<Item> NBTLESS_ITEM = new NbtLessItemType();
    public static final Type<Item[]> NBTLESS_ITEM_ARRAY = new ItemArrayType<Item>(NBTLESS_ITEM);
    public static final Type<EntityData> ENTITY_DATA = new EntityDataType();
    public static final Type<List<EntityData>> ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
}

