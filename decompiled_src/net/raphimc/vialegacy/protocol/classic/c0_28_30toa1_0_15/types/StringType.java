/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.Arrays;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.data.Cp437String;

public class StringType
extends Type<String> {
    public StringType() {
        super(String.class);
    }

    @Override
    public String read(ByteBuf buffer) {
        byte[] stringBytes = new byte[64];
        buffer.readBytes(stringBytes);
        return Cp437String.fromBytes(stringBytes).trim();
    }

    @Override
    public void write(ByteBuf buffer, String s) {
        byte[] bytes = new byte[64];
        byte[] stringBytes = Cp437String.toBytes(s);
        Arrays.fill(bytes, (byte)32);
        System.arraycopy(stringBytes, 0, bytes, 0, Math.min(bytes.length, stringBytes.length));
        buffer.writeBytes(bytes);
    }
}

