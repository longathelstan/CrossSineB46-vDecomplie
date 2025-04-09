/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeMap;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ProtocolUtil {
    @SafeVarargs
    public static <P extends PacketType> Map<State, PacketTypeMap<P>> packetTypeMap(@Nullable Class<P> parent, Class<? extends P> ... packetTypeClasses) {
        if (parent == null) {
            return Collections.emptyMap();
        }
        EnumMap<State, PacketTypeMap<P>> map = new EnumMap<State, PacketTypeMap<P>>(State.class);
        for (Class<P> clazz : packetTypeClasses) {
            PacketType[] types = (PacketType[])clazz.getEnumConstants();
            Preconditions.checkArgument((types != null ? 1 : 0) != 0, (String)"%s not an enum", (Object[])new Object[]{clazz});
            Preconditions.checkArgument((types.length > 0 ? 1 : 0) != 0, (String)"Enum %s has no types", (Object[])new Object[]{clazz});
            State state = types[0].state();
            map.put(state, PacketTypeMap.of(clazz));
        }
        return map;
    }

    public static String toNiceHex(int id) {
        String hex = Integer.toHexString(id).toUpperCase(Locale.ROOT);
        String string = hex.length() == 1 ? "0x0" : "0x";
        String string2 = hex;
        String string3 = string;
        return string3 + string2;
    }

    public static String toNiceName(Class<? extends Protocol> protocol) {
        return protocol.getSimpleName().replace("Protocol", "").replace("To", "->").replace("_", ".");
    }
}

