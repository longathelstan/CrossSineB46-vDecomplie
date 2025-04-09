/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_9to1_8.cooldown;

import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viarewind.protocol.v1_9to1_8.cooldown.CooldownVisualization;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;

public class ActionBarVisualization
implements CooldownVisualization {
    private final UserConnection user;

    public ActionBarVisualization(UserConnection user) {
        this.user = user;
    }

    @Override
    public void show(double progress) throws Exception {
        this.sendActionBar(CooldownVisualization.buildProgressText("\u25a0", progress));
    }

    @Override
    public void hide() throws Exception {
        this.sendActionBar("\u00a7r");
    }

    private void sendActionBar(String bar) {
        PacketWrapper actionBarPacket = PacketWrapper.create(ClientboundPackets1_8.CHAT, this.user);
        actionBarPacket.write(Types.COMPONENT, new JsonPrimitive(bar));
        actionBarPacket.write(Types.BYTE, (byte)2);
        actionBarPacket.scheduleSend(Protocol1_9To1_8.class);
    }
}

