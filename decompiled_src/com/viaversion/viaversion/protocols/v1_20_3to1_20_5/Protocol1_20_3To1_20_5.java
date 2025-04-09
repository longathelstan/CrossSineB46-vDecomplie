/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_3to1_20_5;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_5;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.packet.provider.SimplePacketTypesProvider;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter.CommandRewriter1_19_4;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundConfigurationPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundPacket1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ServerboundPacket1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ServerboundPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.MappingData1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundConfigurationPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundConfigurationPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.rewriter.BlockItemPacketRewriter1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.rewriter.ComponentRewriter1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.rewriter.EntityPacketRewriter1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.storage.AcknowledgedMessagesStorage;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.storage.ArmorTrimStorage;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ServerboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.ProtocolLogger;
import com.viaversion.viaversion.util.ProtocolUtil;
import java.util.BitSet;
import java.util.UUID;

public final class Protocol1_20_3To1_20_5
extends AbstractProtocol<ClientboundPacket1_20_3, ClientboundPacket1_20_5, ServerboundPacket1_20_3, ServerboundPacket1_20_5> {
    public static final MappingData1_20_5 MAPPINGS = new MappingData1_20_5();
    public static final ProtocolLogger LOGGER = new ProtocolLogger(Protocol1_20_3To1_20_5.class);
    public static boolean strictErrorHandling = System.getProperty("viaversion.strict-error-handling1_20_5", "true").equalsIgnoreCase("true");
    private final EntityPacketRewriter1_20_5 entityRewriter = new EntityPacketRewriter1_20_5(this);
    private final BlockItemPacketRewriter1_20_5 itemRewriter = new BlockItemPacketRewriter1_20_5(this);
    private final TagRewriter<ClientboundPacket1_20_3> tagRewriter = new TagRewriter<ClientboundPacket1_20_3>(this);
    private final ComponentRewriter1_20_5<ClientboundPacket1_20_3> componentRewriter = new ComponentRewriter1_20_5<ClientboundPacket1_20_3>(this, Types1_20_5.STRUCTURED_DATA);

    public Protocol1_20_3To1_20_5() {
        super(ClientboundPacket1_20_3.class, ClientboundPacket1_20_5.class, ServerboundPacket1_20_3.class, ServerboundPacket1_20_5.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.tagRewriter.registerGeneric(ClientboundPackets1_20_3.UPDATE_TAGS);
        this.tagRewriter.registerGeneric(ClientboundConfigurationPackets1_20_3.UPDATE_TAGS);
        SoundRewriter<ClientboundPacket1_20_3> soundRewriter = new SoundRewriter<ClientboundPacket1_20_3>(this);
        soundRewriter.registerSound1_19_3(ClientboundPackets1_20_3.SOUND);
        soundRewriter.registerSound1_19_3(ClientboundPackets1_20_3.SOUND_ENTITY);
        new StatisticsRewriter<ClientboundPacket1_20_3>(this).register(ClientboundPackets1_20_3.AWARD_STATS);
        this.componentRewriter.registerComponentPacket(ClientboundPackets1_20_3.SYSTEM_CHAT);
        this.componentRewriter.registerComponentPacket(ClientboundPackets1_20_3.DISGUISED_CHAT);
        this.componentRewriter.registerPlayerCombatKill1_20(ClientboundPackets1_20_3.PLAYER_COMBAT_KILL);
        this.componentRewriter.registerOpenScreen(ClientboundPackets1_20_3.OPEN_SCREEN);
        this.componentRewriter.registerComponentPacket(ClientboundPackets1_20_3.SET_ACTION_BAR_TEXT);
        this.componentRewriter.registerComponentPacket(ClientboundPackets1_20_3.SET_TITLE_TEXT);
        this.componentRewriter.registerComponentPacket(ClientboundPackets1_20_3.SET_SUBTITLE_TEXT);
        this.componentRewriter.registerBossEvent(ClientboundPackets1_20_3.BOSS_EVENT);
        this.componentRewriter.registerComponentPacket(ClientboundPackets1_20_3.DISCONNECT);
        this.componentRewriter.registerTabList(ClientboundPackets1_20_3.TAB_LIST);
        this.componentRewriter.registerPlayerInfoUpdate1_20_3(ClientboundPackets1_20_3.PLAYER_INFO_UPDATE);
        this.componentRewriter.registerPing();
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO, (PacketWrapper wrapper) -> {
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.BYTE_ARRAY_PRIMITIVE);
            wrapper.passthrough(Types.BYTE_ARRAY_PRIMITIVE);
            wrapper.write(Types.BOOLEAN, true);
        });
        this.registerClientbound(ClientboundPackets1_20_3.SERVER_DATA, wrapper -> {
            wrapper.passthrough(Types.TAG);
            wrapper.passthrough(Types.OPTIONAL_BYTE_ARRAY_PRIMITIVE);
            boolean enforcesSecureChat = wrapper.read(Types.BOOLEAN);
            AcknowledgedMessagesStorage storage = wrapper.user().get(AcknowledgedMessagesStorage.class);
            storage.setSecureChatEnforced(enforcesSecureChat);
            if (enforcesSecureChat) {
                storage.sendQueuedChatSession(wrapper);
            }
        });
        this.registerServerbound(ServerboundPackets1_20_5.CHAT, wrapper -> {
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.LONG);
            AcknowledgedMessagesStorage storage = wrapper.user().get(AcknowledgedMessagesStorage.class);
            long salt = wrapper.read(Types.LONG);
            byte[] signature = wrapper.read(Types.OPTIONAL_SIGNATURE_BYTES);
            if (storage.isSecureChatEnforced()) {
                wrapper.write(Types.LONG, salt);
                wrapper.write(Types.OPTIONAL_SIGNATURE_BYTES, signature);
            } else {
                wrapper.write(Types.LONG, 0L);
                wrapper.write(Types.OPTIONAL_SIGNATURE_BYTES, null);
            }
            this.fixChatAck(wrapper, storage);
        });
        this.registerServerbound(ServerboundPackets1_20_5.CHAT_COMMAND_SIGNED, ServerboundPackets1_20_3.CHAT_COMMAND, (PacketWrapper wrapper) -> {
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.LONG);
            AcknowledgedMessagesStorage storage = wrapper.user().get(AcknowledgedMessagesStorage.class);
            long salt = wrapper.read(Types.LONG);
            int signatures = wrapper.read(Types.VAR_INT);
            if (storage.isSecureChatEnforced()) {
                wrapper.write(Types.LONG, salt);
                wrapper.write(Types.VAR_INT, signatures);
                for (int i = 0; i < signatures; ++i) {
                    wrapper.passthrough(Types.STRING);
                    wrapper.passthrough(Types.SIGNATURE_BYTES);
                }
            } else {
                wrapper.write(Types.LONG, 0L);
                wrapper.write(Types.VAR_INT, 0);
                for (int i = 0; i < signatures; ++i) {
                    wrapper.read(Types.STRING);
                    wrapper.read(Types.SIGNATURE_BYTES);
                }
            }
            this.fixChatAck(wrapper, storage);
        });
        this.registerServerbound(ServerboundPackets1_20_5.CHAT_ACK, wrapper -> {
            int offset = wrapper.read(Types.VAR_INT);
            int fixedOffset = wrapper.user().get(AcknowledgedMessagesStorage.class).accumulateAckCount(offset);
            if (fixedOffset > 0) {
                wrapper.write(Types.VAR_INT, fixedOffset);
            } else {
                wrapper.cancel();
            }
        });
        this.registerServerbound(ServerboundPackets1_20_5.CHAT_COMMAND, wrapper -> {
            wrapper.passthrough(Types.STRING);
            wrapper.write(Types.LONG, System.currentTimeMillis());
            wrapper.write(Types.LONG, 0L);
            wrapper.write(Types.VAR_INT, 0);
            this.writeSpoofedChatAck(wrapper, wrapper.user().get(AcknowledgedMessagesStorage.class));
        });
        this.registerServerbound(ServerboundPackets1_20_5.CHAT_SESSION_UPDATE, wrapper -> {
            AcknowledgedMessagesStorage storage = wrapper.user().get(AcknowledgedMessagesStorage.class);
            if (storage.secureChatEnforced() != null && storage.secureChatEnforced().booleanValue()) {
                return;
            }
            UUID sessionId = wrapper.read(Types.UUID);
            ProfileKey profileKey = wrapper.read(Types.PROFILE_KEY);
            storage.queueChatSession(sessionId, profileKey);
            wrapper.cancel();
        });
        this.registerClientbound(ClientboundPackets1_20_3.START_CONFIGURATION, wrapper -> wrapper.user().put(new AcknowledgedMessagesStorage()));
        new CommandRewriter1_19_4<ClientboundPacket1_20_3>(this).registerDeclareCommands1_19(ClientboundPackets1_20_3.COMMANDS);
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE, (PacketWrapper wrapper) -> {
            wrapper.passthrough(Types.UUID);
            wrapper.passthrough(Types.STRING);
            int properties = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < properties; ++i) {
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.OPTIONAL_STRING);
            }
            wrapper.write(Types.BOOLEAN, strictErrorHandling);
        });
        this.cancelServerbound(State.LOGIN, ServerboundLoginPackets.COOKIE_RESPONSE.getId());
        this.cancelServerbound(ServerboundConfigurationPackets1_20_5.COOKIE_RESPONSE);
        this.cancelServerbound(ServerboundConfigurationPackets1_20_5.SELECT_KNOWN_PACKS);
        this.cancelServerbound(ServerboundPackets1_20_5.COOKIE_RESPONSE);
        this.cancelServerbound(ServerboundPackets1_20_5.DEBUG_SAMPLE_SUBSCRIPTION);
    }

    private void fixChatAck(PacketWrapper wrapper, AcknowledgedMessagesStorage storage) {
        int offset = wrapper.read(Types.VAR_INT);
        BitSet acknowledged = wrapper.read(Types.ACKNOWLEDGED_BIT_SET);
        int fixedOffset = storage.updateFromMessage(offset, acknowledged);
        wrapper.write(Types.VAR_INT, fixedOffset);
        wrapper.write(Types.ACKNOWLEDGED_BIT_SET, acknowledged);
    }

    private void writeSpoofedChatAck(PacketWrapper wrapper, AcknowledgedMessagesStorage storage) {
        wrapper.write(Types.VAR_INT, 0);
        wrapper.write(Types.ACKNOWLEDGED_BIT_SET, storage.createSpoofedAck());
    }

    @Override
    protected void onMappingDataLoaded() {
        EntityTypes1_20_5.initialize(this);
        Types1_20_5.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("block_marker", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("dust_pillar", ParticleType.Readers.BLOCK).reader("falling_dust", ParticleType.Readers.BLOCK).reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION).reader("item", ParticleType.Readers.item(Types1_20_5.ITEM)).reader("vibration", ParticleType.Readers.VIBRATION1_20_3).reader("sculk_charge", ParticleType.Readers.SCULK_CHARGE).reader("shriek", ParticleType.Readers.SHRIEK).reader("entity_effect", ParticleType.Readers.COLOR);
        Types1_20_5.STRUCTURED_DATA.filler(this).add(StructuredDataKey.CUSTOM_DATA).add(StructuredDataKey.MAX_STACK_SIZE).add(StructuredDataKey.MAX_DAMAGE).add(StructuredDataKey.DAMAGE).add(StructuredDataKey.UNBREAKABLE).add(StructuredDataKey.RARITY).add(StructuredDataKey.HIDE_TOOLTIP).add(StructuredDataKey.FOOD1_20_5).add(StructuredDataKey.FIRE_RESISTANT).add(StructuredDataKey.CUSTOM_NAME).add(StructuredDataKey.LORE).add(StructuredDataKey.ENCHANTMENTS).add(StructuredDataKey.CAN_PLACE_ON).add(StructuredDataKey.CAN_BREAK).add(StructuredDataKey.ATTRIBUTE_MODIFIERS1_20_5).add(StructuredDataKey.CUSTOM_MODEL_DATA).add(StructuredDataKey.HIDE_ADDITIONAL_TOOLTIP).add(StructuredDataKey.REPAIR_COST).add(StructuredDataKey.CREATIVE_SLOT_LOCK).add(StructuredDataKey.ENCHANTMENT_GLINT_OVERRIDE).add(StructuredDataKey.INTANGIBLE_PROJECTILE).add(StructuredDataKey.STORED_ENCHANTMENTS).add(StructuredDataKey.DYED_COLOR).add(StructuredDataKey.MAP_COLOR).add(StructuredDataKey.MAP_ID).add(StructuredDataKey.MAP_DECORATIONS).add(StructuredDataKey.MAP_POST_PROCESSING).add(StructuredDataKey.CHARGED_PROJECTILES1_20_5).add(StructuredDataKey.BUNDLE_CONTENTS1_20_5).add(StructuredDataKey.POTION_CONTENTS).add(StructuredDataKey.SUSPICIOUS_STEW_EFFECTS).add(StructuredDataKey.WRITABLE_BOOK_CONTENT).add(StructuredDataKey.WRITTEN_BOOK_CONTENT).add(StructuredDataKey.TRIM).add(StructuredDataKey.DEBUG_STICK_STATE).add(StructuredDataKey.ENTITY_DATA).add(StructuredDataKey.BUCKET_ENTITY_DATA).add(StructuredDataKey.BLOCK_ENTITY_DATA).add(StructuredDataKey.INSTRUMENT).add(StructuredDataKey.RECIPES).add(StructuredDataKey.LODESTONE_TRACKER).add(StructuredDataKey.FIREWORK_EXPLOSION).add(StructuredDataKey.FIREWORKS).add(StructuredDataKey.PROFILE).add(StructuredDataKey.NOTE_BLOCK_SOUND).add(StructuredDataKey.BANNER_PATTERNS).add(StructuredDataKey.BASE_COLOR).add(StructuredDataKey.POT_DECORATIONS).add(StructuredDataKey.CONTAINER1_20_5).add(StructuredDataKey.BLOCK_STATE).add(StructuredDataKey.BEES).add(StructuredDataKey.LOCK).add(StructuredDataKey.CONTAINER_LOOT).add(StructuredDataKey.TOOL).add(StructuredDataKey.ITEM_NAME).add(StructuredDataKey.OMINOUS_BOTTLE_AMPLIFIER);
        this.tagRewriter.renameTag(RegistryType.ITEM, "minecraft:axolotl_tempt_items", "minecraft:axolotl_food");
        this.tagRewriter.removeTag(RegistryType.ITEM, "minecraft:tools");
        this.tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:badlands_terracotta");
        super.onMappingDataLoaded();
    }

    @Override
    public void init(UserConnection connection) {
        this.addEntityTracker(connection, new EntityTrackerBase(connection, EntityTypes1_20_5.PLAYER));
        connection.put(new AcknowledgedMessagesStorage());
        connection.put(new ArmorTrimStorage());
    }

    @Override
    public MappingData1_20_5 getMappingData() {
        return MAPPINGS;
    }

    @Override
    public ProtocolLogger getLogger() {
        return LOGGER;
    }

    public EntityPacketRewriter1_20_5 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPacketRewriter1_20_5 getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public TagRewriter<ClientboundPacket1_20_3> getTagRewriter() {
        return this.tagRewriter;
    }

    @Override
    public ComponentRewriter<ClientboundPacket1_20_3> getComponentRewriter() {
        return this.componentRewriter;
    }

    @Override
    protected PacketTypesProvider<ClientboundPacket1_20_3, ClientboundPacket1_20_5, ServerboundPacket1_20_3, ServerboundPacket1_20_5> createPacketTypesProvider() {
        return new SimplePacketTypesProvider<ClientboundPacket1_20_3, ClientboundPacket1_20_5, ServerboundPacket1_20_3, ServerboundPacket1_20_5>(ProtocolUtil.packetTypeMap(this.unmappedClientboundPacketType, ClientboundPackets1_20_3.class, ClientboundConfigurationPackets1_20_3.class), ProtocolUtil.packetTypeMap(this.mappedClientboundPacketType, ClientboundPackets1_20_5.class, ClientboundConfigurationPackets1_20_5.class), ProtocolUtil.packetTypeMap(this.mappedServerboundPacketType, ServerboundPackets1_20_3.class, ServerboundConfigurationPackets1_20_2.class), ProtocolUtil.packetTypeMap(this.unmappedServerboundPacketType, ServerboundPackets1_20_5.class, ServerboundConfigurationPackets1_20_5.class));
    }
}

