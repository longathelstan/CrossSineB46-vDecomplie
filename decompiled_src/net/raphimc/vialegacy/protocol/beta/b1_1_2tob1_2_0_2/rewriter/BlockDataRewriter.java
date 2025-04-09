/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.rewriter;

import com.viaversion.viaversion.util.IdAndData;
import net.raphimc.vialegacy.api.data.BlockList1_6;
import net.raphimc.vialegacy.api.remapper.AbstractBlockRemapper;

public class BlockDataRewriter
extends AbstractBlockRemapper {
    public BlockDataRewriter() {
        int i;
        for (i = 1; i < 16; ++i) {
            this.registerReplacement(new IdAndData(BlockList1_6.cobblestone.blockId(), i), new IdAndData(BlockList1_6.cobblestone.blockId(), 0));
        }
        for (i = 1; i < 16; ++i) {
            this.registerReplacement(new IdAndData(BlockList1_6.sand.blockId(), i), new IdAndData(BlockList1_6.sand.blockId(), 0));
        }
        for (i = 1; i < 16; ++i) {
            this.registerReplacement(new IdAndData(BlockList1_6.gravel.blockId(), i), new IdAndData(BlockList1_6.gravel.blockId(), 0));
        }
        for (i = 1; i < 16; ++i) {
            this.registerReplacement(new IdAndData(BlockList1_6.obsidian.blockId(), i), new IdAndData(BlockList1_6.obsidian.blockId(), 0));
        }
    }
}

