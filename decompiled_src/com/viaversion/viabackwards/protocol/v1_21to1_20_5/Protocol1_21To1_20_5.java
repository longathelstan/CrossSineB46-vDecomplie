/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_21to1_20_5;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_21to1_20_5.rewriter.BlockItemPacketRewriter1_21;
import com.viaversion.viabackwards.protocol.v1_21to1_20_5.rewriter.ComponentRewriter1_21;
import com.viaversion.viabackwards.protocol.v1_21to1_20_5.rewriter.EntityPacketRewriter1_21;
import com.viaversion.viabackwards.protocol.v1_21to1_20_5.storage.EnchantmentsPaintingsStorage;
import com.viaversion.viabackwards.protocol.v1_21to1_20_5.storage.OpenScreenStorage;
import com.viaversion.viabackwards.protocol.v1_21to1_20_5.storage.PlayerRotationStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_5;
import com.viaversion.viaversion.api.minecraft.item.data.ChatType;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.packet.provider.SimplePacketTypesProvider;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundConfigurationPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundConfigurationPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.Protocol1_20_5To1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundConfigurationPackets1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPacket1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPackets1_21;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.ArrayUtil;
import com.viaversion.viaversion.util.ProtocolUtil;

public final class Protocol1_21To1_20_5
extends BackwardsProtocol<ClientboundPacket1_21, ClientboundPacket1_20_5, ServerboundPacket1_20_5, ServerboundPacket1_20_5> {
    public static final BackwardsMappingData MAPPINGS = new BackwardsMappingData("1.21", "1.20.5", Protocol1_20_5To1_21.class);
    private final EntityPacketRewriter1_21 entityRewriter = new EntityPacketRewriter1_21(this);
    private final BlockItemPacketRewriter1_21 itemRewriter = new BlockItemPacketRewriter1_21(this);
    private final TranslatableRewriter<ClientboundPacket1_21> translatableRewriter = new ComponentRewriter1_21(this);
    private final TagRewriter<ClientboundPacket1_21> tagRewriter = new TagRewriter<ClientboundPacket1_21>(this);

    public Protocol1_21To1_20_5() {
        super(ClientboundPacket1_21.class, ClientboundPacket1_20_5.class, ServerboundPacket1_20_5.class, ServerboundPacket1_20_5.class);
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
        this.translatableRewriter.registerPing();
        this.cancelClientbound(ClientboundPackets1_21.PROJECTILE_POWER);
        this.cancelClientbound(ClientboundPackets1_21.CUSTOM_REPORT_DETAILS);
        this.cancelClientbound(ClientboundPackets1_21.SERVER_LINKS);
        this.cancelClientbound(ClientboundConfigurationPackets1_21.CUSTOM_REPORT_DETAILS);
        this.cancelClientbound(ClientboundConfigurationPackets1_21.SERVER_LINKS);
        this.registerClientbound(ClientboundPackets1_21.DISGUISED_CHAT, wrapper -> {
            this.translatableRewriter.processTag(wrapper.user(), wrapper.passthrough(Types.TAG));
            Holder chatType = (Holder)((Object)wrapper.read(ChatType.TYPE));
            if (chatType.isDirect()) {
                wrapper.write(Types.VAR_INT, 0);
                return;
            }
            wrapper.write(Types.VAR_INT, chatType.id());
        });
        this.registerClientbound(ClientboundPackets1_21.PLAYER_CHAT, wrapper -> {
            Holder chatType;
            wrapper.passthrough(Types.UUID);
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.OPTIONAL_SIGNATURE_BYTES);
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.LONG);
            wrapper.passthrough(Types.LONG);
            int lastSeen = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < lastSeen; ++i) {
                int index2 = wrapper.passthrough(Types.VAR_INT);
                if (index2 != 0) continue;
                wrapper.passthrough(Types.SIGNATURE_BYTES);
            }
            wrapper.passthrough(Types.OPTIONAL_TAG);
            int filterMaskType = wrapper.passthrough(Types.VAR_INT);
            if (filterMaskType == 2) {
                wrapper.passthrough(Types.LONG_ARRAY_PRIMITIVE);
            }
            if ((chatType = (Holder)((Object)wrapper.read(ChatType.TYPE))).isDirect()) {
                wrapper.write(Types.VAR_INT, 0);
                return;
            }
            wrapper.write(Types.VAR_INT, chatType.id());
        });
        this.registerClientbound(ClientboundPackets1_21.UPDATE_ATTRIBUTES, wrapper -> {
            int size;
            wrapper.passthrough(Types.VAR_INT);
            int newSize = size = wrapper.passthrough(Types.VAR_INT).intValue();
            for (int i = 0; i < size; ++i) {
                int j;
                int modifierSize;
                int attributeId = wrapper.read(Types.VAR_INT);
                int mappedId = MAPPINGS.getNewAttributeId(attributeId);
                if (mappedId == -1) {
                    --newSize;
                    wrapper.read(Types.DOUBLE);
                    modifierSize = wrapper.read(Types.VAR_INT);
                    for (j = 0; j < modifierSize; ++j) {
                        wrapper.read(Types.STRING);
                        wrapper.read(Types.DOUBLE);
                        wrapper.read(Types.BYTE);
                    }
                    continue;
                }
                wrapper.write(Types.VAR_INT, mappedId);
                wrapper.passthrough(Types.DOUBLE);
                modifierSize = wrapper.passthrough(Types.VAR_INT);
                for (j = 0; j < modifierSize; ++j) {
                    String id = wrapper.read(Types.STRING);
                    wrapper.write(Types.UUID, Protocol1_20_5To1_21.mapAttributeId(id));
                    wrapper.passthrough(Types.DOUBLE);
                    wrapper.passthrough(Types.BYTE);
                }
            }
            if (size != newSize) {
                wrapper.set(Types.VAR_INT, 1, newSize);
            }
        });
        this.registerClientbound(ClientboundConfigurationPackets1_21.UPDATE_ENABLED_FEATURES, wrapper -> {
            String[] enabledFeatures = wrapper.read(Types.STRING_ARRAY);
            wrapper.write(Types.STRING_ARRAY, ArrayUtil.add(enabledFeatures, "minecraft:update_1_21"));
        });
    }

    @Override
    public void init(UserConnection user) {
        this.addEntityTracker(user, new EntityTrackerBase(user, EntityTypes1_20_5.PLAYER));
        user.put(new EnchantmentsPaintingsStorage());
        user.put(new OpenScreenStorage());
        user.put(new PlayerRotationStorage());
    }

    @Override
    public BackwardsMappingData getMappingData() {
        return MAPPINGS;
    }

    public EntityPacketRewriter1_21 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPacketRewriter1_21 getItemRewriter() {
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
    protected PacketTypesProvider<ClientboundPacket1_21, ClientboundPacket1_20_5, ServerboundPacket1_20_5, ServerboundPacket1_20_5> createPacketTypesProvider() {
        return new SimplePacketTypesProvider<ClientboundPacket1_21, ClientboundPacket1_20_5, ServerboundPacket1_20_5, ServerboundPacket1_20_5>(ProtocolUtil.packetTypeMap(this.unmappedClientboundPacketType, ClientboundPackets1_21.class, ClientboundConfigurationPackets1_21.class), ProtocolUtil.packetTypeMap(this.mappedClientboundPacketType, ClientboundPackets1_20_5.class, ClientboundConfigurationPackets1_20_5.class), ProtocolUtil.packetTypeMap(this.mappedServerboundPacketType, ServerboundPackets1_20_5.class, ServerboundConfigurationPackets1_20_5.class), ProtocolUtil.packetTypeMap(this.unmappedServerboundPacketType, ServerboundPackets1_20_5.class, ServerboundConfigurationPackets1_20_5.class));
    }
}

