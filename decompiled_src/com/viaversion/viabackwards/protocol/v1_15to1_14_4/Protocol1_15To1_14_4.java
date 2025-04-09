/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_15to1_14_4;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_15to1_14_4.rewriter.BlockItemPacketRewriter1_15;
import com.viaversion.viabackwards.protocol.v1_15to1_14_4.rewriter.EntityPacketRewriter1_15;
import com.viaversion.viabackwards.protocol.v1_15to1_14_4.storage.ImmediateRespawnStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_14_3to1_14_4.packet.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

public class Protocol1_15To1_14_4
extends BackwardsProtocol<ClientboundPackets1_15, ClientboundPackets1_14_4, ServerboundPackets1_14, ServerboundPackets1_14> {
    public static final BackwardsMappingData MAPPINGS = new BackwardsMappingData("1.15", "1.14", Protocol1_14_4To1_15.class);
    final EntityPacketRewriter1_15 entityRewriter = new EntityPacketRewriter1_15(this);
    final BlockItemPacketRewriter1_15 blockItemPackets = new BlockItemPacketRewriter1_15(this);
    final TranslatableRewriter<ClientboundPackets1_15> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_15>(this, ComponentRewriter.ReadType.JSON);
    final TagRewriter<ClientboundPackets1_15> tagRewriter = new TagRewriter<ClientboundPackets1_15>(this);

    public Protocol1_15To1_14_4() {
        super(ClientboundPackets1_15.class, ClientboundPackets1_14_4.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.translatableRewriter.registerBossEvent(ClientboundPackets1_15.BOSS_EVENT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_15.CHAT);
        this.translatableRewriter.registerPlayerCombat(ClientboundPackets1_15.PLAYER_COMBAT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_15.DISCONNECT);
        this.translatableRewriter.registerOpenScreen(ClientboundPackets1_15.OPEN_SCREEN);
        this.translatableRewriter.registerTabList(ClientboundPackets1_15.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_15.SET_TITLES);
        this.translatableRewriter.registerPing();
        SoundRewriter<ClientboundPackets1_15> soundRewriter = new SoundRewriter<ClientboundPackets1_15>(this);
        soundRewriter.registerSound(ClientboundPackets1_15.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_15.SOUND_ENTITY);
        soundRewriter.registerNamedSound(ClientboundPackets1_15.CUSTOM_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_15.STOP_SOUND);
        this.registerClientbound(ClientboundPackets1_15.EXPLODE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.handler(wrapper -> {
                    PacketWrapper soundPacket = wrapper.create(ClientboundPackets1_14_4.SOUND);
                    soundPacket.write(Types.VAR_INT, 243);
                    soundPacket.write(Types.VAR_INT, 4);
                    soundPacket.write(Types.INT, this.toEffectCoordinate(wrapper.get(Types.FLOAT, 0).floatValue()));
                    soundPacket.write(Types.INT, this.toEffectCoordinate(wrapper.get(Types.FLOAT, 1).floatValue()));
                    soundPacket.write(Types.INT, this.toEffectCoordinate(wrapper.get(Types.FLOAT, 2).floatValue()));
                    soundPacket.write(Types.FLOAT, Float.valueOf(4.0f));
                    soundPacket.write(Types.FLOAT, Float.valueOf(1.0f));
                    soundPacket.send(Protocol1_15To1_14_4.class);
                });
            }

            int toEffectCoordinate(float coordinate) {
                return (int)(coordinate * 8.0f);
            }
        });
        this.tagRewriter.register(ClientboundPackets1_15.UPDATE_TAGS, RegistryType.ENTITY);
        new StatisticsRewriter<ClientboundPackets1_15>(this).register(ClientboundPackets1_15.AWARD_STATS);
    }

    @Override
    public void init(UserConnection user) {
        user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, EntityTypes1_15.PLAYER));
        user.addClientWorld(this.getClass(), new ClientWorld());
        user.put(new ImmediateRespawnStorage());
    }

    @Override
    public BackwardsMappingData getMappingData() {
        return MAPPINGS;
    }

    public EntityPacketRewriter1_15 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPacketRewriter1_15 getItemRewriter() {
        return this.blockItemPackets;
    }

    @Override
    public TagRewriter<ClientboundPackets1_15> getTagRewriter() {
        return this.tagRewriter;
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_15> getComponentRewriter() {
        return this.translatableRewriter;
    }
}

