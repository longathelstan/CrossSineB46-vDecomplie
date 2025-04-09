/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import java.util.List;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.packet.ClientboundPackets1_4_2;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.rewriter.ItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.Types1_4_2;
import net.raphimc.vialegacy.protocol.release.r1_4_4_5tor1_4_6_7.packet.ClientboundPackets1_4_4;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.packet.ServerboundPackets1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.EntityDataTypes1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;

public class Protocolr1_4_2Tor1_4_4_5
extends StatelessProtocol<ClientboundPackets1_4_2, ClientboundPackets1_4_4, ServerboundPackets1_5_2, ServerboundPackets1_5_2> {
    final ItemRewriter itemRewriter = new ItemRewriter(this);

    public Protocolr1_4_2Tor1_4_4_5() {
        super(ClientboundPackets1_4_2.class, ClientboundPackets1_4_4.class, ServerboundPackets1_5_2.class, ServerboundPackets1_5_2.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.registerClientbound(ClientboundPackets1_4_2.MAP_ITEM_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types1_4_2.UNSIGNED_BYTE_BYTE_ARRAY, Types.SHORT_BYTE_ARRAY);
            }
        });
        this.registerClientbound(ClientboundPackets1_4_2.ADD_PLAYER, new PacketHandlers(){

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
                this.map(Types1_4_2.ENTITY_DATA_LIST, Types1_6_4.ENTITY_DATA_LIST);
                this.handler(wrapper -> Protocolr1_4_2Tor1_4_4_5.this.rewriteEntityData(wrapper.get(Types1_6_4.ENTITY_DATA_LIST, 0)));
            }
        });
        this.registerClientbound(ClientboundPackets1_4_2.ADD_MOB, new PacketHandlers(){

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
                this.map(Types1_4_2.ENTITY_DATA_LIST, Types1_6_4.ENTITY_DATA_LIST);
                this.handler(wrapper -> Protocolr1_4_2Tor1_4_4_5.this.rewriteEntityData(wrapper.get(Types1_6_4.ENTITY_DATA_LIST, 0)));
            }
        });
        this.registerClientbound(ClientboundPackets1_4_2.SET_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_4_2.ENTITY_DATA_LIST, Types1_6_4.ENTITY_DATA_LIST);
                this.handler(wrapper -> Protocolr1_4_2Tor1_4_4_5.this.rewriteEntityData(wrapper.get(Types1_6_4.ENTITY_DATA_LIST, 0)));
            }
        });
    }

    void rewriteEntityData(List<EntityData> entityDataList) {
        for (EntityData entityData : entityDataList) {
            entityData.setDataType(EntityDataTypes1_6_4.byId(entityData.dataType().typeId()));
        }
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolr1_4_2Tor1_4_4_5.class, ClientboundPackets1_4_2::getPacket));
    }

    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}

