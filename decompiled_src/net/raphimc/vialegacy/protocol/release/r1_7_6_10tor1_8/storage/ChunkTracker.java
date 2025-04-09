/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.storage;

import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.util.IdAndData;
import net.raphimc.vialegacy.api.data.BlockList1_6;
import net.raphimc.vialegacy.api.remapper.AbstractChunkTracker;

public class ChunkTracker
extends AbstractChunkTracker {
    public ChunkTracker() {
        super(BlockList1_6.obsidian.blockId(), BlockList1_6.portal.blockId());
        int i;
        for (i = 9; i < 16; ++i) {
            this.registerReplacement(new IdAndData(175, i), new IdAndData(175, 0));
        }
        for (i = 1; i < 16; ++i) {
            this.registerReplacement(new IdAndData(0, i), new IdAndData(0, 0));
        }
        for (i = 1; i < 7; ++i) {
            this.registerReplacement(new IdAndData(BlockList1_6.stone.blockId(), i), new IdAndData(BlockList1_6.stone.blockId(), 0));
        }
        this.registerInvalidDirectionReplacements(BlockList1_6.ladder.blockId(), new IdAndData(BlockList1_6.planks.blockId(), 0));
        this.registerInvalidDirectionReplacements(BlockList1_6.furnaceBurning.blockId(), new IdAndData(BlockList1_6.furnaceBurning.blockId(), 2));
        this.registerReplacement(new IdAndData(BlockList1_6.chest.blockId(), 0), new IdAndData(BlockList1_6.chest.blockId(), 3));
    }

    @Override
    protected void remapBlock(IdAndData block, int x, int y, int z) {
        if (block.getId() == BlockList1_6.portal.blockId() && block.getData() == 0) {
            if (this.getBlockNotNull(x - 1, y, z).getId() == BlockList1_6.obsidian.blockId() || this.getBlockNotNull(x + 1, y, z).getId() == BlockList1_6.obsidian.blockId()) {
                block.setData(1);
            } else {
                block.setData(2);
            }
        }
    }

    @Override
    protected void postRemap(DataPalette palette) {
        palette.replaceId(BlockList1_6.portal.blockId() << 4, 0);
    }

    private void registerInvalidDirectionReplacements(int blockId, IdAndData replacement) {
        int i;
        for (i = 0; i < 2; ++i) {
            this.registerReplacement(new IdAndData(blockId, i), replacement);
        }
        for (i = 6; i < 16; ++i) {
            this.registerReplacement(new IdAndData(blockId, i), replacement);
        }
    }
}

