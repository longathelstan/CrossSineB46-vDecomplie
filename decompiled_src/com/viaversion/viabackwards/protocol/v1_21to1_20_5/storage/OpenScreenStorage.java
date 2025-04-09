/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_21to1_20_5.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public final class OpenScreenStorage
implements StorableObject {
    private int menuType = -1;

    public int menuType() {
        return this.menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }
}

