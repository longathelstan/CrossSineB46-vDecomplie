/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_8to1_9.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.GameMode;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_9;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.CommandBlockProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.CompressionProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.MainHandProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.ClientWorld1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.EntityTracker1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.MovementTracker;
import com.viaversion.viaversion.util.ComponentUtil;

public class PlayerPacketRewriter1_9 {
    public static void register(final Protocol1_8To1_9 protocol) {
        protocol.registerClientbound(ClientboundPackets1_8.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING, Protocol1_8To1_9.STRING_TO_JSON);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    JsonObject obj = (JsonObject)wrapper.get(Types.COMPONENT, 0);
                    if (obj.get("translate") != null && obj.get("translate").getAsString().equals("gameMode.changed")) {
                        EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                        String gameMode = tracker.getGameMode().text();
                        JsonObject gameModeObject = new JsonObject();
                        gameModeObject.addProperty("text", gameMode);
                        gameModeObject.addProperty("color", "gray");
                        gameModeObject.addProperty("italic", true);
                        JsonArray array = new JsonArray();
                        array.add(gameModeObject);
                        obj.add("with", array);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.TAB_LIST, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING, Protocol1_8To1_9.STRING_TO_JSON);
                this.map(Types.STRING, Protocol1_8To1_9.STRING_TO_JSON);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.DISCONNECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING, Protocol1_8To1_9.STRING_TO_JSON);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.SET_TITLES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int action = wrapper.get(Types.VAR_INT, 0);
                    if (action == 0 || action == 1) {
                        Protocol1_8To1_9.STRING_TO_JSON.write(wrapper, wrapper.read(Types.STRING));
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.PLAYER_POSITION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.BYTE);
                this.create(Types.VAR_INT, 0);
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.SET_PLAYER_TEAM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    byte mode2 = wrapper.get(Types.BYTE, 0);
                    if (mode2 == 0 || mode2 == 2) {
                        wrapper.passthrough(Types.STRING);
                        wrapper.passthrough(Types.STRING);
                        wrapper.passthrough(Types.STRING);
                        wrapper.passthrough(Types.BYTE);
                        wrapper.passthrough(Types.STRING);
                        wrapper.write(Types.STRING, Via.getConfig().isPreventCollision() ? "never" : "");
                        wrapper.passthrough(Types.BYTE);
                    }
                    if (mode2 == 0 || mode2 == 3 || mode2 == 4) {
                        String[] players = wrapper.passthrough(Types.STRING_ARRAY);
                        EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                        String myName = wrapper.user().getProtocolInfo().getUsername();
                        String teamName = wrapper.get(Types.STRING, 0);
                        for (String player : players) {
                            if (!entityTracker.isAutoTeam() || !player.equalsIgnoreCase(myName)) continue;
                            if (mode2 == 4) {
                                wrapper.send(Protocol1_8To1_9.class);
                                wrapper.cancel();
                                entityTracker.sendTeamPacket(true, true);
                                entityTracker.setCurrentTeam("viaversion");
                                continue;
                            }
                            entityTracker.sendTeamPacket(false, true);
                            entityTracker.setCurrentTeam(teamName);
                        }
                    }
                    if (mode2 == 1) {
                        EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                        String teamName = wrapper.get(Types.STRING, 0);
                        if (entityTracker.isAutoTeam() && teamName.equals(entityTracker.getCurrentTeam())) {
                            wrapper.send(Protocol1_8To1_9.class);
                            wrapper.cancel();
                            entityTracker.sendTeamPacket(true, true);
                            entityTracker.setCurrentTeam("viaversion");
                        }
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.INT, 0);
                    EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                    tracker.addEntity(entityId, EntityTypes1_9.EntityType.PLAYER);
                    tracker.setClientEntityId(entityId);
                });
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.STRING);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                    short gamemodeId = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    gamemodeId = (short)(gamemodeId & 0xFFFFFFF7);
                    tracker.setGameMode(GameMode.getById(gamemodeId));
                });
                this.handler(wrapper -> {
                    Object clientWorld = wrapper.user().getClientWorld(Protocol1_8To1_9.class);
                    byte dimensionId = wrapper.get(Types.BYTE, 0);
                    ((ClientWorld)clientWorld).setEnvironment(dimensionId);
                });
                this.handler(wrapper -> {
                    CommandBlockProvider provider = Via.getManager().getProviders().get(CommandBlockProvider.class);
                    provider.sendPermission(wrapper.user());
                });
                this.handler(wrapper -> {
                    EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                    if (Via.getConfig().isAutoTeam()) {
                        entityTracker.setAutoTeam(true);
                        wrapper.send(Protocol1_8To1_9.class);
                        wrapper.cancel();
                        entityTracker.sendTeamPacket(true, true);
                        entityTracker.setCurrentTeam("viaversion");
                    } else {
                        entityTracker.setAutoTeam(false);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.PLAYER_INFO, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int action = wrapper.get(Types.VAR_INT, 0);
                    int count = wrapper.get(Types.VAR_INT, 1);
                    for (int i = 0; i < count; ++i) {
                        wrapper.passthrough(Types.UUID);
                        if (action == 0) {
                            wrapper.passthrough(Types.STRING);
                            int properties = wrapper.passthrough(Types.VAR_INT);
                            for (int j = 0; j < properties; ++j) {
                                wrapper.passthrough(Types.STRING);
                                wrapper.passthrough(Types.STRING);
                                wrapper.passthrough(Types.OPTIONAL_STRING);
                            }
                            wrapper.passthrough(Types.VAR_INT);
                            wrapper.passthrough(Types.VAR_INT);
                            String displayName = wrapper.read(Types.OPTIONAL_STRING);
                            wrapper.write(Types.OPTIONAL_COMPONENT, displayName != null ? Protocol1_8To1_9.STRING_TO_JSON.transform(wrapper, displayName) : null);
                            continue;
                        }
                        if (action == 1 || action == 2) {
                            wrapper.passthrough(Types.VAR_INT);
                            continue;
                        }
                        if (action != 3) continue;
                        String displayName = wrapper.read(Types.OPTIONAL_STRING);
                        wrapper.write(Types.OPTIONAL_COMPONENT, displayName != null ? Protocol1_8To1_9.STRING_TO_JSON.transform(wrapper, displayName) : null);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.CUSTOM_PAYLOAD, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.handlerSoftFail(wrapper -> {
                    String name = wrapper.get(Types.STRING, 0);
                    if (name.equals("MC|BOpen")) {
                        wrapper.write(Types.VAR_INT, 0);
                    } else if (name.equals("MC|TrList")) {
                        protocol.getItemRewriter().handleTradeList(wrapper);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.STRING);
                this.handler(wrapper -> {
                    CommandBlockProvider provider = Via.getManager().getProviders().get(CommandBlockProvider.class);
                    provider.sendPermission(wrapper.user());
                    EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                    short gamemode = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    tracker.setGameMode(GameMode.getById(gamemode));
                    ClientWorld1_9 clientWorld = (ClientWorld1_9)wrapper.user().getClientWorld(Protocol1_8To1_9.class);
                    int dimensionId = wrapper.get(Types.INT, 0);
                    if (clientWorld.setEnvironment(dimensionId)) {
                        tracker.clearEntities();
                        clientWorld.getLoadedChunks().clear();
                        provider.unloadChunks(wrapper.user());
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.GAME_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.FLOAT);
                this.handler(wrapper -> {
                    short reason = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    if (reason == 3) {
                        int gamemode = wrapper.get(Types.FLOAT, 0).intValue();
                        EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                        tracker.setGameMode(GameMode.getById(gamemode));
                    } else if (reason == 4) {
                        wrapper.set(Types.FLOAT, 0, Float.valueOf(1.0f));
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.SET_COMPRESSION, null, wrapper -> {
            wrapper.cancel();
            CompressionProvider provider = Via.getManager().getProviders().get(CompressionProvider.class);
            provider.handlePlayCompression(wrapper.user(), wrapper.read(Types.VAR_INT));
        });
        protocol.registerServerbound(ServerboundPackets1_9.COMMAND_SUGGESTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.read(Types.BOOLEAN);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.CLIENT_INFORMATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.BYTE);
                this.map(Types.VAR_INT, Types.BYTE);
                this.map(Types.BOOLEAN);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    int hand = wrapper.read(Types.VAR_INT);
                    if (Via.getConfig().isLeftHandedHandling() && hand == 0) {
                        wrapper.set(Types.UNSIGNED_BYTE, 0, (short)(wrapper.get(Types.UNSIGNED_BYTE, 0).intValue() | 0x80));
                    }
                    wrapper.sendToServer(Protocol1_8To1_9.class);
                    wrapper.cancel();
                    Via.getManager().getProviders().get(MainHandProvider.class).setMainHand(wrapper.user(), hand);
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.SWING, new PacketHandlers(){

            @Override
            public void register() {
                this.read(Types.VAR_INT);
            }
        });
        protocol.cancelServerbound(ServerboundPackets1_9.ACCEPT_TELEPORTATION);
        protocol.cancelServerbound(ServerboundPackets1_9.MOVE_VEHICLE);
        protocol.cancelServerbound(ServerboundPackets1_9.PADDLE_BOAT);
        protocol.registerServerbound(ServerboundPackets1_9.CUSTOM_PAYLOAD, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.handler(wrapper -> {
                    Item item;
                    String name = wrapper.get(Types.STRING, 0);
                    if (name.equals("MC|BSign") && (item = wrapper.passthrough(Types.ITEM1_8)) != null) {
                        item.setIdentifier(387);
                        CompoundTag tag = item.tag();
                        ListTag<StringTag> pages = tag.getListTag("pages", StringTag.class);
                        if (pages == null) {
                            return;
                        }
                        for (int i = 0; i < pages.size(); ++i) {
                            StringTag pageTag = pages.get(i);
                            String value = pageTag.getValue();
                            pageTag.setValue(ComponentUtil.plainToJson(value).toString());
                        }
                    }
                    if (name.equals("MC|AutoCmd")) {
                        wrapper.set(Types.STRING, 0, "MC|AdvCdm");
                        wrapper.write(Types.BYTE, (byte)0);
                        wrapper.passthrough(Types.INT);
                        wrapper.passthrough(Types.INT);
                        wrapper.passthrough(Types.INT);
                        wrapper.passthrough(Types.STRING);
                        wrapper.passthrough(Types.BOOLEAN);
                        wrapper.clearInputBuffer();
                    }
                    if (name.equals("MC|AdvCmd")) {
                        wrapper.set(Types.STRING, 0, "MC|AdvCdm");
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.CLIENT_COMMAND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    EntityTracker1_9 tracker;
                    int action = wrapper.get(Types.VAR_INT, 0);
                    if (action == 2 && (tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class)).isBlocking()) {
                        if (!Via.getConfig().isShowShieldWhenSwordInHand()) {
                            tracker.setSecondHand(null);
                        }
                        tracker.setBlocking(false);
                    }
                });
            }
        });
        final PacketHandler onGroundHandler = wrapper -> {
            MovementTracker tracker = wrapper.user().get(MovementTracker.class);
            tracker.incrementIdlePacket();
            tracker.setGround(wrapper.get(Types.BOOLEAN, 0));
        };
        protocol.registerServerbound(ServerboundPackets1_9.MOVE_PLAYER_POS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BOOLEAN);
                this.handler(onGroundHandler);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.MOVE_PLAYER_POS_ROT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.BOOLEAN);
                this.handler(onGroundHandler);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.MOVE_PLAYER_ROT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.BOOLEAN);
                this.handler(onGroundHandler);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.MOVE_PLAYER_STATUS_ONLY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BOOLEAN);
                this.handler(onGroundHandler);
            }
        });
    }
}

