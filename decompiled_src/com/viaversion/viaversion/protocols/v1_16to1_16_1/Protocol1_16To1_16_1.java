/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_16to1_16_1;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ServerboundPackets1_16;

public class Protocol1_16To1_16_1
extends AbstractProtocol<ClientboundPackets1_16, ClientboundPackets1_16, ServerboundPackets1_16, ServerboundPackets1_16> {
    public Protocol1_16To1_16_1() {
        super(ClientboundPackets1_16.class, ClientboundPackets1_16.class, ServerboundPackets1_16.class, ServerboundPackets1_16.class);
    }
}

