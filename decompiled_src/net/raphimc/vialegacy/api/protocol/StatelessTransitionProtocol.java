/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.api.protocol;

import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;

public abstract class StatelessTransitionProtocol<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType>
extends StatelessProtocol<CU, CM, SM, SU> {
    public StatelessTransitionProtocol(Class<CU> unmappedClientboundPacketType, Class<CM> mappedClientboundPacketType, Class<SM> mappedServerboundPacketType, Class<SU> unmappedServerboundPacketType) {
        super(unmappedClientboundPacketType, mappedClientboundPacketType, mappedServerboundPacketType, unmappedServerboundPacketType);
    }

    public void registerServerboundTransition(ServerboundPacketType unmappedPacketType, SM mappedPacketType, PacketHandler handler) {
        this.registerServerbound(unmappedPacketType.state(), unmappedPacketType.getId(), mappedPacketType != null ? mappedPacketType.getId() : -1, wrapper -> {
            wrapper.setPacketType((PacketType)mappedPacketType);
            if (handler != null) {
                handler.handle(wrapper);
            }
        });
    }

    public void registerClientboundTransition(CU unmappedPacketType, Object ... handlers) {
        if (handlers.length % 2 != 0) {
            throw new IllegalArgumentException("handlers.length % 2 != 0");
        }
        this.registerClientbound(unmappedPacketType.state(), unmappedPacketType.getId(), -1, wrapper -> {
            State currentState = wrapper.user().getProtocolInfo().getServerState();
            for (int i = 0; i < handlers.length; i += 2) {
                PacketHandler handler;
                Object patt2681$temp = handlers[i];
                if (patt2681$temp instanceof State) {
                    State state = (State)((Object)((Object)patt2681$temp));
                    if (state != currentState) {
                        continue;
                    }
                } else {
                    ClientboundPacketType mappedPacketType = (ClientboundPacketType)handlers[i];
                    if (mappedPacketType.state() != currentState) continue;
                    wrapper.setPacketType(mappedPacketType);
                }
                if ((handler = (PacketHandler)handlers[i + 1]) != null) {
                    handler.handle(wrapper);
                }
                return;
            }
            State state = currentState;
            ClientboundPacketType clientboundPacketType = unmappedPacketType;
            throw new IllegalStateException("No handler found for packet " + clientboundPacketType + " in state " + (Object)((Object)state));
        });
    }
}

