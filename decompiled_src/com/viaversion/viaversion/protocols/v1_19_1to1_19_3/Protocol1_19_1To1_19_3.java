/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_19_1to1_19_3;

import com.google.common.primitives.Longs;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.SoundEvent;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_3;
import com.viaversion.viaversion.api.minecraft.signature.SignableCommandArgumentsProvider;
import com.viaversion.viaversion.api.minecraft.signature.model.DecoratableMessage;
import com.viaversion.viaversion.api.minecraft.signature.model.MessageMetadata;
import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_1;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ServerboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.rewriter.EntityPacketRewriter1_19_3;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.rewriter.ItemPacketRewriter1_19_3;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.storage.NonceStorage1_19_3;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.storage.ReceivedMessagesStorage;
import com.viaversion.viaversion.protocols.v1_19to1_19_1.packet.ClientboundPackets1_19_1;
import com.viaversion.viaversion.protocols.v1_19to1_19_1.packet.ServerboundPackets1_19_1;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Pair;
import java.security.SignatureException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class Protocol1_19_1To1_19_3
extends AbstractProtocol<ClientboundPackets1_19_1, ClientboundPackets1_19_3, ServerboundPackets1_19_1, ServerboundPackets1_19_3> {
    public static final MappingData MAPPINGS = new MappingDataBase("1.19", "1.19.3");
    static final UUID ZERO_UUID = new UUID(0L, 0L);
    static final byte[] EMPTY_BYTES = new byte[0];
    final EntityPacketRewriter1_19_3 entityRewriter = new EntityPacketRewriter1_19_3(this);
    final ItemPacketRewriter1_19_3 itemRewriter = new ItemPacketRewriter1_19_3(this);
    final TagRewriter<ClientboundPackets1_19_1> tagRewriter = new TagRewriter<ClientboundPackets1_19_1>(this);

    public Protocol1_19_1To1_19_3() {
        super(ClientboundPackets1_19_1.class, ClientboundPackets1_19_3.class, ServerboundPackets1_19_1.class, ServerboundPackets1_19_3.class);
    }

    @Override
    protected void registerPackets() {
        this.tagRewriter.registerGeneric(ClientboundPackets1_19_1.UPDATE_TAGS);
        this.entityRewriter.register();
        this.itemRewriter.register();
        PacketHandler soundHandler = wrapper -> {
            int soundId = wrapper.read(Types.VAR_INT);
            soundId = MAPPINGS.getSoundMappings().getNewId(soundId);
            if (soundId == -1) {
                wrapper.cancel();
                return;
            }
            wrapper.write(Types.SOUND_EVENT, Holder.of(soundId));
        };
        this.registerClientbound(ClientboundPackets1_19_1.SOUND_ENTITY, soundHandler);
        this.registerClientbound(ClientboundPackets1_19_1.SOUND, soundHandler);
        this.registerClientbound(ClientboundPackets1_19_1.CUSTOM_SOUND, ClientboundPackets1_19_3.SOUND, (PacketWrapper wrapper) -> {
            String soundIdentifier = wrapper.read(Types.STRING);
            wrapper.write(Types.SOUND_EVENT, Holder.of(new SoundEvent(soundIdentifier, null)));
        });
        new StatisticsRewriter<ClientboundPackets1_19_1>(this).register(ClientboundPackets1_19_1.AWARD_STATS);
        CommandRewriter<ClientboundPackets1_19_1> commandRewriter = new CommandRewriter<ClientboundPackets1_19_1>((Protocol)this){

            @Override
            public void handleArgument(PacketWrapper wrapper, String argumentType) {
                switch (argumentType) {
                    case "minecraft:item_enchantment": {
                        wrapper.write(Types.STRING, "minecraft:enchantment");
                        break;
                    }
                    case "minecraft:mob_effect": {
                        wrapper.write(Types.STRING, "minecraft:mob_effect");
                        break;
                    }
                    case "minecraft:entity_summon": {
                        wrapper.write(Types.STRING, "minecraft:entity_type");
                        break;
                    }
                    default: {
                        super.handleArgument(wrapper, argumentType);
                    }
                }
            }

            @Override
            public String handleArgumentType(String argumentType) {
                String string;
                switch (argumentType) {
                    case "minecraft:resource": {
                        string = "minecraft:resource_key";
                        break;
                    }
                    case "minecraft:resource_or_tag": {
                        string = "minecraft:resource_or_tag_key";
                        break;
                    }
                    case "minecraft:entity_summon": 
                    case "minecraft:item_enchantment": 
                    case "minecraft:mob_effect": {
                        string = "minecraft:resource";
                        break;
                    }
                    default: {
                        string = argumentType;
                    }
                }
                return string;
            }
        };
        commandRewriter.registerDeclareCommands1_19(ClientboundPackets1_19_1.COMMANDS);
        this.registerClientbound(ClientboundPackets1_19_1.SERVER_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.OPTIONAL_COMPONENT);
                this.map(Types.OPTIONAL_STRING);
                this.read(Types.BOOLEAN);
            }
        });
        this.registerClientbound(ClientboundPackets1_19_1.PLAYER_CHAT, ClientboundPackets1_19_3.DISGUISED_CHAT, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.read(Types.OPTIONAL_BYTE_ARRAY_PRIMITIVE);
                this.handler(wrapper -> {
                    int filterMaskType;
                    PlayerMessageSignature signature = wrapper.read(Types.PLAYER_MESSAGE_SIGNATURE);
                    if (!signature.uuid().equals(ZERO_UUID) && signature.signatureBytes().length != 0) {
                        ReceivedMessagesStorage messagesStorage = wrapper.user().get(ReceivedMessagesStorage.class);
                        messagesStorage.add(signature);
                        if (messagesStorage.tickUnacknowledged() > 64) {
                            messagesStorage.resetUnacknowledgedCount();
                            PacketWrapper chatAckPacket = wrapper.create(ServerboundPackets1_19_1.CHAT_ACK);
                            chatAckPacket.write(Types.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
                            chatAckPacket.write(Types.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
                            chatAckPacket.sendToServer(Protocol1_19_1To1_19_3.class);
                        }
                    }
                    String plainMessage = wrapper.read(Types.STRING);
                    JsonElement decoratedMessage = wrapper.read(Types.OPTIONAL_COMPONENT);
                    wrapper.read(Types.LONG);
                    wrapper.read(Types.LONG);
                    wrapper.read(Types.PLAYER_MESSAGE_SIGNATURE_ARRAY);
                    JsonElement unsignedMessage = wrapper.read(Types.OPTIONAL_COMPONENT);
                    if (unsignedMessage != null) {
                        decoratedMessage = unsignedMessage;
                    }
                    if (decoratedMessage == null) {
                        decoratedMessage = ComponentUtil.plainToJson(plainMessage);
                    }
                    if ((filterMaskType = wrapper.read(Types.VAR_INT).intValue()) == 2) {
                        wrapper.read(Types.LONG_ARRAY_PRIMITIVE);
                    }
                    wrapper.write(Types.COMPONENT, decoratedMessage);
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_19_3.CHAT_COMMAND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.LONG);
                this.map(Types.LONG);
                this.handler(wrapper -> {
                    ChatSession1_19_1 chatSession = wrapper.user().get(ChatSession1_19_1.class);
                    ReceivedMessagesStorage messagesStorage = wrapper.user().get(ReceivedMessagesStorage.class);
                    int signatures = wrapper.read(Types.VAR_INT);
                    for (int i = 0; i < signatures; ++i) {
                        wrapper.read(Types.STRING);
                        wrapper.read(Types.SIGNATURE_BYTES);
                    }
                    SignableCommandArgumentsProvider argumentsProvider = Via.getManager().getProviders().get(SignableCommandArgumentsProvider.class);
                    if (chatSession != null && argumentsProvider != null) {
                        UUID sender = wrapper.user().getProtocolInfo().getUuid();
                        String message = wrapper.get(Types.STRING, 0);
                        long timestamp = wrapper.get(Types.LONG, 0);
                        long salt = wrapper.get(Types.LONG, 1);
                        List<Pair<String, String>> arguments = argumentsProvider.getSignableArguments(message);
                        wrapper.write(Types.VAR_INT, arguments.size());
                        for (Pair<String, String> argument : arguments) {
                            byte[] signature;
                            MessageMetadata metadata = new MessageMetadata(sender, timestamp, salt);
                            DecoratableMessage decoratableMessage = new DecoratableMessage(argument.value());
                            try {
                                signature = chatSession.signChatMessage(metadata, decoratableMessage, messagesStorage.lastSignatures());
                            }
                            catch (SignatureException e) {
                                throw new RuntimeException(e);
                            }
                            wrapper.write(Types.STRING, argument.key());
                            wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, signature);
                        }
                    } else {
                        wrapper.write(Types.VAR_INT, 0);
                    }
                    wrapper.write(Types.BOOLEAN, false);
                    messagesStorage.resetUnacknowledgedCount();
                    wrapper.write(Types.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
                    wrapper.write(Types.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
                });
                this.read(Types.VAR_INT);
                this.read(Types.ACKNOWLEDGED_BIT_SET);
            }
        });
        this.registerServerbound(ServerboundPackets1_19_3.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.LONG);
                this.map(Types.LONG);
                this.read(Types.OPTIONAL_SIGNATURE_BYTES);
                this.handler(wrapper -> {
                    ChatSession1_19_1 chatSession = wrapper.user().get(ChatSession1_19_1.class);
                    ReceivedMessagesStorage messagesStorage = wrapper.user().get(ReceivedMessagesStorage.class);
                    if (chatSession != null) {
                        byte[] signature;
                        UUID sender = wrapper.user().getProtocolInfo().getUuid();
                        String message = wrapper.get(Types.STRING, 0);
                        long timestamp = wrapper.get(Types.LONG, 0);
                        long salt = wrapper.get(Types.LONG, 1);
                        MessageMetadata metadata = new MessageMetadata(sender, timestamp, salt);
                        DecoratableMessage decoratableMessage = new DecoratableMessage(message);
                        try {
                            signature = chatSession.signChatMessage(metadata, decoratableMessage, messagesStorage.lastSignatures());
                        }
                        catch (SignatureException e) {
                            throw new RuntimeException(e);
                        }
                        wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, signature);
                        wrapper.write(Types.BOOLEAN, decoratableMessage.isDecorated());
                    } else {
                        wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, EMPTY_BYTES);
                        wrapper.write(Types.BOOLEAN, false);
                    }
                    messagesStorage.resetUnacknowledgedCount();
                    wrapper.write(Types.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
                    wrapper.write(Types.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
                });
                this.read(Types.VAR_INT);
                this.read(Types.ACKNOWLEDGED_BIT_SET);
            }
        });
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.BYTE_ARRAY_PRIMITIVE);
                this.handler(wrapper -> {
                    if (wrapper.user().has(ChatSession1_19_1.class)) {
                        wrapper.user().put(new NonceStorage1_19_3(wrapper.passthrough(Types.BYTE_ARRAY_PRIMITIVE)));
                    }
                });
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.handler(wrapper -> {
                    ChatSession1_19_1 chatSession = wrapper.user().get(ChatSession1_19_1.class);
                    wrapper.write(Types.OPTIONAL_PROFILE_KEY, chatSession == null ? null : chatSession.getProfileKey());
                });
                this.map(Types.OPTIONAL_UUID);
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE_ARRAY_PRIMITIVE);
                this.handler(wrapper -> {
                    ChatSession1_19_1 chatSession = wrapper.user().get(ChatSession1_19_1.class);
                    byte[] verifyToken = wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
                    wrapper.write(Types.BOOLEAN, chatSession == null);
                    if (chatSession != null) {
                        byte[] signature;
                        long salt = ThreadLocalRandom.current().nextLong();
                        try {
                            signature = chatSession.sign(signer -> {
                                signer.accept(wrapper.user().remove(NonceStorage1_19_3.class).nonce());
                                signer.accept(Longs.toByteArray((long)salt));
                            });
                        }
                        catch (SignatureException e) {
                            throw new RuntimeException(e);
                        }
                        wrapper.write(Types.LONG, salt);
                        wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, signature);
                    } else {
                        wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, verifyToken);
                    }
                });
            }
        });
        this.cancelServerbound(ServerboundPackets1_19_3.CHAT_SESSION_UPDATE);
        this.cancelClientbound(ClientboundPackets1_19_1.DELETE_CHAT);
        this.cancelClientbound(ClientboundPackets1_19_1.PLAYER_CHAT_HEADER);
        this.cancelClientbound(ClientboundPackets1_19_1.CHAT_PREVIEW);
        this.cancelClientbound(ClientboundPackets1_19_1.SET_DISPLAY_CHAT_PREVIEW);
        this.cancelServerbound(ServerboundPackets1_19_3.CHAT_ACK);
    }

    @Override
    protected void onMappingDataLoaded() {
        Types1_19_3.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("block_marker", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION).reader("item", ParticleType.Readers.ITEM1_13_2).reader("vibration", ParticleType.Readers.VIBRATION1_19).reader("sculk_charge", ParticleType.Readers.SCULK_CHARGE).reader("shriek", ParticleType.Readers.SHRIEK);
        EntityTypes1_19_3.initialize(this);
        this.tagRewriter.removeTag(RegistryType.ITEM, "minecraft:overworld_natural_logs");
        this.tagRewriter.removeTag(RegistryType.BLOCK, "minecraft:non_flammable_wood");
        super.onMappingDataLoaded();
    }

    @Override
    public void init(UserConnection user) {
        user.put(new ReceivedMessagesStorage());
        this.addEntityTracker(user, new EntityTrackerBase(user, EntityTypes1_19_3.PLAYER));
    }

    @Override
    public MappingData getMappingData() {
        return MAPPINGS;
    }

    public EntityPacketRewriter1_19_3 getEntityRewriter() {
        return this.entityRewriter;
    }

    public ItemPacketRewriter1_19_3 getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public TagRewriter<ClientboundPackets1_19_1> getTagRewriter() {
        return this.tagRewriter;
    }
}

