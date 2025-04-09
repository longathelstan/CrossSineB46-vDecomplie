/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialoader.netty.viabedrock;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import java.util.List;
import net.raphimc.viabedrock.ViaBedrock;
import org.cloudburstmc.netty.channel.raknet.RakReliability;
import org.cloudburstmc.netty.channel.raknet.packet.RakMessage;

public class RakMessageEncapsulationCodec
extends MessageToMessageCodec<RakMessage, ByteBuf> {
    private static final int FRAME_ID = 254;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        CompositeByteBuf buf = ctx.alloc().compositeBuffer(2);
        try {
            buf.addComponent(true, ctx.alloc().ioBuffer(1).writeByte(254));
            buf.addComponent(true, msg.retainedSlice());
            out.add(new RakMessage((ByteBuf)buf.retain()));
        }
        finally {
            buf.release();
        }
    }

    protected void decode(ChannelHandlerContext ctx, RakMessage msg, List<Object> out) {
        if (msg.channel() != 0 && msg.reliability() != RakReliability.RELIABLE_ORDERED) {
            return;
        }
        ByteBuf in = msg.content();
        if (!in.isReadable()) {
            return;
        }
        short id = in.readUnsignedByte();
        if (id != 254) {
            short s = id;
            ViaBedrock.getPlatform().getLogger().warning("Received invalid RakNet frame id: " + s);
            return;
        }
        out.add(in.readRetainedSlice(in.readableBytes()));
    }
}

