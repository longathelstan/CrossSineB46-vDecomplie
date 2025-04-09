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
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import java.util.UUID;

public class BossBarVisualization
implements CooldownVisualization {
    private final UserConnection user;
    private UUID bossUUID;

    public BossBarVisualization(UserConnection user) {
        this.user = user;
    }

    @Override
    public void show(double progress) throws Exception {
        PacketWrapper setBossbar = PacketWrapper.create(ClientboundPackets1_9.BOSS_EVENT, this.user);
        if (this.bossUUID == null) {
            this.bossUUID = UUID.randomUUID();
            setBossbar.write(Types.UUID, this.bossUUID);
            setBossbar.write(Types.VAR_INT, 0);
            setBossbar.write(Types.COMPONENT, new JsonPrimitive(" "));
            setBossbar.write(Types.FLOAT, Float.valueOf((float)progress));
            setBossbar.write(Types.VAR_INT, 0);
            setBossbar.write(Types.VAR_INT, 0);
            setBossbar.write(Types.UNSIGNED_BYTE, (short)0);
        } else {
            setBossbar.write(Types.UUID, this.bossUUID);
            setBossbar.write(Types.VAR_INT, 2);
            setBossbar.write(Types.FLOAT, Float.valueOf((float)progress));
        }
        setBossbar.send(Protocol1_9To1_8.class, false);
    }

    @Override
    public void hide() throws Exception {
        if (this.bossUUID == null) {
            return;
        }
        PacketWrapper removeBossbar = PacketWrapper.create(ClientboundPackets1_9.BOSS_EVENT, this.user);
        removeBossbar.write(Types.UUID, this.bossUUID);
        removeBossbar.write(Types.VAR_INT, 1);
        removeBossbar.send(Protocol1_9To1_8.class, false);
        this.bossUUID = null;
    }
}

