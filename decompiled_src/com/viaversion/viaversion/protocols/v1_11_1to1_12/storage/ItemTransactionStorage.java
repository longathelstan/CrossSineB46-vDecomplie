/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_11_1to1_12.storage;

public final class ItemTransactionStorage {
    private final short windowId;
    private final short slotId;
    private final short actionId;

    public ItemTransactionStorage(short windowId, short slotId, short actionId) {
        this.windowId = windowId;
        this.slotId = slotId;
        this.actionId = actionId;
    }

    public short windowId() {
        return this.windowId;
    }

    public short slotId() {
        return this.slotId;
    }

    public short actionId() {
        return this.actionId;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ItemTransactionStorage)) {
            return false;
        }
        ItemTransactionStorage itemTransactionStorage = (ItemTransactionStorage)object;
        return this.windowId == itemTransactionStorage.windowId && this.slotId == itemTransactionStorage.slotId && this.actionId == itemTransactionStorage.actionId;
    }

    public int hashCode() {
        return ((0 * 31 + Short.hashCode(this.windowId)) * 31 + Short.hashCode(this.slotId)) * 31 + Short.hashCode(this.actionId);
    }

    public String toString() {
        return String.format("%s[windowId=%s, slotId=%s, actionId=%s]", this.getClass().getSimpleName(), Short.toString(this.windowId), Short.toString(this.slotId), Short.toString(this.actionId));
    }
}

