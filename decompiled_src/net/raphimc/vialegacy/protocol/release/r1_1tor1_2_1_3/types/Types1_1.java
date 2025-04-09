/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.types;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.type.Type;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.types.BlockChangeRecordArrayType;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.types.ChunkType;

public class Types1_1 {
    public static Type<BlockChangeRecord[]> BLOCK_CHANGE_RECORD_ARRAY = new BlockChangeRecordArrayType();
    public static Type<Chunk> CHUNK = new ChunkType();
}

