/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_12_1to1_12;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.packet.ClientboundPackets1_12;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.packet.ServerboundPackets1_12;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ServerboundPackets1_12_1;

public class Protocol1_12_1To1_12
extends BackwardsProtocol<ClientboundPackets1_12_1, ClientboundPackets1_12, ServerboundPackets1_12_1, ServerboundPackets1_12> {
    public Protocol1_12_1To1_12() {
        super(ClientboundPackets1_12_1.class, ClientboundPackets1_12.class, ServerboundPackets1_12_1.class, ServerboundPackets1_12.class);
    }

    @Override
    protected void registerPackets() {
        this.cancelClientbound(ClientboundPackets1_12_1.PLACE_GHOST_RECIPE);
        this.cancelServerbound(ServerboundPackets1_12.CRAFTING_RECIPE_PLACEMENT);
    }
}

