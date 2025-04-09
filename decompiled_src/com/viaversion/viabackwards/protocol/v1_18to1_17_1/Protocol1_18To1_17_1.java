/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_18to1_17_1;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_18to1_17_1.data.BackwardsMappingData1_18;
import com.viaversion.viabackwards.protocol.v1_18to1_17_1.rewriter.BlockItemPacketRewriter1_18;
import com.viaversion.viabackwards.protocol.v1_18to1_17_1.rewriter.EntityPacketRewriter1_18;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_17;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.packet.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.v1_17to1_17_1.packet.ClientboundPackets1_17_1;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

public final class Protocol1_18To1_17_1
extends BackwardsProtocol<ClientboundPackets1_18, ClientboundPackets1_17_1, ServerboundPackets1_17, ServerboundPackets1_17> {
    static final BackwardsMappingData1_18 MAPPINGS = new BackwardsMappingData1_18();
    final EntityPacketRewriter1_18 entityRewriter = new EntityPacketRewriter1_18(this);
    final BlockItemPacketRewriter1_18 itemRewriter = new BlockItemPacketRewriter1_18(this);
    final TranslatableRewriter<ClientboundPackets1_18> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_18>(this, ComponentRewriter.ReadType.JSON);
    final TagRewriter<ClientboundPackets1_18> tagRewriter = new TagRewriter<ClientboundPackets1_18>(this);

    public Protocol1_18To1_17_1() {
        super(ClientboundPackets1_18.class, ClientboundPackets1_17_1.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.CHAT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.SET_ACTION_BAR_TEXT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.SET_TITLE_TEXT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.SET_SUBTITLE_TEXT);
        this.translatableRewriter.registerBossEvent(ClientboundPackets1_18.BOSS_EVENT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_18.TAB_LIST);
        this.translatableRewriter.registerOpenScreen(ClientboundPackets1_18.OPEN_SCREEN);
        this.translatableRewriter.registerPlayerCombatKill(ClientboundPackets1_18.PLAYER_COMBAT_KILL);
        this.translatableRewriter.registerPing();
        SoundRewriter<ClientboundPackets1_18> soundRewriter = new SoundRewriter<ClientboundPackets1_18>(this);
        soundRewriter.registerSound(ClientboundPackets1_18.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_18.SOUND_ENTITY);
        soundRewriter.registerStopSound(ClientboundPackets1_18.STOP_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_18.CUSTOM_SOUND);
        this.tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:lava_pool_stone_replaceables");
        this.tagRewriter.registerGeneric(ClientboundPackets1_18.UPDATE_TAGS);
        this.registerServerbound(ServerboundPackets1_17.CLIENT_INFORMATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.BYTE);
                this.map(Types.VAR_INT);
                this.map(Types.BOOLEAN);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.VAR_INT);
                this.map(Types.BOOLEAN);
                this.create(Types.BOOLEAN, true);
            }
        });
        this.registerClientbound(ClientboundPackets1_18.SET_OBJECTIVE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.handler(Protocol1_18To1_17_1.this.cutName(0, 16));
            }
        });
        this.registerClientbound(ClientboundPackets1_18.SET_DISPLAY_OBJECTIVE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types.STRING);
                this.handler(Protocol1_18To1_17_1.this.cutName(0, 16));
            }
        });
        this.registerClientbound(ClientboundPackets1_18.SET_PLAYER_TEAM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.handler(Protocol1_18To1_17_1.this.cutName(0, 16));
            }
        });
        this.registerClientbound(ClientboundPackets1_18.SET_SCORE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.VAR_INT);
                this.map(Types.STRING);
                this.handler(Protocol1_18To1_17_1.this.cutName(0, 40));
                this.handler(Protocol1_18To1_17_1.this.cutName(1, 16));
            }
        });
    }

    PacketHandler cutName(int index2, int maxLength) {
        return wrapper -> {
            String s = wrapper.get(Types.STRING, index2);
            if (s.length() > maxLength) {
                wrapper.set(Types.STRING, index2, s.substring(0, maxLength));
            }
        };
    }

    @Override
    public void init(UserConnection connection) {
        this.addEntityTracker(connection, new EntityTrackerBase(connection, EntityTypes1_17.PLAYER));
    }

    @Override
    public BackwardsMappingData1_18 getMappingData() {
        return MAPPINGS;
    }

    public EntityPacketRewriter1_18 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPacketRewriter1_18 getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_18> getComponentRewriter() {
        return this.translatableRewriter;
    }

    @Override
    public TagRewriter<ClientboundPackets1_18> getTagRewriter() {
        return this.tagRewriter;
    }
}

