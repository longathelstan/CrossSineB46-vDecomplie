/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_5_0_1tor1_5_2;

import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.packet.ClientboundPackets1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.packet.ServerboundPackets1_5_2;

public class Protocolr1_5_0_1Tor1_5_2
extends StatelessProtocol<ClientboundPackets1_5_2, ClientboundPackets1_5_2, ServerboundPackets1_5_2, ServerboundPackets1_5_2> {
    public Protocolr1_5_0_1Tor1_5_2() {
        super(ClientboundPackets1_5_2.class, ClientboundPackets1_5_2.class, ServerboundPackets1_5_2.class, ServerboundPackets1_5_2.class);
    }
}

