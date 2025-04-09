/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20_3to1_20_2;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_20_3to1_20_2.rewriter.BlockItemPacketRewriter1_20_3;
import com.viaversion.viabackwards.protocol.v1_20_3to1_20_2.rewriter.EntityPacketRewriter1_20_3;
import com.viaversion.viabackwards.protocol.v1_20_3to1_20_2.storage.ResourcepackIDStorage;
import com.viaversion.viabackwards.protocol.v1_20_3to1_20_2.storage.SpawnPositionStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_3;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.packet.provider.SimplePacketTypesProvider;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter.CommandRewriter1_19_4;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.Protocol1_20_2To1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundConfigurationPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundPacket1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ServerboundPacket1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ServerboundPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundPacket1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundPackets1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ServerboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ServerboundPacket1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ServerboundPackets1_20_2;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.ProtocolUtil;
import java.util.BitSet;
import java.util.UUID;

public final class Protocol1_20_3To1_20_2
extends BackwardsProtocol<ClientboundPacket1_20_3, ClientboundPacket1_20_2, ServerboundPacket1_20_3, ServerboundPacket1_20_2> {
    public static final BackwardsMappingData MAPPINGS = new BackwardsMappingData("1.20.3", "1.20.2", Protocol1_20_2To1_20_3.class);
    final EntityPacketRewriter1_20_3 entityRewriter = new EntityPacketRewriter1_20_3(this);
    final BlockItemPacketRewriter1_20_3 itemRewriter = new BlockItemPacketRewriter1_20_3(this);
    final TranslatableRewriter<ClientboundPacket1_20_3> translatableRewriter = new TranslatableRewriter<ClientboundPacket1_20_3>(this, ComponentRewriter.ReadType.NBT);
    final TagRewriter<ClientboundPacket1_20_3> tagRewriter = new TagRewriter<ClientboundPacket1_20_3>(this);

    public Protocol1_20_3To1_20_2() {
        super(ClientboundPacket1_20_3.class, ClientboundPacket1_20_2.class, ServerboundPacket1_20_3.class, ServerboundPacket1_20_2.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.tagRewriter.registerGeneric(ClientboundPackets1_20_3.UPDATE_TAGS);
        this.tagRewriter.registerGeneric(ClientboundConfigurationPackets1_20_3.UPDATE_TAGS);
        SoundRewriter<ClientboundPacket1_20_3> soundRewriter = new SoundRewriter<ClientboundPacket1_20_3>(this);
        soundRewriter.registerSound1_19_3(ClientboundPackets1_20_3.SOUND);
        soundRewriter.registerSound1_19_3(ClientboundPackets1_20_3.SOUND_ENTITY);
        soundRewriter.registerStopSound(ClientboundPackets1_20_3.STOP_SOUND);
        new StatisticsRewriter<ClientboundPacket1_20_3>(this).register(ClientboundPackets1_20_3.AWARD_STATS);
        new CommandRewriter1_19_4<ClientboundPacket1_20_3>((Protocol)this){

            @Override
            public void handleArgument(PacketWrapper wrapper, String argumentType) {
                if (argumentType.equals("minecraft:style")) {
                    wrapper.write(Types.VAR_INT, 1);
                } else {
                    super.handleArgument(wrapper, argumentType);
                }
            }
        }.registerDeclareCommands1_19(ClientboundPackets1_20_3.COMMANDS);
        this.registerClientbound(ClientboundPackets1_20_3.RESET_SCORE, ClientboundPackets1_20_2.SET_SCORE, (PacketWrapper wrapper) -> {
            wrapper.passthrough(Types.STRING);
            wrapper.write(Types.VAR_INT, 1);
            String objectiveName = wrapper.read(Types.OPTIONAL_STRING);
            wrapper.write(Types.STRING, objectiveName != null ? objectiveName : "");
        });
        this.registerClientbound(ClientboundPackets1_20_3.SET_SCORE, wrapper -> {
            wrapper.passthrough(Types.STRING);
            wrapper.write(Types.VAR_INT, 0);
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.VAR_INT);
            wrapper.clearInputBuffer();
        });
        this.registerClientbound(ClientboundPackets1_20_3.SET_OBJECTIVE, wrapper -> {
            wrapper.passthrough(Types.STRING);
            byte action = wrapper.passthrough(Types.BYTE);
            if (action == 0 || action == 2) {
                this.convertComponent(wrapper);
                wrapper.passthrough(Types.VAR_INT);
                wrapper.clearInputBuffer();
            }
        });
        this.cancelClientbound(ClientboundPackets1_20_3.TICKING_STATE);
        this.cancelClientbound(ClientboundPackets1_20_3.TICKING_STEP);
        this.registerServerbound(ServerboundPackets1_20_2.SET_JIGSAW_BLOCK, wrapper -> {
            wrapper.passthrough(Types.BLOCK_POSITION1_14);
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.STRING);
            wrapper.write(Types.VAR_INT, 0);
            wrapper.write(Types.VAR_INT, 0);
        });
        this.registerClientbound(ClientboundPackets1_20_3.UPDATE_ADVANCEMENTS, wrapper -> {
            wrapper.passthrough(Types.BOOLEAN);
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.OPTIONAL_STRING);
                if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                    this.convertComponent(wrapper);
                    this.convertComponent(wrapper);
                    this.itemRewriter.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_20_2));
                    wrapper.passthrough(Types.VAR_INT);
                    int flags = wrapper.passthrough(Types.INT);
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Types.STRING);
                    }
                    wrapper.passthrough(Types.FLOAT);
                    wrapper.passthrough(Types.FLOAT);
                }
                int requirements = wrapper.passthrough(Types.VAR_INT);
                for (int array = 0; array < requirements; ++array) {
                    wrapper.passthrough(Types.STRING_ARRAY);
                }
                wrapper.passthrough(Types.BOOLEAN);
            }
        });
        this.registerClientbound(ClientboundPackets1_20_3.COMMAND_SUGGESTIONS, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT);
            int suggestions = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < suggestions; ++i) {
                wrapper.passthrough(Types.STRING);
                this.convertOptionalComponent(wrapper);
            }
        });
        this.registerClientbound(ClientboundPackets1_20_3.MAP_ITEM_DATA, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.BYTE);
            wrapper.passthrough(Types.BOOLEAN);
            if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                int icons = wrapper.passthrough(Types.VAR_INT);
                for (int i = 0; i < icons; ++i) {
                    wrapper.passthrough(Types.VAR_INT);
                    wrapper.passthrough(Types.BYTE);
                    wrapper.passthrough(Types.BYTE);
                    wrapper.passthrough(Types.BYTE);
                    this.convertOptionalComponent(wrapper);
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_20_3.BOSS_EVENT, wrapper -> {
            wrapper.passthrough(Types.UUID);
            int action = wrapper.passthrough(Types.VAR_INT);
            if (action == 0 || action == 3) {
                this.convertComponent(wrapper);
            }
        });
        this.registerClientbound(ClientboundPackets1_20_3.PLAYER_CHAT, wrapper -> {
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
            this.convertOptionalComponent(wrapper);
            int filterMaskType = wrapper.passthrough(Types.VAR_INT);
            if (filterMaskType == 2) {
                wrapper.passthrough(Types.LONG_ARRAY_PRIMITIVE);
            }
            wrapper.passthrough(Types.VAR_INT);
            this.convertComponent(wrapper);
            this.convertOptionalComponent(wrapper);
        });
        this.registerClientbound(ClientboundPackets1_20_3.SET_PLAYER_TEAM, wrapper -> {
            wrapper.passthrough(Types.STRING);
            byte action = wrapper.passthrough(Types.BYTE);
            if (action == 0 || action == 2) {
                this.convertComponent(wrapper);
                wrapper.passthrough(Types.BYTE);
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.VAR_INT);
                this.convertComponent(wrapper);
                this.convertComponent(wrapper);
            }
        });
        this.registerClientbound(ClientboundConfigurationPackets1_20_3.DISCONNECT, this::convertComponent);
        this.registerClientbound(ClientboundPackets1_20_3.DISCONNECT, this::convertComponent);
        this.registerClientbound(ClientboundPackets1_20_3.RESOURCE_PACK_PUSH, ClientboundPackets1_20_2.RESOURCE_PACK, this.resourcePackHandler());
        this.registerClientbound(ClientboundPackets1_20_3.SERVER_DATA, this::convertComponent);
        this.registerClientbound(ClientboundPackets1_20_3.SET_ACTION_BAR_TEXT, this::convertComponent);
        this.registerClientbound(ClientboundPackets1_20_3.SET_TITLE_TEXT, this::convertComponent);
        this.registerClientbound(ClientboundPackets1_20_3.SET_SUBTITLE_TEXT, this::convertComponent);
        this.registerClientbound(ClientboundPackets1_20_3.DISGUISED_CHAT, wrapper -> {
            this.convertComponent(wrapper);
            wrapper.passthrough(Types.VAR_INT);
            this.convertComponent(wrapper);
            this.convertOptionalComponent(wrapper);
        });
        this.registerClientbound(ClientboundPackets1_20_3.SYSTEM_CHAT, this::convertComponent);
        this.registerClientbound(ClientboundPackets1_20_3.OPEN_SCREEN, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            int containerTypeId = wrapper.read(Types.VAR_INT);
            int mappedContainerTypeId = MAPPINGS.getMenuMappings().getNewId(containerTypeId);
            if (mappedContainerTypeId == -1) {
                wrapper.cancel();
                return;
            }
            wrapper.write(Types.VAR_INT, mappedContainerTypeId);
            this.convertComponent(wrapper);
        });
        this.registerClientbound(ClientboundPackets1_20_3.TAB_LIST, wrapper -> {
            this.convertComponent(wrapper);
            this.convertComponent(wrapper);
        });
        this.registerClientbound(ClientboundPackets1_20_3.PLAYER_COMBAT_KILL, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> Protocol1_20_3To1_20_2.this.convertComponent(wrapper));
            }
        });
        this.registerClientbound(ClientboundPackets1_20_3.PLAYER_INFO_UPDATE, wrapper -> {
            BitSet actions = wrapper.passthrough(Types.PROFILE_ACTIONS_ENUM);
            int entries = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < entries; ++i) {
                wrapper.passthrough(Types.UUID);
                if (actions.get(0)) {
                    wrapper.passthrough(Types.STRING);
                    int properties = wrapper.passthrough(Types.VAR_INT);
                    for (int j = 0; j < properties; ++j) {
                        wrapper.passthrough(Types.STRING);
                        wrapper.passthrough(Types.STRING);
                        wrapper.passthrough(Types.OPTIONAL_STRING);
                    }
                }
                if (actions.get(1) && wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                    wrapper.passthrough(Types.UUID);
                    wrapper.passthrough(Types.PROFILE_KEY);
                }
                if (actions.get(2)) {
                    wrapper.passthrough(Types.VAR_INT);
                }
                if (actions.get(3)) {
                    wrapper.passthrough(Types.BOOLEAN);
                }
                if (actions.get(4)) {
                    wrapper.passthrough(Types.VAR_INT);
                }
                if (!actions.get(5)) continue;
                this.convertOptionalComponent(wrapper);
            }
        });
        this.registerClientbound(ClientboundPackets1_20_3.SET_DEFAULT_SPAWN_POSITION, wrapper -> {
            BlockPosition position = wrapper.passthrough(Types.BLOCK_POSITION1_14);
            float angle = wrapper.passthrough(Types.FLOAT).floatValue();
            wrapper.user().get(SpawnPositionStorage.class).setSpawnPosition(Pair.of(position, Float.valueOf(angle)));
        });
        this.registerClientbound(ClientboundPackets1_20_3.GAME_EVENT, wrapper -> {
            short reason = wrapper.passthrough(Types.UNSIGNED_BYTE);
            if (reason == 13) {
                wrapper.cancel();
                Pair<BlockPosition, Float> spawnPositionAndAngle = wrapper.user().get(SpawnPositionStorage.class).getSpawnPosition();
                PacketWrapper spawnPosition = wrapper.create(ClientboundPackets1_20_2.SET_DEFAULT_SPAWN_POSITION);
                spawnPosition.write(Types.BLOCK_POSITION1_14, spawnPositionAndAngle.first());
                spawnPosition.write(Types.FLOAT, spawnPositionAndAngle.second());
                spawnPosition.send(Protocol1_20_3To1_20_2.class, true);
            }
        });
        this.cancelClientbound(ClientboundPackets1_20_3.RESOURCE_PACK_POP);
        this.registerServerbound(ServerboundPackets1_20_2.RESOURCE_PACK, this.resourcePackStatusHandler());
        this.cancelClientbound(ClientboundConfigurationPackets1_20_3.RESOURCE_PACK_POP);
        this.registerServerbound(ServerboundConfigurationPackets1_20_2.RESOURCE_PACK, this.resourcePackStatusHandler());
        this.registerClientbound(ClientboundConfigurationPackets1_20_3.RESOURCE_PACK_PUSH, ClientboundConfigurationPackets1_20_2.RESOURCE_PACK, this.resourcePackHandler());
    }

    PacketHandler resourcePackStatusHandler() {
        return wrapper -> {
            ResourcepackIDStorage storage = wrapper.user().get(ResourcepackIDStorage.class);
            wrapper.write(Types.UUID, storage != null ? storage.uuid() : UUID.randomUUID());
        };
    }

    PacketHandler resourcePackHandler() {
        return wrapper -> {
            UUID uuid = wrapper.read(Types.UUID);
            wrapper.user().put(new ResourcepackIDStorage(uuid));
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.BOOLEAN);
            this.convertOptionalComponent(wrapper);
        };
    }

    void convertComponent(PacketWrapper wrapper) {
        Tag tag = wrapper.read(Types.TAG);
        this.translatableRewriter.processTag(wrapper.user(), tag);
        wrapper.write(Types.COMPONENT, ComponentUtil.tagToJson(tag));
    }

    void convertOptionalComponent(PacketWrapper wrapper) {
        Tag tag = wrapper.read(Types.OPTIONAL_TAG);
        this.translatableRewriter.processTag(wrapper.user(), tag);
        wrapper.write(Types.OPTIONAL_COMPONENT, ComponentUtil.tagToJson(tag));
    }

    @Override
    public void init(UserConnection connection) {
        connection.put(new SpawnPositionStorage());
        this.addEntityTracker(connection, new EntityTrackerBase(connection, EntityTypes1_20_3.PLAYER));
    }

    @Override
    public BackwardsMappingData getMappingData() {
        return MAPPINGS;
    }

    public BlockItemPacketRewriter1_20_3 getItemRewriter() {
        return this.itemRewriter;
    }

    public EntityPacketRewriter1_20_3 getEntityRewriter() {
        return this.entityRewriter;
    }

    @Override
    public TranslatableRewriter<ClientboundPacket1_20_3> getComponentRewriter() {
        return this.translatableRewriter;
    }

    @Override
    public TagRewriter<ClientboundPacket1_20_3> getTagRewriter() {
        return this.tagRewriter;
    }

    @Override
    protected PacketTypesProvider<ClientboundPacket1_20_3, ClientboundPacket1_20_2, ServerboundPacket1_20_3, ServerboundPacket1_20_2> createPacketTypesProvider() {
        return new SimplePacketTypesProvider<ClientboundPacket1_20_3, ClientboundPacket1_20_2, ServerboundPacket1_20_3, ServerboundPacket1_20_2>(ProtocolUtil.packetTypeMap(this.unmappedClientboundPacketType, ClientboundPackets1_20_3.class, ClientboundConfigurationPackets1_20_3.class), ProtocolUtil.packetTypeMap(this.mappedClientboundPacketType, ClientboundPackets1_20_2.class, ClientboundConfigurationPackets1_20_2.class), ProtocolUtil.packetTypeMap(this.mappedServerboundPacketType, ServerboundPackets1_20_3.class, ServerboundConfigurationPackets1_20_2.class), ProtocolUtil.packetTypeMap(this.unmappedServerboundPacketType, ServerboundPackets1_20_2.class, ServerboundConfigurationPackets1_20_2.class));
    }
}

