/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_11_1to1_11;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.v1_11_1to1_11.rewriter.EntityPacketRewriter1_11_1;
import com.viaversion.viabackwards.protocol.v1_11_1to1_11.rewriter.ItemPacketRewriter1_11_1;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_11;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ServerboundPackets1_9_3;

public class Protocol1_11_1To1_11
extends BackwardsProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
    private final EntityPacketRewriter1_11_1 entityRewriter = new EntityPacketRewriter1_11_1(this);
    private final ItemPacketRewriter1_11_1 itemRewriter = new ItemPacketRewriter1_11_1(this);

    public Protocol1_11_1To1_11() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
    }

    @Override
    public void init(UserConnection user) {
        user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, EntityTypes1_11.EntityType.PLAYER));
        user.addClientWorld(this.getClass(), new ClientWorld());
    }

    public EntityPacketRewriter1_11_1 getEntityRewriter() {
        return this.entityRewriter;
    }

    public ItemPacketRewriter1_11_1 getItemRewriter() {
        return this.itemRewriter;
    }
}

