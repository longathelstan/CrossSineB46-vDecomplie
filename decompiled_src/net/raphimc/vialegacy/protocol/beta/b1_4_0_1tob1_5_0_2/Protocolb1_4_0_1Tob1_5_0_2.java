/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_4_0_1tob1_5_0_2;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import java.util.List;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.beta.b1_4_0_1tob1_5_0_2.packet.ClientboundPacketsb1_4;
import net.raphimc.vialegacy.protocol.beta.b1_4_0_1tob1_5_0_2.packet.ServerboundPacketsb1_4;
import net.raphimc.vialegacy.protocol.beta.b1_4_0_1tob1_5_0_2.types.Typesb1_4;
import net.raphimc.vialegacy.protocol.beta.b1_5_0_2tob1_6_0_6.packet.ClientboundPacketsb1_5;
import net.raphimc.vialegacy.protocol.beta.b1_5_0_2tob1_6_0_6.packet.ServerboundPacketsb1_5;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.types.Typesb1_7_0_3;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types.EntityDataTypes1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types.Types1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.Types1_4_2;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolb1_4_0_1Tob1_5_0_2
extends StatelessProtocol<ClientboundPacketsb1_4, ClientboundPacketsb1_5, ServerboundPacketsb1_4, ServerboundPacketsb1_5> {
    public Protocolb1_4_0_1Tob1_5_0_2() {
        super(ClientboundPacketsb1_4.class, ClientboundPacketsb1_5.class, ServerboundPacketsb1_4.class, ServerboundPacketsb1_5.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPacketsb1_4.HANDSHAKE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Typesb1_7_0_3.STRING, Types1_6_4.STRING);
            }
        });
        this.registerClientbound(ClientboundPacketsb1_4.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Typesb1_7_0_3.STRING, Types1_6_4.STRING);
                this.read(Typesb1_7_0_3.STRING);
                this.map(Types.LONG);
                this.map(Types.BYTE);
            }
        });
        this.registerClientbound(ClientboundPacketsb1_4.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Typesb1_7_0_3.STRING, Types1_6_4.STRING);
            }
        });
        this.registerClientbound(ClientboundPacketsb1_4.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Typesb1_7_0_3.STRING, Types1_6_4.STRING);
                this.handler(wrapper -> {
                    String name = wrapper.get(Types1_6_4.STRING, 0);
                    name = name.substring(0, Math.min(name.length(), 16));
                    wrapper.set(Types1_6_4.STRING, 0, name);
                });
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.UNSIGNED_SHORT);
            }
        });
        this.registerClientbound(ClientboundPacketsb1_4.ADD_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Typesb1_4.ENTITY_DATA_LIST, Types1_3_1.ENTITY_DATA_LIST);
                this.handler(wrapper -> Protocolb1_4_0_1Tob1_5_0_2.this.rewriteEntityData(wrapper.get(Types1_3_1.ENTITY_DATA_LIST, 0)));
            }
        });
        this.registerClientbound(ClientboundPacketsb1_4.ADD_PAINTING, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Typesb1_7_0_3.STRING, Types1_6_4.STRING);
                this.map(Types1_7_6.BLOCK_POSITION_INT);
                this.map(Types.INT);
            }
        });
        this.registerClientbound(ClientboundPacketsb1_4.SET_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Typesb1_4.ENTITY_DATA_LIST, Types1_3_1.ENTITY_DATA_LIST);
                this.handler(wrapper -> Protocolb1_4_0_1Tob1_5_0_2.this.rewriteEntityData(wrapper.get(Types1_3_1.ENTITY_DATA_LIST, 0)));
            }
        });
        this.registerClientbound(ClientboundPacketsb1_4.UPDATE_SIGN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_SHORT);
                this.map(Typesb1_7_0_3.STRING, Types1_6_4.STRING);
                this.map(Typesb1_7_0_3.STRING, Types1_6_4.STRING);
                this.map(Typesb1_7_0_3.STRING, Types1_6_4.STRING);
                this.map(Typesb1_7_0_3.STRING, Types1_6_4.STRING);
            }
        });
        this.registerClientbound(ClientboundPacketsb1_4.DISCONNECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Typesb1_7_0_3.STRING, Types1_6_4.STRING);
            }
        });
        this.registerServerbound(ServerboundPacketsb1_5.HANDSHAKE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING, Typesb1_7_0_3.STRING);
            }
        });
        this.registerServerbound(ServerboundPacketsb1_5.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_6_4.STRING, Typesb1_7_0_3.STRING);
                this.create(Typesb1_7_0_3.STRING, "Password");
                this.map(Types.LONG);
                this.map(Types.BYTE);
            }
        });
        this.registerServerbound(ServerboundPacketsb1_5.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING, Typesb1_7_0_3.STRING);
            }
        });
        this.registerServerbound(ServerboundPacketsb1_5.CONTAINER_CLICK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.read(Types.BYTE);
                this.map(Types1_4_2.NBTLESS_ITEM);
            }
        });
        this.registerServerbound(ServerboundPacketsb1_5.SIGN_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_SHORT);
                this.map(Types1_6_4.STRING, Typesb1_7_0_3.STRING);
                this.map(Types1_6_4.STRING, Typesb1_7_0_3.STRING);
                this.map(Types1_6_4.STRING, Typesb1_7_0_3.STRING);
                this.map(Types1_6_4.STRING, Typesb1_7_0_3.STRING);
            }
        });
        this.registerServerbound(ServerboundPacketsb1_5.DISCONNECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING, Typesb1_7_0_3.STRING);
            }
        });
    }

    void rewriteEntityData(List<EntityData> entityDataList) {
        for (EntityData entityData : entityDataList) {
            entityData.setDataType(EntityDataTypes1_3_1.byId(entityData.dataType().typeId()));
        }
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolb1_4_0_1Tob1_5_0_2.class, ClientboundPacketsb1_4::getPacket));
    }
}

