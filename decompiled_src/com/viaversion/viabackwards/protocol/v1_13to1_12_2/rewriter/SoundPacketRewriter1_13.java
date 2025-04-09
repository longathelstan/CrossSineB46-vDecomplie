/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13to1_12_2.rewriter;

import com.viaversion.viabackwards.protocol.v1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.data.NamedSoundMappings1_12_2;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ClientboundPackets1_12_1;

public class SoundPacketRewriter1_13
extends RewriterBase<Protocol1_13To1_12_2> {
    static final String[] SOUND_SOURCES = new String[]{"master", "music", "record", "weather", "block", "hostile", "neutral", "player", "ambient", "voice"};

    public SoundPacketRewriter1_13(Protocol1_13To1_12_2 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.CUSTOM_SOUND, wrapper -> {
            String sound = wrapper.read(Types.STRING);
            String mappedSound = NamedSoundMappings1_12_2.getOldId(sound);
            if (mappedSound != null || (mappedSound = ((Protocol1_13To1_12_2)this.protocol).getMappingData().getMappedNamedSound(sound)) != null) {
                wrapper.write(Types.STRING, mappedSound);
            } else {
                wrapper.write(Types.STRING, sound);
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.STOP_SOUND, ClientboundPackets1_12_1.CUSTOM_PAYLOAD, wrapper -> {
            String sound;
            wrapper.write(Types.STRING, "MC|StopSound");
            byte flags = wrapper.read(Types.BYTE);
            String source = (flags & 1) != 0 ? SOUND_SOURCES[wrapper.read(Types.VAR_INT)] : "";
            if ((flags & 2) != 0) {
                String newSound = wrapper.read(Types.STRING);
                sound = ((Protocol1_13To1_12_2)this.protocol).getMappingData().getMappedNamedSound(newSound);
                if (sound == null) {
                    sound = "";
                }
            } else {
                sound = "";
            }
            wrapper.write(Types.STRING, source);
            wrapper.write(Types.STRING, sound);
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int newSound = wrapper.get(Types.VAR_INT, 0);
                    int oldSound = ((Protocol1_13To1_12_2)SoundPacketRewriter1_13.this.protocol).getMappingData().getSoundMappings().getNewId(newSound);
                    if (oldSound == -1) {
                        wrapper.cancel();
                    } else {
                        wrapper.set(Types.VAR_INT, 0, oldSound);
                    }
                });
            }
        });
    }
}

