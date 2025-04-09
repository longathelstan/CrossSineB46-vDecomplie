/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.netty;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PreNettyLengthRemover
extends MessageToByteEncoder<ByteBuf> {
    protected final UserConnection user;

    public PreNettyLengthRemover(UserConnection user) {
        this.user = user;
    }

    protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) {
        Types.VAR_INT.readPrimitive(in);
        out.writeByte(Types.VAR_INT.readPrimitive(in) & 0xFF);
        out.writeBytes(in);
    }
}

