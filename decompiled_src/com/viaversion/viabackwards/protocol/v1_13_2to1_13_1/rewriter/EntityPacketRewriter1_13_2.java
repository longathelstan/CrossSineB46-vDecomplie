/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13_2to1_13_1.rewriter;

import com.viaversion.viabackwards.protocol.v1_13_2to1_13_1.Protocol1_13_2To1_13_1;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;

public class EntityPacketRewriter1_13_2 {
    public static void register(Protocol1_13_2To1_13_1 protocol) {
        protocol.registerClientbound(ClientboundPackets1_13.ADD_MOB, new PacketHandlers(){

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
                this.map(Types1_13_2.ENTITY_DATA_LIST, Types1_13.ENTITY_DATA_LIST);
                this.handler(wrapper -> {
                    for (EntityData entityData : wrapper.get(Types1_13.ENTITY_DATA_LIST, 0)) {
                        entityData.setDataType(Types1_13.ENTITY_DATA_TYPES.byId(entityData.dataType().typeId()));
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types1_13_2.ENTITY_DATA_LIST, Types1_13.ENTITY_DATA_LIST);
                this.handler(wrapper -> {
                    for (EntityData entityData : wrapper.get(Types1_13.ENTITY_DATA_LIST, 0)) {
                        entityData.setDataType(Types1_13.ENTITY_DATA_TYPES.byId(entityData.dataType().typeId()));
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.SET_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types1_13_2.ENTITY_DATA_LIST, Types1_13.ENTITY_DATA_LIST);
                this.handler(wrapper -> {
                    for (EntityData entityData : wrapper.get(Types1_13.ENTITY_DATA_LIST, 0)) {
                        entityData.setDataType(Types1_13.ENTITY_DATA_TYPES.byId(entityData.dataType().typeId()));
                    }
                });
            }
        });
    }
}

