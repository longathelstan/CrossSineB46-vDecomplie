/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_19_4to1_19_3;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_19_4to1_19_3.rewriter.BlockItemPacketRewriter1_19_4;
import com.viaversion.viabackwards.protocol.v1_19_4to1_19_3.rewriter.EntityPacketRewriter1_19_4;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ServerboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.Protocol1_19_3To1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ServerboundPackets1_19_4;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class Protocol1_19_4To1_19_3
extends BackwardsProtocol<ClientboundPackets1_19_4, ClientboundPackets1_19_3, ServerboundPackets1_19_4, ServerboundPackets1_19_3> {
    public static final BackwardsMappingData MAPPINGS = new BackwardsMappingData("1.19.4", "1.19.3", Protocol1_19_3To1_19_4.class);
    final EntityPacketRewriter1_19_4 entityRewriter = new EntityPacketRewriter1_19_4(this);
    final BlockItemPacketRewriter1_19_4 itemRewriter = new BlockItemPacketRewriter1_19_4(this);
    final TranslatableRewriter<ClientboundPackets1_19_4> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_19_4>(this, ComponentRewriter.ReadType.JSON);
    final TagRewriter<ClientboundPackets1_19_4> tagRewriter = new TagRewriter<ClientboundPackets1_19_4>(this);

    public Protocol1_19_4To1_19_3() {
        super(ClientboundPackets1_19_4.class, ClientboundPackets1_19_3.class, ServerboundPackets1_19_4.class, ServerboundPackets1_19_3.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        SoundRewriter<ClientboundPackets1_19_4> soundRewriter = new SoundRewriter<ClientboundPackets1_19_4>(this);
        soundRewriter.registerStopSound(ClientboundPackets1_19_4.STOP_SOUND);
        soundRewriter.registerSound1_19_3(ClientboundPackets1_19_4.SOUND);
        soundRewriter.registerSound1_19_3(ClientboundPackets1_19_4.SOUND_ENTITY);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.SET_ACTION_BAR_TEXT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.SET_TITLE_TEXT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.SET_SUBTITLE_TEXT);
        this.translatableRewriter.registerBossEvent(ClientboundPackets1_19_4.BOSS_EVENT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_19_4.TAB_LIST);
        this.translatableRewriter.registerPlayerCombatKill(ClientboundPackets1_19_4.PLAYER_COMBAT_KILL);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.SYSTEM_CHAT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_4.DISGUISED_CHAT);
        this.translatableRewriter.registerPing();
        new CommandRewriter<ClientboundPackets1_19_4>((Protocol)this){

            @Override
            public void handleArgument(PacketWrapper wrapper, String argumentType) {
                switch (argumentType) {
                    case "minecraft:heightmap": {
                        wrapper.write(Types.VAR_INT, 0);
                        break;
                    }
                    case "minecraft:time": {
                        wrapper.read(Types.INT);
                        break;
                    }
                    case "minecraft:resource": 
                    case "minecraft:resource_or_tag": {
                        String resource = wrapper.read(Types.STRING);
                        wrapper.write(Types.STRING, resource.equals("minecraft:damage_type") ? "minecraft:mob_effect" : resource);
                        break;
                    }
                    default: {
                        super.handleArgument(wrapper, argumentType);
                    }
                }
            }
        }.registerDeclareCommands1_19(ClientboundPackets1_19_4.COMMANDS);
        this.tagRewriter.removeTags("minecraft:damage_type");
        this.tagRewriter.registerGeneric(ClientboundPackets1_19_4.UPDATE_TAGS);
        new StatisticsRewriter<ClientboundPackets1_19_4>(this).register(ClientboundPackets1_19_4.AWARD_STATS);
        this.registerClientbound(ClientboundPackets1_19_4.SERVER_DATA, wrapper -> {
            String string;
            JsonElement element = wrapper.read(Types.COMPONENT);
            wrapper.write(Types.OPTIONAL_COMPONENT, element);
            byte[] iconBytes = wrapper.read(Types.OPTIONAL_BYTE_ARRAY_PRIMITIVE);
            if (iconBytes != null) {
                String string2 = new String(Base64.getEncoder().encode(iconBytes), StandardCharsets.UTF_8);
                string = "data:image/png;base64," + string2;
            } else {
                string = null;
            }
            String iconBase64 = string;
            wrapper.write(Types.OPTIONAL_STRING, iconBase64);
        });
        this.cancelClientbound(ClientboundPackets1_19_4.BUNDLE_DELIMITER);
        this.cancelClientbound(ClientboundPackets1_19_4.CHUNKS_BIOMES);
    }

    @Override
    public void init(UserConnection user) {
        this.addEntityTracker(user, new EntityTrackerBase(user, EntityTypes1_19_4.PLAYER));
    }

    @Override
    public BackwardsMappingData getMappingData() {
        return MAPPINGS;
    }

    public BlockItemPacketRewriter1_19_4 getItemRewriter() {
        return this.itemRewriter;
    }

    public EntityPacketRewriter1_19_4 getEntityRewriter() {
        return this.entityRewriter;
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

