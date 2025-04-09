/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_15_1to1_15_2;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;

public class Protocol1_15_1To1_15_2
extends AbstractProtocol<ClientboundPackets1_15, ClientboundPackets1_15, ServerboundPackets1_14, ServerboundPackets1_14> {
    public Protocol1_15_1To1_15_2() {
        super(ClientboundPackets1_15.class, ClientboundPackets1_15.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
    }
}

