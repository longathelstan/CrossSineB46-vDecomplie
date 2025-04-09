/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.template;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.template.BlockItemPacketRewriter1_99;
import com.viaversion.viabackwards.protocol.template.EntityPacketRewriter1_99;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_5;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.packet.provider.SimplePacketTypesProvider;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundConfigurationPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.Protocol1_20_5To1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundConfigurationPackets1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPacket1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPackets1_21;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.ProtocolUtil;

final class Protocol1_98To1_99
extends BackwardsProtocol<ClientboundPacket1_21, ClientboundPacket1_21, ServerboundPacket1_20_5, ServerboundPacket1_20_5> {
    public static final BackwardsMappingData MAPPINGS = new BackwardsMappingData("1.99", "1.98", Protocol1_20_5To1_21.class);
    private final EntityPacketRewriter1_99 entityRewriter = new EntityPacketRewriter1_99(this);
    private final BlockItemPacketRewriter1_99 itemRewriter = new BlockItemPacketRewriter1_99(this);
    private final TranslatableRewriter<ClientboundPacket1_21> translatableRewriter = new TranslatableRewriter<ClientboundPacket1_21>(this, ComponentRewriter.ReadType.NBT);
    private final TagRewriter<ClientboundPacket1_21> tagRewriter = new TagRewriter<ClientboundPacket1_21>(this);

    public Protocol1_98To1_99() {
        super(ClientboundPacket1_21.class, ClientboundPacket1_21.class, ServerboundPacket1_20_5.class, ServerboundPacket1_20_5.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.tagRewriter.registerGeneric(ClientboundPackets1_21.UPDATE_TAGS);
        this.tagRewriter.registerGeneric(ClientboundConfigurationPackets1_21.UPDATE_TAGS);
        SoundRewriter<ClientboundPacket1_21> soundRewriter = new SoundRewriter<ClientboundPacket1_21>(this);
        soundRewriter.registerSound1_19_3(ClientboundPackets1_21.SOUND);
        soundRewriter.registerSound1_19_3(ClientboundPackets1_21.SOUND_ENTITY);
        soundRewriter.registerStopSound(ClientboundPackets1_21.STOP_SOUND);
        new StatisticsRewriter<ClientboundPacket1_21>(this).register(ClientboundPackets1_21.AWARD_STATS);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_21.SET_ACTION_BAR_TEXT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_21.SET_TITLE_TEXT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_21.SET_SUBTITLE_TEXT);
        this.translatableRewriter.registerBossEvent(ClientboundPackets1_21.BOSS_EVENT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_21.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_21.TAB_LIST);
        this.translatableRewriter.registerPlayerCombatKill1_20(ClientboundPackets1_21.PLAYER_COMBAT_KILL);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_21.SYSTEM_CHAT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_21.DISGUISED_CHAT);
        this.translatableRewriter.registerPing();
    }

    @Override
    public void init(UserConnection user) {
        this.addEntityTracker(user, new EntityTrackerBase(user, EntityTypes1_20_5.PLAYER));
    }

    @Override
    public BackwardsMappingData getMappingData() {
        return MAPPINGS;
    }

    public EntityPacketRewriter1_99 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPacketRewriter1_99 getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public TranslatableRewriter<ClientboundPacket1_21> getComponentRewriter() {
        return this.translatableRewriter;
    }

    @Override
    public TagRewriter<ClientboundPacket1_21> getTagRewriter() {
        return this.tagRewriter;
    }

    @Override
    protected PacketTypesProvider<ClientboundPacket1_21, ClientboundPacket1_21, ServerboundPacket1_20_5, ServerboundPacket1_20_5> createPacketTypesProvider() {
        return new SimplePacketTypesProvider<ClientboundPacket1_21, ClientboundPacket1_21, ServerboundPacket1_20_5, ServerboundPacket1_20_5>(ProtocolUtil.packetTypeMap(this.unmappedClientboundPacketType, ClientboundPackets1_21.class, ClientboundConfigurationPackets1_21.class), ProtocolUtil.packetTypeMap(this.mappedClientboundPacketType, ClientboundPackets1_21.class, ClientboundConfigurationPackets1_21.class), ProtocolUtil.packetTypeMap(this.mappedServerboundPacketType, ServerboundPackets1_20_5.class, ServerboundConfigurationPackets1_20_5.class), ProtocolUtil.packetTypeMap(this.unmappedServerboundPacketType, ServerboundPackets1_20_5.class, ServerboundConfigurationPackets1_20_5.class));
    }
}

