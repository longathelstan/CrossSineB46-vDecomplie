/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20_2to1_20.storage;

import com.google.common.base.Preconditions;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.protocol.v1_20_2to1_20.Protocol1_20_2To1_20;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ConfigurationPacketStorage
implements StorableObject {
    final List<QueuedPacket> rawPackets = new ArrayList<QueuedPacket>();
    CompoundTag registry;
    String[] enabledFeatures;
    boolean finished;
    QueuedPacket resourcePack;

    public void setResourcePack(PacketWrapper wrapper) {
        this.resourcePack = this.toQueuedPacket(wrapper, ClientboundPackets1_19_4.RESOURCE_PACK);
    }

    public CompoundTag registry() {
        Preconditions.checkNotNull((Object)this.registry);
        return this.registry;
    }

    public void setRegistry(CompoundTag registry) {
        this.registry = registry;
    }

    public String @Nullable [] enabledFeatures() {
        return this.enabledFeatures;
    }

    public void setEnabledFeatures(String[] enabledFeatures) {
        this.enabledFeatures = enabledFeatures;
    }

    public void addRawPacket(PacketWrapper wrapper, PacketType type) {
        this.rawPackets.add(this.toQueuedPacket(wrapper, type));
    }

    QueuedPacket toQueuedPacket(PacketWrapper wrapper, PacketType type) {
        Preconditions.checkArgument((!wrapper.isCancelled() ? 1 : 0) != 0, (Object)"Wrapper should be cancelled AFTER calling toQueuedPacket");
        ByteBuf buf = Unpooled.buffer();
        wrapper.setId(-1);
        wrapper.writeToBuffer(buf);
        return new QueuedPacket(buf, type);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void sendQueuedPackets(UserConnection connection) {
        List<QueuedPacket> packets = this.rawPackets;
        if (this.resourcePack != null) {
            packets = new ArrayList<QueuedPacket>(this.rawPackets);
            packets.add(this.resourcePack);
            this.resourcePack = null;
        }
        for (QueuedPacket queuedPacket : packets) {
            ByteBuf buf = queuedPacket.buf().copy();
            try {
                PacketWrapper packet = PacketWrapper.create(queuedPacket.packetType(), buf, connection);
                packet.send(Protocol1_20_2To1_20.class);
            }
            finally {
                buf.release();
            }
        }
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public static final class QueuedPacket {
        final ByteBuf buf;
        final PacketType packetType;

        public QueuedPacket(ByteBuf buf, PacketType packetType) {
            this.buf = buf;
            this.packetType = packetType;
        }

        public ByteBuf buf() {
            return this.buf;
        }

        public PacketType packetType() {
            return this.packetType;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof QueuedPacket)) {
                return false;
            }
            QueuedPacket queuedPacket = (QueuedPacket)object;
            return Objects.equals(this.buf, queuedPacket.buf) && Objects.equals(this.packetType, queuedPacket.packetType);
        }

        public int hashCode() {
            return (0 * 31 + Objects.hashCode(this.buf)) * 31 + Objects.hashCode(this.packetType);
        }

        public String toString() {
            return String.format("%s[buf=%s, packetType=%s]", this.getClass().getSimpleName(), Objects.toString(this.buf), Objects.toString(this.packetType));
        }
    }
}

