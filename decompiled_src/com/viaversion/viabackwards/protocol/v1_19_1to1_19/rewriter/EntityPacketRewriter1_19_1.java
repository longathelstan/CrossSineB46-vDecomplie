/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_19_1to1_19.rewriter;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.v1_19_1to1_19.Protocol1_19_1To1_19;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19;
import com.viaversion.viaversion.api.type.types.version.Types1_19;
import com.viaversion.viaversion.protocols.v1_19to1_19_1.packet.ClientboundPackets1_19_1;

public final class EntityPacketRewriter1_19_1
extends EntityRewriter<ClientboundPackets1_19_1, Protocol1_19_1To1_19> {
    public EntityPacketRewriter1_19_1(Protocol1_19_1To1_19 protocol) {
        super(protocol, Types1_19.ENTITY_DATA_TYPES.optionalComponentType, Types1_19.ENTITY_DATA_TYPES.booleanType);
    }

    @Override
    protected void registerPackets() {
        this.registerSetEntityData(ClientboundPackets1_19_1.SET_ENTITY_DATA, Types1_19.ENTITY_DATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_19_1.REMOVE_ENTITIES);
        this.registerSpawnTracker(ClientboundPackets1_19_1.ADD_ENTITY);
    }

    @Override
    public void registerRewrites() {
        this.filter().type(EntityTypes1_19.ALLAY).cancel(16);
        this.filter().type(EntityTypes1_19.ALLAY).cancel(17);
    }

    @Override
    public EntityType typeFromId(int typeId) {
        return EntityTypes1_19.getTypeFromId(typeId);
    }
}

