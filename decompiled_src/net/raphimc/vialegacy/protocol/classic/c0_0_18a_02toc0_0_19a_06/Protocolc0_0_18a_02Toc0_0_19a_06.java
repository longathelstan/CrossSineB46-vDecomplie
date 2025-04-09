/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_0_18a_02toc0_0_19a_06;

import com.viaversion.viaversion.api.connection.UserConnection;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.protocol.classic.c0_0_19a_06toc0_0_20a_27.packet.ClientboundPacketsc0_19a;
import net.raphimc.vialegacy.protocol.classic.c0_0_19a_06toc0_0_20a_27.packet.ServerboundPacketsc0_19a;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicBlockRemapper;

public class Protocolc0_0_18a_02Toc0_0_19a_06
extends StatelessProtocol<ClientboundPacketsc0_19a, ClientboundPacketsc0_19a, ServerboundPacketsc0_19a, ServerboundPacketsc0_19a> {
    public Protocolc0_0_18a_02Toc0_0_19a_06() {
        super(ClientboundPacketsc0_19a.class, ClientboundPacketsc0_19a.class, ServerboundPacketsc0_19a.class, ServerboundPacketsc0_19a.class);
    }

    @Override
    public void init(UserConnection userConnection) {
        ClassicBlockRemapper previousRemapper = userConnection.get(ClassicBlockRemapper.class);
        userConnection.put(new ClassicBlockRemapper(previousRemapper.mapper(), o -> {
            int block = previousRemapper.reverseMapper().getInt(o);
            if (block != 1 && block != 3 && block != 5 && block != 6 && block != 13 && block != 17 && block != 18 && block != 12 && block != 4) {
                block = 1;
            }
            return block;
        }));
    }
}

