/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.protocol.alpha.a1_0_15toa1_0_16_2.packet.ClientboundPacketsa1_0_15;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.types.Typesb1_7_0_3;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.Protocolc0_28_30Toa1_0_15;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicServerTitleStorage;
import net.raphimc.vialegacy.protocol.release.r1_2_1_3tor1_2_4_5.Protocolr1_2_1_3Tor1_2_4_5;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.packet.ClientboundPackets1_2_4;

public class ClassicOpLevelStorage
extends StoredObject {
    private byte opLevel;
    private final boolean haxEnabled;
    private boolean flying = false;
    private boolean noClip = false;
    private boolean speed = false;
    private boolean respawn = false;

    public ClassicOpLevelStorage(UserConnection user, boolean haxEnabled) {
        super(user);
        this.haxEnabled = haxEnabled;
        if (haxEnabled) {
            this.flying = true;
            this.noClip = true;
            this.speed = true;
            this.respawn = true;
        }
    }

    public void updateHax(boolean flying, boolean noClip, boolean speed, boolean respawn) {
        if (!this.haxEnabled) {
            return;
        }
        boolean changed = this.flying != flying;
        changed |= this.noClip != noClip;
        changed |= this.speed != speed;
        changed |= this.respawn != respawn;
        if (this.flying != flying) {
            this.flying = flying;
            this.updateAbilities();
        }
        this.noClip = noClip;
        this.speed = speed;
        this.respawn = respawn;
        if (changed) {
            String statusMessage = "\u00a76Hack control: ";
            String string = this.flying ? "\u00a7aFlying" : "\u00a7cFlying";
            String string2 = statusMessage;
            String string3 = statusMessage = string2 + string;
            statusMessage = string3 + " ";
            String string4 = this.noClip ? "\u00a7aNoClip" : "\u00a7cNoClip";
            String string5 = statusMessage;
            String string6 = statusMessage = string5 + string4;
            statusMessage = string6 + " ";
            String string7 = this.speed ? "\u00a7aSpeed" : "\u00a7cSpeed";
            String string8 = statusMessage;
            String string9 = statusMessage = string8 + string7;
            statusMessage = string9 + " ";
            String string10 = this.respawn ? "\u00a7aRespawn" : "\u00a7cRespawn";
            String string11 = statusMessage;
            statusMessage = string11 + string10;
            PacketWrapper chatMessage = PacketWrapper.create(ClientboundPacketsa1_0_15.CHAT, this.user());
            chatMessage.write(Typesb1_7_0_3.STRING, statusMessage);
            chatMessage.send(Protocolc0_28_30Toa1_0_15.class);
        }
    }

    public void setOpLevel(byte opLevel) {
        ClassicServerTitleStorage serverTitleStorage;
        this.opLevel = opLevel;
        if (this.haxEnabled && (serverTitleStorage = this.user().get(ClassicServerTitleStorage.class)) != null) {
            this.updateHax(serverTitleStorage.isFlyEffectivelyEnabled(), serverTitleStorage.isNoclipEffectivelyEnabled(), serverTitleStorage.isSpeedEffectivelyEnabled(), serverTitleStorage.isRespawnEffectivelyEnabled());
        }
    }

    public byte getOpLevel() {
        return this.opLevel;
    }

    public void updateAbilities() {
        if (this.user().getProtocolInfo().getPipeline().contains(Protocolr1_2_1_3Tor1_2_4_5.class)) {
            PacketWrapper playerAbilities = PacketWrapper.create(ClientboundPackets1_2_4.PLAYER_ABILITIES, this.user());
            playerAbilities.write(Types.BOOLEAN, true);
            playerAbilities.write(Types.BOOLEAN, false);
            playerAbilities.write(Types.BOOLEAN, this.flying);
            playerAbilities.write(Types.BOOLEAN, true);
            playerAbilities.scheduleSend(Protocolr1_2_1_3Tor1_2_4_5.class);
        }
    }
}

