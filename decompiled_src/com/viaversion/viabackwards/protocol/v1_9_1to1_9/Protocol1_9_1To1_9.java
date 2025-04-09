/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_9_1to1_9;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_9;
import com.viaversion.viaversion.rewriter.ComponentRewriter;

public class Protocol1_9_1To1_9
extends BackwardsProtocol<ClientboundPackets1_9, ClientboundPackets1_9, ServerboundPackets1_9, ServerboundPackets1_9> {
    public Protocol1_9_1To1_9() {
        super(ClientboundPackets1_9.class, ClientboundPackets1_9.class, ServerboundPackets1_9.class, ServerboundPackets1_9.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_9.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT, Types.BYTE);
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
                    if (sound == 415) {
                        wrapper.cancel();
                    } else if (sound >= 416) {
                        wrapper.set(Types.VAR_INT, 0, sound - 1);
                    }
                });
            }
        });
        TranslatableRewriter<ClientboundPackets1_9> componentRewriter = new TranslatableRewriter<ClientboundPackets1_9>(this, ComponentRewriter.ReadType.JSON);
        componentRewriter.registerComponentPacket(ClientboundPackets1_9.CHAT);
    }
}

