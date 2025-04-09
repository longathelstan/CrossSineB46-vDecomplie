/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_17_1to1_18.rewriter;

import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_17;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.Protocol1_17_1To1_18;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.storage.ChunkLightStorage;
import com.viaversion.viaversion.protocols.v1_17to1_17_1.packet.ClientboundPackets1_17_1;
import com.viaversion.viaversion.rewriter.EntityRewriter;

public final class EntityPacketRewriter1_18
extends EntityRewriter<ClientboundPackets1_17_1, Protocol1_17_1To1_18> {
    public EntityPacketRewriter1_18(Protocol1_17_1To1_18 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        this.registerSetEntityData(ClientboundPackets1_17_1.SET_ENTITY_DATA, Types1_17.ENTITY_DATA_LIST, Types1_18.ENTITY_DATA_LIST);
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_17_1.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.STRING_ARRAY);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.map(Types.STRING);
                this.map(Types.LONG);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int chunkRadius = wrapper.passthrough(Types.VAR_INT);
                    wrapper.write(Types.VAR_INT, chunkRadius);
                });
                this.handler(EntityPacketRewriter1_18.this.worldDataTrackerHandler(1));
                this.handler(EntityPacketRewriter1_18.this.biomeSizeTracker());
            }
        });
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_17_1.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.NAMED_COMPOUND_TAG);
                this.map(Types.STRING);
                this.handler(wrapper -> {
                    Object tracker;
                    String world = wrapper.get(Types.STRING, 0);
                    if (!world.equals((tracker = EntityPacketRewriter1_18.this.tracker(wrapper.user())).currentWorld())) {
                        wrapper.user().get(ChunkLightStorage.class).clear();
                    }
                });
                this.handler(EntityPacketRewriter1_18.this.worldDataTrackerHandler(0));
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().mapDataType(Types1_18.ENTITY_DATA_TYPES::byId);
        this.filter().dataType(Types1_18.ENTITY_DATA_TYPES.particleType).handler((event, data) -> {
            Particle particle = (Particle)data.getValue();
            if (particle.id() == 2) {
                particle.setId(3);
                particle.add(Types.VAR_INT, 7754);
            } else if (particle.id() == 3) {
                particle.add(Types.VAR_INT, 7786);
            } else {
                this.rewriteParticle(event.user(), particle);
            }
        });
        this.registerEntityDataTypeHandler(Types1_18.ENTITY_DATA_TYPES.itemType, null, null);
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_17.getTypeFromId(type);
    }
}

