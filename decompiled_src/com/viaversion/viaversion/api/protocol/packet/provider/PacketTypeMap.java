/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol.packet.provider;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeArrayMap;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeMapMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface PacketTypeMap<P extends PacketType> {
    public @Nullable P typeByName(String var1);

    public @Nullable P typeById(int var1);

    public Collection<P> types();

    public static <T extends PacketType, E extends T> PacketTypeMap<T> of(Class<E> enumClass) {
        PacketType[] types = (PacketType[])enumClass.getEnumConstants();
        Preconditions.checkArgument((types != null ? 1 : 0) != 0, (String)"%s is not an enum", (Object[])new Object[]{enumClass});
        HashMap<String, PacketType> byName = new HashMap<String, PacketType>(types.length);
        for (PacketType type : types) {
            byName.put(type.getName(), type);
        }
        return PacketTypeMap.of(byName, (PacketType[])types);
    }

    public static <T extends PacketType> PacketTypeMap<T> of(Map<String, T> packetsByName, Int2ObjectMap<T> packetsById) {
        return new PacketTypeMapMap<T>(packetsByName, packetsById);
    }

    public static <T extends PacketType> PacketTypeMap<T> of(Map<String, T> packetsByName, T[] packets) {
        return new PacketTypeArrayMap(packetsByName, packets);
    }
}

