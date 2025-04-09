/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13_2to1_13_1.rewriter;

import com.viaversion.viabackwards.protocol.v1_13_2to1_13_1.Protocol1_13_2To1_13_1;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;

public class WorldPacketRewriter1_13_2 {
    public static void register(Protocol1_13_2To1_13_1 protocol) {
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
                        wrapper.write(Types.ITEM1_13, wrapper.read(Types.ITEM1_13_2));
                    }
                });
            }
        });
    }
}

