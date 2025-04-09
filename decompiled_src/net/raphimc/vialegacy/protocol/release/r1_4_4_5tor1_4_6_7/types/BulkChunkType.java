/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_4_4_5tor1_4_6_7.types;

import io.netty.buffer.ByteBuf;

public class BulkChunkType
extends net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.BulkChunkType {
    @Override
    protected boolean readHasSkyLight(ByteBuf byteBuf) {
        return true;
    }

    @Override
    protected void writeHasSkyLight(ByteBuf byteBuf, boolean hasSkyLight) {
    }
}

