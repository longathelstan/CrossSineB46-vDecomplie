/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2;

import com.google.common.collect.Lists;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import java.util.List;
import java.util.logging.Level;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.data.ItemList1_6;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.api.util.PacketUtil;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.packet.ClientboundPackets1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.packet.ServerboundPackets1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.rewriter.ItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.rewriter.SoundRewriter;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types.Types1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.packet.ClientboundPackets1_4_2;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.EntityDataTypes1_4_2;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.Types1_4_2;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.packet.ServerboundPackets1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.storage.EntityTracker;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.data.EntityDataIndex1_7_6;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolr1_3_1_2Tor1_4_2
extends StatelessProtocol<ClientboundPackets1_3_1, ClientboundPackets1_4_2, ServerboundPackets1_3_1, ServerboundPackets1_5_2> {
    final ItemRewriter itemRewriter = new ItemRewriter(this);

    public Protocolr1_3_1_2Tor1_4_2() {
        super(ClientboundPackets1_3_1.class, ClientboundPackets1_4_2.class, ServerboundPackets1_3_1.class, ServerboundPackets1_5_2.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.registerClientbound(ClientboundPackets1_3_1.DISCONNECT, wrapper -> {
            State currentState = wrapper.user().getProtocolInfo().getServerState();
            if (currentState == State.STATUS) {
                String reason = wrapper.read(Types1_6_4.STRING);
                try {
                    ProtocolInfo info = wrapper.user().getProtocolInfo();
                    String[] pingParts = reason.split("\u00a7");
                    String string = pingParts[2];
                    String string2 = pingParts[1];
                    String string3 = pingParts[0];
                    String string4 = info.serverProtocolVersion().getName();
                    int n = info.serverProtocolVersion().getVersion();
                    String out = "\u00a71\u0000" + n + "\u0000" + string4 + "\u0000" + string3 + "\u0000" + string2 + "\u0000" + string;
                    wrapper.write(Types1_6_4.STRING, out);
                }
                catch (Throwable e) {
                    String string = reason;
                    ViaLegacy.getPlatform().getLogger().log(Level.WARNING, "Could not parse 1.3.1 ping: " + string, e);
                    wrapper.cancel();
                }
            }
        });
        this.registerClientbound(ClientboundPackets1_3_1.LOGIN, new PacketHandlers(){

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
                    wrapper.send(Protocolr1_3_1_2Tor1_4_2.class);
                    wrapper.cancel();
                    PacketWrapper setEntityData = PacketWrapper.create(ClientboundPackets1_4_2.SET_ENTITY_DATA, wrapper.user());
                    setEntityData.write(Types.INT, wrapper.get(Types.INT, 0));
                    setEntityData.write(Types1_4_2.ENTITY_DATA_LIST, Lists.newArrayList((Object[])new EntityData[]{new EntityData(EntityDataIndex1_7_6.HUMAN_SKIN_FLAGS.getOldIndex(), EntityDataTypes1_4_2.BYTE, (byte)0)}));
                    setEntityData.send(Protocolr1_3_1_2Tor1_4_2.class);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_3_1.SET_TIME, wrapper -> {
            long time = wrapper.passthrough(Types.LONG);
            wrapper.write(Types.LONG, time % 24000L);
        });
        this.registerClientbound(ClientboundPackets1_3_1.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types1_6_4.STRING);
                this.handler(wrapper -> {
                    EntityTracker entityTracker = wrapper.user().get(EntityTracker.class);
                    Integer[] entityIds = (Integer[])entityTracker.getTrackedEntities().keySet().stream().filter(i -> i.intValue() != entityTracker.getPlayerID()).toArray(Integer[]::new);
                    int[] primitiveInts = new int[entityIds.length];
                    for (int i2 = 0; i2 < entityIds.length; ++i2) {
                        primitiveInts[i2] = entityIds[i2];
                    }
                    PacketWrapper destroyEntities = PacketWrapper.create(ClientboundPackets1_4_2.REMOVE_ENTITIES, wrapper.user());
                    destroyEntities.write(Types1_7_6.INT_ARRAY, primitiveInts);
                    destroyEntities.send(Protocolr1_3_1_2Tor1_4_2.class);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_3_1.ADD_PLAYER, new PacketHandlers(){

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
                this.map(Types1_3_1.ENTITY_DATA_LIST, Types1_4_2.ENTITY_DATA_LIST);
                this.handler(wrapper -> {
                    List<EntityData> entityDataList = wrapper.get(Types1_4_2.ENTITY_DATA_LIST, 0);
                    Protocolr1_3_1_2Tor1_4_2.this.rewriteEntityData(entityDataList);
                    entityDataList.removeIf(entityData -> entityData.dataType() == EntityDataTypes1_4_2.BYTE && entityData.id() == EntityDataIndex1_7_6.HUMAN_SKIN_FLAGS.getOldIndex());
                    entityDataList.add(new EntityData(EntityDataIndex1_7_6.HUMAN_SKIN_FLAGS.getOldIndex(), EntityDataTypes1_4_2.BYTE, (byte)0));
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_3_1.SPAWN_ITEM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_3_1.NBTLESS_ITEM, Types1_7_6.ITEM);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
            }
        });
        this.registerClientbound(ClientboundPackets1_3_1.ADD_MOB, new PacketHandlers(){

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
                this.map(Types1_3_1.ENTITY_DATA_LIST, Types1_4_2.ENTITY_DATA_LIST);
                this.handler(wrapper -> {
                    Protocolr1_3_1_2Tor1_4_2.this.rewriteEntityData(wrapper.get(Types1_4_2.ENTITY_DATA_LIST, 0));
                    int entityId = wrapper.get(Types.INT, 0);
                    short typeId = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    if (typeId == EntityTypes1_8.EntityType.SKELETON.getId()) {
                        Protocolr1_3_1_2Tor1_4_2.this.setMobHandItem(entityId, new DataItem(ItemList1_6.bow.itemId(), 1, 0, null), wrapper);
                    } else if (typeId == EntityTypes1_8.EntityType.ZOMBIE_PIGMEN.getId()) {
                        Protocolr1_3_1_2Tor1_4_2.this.setMobHandItem(entityId, new DataItem(ItemList1_6.swordGold.itemId(), 1, 0, null), wrapper);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_3_1.ADD_PAINTING, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_6_4.STRING);
                this.map(Types1_7_6.BLOCK_POSITION_INT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int n;
                    int direction = wrapper.get(Types.INT, 1);
                    switch (direction) {
                        case 0: {
                            n = 2;
                            break;
                        }
                        case 2: {
                            n = 0;
                            break;
                        }
                        default: {
                            n = direction;
                        }
                    }
                    direction = n;
                    wrapper.set(Types.INT, 1, direction);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_3_1.SET_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_3_1.ENTITY_DATA_LIST, Types1_4_2.ENTITY_DATA_LIST);
                this.handler(wrapper -> Protocolr1_3_1_2Tor1_4_2.this.rewriteEntityData(wrapper.get(Types1_4_2.ENTITY_DATA_LIST, 0)));
            }
        });
        this.registerClientbound(ClientboundPackets1_3_1.LEVEL_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
                this.map(Types.INT);
                this.create(Types.BOOLEAN, false);
            }
        });
        this.registerClientbound(ClientboundPackets1_3_1.CUSTOM_SOUND, new PacketHandlers(){

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
                            ViaLegacy.getPlatform().getLogger().warning("Unable to map 1.3.2 sound '" + string + "'");
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
        this.registerClientbound(ClientboundPackets1_3_1.MAP_ITEM_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types1_4_2.UNSIGNED_BYTE_BYTE_ARRAY);
                this.handler(wrapper -> {
                    byte[] data = wrapper.get(Types1_4_2.UNSIGNED_BYTE_BYTE_ARRAY, 0);
                    if (data[0] == 1) {
                        for (int i = 0; i < (data.length - 1) / 3; ++i) {
                            byte icon = (byte)(data[i * 3 + 1] % 16);
                            byte centerX = data[i * 3 + 2];
                            byte centerZ = data[i * 3 + 3];
                            byte iconRotation = (byte)(data[i * 3 + 1] / 16);
                            data[i * 3 + 1] = (byte)(icon << 4 | iconRotation & 0xF);
                            data[i * 3 + 2] = centerX;
                            data[i * 3 + 3] = centerZ;
                        }
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_3_1.CUSTOM_PAYLOAD, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    String channel = wrapper.read(Types1_6_4.STRING);
                    short length = wrapper.read(Types.SHORT);
                    try {
                        if (channel.equals("MC|TrList")) {
                            wrapper.passthrough(Types.INT);
                            int count = wrapper.passthrough(Types.UNSIGNED_BYTE).shortValue();
                            for (int i = 0; i < count; ++i) {
                                wrapper.passthrough(Types1_7_6.ITEM);
                                wrapper.passthrough(Types1_7_6.ITEM);
                                if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                                    wrapper.passthrough(Types1_7_6.ITEM);
                                }
                                wrapper.write(Types.BOOLEAN, false);
                            }
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
        this.registerServerbound(ServerboundPackets1_5_2.SERVER_PING, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(PacketWrapper::clearPacket);
            }
        });
        this.registerServerbound(ServerboundPackets1_5_2.CLIENT_INFORMATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.read(Types.BOOLEAN);
            }
        });
    }

    void rewriteEntityData(List<EntityData> entityDataList) {
        for (EntityData entityData : entityDataList) {
            entityData.setDataType(EntityDataTypes1_4_2.byId(entityData.dataType().typeId()));
        }
    }

    void setMobHandItem(int entityId, Item item, PacketWrapper wrapper) {
        PacketWrapper handItem = PacketWrapper.create(ClientboundPackets1_4_2.SET_EQUIPPED_ITEM, wrapper.user());
        handItem.write(Types.INT, entityId);
        handItem.write(Types.SHORT, (short)0);
        handItem.write(Types1_7_6.ITEM, item);
        wrapper.send(Protocolr1_3_1_2Tor1_4_2.class);
        handItem.send(Protocolr1_3_1_2Tor1_4_2.class);
        wrapper.cancel();
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolr1_3_1_2Tor1_4_2.class, ClientboundPackets1_3_1::getPacket));
    }

    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}

