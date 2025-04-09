/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_18_2to1_19.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public final class SequenceStorage
implements StorableObject {
    private final Object lock = new Object();
    private int sequenceId = -1;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int sequenceId() {
        Object object = this.lock;
        synchronized (object) {
            return this.sequenceId;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int setSequenceId(int sequenceId) {
        Object object = this.lock;
        synchronized (object) {
            int previousSequence = this.sequenceId;
            this.sequenceId = sequenceId;
            return previousSequence;
        }
    }
}

