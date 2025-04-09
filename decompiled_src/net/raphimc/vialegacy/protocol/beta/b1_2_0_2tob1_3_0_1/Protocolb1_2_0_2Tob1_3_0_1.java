/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.IdAndData;
import java.util.List;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.data.BlockHardnessList;
import net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.packet.ClientboundPacketsb1_2;
import net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.packet.ServerboundPacketsb1_2;
import net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.storage.BlockDigStorage;
import net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.task.BlockDigTickTask;
import net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.types.Typesb1_2;
import net.raphimc.vialegacy.protocol.beta.b1_3_0_1tob1_4_0_1.packet.ClientboundPacketsb1_3;
import net.raphimc.vialegacy.protocol.beta.b1_4_0_1tob1_5_0_2.packet.ServerboundPacketsb1_4;
import net.raphimc.vialegacy.protocol.beta.b1_4_0_1tob1_5_0_2.types.EntityDataTypesb1_4;
import net.raphimc.vialegacy.protocol.beta.b1_4_0_1tob1_5_0_2.types.Typesb1_4;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.ChunkTracker;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolb1_2_0_2Tob1_3_0_1
extends StatelessProtocol<ClientboundPacketsb1_2, ClientboundPacketsb1_3, ServerboundPacketsb1_2, ServerboundPacketsb1_4> {
    public Protocolb1_2_0_2Tob1_3_0_1() {
        super(ClientboundPacketsb1_2.class, ClientboundPacketsb1_3.class, ServerboundPacketsb1_2.class, ServerboundPacketsb1_4.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPacketsb1_2.ADD_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Typesb1_2.ENTITY_DATA_LIST, Typesb1_4.ENTITY_DATA_LIST);
                this.handler(wrapper -> Protocolb1_2_0_2Tob1_3_0_1.this.rewriteEntityData(wrapper.get(Typesb1_4.ENTITY_DATA_LIST, 0)));
            }
        });
        this.registerClientbound(ClientboundPacketsb1_2.SET_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Typesb1_2.ENTITY_DATA_LIST, Typesb1_4.ENTITY_DATA_LIST);
                this.handler(wrapper -> Protocolb1_2_0_2Tob1_3_0_1.this.rewriteEntityData(wrapper.get(Typesb1_4.ENTITY_DATA_LIST, 0)));
            }
        });
        this.registerServerbound(ServerboundPacketsb1_4.PLAYER_ACTION, wrapper -> {
            wrapper.cancel();
            short status = wrapper.read(Types.UNSIGNED_BYTE);
            BlockPosition pos = wrapper.read(Types1_7_6.BLOCK_POSITION_UBYTE);
            short facing = wrapper.read(Types.UNSIGNED_BYTE);
            if (status != 4) {
                wrapper.user().getStoredObjects().remove(BlockDigStorage.class);
            }
            switch (status) {
                case 0: {
                    IdAndData blockBeingBroken = wrapper.user().get(ChunkTracker.class).getBlockNotNull(pos);
                    if (BlockHardnessList.canBeBrokenInstantly(blockBeingBroken)) {
                        Protocolb1_2_0_2Tob1_3_0_1.sendBlockDigPacket(wrapper.user(), (short)0, pos, facing);
                        Protocolb1_2_0_2Tob1_3_0_1.sendBlockDigPacket(wrapper.user(), (short)3, pos, facing);
                        Protocolb1_2_0_2Tob1_3_0_1.sendBlockDigPacket(wrapper.user(), (short)1, pos, facing);
                        Protocolb1_2_0_2Tob1_3_0_1.sendBlockDigPacket(wrapper.user(), (short)2, pos, facing);
                        return;
                    }
                    wrapper.user().put(new BlockDigStorage(wrapper.user(), pos, facing));
                    Protocolb1_2_0_2Tob1_3_0_1.sendBlockDigPacket(wrapper.user(), (short)0, pos, facing);
                    Protocolb1_2_0_2Tob1_3_0_1.sendBlockDigPacket(wrapper.user(), (short)1, pos, facing);
                    break;
                }
                case 1: {
                    Protocolb1_2_0_2Tob1_3_0_1.sendBlockDigPacket(wrapper.user(), (short)2, pos, facing);
                    break;
                }
                case 2: {
                    Protocolb1_2_0_2Tob1_3_0_1.sendBlockDigPacket(wrapper.user(), (short)1, pos, facing);
                    Protocolb1_2_0_2Tob1_3_0_1.sendBlockDigPacket(wrapper.user(), (short)3, pos, facing);
                    Protocolb1_2_0_2Tob1_3_0_1.sendBlockDigPacket(wrapper.user(), (short)2, pos, facing);
                    break;
                }
                case 4: {
                    Protocolb1_2_0_2Tob1_3_0_1.sendBlockDigPacket(wrapper.user(), (short)4, pos, facing);
                }
            }
        });
        this.registerServerbound(ServerboundPacketsb1_4.PLAYER_COMMAND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    if (wrapper.get(Types.BYTE, 0) > 2) {
                        wrapper.cancel();
                    }
                });
            }
        });
        this.cancelServerbound(ServerboundPacketsb1_4.POSITION);
    }

    void rewriteEntityData(List<EntityData> entityDataList) {
        for (EntityData entityData : entityDataList) {
            entityData.setDataType(EntityDataTypesb1_4.byId(entityData.dataType().typeId()));
        }
    }

    @Override
    public void register(ViaProviders providers) {
        Via.getPlatform().runRepeatingSync(new BlockDigTickTask(), 1L);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolb1_2_0_2Tob1_3_0_1.class, ClientboundPacketsb1_2::getPacket));
    }

    public static void sendBlockDigPacket(UserConnection userConnection, short status, BlockPosition position, short facing) {
        PacketWrapper blockDig = PacketWrapper.create(ServerboundPacketsb1_2.PLAYER_ACTION, userConnection);
        blockDig.write(Types.UNSIGNED_BYTE, status);
        blockDig.write(Types1_7_6.BLOCK_POSITION_UBYTE, position);
        blockDig.write(Types.UNSIGNED_BYTE, facing);
        blockDig.sendToServer(Protocolb1_2_0_2Tob1_3_0_1.class);
    }
}

