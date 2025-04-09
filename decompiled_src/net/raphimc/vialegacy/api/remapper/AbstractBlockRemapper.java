/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.api.remapper;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.util.IdAndData;

public abstract class AbstractBlockRemapper {
    private final Int2IntMap REPLACEMENTS = new Int2IntOpenHashMap();

    protected void registerReplacement(int from, int to) {
        this.REPLACEMENTS.put(from, to);
    }

    protected void registerReplacement(IdAndData from, IdAndData to) {
        this.REPLACEMENTS.put(from.toRawData(), to.toRawData());
    }

    public void remapChunk(Chunk chunk) {
        for (int i = 0; i < chunk.getSections().length; ++i) {
            ChunkSection section = chunk.getSections()[i];
            if (section == null) continue;
            DataPalette palette = section.palette(PaletteType.BLOCKS);
            for (Int2IntMap.Entry entry : this.REPLACEMENTS.int2IntEntrySet()) {
                palette.replaceId(entry.getIntKey(), entry.getIntValue());
            }
        }
    }

    public void remapBlockChangeRecords(BlockChangeRecord[] blockChangeRecords) {
        for (BlockChangeRecord record : blockChangeRecords) {
            int id = record.getBlockId();
            if (!this.REPLACEMENTS.containsKey(id)) continue;
            record.setBlockId(this.REPLACEMENTS.get(id));
        }
    }

    public void remapBlock(IdAndData block) {
        if (this.REPLACEMENTS.containsKey(block.toRawData())) {
            int replacement = this.REPLACEMENTS.get(block.toRawData());
            block.setId(replacement >> 4);
            block.setData(replacement & 0xF);
        }
    }

    public int remapBlock(int id) {
        return this.REPLACEMENTS.getOrDefault(id, id);
    }
}

