/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ChestStateTracker
implements StorableObject {
    private final Set<BlockPosition> openChests = new HashSet<BlockPosition>();

    public void openChest(BlockPosition position) {
        this.openChests.add(position);
    }

    public void closeChest(BlockPosition position) {
        this.openChests.remove(position);
    }

    public boolean isChestOpen(BlockPosition position) {
        return this.openChests.contains(position);
    }

    public void clear() {
        this.openChests.clear();
    }

    public void unload(int chunkX, int chunkZ) {
        Iterator<BlockPosition> it = this.openChests.iterator();
        while (it.hasNext()) {
            BlockPosition entry = it.next();
            int x = entry.x() >> 4;
            int z = entry.z() >> 4;
            if (chunkX != x || chunkZ != z) continue;
            it.remove();
        }
    }
}

