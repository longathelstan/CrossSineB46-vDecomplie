/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.storage;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.Protocolr1_7_6_10Tor1_8;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.GameProfile;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.TabListEntry;

public class TablistStorage
extends StoredObject {
    public final Map<String, TabListEntry> tablist = new HashMap<String, TabListEntry>();

    public TablistStorage(UserConnection user) {
        super(user);
    }

    public void sendTempEntry(TabListEntry entry) {
        entry.ping = -1;
        this.sendAddEntry(entry);
        Via.getPlatform().runSync(() -> this.sendRemoveEntry(entry), entry.gameProfile.isOffline() ? 2L : 60L);
    }

    public void sendAddEntry(TabListEntry entry) {
        List<GameProfile.Property> gameProfileProperties = entry.gameProfile.getAllProperties();
        PacketWrapper addPlayerListItemPacket = PacketWrapper.create(ClientboundPackets1_8.PLAYER_INFO, this.user());
        addPlayerListItemPacket.write(Types.VAR_INT, 0);
        addPlayerListItemPacket.write(Types.VAR_INT, 1);
        addPlayerListItemPacket.write(Types.UUID, entry.gameProfile.uuid);
        addPlayerListItemPacket.write(Types.STRING, entry.gameProfile.userName);
        addPlayerListItemPacket.write(Types.VAR_INT, gameProfileProperties.size());
        for (GameProfile.Property profileEntry : gameProfileProperties) {
            addPlayerListItemPacket.write(Types.STRING, profileEntry.key);
            addPlayerListItemPacket.write(Types.STRING, profileEntry.value);
            addPlayerListItemPacket.write(Types.OPTIONAL_STRING, profileEntry.signature);
        }
        addPlayerListItemPacket.write(Types.VAR_INT, entry.gameMode);
        addPlayerListItemPacket.write(Types.VAR_INT, entry.ping);
        addPlayerListItemPacket.write(Types.OPTIONAL_STRING, null);
        addPlayerListItemPacket.send(Protocolr1_7_6_10Tor1_8.class);
    }

    public void sendRemoveEntry(TabListEntry entry) {
        PacketWrapper removePlayerListItemPacket = PacketWrapper.create(ClientboundPackets1_8.PLAYER_INFO, this.user());
        removePlayerListItemPacket.write(Types.VAR_INT, 4);
        removePlayerListItemPacket.write(Types.VAR_INT, 1);
        removePlayerListItemPacket.write(Types.UUID, entry.gameProfile.uuid);
        removePlayerListItemPacket.send(Protocolr1_7_6_10Tor1_8.class);
    }
}

