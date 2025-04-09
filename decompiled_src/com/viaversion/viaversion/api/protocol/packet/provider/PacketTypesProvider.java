/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol.packet.provider;

import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface PacketTypesProvider<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType> {
    public Map<State, PacketTypeMap<CU>> unmappedClientboundPacketTypes();

    public Map<State, PacketTypeMap<SU>> unmappedServerboundPacketTypes();

    public Map<State, PacketTypeMap<CM>> mappedClientboundPacketTypes();

    public Map<State, PacketTypeMap<SM>> mappedServerboundPacketTypes();

    default public @Nullable CU unmappedClientboundType(State state, String typeName) {
        PacketTypeMap<CU> map = this.unmappedClientboundPacketTypes().get((Object)state);
        return (CU)(map != null ? (ClientboundPacketType)map.typeByName(typeName) : null);
    }

    default public @Nullable SU unmappedServerboundType(State state, String typeName) {
        PacketTypeMap<SU> map = this.unmappedServerboundPacketTypes().get((Object)state);
        return (SU)(map != null ? (ServerboundPacketType)map.typeByName(typeName) : null);
    }

    default public @Nullable CU unmappedClientboundType(State state, int packetId) {
        PacketTypeMap<CU> map = this.unmappedClientboundPacketTypes().get((Object)state);
        return (CU)(map != null ? (ClientboundPacketType)map.typeById(packetId) : null);
    }

    default public @Nullable SU unmappedServerboundType(State state, int packetId) {
        PacketTypeMap<SU> map = this.unmappedServerboundPacketTypes().get((Object)state);
        return (SU)(map != null ? (ServerboundPacketType)map.typeById(packetId) : null);
    }
}

