/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.task;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.Protocolc0_30cpeToc0_28_30;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.data.ClassicProtocolExtension;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.packet.ServerboundPacketsc0_30cpe;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.storage.ExtensionProtocolMetadataStorage;

public class ClassicPingTask
implements Runnable {
    @Override
    public void run() {
        for (UserConnection info : Via.getManager().getConnectionManager().getConnections()) {
            ExtensionProtocolMetadataStorage protocolMetadata = info.get(ExtensionProtocolMetadataStorage.class);
            if (protocolMetadata == null) continue;
            if (!protocolMetadata.hasServerExtension(ClassicProtocolExtension.TWO_WAY_PING, 1)) continue;
            info.getChannel().eventLoop().submit(() -> {
                if (!info.getChannel().isActive()) {
                    return;
                }
                try {
                    PacketWrapper pingRequest = PacketWrapper.create(ServerboundPacketsc0_30cpe.EXT_TWO_WAY_PING, info);
                    pingRequest.write(Types.BYTE, (byte)0);
                    pingRequest.write(Types.SHORT, (short)(ThreadLocalRandom.current().nextInt() % Short.MAX_VALUE));
                    pingRequest.sendToServer(Protocolc0_30cpeToc0_28_30.class);
                }
                catch (Throwable e) {
                    ViaLegacy.getPlatform().getLogger().log(Level.WARNING, "Error sending TwoWayPing extension ping packet", e);
                }
            });
        }
    }
}

