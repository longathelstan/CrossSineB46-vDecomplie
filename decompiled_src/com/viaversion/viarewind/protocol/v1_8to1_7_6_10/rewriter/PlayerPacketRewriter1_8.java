/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.api.minecraft.math.AABB;
import com.viaversion.viarewind.api.minecraft.math.Ray3d;
import com.viaversion.viarewind.api.minecraft.math.RayTracing;
import com.viaversion.viarewind.api.minecraft.math.Vector3d;
import com.viaversion.viarewind.api.type.RewindTypes;
import com.viaversion.viarewind.api.type.version.Types1_7_6_10;
import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet.ServerboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.Protocol1_8To1_7_6_10;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.data.VirtualHologramEntity;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.provider.TitleRenderProvider;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.EntityTracker1_8;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.InventoryTracker;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.PlayerSessionStorage;
import com.viaversion.viarewind.utils.ChatUtil;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_8;
import com.viaversion.viaversion.util.ComponentUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PlayerPacketRewriter1_8
extends RewriterBase<Protocol1_8To1_7_6_10> {
    public PlayerPacketRewriter1_8(Protocol1_8To1_7_6_10 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.COMPONENT);
                this.handler(wrapper -> {
                    byte position = wrapper.read(Types.BYTE);
                    if (position == 2) {
                        wrapper.cancel();
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_DEFAULT_SPAWN_POSITION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8, RewindTypes.INT_POSITION);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_HEALTH, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.FLOAT);
                this.map(Types.VAR_INT, Types.SHORT);
                this.map(Types.FLOAT);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    if (ViaRewind.getConfig().isReplaceAdventureMode() && wrapper.get(Types.UNSIGNED_BYTE, 1) == 2) {
                        wrapper.set(Types.UNSIGNED_BYTE, 1, (short)0);
                    }
                    EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                    tracker.setClientEntityGameMode(wrapper.get(Types.UNSIGNED_BYTE, 1).shortValue());
                    int dimension = wrapper.get(Types.INT, 0);
                    Object world = wrapper.user().getClientWorld(Protocol1_8To1_7_6_10.class);
                    if (((ClientWorld)world).setEnvironment(dimension)) {
                        tracker.clearEntities();
                        tracker.addPlayer(tracker.clientEntityId(), wrapper.user().getProtocolInfo().getUuid());
                    }
                    wrapper.send(Protocol1_8To1_7_6_10.class);
                    wrapper.cancel();
                    if (tracker.getEntityData().isEmpty()) {
                        return;
                    }
                    PacketWrapper setEntityData = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_ENTITY_DATA, wrapper.user());
                    setEntityData.write(Types.VAR_INT, tracker.clientEntityId());
                    setEntityData.write(Types1_7_6_10.ENTITY_DATA_LIST, tracker.getEntityData());
                    setEntityData.send(Protocol1_8To1_7_6_10.class);
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.PLAYER_POSITION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.handler(wrapper -> {
                    double x = wrapper.get(Types.DOUBLE, 0);
                    double y = wrapper.get(Types.DOUBLE, 1);
                    double z = wrapper.get(Types.DOUBLE, 2);
                    float yaw = wrapper.get(Types.FLOAT, 0).floatValue();
                    float pitch = wrapper.get(Types.FLOAT, 1).floatValue();
                    PlayerSessionStorage playerSession = wrapper.user().get(PlayerSessionStorage.class);
                    byte flags = wrapper.read(Types.BYTE);
                    if ((flags & 1) == 1) {
                        wrapper.set(Types.DOUBLE, 0, x + playerSession.getPosX());
                    }
                    if ((flags & 2) == 2) {
                        y += playerSession.getPosY();
                    }
                    playerSession.receivedPosY = y;
                    wrapper.set(Types.DOUBLE, 1, y + (double)1.62f);
                    if ((flags & 4) == 4) {
                        wrapper.set(Types.DOUBLE, 2, z + playerSession.getPosZ());
                    }
                    if ((flags & 8) == 8) {
                        wrapper.set(Types.FLOAT, 0, Float.valueOf(yaw + playerSession.yaw));
                    }
                    if ((flags & 0x10) == 16) {
                        wrapper.set(Types.FLOAT, 1, Float.valueOf(pitch + playerSession.pitch));
                    }
                    wrapper.write(Types.BOOLEAN, playerSession.onGround);
                    EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                    if (!Objects.equals(tracker.spectatingClientEntityId, tracker.clientEntityIdOrNull())) {
                        wrapper.cancel();
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_EXPERIENCE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.FLOAT);
                this.map(Types.VAR_INT, Types.SHORT);
                this.map(Types.VAR_INT, Types.SHORT);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.GAME_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.FLOAT);
                this.handler(wrapper -> {
                    if (wrapper.get(Types.UNSIGNED_BYTE, 0) != 3) {
                        return;
                    }
                    int gameMode = wrapper.get(Types.FLOAT, 0).intValue();
                    EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                    if (gameMode == 3 || tracker.isSpectator()) {
                        UUID myId = wrapper.user().getProtocolInfo().getUuid();
                        Item[] equipment = new Item[4];
                        if (gameMode == 3) {
                            GameProfileStorage.GameProfile profile = wrapper.user().get(GameProfileStorage.class).get(myId);
                            equipment[3] = profile == null ? null : profile.getSkull();
                        } else {
                            for (int i = 0; i < equipment.length; ++i) {
                                equipment[i] = wrapper.user().get(PlayerSessionStorage.class).getPlayerEquipment(myId, i);
                            }
                        }
                        for (int i = 0; i < equipment.length; ++i) {
                            PacketWrapper setSlot = PacketWrapper.create(ClientboundPackets1_7_2_5.CONTAINER_SET_SLOT, wrapper.user());
                            setSlot.write(Types.BYTE, (byte)0);
                            setSlot.write(Types.SHORT, (short)(8 - i));
                            setSlot.write(RewindTypes.COMPRESSED_NBT_ITEM, equipment[i]);
                            setSlot.scheduleSend(Protocol1_8To1_7_6_10.class);
                        }
                    }
                    if (gameMode == 2 && ViaRewind.getConfig().isReplaceAdventureMode()) {
                        gameMode = 0;
                        wrapper.set(Types.FLOAT, 0, Float.valueOf(0.0f));
                    }
                    tracker.setClientEntityGameMode(gameMode);
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.OPEN_SIGN_EDITOR, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8, RewindTypes.INT_POSITION);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.PLAYER_INFO, wrapper -> {
            wrapper.cancel();
            GameProfileStorage gameProfileStorage = wrapper.user().get(GameProfileStorage.class);
            int action = wrapper.read(Types.VAR_INT);
            int count = wrapper.read(Types.VAR_INT);
            for (int i = 0; i < count; ++i) {
                GameProfileStorage.GameProfile gameProfile;
                GameProfileStorage.GameProfile gameProfile2;
                UUID uuid = wrapper.read(Types.UUID);
                if (action == 0) {
                    int ping;
                    String name = wrapper.read(Types.STRING);
                    gameProfile2 = gameProfileStorage.get(uuid);
                    if (gameProfile2 == null) {
                        gameProfile2 = gameProfileStorage.put(uuid, name);
                    }
                    int propertyCount = wrapper.read(Types.VAR_INT);
                    while (propertyCount-- > 0) {
                        String propertyName = wrapper.read(Types.STRING);
                        String propertyValue = wrapper.read(Types.STRING);
                        String propertySignature = wrapper.read(Types.OPTIONAL_STRING);
                        gameProfile2.properties.add(new GameProfileStorage.Property(propertyName, propertyValue, propertySignature));
                    }
                    int gamemode = wrapper.read(Types.VAR_INT);
                    gameProfile2.ping = ping = wrapper.read(Types.VAR_INT).intValue();
                    gameProfile2.gamemode = gamemode;
                    JsonElement displayName = wrapper.read(Types.OPTIONAL_COMPONENT);
                    if (displayName != null) {
                        gameProfile2.setDisplayName(ChatUtil.jsonToLegacy(displayName));
                    }
                    PacketWrapper playerInfo = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, wrapper.user());
                    playerInfo.write(Types.STRING, gameProfile2.getDisplayName());
                    playerInfo.write(Types.BOOLEAN, true);
                    playerInfo.write(Types.SHORT, (short)ping);
                    playerInfo.scheduleSend(Protocol1_8To1_7_6_10.class);
                    continue;
                }
                if (action == 1) {
                    int gamemode = wrapper.read(Types.VAR_INT);
                    gameProfile2 = gameProfileStorage.get(uuid);
                    if (gameProfile2 == null || gameProfile2.gamemode == gamemode) continue;
                    if (gamemode == 3 || gameProfile2.gamemode == 3) {
                        boolean isOwnPlayer;
                        EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                        int entityId = tracker.getPlayerEntityId(uuid);
                        boolean bl = isOwnPlayer = entityId == tracker.clientEntityId();
                        if (entityId != -1) {
                            Item[] equipment = new Item[isOwnPlayer ? 4 : 5];
                            if (gamemode == 3) {
                                equipment[isOwnPlayer ? 3 : 4] = gameProfile2.getSkull();
                            } else {
                                for (int j = 0; j < equipment.length; ++j) {
                                    equipment[j] = wrapper.user().get(PlayerSessionStorage.class).getPlayerEquipment(uuid, j);
                                }
                            }
                            for (short slot = 0; slot < equipment.length; slot = (short)(slot + 1)) {
                                PacketWrapper setEquipment = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_EQUIPPED_ITEM, wrapper.user());
                                setEquipment.write(Types.INT, entityId);
                                setEquipment.write(Types.SHORT, slot);
                                setEquipment.write(RewindTypes.COMPRESSED_NBT_ITEM, equipment[slot]);
                                setEquipment.scheduleSend(Protocol1_8To1_7_6_10.class);
                            }
                        }
                    }
                    gameProfile2.gamemode = gamemode;
                    continue;
                }
                if (action == 2) {
                    int ping = wrapper.read(Types.VAR_INT);
                    gameProfile2 = gameProfileStorage.get(uuid);
                    if (gameProfile2 == null) continue;
                    PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, wrapper.user());
                    packet.write(Types.STRING, gameProfile2.getDisplayName());
                    packet.write(Types.BOOLEAN, false);
                    packet.write(Types.SHORT, (short)gameProfile2.ping);
                    packet.scheduleSend(Protocol1_8To1_7_6_10.class);
                    gameProfile2.ping = ping;
                    packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, wrapper.user());
                    packet.write(Types.STRING, gameProfile2.getDisplayName());
                    packet.write(Types.BOOLEAN, true);
                    packet.write(Types.SHORT, (short)ping);
                    packet.scheduleSend(Protocol1_8To1_7_6_10.class);
                    continue;
                }
                if (action == 3) {
                    JsonElement displayNameComponent = wrapper.read(Types.OPTIONAL_COMPONENT);
                    String displayName = displayNameComponent != null ? ChatUtil.jsonToLegacy(displayNameComponent) : null;
                    GameProfileStorage.GameProfile gameProfile3 = gameProfileStorage.get(uuid);
                    if (gameProfile3 == null || gameProfile3.displayName == null && displayName == null) continue;
                    PacketWrapper playerInfo = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, wrapper.user());
                    playerInfo.write(Types.STRING, gameProfile3.getDisplayName());
                    playerInfo.write(Types.BOOLEAN, false);
                    playerInfo.write(Types.SHORT, (short)gameProfile3.ping);
                    playerInfo.scheduleSend(Protocol1_8To1_7_6_10.class);
                    if (gameProfile3.displayName == null && displayName != null || gameProfile3.displayName != null && displayName == null || !gameProfile3.displayName.equals(displayName)) {
                        gameProfile3.setDisplayName(displayName);
                    }
                    playerInfo = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, wrapper.user());
                    playerInfo.write(Types.STRING, gameProfile3.getDisplayName());
                    playerInfo.write(Types.BOOLEAN, true);
                    playerInfo.write(Types.SHORT, (short)gameProfile3.ping);
                    playerInfo.scheduleSend(Protocol1_8To1_7_6_10.class);
                    continue;
                }
                if (action != 4 || (gameProfile = gameProfileStorage.remove(uuid)) == null) continue;
                PacketWrapper playerInfo = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, wrapper.user());
                playerInfo.write(Types.STRING, gameProfile.getDisplayName());
                playerInfo.write(Types.BOOLEAN, false);
                playerInfo.write(Types.SHORT, (short)gameProfile.ping);
                playerInfo.scheduleSend(Protocol1_8To1_7_6_10.class);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.PLAYER_ABILITIES, wrapper -> {
            byte flags = wrapper.passthrough(Types.BYTE);
            float flySpeed = wrapper.passthrough(Types.FLOAT).floatValue();
            float walkSpeed = wrapper.passthrough(Types.FLOAT).floatValue();
            PlayerSessionStorage abilities = wrapper.user().get(PlayerSessionStorage.class);
            abilities.invincible = (flags & 8) == 8;
            abilities.allowFly = (flags & 4) == 4;
            abilities.flying = (flags & 2) == 2;
            abilities.creative = (flags & 1) == 1;
            abilities.flySpeed = flySpeed;
            abilities.walkSpeed = walkSpeed;
            if (abilities.sprinting && abilities.flying) {
                wrapper.set(Types.FLOAT, 0, Float.valueOf(abilities.flySpeed * 2.0f));
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.CUSTOM_PAYLOAD, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.handlerSoftFail(wrapper -> {
                    wrapper.cancel();
                    String channel = wrapper.get(Types.STRING, 0);
                    if (channel.equals("MC|TrList")) {
                        wrapper.passthrough(Types.INT);
                        int size = wrapper.passthrough(Types.UNSIGNED_BYTE).shortValue();
                        for (int i = 0; i < size; ++i) {
                            Item item = ((Protocol1_8To1_7_6_10)PlayerPacketRewriter1_8.this.protocol).getItemRewriter().handleItemToClient(wrapper.user(), wrapper.read(Types.ITEM1_8));
                            wrapper.write(RewindTypes.COMPRESSED_NBT_ITEM, item);
                            item = ((Protocol1_8To1_7_6_10)PlayerPacketRewriter1_8.this.protocol).getItemRewriter().handleItemToClient(wrapper.user(), wrapper.read(Types.ITEM1_8));
                            wrapper.write(RewindTypes.COMPRESSED_NBT_ITEM, item);
                            boolean has3Items = wrapper.passthrough(Types.BOOLEAN);
                            if (has3Items) {
                                item = ((Protocol1_8To1_7_6_10)PlayerPacketRewriter1_8.this.protocol).getItemRewriter().handleItemToClient(wrapper.user(), wrapper.read(Types.ITEM1_8));
                                wrapper.write(RewindTypes.COMPRESSED_NBT_ITEM, item);
                            }
                            wrapper.passthrough(Types.BOOLEAN);
                            wrapper.read(Types.INT);
                            wrapper.read(Types.INT);
                        }
                    } else if (channel.equals("MC|Brand")) {
                        wrapper.write(Types.REMAINING_BYTES, wrapper.read(Types.STRING).getBytes(StandardCharsets.UTF_8));
                    }
                    wrapper.setPacketType(null);
                    ByteBuf data = Unpooled.buffer();
                    wrapper.writeToBuffer(data);
                    PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_7_2_5.CUSTOM_PAYLOAD, data, wrapper.user());
                    packet.passthrough(Types.STRING);
                    if (data.readableBytes() <= Short.MAX_VALUE) {
                        packet.write(Types.SHORT, (short)data.readableBytes());
                        packet.send(Protocol1_8To1_7_6_10.class);
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_CAMERA, null, wrapper -> {
            wrapper.cancel();
            int entityId = wrapper.read(Types.VAR_INT);
            EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
            if (tracker.spectatingClientEntityId != entityId) {
                tracker.setSpectating(entityId);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_TITLES, null, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    wrapper.cancel();
                    int action = wrapper.read(Types.VAR_INT);
                    TitleRenderProvider provider = Via.getManager().getProviders().get(TitleRenderProvider.class);
                    if (provider == null) {
                        return;
                    }
                    UUID uuid = wrapper.user().getProtocolInfo().getUuid();
                    switch (action) {
                        case 0: {
                            provider.setTitle(uuid, wrapper.read(Types.STRING));
                            break;
                        }
                        case 1: {
                            provider.setSubTitle(uuid, wrapper.read(Types.STRING));
                            break;
                        }
                        case 2: {
                            provider.setTimings(uuid, wrapper.read(Types.INT), wrapper.read(Types.INT), wrapper.read(Types.INT));
                            break;
                        }
                        case 3: {
                            provider.clear(uuid);
                            break;
                        }
                        case 4: {
                            provider.reset(uuid);
                        }
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).cancelClientbound(ClientboundPackets1_8.TAB_LIST);
        ((Protocol1_8To1_7_6_10)this.protocol).cancelClientbound(ClientboundPackets1_8.PLAYER_COMBAT);
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.RESOURCE_PACK, ClientboundPackets1_7_2_5.CUSTOM_PAYLOAD, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.create(Types.STRING, "MC|RPack");
                this.handler(wrapper -> {
                    ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
                    try {
                        Types.STRING.write(buf, wrapper.read(Types.STRING));
                        wrapper.write(Types.SHORT_BYTE_ARRAY, (byte[])Types.REMAINING_BYTES.read(buf));
                    }
                    finally {
                        buf.release();
                    }
                });
                this.read(Types.STRING);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.CHAT, wrapper -> {
            String message = wrapper.passthrough(Types.STRING);
            EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
            if (tracker.isSpectator() && message.toLowerCase().startsWith("/stp ")) {
                String username = message.split(" ")[1];
                GameProfileStorage storage = wrapper.user().get(GameProfileStorage.class);
                GameProfileStorage.GameProfile profile = storage.get(username, true);
                if (profile != null && profile.uuid != null) {
                    wrapper.cancel();
                    PacketWrapper teleport = PacketWrapper.create(ClientboundPackets1_7_2_5.TELEPORT_ENTITY, wrapper.user());
                    teleport.write(Types.UUID, profile.uuid);
                    teleport.send(Protocol1_8To1_7_6_10.class);
                }
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.INTERACT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT, Types.VAR_INT);
                this.map(Types.BYTE, Types.VAR_INT);
                this.handler(wrapper -> {
                    int mode2 = wrapper.get(Types.VAR_INT, 1);
                    if (mode2 != 0) {
                        return;
                    }
                    int entityId = wrapper.get(Types.VAR_INT, 0);
                    EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                    PlayerSessionStorage position = wrapper.user().get(PlayerSessionStorage.class);
                    if (!tracker.getHolograms().containsKey(entityId)) {
                        return;
                    }
                    AABB boundingBox = ((VirtualHologramEntity)tracker.getHolograms().get(entityId)).getBoundingBox();
                    Vector3d pos = new Vector3d(position.getPosX(), position.getPosY() + 1.8, position.getPosZ());
                    double yaw = Math.toRadians(position.yaw);
                    double pitch = Math.toRadians(position.pitch);
                    Vector3d dir = new Vector3d(-Math.cos(pitch) * Math.sin(yaw), -Math.sin(pitch), Math.cos(pitch) * Math.cos(yaw));
                    Ray3d ray = new Ray3d(pos, dir);
                    Vector3d intersection = RayTracing.trace(ray, boundingBox, 5.0);
                    if (intersection == null) {
                        return;
                    }
                    intersection.substract(boundingBox.getMin());
                    wrapper.set(Types.VAR_INT, 1, 2);
                    wrapper.write(Types.FLOAT, Float.valueOf((float)intersection.getX()));
                    wrapper.write(Types.FLOAT, Float.valueOf((float)intersection.getY()));
                    wrapper.write(Types.FLOAT, Float.valueOf((float)intersection.getZ()));
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.MOVE_PLAYER_STATUS_ONLY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    wrapper.user().get(PlayerSessionStorage.class).onGround = wrapper.get(Types.BOOLEAN, 0);
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.MOVE_PLAYER_POS, wrapper -> {
            double x = wrapper.passthrough(Types.DOUBLE);
            double y = wrapper.passthrough(Types.DOUBLE);
            wrapper.read(Types.DOUBLE);
            double z = wrapper.passthrough(Types.DOUBLE);
            PlayerSessionStorage storage = wrapper.user().get(PlayerSessionStorage.class);
            storage.onGround = wrapper.passthrough(Types.BOOLEAN);
            storage.setPos(x, y, z);
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.MOVE_PLAYER_ROT, wrapper -> {
            PlayerSessionStorage storage = wrapper.user().get(PlayerSessionStorage.class);
            storage.yaw = wrapper.passthrough(Types.FLOAT).floatValue();
            storage.pitch = wrapper.passthrough(Types.FLOAT).floatValue();
            storage.onGround = wrapper.passthrough(Types.BOOLEAN);
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.MOVE_PLAYER_POS_ROT, wrapper -> {
            double x = wrapper.passthrough(Types.DOUBLE);
            double y = wrapper.passthrough(Types.DOUBLE);
            wrapper.read(Types.DOUBLE);
            double z = wrapper.passthrough(Types.DOUBLE);
            PlayerSessionStorage storage = wrapper.user().get(PlayerSessionStorage.class);
            storage.setPos(x, y, z);
            storage.yaw = wrapper.passthrough(Types.FLOAT).floatValue();
            storage.pitch = wrapper.passthrough(Types.FLOAT).floatValue();
            storage.onGround = wrapper.passthrough(Types.BOOLEAN);
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.PLAYER_ACTION, new PacketHandlers(){

            @Override
            protected void register() {
                this.map(Types.VAR_INT);
                this.map(RewindTypes.U_BYTE_POSITION, Types.BLOCK_POSITION1_8);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.USE_ITEM_ON, new PacketHandlers(){

            @Override
            protected void register() {
                this.map(RewindTypes.U_BYTE_POSITION, Types.BLOCK_POSITION1_8);
                this.map(Types.BYTE);
                this.map(RewindTypes.COMPRESSED_NBT_ITEM, Types.ITEM1_8);
                this.handler(wrapper -> ((Protocol1_8To1_7_6_10)PlayerPacketRewriter1_8.this.protocol).getItemRewriter().handleItemToServer(wrapper.user(), wrapper.get(Types.ITEM1_8, 0)));
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.SWING, wrapper -> {
            int entityId = wrapper.read(Types.INT);
            int animation = wrapper.read(Types.BYTE).byteValue();
            if (animation == 1) {
                return;
            }
            wrapper.cancel();
            switch (animation) {
                case 104: {
                    animation = 0;
                    break;
                }
                case 105: {
                    animation = 1;
                    break;
                }
                case 3: {
                    animation = 2;
                    break;
                }
                default: {
                    return;
                }
            }
            PacketWrapper animate = PacketWrapper.create(ClientboundPackets1_7_2_5.ANIMATE, wrapper.user());
            animate.write(Types.VAR_INT, entityId);
            animate.write(Types.VAR_INT, animation);
            animate.write(Types.VAR_INT, 0);
            animate.send(Protocol1_8To1_7_6_10.class);
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.PLAYER_COMMAND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT, Types.VAR_INT);
                this.handler(wrapper -> wrapper.write(Types.VAR_INT, wrapper.read(Types.BYTE) - 1));
                this.map(Types.INT, Types.VAR_INT);
                this.handler(wrapper -> {
                    int action = wrapper.get(Types.VAR_INT, 1);
                    if (action == 3 || action == 4) {
                        PlayerSessionStorage storage = wrapper.user().get(PlayerSessionStorage.class);
                        storage.sprinting = action == 3;
                        PacketWrapper abilities = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_ABILITIES, wrapper.user());
                        abilities.write(Types.BYTE, storage.combineAbilities());
                        abilities.write(Types.FLOAT, Float.valueOf(storage.sprinting ? storage.flySpeed * 2.0f : storage.flySpeed));
                        abilities.write(Types.FLOAT, Float.valueOf(storage.walkSpeed));
                        abilities.scheduleSend(Protocol1_8To1_7_6_10.class);
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.PLAYER_INPUT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.handler(wrapper -> {
                    boolean jump = wrapper.read(Types.BOOLEAN);
                    boolean unmount = wrapper.read(Types.BOOLEAN);
                    short flags = 0;
                    if (jump) {
                        flags = (short)(flags + 1);
                    }
                    if (unmount) {
                        flags = (short)(flags + 2);
                    }
                    wrapper.write(Types.UNSIGNED_BYTE, flags);
                    if (!unmount) {
                        return;
                    }
                    EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                    if (tracker.spectatingClientEntityId.intValue() != tracker.clientEntityId()) {
                        PacketWrapper sneakPacket = PacketWrapper.create(ServerboundPackets1_8.PLAYER_COMMAND, wrapper.user());
                        sneakPacket.write(Types.VAR_INT, tracker.clientEntityId());
                        sneakPacket.write(Types.VAR_INT, 0);
                        sneakPacket.write(Types.VAR_INT, 0);
                        sneakPacket.scheduleSendToServer(Protocol1_8To1_7_6_10.class);
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.SIGN_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(RewindTypes.SHORT_POSITION, Types.BLOCK_POSITION1_8);
                this.handler(wrapper -> {
                    for (int i = 0; i < 4; ++i) {
                        String line = wrapper.read(Types.STRING);
                        wrapper.write(Types.COMPONENT, ComponentUtil.legacyToJson(line));
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.PLAYER_ABILITIES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.handler(wrapper -> {
                    PlayerSessionStorage storage = wrapper.user().get(PlayerSessionStorage.class);
                    if (storage.allowFly) {
                        byte flags = wrapper.get(Types.BYTE, 0);
                        storage.flying = (flags & 2) == 2;
                    }
                    wrapper.set(Types.FLOAT, 0, Float.valueOf(storage.flySpeed));
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.COMMAND_SUGGESTION, wrapper -> {
            String message = wrapper.passthrough(Types.STRING);
            wrapper.write(Types.OPTIONAL_POSITION1_8, null);
            if (message.toLowerCase().startsWith("/stp ")) {
                wrapper.cancel();
                String[] args2 = message.split(" ");
                if (args2.length <= 2) {
                    String prefix = args2.length == 1 ? "" : args2[1];
                    GameProfileStorage storage = wrapper.user().get(GameProfileStorage.class);
                    List<GameProfileStorage.GameProfile> profiles = storage.getAllWithPrefix(prefix, true);
                    PacketWrapper commandSuggestions = PacketWrapper.create(ClientboundPackets1_7_2_5.COMMAND_SUGGESTIONS, wrapper.user());
                    commandSuggestions.write(Types.VAR_INT, profiles.size());
                    for (GameProfileStorage.GameProfile profile : profiles) {
                        commandSuggestions.write(Types.STRING, profile.name);
                    }
                    commandSuggestions.scheduleSend(Protocol1_8To1_7_6_10.class);
                }
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.CLIENT_INFORMATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BOOLEAN);
                this.read(Types.BYTE);
                this.handler(wrapper -> {
                    boolean showCape = wrapper.read(Types.BOOLEAN);
                    wrapper.write(Types.UNSIGNED_BYTE, (short)(showCape ? 127 : 126));
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.CUSTOM_PAYLOAD, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.read(Types.SHORT);
                this.handler(wrapper -> {
                    String channel;
                    switch (channel = wrapper.get(Types.STRING, 0)) {
                        case "MC|TrSel": {
                            wrapper.passthrough(Types.INT);
                            wrapper.read(Types.REMAINING_BYTES);
                            break;
                        }
                        case "MC|ItemName": {
                            byte[] data = wrapper.read(Types.REMAINING_BYTES);
                            wrapper.write(Types.STRING, new String(data, StandardCharsets.UTF_8));
                            InventoryTracker tracker = wrapper.user().get(InventoryTracker.class);
                            PacketWrapper updateCost = PacketWrapper.create(ClientboundPackets1_7_2_5.CONTAINER_SET_DATA, wrapper.user());
                            updateCost.write(Types.UNSIGNED_BYTE, tracker.anvilId);
                            updateCost.write(Types.SHORT, (short)0);
                            updateCost.write(Types.SHORT, tracker.levelCost);
                            updateCost.send(Protocol1_8To1_7_6_10.class);
                            break;
                        }
                        case "MC|BEdit": 
                        case "MC|BSign": {
                            Item book = wrapper.read(RewindTypes.COMPRESSED_NBT_ITEM);
                            CompoundTag tag = book.tag();
                            if (tag != null && tag.contains("pages")) {
                                ListTag<StringTag> pages = tag.getListTag("pages", StringTag.class);
                                for (int i = 0; i < pages.size(); ++i) {
                                    StringTag page = pages.get(i);
                                    String value = page.getValue();
                                    value = ComponentUtil.legacyToJsonString(value);
                                    page.setValue(value);
                                }
                            }
                            wrapper.write(Types.ITEM1_8, book);
                            break;
                        }
                        case "MC|Brand": {
                            wrapper.write(Types.STRING, new String(wrapper.read(Types.REMAINING_BYTES), StandardCharsets.UTF_8));
                        }
                    }
                });
            }
        });
    }
}

