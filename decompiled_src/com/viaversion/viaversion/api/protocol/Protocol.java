/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.rewriter.ComponentRewriter;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.rewriter.TagRewriter;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.ProtocolLogger;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Protocol<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType> {
    default public void registerClientbound(State state, ClientboundPacketType packetType, @Nullable PacketHandler handler) {
        Preconditions.checkArgument((packetType.state() == state ? 1 : 0) != 0);
        this.registerClientbound(state, packetType.getId(), packetType.getId(), handler, false);
    }

    default public void registerServerbound(State state, ServerboundPacketType packetType, @Nullable PacketHandler handler) {
        Preconditions.checkArgument((packetType.state() == state ? 1 : 0) != 0);
        this.registerServerbound(state, packetType.getId(), packetType.getId(), handler, false);
    }

    default public void registerServerbound(State state, int unmappedPacketId, int mappedPacketId, @Nullable PacketHandler handler) {
        this.registerServerbound(state, unmappedPacketId, mappedPacketId, handler, false);
    }

    public void registerServerbound(State var1, int var2, int var3, @Nullable PacketHandler var4, boolean var5);

    public void cancelServerbound(State var1, int var2);

    default public void registerClientbound(State state, int unmappedPacketId, int mappedPacketId, @Nullable PacketHandler handler) {
        this.registerClientbound(state, unmappedPacketId, mappedPacketId, handler, false);
    }

    public void cancelClientbound(State var1, int var2);

    public void registerClientbound(State var1, int var2, int var3, @Nullable PacketHandler var4, boolean var5);

    public void registerClientbound(CU var1, @Nullable PacketHandler var2);

    default public void registerClientbound(CU packetType, @Nullable CM mappedPacketType) {
        this.registerClientbound(packetType, mappedPacketType, null);
    }

    default public void registerClientbound(CU packetType, @Nullable CM mappedPacketType, @Nullable PacketHandler handler) {
        this.registerClientbound(packetType, mappedPacketType, handler, false);
    }

    public void registerClientbound(CU var1, @Nullable CM var2, @Nullable PacketHandler var3, boolean var4);

    public void cancelClientbound(CU var1);

    default public void registerServerbound(SU packetType, @Nullable SM mappedPacketType) {
        this.registerServerbound(packetType, mappedPacketType, null);
    }

    public void registerServerbound(SU var1, @Nullable PacketHandler var2);

    default public void registerServerbound(SU packetType, @Nullable SM mappedPacketType, @Nullable PacketHandler handler) {
        this.registerServerbound(packetType, mappedPacketType, handler, false);
    }

    public void registerServerbound(SU var1, @Nullable SM var2, @Nullable PacketHandler var3, boolean var4);

    public void cancelServerbound(SU var1);

    default public boolean hasRegisteredClientbound(CU packetType) {
        return this.hasRegisteredClientbound(packetType.state(), packetType.getId());
    }

    default public boolean hasRegisteredServerbound(SU packetType) {
        return this.hasRegisteredServerbound(packetType.state(), packetType.getId());
    }

    public boolean hasRegisteredClientbound(State var1, int var2);

    public boolean hasRegisteredServerbound(State var1, int var2);

    public void appendClientbound(CU var1, PacketHandler var2);

    public void appendServerbound(SU var1, PacketHandler var2);

    public void transform(Direction var1, State var2, PacketWrapper var3) throws InformativeException, CancelException;

    public PacketTypesProvider<CU, CM, SM, SU> getPacketTypesProvider();

    @Deprecated
    public <T> @Nullable T get(Class<T> var1);

    @Deprecated
    public void put(Object var1);

    public void initialize();

    default public boolean hasMappingDataToLoad() {
        return this.getMappingData() != null;
    }

    public void loadMappingData();

    default public void register(ViaProviders providers) {
    }

    default public void init(UserConnection connection) {
    }

    default public @Nullable MappingData getMappingData() {
        return null;
    }

    public ProtocolLogger getLogger();

    default public @Nullable EntityRewriter<?> getEntityRewriter() {
        return null;
    }

    default public @Nullable ItemRewriter<?> getItemRewriter() {
        return null;
    }

    default public @Nullable TagRewriter getTagRewriter() {
        return null;
    }

    default public @Nullable ComponentRewriter getComponentRewriter() {
        return null;
    }

    default public boolean isBaseProtocol() {
        return false;
    }
}

