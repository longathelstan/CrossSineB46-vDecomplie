/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_14to1_14_1.rewriter;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_14to1_14_1.Protocol1_14To1_14_1;
import com.viaversion.viaversion.rewriter.EntityRewriter;

public class EntityPacketRewriter1_14_1
extends EntityRewriter<ClientboundPackets1_14, Protocol1_14To1_14_1> {
    public EntityPacketRewriter1_14_1(Protocol1_14To1_14_1 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_14To1_14_1)this.protocol).registerClientbound(ClientboundPackets1_14.ADD_MOB, new PacketHandlers(){

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
                this.handler(EntityPacketRewriter1_14_1.this.trackerAndRewriterHandler(Types1_14.ENTITY_DATA_LIST));
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_14.REMOVE_ENTITIES);
        ((Protocol1_14To1_14_1)this.protocol).registerClientbound(ClientboundPackets1_14.ADD_PLAYER, new PacketHandlers(){

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
                this.handler(EntityPacketRewriter1_14_1.this.trackerAndRewriterHandler(Types1_14.ENTITY_DATA_LIST, EntityTypes1_14.PLAYER));
            }
        });
        this.registerSetEntityData(ClientboundPackets1_14.SET_ENTITY_DATA, Types1_14.ENTITY_DATA_LIST);
    }

    @Override
    protected void registerRewrites() {
        this.filter().type(EntityTypes1_14.VILLAGER).addIndex(15);
        this.filter().type(EntityTypes1_14.WANDERING_TRADER).addIndex(15);
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_14.getTypeFromId(type);
    }
}

