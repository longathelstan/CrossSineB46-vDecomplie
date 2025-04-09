/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.netty;

import com.google.common.collect.EvictingQueue;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.splitter.PreNettyPacketType;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;

public class PreNettyLengthPrepender
extends ByteToMessageDecoder {
    protected final UserConnection user;
    private final EvictingQueue<Integer> lastPackets = EvictingQueue.create((int)8);

    public PreNettyLengthPrepender(UserConnection user) {
        this.user = user;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (!in.isReadable() || in.readableBytes() <= 0) {
            return;
        }
        PreNettySplitter splitter = this.user.get(PreNettySplitter.class);
        if (splitter == null) {
            ViaLegacy.getPlatform().getLogger().severe("Received data, but no splitter is set");
            return;
        }
        while (in.readableBytes() > 0) {
            in.markReaderIndex();
            short packetId = in.readUnsignedByte();
            PreNettyPacketType packetType = splitter.getPacketType(packetId);
            if (packetType == null) {
                String string = splitter.getProtocolName();
                short s = packetId;
                ViaLegacy.getPlatform().getLogger().severe("Encountered undefined packet: " + s + " in " + string);
                ViaLegacy.getPlatform().getLogger().severe(ByteBufUtil.hexDump((ByteBuf)in.readSlice(in.readableBytes())));
                EvictingQueue<Integer> evictingQueue = this.lastPackets;
                ViaLegacy.getPlatform().getLogger().severe("Last 8 read packet ids: " + evictingQueue);
                ctx.channel().close();
                return;
            }
            this.lastPackets.add((Object)packetId);
            try {
                int begin = in.readerIndex();
                packetType.getPacketReader().accept(this.user, in);
                int length = in.readerIndex() - begin;
                in.readerIndex(begin);
                int totalLength = length;
                for (int i = 1; i < 5; ++i) {
                    if ((packetId & -1 << i * 7) != 0) continue;
                    totalLength += i;
                    break;
                }
                ByteBuf buf = ctx.alloc().buffer();
                Types.VAR_INT.writePrimitive(buf, totalLength);
                Types.VAR_INT.writePrimitive(buf, packetId);
                buf.writeBytes(in.readSlice(length));
                out.add(buf);
            }
            catch (IndexOutOfBoundsException e) {
                in.resetReaderIndex();
                return;
            }
        }
    }
}

