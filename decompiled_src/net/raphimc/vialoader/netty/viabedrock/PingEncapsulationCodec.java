/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialoader.netty.viabedrock;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import java.net.InetSocketAddress;
import java.util.List;
import org.cloudburstmc.netty.channel.raknet.RakPing;
import org.cloudburstmc.netty.channel.raknet.RakPong;

public class PingEncapsulationCodec
extends MessageToMessageCodec<RakPong, ByteBuf> {
    private final InetSocketAddress remoteAddress;

    public PingEncapsulationCodec(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    protected void encode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        short packetId = in.readUnsignedByte();
        if (packetId != 1) {
            ctx.close();
            short s = packetId;
            throw new IllegalStateException("Unexpected packet ID: " + s);
        }
        out.add(new RakPing(in.readLong(), this.remoteAddress));
    }

    protected void decode(ChannelHandlerContext ctx, RakPong in, List<Object> out) {
        if (!this.remoteAddress.equals(in.getSender())) {
            ctx.close();
            InetSocketAddress inetSocketAddress = in.getSender();
            throw new IllegalStateException("Received pong from unexpected address: " + inetSocketAddress);
        }
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeByte(28);
        buf.writeLong(in.getPingTime());
        buf.writeBytes(in.getPongData());
        out.add(buf);
    }
}

