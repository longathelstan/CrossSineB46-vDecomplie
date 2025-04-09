/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2;

import com.google.common.collect.Lists;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.IdAndData;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.packet.ClientboundPacketsb1_1;
import net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.packet.ServerboundPacketsb1_1;
import net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.rewriter.BlockDataRewriter;
import net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.storage.EntityFlagStorage;
import net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.types.Typesb1_1;
import net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.packet.ClientboundPacketsb1_2;
import net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.packet.ServerboundPacketsb1_2;
import net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.types.EntityDataTypesb1_2;
import net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.types.Typesb1_2;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.types.Types1_1;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types.Types1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.Types1_4_2;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolb1_1_2Tob1_2_0_2
extends StatelessProtocol<ClientboundPacketsb1_1, ClientboundPacketsb1_2, ServerboundPacketsb1_1, ServerboundPacketsb1_2> {
    final BlockDataRewriter BLOCK_DATA_REWRITER = new BlockDataRewriter();

    public Protocolb1_1_2Tob1_2_0_2() {
        super(ClientboundPacketsb1_1.class, ClientboundPacketsb1_2.class, ServerboundPacketsb1_1.class, ServerboundPacketsb1_2.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPacketsb1_1.SET_EQUIPPED_ITEM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.create(Types.SHORT, (short)0);
            }
        });
        this.registerClientbound(ClientboundPacketsb1_1.ANIMATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.INT, 0);
                    byte animationId = wrapper.get(Types.BYTE, 0);
                    if (animationId <= 2) {
                        return;
                    }
                    wrapper.cancel();
                    EntityFlagStorage entityFlagStorage = wrapper.user().get(EntityFlagStorage.class);
                    int oldMask = entityFlagStorage.getFlagMask(entityId);
                    switch (animationId) {
                        case 100: {
                            entityFlagStorage.setFlag(entityId, 2, true);
                            break;
                        }
                        case 101: {
                            entityFlagStorage.setFlag(entityId, 2, false);
                            break;
                        }
                        case 102: {
                            entityFlagStorage.setFlag(entityId, 0, true);
                            break;
                        }
                        case 103: {
                            entityFlagStorage.setFlag(entityId, 0, false);
                            break;
                        }
                        case 104: {
                            entityFlagStorage.setFlag(entityId, 1, true);
                            break;
                        }
                        case 105: {
                            entityFlagStorage.setFlag(entityId, 1, false);
                        }
                    }
                    if (oldMask != entityFlagStorage.getFlagMask(entityId)) {
                        PacketWrapper setEntityData = PacketWrapper.create(ClientboundPacketsb1_2.SET_ENTITY_DATA, wrapper.user());
                        setEntityData.write(Types.INT, wrapper.get(Types.INT, 0));
                        setEntityData.write(Typesb1_2.ENTITY_DATA_LIST, Lists.newArrayList((Object[])new EntityData[]{new EntityData(0, EntityDataTypesb1_2.BYTE, (byte)entityFlagStorage.getFlagMask(entityId))}));
                        setEntityData.send(Protocolb1_1_2Tob1_2_0_2.class);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPacketsb1_1.SPAWN_ITEM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.handler(wrapper -> {
                    short itemId = wrapper.read(Types.SHORT);
                    byte itemCount2 = wrapper.read(Types.BYTE);
                    wrapper.write(Types1_3_1.NBTLESS_ITEM, new DataItem(itemId, itemCount2, 0, null));
                });
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
            }
        });
        this.registerClientbound(ClientboundPacketsb1_1.ADD_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> wrapper.write(Typesb1_2.ENTITY_DATA_LIST, Lists.newArrayList((Object[])new EntityData[]{new EntityData(0, EntityDataTypesb1_2.BYTE, (byte)0)})));
            }
        });
        this.registerClientbound(ClientboundPacketsb1_1.LEVEL_CHUNK, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> Protocolb1_1_2Tob1_2_0_2.this.BLOCK_DATA_REWRITER.remapChunk(wrapper.passthrough(Types1_1.CHUNK)));
            }
        });
        this.registerClientbound(ClientboundPacketsb1_1.CHUNK_BLOCKS_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types1_1.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(wrapper -> Protocolb1_1_2Tob1_2_0_2.this.BLOCK_DATA_REWRITER.remapBlockChangeRecords(wrapper.get(Types1_1.BLOCK_CHANGE_RECORD_ARRAY, 0)));
            }
        });
        this.registerClientbound(ClientboundPacketsb1_1.BLOCK_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    IdAndData block = new IdAndData(wrapper.get(Types.UNSIGNED_BYTE, 0).shortValue(), wrapper.get(Types.UNSIGNED_BYTE, 1).shortValue());
                    Protocolb1_1_2Tob1_2_0_2.this.BLOCK_DATA_REWRITER.remapBlock(block);
                    wrapper.set(Types.UNSIGNED_BYTE, 0, (short)block.getId());
                    wrapper.set(Types.UNSIGNED_BYTE, 1, Short.valueOf(block.getData()));
                });
            }
        });
        this.registerClientbound(ClientboundPacketsb1_1.CONTAINER_SET_SLOT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Typesb1_1.NBTLESS_ITEM, Types1_4_2.NBTLESS_ITEM);
            }
        });
        this.registerServerbound(ServerboundPacketsb1_2.USE_ITEM_ON, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types1_4_2.NBTLESS_ITEM, Typesb1_1.NBTLESS_ITEM);
            }
        });
        this.registerServerbound(ServerboundPacketsb1_2.PLAYER_COMMAND, ServerboundPacketsb1_1.SWING, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE, Types.UNSIGNED_BYTE, i -> (short)(i + 103));
            }
        });
        this.registerServerbound(ServerboundPacketsb1_2.CONTAINER_CLICK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types1_4_2.NBTLESS_ITEM, Typesb1_1.NBTLESS_ITEM);
            }
        });
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolb1_1_2Tob1_2_0_2.class, ClientboundPacketsb1_1::getPacket));
        userConnection.put(new EntityFlagStorage());
    }
}

