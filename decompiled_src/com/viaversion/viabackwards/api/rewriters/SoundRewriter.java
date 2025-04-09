/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.SoundEvent;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;

public class SoundRewriter<C extends ClientboundPacketType>
extends com.viaversion.viaversion.rewriter.SoundRewriter<C> {
    final BackwardsProtocol<C, ?, ?, ?> protocol;

    public SoundRewriter(BackwardsProtocol<C, ?, ?, ?> protocol) {
        super(protocol);
        this.protocol = protocol;
    }

    public void registerNamedSound(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.handler(SoundRewriter.this.getNamedSoundHandler());
            }
        });
    }

    public void registerStopSound(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(SoundRewriter.this.getStopSoundHandler());
            }
        });
    }

    public PacketHandler getNamedSoundHandler() {
        return wrapper -> {
            String soundId = wrapper.get(Types.STRING, 0);
            String mappedId = this.protocol.getMappingData().getMappedNamedSound(soundId);
            if (mappedId == null) {
                return;
            }
            if (!mappedId.isEmpty()) {
                wrapper.set(Types.STRING, 0, mappedId);
            } else {
                wrapper.cancel();
            }
        };
    }

    public PacketHandler getStopSoundHandler() {
        return wrapper -> {
            byte flags = wrapper.passthrough(Types.BYTE);
            if ((flags & 2) == 0) {
                return;
            }
            if ((flags & 1) != 0) {
                wrapper.passthrough(Types.VAR_INT);
            }
            String soundId = wrapper.read(Types.STRING);
            String mappedId = this.protocol.getMappingData().getMappedNamedSound(soundId);
            if (mappedId == null) {
                wrapper.write(Types.STRING, soundId);
                return;
            }
            if (!mappedId.isEmpty()) {
                wrapper.write(Types.STRING, mappedId);
            } else {
                wrapper.cancel();
            }
        };
    }

    @Override
    public void registerSound1_19_3(C packetType) {
        this.protocol.registerClientbound(packetType, this.getSoundHandler1_19_3());
    }

    public PacketHandler getSoundHandler1_19_3() {
        return wrapper -> {
            Holder<int> soundEventHolder = (Holder<int>)((Object)wrapper.read(Types.SOUND_EVENT));
            if (soundEventHolder.isDirect()) {
                wrapper.write(Types.SOUND_EVENT, this.rewriteSoundEvent(wrapper, (Holder<SoundEvent>)soundEventHolder));
                return;
            }
            int mappedId = this.idRewriter.rewrite(soundEventHolder.id());
            if (mappedId == -1) {
                wrapper.cancel();
                return;
            }
            if (mappedId != soundEventHolder.id()) {
                soundEventHolder = Holder.of(mappedId);
            }
            wrapper.write(Types.SOUND_EVENT, soundEventHolder);
        };
    }

    public Holder<SoundEvent> rewriteSoundEvent(PacketWrapper wrapper, Holder<SoundEvent> soundEventHolder) {
        SoundEvent soundEvent = soundEventHolder.value();
        String mappedIdentifier = this.protocol.getMappingData().getMappedNamedSound(soundEvent.identifier());
        if (mappedIdentifier != null) {
            if (!mappedIdentifier.isEmpty()) {
                return Holder.of(soundEvent.withIdentifier(mappedIdentifier));
            }
            wrapper.cancel();
        }
        return soundEventHolder;
    }
}

