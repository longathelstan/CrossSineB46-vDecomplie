/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.HashMap;
import java.util.Map;

public final class CookieStorage
implements StorableObject {
    private final Map<String, byte[]> cookies = new HashMap<String, byte[]>();

    public Map<String, byte[]> cookies() {
        return this.cookies;
    }

    @Override
    public boolean clearOnServerSwitch() {
        return false;
    }
}

