/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.types;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.types.ChunkType;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.types.ItemType;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.ItemArrayType;

public class Types1_2_4 {
    public static final Type<Item> NBT_ITEM = new ItemType();
    public static final Type<Item[]> NBT_ITEM_ARRAY = new ItemArrayType<Item>(NBT_ITEM);
    public static final Type<Chunk> CHUNK = new ChunkType();
}

