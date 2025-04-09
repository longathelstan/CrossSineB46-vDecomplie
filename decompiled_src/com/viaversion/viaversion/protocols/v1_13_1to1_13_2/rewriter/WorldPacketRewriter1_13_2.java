/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_13_1to1_13_2.rewriter;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_13_1to1_13_2.Protocol1_13_1To1_13_2;

public class WorldPacketRewriter1_13_2 {
    public static void register(Protocol1_13_1To1_13_2 protocol) {
        protocol.registerClientbound(ClientboundPackets1_13.LEVEL_PARTICLES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Types.INT, 0);
                    if (id == 27) {
                        wrapper.write(Types.ITEM1_13_2, wrapper.read(Types.ITEM1_13));
                    }
                });
            }
        });
    }
}

