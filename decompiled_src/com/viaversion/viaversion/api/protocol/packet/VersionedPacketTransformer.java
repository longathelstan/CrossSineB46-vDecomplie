/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.exception.InformativeException;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface VersionedPacketTransformer<C extends ClientboundPacketType, S extends ServerboundPacketType> {
    public boolean send(PacketWrapper var1) throws InformativeException;

    public boolean send(UserConnection var1, C var2, Consumer<PacketWrapper> var3) throws InformativeException;

    public boolean send(UserConnection var1, S var2, Consumer<PacketWrapper> var3) throws InformativeException;

    public boolean scheduleSend(PacketWrapper var1) throws InformativeException;

    public boolean scheduleSend(UserConnection var1, C var2, Consumer<PacketWrapper> var3) throws InformativeException;

    public boolean scheduleSend(UserConnection var1, S var2, Consumer<PacketWrapper> var3) throws InformativeException;

    public @Nullable PacketWrapper transform(PacketWrapper var1) throws InformativeException;

    public @Nullable PacketWrapper transform(UserConnection var1, C var2, Consumer<PacketWrapper> var3) throws InformativeException;

    public @Nullable PacketWrapper transform(UserConnection var1, S var2, Consumer<PacketWrapper> var3) throws InformativeException;
}

