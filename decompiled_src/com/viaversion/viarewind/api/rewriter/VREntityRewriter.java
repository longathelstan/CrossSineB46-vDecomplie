/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.api.rewriter;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_8;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;

public abstract class VREntityRewriter<C extends ClientboundPacketType, T extends BackwardsProtocol<C, ?, ?, ?>>
extends LegacyEntityRewriter<C, T> {
    public VREntityRewriter(T protocol) {
        super(protocol, EntityDataTypes1_8.STRING, EntityDataTypes1_8.BYTE);
    }

    public VREntityRewriter(T protocol, EntityDataType displayType, EntityDataType displayVisibilityType) {
        super(protocol, displayType, displayVisibilityType);
    }

    protected void registerJoinGame1_8(C packetType) {
        ((BackwardsProtocol)this.protocol).registerClientbound(packetType, new PacketHandlers(){

            @Override
            protected void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.BYTE);
                this.handler(VREntityRewriter.this.playerTrackerHandler());
                this.handler(VREntityRewriter.this.getDimensionHandler());
            }
        });
    }

    protected void removeEntities(UserConnection connection, int[] entities) {
        EntityTrackerBase tracker = (EntityTrackerBase)this.tracker(connection);
        for (int entityId : entities) {
            tracker.removeEntity(entityId);
        }
    }

    protected PacketHandler getDimensionHandler() {
        return wrapper -> {
            byte dimension = wrapper.get(Types.BYTE, 0);
            Object clientWorld = wrapper.user().getClientWorld(((BackwardsProtocol)this.protocol).getClass());
            ((ClientWorld)clientWorld).setEnvironment(dimension);
        };
    }

    @Override
    protected Object getDisplayVisibilityDataValue() {
        return (byte)1;
    }

    @Override
    protected boolean alwaysShowOriginalMobName() {
        return ViaRewind.getConfig().alwaysShowOriginalMobName();
    }
}

