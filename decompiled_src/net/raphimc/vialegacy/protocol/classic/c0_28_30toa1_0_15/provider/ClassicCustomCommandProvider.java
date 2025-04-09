/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.provider;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import net.raphimc.vialegacy.protocol.alpha.a1_0_15toa1_0_16_2.packet.ClientboundPacketsa1_0_15;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.types.Typesb1_7_0_3;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.Protocolc0_28_30Toa1_0_15;

public class ClassicCustomCommandProvider
implements Provider {
    public boolean handleChatMessage(UserConnection user, String message) {
        return false;
    }

    public void sendFeedback(UserConnection user, String message) {
        PacketWrapper chatMessage = PacketWrapper.create(ClientboundPacketsa1_0_15.CHAT, user);
        chatMessage.write(Typesb1_7_0_3.STRING, message);
        chatMessage.send(Protocolc0_28_30Toa1_0_15.class);
    }
}

