/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_0_1_1tob1_1_2;

import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.packet.ClientboundPacketsb1_1;
import net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.packet.ServerboundPacketsb1_1;

public class Protocolb1_0_1_1Tob1_1_2
extends StatelessProtocol<ClientboundPacketsb1_1, ClientboundPacketsb1_1, ServerboundPacketsb1_1, ServerboundPacketsb1_1> {
    public Protocolb1_0_1_1Tob1_1_2() {
        super(ClientboundPacketsb1_1.class, ClientboundPacketsb1_1.class, ServerboundPacketsb1_1.class, ServerboundPacketsb1_1.class);
    }
}

