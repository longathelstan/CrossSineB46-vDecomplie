/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_3_0_1tob1_4_0_1;

import com.viaversion.viaversion.api.connection.UserConnection;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.beta.b1_3_0_1tob1_4_0_1.packet.ClientboundPacketsb1_3;
import net.raphimc.vialegacy.protocol.beta.b1_4_0_1tob1_5_0_2.packet.ClientboundPacketsb1_4;
import net.raphimc.vialegacy.protocol.beta.b1_4_0_1tob1_5_0_2.packet.ServerboundPacketsb1_4;

public class Protocolb1_3_0_1Tob1_4_0_1
extends StatelessProtocol<ClientboundPacketsb1_3, ClientboundPacketsb1_4, ServerboundPacketsb1_4, ServerboundPacketsb1_4> {
    public Protocolb1_3_0_1Tob1_4_0_1() {
        super(ClientboundPacketsb1_3.class, ClientboundPacketsb1_4.class, ServerboundPacketsb1_4.class, ServerboundPacketsb1_4.class);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolb1_3_0_1Tob1_4_0_1.class, ClientboundPacketsb1_3::getPacket));
    }
}

