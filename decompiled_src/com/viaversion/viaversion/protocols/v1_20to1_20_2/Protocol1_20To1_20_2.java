/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20to1_20_2;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ServerboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundPackets1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ServerboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ServerboundPackets1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.rewriter.BlockItemPacketRewriter1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.rewriter.EntityPacketRewriter1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.storage.ConfigurationState;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.storage.LastResourcePack;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.storage.LastTags;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.Key;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Protocol1_20To1_20_2
extends AbstractProtocol<ClientboundPackets1_19_4, ClientboundPackets1_20_2, ServerboundPackets1_19_4, ServerboundPackets1_20_2> {
    public static final MappingData MAPPINGS = new MappingDataBase("1.20", "1.20.2");
    final EntityPacketRewriter1_20_2 entityPacketRewriter = new EntityPacketRewriter1_20_2(this);
    final BlockItemPacketRewriter1_20_2 itemPacketRewriter = new BlockItemPacketRewriter1_20_2(this);
    final TagRewriter<ClientboundPackets1_19_4> tagRewriter = new TagRewriter<ClientboundPackets1_19_4>(this);

    public Protocol1_20To1_20_2() {
        super(ClientboundPackets1_19_4.class, ClientboundPackets1_20_2.class, ServerboundPackets1_19_4.class, ServerboundPackets1_20_2.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        SoundRewriter<ClientboundPackets1_19_4> soundRewriter = new SoundRewriter<ClientboundPackets1_19_4>(this);
        soundRewriter.registerSound1_19_3(ClientboundPackets1_19_4.SOUND);
        soundRewriter.registerSound1_19_3(ClientboundPackets1_19_4.SOUND_ENTITY);
        this.registerClientbound(ClientboundPackets1_19_4.CUSTOM_PAYLOAD, new PacketHandlers(){

            @Override
            protected void register() {
                this.map(Types.STRING);
                this.handlerSoftFail(Protocol1_20To1_20_2::sanitizeCustomPayload);
            }
        });
        this.registerServerbound(ServerboundPackets1_20_2.CUSTOM_PAYLOAD, wrapper -> {
            wrapper.passthrough(Types.STRING);
            Protocol1_20To1_20_2.sanitizeCustomPayload(wrapper);
        });
        this.registerClientbound(ClientboundPackets1_19_4.SYSTEM_CHAT, wrapper -> {
            JsonElement translate;
            JsonObject object;
            if (wrapper.user().isClientSide() || Via.getPlatform().isProxy()) {
                return;
            }
            JsonElement component = wrapper.passthrough(Types.COMPONENT);
            if (component instanceof JsonObject && (object = (JsonObject)component).has("translate") && (translate = object.get("translate")) != null && translate.getAsString().equals("multiplayer.message_not_delivered")) {
                wrapper.cancel();
            }
        });
        this.registerClientbound(ClientboundPackets1_19_4.RESOURCE_PACK, wrapper -> {
            String url = wrapper.passthrough(Types.STRING);
            String hash = wrapper.passthrough(Types.STRING);
            boolean required = wrapper.passthrough(Types.BOOLEAN);
            JsonElement prompt = wrapper.passthrough(Types.OPTIONAL_COMPONENT);
            wrapper.user().put(new LastResourcePack(url, hash, required, prompt));
        });
        this.registerClientbound(ClientboundPackets1_19_4.UPDATE_TAGS, wrapper -> {
            this.tagRewriter.handleGeneric(wrapper);
            wrapper.resetReader();
            wrapper.user().put(new LastTags(wrapper));
        });
        this.registerClientbound(State.CONFIGURATION, ClientboundConfigurationPackets1_20_2.UPDATE_TAGS, (PacketWrapper wrapper) -> {
            this.tagRewriter.handleGeneric(wrapper);
            wrapper.resetReader();
            wrapper.user().put(new LastTags(wrapper));
        });
        this.registerClientbound(ClientboundPackets1_19_4.SET_DISPLAY_OBJECTIVE, wrapper -> {
            byte slot = wrapper.read(Types.BYTE);
            wrapper.write(Types.VAR_INT, Integer.valueOf(slot));
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO, (PacketWrapper wrapper) -> {
            wrapper.passthrough(Types.STRING);
            UUID uuid = wrapper.read(Types.UUID);
            wrapper.write(Types.OPTIONAL_UUID, uuid);
        });
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE, (PacketWrapper wrapper) -> {
            wrapper.user().get(ConfigurationState.class).setBridgePhase(ConfigurationState.BridgePhase.PROFILE_SENT);
            wrapper.user().getProtocolInfo().setServerState(State.PLAY);
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.LOGIN_ACKNOWLEDGED.getId(), -1, wrapper -> {
            wrapper.cancel();
            wrapper.user().getProtocolInfo().setServerState(State.PLAY);
            ConfigurationState configurationState = wrapper.user().get(ConfigurationState.class);
            configurationState.setBridgePhase(ConfigurationState.BridgePhase.CONFIGURATION);
            configurationState.sendQueuedPackets(wrapper.user());
        });
        this.registerServerbound(State.CONFIGURATION, ServerboundConfigurationPackets1_20_2.FINISH_CONFIGURATION.getId(), -1, wrapper -> {
            wrapper.cancel();
            wrapper.user().getProtocolInfo().setClientState(State.PLAY);
            ConfigurationState configurationState = wrapper.user().get(ConfigurationState.class);
            configurationState.setBridgePhase(ConfigurationState.BridgePhase.NONE);
            configurationState.sendQueuedPackets(wrapper.user());
            configurationState.clear();
        });
        this.registerServerbound(State.CONFIGURATION, ServerboundConfigurationPackets1_20_2.CLIENT_INFORMATION.getId(), -1, wrapper -> {
            ConfigurationState.ClientInformation clientInformation = new ConfigurationState.ClientInformation(wrapper.read(Types.STRING), wrapper.read(Types.BYTE), wrapper.read(Types.VAR_INT), wrapper.read(Types.BOOLEAN), wrapper.read(Types.UNSIGNED_BYTE), wrapper.read(Types.VAR_INT), wrapper.read(Types.BOOLEAN), wrapper.read(Types.BOOLEAN));
            ConfigurationState configurationState = wrapper.user().get(ConfigurationState.class);
            configurationState.setClientInformation(clientInformation);
            wrapper.cancel();
        });
        this.registerServerbound(State.CONFIGURATION, ServerboundConfigurationPackets1_20_2.CUSTOM_PAYLOAD.getId(), -1, this.queueServerboundPacket(ServerboundPackets1_20_2.CUSTOM_PAYLOAD));
        this.registerServerbound(State.CONFIGURATION, ServerboundConfigurationPackets1_20_2.KEEP_ALIVE.getId(), -1, this.queueServerboundPacket(ServerboundPackets1_20_2.KEEP_ALIVE));
        this.registerServerbound(State.CONFIGURATION, ServerboundConfigurationPackets1_20_2.PONG.getId(), -1, this.queueServerboundPacket(ServerboundPackets1_20_2.PONG));
        this.registerServerbound(State.CONFIGURATION, ServerboundConfigurationPackets1_20_2.RESOURCE_PACK.getId(), -1, PacketWrapper::cancel);
        this.cancelClientbound(ClientboundPackets1_19_4.UPDATE_ENABLED_FEATURES);
        this.registerServerbound(ServerboundPackets1_20_2.CONFIGURATION_ACKNOWLEDGED, null, (PacketWrapper wrapper) -> {
            wrapper.cancel();
            ConfigurationState configurationState = wrapper.user().get(ConfigurationState.class);
            if (configurationState.bridgePhase() != ConfigurationState.BridgePhase.REENTERING_CONFIGURATION) {
                return;
            }
            wrapper.user().getProtocolInfo().setClientState(State.CONFIGURATION);
            configurationState.setBridgePhase(ConfigurationState.BridgePhase.CONFIGURATION);
            LastResourcePack lastResourcePack = wrapper.user().get(LastResourcePack.class);
            Protocol1_20To1_20_2.sendConfigurationPackets(wrapper.user(), configurationState.lastDimensionRegistry(), lastResourcePack);
        });
        this.cancelServerbound(ServerboundPackets1_20_2.CHUNK_BATCH_RECEIVED);
        this.registerServerbound(ServerboundPackets1_20_2.PING_REQUEST, null, (PacketWrapper wrapper) -> {
            wrapper.cancel();
            long time = wrapper.read(Types.LONG);
            PacketWrapper responsePacket = wrapper.create(ClientboundPackets1_20_2.PONG_RESPONSE);
            responsePacket.write(Types.LONG, time);
            responsePacket.sendFuture(Protocol1_20To1_20_2.class);
        });
    }

    static void sanitizeCustomPayload(PacketWrapper wrapper) {
        String channel = Key.namespaced(wrapper.get(Types.STRING, 0));
        if (channel.equals("minecraft:brand")) {
            wrapper.passthrough(Types.STRING);
            wrapper.clearInputBuffer();
        }
    }

    @Override
    public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws InformativeException, CancelException {
        if (direction == Direction.SERVERBOUND) {
            super.transform(direction, state, packetWrapper);
            return;
        }
        ConfigurationState configurationBridge = packetWrapper.user().get(ConfigurationState.class);
        if (configurationBridge == null) {
            return;
        }
        ConfigurationState.BridgePhase phase = configurationBridge.bridgePhase();
        if (phase == ConfigurationState.BridgePhase.NONE) {
            super.transform(direction, state, packetWrapper);
            return;
        }
        int unmappedId = packetWrapper.getId();
        if (phase == ConfigurationState.BridgePhase.PROFILE_SENT || phase == ConfigurationState.BridgePhase.REENTERING_CONFIGURATION) {
            if (unmappedId == ClientboundPackets1_19_4.UPDATE_TAGS.getId()) {
                packetWrapper.user().remove(LastTags.class);
            }
            configurationBridge.addPacketToQueue(packetWrapper, true);
            throw CancelException.generate();
        }
        if (packetWrapper.getPacketType() == null || packetWrapper.getPacketType().state() != State.CONFIGURATION) {
            if (unmappedId == ClientboundPackets1_19_4.LOGIN.getId()) {
                super.transform(direction, State.PLAY, packetWrapper);
                return;
            }
            if (configurationBridge.queuedOrSentJoinGame()) {
                if (!packetWrapper.user().isClientSide() && !Via.getPlatform().isProxy() && unmappedId == ClientboundPackets1_19_4.SYSTEM_CHAT.getId()) {
                    super.transform(direction, State.PLAY, packetWrapper);
                    return;
                }
                configurationBridge.addPacketToQueue(packetWrapper, true);
                throw CancelException.generate();
            }
            if (unmappedId == ClientboundPackets1_19_4.CUSTOM_PAYLOAD.getId()) {
                packetWrapper.setPacketType(ClientboundConfigurationPackets1_20_2.CUSTOM_PAYLOAD);
            } else if (unmappedId == ClientboundPackets1_19_4.DISCONNECT.getId()) {
                packetWrapper.setPacketType(ClientboundConfigurationPackets1_20_2.DISCONNECT);
            } else if (unmappedId == ClientboundPackets1_19_4.KEEP_ALIVE.getId()) {
                packetWrapper.setPacketType(ClientboundConfigurationPackets1_20_2.KEEP_ALIVE);
            } else if (unmappedId == ClientboundPackets1_19_4.PING.getId()) {
                packetWrapper.setPacketType(ClientboundConfigurationPackets1_20_2.PING);
            } else if (unmappedId == ClientboundPackets1_19_4.UPDATE_ENABLED_FEATURES.getId()) {
                packetWrapper.setPacketType(ClientboundConfigurationPackets1_20_2.UPDATE_ENABLED_FEATURES);
            } else if (unmappedId == ClientboundPackets1_19_4.UPDATE_TAGS.getId()) {
                packetWrapper.setPacketType(ClientboundConfigurationPackets1_20_2.UPDATE_TAGS);
            } else {
                configurationBridge.addPacketToQueue(packetWrapper, true);
                throw CancelException.generate();
            }
            return;
        }
        super.transform(direction, State.CONFIGURATION, packetWrapper);
    }

    public static void sendConfigurationPackets(UserConnection connection, CompoundTag dimensionRegistry, @Nullable LastResourcePack lastResourcePack) {
        ProtocolInfo protocolInfo = connection.getProtocolInfo();
        protocolInfo.setServerState(State.CONFIGURATION);
        PacketWrapper registryDataPacket = PacketWrapper.create(ClientboundConfigurationPackets1_20_2.REGISTRY_DATA, connection);
        registryDataPacket.write(Types.COMPOUND_TAG, dimensionRegistry);
        registryDataPacket.send(Protocol1_20To1_20_2.class);
        LastTags lastTags = connection.get(LastTags.class);
        if (lastTags != null) {
            lastTags.sendLastTags(connection);
        }
        if (lastResourcePack != null && connection.getProtocolInfo().protocolVersion() == ProtocolVersion.v1_20_2) {
            PacketWrapper resourcePackPacket = PacketWrapper.create(ClientboundConfigurationPackets1_20_2.RESOURCE_PACK, connection);
            resourcePackPacket.write(Types.STRING, lastResourcePack.url());
            resourcePackPacket.write(Types.STRING, lastResourcePack.hash());
            resourcePackPacket.write(Types.BOOLEAN, lastResourcePack.required());
            resourcePackPacket.write(Types.OPTIONAL_COMPONENT, lastResourcePack.prompt());
            resourcePackPacket.send(Protocol1_20To1_20_2.class);
        }
        PacketWrapper finishConfigurationPacket = PacketWrapper.create(ClientboundConfigurationPackets1_20_2.FINISH_CONFIGURATION, connection);
        finishConfigurationPacket.send(Protocol1_20To1_20_2.class);
        protocolInfo.setServerState(State.PLAY);
    }

    PacketHandler queueServerboundPacket(ServerboundPackets1_20_2 packetType) {
        return wrapper -> {
            wrapper.setPacketType(packetType);
            wrapper.user().get(ConfigurationState.class).addPacketToQueue(wrapper, false);
            wrapper.cancel();
        };
    }

    @Override
    public MappingData getMappingData() {
        return MAPPINGS;
    }

    @Override
    protected void registerConfigurationChangeHandlers() {
    }

    @Override
    public void init(UserConnection user) {
        user.put(new ConfigurationState());
        this.addEntityTracker(user, new EntityTrackerBase(user, EntityTypes1_19_4.PLAYER));
    }

    @Override
    public EntityRewriter<Protocol1_20To1_20_2> getEntityRewriter() {
        return this.entityPacketRewriter;
    }

    @Override
    public ItemRewriter<Protocol1_20To1_20_2> getItemRewriter() {
        return this.itemPacketRewriter;
    }

    @Override
    public TagRewriter<ClientboundPackets1_19_4> getTagRewriter() {
        return this.tagRewriter;
    }
}

