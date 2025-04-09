/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.protocol.api;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.protocol.version.BaseVersionProvider;
import java.util.Objects;
import net.ccbluex.liquidbounce.protocol.ProtocolBase;
import net.ccbluex.liquidbounce.protocol.api.VFNetworkManager;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

public class ProtocolVersionProvider
extends BaseVersionProvider {
    @Override
    public ProtocolVersion getClosestServerProtocol(UserConnection connection) throws Exception {
        if (connection.isClientSide() && !MinecraftInstance.mc.func_71387_A()) {
            return ((VFNetworkManager)Objects.requireNonNull(connection.getChannel()).attr(ProtocolBase.VF_NETWORK_MANAGER).get()).viaForge$getTrackedVersion();
        }
        return super.getClosestServerProtocol(connection);
    }
}

