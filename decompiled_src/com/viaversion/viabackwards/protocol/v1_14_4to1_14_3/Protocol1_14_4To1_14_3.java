/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_14_4to1_14_3;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_14_3to1_14_4.packet.ClientboundPackets1_14_4;

public class Protocol1_14_4To1_14_3
extends BackwardsProtocol<ClientboundPackets1_14_4, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14> {
    public Protocol1_14_4To1_14_3() {
        super(ClientboundPackets1_14_4.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_14_4.BLOCK_BREAK_ACK, ClientboundPackets1_14.BLOCK_UPDATE, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_14);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int status = wrapper.read(Types.VAR_INT);
                    boolean allGood = wrapper.read(Types.BOOLEAN);
                    if (allGood && status == 0) {
                        wrapper.cancel();
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_14_4.MERCHANT_OFFERS, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            int size = wrapper.passthrough(Types.UNSIGNED_BYTE).shortValue();
            for (int i = 0; i < size; ++i) {
                wrapper.passthrough(Types.ITEM1_13_2);
                wrapper.passthrough(Types.ITEM1_13_2);
                if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                    wrapper.passthrough(Types.ITEM1_13_2);
                }
                wrapper.passthrough(Types.BOOLEAN);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.FLOAT);
                wrapper.read(Types.INT);
            }
        });
    }
}

