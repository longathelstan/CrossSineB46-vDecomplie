/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.model;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import java.util.ArrayList;
import java.util.List;

public class NonFullChunk
extends BaseChunk {
    private final BlockPosition startPos;
    private final BlockPosition endPos;

    public NonFullChunk(int x, int z, int bitmask, ChunkSection[] sections, BlockPosition startPos, BlockPosition endPos) {
        super(x, z, false, false, bitmask, sections, null, new CompoundTag(), new ArrayList<CompoundTag>());
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public BlockPosition getStartPos() {
        return this.startPos;
    }

    public BlockPosition getEndPos() {
        return this.endPos;
    }

    public List<BlockChangeRecord> asBlockChangeRecords() {
        ArrayList<BlockChangeRecord> blockChangeRecords = new ArrayList<BlockChangeRecord>();
        for (int y = this.startPos.y(); y < this.endPos.y(); ++y) {
            ChunkSection section = this.getSections()[y >> 4];
            for (int x = this.startPos.x(); x < this.endPos.x(); ++x) {
                for (int z = this.startPos.z(); z < this.endPos.z(); ++z) {
                    blockChangeRecords.add(new BlockChangeRecord1_8(x, y, z, section.palette(PaletteType.BLOCKS).idAt(x, y & 0xF, z)));
                }
            }
        }
        return blockChangeRecords;
    }
}

