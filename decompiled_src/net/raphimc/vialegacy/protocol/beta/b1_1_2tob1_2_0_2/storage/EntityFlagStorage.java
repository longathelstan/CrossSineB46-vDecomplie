/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;

public class EntityFlagStorage
implements StorableObject {
    private final Int2IntMap animationFlags = new Int2IntOpenHashMap();

    public boolean getFlag(int entityId, int index2) {
        return (this.getFlagMask(entityId) & 1 << index2) != 0;
    }

    public int getFlagMask(int entityId) {
        return this.animationFlags.get(entityId);
    }

    public void setFlag(int entityId, int index2, boolean flag) {
        int mask = this.animationFlags.get(entityId);
        if (flag) {
            this.animationFlags.put(entityId, mask | 1 << index2);
        } else {
            this.animationFlags.put(entityId, mask & ~(1 << index2));
        }
    }
}

