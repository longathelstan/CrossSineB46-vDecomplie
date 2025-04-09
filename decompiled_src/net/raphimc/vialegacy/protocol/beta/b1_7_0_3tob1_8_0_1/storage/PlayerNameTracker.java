/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;

public class PlayerNameTracker
implements StorableObject {
    public final Int2ObjectMap<String> names = new Int2ObjectArrayMap<String>();
}

