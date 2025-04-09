/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.api.minecraft.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ForwardMessageToByteEncoder
extends MessageToByteEncoder<ByteBuf> {
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
        out.writeBytes(msg);
    }
}

