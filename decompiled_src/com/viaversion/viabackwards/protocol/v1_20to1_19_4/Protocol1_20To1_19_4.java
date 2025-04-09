/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20to1_19_4;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_20to1_19_4.data.BackwardsMappingData1_20;
import com.viaversion.viabackwards.protocol.v1_20to1_19_4.rewriter.BlockItemPacketRewriter1_20;
import com.viaversion.viabackwards.protocol.v1_20to1_19_4.rewriter.EntityPacketRewriter1_20;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ServerboundPackets1_19_4;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.ArrayUtil;

public final class Protocol1_20To1_19_4
extends BackwardsProtocol<ClientboundPackets1_19_4, ClientboundPackets1_19_4, ServerboundPackets1_19_4, ServerboundPackets1_19_4> {
    public static final BackwardsMappingData1_20 MAPPINGS = new BackwardsMappingData1_20();
    private final TranslatableRewriter<ClientboundPackets1_19_4> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_19_4>(this, ComponentRewriter.ReadType.JSON);
    private final EntityPacketRewriter1_20 entityRewriter = new EntityPacketRewriter1_20(this);
    private final BlockItemPacketRewriter1_20 itemRewriter = new BlockItemPacketRewriter1_20(this);
    private final TagRewriter<ClientboundPackets1_19_4> tagRewriter = new TagRewriter<ClientboundPackets1_19_4>(this);

    public Protocol1_20To1_19_4() {
        super(ClientboundPackets1_19_4.class, ClientboundPackets1_19_4.class, ServerboundPackets1_19_4.class, ServerboundPackets1_19_4.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:replaceable_plants");
        this.tagRewriter.registerGeneric(ClientboundPackets1_19_4.UPDATE_TAGS);
        SoundRewriter<ClientboundPackets1_19_4> soundRewriter = new SoundRewriter<ClientboundPackets1_19_4>(this);
        soundRewriter.registerStopSound(ClientboundPackets1_19_4.STOP_SOUND);
        soundRewriter.registerSound1_19_3(ClientboundPackets1_19_4.SOUND);
        soundRewriter.registerSound1_19_3(ClientboundPackets1_19_4.SOUND_ENTITY);
        new StatisticsRewriter<ClientboundPackets1_19_4>(this).register(ClientboundPackets1_19_4.AWARD_STATS);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.SET_ACTION_BAR_TEXT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.SET_TITLE_TEXT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.SET_SUBTITLE_TEXT);
        this.translatableRewriter.registerBossEvent(ClientboundPackets1_19_4.BOSS_EVENT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_19_4.TAB_LIST);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.SYSTEM_CHAT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.DISGUISED_CHAT);
        this.translatableRewriter.registerPing();
        this.registerClientbound(ClientboundPackets1_19_4.UPDATE_ENABLED_FEATURES, wrapper -> {
            String[] enabledFeatures = wrapper.read(Types.STRING_ARRAY);
            wrapper.write(Types.STRING_ARRAY, ArrayUtil.add(enabledFeatures, "minecraft:update_1_20"));
        });
        this.registerClientbound(ClientboundPackets1_19_4.PLAYER_COMBAT_END, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.write(Types.INT, -1);
        });
        this.registerClientbound(ClientboundPackets1_19_4.PLAYER_COMBAT_KILL, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.write(Types.INT, -1);
            this.translatableRewriter.processText(wrapper.user(), wrapper.passthrough(Types.COMPONENT));
        });
    }

    @Override
    public void init(UserConnection user) {
        this.addEntityTracker(user, new EntityTrackerBase(user, EntityTypes1_19_4.PLAYER));
    }

    @Override
    public BackwardsMappingData1_20 getMappingData() {
        return MAPPINGS;
    }

    public EntityPacketRewriter1_20 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPacketRewriter1_20 getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_19_4> getComponentRewriter() {
        return this.translatableRewriter;
    }

    @Override
    public TagRewriter<ClientboundPackets1_19_4> getTagRewriter() {
        return this.tagRewriter;
    }
}

