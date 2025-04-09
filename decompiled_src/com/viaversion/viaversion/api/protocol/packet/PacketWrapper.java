/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface PacketWrapper {
    public static final int PASSTHROUGH_ID = 1000;

    public static PacketWrapper create(@Nullable PacketType packetType, UserConnection connection) {
        return PacketWrapper.create(packetType, null, connection);
    }

    public static PacketWrapper create(@Nullable PacketType packetType, @Nullable ByteBuf inputBuffer, UserConnection connection) {
        return Via.getManager().getProtocolManager().createPacketWrapper(packetType, inputBuffer, connection);
    }

    @Deprecated
    public static PacketWrapper create(int packetId, @Nullable ByteBuf inputBuffer, UserConnection connection) {
        return Via.getManager().getProtocolManager().createPacketWrapper(packetId, inputBuffer, connection);
    }

    public <T> T get(Type<T> var1, int var2) throws InformativeException;

    @Deprecated
    public boolean is(Type var1, int var2);

    public boolean isReadable(Type var1, int var2);

    public <T> void set(Type<T> var1, int var2, @Nullable T var3) throws InformativeException;

    public <T> T read(Type<T> var1) throws InformativeException;

    public <T> void write(Type<T> var1, @Nullable T var2);

    public <T> T passthrough(Type<T> var1) throws InformativeException;

    public <T> T passthroughAndMap(Type<?> var1, Type<T> var2) throws InformativeException;

    public void passthroughAll() throws InformativeException;

    public void writeToBuffer(ByteBuf var1) throws InformativeException;

    public void clearInputBuffer();

    public void clearPacket();

    default public void send(Class<? extends Protocol> protocol) throws InformativeException {
        this.send(protocol, true);
    }

    public void send(Class<? extends Protocol> var1, boolean var2) throws InformativeException;

    default public void scheduleSend(Class<? extends Protocol> protocol) throws InformativeException {
        this.scheduleSend(protocol, true);
    }

    public void scheduleSend(Class<? extends Protocol> var1, boolean var2) throws InformativeException;

    public ChannelFuture sendFuture(Class<? extends Protocol> var1) throws InformativeException;

    public void sendRaw() throws InformativeException;

    public ChannelFuture sendFutureRaw() throws InformativeException;

    public void scheduleSendRaw() throws InformativeException;

    default public PacketWrapper create(PacketType packetType) {
        return this.create(packetType.getId());
    }

    default public PacketWrapper create(PacketType packetType, PacketHandler handler) throws InformativeException {
        return this.create(packetType.getId(), handler);
    }

    public PacketWrapper create(int var1);

    public PacketWrapper create(int var1, PacketHandler var2) throws InformativeException;

    public void apply(Direction var1, State var2, List<Protocol> var3) throws InformativeException, CancelException;

    public boolean isCancelled();

    default public void cancel() {
        this.setCancelled(true);
    }

    public void setCancelled(boolean var1);

    public UserConnection user();

    public void resetReader();

    public void sendToServerRaw() throws InformativeException;

    public void scheduleSendToServerRaw() throws InformativeException;

    default public void sendToServer(Class<? extends Protocol> protocol) throws InformativeException {
        this.sendToServer(protocol, true);
    }

    public void sendToServer(Class<? extends Protocol> var1, boolean var2) throws InformativeException;

    default public void scheduleSendToServer(Class<? extends Protocol> protocol) throws InformativeException {
        this.scheduleSendToServer(protocol, true);
    }

    public void scheduleSendToServer(Class<? extends Protocol> var1, boolean var2) throws InformativeException;

    public @Nullable PacketType getPacketType();

    public void setPacketType(@Nullable PacketType var1);

    public int getId();

    @Deprecated
    public void setId(int var1);
}

