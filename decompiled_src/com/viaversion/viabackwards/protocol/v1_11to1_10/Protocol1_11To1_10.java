/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_11to1_10;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_11to1_10.rewriter.BlockItemPacketRewriter1_11;
import com.viaversion.viabackwards.protocol.v1_11to1_10.rewriter.EntityPacketRewriter1_11;
import com.viaversion.viabackwards.protocol.v1_11to1_10.rewriter.PlayerPacketRewriter1_11;
import com.viaversion.viabackwards.protocol.v1_11to1_10.storage.WindowTracker;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_11;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ServerboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.ComponentRewriter;

public class Protocol1_11To1_10
extends BackwardsProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
    public static final BackwardsMappingData MAPPINGS = new BackwardsMappingData("1.11", "1.10");
    private final EntityPacketRewriter1_11 entityRewriter = new EntityPacketRewriter1_11(this);
    private final BlockItemPacketRewriter1_11 itemRewriter = new BlockItemPacketRewriter1_11(this);
    private TranslatableRewriter<ClientboundPackets1_9_3> componentRewriter;

    public Protocol1_11To1_10() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
    }

    @Override
    protected void registerPackets() {
        this.entityRewriter.register();
        this.itemRewriter.register();
        PlayerPacketRewriter1_11.register(this);
        SoundRewriter<ClientboundPackets1_9_3> soundRewriter = new SoundRewriter<ClientboundPackets1_9_3>(this);
        soundRewriter.registerNamedSound(ClientboundPackets1_9_3.CUSTOM_SOUND);
        soundRewriter.registerSound(ClientboundPackets1_9_3.SOUND);
        this.componentRewriter = new TranslatableRewriter<ClientboundPackets1_9_3>(this, ComponentRewriter.ReadType.JSON);
        this.componentRewriter.registerComponentPacket(ClientboundPackets1_9_3.CHAT);
    }

    @Override
    public void init(UserConnection user) {
        user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, EntityTypes1_11.EntityType.PLAYER));
        user.addClientWorld(this.getClass(), new ClientWorld());
        if (!user.has(WindowTracker.class)) {
            user.put(new WindowTracker());
        }
    }

    @Override
    public BackwardsMappingData getMappingData() {
        return MAPPINGS;
    }

    public EntityPacketRewriter1_11 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPacketRewriter1_11 getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_9_3> getComponentRewriter() {
        return this.componentRewriter;
    }

    @Override
    public boolean hasMappingDataToLoad() {
        return true;
    }
}

