/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_0_20a_27toc0_28_30;

import com.viaversion.viaversion.api.connection.UserConnection;
import net.raphimc.vialegacy.api.LegacyProtocolVersion;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.classic.c0_0_20a_27toc0_28_30.packet.ClientboundPacketsc0_20a;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.packet.ClientboundPacketsc0_28;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.packet.ServerboundPacketsc0_28;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicBlockRemapper;

public class Protocolc0_0_20a_27Toc0_28_30
extends StatelessProtocol<ClientboundPacketsc0_20a, ClientboundPacketsc0_28, ServerboundPacketsc0_28, ServerboundPacketsc0_28> {
    public Protocolc0_0_20a_27Toc0_28_30() {
        super(ClientboundPacketsc0_20a.class, ClientboundPacketsc0_28.class, ServerboundPacketsc0_28.class, ServerboundPacketsc0_28.class);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolc0_0_20a_27Toc0_28_30.class, ClientboundPacketsc0_20a::getPacket));
        ClassicBlockRemapper previousRemapper = userConnection.get(ClassicBlockRemapper.class);
        userConnection.put(new ClassicBlockRemapper(previousRemapper.mapper(), o -> {
            int block = previousRemapper.reverseMapper().getInt(o);
            if (userConnection.getProtocolInfo().serverProtocolVersion().equals(LegacyProtocolVersion.c0_0_20ac0_27) && (block == 14 || block == 15 || block == 16 || block == 44 || block == 45 || block == 46 || block == 47 || block == 48 || block == 49)) {
                block = 1;
            }
            return block;
        }));
    }
}

