/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_6_2tor1_6_4;

import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.packet.ClientboundPackets1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.packet.ServerboundPackets1_6_4;

public class Protocolr1_6_2Tor1_6_4
extends StatelessProtocol<ClientboundPackets1_6_4, ClientboundPackets1_6_4, ServerboundPackets1_6_4, ServerboundPackets1_6_4> {
    public Protocolr1_6_2Tor1_6_4() {
        super(ClientboundPackets1_6_4.class, ClientboundPackets1_6_4.class, ServerboundPackets1_6_4.class, ServerboundPackets1_6_4.class);
    }
}

