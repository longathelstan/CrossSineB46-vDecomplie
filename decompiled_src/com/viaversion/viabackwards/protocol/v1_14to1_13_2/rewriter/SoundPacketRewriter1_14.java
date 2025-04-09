/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_14to1_13_2.rewriter;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.protocol.v1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viabackwards.protocol.v1_14to1_13_2.storage.EntityPositionStorage1_14;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;

public class SoundPacketRewriter1_14
extends RewriterBase<Protocol1_14To1_13_2> {
    public SoundPacketRewriter1_14(Protocol1_14To1_13_2 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        SoundRewriter<ClientboundPackets1_14> soundRewriter = new SoundRewriter<ClientboundPackets1_14>((BackwardsProtocol)this.protocol);
        soundRewriter.registerSound(ClientboundPackets1_14.SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_14.CUSTOM_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_14.STOP_SOUND);
        ((Protocol1_14To1_13_2)this.protocol).registerClientbound(ClientboundPackets1_14.SOUND_ENTITY, null, wrapper -> {
            EntityPositionStorage1_14 entityStorage;
            wrapper.cancel();
            int soundId = wrapper.read(Types.VAR_INT);
            int newId = ((Protocol1_14To1_13_2)this.protocol).getMappingData().getSoundMappings().getNewId(soundId);
            if (newId == -1) {
                return;
            }
            int category = wrapper.read(Types.VAR_INT);
            int entityId = wrapper.read(Types.VAR_INT);
            StoredEntityData storedEntity = wrapper.user().getEntityTracker(((Protocol1_14To1_13_2)this.protocol).getClass()).entityData(entityId);
            if (storedEntity == null || (entityStorage = storedEntity.get(EntityPositionStorage1_14.class)) == null) {
                int n = entityId;
                ((Protocol1_14To1_13_2)this.protocol).getLogger().warning("Untracked entity with id " + n);
                return;
            }
            float volume = wrapper.read(Types.FLOAT).floatValue();
            float pitch = wrapper.read(Types.FLOAT).floatValue();
            int x = (int)(entityStorage.x() * 8.0);
            int y = (int)(entityStorage.y() * 8.0);
            int z = (int)(entityStorage.z() * 8.0);
            PacketWrapper soundPacket = wrapper.create(ClientboundPackets1_13.SOUND);
            soundPacket.write(Types.VAR_INT, newId);
            soundPacket.write(Types.VAR_INT, category);
            soundPacket.write(Types.INT, x);
            soundPacket.write(Types.INT, y);
            soundPacket.write(Types.INT, z);
            soundPacket.write(Types.FLOAT, Float.valueOf(volume));
            soundPacket.write(Types.FLOAT, Float.valueOf(pitch));
            soundPacket.send(Protocol1_14To1_13_2.class);
        });
    }
}

