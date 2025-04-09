/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_15_2to1_16.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class InventoryTracker1_16
implements StorableObject {
    private boolean inventoryOpen;

    public boolean isInventoryOpen() {
        return this.inventoryOpen;
    }

    public void setInventoryOpen(boolean inventoryOpen) {
        this.inventoryOpen = inventoryOpen;
    }
}

