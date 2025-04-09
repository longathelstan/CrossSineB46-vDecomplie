/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_4_4_5tor1_4_6_7;

import com.google.common.collect.Lists;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.release.r1_4_4_5tor1_4_6_7.packet.ClientboundPackets1_4_4;
import net.raphimc.vialegacy.protocol.release.r1_4_4_5tor1_4_6_7.rewriter.ItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_4_4_5tor1_4_6_7.types.Types1_4_4;
import net.raphimc.vialegacy.protocol.release.r1_4_6_7tor1_5_0_1.packet.ClientboundPackets1_4_6;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.packet.ServerboundPackets1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.EntityDataTypes1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.data.EntityDataIndex1_7_6;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolr1_4_4_5Tor1_4_6_7
extends StatelessProtocol<ClientboundPackets1_4_4, ClientboundPackets1_4_6, ServerboundPackets1_5_2, ServerboundPackets1_5_2> {
    final ItemRewriter itemRewriter = new ItemRewriter(this);

    public Protocolr1_4_4_5Tor1_4_6_7() {
        super(ClientboundPackets1_4_4.class, ClientboundPackets1_4_6.class, ServerboundPackets1_5_2.class, ServerboundPackets1_5_2.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.registerClientbound(ClientboundPackets1_4_4.SPAWN_ITEM, ClientboundPackets1_4_6.ADD_ENTITY, (PacketWrapper wrapper) -> {
            int entityId = wrapper.read(Types.INT);
            Item item = wrapper.read(Types1_7_6.ITEM);
            int x = wrapper.read(Types.INT);
            int y = wrapper.read(Types.INT);
            int z = wrapper.read(Types.INT);
            byte motionX = wrapper.read(Types.BYTE);
            byte motionY = wrapper.read(Types.BYTE);
            byte motionZ = wrapper.read(Types.BYTE);
            wrapper.write(Types.INT, entityId);
            wrapper.write(Types.BYTE, (byte)EntityTypes1_8.ObjectType.ITEM.getId());
            wrapper.write(Types.INT, x);
            wrapper.write(Types.INT, y);
            wrapper.write(Types.INT, z);
            wrapper.write(Types.BYTE, (byte)0);
            wrapper.write(Types.BYTE, (byte)0);
            wrapper.write(Types.INT, 1);
            wrapper.write(Types.SHORT, (short)((float)motionX / 128.0f * 8000.0f));
            wrapper.write(Types.SHORT, (short)((float)motionY / 128.0f * 8000.0f));
            wrapper.write(Types.SHORT, (short)((float)motionZ / 128.0f * 8000.0f));
            PacketWrapper setEntityData = PacketWrapper.create(ClientboundPackets1_4_6.SET_ENTITY_DATA, wrapper.user());
            setEntityData.write(Types.INT, entityId);
            setEntityData.write(Types1_6_4.ENTITY_DATA_LIST, Lists.newArrayList((Object[])new EntityData[]{new EntityData(EntityDataIndex1_7_6.ITEM_ITEM.getOldIndex(), EntityDataTypes1_6_4.ITEM, item)}));
            wrapper.send(Protocolr1_4_4_5Tor1_4_6_7.class);
            setEntityData.send(Protocolr1_4_4_5Tor1_4_6_7.class);
            wrapper.cancel();
        });
        this.registerClientbound(ClientboundPackets1_4_4.ADD_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.create(Types.BYTE, (byte)0);
                this.create(Types.BYTE, (byte)0);
                this.map(Types.INT);
            }
        });
        this.registerClientbound(ClientboundPackets1_4_4.MAP_BULK_CHUNK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_4_4.CHUNK_BULK, Types1_7_6.CHUNK_BULK);
            }
        });
        this.registerServerbound(ServerboundPackets1_5_2.PLAYER_ACTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    short status = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    if (status == 3) {
                        wrapper.set(Types.UNSIGNED_BYTE, 0, (short)4);
                    }
                });
                this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
                this.map(Types.UNSIGNED_BYTE);
            }
        });
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolr1_4_4_5Tor1_4_6_7.class, ClientboundPackets1_4_4::getPacket));
    }

    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}

