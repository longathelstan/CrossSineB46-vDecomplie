/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_12to1_11_1.rewriter;

import com.viaversion.viabackwards.api.rewriters.LegacySoundRewriter;
import com.viaversion.viabackwards.protocol.v1_12to1_11_1.Protocol1_12To1_11_1;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.packet.ClientboundPackets1_12;

public class SoundPacketRewriter1_12
extends LegacySoundRewriter<Protocol1_12To1_11_1> {
    public SoundPacketRewriter1_12(Protocol1_12To1_11_1 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_12To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_12.CUSTOM_SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.VAR_INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
            }
        });
        ((Protocol1_12To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_12.SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.handler(wrapper -> {
                    int oldId = wrapper.get(Types.VAR_INT, 0);
                    int newId = SoundPacketRewriter1_12.this.handleSounds(oldId);
                    if (newId == -1) {
                        wrapper.cancel();
                        return;
                    }
                    if (SoundPacketRewriter1_12.this.hasPitch(oldId)) {
                        wrapper.set(Types.FLOAT, 1, Float.valueOf(SoundPacketRewriter1_12.this.handlePitch(oldId)));
                    }
                    wrapper.set(Types.VAR_INT, 0, newId);
                });
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.added(26, 277, 1.4f);
        this.added(27, -1);
        this.added(72, 70);
        this.added(73, 70);
        this.added(74, 70);
        this.added(75, 70);
        this.added(80, 70);
        this.added(150, -1);
        this.added(151, -1);
        this.added(152, -1);
        this.added(195, -1);
        this.added(274, 198, 0.8f);
        this.added(275, 199, 0.8f);
        this.added(276, 200, 0.8f);
        this.added(277, 201, 0.8f);
        this.added(278, 191, 0.9f);
        this.added(279, 203, 1.5f);
        this.added(280, 202, 0.8f);
        this.added(319, 133, 0.6f);
        this.added(320, 134, 1.7f);
        this.added(321, 219, 1.5f);
        this.added(322, 136, 0.7f);
        this.added(323, 135, 1.6f);
        this.added(324, 138, 1.5f);
        this.added(325, 163, 1.5f);
        this.added(326, 170, 1.5f);
        this.added(327, 178, 1.5f);
        this.added(328, 186, 1.5f);
        this.added(329, 192, 1.5f);
        this.added(330, 198, 1.5f);
        this.added(331, 226, 1.5f);
        this.added(332, 259, 1.5f);
        this.added(333, 198, 1.3f);
        this.added(334, 291, 1.5f);
        this.added(335, 321, 1.5f);
        this.added(336, 337, 1.5f);
        this.added(337, 347, 1.5f);
        this.added(338, 351, 1.5f);
        this.added(339, 363, 1.5f);
        this.added(340, 376, 1.5f);
        this.added(341, 385, 1.5f);
        this.added(342, 390, 1.5f);
        this.added(343, 400, 1.5f);
        this.added(344, 403, 1.5f);
        this.added(345, 408, 1.5f);
        this.added(346, 414, 1.5f);
        this.added(347, 418, 1.5f);
        this.added(348, 427, 1.5f);
        this.added(349, 438, 1.5f);
        this.added(350, 442, 1.5f);
        this.added(351, 155);
        this.added(368, 316);
        this.added(369, 316);
        this.added(544, -1);
        this.added(545, -1);
        this.added(546, 317, 1.5f);
    }
}

