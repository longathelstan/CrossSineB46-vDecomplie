/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_6_0_6tob1_7_0_3;

import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.packet.ClientboundPacketsb1_7;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.packet.ServerboundPacketsb1_7;

public class Protocolb1_6_0_6Tob1_7_0_3
extends StatelessProtocol<ClientboundPacketsb1_7, ClientboundPacketsb1_7, ServerboundPacketsb1_7, ServerboundPacketsb1_7> {
    public Protocolb1_6_0_6Tob1_7_0_3() {
        super(ClientboundPacketsb1_7.class, ClientboundPacketsb1_7.class, ServerboundPacketsb1_7.class, ServerboundPacketsb1_7.class);
    }
}

