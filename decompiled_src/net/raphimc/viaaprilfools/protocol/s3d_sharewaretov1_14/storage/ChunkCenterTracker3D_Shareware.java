/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class ChunkCenterTracker3D_Shareware
implements StorableObject {
    private boolean forceSendCenterChunk = true;
    private int chunkCenterX;
    private int chunkCenterZ;

    public boolean isForceSendCenterChunk() {
        return this.forceSendCenterChunk;
    }

    public void setForceSendCenterChunk(boolean forceSendCenterChunk) {
        this.forceSendCenterChunk = forceSendCenterChunk;
    }

    public int getChunkCenterX() {
        return this.chunkCenterX;
    }

    public void setChunkCenterX(int chunkCenterX) {
        this.chunkCenterX = chunkCenterX;
    }

    public int getChunkCenterZ() {
        return this.chunkCenterZ;
    }

    public void setChunkCenterZ(int chunkCenterZ) {
        this.chunkCenterZ = chunkCenterZ;
    }
}

