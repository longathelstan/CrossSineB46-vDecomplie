/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20to1_20_2.storage;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ServerboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.Protocol1_20To1_20_2;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ConfigurationState
implements StorableObject {
    static final QueuedPacket[] EMPTY_PACKET_ARRAY = new QueuedPacket[0];
    final List<QueuedPacket> packetQueue = new ArrayList<QueuedPacket>();
    BridgePhase bridgePhase = BridgePhase.NONE;
    QueuedPacket joinGamePacket;
    boolean queuedJoinGame;
    CompoundTag lastDimensionRegistry;
    ClientInformation clientInformation;

    public BridgePhase bridgePhase() {
        return this.bridgePhase;
    }

    public void setBridgePhase(BridgePhase bridgePhase) {
        this.bridgePhase = bridgePhase;
    }

    public @Nullable CompoundTag lastDimensionRegistry() {
        return this.lastDimensionRegistry;
    }

    public boolean setLastDimensionRegistry(CompoundTag dimensionRegistry) {
        boolean equals = Objects.equals(this.lastDimensionRegistry, dimensionRegistry);
        this.lastDimensionRegistry = dimensionRegistry;
        return !equals;
    }

    public void setClientInformation(ClientInformation clientInformation) {
        this.clientInformation = clientInformation;
    }

    public void addPacketToQueue(PacketWrapper wrapper, boolean clientbound) {
        this.packetQueue.add(this.toQueuedPacket(wrapper, clientbound, false));
    }

    QueuedPacket toQueuedPacket(PacketWrapper wrapper, boolean clientbound, boolean skipCurrentPipeline) {
        ByteBuf copy = Unpooled.buffer();
        PacketType packetType = wrapper.getPacketType();
        int packetId = wrapper.getId();
        wrapper.setId(-1);
        wrapper.writeToBuffer(copy);
        return new QueuedPacket(copy, clientbound, packetType, packetId, skipCurrentPipeline);
    }

    public void setJoinGamePacket(PacketWrapper wrapper) {
        this.joinGamePacket = this.toQueuedPacket(wrapper, true, true);
        this.queuedJoinGame = true;
    }

    @Override
    public boolean clearOnServerSwitch() {
        return false;
    }

    @Override
    public void onRemove() {
        for (QueuedPacket packet : this.packetQueue) {
            packet.buf().release();
        }
        if (this.joinGamePacket != null) {
            this.joinGamePacket.buf().release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void sendQueuedPackets(UserConnection connection) {
        PacketWrapper clientInformationPacket;
        boolean hasJoinGamePacket;
        boolean bl = hasJoinGamePacket = this.joinGamePacket != null;
        if (hasJoinGamePacket) {
            this.packetQueue.add(0, this.joinGamePacket);
            this.joinGamePacket = null;
        }
        if ((clientInformationPacket = this.clientInformationPacket(connection)) != null) {
            this.packetQueue.add(hasJoinGamePacket ? 1 : 0, this.toQueuedPacket(clientInformationPacket, false, true));
        }
        QueuedPacket[] queuedPackets = this.packetQueue.toArray(EMPTY_PACKET_ARRAY);
        this.packetQueue.clear();
        for (QueuedPacket packet : queuedPackets) {
            try {
                PacketWrapper queuedWrapper = packet.packetType() != null ? PacketWrapper.create(packet.packetType(), packet.buf(), connection) : PacketWrapper.create(packet.packetId(), packet.buf(), connection);
                if (packet.clientbound()) {
                    queuedWrapper.send(Protocol1_20To1_20_2.class, packet.skipCurrentPipeline());
                    continue;
                }
                queuedWrapper.sendToServer(Protocol1_20To1_20_2.class, packet.skipCurrentPipeline());
            }
            finally {
                packet.buf().release();
            }
        }
    }

    public void clear() {
        this.packetQueue.clear();
        this.bridgePhase = BridgePhase.NONE;
        this.queuedJoinGame = false;
    }

    public boolean queuedOrSentJoinGame() {
        return this.queuedJoinGame;
    }

    public @Nullable PacketWrapper clientInformationPacket(UserConnection connection) {
        if (this.clientInformation == null) {
            return null;
        }
        PacketWrapper settingsPacket = PacketWrapper.create(ServerboundPackets1_19_4.CLIENT_INFORMATION, connection);
        settingsPacket.write(Types.STRING, this.clientInformation.language);
        settingsPacket.write(Types.BYTE, this.clientInformation.viewDistance);
        settingsPacket.write(Types.VAR_INT, this.clientInformation.chatVisibility);
        settingsPacket.write(Types.BOOLEAN, this.clientInformation.showChatColors);
        settingsPacket.write(Types.UNSIGNED_BYTE, this.clientInformation.modelCustomization);
        settingsPacket.write(Types.VAR_INT, this.clientInformation.mainHand);
        settingsPacket.write(Types.BOOLEAN, this.clientInformation.textFiltering);
        settingsPacket.write(Types.BOOLEAN, this.clientInformation.allowListing);
        return settingsPacket;
    }

    public static enum BridgePhase {
        NONE,
        PROFILE_SENT,
        CONFIGURATION,
        REENTERING_CONFIGURATION;

    }

    public static final class ClientInformation {
        final String language;
        final byte viewDistance;
        final int chatVisibility;
        final boolean showChatColors;
        final short modelCustomization;
        final int mainHand;
        final boolean textFiltering;
        final boolean allowListing;

        public ClientInformation(String language, byte viewDistance, int chatVisibility, boolean showChatColors, short modelCustomization, int mainHand, boolean textFiltering, boolean allowListing) {
            this.language = language;
            this.viewDistance = viewDistance;
            this.chatVisibility = chatVisibility;
            this.showChatColors = showChatColors;
            this.modelCustomization = modelCustomization;
            this.mainHand = mainHand;
            this.textFiltering = textFiltering;
            this.allowListing = allowListing;
        }
    }

    public static final class QueuedPacket {
        final ByteBuf buf;
        final boolean clientbound;
        final PacketType packetType;
        final int packetId;
        final boolean skipCurrentPipeline;

        QueuedPacket(ByteBuf buf, boolean clientbound, PacketType packetType, int packetId, boolean skipCurrentPipeline) {
            this.buf = buf;
            this.clientbound = clientbound;
            this.packetType = packetType;
            this.packetId = packetId;
            this.skipCurrentPipeline = skipCurrentPipeline;
        }

        public ByteBuf buf() {
            return this.buf;
        }

        public boolean clientbound() {
            return this.clientbound;
        }

        public int packetId() {
            return this.packetId;
        }

        public @Nullable PacketType packetType() {
            return this.packetType;
        }

        public boolean skipCurrentPipeline() {
            return this.skipCurrentPipeline;
        }
    }
}

