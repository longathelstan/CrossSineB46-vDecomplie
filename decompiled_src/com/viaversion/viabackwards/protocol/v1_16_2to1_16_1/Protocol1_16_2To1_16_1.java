/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_16_2to1_16_1;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.rewriter.BlockItemPacketRewriter1_16_2;
import com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.rewriter.CommandRewriter1_16_2;
import com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.rewriter.EntityPacketRewriter1_16_2;
import com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.storage.BiomeStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16_2;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ServerboundPackets1_16_2;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.ProtocolLogger;

public class Protocol1_16_2To1_16_1
extends BackwardsProtocol<ClientboundPackets1_16_2, ClientboundPackets1_16, ServerboundPackets1_16_2, ServerboundPackets1_16> {
    public static final BackwardsMappingData MAPPINGS = new BackwardsMappingData("1.16.2", "1.16", Protocol1_16_1To1_16_2.class);
    public static final ProtocolLogger LOGGER = new ProtocolLogger(Protocol1_16_2To1_16_1.class);
    private final EntityPacketRewriter1_16_2 entityRewriter = new EntityPacketRewriter1_16_2(this);
    private final BlockItemPacketRewriter1_16_2 blockItemPackets = new BlockItemPacketRewriter1_16_2(this);
    private final TranslatableRewriter<ClientboundPackets1_16_2> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_16_2>(this, ComponentRewriter.ReadType.JSON);
    private final TagRewriter<ClientboundPackets1_16_2> tagRewriter = new TagRewriter<ClientboundPackets1_16_2>(this);

    public Protocol1_16_2To1_16_1() {
        super(ClientboundPackets1_16_2.class, ClientboundPackets1_16.class, ServerboundPackets1_16_2.class, ServerboundPackets1_16.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.translatableRewriter.registerBossEvent(ClientboundPackets1_16_2.BOSS_EVENT);
        this.translatableRewriter.registerPlayerCombat(ClientboundPackets1_16_2.PLAYER_COMBAT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_16_2.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_16_2.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_16_2.SET_TITLES);
        this.translatableRewriter.registerOpenScreen(ClientboundPackets1_16_2.OPEN_SCREEN);
        this.translatableRewriter.registerPing();
        new CommandRewriter1_16_2(this).registerDeclareCommands(ClientboundPackets1_16_2.COMMANDS);
        SoundRewriter<ClientboundPackets1_16_2> soundRewriter = new SoundRewriter<ClientboundPackets1_16_2>(this);
        soundRewriter.registerSound(ClientboundPackets1_16_2.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_16_2.SOUND_ENTITY);
        soundRewriter.registerNamedSound(ClientboundPackets1_16_2.CUSTOM_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_16_2.STOP_SOUND);
        this.registerClientbound(ClientboundPackets1_16_2.CHAT, wrapper -> {
            JsonElement message = wrapper.passthrough(Types.COMPONENT);
            this.translatableRewriter.processText(wrapper.user(), message);
            byte position = wrapper.passthrough(Types.BYTE);
            if (position == 2) {
                wrapper.clearPacket();
                wrapper.setPacketType(ClientboundPackets1_16.SET_TITLES);
                wrapper.write(Types.VAR_INT, 2);
                wrapper.write(Types.COMPONENT, message);
            }
        });
        this.registerServerbound(ServerboundPackets1_16.RECIPE_BOOK_UPDATE, ServerboundPackets1_16_2.RECIPE_BOOK_CHANGE_SETTINGS, (PacketWrapper wrapper) -> {
            int type = wrapper.read(Types.VAR_INT);
            if (type == 0) {
                wrapper.passthrough(Types.STRING);
                wrapper.setPacketType(ServerboundPackets1_16_2.RECIPE_BOOK_SEEN_RECIPE);
            } else {
                wrapper.cancel();
                for (int i = 0; i < 3; ++i) {
                    Protocol1_16_2To1_16_1.sendSeenRecipePacket(i, wrapper);
                }
            }
        });
        this.tagRewriter.register(ClientboundPackets1_16_2.UPDATE_TAGS, RegistryType.ENTITY);
        new StatisticsRewriter<ClientboundPackets1_16_2>(this).register(ClientboundPackets1_16_2.AWARD_STATS);
    }

    private static void sendSeenRecipePacket(int recipeType, PacketWrapper wrapper) {
        boolean open = wrapper.read(Types.BOOLEAN);
        boolean filter = wrapper.read(Types.BOOLEAN);
        PacketWrapper newPacket = wrapper.create(ServerboundPackets1_16_2.RECIPE_BOOK_CHANGE_SETTINGS);
        newPacket.write(Types.VAR_INT, recipeType);
        newPacket.write(Types.BOOLEAN, open);
        newPacket.write(Types.BOOLEAN, filter);
        newPacket.sendToServer(Protocol1_16_2To1_16_1.class);
    }

    @Override
    public void init(UserConnection user) {
        user.put(new BiomeStorage());
        user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, EntityTypes1_16_2.PLAYER));
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_16_2> getComponentRewriter() {
        return this.translatableRewriter;
    }

    @Override
    public BackwardsMappingData getMappingData() {
        return MAPPINGS;
    }

    @Override
    public ProtocolLogger getLogger() {
        return LOGGER;
    }

    public EntityPacketRewriter1_16_2 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPacketRewriter1_16_2 getItemRewriter() {
        return this.blockItemPackets;
    }

    @Override
    public TagRewriter<ClientboundPackets1_16_2> getTagRewriter() {
        return this.tagRewriter;
    }
}

