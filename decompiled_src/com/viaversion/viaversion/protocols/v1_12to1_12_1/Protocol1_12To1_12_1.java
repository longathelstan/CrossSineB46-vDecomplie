/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12to1_12_1;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.packet.ClientboundPackets1_12;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.packet.ServerboundPackets1_12;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ServerboundPackets1_12_1;

public class Protocol1_12To1_12_1
extends AbstractProtocol<ClientboundPackets1_12, ClientboundPackets1_12_1, ServerboundPackets1_12, ServerboundPackets1_12_1> {
    public Protocol1_12To1_12_1() {
        super(ClientboundPackets1_12.class, ClientboundPackets1_12_1.class, ServerboundPackets1_12.class, ServerboundPackets1_12_1.class);
    }

    @Override
    protected void registerPackets() {
        this.cancelServerbound(ServerboundPackets1_12_1.PLACE_RECIPE);
    }
}

