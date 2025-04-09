/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.api.remapper;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.util.IdAndData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.raphimc.vialegacy.api.model.ChunkCoord;

public abstract class AbstractChunkTracker
implements StorableObject {
    private final Map<ChunkCoord, Chunk> chunks = new HashMap<ChunkCoord, Chunk>();
    private final IntSet toTrack = new IntOpenHashSet();
    private final boolean trackAll;
    private final Int2IntMap replacements = new Int2IntOpenHashMap();

    public AbstractChunkTracker(int ... toTrack) {
        for (int trackedBlock : toTrack) {
            this.toTrack.add(trackedBlock);
        }
        this.trackAll = this.toTrack.contains(0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void trackAndRemap(Chunk chunk) {
        DataPalette palette;
        ChunkSection section;
        int i;
        ChunkCoord chunkCoord = new ChunkCoord(chunk.getX(), chunk.getZ());
        if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
            this.chunks.remove(chunkCoord);
            return;
        }
        BaseChunk copyChunk = new BaseChunk(chunk.getX(), chunk.getZ(), true, false, 65535, new ChunkSection[chunk.getSections().length], null, new ArrayList<CompoundTag>());
        if (!chunk.isFullChunk()) {
            if (!this.chunks.containsKey(chunkCoord)) return;
            copyChunk.setSections(this.chunks.get(chunkCoord).getSections());
        } else {
            this.chunks.put(chunkCoord, copyChunk);
        }
        if (!this.toTrack.isEmpty()) {
            for (i = 0; i < chunk.getSections().length; ++i) {
                section = chunk.getSections()[i];
                if (section == null) continue;
                copyChunk.getSections()[i] = null;
                palette = section.palette(PaletteType.BLOCKS);
                if (!this.hasRemappableBlocks(palette)) continue;
                ChunkSectionImpl chunkSectionImpl = new ChunkSectionImpl(false);
                copyChunk.getSections()[i] = chunkSectionImpl;
                ChunkSectionImpl copySection = chunkSectionImpl;
                DataPalette copyPalette = copySection.palette(PaletteType.BLOCKS);
                copyPalette.addId(0);
                for (int x = 0; x < 16; ++x) {
                    for (int y = 0; y < 16; ++y) {
                        for (int z = 0; z < 16; ++z) {
                            int flatBlock = palette.idAt(x, y, z);
                            if (!this.trackAll && !this.toTrack.contains(flatBlock >> 4)) continue;
                            copyPalette.setIdAt(x, y, z, flatBlock);
                        }
                    }
                }
            }
        }
        for (i = 0; i < chunk.getSections().length; ++i) {
            section = chunk.getSections()[i];
            if (section == null) continue;
            palette = section.palette(PaletteType.BLOCKS);
            for (Int2IntMap.Entry entry : this.replacements.int2IntEntrySet()) {
                palette.replaceId(entry.getIntKey(), entry.getIntValue());
            }
            if (!this.hasRemappableBlocks(palette)) continue;
            for (int x = 0; x < 16; ++x) {
                for (int y = 0; y < 16; ++y) {
                    for (int z = 0; z < 16; ++z) {
                        int flatBlock = palette.idAt(x, y, z);
                        if (!this.trackAll && !this.toTrack.contains(flatBlock >> 4)) continue;
                        IdAndData block = IdAndData.fromRawData(flatBlock);
                        this.remapBlock(block, x + (chunk.getX() << 4), y + i * 16, z + (chunk.getZ() << 4));
                        int newFlatBlock = block.toRawData();
                        if (newFlatBlock == flatBlock) continue;
                        palette.setIdAt(x, y, z, newFlatBlock);
                    }
                }
            }
            this.postRemap(palette);
        }
    }

    public void trackAndRemap(BlockPosition position, IdAndData block) {
        int x = position.x();
        int y = position.y();
        int z = position.z();
        Chunk chunk = this.chunks.get(new ChunkCoord(x >> 4, z >> 4));
        if (chunk != null && y >= 0 && y >> 4 < chunk.getSections().length) {
            ChunkSection section = chunk.getSections()[y >> 4];
            if (this.trackAll || this.toTrack.contains(block.getId())) {
                if (section == null) {
                    ChunkSectionImpl chunkSectionImpl = new ChunkSectionImpl(false);
                    chunk.getSections()[y >> 4] = chunkSectionImpl;
                    section = chunkSectionImpl;
                    section.palette(PaletteType.BLOCKS).addId(0);
                }
                section.palette(PaletteType.BLOCKS).setIdAt(x & 0xF, y & 0xF, z & 0xF, block.toRawData());
            } else if (section != null) {
                section.palette(PaletteType.BLOCKS).setIdAt(x & 0xF, y & 0xF, z & 0xF, 0);
            }
        }
        if (this.replacements.containsKey(block.toRawData())) {
            int newFlatBlock = this.replacements.get(block.toRawData());
            block.setId(newFlatBlock >> 4);
            block.setData(newFlatBlock & 0xF);
        }
        if (this.trackAll || this.toTrack.contains(block.getId())) {
            this.remapBlock(block, x, y, z);
        }
    }

    public void remapBlockParticle(IdAndData block) {
        if (this.replacements.containsKey(block.toRawData())) {
            int newFlatBlock = this.replacements.get(block.toRawData());
            block.setId(newFlatBlock >> 4);
            block.setData(newFlatBlock & 0xF);
        }
        if (this.trackAll || this.toTrack.contains(block.getId())) {
            this.remapBlock(block, 0, -16, 0);
        }
    }

    public void clear() {
        this.chunks.clear();
    }

    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        return this.chunks.containsKey(new ChunkCoord(chunkX, chunkZ));
    }

    public IdAndData getBlockNotNull(BlockPosition position) {
        return this.getBlockNotNull(position.x(), position.y(), position.z());
    }

    public IdAndData getBlockNotNull(int x, int y, int z) {
        IdAndData block = this.getBlock(x, y, z);
        if (block == null) {
            block = new IdAndData(0, 0);
        }
        return block;
    }

    public IdAndData getBlock(BlockPosition position) {
        return this.getBlock(position.x(), position.y(), position.z());
    }

    public IdAndData getBlock(int x, int y, int z) {
        Chunk chunk = this.chunks.get(new ChunkCoord(x >> 4, z >> 4));
        if (chunk != null) {
            if (y < 0 || y >> 4 > chunk.getSections().length - 1) {
                return null;
            }
            ChunkSection section = chunk.getSections()[y >> 4];
            if (section != null) {
                return IdAndData.fromRawData(section.palette(PaletteType.BLOCKS).idAt(x & 0xF, y & 0xF, z & 0xF));
            }
        }
        return null;
    }

    protected void registerReplacement(IdAndData from, IdAndData to) {
        this.replacements.put(from.toRawData(), to.toRawData());
    }

    protected void remapBlock(IdAndData block, int x, int y, int z) {
    }

    protected void postRemap(DataPalette palette) {
    }

    private boolean hasRemappableBlocks(DataPalette palette) {
        if (this.trackAll) {
            return true;
        }
        if (this.toTrack.isEmpty()) {
            return false;
        }
        boolean hasTrackableBlocks = false;
        for (int i = 0; i < palette.size(); ++i) {
            if (!this.toTrack.contains(palette.idByIndex(i) >> 4)) continue;
            hasTrackableBlocks = true;
        }
        return hasTrackableBlocks;
    }
}

