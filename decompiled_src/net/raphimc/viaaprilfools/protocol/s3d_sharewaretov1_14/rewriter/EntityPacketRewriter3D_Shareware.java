/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14.rewriter;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import java.util.List;
import net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14.Protocol3D_SharewareTo1_14;
import net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14.packet.ClientboundPackets3D_Shareware;

public class EntityPacketRewriter3D_Shareware
extends RewriterBase<Protocol3D_SharewareTo1_14> {
    public EntityPacketRewriter3D_Shareware(Protocol3D_SharewareTo1_14 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        ((Protocol3D_SharewareTo1_14)this.protocol).registerClientbound(ClientboundPackets3D_Shareware.ADD_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.VAR_INT);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types1_14.ENTITY_DATA_LIST);
                this.handler(packetWrapper -> EntityPacketRewriter3D_Shareware.this.handleEntityData(packetWrapper.user(), packetWrapper.get(Types1_14.ENTITY_DATA_LIST, 0)));
            }
        });
        ((Protocol3D_SharewareTo1_14)this.protocol).registerClientbound(ClientboundPackets3D_Shareware.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types1_14.ENTITY_DATA_LIST);
                this.handler(packetWrapper -> EntityPacketRewriter3D_Shareware.this.handleEntityData(packetWrapper.user(), packetWrapper.get(Types1_14.ENTITY_DATA_LIST, 0)));
            }
        });
        ((Protocol3D_SharewareTo1_14)this.protocol).registerClientbound(ClientboundPackets3D_Shareware.SET_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types1_14.ENTITY_DATA_LIST);
                this.handler(packetWrapper -> EntityPacketRewriter3D_Shareware.this.handleEntityData(packetWrapper.user(), packetWrapper.get(Types1_14.ENTITY_DATA_LIST, 0)));
            }
        });
    }

    public void handleEntityData(UserConnection user, List<EntityData> entityDataList) {
        for (EntityData entityData : entityDataList) {
            if (entityData.dataType() != Types1_14.ENTITY_DATA_TYPES.itemType) continue;
            entityData.setValue(((Protocol3D_SharewareTo1_14)this.protocol).getItemRewriter().handleItemToClient(user, (Item)entityData.value()));
        }
    }
}

