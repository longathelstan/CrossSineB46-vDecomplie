/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentSerializer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.api.util.PacketUtil;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.packet.ClientboundPackets1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.packet.ServerboundPackets1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.rewriter.EntityDataRewriter;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.rewriter.ItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.rewriter.SoundRewriter;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.storage.AttachTracker;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.storage.EntityTracker;
import net.raphimc.vialegacy.protocol.release.r1_6_1tor1_6_2.packet.ClientboundPackets1_6_1;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.packet.ServerboundPackets1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.data.EntityDataIndex1_7_6;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolr1_5_2Tor1_6_1
extends StatelessProtocol<ClientboundPackets1_5_2, ClientboundPackets1_6_1, ServerboundPackets1_5_2, ServerboundPackets1_6_4> {
    final ItemRewriter itemRewriter = new ItemRewriter(this);

    public Protocolr1_5_2Tor1_6_1() {
        super(ClientboundPackets1_5_2.class, ClientboundPackets1_6_1.class, ServerboundPackets1_5_2.class, ServerboundPackets1_6_4.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.registerClientbound(ClientboundPackets1_5_2.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_6_4.STRING);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.INT, 0);
                    EntityTracker tracker = wrapper.user().get(EntityTracker.class);
                    tracker.getTrackedEntities().put(entityId, EntityTypes1_8.EntityType.PLAYER);
                    tracker.setPlayerID(entityId);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_5_2.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING, Types1_6_4.STRING, msg -> TextComponentSerializer.V1_6.serialize(new StringComponent((String)msg)));
            }
        });
        this.registerClientbound(ClientboundPackets1_5_2.SET_HEALTH, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.SHORT, Types.FLOAT);
                this.map(Types.SHORT);
                this.map(Types.FLOAT);
            }
        });
        this.registerClientbound(ClientboundPackets1_5_2.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_6_4.STRING);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.UNSIGNED_SHORT);
                this.map(Types1_6_4.ENTITY_DATA_LIST);
                this.handler(wrapper -> EntityDataRewriter.transform(EntityTypes1_8.EntityType.PLAYER, wrapper.get(Types1_6_4.ENTITY_DATA_LIST, 0)));
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.INT, 0);
                    wrapper.user().get(EntityTracker.class).getTrackedEntities().put(entityId, EntityTypes1_8.EntityType.PLAYER);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_5_2.TAKE_ITEM_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.INT);
                this.handler(wrapper -> wrapper.user().get(EntityTracker.class).getTrackedEntities().remove(wrapper.get(Types.INT, 0)));
            }
        });
        this.registerClientbound(ClientboundPackets1_5_2.ADD_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int entityID = wrapper.get(Types.INT, 0);
                    byte typeID = wrapper.get(Types.BYTE, 0);
                    wrapper.user().get(EntityTracker.class).getTrackedEntities().put(entityID, EntityTypes1_8.getTypeFromId(typeID, true));
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_5_2.ADD_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types1_6_4.ENTITY_DATA_LIST);
                this.handler(wrapper -> {
                    int entityID = wrapper.get(Types.INT, 0);
                    short typeID = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    EntityTypes1_8.EntityType entityType = EntityTypes1_8.getTypeFromId(typeID, false);
                    List<EntityData> entityDataList = wrapper.get(Types1_6_4.ENTITY_DATA_LIST, 0);
                    wrapper.user().get(EntityTracker.class).getTrackedEntities().put(entityID, entityType);
                    EntityDataRewriter.transform(entityType, entityDataList);
                    if (entityType.isOrHasParent(EntityTypes1_8.EntityType.WOLF)) {
                        Protocolr1_5_2Tor1_6_1.this.handleWolfEntityData(entityID, entityDataList, wrapper);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_5_2.ADD_PAINTING, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_6_4.STRING);
                this.map(Types1_7_6.BLOCK_POSITION_INT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int entityID = wrapper.get(Types.INT, 0);
                    wrapper.user().get(EntityTracker.class).getTrackedEntities().put(entityID, EntityTypes1_8.EntityType.PAINTING);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_5_2.ADD_EXPERIENCE_ORB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.SHORT);
                this.handler(wrapper -> {
                    int entityID = wrapper.get(Types.INT, 0);
                    wrapper.user().get(EntityTracker.class).getTrackedEntities().put(entityID, EntityTypes1_8.EntityType.EXPERIENCE_ORB);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_5_2.REMOVE_ENTITIES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.INT_ARRAY);
                this.handler(wrapper -> {
                    EntityTracker tracker = wrapper.user().get(EntityTracker.class);
                    for (int entityId : wrapper.get(Types1_7_6.INT_ARRAY, 0)) {
                        tracker.getTrackedEntities().remove(entityId);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_5_2.SET_ENTITY_LINK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    AttachTracker attachTracker = wrapper.user().get(AttachTracker.class);
                    EntityTracker entityTracker = wrapper.user().get(EntityTracker.class);
                    int ridingId = wrapper.get(Types.INT, 0);
                    int vehicleId = wrapper.get(Types.INT, 1);
                    if (entityTracker.getPlayerID() == ridingId) {
                        attachTracker.vehicleEntityId = vehicleId;
                    }
                });
                this.create(Types.UNSIGNED_BYTE, (short)0);
            }
        });
        this.registerClientbound(ClientboundPackets1_5_2.SET_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_6_4.ENTITY_DATA_LIST);
                this.handler(wrapper -> {
                    EntityTracker tracker = wrapper.user().get(EntityTracker.class);
                    List<EntityData> entityDataList = wrapper.get(Types1_6_4.ENTITY_DATA_LIST, 0);
                    int entityID = wrapper.get(Types.INT, 0);
                    EntityTypes1_8.EntityType entityType = tracker.getTrackedEntities().get(entityID);
                    if (tracker.getTrackedEntities().containsKey(entityID)) {
                        EntityDataRewriter.transform(entityType, entityDataList);
                        if (entityDataList.isEmpty()) {
                            wrapper.cancel();
                        }
                        if (entityType.isOrHasParent(EntityTypes1_8.EntityType.WOLF)) {
                            Protocolr1_5_2Tor1_6_1.this.handleWolfEntityData(entityID, entityDataList, wrapper);
                        }
                    } else {
                        wrapper.cancel();
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_5_2.CUSTOM_SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    String oldSound = wrapper.read(Types1_6_4.STRING);
                    String newSound = SoundRewriter.map(oldSound);
                    if (oldSound.isEmpty()) {
                        newSound = "";
                    }
                    if (newSound == null) {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                            String string = oldSound;
                            ViaLegacy.getPlatform().getLogger().warning("Unable to map 1.5.2 sound '" + string + "'");
                        }
                        newSound = "";
                    }
                    if (newSound.isEmpty()) {
                        wrapper.cancel();
                        return;
                    }
                    wrapper.write(Types1_6_4.STRING, newSound);
                });
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.FLOAT);
                this.map(Types.UNSIGNED_BYTE);
            }
        });
        this.registerClientbound(ClientboundPackets1_5_2.AWARD_STATS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE, Types.INT);
            }
        });
        this.registerClientbound(ClientboundPackets1_5_2.PLAYER_ABILITIES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    float flySpeed = (float)wrapper.read(Types.BYTE).byteValue() / 255.0f;
                    float walkSpeed = (float)wrapper.read(Types.BYTE).byteValue() / 255.0f;
                    wrapper.write(Types.FLOAT, Float.valueOf(flySpeed));
                    wrapper.write(Types.FLOAT, Float.valueOf(walkSpeed));
                    PacketWrapper entityProperties = PacketWrapper.create(ClientboundPackets1_6_1.UPDATE_ATTRIBUTES, wrapper.user());
                    entityProperties.write(Types.INT, wrapper.user().get(EntityTracker.class).getPlayerID());
                    entityProperties.write(Types.INT, 1);
                    entityProperties.write(Types1_6_4.STRING, "generic.movementSpeed");
                    entityProperties.write(Types.DOUBLE, Double.valueOf(walkSpeed));
                    wrapper.send(Protocolr1_5_2Tor1_6_1.class);
                    entityProperties.send(Protocolr1_5_2Tor1_6_1.class);
                    wrapper.cancel();
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_5_2.CUSTOM_PAYLOAD, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    String channel = wrapper.read(Types1_6_4.STRING);
                    short length = wrapper.read(Types.SHORT);
                    try {
                        if (channel.equals("MC|TPack")) {
                            channel = "MC|RPack";
                            String[] data = new String(wrapper.read(Types.REMAINING_BYTES), StandardCharsets.UTF_8).split("\u0000");
                            String url = data[0];
                            String resolution = data[1];
                            if (!resolution.equals("16")) {
                                wrapper.cancel();
                                return;
                            }
                            wrapper.write(Types.REMAINING_BYTES, url.getBytes(StandardCharsets.UTF_8));
                            length = (short)PacketUtil.calculateLength(wrapper);
                        }
                    }
                    catch (Exception e) {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                            Via.getPlatform().getLogger().log(Level.WARNING, "Failed to handle packet", e);
                        }
                        wrapper.cancel();
                        return;
                    }
                    wrapper.resetReader();
                    wrapper.write(Types1_6_4.STRING, channel);
                    wrapper.write(Types.SHORT, length);
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_6_4.SERVER_PING, wrapper -> {
            wrapper.clearPacket();
            wrapper.write(Types.BYTE, (byte)1);
        });
        this.registerServerbound(ServerboundPackets1_6_4.PLAYER_COMMAND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.read(Types.INT);
                this.handler(wrapper -> {
                    if (wrapper.get(Types.BYTE, 0) > 5) {
                        wrapper.cancel();
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_6_4.PLAYER_INPUT, ServerboundPackets1_5_2.INTERACT, (PacketWrapper wrapper) -> {
            AttachTracker attachTracker = wrapper.user().get(AttachTracker.class);
            EntityTracker entityTracker = wrapper.user().get(EntityTracker.class);
            wrapper.read(Types.FLOAT);
            wrapper.read(Types.FLOAT);
            wrapper.read(Types.BOOLEAN);
            boolean sneaking = wrapper.read(Types.BOOLEAN);
            if (attachTracker.lastSneakState != sneaking) {
                attachTracker.lastSneakState = sneaking;
                if (sneaking) {
                    wrapper.write(Types.INT, entityTracker.getPlayerID());
                    wrapper.write(Types.INT, attachTracker.vehicleEntityId);
                    wrapper.write(Types.BYTE, (byte)0);
                    return;
                }
            }
            wrapper.cancel();
        });
        this.registerServerbound(ServerboundPackets1_6_4.PLAYER_ABILITIES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types.FLOAT, Types.BYTE, f -> (byte)(f.floatValue() * 255.0f));
                this.map(Types.FLOAT, Types.BYTE, f -> (byte)(f.floatValue() * 255.0f));
            }
        });
    }

    void handleWolfEntityData(int entityId, List<EntityData> entityDataList, PacketWrapper wrapper) {
        for (EntityData entityData : entityDataList) {
            EntityDataIndex1_7_6 index2 = EntityDataIndex1_7_6.searchIndex(EntityTypes1_8.EntityType.WOLF, entityData.id());
            if (index2 != EntityDataIndex1_7_6.TAMEABLE_FLAGS) continue;
            if (((Byte)entityData.value() & 4) == 0) break;
            PacketWrapper attributes = PacketWrapper.create(ClientboundPackets1_6_1.UPDATE_ATTRIBUTES, wrapper.user());
            attributes.write(Types.INT, entityId);
            attributes.write(Types.INT, 1);
            attributes.write(Types1_6_4.STRING, "generic.maxHealth");
            attributes.write(Types.DOUBLE, 20.0);
            wrapper.send(Protocolr1_5_2Tor1_6_1.class);
            attributes.send(Protocolr1_5_2Tor1_6_1.class);
            wrapper.cancel();
            break;
        }
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolr1_5_2Tor1_6_1.class, ClientboundPackets1_5_2::getPacket));
        userConnection.put(new EntityTracker());
        userConnection.put(new AttachTracker());
    }

    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}

