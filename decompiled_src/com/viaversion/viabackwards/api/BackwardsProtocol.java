/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api;

import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class BackwardsProtocol<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType>
extends AbstractProtocol<CU, CM, SM, SU> {
    protected BackwardsProtocol() {
    }

    protected BackwardsProtocol(@Nullable Class<CU> oldClientboundPacketEnum, @Nullable Class<CM> clientboundPacketEnum, @Nullable Class<SM> oldServerboundPacketEnum, @Nullable Class<SU> serverboundPacketEnum) {
        super(oldClientboundPacketEnum, clientboundPacketEnum, oldServerboundPacketEnum, serverboundPacketEnum);
    }

    protected void executeAsyncAfterLoaded(Class<? extends Protocol> protocolClass, Runnable runnable) {
        Via.getManager().getProtocolManager().addMappingLoaderFuture(this.getClass(), protocolClass, runnable);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        BackwardsMappingData mappingData = this.getMappingData();
        if (mappingData != null && mappingData.getViaVersionProtocolClass() != null) {
            this.executeAsyncAfterLoaded(mappingData.getViaVersionProtocolClass(), this::loadMappingData);
        }
    }

    @Override
    public boolean hasMappingDataToLoad() {
        return false;
    }

    @Override
    public @Nullable BackwardsMappingData getMappingData() {
        return null;
    }

    @Override
    public @Nullable TranslatableRewriter<CU> getComponentRewriter() {
        return null;
    }
}

