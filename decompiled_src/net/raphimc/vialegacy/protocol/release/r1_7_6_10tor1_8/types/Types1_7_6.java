/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;
import java.util.List;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.BlockChangeRecordArrayType;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.BlockPositionVarYType;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.BulkChunkType;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.ChunkType;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.EntityDataType;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.IntArrayType;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.ItemArrayType;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.ItemType;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.NBTType;

public class Types1_7_6 {
    public static final Type<int[]> INT_ARRAY = new IntArrayType();
    public static final Type<CompoundTag> NBT = new NBTType();
    public static final Type<Item> ITEM = new ItemType();
    public static final Type<Item[]> ITEM_ARRAY = new ItemArrayType<Item>(ITEM);
    public static final Type<EntityData> ENTITY_DATA = new EntityDataType();
    public static final Type<List<EntityData>> ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
    public static final Type<BlockChangeRecord[]> BLOCK_CHANGE_RECORD_ARRAY = new BlockChangeRecordArrayType();
    public static final Type<BlockPosition> BLOCK_POSITION_BYTE = new BlockPositionVarYType<Byte>(Types.BYTE, i -> (byte)i);
    public static final Type<BlockPosition> BLOCK_POSITION_UBYTE = new BlockPositionVarYType<Short>(Types.UNSIGNED_BYTE, i -> (short)i);
    public static final Type<BlockPosition> BLOCK_POSITION_SHORT = new BlockPositionVarYType<Short>(Types.SHORT, i -> (short)i);
    public static final Type<BlockPosition> BLOCK_POSITION_INT = new BlockPositionVarYType<Integer>(Types.INT, i -> i);
    public static final Type<Chunk> CHUNK_WITH_SKYLIGHT = new ChunkType(true);
    public static final Type<Chunk> CHUNK_WITHOUT_SKYLIGHT = new ChunkType(false);
    public static final Type<Chunk[]> CHUNK_BULK = new BulkChunkType();

    public static Type<Chunk> getChunk(Environment dimension) {
        return dimension == Environment.NORMAL ? CHUNK_WITH_SKYLIGHT : CHUNK_WITHOUT_SKYLIGHT;
    }
}

