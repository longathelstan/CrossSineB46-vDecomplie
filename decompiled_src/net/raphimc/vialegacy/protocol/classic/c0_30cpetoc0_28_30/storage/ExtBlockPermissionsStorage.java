/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;

public class ExtBlockPermissionsStorage
implements StorableObject {
    private final IntSet placingDenied = new IntOpenHashSet();
    private final IntSet breakingDenied = new IntOpenHashSet();

    public void addPlaceable(int block) {
        this.placingDenied.remove(block);
    }

    public void addBreakable(int block) {
        this.breakingDenied.remove(block);
    }

    public void removePlaceable(int block) {
        this.placingDenied.add(block);
    }

    public void removeBreakable(int block) {
        this.breakingDenied.add(block);
    }

    public boolean isPlacingDenied(int block) {
        return this.placingDenied.contains(block);
    }

    public boolean isBreakingDenied(int block) {
        return this.breakingDenied.contains(block);
    }
}

