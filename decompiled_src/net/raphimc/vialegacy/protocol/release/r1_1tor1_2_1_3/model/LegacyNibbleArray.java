/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.model;

import com.viaversion.viaversion.api.minecraft.chunks.NibbleArray;

public class LegacyNibbleArray
extends NibbleArray {
    private final int depthBits;
    private final int depthBitsPlusFour;

    public LegacyNibbleArray(int length) {
        this(length, 7);
    }

    public LegacyNibbleArray(byte[] handle) {
        this(handle, 7);
    }

    public LegacyNibbleArray(int length, int depthBits) {
        super(length);
        this.depthBits = depthBits;
        this.depthBitsPlusFour = depthBits + 4;
    }

    public LegacyNibbleArray(byte[] handle, int depthBits) {
        super(handle);
        this.depthBits = depthBits;
        this.depthBitsPlusFour = depthBits + 4;
    }

    @Override
    public byte get(int x, int y, int z) {
        return this.get(this.index(x, y, z));
    }

    @Override
    public void set(int x, int y, int z, int value) {
        this.set(this.index(x, y, z), value);
    }

    public int index(int x, int y, int z) {
        return x << this.depthBitsPlusFour | z << this.depthBits | y;
    }
}

