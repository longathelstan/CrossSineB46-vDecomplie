/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_19_3to1_19_1.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.v1_19_3to1_19_1.Protocol1_19_3To1_19_1;
import com.viaversion.viabackwards.protocol.v1_19_3to1_19_1.storage.ChatTypeStorage1_19_3;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_3;
import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_3;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.BitSetType;
import com.viaversion.viaversion.api.type.types.version.Types1_19;
import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ServerboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19to1_19_1.packet.ClientboundPackets1_19_1;
import com.viaversion.viaversion.util.TagUtil;
import java.util.BitSet;
import java.util.Objects;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class EntityPacketRewriter1_19_3
extends EntityRewriter<ClientboundPackets1_19_3, Protocol1_19_3To1_19_1> {
    static final BitSetType PROFILE_ACTIONS_ENUM_TYPE = new BitSetType(6);
    static final int[] PROFILE_ACTIONS = new int[]{2, 3, 4, 5};
    static final int ADD_PLAYER = 0;
    static final int INITIALIZE_CHAT = 1;
    static final int UPDATE_GAMEMODE = 2;
    static final int UPDATE_LISTED = 3;
    static final int UPDATE_LATENCY = 4;
    static final int UPDATE_DISPLAYNAME = 5;

    public EntityPacketRewriter1_19_3(Protocol1_19_3To1_19_1 protocol) {
        super(protocol, Types1_19.ENTITY_DATA_TYPES.optionalComponentType, Types1_19.ENTITY_DATA_TYPES.booleanType);
    }

    @Override
    protected void registerPackets() {
        this.registerSetEntityData(ClientboundPackets1_19_3.SET_ENTITY_DATA, Types1_19_3.ENTITY_DATA_LIST, Types1_19.ENTITY_DATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_19_3.REMOVE_ENTITIES);
        this.registerTrackerWithData1_19(ClientboundPackets1_19_3.ADD_ENTITY, EntityTypes1_19_3.FALLING_BLOCK);
        ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound(ClientboundPackets1_19_3.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.STRING_ARRAY);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.map(Types.STRING);
                this.map(Types.STRING);
                this.handler(EntityPacketRewriter1_19_3.this.dimensionDataHandler());
                this.handler(EntityPacketRewriter1_19_3.this.biomeSizeTracker());
                this.handler(EntityPacketRewriter1_19_3.this.worldDataTrackerHandlerByKey());
                this.handler(wrapper -> {
                    ChatTypeStorage1_19_3 chatTypeStorage = wrapper.user().get(ChatTypeStorage1_19_3.class);
                    chatTypeStorage.clear();
                    CompoundTag registry = wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
                    ListTag<CompoundTag> chatTypes = TagUtil.getRegistryEntries(registry, "chat_type", new ListTag<CompoundTag>(CompoundTag.class));
                    for (CompoundTag chatType : chatTypes) {
                        NumberTag idTag = chatType.getNumberTag("id");
                        chatTypeStorage.addChatType(idTag.asInt(), chatType);
                    }
                });
                this.handler(wrapper -> {
                    ChatSession1_19_3 chatSession = wrapper.user().get(ChatSession1_19_3.class);
                    if (chatSession != null) {
                        PacketWrapper chatSessionUpdate = wrapper.create(ServerboundPackets1_19_3.CHAT_SESSION_UPDATE);
                        chatSessionUpdate.write(Types.UUID, chatSession.getSessionId());
                        chatSessionUpdate.write(Types.PROFILE_KEY, chatSession.getProfileKey());
                        chatSessionUpdate.sendToServer(Protocol1_19_3To1_19_1.class);
                    }
                });
            }
        });
        ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound(ClientboundPackets1_19_3.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.STRING);
                this.map(Types.LONG);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.BYTE);
                this.map(Types.BOOLEAN);
                this.map(Types.BOOLEAN);
                this.handler(EntityPacketRewriter1_19_3.this.worldDataTrackerHandlerByKey());
                this.handler(wrapper -> {
                    byte keepDataMask = wrapper.read(Types.BYTE);
                    wrapper.write(Types.BOOLEAN, (keepDataMask & 1) != 0);
                });
            }
        });
        ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound(ClientboundPackets1_19_3.PLAYER_INFO_UPDATE, ClientboundPackets1_19_1.PLAYER_INFO, wrapper -> {
            wrapper.cancel();
            BitSet actions = wrapper.read(PROFILE_ACTIONS_ENUM_TYPE);
            int entries = wrapper.read(Types.VAR_INT);
            if (actions.get(0)) {
                PacketWrapper playerInfoPacket = wrapper.create(ClientboundPackets1_19_1.PLAYER_INFO);
                playerInfoPacket.write(Types.VAR_INT, 0);
                playerInfoPacket.write(Types.VAR_INT, entries);
                for (int i = 0; i < entries; ++i) {
                    int gamemode;
                    ProfileKey profileKey;
                    playerInfoPacket.write(Types.UUID, wrapper.read(Types.UUID));
                    playerInfoPacket.write(Types.STRING, wrapper.read(Types.STRING));
                    int properties = wrapper.read(Types.VAR_INT);
                    playerInfoPacket.write(Types.VAR_INT, properties);
                    for (int j = 0; j < properties; ++j) {
                        playerInfoPacket.write(Types.STRING, wrapper.read(Types.STRING));
                        playerInfoPacket.write(Types.STRING, wrapper.read(Types.STRING));
                        playerInfoPacket.write(Types.OPTIONAL_STRING, wrapper.read(Types.OPTIONAL_STRING));
                    }
                    if (actions.get(1) && wrapper.read(Types.BOOLEAN).booleanValue()) {
                        wrapper.read(Types.UUID);
                        profileKey = wrapper.read(Types.PROFILE_KEY);
                    } else {
                        profileKey = null;
                    }
                    int n = gamemode = actions.get(2) ? wrapper.read(Types.VAR_INT) : 0;
                    if (actions.get(3)) {
                        wrapper.read(Types.BOOLEAN);
                    }
                    int latency = actions.get(4) ? wrapper.read(Types.VAR_INT) : 0;
                    JsonElement displayName = actions.get(5) ? wrapper.read(Types.OPTIONAL_COMPONENT) : null;
                    playerInfoPacket.write(Types.VAR_INT, gamemode);
                    playerInfoPacket.write(Types.VAR_INT, latency);
                    playerInfoPacket.write(Types.OPTIONAL_COMPONENT, displayName);
                    playerInfoPacket.write(Types.OPTIONAL_PROFILE_KEY, profileKey);
                }
                playerInfoPacket.send(Protocol1_19_3To1_19_1.class);
                return;
            }
            PlayerProfileUpdate[] updates = new PlayerProfileUpdate[entries];
            for (int i = 0; i < entries; ++i) {
                UUID uuid = wrapper.read(Types.UUID);
                int gamemode = 0;
                int latency = 0;
                JsonElement displayName = null;
                block9: for (int action : PROFILE_ACTIONS) {
                    if (!actions.get(action)) continue;
                    switch (action) {
                        case 2: {
                            gamemode = wrapper.read(Types.VAR_INT);
                            continue block9;
                        }
                        case 3: {
                            wrapper.read(Types.BOOLEAN);
                            continue block9;
                        }
                        case 4: {
                            latency = wrapper.read(Types.VAR_INT);
                            continue block9;
                        }
                        case 5: {
                            displayName = wrapper.read(Types.OPTIONAL_COMPONENT);
                        }
                    }
                }
                updates[i] = new PlayerProfileUpdate(uuid, gamemode, latency, displayName);
            }
            if (actions.get(2)) {
                this.sendPlayerProfileUpdate(wrapper.user(), 1, updates);
            } else if (actions.get(4)) {
                this.sendPlayerProfileUpdate(wrapper.user(), 2, updates);
            } else if (actions.get(5)) {
                this.sendPlayerProfileUpdate(wrapper.user(), 3, updates);
            }
        });
        ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound(ClientboundPackets1_19_3.PLAYER_INFO_REMOVE, ClientboundPackets1_19_1.PLAYER_INFO, wrapper -> {
            UUID[] uuids = wrapper.read(Types.UUID_ARRAY);
            wrapper.write(Types.VAR_INT, 4);
            wrapper.write(Types.VAR_INT, uuids.length);
            for (UUID uuid : uuids) {
                wrapper.write(Types.UUID, uuid);
            }
        });
    }

    void sendPlayerProfileUpdate(UserConnection connection, int action, PlayerProfileUpdate[] updates) {
        PacketWrapper playerInfoPacket = PacketWrapper.create(ClientboundPackets1_19_1.PLAYER_INFO, connection);
        playerInfoPacket.write(Types.VAR_INT, action);
        playerInfoPacket.write(Types.VAR_INT, updates.length);
        for (PlayerProfileUpdate update : updates) {
            playerInfoPacket.write(Types.UUID, update.uuid());
            if (action == 1) {
                playerInfoPacket.write(Types.VAR_INT, update.gamemode());
                continue;
            }
            if (action == 2) {
                playerInfoPacket.write(Types.VAR_INT, update.latency());
                continue;
            }
            if (action == 3) {
                playerInfoPacket.write(Types.OPTIONAL_COMPONENT, update.displayName());
                continue;
            }
            int n = action;
            throw new IllegalArgumentException("Invalid action: " + n);
        }
        playerInfoPacket.send(Protocol1_19_3To1_19_1.class);
    }

    @Override
    public void registerRewrites() {
        this.filter().handler((event, data) -> {
            int id = data.dataType().typeId();
            if (id > 2) {
                data.setDataType(Types1_19.ENTITY_DATA_TYPES.byId(id - 1));
            } else if (id != 2) {
                data.setDataType(Types1_19.ENTITY_DATA_TYPES.byId(id));
            }
        });
        this.registerEntityDataTypeHandler(Types1_19.ENTITY_DATA_TYPES.itemType, null, Types1_19.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_19.ENTITY_DATA_TYPES.particleType, Types1_19.ENTITY_DATA_TYPES.componentType, Types1_19.ENTITY_DATA_TYPES.optionalComponentType);
        this.registerBlockStateHandler(EntityTypes1_19_3.ABSTRACT_MINECART, 11);
        this.filter().dataType(Types1_19.ENTITY_DATA_TYPES.poseType).handler((event, data) -> {
            int pose = (Integer)data.value();
            if (pose == 10) {
                data.setValue(0);
            } else if (pose > 10) {
                data.setValue(pose - 1);
            }
        });
        this.filter().type(EntityTypes1_19_3.CAMEL).cancel(19);
        this.filter().type(EntityTypes1_19_3.CAMEL).cancel(20);
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
        this.mapEntityTypeWithData(EntityTypes1_19_3.CAMEL, EntityTypes1_19_3.DONKEY).jsonName();
    }

    @Override
    public EntityType typeFromId(int typeId) {
        return EntityTypes1_19_3.getTypeFromId(typeId);
    }

    private static final class PlayerProfileUpdate {
        final UUID uuid;
        final int gamemode;
        final int latency;
        final @Nullable JsonElement displayName;

        PlayerProfileUpdate(UUID uuid, int gamemode, int latency, @Nullable JsonElement displayName) {
            this.uuid = uuid;
            this.gamemode = gamemode;
            this.latency = latency;
            this.displayName = displayName;
        }

        public UUID uuid() {
            return this.uuid;
        }

        public int gamemode() {
            return this.gamemode;
        }

        public int latency() {
            return this.latency;
        }

        public @Nullable JsonElement displayName() {
            return this.displayName;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof PlayerProfileUpdate)) {
                return false;
            }
            PlayerProfileUpdate playerProfileUpdate = (PlayerProfileUpdate)object;
            return Objects.equals(this.uuid, playerProfileUpdate.uuid) && this.gamemode == playerProfileUpdate.gamemode && this.latency == playerProfileUpdate.latency && Objects.equals(this.displayName, playerProfileUpdate.displayName);
        }

        public int hashCode() {
            return (((0 * 31 + Objects.hashCode(this.uuid)) * 31 + Integer.hashCode(this.gamemode)) * 31 + Integer.hashCode(this.latency)) * 31 + Objects.hashCode(this.displayName);
        }

        public String toString() {
            return String.format("%s[uuid=%s, gamemode=%s, latency=%s, displayName=%s]", this.getClass().getSimpleName(), Objects.toString(this.uuid), Integer.toString(this.gamemode), Integer.toString(this.latency), Objects.toString(this.displayName));
        }
    }
}

