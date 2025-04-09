/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.api.protocol;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;

public abstract class StatelessProtocol<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType>
extends AbstractProtocol<CU, CM, SM, SU> {
    public StatelessProtocol(Class<CU> unmappedClientboundPacketType, Class<CM> mappedClientboundPacketType, Class<SM> mappedServerboundPacketType, Class<SU> unmappedServerboundPacketType) {
        super(unmappedClientboundPacketType, mappedClientboundPacketType, mappedServerboundPacketType, unmappedServerboundPacketType);
    }

    @Override
    public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws InformativeException, CancelException {
        super.transform(direction, direction == Direction.SERVERBOUND ? state : State.PLAY, packetWrapper);
    }
}

