/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.template;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_5;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.packet.provider.SimplePacketTypesProvider;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.template.BlockItemPacketRewriter1_99;
import com.viaversion.viaversion.protocols.template.EntityPacketRewriter1_99;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundConfigurationPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundConfigurationPackets1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPacket1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPackets1_21;
import com.viaversion.viaversion.rewriter.AttributeRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.ProtocolUtil;

final class Protocol1_99To_98
extends AbstractProtocol<ClientboundPacket1_21, ClientboundPacket1_21, ServerboundPacket1_20_5, ServerboundPacket1_20_5> {
    public static final MappingData MAPPINGS = new MappingDataBase("1.98", "1.99");
    private final EntityPacketRewriter1_99 entityRewriter = new EntityPacketRewriter1_99(this);
    private final BlockItemPacketRewriter1_99 itemRewriter = new BlockItemPacketRewriter1_99(this);
    private final TagRewriter<ClientboundPacket1_21> tagRewriter = new TagRewriter<ClientboundPacket1_21>(this);

    public Protocol1_99To_98() {
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
        new StatisticsRewriter<ClientboundPacket1_21>(this).register(ClientboundPackets1_21.AWARD_STATS);
        new AttributeRewriter<ClientboundPacket1_21>(this).register1_21(ClientboundPackets1_21.UPDATE_ATTRIBUTES);
    }

    @Override
    protected void onMappingDataLoaded() {
        super.onMappingDataLoaded();
    }

    @Override
    public void init(UserConnection connection) {
        this.addEntityTracker(connection, new EntityTrackerBase(connection, EntityTypes1_20_5.PLAYER));
    }

    @Override
    public MappingData getMappingData() {
        return MAPPINGS;
    }

    public EntityPacketRewriter1_99 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPacketRewriter1_99 getItemRewriter() {
        return this.itemRewriter;
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

