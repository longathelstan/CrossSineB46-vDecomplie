/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.exception.InformativeException;

@FunctionalInterface
public interface ValueReader<T> {
    public T read(PacketWrapper var1) throws InformativeException;
}

