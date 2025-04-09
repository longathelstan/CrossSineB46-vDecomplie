/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.api.splitter;

import com.viaversion.viaversion.api.connection.UserConnection;
import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;

public interface PreNettyPacketType {
    public BiConsumer<UserConnection, ByteBuf> getPacketReader();
}

