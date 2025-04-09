/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_9to1_9_1;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_9;

public class Protocol1_9To1_9_1
extends AbstractProtocol<ClientboundPackets1_9, ClientboundPackets1_9, ServerboundPackets1_9, ServerboundPackets1_9> {
    public Protocol1_9To1_9_1() {
        super(ClientboundPackets1_9.class, ClientboundPackets1_9.class, ServerboundPackets1_9.class, ServerboundPackets1_9.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_9.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.BYTE, Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.STRING);
                this.map(Types.BOOLEAN);
            }
        });
        this.registerClientbound(ClientboundPackets1_9.SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int sound = wrapper.get(Types.VAR_INT, 0);
                    if (sound >= 415) {
                        wrapper.set(Types.VAR_INT, 0, sound + 1);
                    }
                });
            }
        });
    }
}

