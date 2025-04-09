/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.exception.InformativeException;

@FunctionalInterface
public interface PacketHandler {
    public void handle(PacketWrapper var1) throws InformativeException;

    default public PacketHandler then(PacketHandler handler) {
        return wrapper -> {
            this.handle(wrapper);
            handler.handle(wrapper);
        };
    }
}

