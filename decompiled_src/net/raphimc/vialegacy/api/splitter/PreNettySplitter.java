/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.api.splitter;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.protocol.Protocol;
import java.util.function.IntFunction;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;

public class PreNettySplitter
implements StorableObject {
    private final IntFunction<PreNettyPacketType> packetTypeSupplier;
    private final Class<? extends Protocol<?, ?, ?, ?>> protocolClass;

    public PreNettySplitter(Class<? extends Protocol<?, ?, ?, ?>> protocolClass, IntFunction<PreNettyPacketType> packetTypeSupplier) {
        this.protocolClass = protocolClass;
        this.packetTypeSupplier = packetTypeSupplier;
    }

    public PreNettyPacketType getPacketType(int packetId) {
        return this.packetTypeSupplier.apply(packetId);
    }

    public String getProtocolName() {
        return this.protocolClass.getSimpleName();
    }
}

