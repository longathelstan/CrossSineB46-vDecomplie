/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_14_4to1_15;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_14_3to1_14_4.packet.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.rewriter.EntityPacketRewriter1_15;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.rewriter.ItemPacketRewriter1_15;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.rewriter.WorldPacketRewriter1_15;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

public class Protocol1_14_4To1_15
extends AbstractProtocol<ClientboundPackets1_14_4, ClientboundPackets1_15, ServerboundPackets1_14, ServerboundPackets1_14> {
    public static final MappingData MAPPINGS = new MappingDataBase("1.14", "1.15");
    private final EntityPacketRewriter1_15 entityRewriter = new EntityPacketRewriter1_15(this);
    private final ItemPacketRewriter1_15 itemRewriter = new ItemPacketRewriter1_15(this);
    private final TagRewriter<ClientboundPackets1_14_4> tagRewriter = new TagRewriter<ClientboundPackets1_14_4>(this);

    public Protocol1_14_4To1_15() {
        super(ClientboundPackets1_14_4.class, ClientboundPackets1_15.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        WorldPacketRewriter1_15.register(this);
        SoundRewriter<ClientboundPackets1_14_4> soundRewriter = new SoundRewriter<ClientboundPackets1_14_4>(this);
        soundRewriter.registerSound(ClientboundPackets1_14_4.SOUND_ENTITY);
        soundRewriter.registerSound(ClientboundPackets1_14_4.SOUND);
        new StatisticsRewriter<ClientboundPackets1_14_4>(this).register(ClientboundPackets1_14_4.AWARD_STATS);
        this.registerServerbound(ServerboundPackets1_14.EDIT_BOOK, wrapper -> this.itemRewriter.handleItemToServer(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2)));
        this.tagRewriter.register(ClientboundPackets1_14_4.UPDATE_TAGS, RegistryType.ENTITY);
    }

    @Override
    protected void onMappingDataLoaded() {
        EntityTypes1_15.initialize(this);
        this.tagRewriter.removeTag(RegistryType.BLOCK, "minecraft:dirt_like");
        this.tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:lectern_books");
        this.tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:bee_growables", "minecraft:beehives");
        this.tagRewriter.addEmptyTag(RegistryType.ENTITY, "minecraft:beehive_inhabitors");
        super.onMappingDataLoaded();
    }

    @Override
    public void init(UserConnection connection) {
        this.addEntityTracker(connection, new EntityTrackerBase(connection, EntityTypes1_15.PLAYER));
    }

    @Override
    public MappingData getMappingData() {
        return MAPPINGS;
    }

    public EntityPacketRewriter1_15 getEntityRewriter() {
        return this.entityRewriter;
    }

    public ItemPacketRewriter1_15 getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public TagRewriter<ClientboundPackets1_14_4> getTagRewriter() {
        return this.tagRewriter;
    }
}

