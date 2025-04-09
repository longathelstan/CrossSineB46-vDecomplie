/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_18_2to1_19;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_19;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.packet.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.data.MappingData1_19;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.packet.ClientboundPackets1_19;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.packet.ServerboundPackets1_19;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.provider.AckSequenceProvider;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.rewriter.EntityPacketRewriter1_19;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.rewriter.ItemPacketRewriter1_19;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.rewriter.WorldPacketRewriter1_19;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.storage.DimensionRegistryStorage;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.storage.NonceStorage1_19;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.storage.SequenceStorage;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.CipherUtil;
import com.viaversion.viaversion.util.ComponentUtil;
import java.util.concurrent.ThreadLocalRandom;

public final class Protocol1_18_2To1_19
extends AbstractProtocol<ClientboundPackets1_18, ClientboundPackets1_19, ServerboundPackets1_17, ServerboundPackets1_19> {
    public static final MappingData1_19 MAPPINGS = new MappingData1_19();
    final EntityPacketRewriter1_19 entityRewriter = new EntityPacketRewriter1_19(this);
    final ItemPacketRewriter1_19 itemRewriter = new ItemPacketRewriter1_19(this);
    final TagRewriter<ClientboundPackets1_18> tagRewriter = new TagRewriter<ClientboundPackets1_18>(this);

    public Protocol1_18_2To1_19() {
        super(ClientboundPackets1_18.class, ClientboundPackets1_19.class, ServerboundPackets1_17.class, ServerboundPackets1_19.class);
    }

    public static boolean isTextComponentNull(JsonElement element) {
        return element == null || element.isJsonNull() || element.isJsonArray() && element.getAsJsonArray().isEmpty();
    }

    public static JsonElement mapTextComponentIfNull(JsonElement component) {
        if (!Protocol1_18_2To1_19.isTextComponentNull(component)) {
            return component;
        }
        return ComponentUtil.emptyJsonComponent();
    }

    @Override
    protected void registerPackets() {
        this.tagRewriter.registerGeneric(ClientboundPackets1_18.UPDATE_TAGS);
        this.entityRewriter.register();
        this.itemRewriter.register();
        WorldPacketRewriter1_19.register(this);
        this.cancelClientbound(ClientboundPackets1_18.ADD_VIBRATION_SIGNAL);
        final SoundRewriter<ClientboundPackets1_18> soundRewriter = new SoundRewriter<ClientboundPackets1_18>(this);
        this.registerClientbound(ClientboundPackets1_18.SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.handler(wrapper -> wrapper.write(Types.LONG, Protocol1_18_2To1_19.randomLong()));
                this.handler(soundRewriter.getSoundHandler());
            }
        });
        this.registerClientbound(ClientboundPackets1_18.SOUND_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.handler(wrapper -> wrapper.write(Types.LONG, Protocol1_18_2To1_19.randomLong()));
                this.handler(soundRewriter.getSoundHandler());
            }
        });
        this.registerClientbound(ClientboundPackets1_18.CUSTOM_SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.VAR_INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.handler(wrapper -> wrapper.write(Types.LONG, Protocol1_18_2To1_19.randomLong()));
            }
        });
        new StatisticsRewriter<ClientboundPackets1_18>(this).register(ClientboundPackets1_18.AWARD_STATS);
        PacketHandler singleNullTextComponentMapper = wrapper -> wrapper.write(Types.COMPONENT, Protocol1_18_2To1_19.mapTextComponentIfNull(wrapper.read(Types.COMPONENT)));
        this.registerClientbound(ClientboundPackets1_18.SET_TITLE_TEXT, singleNullTextComponentMapper);
        this.registerClientbound(ClientboundPackets1_18.SET_SUBTITLE_TEXT, singleNullTextComponentMapper);
        this.registerClientbound(ClientboundPackets1_18.SET_ACTION_BAR_TEXT, singleNullTextComponentMapper);
        this.registerClientbound(ClientboundPackets1_18.SET_OBJECTIVE, wrapper -> {
            wrapper.passthrough(Types.STRING);
            byte action = wrapper.passthrough(Types.BYTE);
            if (action == 0 || action == 2) {
                wrapper.write(Types.COMPONENT, Protocol1_18_2To1_19.mapTextComponentIfNull(wrapper.read(Types.COMPONENT)));
            }
        });
        this.registerClientbound(ClientboundPackets1_18.SET_PLAYER_TEAM, wrapper -> {
            wrapper.passthrough(Types.STRING);
            byte action = wrapper.passthrough(Types.BYTE);
            if (action == 0 || action == 2) {
                wrapper.write(Types.COMPONENT, Protocol1_18_2To1_19.mapTextComponentIfNull(wrapper.read(Types.COMPONENT)));
                wrapper.passthrough(Types.BYTE);
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.VAR_INT);
                wrapper.write(Types.COMPONENT, Protocol1_18_2To1_19.mapTextComponentIfNull(wrapper.read(Types.COMPONENT)));
                wrapper.write(Types.COMPONENT, Protocol1_18_2To1_19.mapTextComponentIfNull(wrapper.read(Types.COMPONENT)));
            }
        });
        CommandRewriter<ClientboundPackets1_18> commandRewriter = new CommandRewriter<ClientboundPackets1_18>(this);
        this.registerClientbound(ClientboundPackets1_18.COMMANDS, wrapper -> {
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                int nodeType;
                byte flags = wrapper.passthrough(Types.BYTE);
                wrapper.passthrough(Types.VAR_INT_ARRAY_PRIMITIVE);
                if ((flags & 8) != 0) {
                    wrapper.passthrough(Types.VAR_INT);
                }
                if ((nodeType = flags & 3) == 1 || nodeType == 2) {
                    wrapper.passthrough(Types.STRING);
                }
                if (nodeType != 2) continue;
                String argumentType = wrapper.read(Types.STRING);
                int argumentTypeId = MAPPINGS.getArgumentTypeMappings().mappedId(argumentType);
                if (argumentTypeId == -1) {
                    String string = argumentType;
                    this.getLogger().warning("Unknown command argument type: " + string);
                }
                wrapper.write(Types.VAR_INT, argumentTypeId);
                commandRewriter.handleArgument(wrapper, argumentType);
                if ((flags & 0x10) == 0) continue;
                wrapper.passthrough(Types.STRING);
            }
            wrapper.passthrough(Types.VAR_INT);
        });
        this.registerClientbound(ClientboundPackets1_18.CHAT, ClientboundPackets1_19.SYSTEM_CHAT, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.COMPONENT);
                this.handler(wrapper -> {
                    byte type = wrapper.read(Types.BYTE);
                    wrapper.write(Types.VAR_INT, Integer.valueOf(type == 0 ? (byte)1 : type));
                });
                this.read(Types.UUID);
            }
        });
        this.registerServerbound(ServerboundPackets1_19.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.read(Types.LONG);
                this.read(Types.LONG);
                this.read(Types.BYTE_ARRAY_PRIMITIVE);
                this.read(Types.BOOLEAN);
            }
        });
        this.registerServerbound(ServerboundPackets1_19.CHAT_COMMAND, ServerboundPackets1_17.CHAT, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.read(Types.LONG);
                this.read(Types.LONG);
                this.handler(wrapper -> {
                    String command;
                    String string = command = wrapper.get(Types.STRING, 0);
                    wrapper.set(Types.STRING, 0, "/" + string);
                    int signatures = wrapper.read(Types.VAR_INT);
                    for (int i = 0; i < signatures; ++i) {
                        wrapper.read(Types.STRING);
                        wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
                    }
                });
                this.read(Types.BOOLEAN);
            }
        });
        this.cancelServerbound(ServerboundPackets1_19.CHAT_PREVIEW);
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UUID);
                this.map(Types.STRING);
                this.create(Types.VAR_INT, 0);
            }
        });
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.handler(wrapper -> {
                    byte[] publicKey = wrapper.passthrough(Types.BYTE_ARRAY_PRIMITIVE);
                    byte[] nonce = wrapper.passthrough(Types.BYTE_ARRAY_PRIMITIVE);
                    wrapper.user().put(new NonceStorage1_19(CipherUtil.encryptNonce(publicKey, nonce)));
                });
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.read(Types.OPTIONAL_PROFILE_KEY);
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE_ARRAY_PRIMITIVE);
                this.handler(wrapper -> {
                    if (wrapper.read(Types.BOOLEAN).booleanValue()) {
                        wrapper.passthrough(Types.BYTE_ARRAY_PRIMITIVE);
                    } else {
                        NonceStorage1_19 nonceStorage = wrapper.user().remove(NonceStorage1_19.class);
                        if (nonceStorage == null) {
                            throw new IllegalArgumentException("Server sent nonce is missing");
                        }
                        wrapper.read(Types.LONG);
                        wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
                        wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce());
                    }
                });
            }
        });
    }

    static long randomLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    @Override
    protected void onMappingDataLoaded() {
        Types1_19.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("block_marker", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION).reader("item", ParticleType.Readers.ITEM1_13_2).reader("vibration", ParticleType.Readers.VIBRATION1_19).reader("sculk_charge", ParticleType.Readers.SCULK_CHARGE).reader("shriek", ParticleType.Readers.SHRIEK);
        EntityTypes1_19.initialize(this);
        this.tagRewriter.removeTag(RegistryType.ITEM, "minecraft:occludes_vibration_signals");
        this.tagRewriter.renameTag(RegistryType.ITEM, "minecraft:carpets", "minecraft:wool_carpets");
        this.tagRewriter.renameTag(RegistryType.BLOCK, "minecraft:carpets", "minecraft:wool_carpets");
        this.tagRewriter.renameTag(RegistryType.BLOCK, "minecraft:polar_bears_spawnable_on_in_frozen_ocean", "minecraft:polar_bears_spawnable_on_alternate");
        this.tagRewriter.addEmptyTags(RegistryType.ITEM, "minecraft:chest_boats", "minecraft:dampens_vibrations", "minecraft:mangrove_logs", "minecraft:overworld_natural_logs");
        this.tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:ancient_city_replaceable", "minecraft:convertable_to_mud", "minecraft:dampens_vibrations", "minecraft:frog_prefer_jump_to", "minecraft:frogs_spawnable_on", "minecraft:mangrove_logs", "minecraft:mangrove_logs_can_grow_through", "minecraft:mangrove_roots_can_grow_through", "minecraft:nether_carver_replaceables", "minecraft:overworld_carver_replaceables", "minecraft:overworld_natural_logs", "minecraft:sculk_replaceable", "minecraft:sculk_replaceable_world_gen", "minecraft:snaps_goat_horn");
        this.tagRewriter.addEmptyTag(RegistryType.ENTITY, "minecraft:frog_food");
        this.tagRewriter.addEmptyTags(RegistryType.GAME_EVENT, "minecraft:allay_can_listen", "minecraft:shrieker_can_listen", "minecraft:warden_can_listen");
        super.onMappingDataLoaded();
    }

    @Override
    public void register(ViaProviders providers) {
        providers.register(AckSequenceProvider.class, new AckSequenceProvider());
    }

    @Override
    public void init(UserConnection user) {
        if (!user.has(DimensionRegistryStorage.class)) {
            user.put(new DimensionRegistryStorage());
        }
        user.put(new SequenceStorage());
        this.addEntityTracker(user, new EntityTrackerBase(user, EntityTypes1_19.PLAYER));
    }

    @Override
    public MappingData1_19 getMappingData() {
        return MAPPINGS;
    }

    public EntityPacketRewriter1_19 getEntityRewriter() {
        return this.entityRewriter;
    }

    public ItemPacketRewriter1_19 getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public TagRewriter<ClientboundPackets1_18> getTagRewriter() {
        return this.tagRewriter;
    }
}

