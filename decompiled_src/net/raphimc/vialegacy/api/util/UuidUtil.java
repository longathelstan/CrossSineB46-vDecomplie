/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.api.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UuidUtil {
    public static UUID createOfflinePlayerUuid(String name) {
        String string = name;
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + string).getBytes(StandardCharsets.UTF_8));
    }
}

