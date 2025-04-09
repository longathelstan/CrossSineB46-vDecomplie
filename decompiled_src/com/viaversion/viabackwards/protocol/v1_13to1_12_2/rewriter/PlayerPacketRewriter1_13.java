/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13to1_12_2.rewriter;

import com.google.common.base.Joiner;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.data.ParticleIdMappings1_12_2;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage.TabCompleteStorage;
import com.viaversion.viabackwards.utils.ChatUtil;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.rewriter.ItemPacketRewriter1_13;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ServerboundPackets1_12_1;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.util.Key;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerPacketRewriter1_13
extends RewriterBase<Protocol1_13To1_12_2> {
    final CommandRewriter<ClientboundPackets1_13> commandRewriter;

    public PlayerPacketRewriter1_13(Protocol1_13To1_12_2 protocol) {
        super(protocol);
        this.commandRewriter = new CommandRewriter(this.protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(State.LOGIN, ClientboundLoginPackets.CUSTOM_QUERY.getId(), -1, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(packetWrapper -> {
                    packetWrapper.cancel();
                    packetWrapper.create(ServerboundLoginPackets.CUSTOM_QUERY_ANSWER.getId(), wrapper -> {
                        wrapper.write(Types.VAR_INT, packetWrapper.read(Types.VAR_INT));
                        wrapper.write(Types.BOOLEAN, false);
                    }).sendToServer(Protocol1_13To1_12_2.class);
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.CUSTOM_PAYLOAD, wrapper -> {
            String channel = wrapper.read(Types.STRING);
            if (channel.equals("minecraft:trader_list")) {
                wrapper.write(Types.STRING, "MC|TrList");
                ((Protocol1_13To1_12_2)this.protocol).getItemRewriter().handleTradeList(wrapper);
            } else {
                String oldChannel = ItemPacketRewriter1_13.getOldPluginChannelId(channel);
                if (oldChannel == null) {
                    if (!Via.getConfig().isSuppressConversionWarnings()) {
                        String string = channel;
                        ((Protocol1_13To1_12_2)this.protocol).getLogger().warning("Ignoring clientbound plugin message with channel: " + string);
                    }
                    wrapper.cancel();
                    return;
                }
                wrapper.write(Types.STRING, oldChannel);
                if (oldChannel.equals("REGISTER") || oldChannel.equals("UNREGISTER")) {
                    String[] channels = new String(wrapper.read(Types.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                    ArrayList<String> rewrittenChannels = new ArrayList<String>();
                    for (String s : channels) {
                        String rewritten = ItemPacketRewriter1_13.getOldPluginChannelId(s);
                        if (rewritten != null) {
                            rewrittenChannels.add(rewritten);
                            continue;
                        }
                        if (Via.getConfig().isSuppressConversionWarnings()) continue;
                        String string = s;
                        String string2 = oldChannel;
                        ((Protocol1_13To1_12_2)this.protocol).getLogger().warning("Ignoring plugin channel in clientbound " + string2 + ": " + string);
                    }
                    wrapper.write(Types.REMAINING_BYTES, Joiner.on((char)'\u0000').join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
                }
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.LEVEL_PARTICLES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    ParticleIdMappings1_12_2.ParticleData old = ParticleIdMappings1_12_2.getMapping(wrapper.get(Types.INT, 0));
                    wrapper.set(Types.INT, 0, old.getHistoryId());
                    int[] data = old.rewriteData((Protocol1_13To1_12_2)PlayerPacketRewriter1_13.this.protocol, wrapper);
                    if (data != null) {
                        if (old.getHandler().isBlockHandler() && data[0] == 0) {
                            wrapper.cancel();
                            return;
                        }
                        for (int i : data) {
                            wrapper.write(Types.VAR_INT, i);
                        }
                    }
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.PLAYER_INFO, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(packetWrapper -> {
                    TabCompleteStorage storage = packetWrapper.user().get(TabCompleteStorage.class);
                    int action = packetWrapper.passthrough(Types.VAR_INT);
                    int nPlayers = packetWrapper.passthrough(Types.VAR_INT);
                    for (int i = 0; i < nPlayers; ++i) {
                        UUID uuid = packetWrapper.passthrough(Types.UUID);
                        if (action == 0) {
                            String name = packetWrapper.passthrough(Types.STRING);
                            storage.usernames().put(uuid, name);
                            int nProperties = packetWrapper.passthrough(Types.VAR_INT);
                            for (int j = 0; j < nProperties; ++j) {
                                packetWrapper.passthrough(Types.STRING);
                                packetWrapper.passthrough(Types.STRING);
                                packetWrapper.passthrough(Types.OPTIONAL_STRING);
                            }
                            packetWrapper.passthrough(Types.VAR_INT);
                            packetWrapper.passthrough(Types.VAR_INT);
                            packetWrapper.passthrough(Types.OPTIONAL_COMPONENT);
                            continue;
                        }
                        if (action == 1) {
                            packetWrapper.passthrough(Types.VAR_INT);
                            continue;
                        }
                        if (action == 2) {
                            packetWrapper.passthrough(Types.VAR_INT);
                            continue;
                        }
                        if (action == 3) {
                            packetWrapper.passthrough(Types.OPTIONAL_COMPONENT);
                            continue;
                        }
                        if (action != 4) continue;
                        storage.usernames().remove(uuid);
                    }
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.SET_OBJECTIVE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    byte mode2 = wrapper.get(Types.BYTE, 0);
                    if (mode2 == 0 || mode2 == 2) {
                        JsonElement value = wrapper.read(Types.COMPONENT);
                        String legacyValue = ((Protocol1_13To1_12_2)PlayerPacketRewriter1_13.this.protocol).jsonToLegacy(wrapper.user(), value);
                        wrapper.write(Types.STRING, ChatUtil.fromLegacy(legacyValue, 'f', 32));
                        int type = wrapper.read(Types.VAR_INT);
                        wrapper.write(Types.STRING, type == 1 ? "hearts" : "integer");
                    }
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.SET_PLAYER_TEAM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    byte action = wrapper.get(Types.BYTE, 0);
                    if (action == 0 || action == 2) {
                        JsonElement displayName = wrapper.read(Types.COMPONENT);
                        String legacyTextDisplayName = ((Protocol1_13To1_12_2)PlayerPacketRewriter1_13.this.protocol).jsonToLegacy(wrapper.user(), displayName);
                        wrapper.write(Types.STRING, ChatUtil.fromLegacy(legacyTextDisplayName, 'f', 32));
                        byte flags = wrapper.read(Types.BYTE);
                        String nameTagVisibility = wrapper.read(Types.STRING);
                        String collisionRule = wrapper.read(Types.STRING);
                        int colour = wrapper.read(Types.VAR_INT);
                        if (colour == 21) {
                            colour = -1;
                        }
                        JsonElement prefixComponent = wrapper.read(Types.COMPONENT);
                        JsonElement suffixComponent = wrapper.read(Types.COMPONENT);
                        String prefix = ((Protocol1_13To1_12_2)PlayerPacketRewriter1_13.this.protocol).jsonToLegacy(wrapper.user(), prefixComponent);
                        if (ViaBackwards.getConfig().addTeamColorTo1_13Prefix()) {
                            String string = colour > -1 && colour <= 15 ? Integer.toHexString(colour) : "r";
                            String string2 = prefix;
                            prefix = string2 + "\u00a7" + string;
                        }
                        String suffix = ((Protocol1_13To1_12_2)PlayerPacketRewriter1_13.this.protocol).jsonToLegacy(wrapper.user(), suffixComponent);
                        wrapper.write(Types.STRING, ChatUtil.fromLegacyPrefix(prefix, 'f', 16));
                        wrapper.write(Types.STRING, ChatUtil.fromLegacy(suffix, '\u0000', 16));
                        wrapper.write(Types.BYTE, flags);
                        wrapper.write(Types.STRING, nameTagVisibility);
                        wrapper.write(Types.STRING, collisionRule);
                        wrapper.write(Types.BYTE, (byte)colour);
                    }
                    if (action == 0 || action == 3 || action == 4) {
                        wrapper.passthrough(Types.STRING_ARRAY);
                    }
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.COMMANDS, null, wrapper -> {
            wrapper.cancel();
            TabCompleteStorage storage = wrapper.user().get(TabCompleteStorage.class);
            if (!storage.commands().isEmpty()) {
                storage.commands().clear();
            }
            int size = wrapper.read(Types.VAR_INT);
            boolean initialNodes = true;
            for (int i = 0; i < size; ++i) {
                byte flags = wrapper.read(Types.BYTE);
                wrapper.read(Types.VAR_INT_ARRAY_PRIMITIVE);
                if ((flags & 8) != 0) {
                    wrapper.read(Types.VAR_INT);
                }
                byte nodeType = (byte)(flags & 3);
                if (initialNodes && nodeType == 2) {
                    initialNodes = false;
                }
                if (nodeType == 1 || nodeType == 2) {
                    String name = wrapper.read(Types.STRING);
                    if (nodeType == 1 && initialNodes) {
                        String string = name;
                        storage.commands().add("/" + string);
                    }
                }
                if (nodeType == 2) {
                    this.commandRewriter.handleArgument(wrapper, wrapper.read(Types.STRING));
                }
                if ((flags & 0x10) == 0) continue;
                wrapper.read(Types.STRING);
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.COMMAND_SUGGESTIONS, wrapper -> {
            TabCompleteStorage storage = wrapper.user().get(TabCompleteStorage.class);
            if (storage.lastRequest() == null) {
                wrapper.cancel();
                return;
            }
            if (storage.lastId() != wrapper.read(Types.VAR_INT).intValue()) {
                wrapper.cancel();
            }
            int start = wrapper.read(Types.VAR_INT);
            int length = wrapper.read(Types.VAR_INT);
            int lastRequestPartIndex = storage.lastRequest().lastIndexOf(32) + 1;
            if (lastRequestPartIndex != start) {
                wrapper.cancel();
            }
            if (length != storage.lastRequest().length() - lastRequestPartIndex) {
                wrapper.cancel();
            }
            int count = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < count; ++i) {
                String match = wrapper.read(Types.STRING);
                String string = match;
                String string2 = start == 0 && !storage.isLastAssumeCommand() ? "/" : "";
                wrapper.write(Types.STRING, string2 + string);
                wrapper.read(Types.OPTIONAL_COMPONENT);
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_12_1.COMMAND_SUGGESTION, wrapper -> {
            TabCompleteStorage storage = wrapper.user().get(TabCompleteStorage.class);
            ArrayList<String> suggestions = new ArrayList<String>();
            String command = wrapper.read(Types.STRING);
            boolean assumeCommand = wrapper.read(Types.BOOLEAN);
            wrapper.read(Types.OPTIONAL_POSITION1_8);
            if (!assumeCommand && !command.startsWith("/")) {
                String buffer = command.substring(command.lastIndexOf(32) + 1);
                for (String value : storage.usernames().values()) {
                    if (!PlayerPacketRewriter1_13.startsWithIgnoreCase(value, buffer)) continue;
                    suggestions.add(value);
                }
            } else if (!storage.commands().isEmpty() && !command.contains(" ")) {
                for (String value : storage.commands()) {
                    if (!PlayerPacketRewriter1_13.startsWithIgnoreCase(value, command)) continue;
                    suggestions.add(value);
                }
            }
            if (!suggestions.isEmpty()) {
                wrapper.cancel();
                PacketWrapper response = wrapper.create(ClientboundPackets1_12_1.COMMAND_SUGGESTIONS);
                response.write(Types.VAR_INT, suggestions.size());
                for (String value : suggestions) {
                    response.write(Types.STRING, value);
                }
                response.scheduleSend(Protocol1_13To1_12_2.class);
                storage.setLastRequest(null);
                return;
            }
            if (!assumeCommand && command.startsWith("/")) {
                command = command.substring(1);
            }
            int id = ThreadLocalRandom.current().nextInt();
            wrapper.write(Types.VAR_INT, id);
            wrapper.write(Types.STRING, command);
            storage.setLastId(id);
            storage.setLastAssumeCommand(assumeCommand);
            storage.setLastRequest(command);
        });
        ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_12_1.CUSTOM_PAYLOAD, wrapper -> {
            String channel;
            switch (channel = wrapper.read(Types.STRING)) {
                case "MC|BSign": 
                case "MC|BEdit": {
                    wrapper.setPacketType(ServerboundPackets1_13.EDIT_BOOK);
                    Item book = wrapper.read(Types.ITEM1_8);
                    wrapper.write(Types.ITEM1_13, ((Protocol1_13To1_12_2)this.protocol).getItemRewriter().handleItemToServer(wrapper.user(), book));
                    boolean signing = channel.equals("MC|BSign");
                    wrapper.write(Types.BOOLEAN, signing);
                    break;
                }
                case "MC|ItemName": {
                    wrapper.setPacketType(ServerboundPackets1_13.RENAME_ITEM);
                    break;
                }
                case "MC|AdvCmd": {
                    byte type = wrapper.read(Types.BYTE);
                    if (type == 0) {
                        wrapper.setPacketType(ServerboundPackets1_13.SET_COMMAND_BLOCK);
                        wrapper.cancel();
                        ((Protocol1_13To1_12_2)this.protocol).getLogger().warning("Client send MC|AdvCmd custom payload to update command block, weird!");
                        break;
                    }
                    if (type == 1) {
                        wrapper.setPacketType(ServerboundPackets1_13.SET_COMMAND_MINECART);
                        wrapper.write(Types.VAR_INT, wrapper.read(Types.INT));
                        wrapper.passthrough(Types.STRING);
                        wrapper.passthrough(Types.BOOLEAN);
                        break;
                    }
                    wrapper.cancel();
                    break;
                }
                case "MC|AutoCmd": {
                    String mode2;
                    wrapper.setPacketType(ServerboundPackets1_13.SET_COMMAND_BLOCK);
                    int x = wrapper.read(Types.INT);
                    int y = wrapper.read(Types.INT);
                    int z = wrapper.read(Types.INT);
                    wrapper.write(Types.BLOCK_POSITION1_8, new BlockPosition(x, (short)y, z));
                    wrapper.passthrough(Types.STRING);
                    byte flags = 0;
                    if (wrapper.read(Types.BOOLEAN).booleanValue()) {
                        flags = (byte)(flags | 1);
                    }
                    int modeId = (mode2 = wrapper.read(Types.STRING)).equals("SEQUENCE") ? 0 : (mode2.equals("AUTO") ? 1 : 2);
                    wrapper.write(Types.VAR_INT, modeId);
                    if (wrapper.read(Types.BOOLEAN).booleanValue()) {
                        flags = (byte)(flags | 2);
                    }
                    if (wrapper.read(Types.BOOLEAN).booleanValue()) {
                        flags = (byte)(flags | 4);
                    }
                    wrapper.write(Types.BYTE, flags);
                    break;
                }
                case "MC|Struct": {
                    wrapper.setPacketType(ServerboundPackets1_13.SET_STRUCTURE_BLOCK);
                    int x = wrapper.read(Types.INT);
                    int y = wrapper.read(Types.INT);
                    int z = wrapper.read(Types.INT);
                    wrapper.write(Types.BLOCK_POSITION1_8, new BlockPosition(x, (short)y, z));
                    wrapper.write(Types.VAR_INT, wrapper.read(Types.BYTE) - 1);
                    String mode3 = wrapper.read(Types.STRING);
                    int modeId = mode3.equals("SAVE") ? 0 : (mode3.equals("LOAD") ? 1 : (mode3.equals("CORNER") ? 2 : 3));
                    wrapper.write(Types.VAR_INT, modeId);
                    wrapper.passthrough(Types.STRING);
                    wrapper.write(Types.BYTE, wrapper.read(Types.INT).byteValue());
                    wrapper.write(Types.BYTE, wrapper.read(Types.INT).byteValue());
                    wrapper.write(Types.BYTE, wrapper.read(Types.INT).byteValue());
                    wrapper.write(Types.BYTE, wrapper.read(Types.INT).byteValue());
                    wrapper.write(Types.BYTE, wrapper.read(Types.INT).byteValue());
                    wrapper.write(Types.BYTE, wrapper.read(Types.INT).byteValue());
                    String mirror = wrapper.read(Types.STRING);
                    int mirrorId = mode3.equals("NONE") ? 0 : (mode3.equals("LEFT_RIGHT") ? 1 : 2);
                    String rotation = wrapper.read(Types.STRING);
                    int rotationId = mode3.equals("NONE") ? 0 : (mode3.equals("CLOCKWISE_90") ? 1 : (mode3.equals("CLOCKWISE_180") ? 2 : 3));
                    wrapper.passthrough(Types.STRING);
                    byte flags = 0;
                    if (wrapper.read(Types.BOOLEAN).booleanValue()) {
                        flags = (byte)(flags | 1);
                    }
                    if (wrapper.read(Types.BOOLEAN).booleanValue()) {
                        flags = (byte)(flags | 2);
                    }
                    if (wrapper.read(Types.BOOLEAN).booleanValue()) {
                        flags = (byte)(flags | 4);
                    }
                    wrapper.passthrough(Types.FLOAT);
                    wrapper.passthrough(Types.VAR_LONG);
                    wrapper.write(Types.BYTE, flags);
                    break;
                }
                case "MC|Beacon": {
                    wrapper.setPacketType(ServerboundPackets1_13.SET_BEACON);
                    wrapper.write(Types.VAR_INT, wrapper.read(Types.INT));
                    wrapper.write(Types.VAR_INT, wrapper.read(Types.INT));
                    break;
                }
                case "MC|TrSel": {
                    wrapper.setPacketType(ServerboundPackets1_13.SELECT_TRADE);
                    wrapper.write(Types.VAR_INT, wrapper.read(Types.INT));
                    break;
                }
                case "MC|PickItem": {
                    wrapper.setPacketType(ServerboundPackets1_13.PICK_ITEM);
                    break;
                }
                default: {
                    String newChannel = ItemPacketRewriter1_13.getNewPluginChannelId(channel);
                    if (newChannel == null) {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                            String string = channel;
                            ((Protocol1_13To1_12_2)this.protocol).getLogger().warning("Ignoring serverbound plugin message with channel: " + string);
                        }
                        wrapper.cancel();
                        return;
                    }
                    wrapper.write(Types.STRING, newChannel);
                    if (!newChannel.equals("minecraft:register") && !newChannel.equals("minecraft:unregister")) break;
                    String[] channels = new String(wrapper.read(Types.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                    ArrayList<String> rewrittenChannels = new ArrayList<String>();
                    for (String s : channels) {
                        String rewritten = ItemPacketRewriter1_13.getNewPluginChannelId(s);
                        if (rewritten != null) {
                            rewrittenChannels.add(rewritten);
                            continue;
                        }
                        if (Via.getConfig().isSuppressConversionWarnings()) continue;
                        String string = s;
                        String string2 = Key.stripMinecraftNamespace(newChannel).toUpperCase(Locale.ROOT);
                        ((Protocol1_13To1_12_2)this.protocol).getLogger().warning("Ignoring plugin channel in serverbound " + string2 + ": " + string);
                    }
                    if (!rewrittenChannels.isEmpty()) {
                        wrapper.write(Types.REMAINING_BYTES, Joiner.on((char)'\u0000').join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
                        break;
                    }
                    wrapper.cancel();
                }
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.AWARD_STATS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int size;
                    int newSize = size = wrapper.get(Types.VAR_INT, 0).intValue();
                    block4: for (int i = 0; i < size; ++i) {
                        int categoryId = wrapper.read(Types.VAR_INT);
                        int statisticId = wrapper.read(Types.VAR_INT);
                        String name = "";
                        switch (categoryId) {
                            case 0: 
                            case 1: 
                            case 2: 
                            case 3: 
                            case 4: 
                            case 5: 
                            case 6: 
                            case 7: {
                                wrapper.read(Types.VAR_INT);
                                --newSize;
                                continue block4;
                            }
                            case 8: {
                                name = (String)((Protocol1_13To1_12_2)PlayerPacketRewriter1_13.this.protocol).getMappingData().getStatisticMappings().get(statisticId);
                                if (name == null) {
                                    wrapper.read(Types.VAR_INT);
                                    --newSize;
                                    continue block4;
                                }
                            }
                            default: {
                                wrapper.write(Types.STRING, name);
                                wrapper.passthrough(Types.VAR_INT);
                            }
                        }
                    }
                    if (newSize != size) {
                        wrapper.set(Types.VAR_INT, 0, newSize);
                    }
                });
            }
        });
    }

    static boolean startsWithIgnoreCase(String string, String prefix) {
        if (string.length() < prefix.length()) {
            return false;
        }
        return string.regionMatches(true, 0, prefix, 0, prefix.length());
    }
}

