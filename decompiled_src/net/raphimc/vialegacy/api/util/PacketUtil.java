/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.api.util;

import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class PacketUtil {
    public static int calculateLength(PacketWrapper wrapper) {
        PacketWrapperImpl impl;
        PacketType packetType = wrapper.getPacketType();
        wrapper.setPacketType(null);
        ByteBuf lengthBuffer = Unpooled.buffer();
        if (wrapper instanceof PacketWrapperImpl && (impl = (PacketWrapperImpl)wrapper).getInputBuffer() != null) {
            impl.getInputBuffer().markReaderIndex();
        }
        wrapper.writeToBuffer(lengthBuffer);
        if (wrapper instanceof PacketWrapperImpl && (impl = (PacketWrapperImpl)wrapper).getInputBuffer() != null) {
            impl.getInputBuffer().resetReaderIndex();
        }
        int length = lengthBuffer.readableBytes();
        lengthBuffer.release();
        wrapper.setPacketType(packetType);
        return length;
    }
}

