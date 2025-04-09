/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_15_2to1_16;

import com.google.common.base.Joiner;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ClientboundStatusPackets;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.provider.PlayerAbilitiesProvider;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.rewriter.ComponentRewriter1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.rewriter.EntityPacketRewriter1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.rewriter.ItemPacketRewriter1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.rewriter.WorldPacketRewriter1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.storage.InventoryTracker1_16;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.GsonUtil;
import com.viaversion.viaversion.util.Key;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

public class Protocol1_15_2To1_16
extends AbstractProtocol<ClientboundPackets1_15, ClientboundPackets1_16, ServerboundPackets1_14, ServerboundPackets1_16> {
    static final UUID ZERO_UUID = new UUID(0L, 0L);
    public static final MappingData MAPPINGS = new MappingDataBase("1.15", "1.16");
    final EntityPacketRewriter1_16 entityRewriter = new EntityPacketRewriter1_16(this);
    final ItemPacketRewriter1_16 itemRewriter = new ItemPacketRewriter1_16(this);
    final ComponentRewriter1_16 componentRewriter = new ComponentRewriter1_16(this);
    final TagRewriter<ClientboundPackets1_15> tagRewriter = new TagRewriter<ClientboundPackets1_15>(this);

    public Protocol1_15_2To1_16() {
        super(ClientboundPackets1_15.class, ClientboundPackets1_16.class, ServerboundPackets1_14.class, ServerboundPackets1_16.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        WorldPacketRewriter1_16.register(this);
        this.tagRewriter.register(ClientboundPackets1_15.UPDATE_TAGS, RegistryType.ENTITY);
        new StatisticsRewriter<ClientboundPackets1_15>(this).register(ClientboundPackets1_15.AWARD_STATS);
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE, (PacketWrapper wrapper) -> {
            UUID uuid = UUID.fromString(wrapper.read(Types.STRING));
            wrapper.write(Types.UUID, uuid);
        });
        this.registerClientbound(State.STATUS, ClientboundStatusPackets.STATUS_RESPONSE, (PacketWrapper wrapper) -> {
            String original = wrapper.passthrough(Types.STRING);
            JsonObject object = GsonUtil.getGson().fromJson(original, JsonObject.class);
            JsonObject players = object.getAsJsonObject("players");
            if (players == null) {
                return;
            }
            JsonArray sample = players.getAsJsonArray("sample");
            if (sample == null) {
                return;
            }
            JsonArray splitSamples = new JsonArray();
            for (JsonElement element : sample) {
                JsonObject playerInfo = element.getAsJsonObject();
                String name = playerInfo.getAsJsonPrimitive("name").getAsString();
                if (name.indexOf(10) == -1) {
                    splitSamples.add(playerInfo);
                    continue;
                }
                String id = playerInfo.getAsJsonPrimitive("id").getAsString();
                for (String s : name.split("\n")) {
                    JsonObject newSample = new JsonObject();
                    newSample.addProperty("name", s);
                    newSample.addProperty("id", id);
                    splitSamples.add(newSample);
                }
            }
            if (splitSamples.size() != sample.size()) {
                players.add("sample", splitSamples);
                wrapper.set(Types.STRING, 0, object.toString());
            }
        });
        this.registerClientbound(ClientboundPackets1_15.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.COMPONENT);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    Protocol1_15_2To1_16.this.componentRewriter.processText(wrapper.user(), wrapper.get(Types.COMPONENT, 0));
                    wrapper.write(Types.UUID, ZERO_UUID);
                });
            }
        });
        this.componentRewriter.registerBossEvent(ClientboundPackets1_15.BOSS_EVENT);
        this.componentRewriter.registerTitle(ClientboundPackets1_15.SET_TITLES);
        this.componentRewriter.registerPlayerCombat(ClientboundPackets1_15.PLAYER_COMBAT);
        SoundRewriter<ClientboundPackets1_15> soundRewriter = new SoundRewriter<ClientboundPackets1_15>(this);
        soundRewriter.registerSound(ClientboundPackets1_15.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_15.SOUND_ENTITY);
        this.registerServerbound(ServerboundPackets1_16.INTERACT, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            int action = wrapper.passthrough(Types.VAR_INT);
            if (action == 0 || action == 2) {
                if (action == 2) {
                    wrapper.passthrough(Types.FLOAT);
                    wrapper.passthrough(Types.FLOAT);
                    wrapper.passthrough(Types.FLOAT);
                }
                wrapper.passthrough(Types.VAR_INT);
            }
            wrapper.read(Types.BOOLEAN);
        });
        if (Via.getConfig().isIgnoreLong1_16ChannelNames()) {
            this.registerServerbound(ServerboundPackets1_16.CUSTOM_PAYLOAD, new PacketHandlers(){

                @Override
                public void register() {
                    this.map(Types.STRING);
                    this.handler(wrapper -> {
                        String channel = wrapper.get(Types.STRING, 0);
                        String namespacedChannel = Key.namespaced(channel);
                        if (channel.length() > 32) {
                            if (!Via.getConfig().isSuppressConversionWarnings()) {
                                String string = channel;
                                Protocol1_15_2To1_16.this.getLogger().warning("Ignoring serverbound plugin channel, as it is longer than 32 characters: " + string);
                            }
                            wrapper.cancel();
                        } else if (namespacedChannel.equals("minecraft:register") || namespacedChannel.equals("minecraft:unregister")) {
                            String[] channels = new String(wrapper.read(Types.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                            ArrayList<String> checkedChannels = new ArrayList<String>(channels.length);
                            for (String registeredChannel : channels) {
                                if (registeredChannel.length() > 32) {
                                    if (Via.getConfig().isSuppressConversionWarnings()) continue;
                                    String string = registeredChannel;
                                    Protocol1_15_2To1_16.this.getLogger().warning("Ignoring serverbound plugin channel register of '" + string + "', as it is longer than 32 characters");
                                    continue;
                                }
                                checkedChannels.add(registeredChannel);
                            }
                            if (checkedChannels.isEmpty()) {
                                wrapper.cancel();
                                return;
                            }
                            wrapper.write(Types.REMAINING_BYTES, Joiner.on((char)'\u0000').join(checkedChannels).getBytes(StandardCharsets.UTF_8));
                        }
                    });
                }
            });
        }
        this.registerServerbound(ServerboundPackets1_16.PLAYER_ABILITIES, wrapper -> {
            wrapper.passthrough(Types.BYTE);
            PlayerAbilitiesProvider playerAbilities = Via.getManager().getProviders().get(PlayerAbilitiesProvider.class);
            wrapper.write(Types.FLOAT, Float.valueOf(playerAbilities.getFlyingSpeed(wrapper.user())));
            wrapper.write(Types.FLOAT, Float.valueOf(playerAbilities.getWalkingSpeed(wrapper.user())));
        });
        this.cancelServerbound(ServerboundPackets1_16.JIGSAW_GENERATE);
        this.cancelServerbound(ServerboundPackets1_16.SET_JIGSAW_BLOCK);
    }

    @Override
    protected void onMappingDataLoaded() {
        EntityTypes1_16.initialize(this);
        Types1_16.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("item", ParticleType.Readers.ITEM1_13_2);
        this.tagRewriter.addEmptyTags(RegistryType.ITEM, "minecraft:crimson_stems", "minecraft:non_flammable_wood", "minecraft:piglin_loved", "minecraft:piglin_repellents", "minecraft:soul_fire_base_blocks", "minecraft:warped_stems");
        this.tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:crimson_stems", "minecraft:guarded_by_piglins", "minecraft:hoglin_repellents", "minecraft:non_flammable_wood", "minecraft:nylium", "minecraft:piglin_repellents", "minecraft:soul_fire_base_blocks", "minecraft:soul_speed_blocks", "minecraft:strider_warm_blocks", "minecraft:warped_stems");
        super.onMappingDataLoaded();
    }

    @Override
    public void register(ViaProviders providers) {
        providers.register(PlayerAbilitiesProvider.class, new PlayerAbilitiesProvider());
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, EntityTypes1_16.PLAYER));
        userConnection.put(new InventoryTracker1_16());
    }

    @Override
    public MappingData getMappingData() {
        return MAPPINGS;
    }

    public EntityPacketRewriter1_16 getEntityRewriter() {
        return this.entityRewriter;
    }

    public ItemPacketRewriter1_16 getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public ComponentRewriter1_16 getComponentRewriter() {
        return this.componentRewriter;
    }

    @Override
    public TagRewriter<ClientboundPackets1_15> getTagRewriter() {
        return this.tagRewriter;
    }
}

