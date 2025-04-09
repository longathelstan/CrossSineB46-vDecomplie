/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol.packet.provider;

import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeMap;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import java.util.Map;
import java.util.Objects;

public final class SimplePacketTypesProvider<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType>
implements PacketTypesProvider<CU, CM, SM, SU> {
    private final Map<State, PacketTypeMap<CU>> unmappedClientboundPacketTypes;
    private final Map<State, PacketTypeMap<CM>> mappedClientboundPacketTypes;
    private final Map<State, PacketTypeMap<SM>> mappedServerboundPacketTypes;
    private final Map<State, PacketTypeMap<SU>> unmappedServerboundPacketTypes;

    public SimplePacketTypesProvider(Map<State, PacketTypeMap<CU>> unmappedClientboundPacketTypes, Map<State, PacketTypeMap<CM>> mappedClientboundPacketTypes, Map<State, PacketTypeMap<SM>> mappedServerboundPacketTypes, Map<State, PacketTypeMap<SU>> unmappedServerboundPacketTypes) {
        this.unmappedClientboundPacketTypes = unmappedClientboundPacketTypes;
        this.mappedClientboundPacketTypes = mappedClientboundPacketTypes;
        this.mappedServerboundPacketTypes = mappedServerboundPacketTypes;
        this.unmappedServerboundPacketTypes = unmappedServerboundPacketTypes;
    }

    @Override
    public Map<State, PacketTypeMap<CU>> unmappedClientboundPacketTypes() {
        return this.unmappedClientboundPacketTypes;
    }

    @Override
    public Map<State, PacketTypeMap<CM>> mappedClientboundPacketTypes() {
        return this.mappedClientboundPacketTypes;
    }

    @Override
    public Map<State, PacketTypeMap<SM>> mappedServerboundPacketTypes() {
        return this.mappedServerboundPacketTypes;
    }

    @Override
    public Map<State, PacketTypeMap<SU>> unmappedServerboundPacketTypes() {
        return this.unmappedServerboundPacketTypes;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SimplePacketTypesProvider)) {
            return false;
        }
        SimplePacketTypesProvider simplePacketTypesProvider = (SimplePacketTypesProvider)object;
        return Objects.equals(this.unmappedClientboundPacketTypes, simplePacketTypesProvider.unmappedClientboundPacketTypes) && Objects.equals(this.mappedClientboundPacketTypes, simplePacketTypesProvider.mappedClientboundPacketTypes) && Objects.equals(this.mappedServerboundPacketTypes, simplePacketTypesProvider.mappedServerboundPacketTypes) && Objects.equals(this.unmappedServerboundPacketTypes, simplePacketTypesProvider.unmappedServerboundPacketTypes);
    }

    public int hashCode() {
        return (((0 * 31 + Objects.hashCode(this.unmappedClientboundPacketTypes)) * 31 + Objects.hashCode(this.mappedClientboundPacketTypes)) * 31 + Objects.hashCode(this.mappedServerboundPacketTypes)) * 31 + Objects.hashCode(this.unmappedServerboundPacketTypes);
    }

    public String toString() {
        return String.format("%s[unmappedClientboundPacketTypes=%s, mappedClientboundPacketTypes=%s, mappedServerboundPacketTypes=%s, unmappedServerboundPacketTypes=%s]", this.getClass().getSimpleName(), Objects.toString(this.unmappedClientboundPacketTypes), Objects.toString(this.mappedClientboundPacketTypes), Objects.toString(this.mappedServerboundPacketTypes), Objects.toString(this.unmappedServerboundPacketTypes));
    }
}

