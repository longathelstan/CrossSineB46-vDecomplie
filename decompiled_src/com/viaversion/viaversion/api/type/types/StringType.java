/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.nio.charset.StandardCharsets;

public class StringType
extends Type<String> {
    static final int MAX_CHAR_UTF_8_LENGTH = Character.toString('\uffff').getBytes(StandardCharsets.UTF_8).length;
    final int maxLength;

    public StringType() {
        this(Short.MAX_VALUE);
    }

    public StringType(int maxLength) {
        super(String.class);
        this.maxLength = maxLength;
    }

    @Override
    public String read(ByteBuf buffer) {
        int len = Types.VAR_INT.readPrimitive(buffer);
        boolean bl = len <= this.maxLength * MAX_CHAR_UTF_8_LENGTH;
        int n = MAX_CHAR_UTF_8_LENGTH;
        Preconditions.checkArgument((boolean)bl, (String)("Cannot receive string longer than Short.MAX_VALUE * " + n + " bytes (got %s bytes)"), (Object[])new Object[]{len});
        String string = buffer.toString(buffer.readerIndex(), len, StandardCharsets.UTF_8);
        buffer.skipBytes(len);
        Preconditions.checkArgument((string.length() <= this.maxLength ? 1 : 0) != 0, (String)"Cannot receive string longer than Short.MAX_VALUE characters (got %s bytes)", (Object[])new Object[]{string.length()});
        return string;
    }

    @Override
    public void write(ByteBuf buffer, String object) {
        if (object.length() > this.maxLength) {
            int n = object.length();
            throw new IllegalArgumentException("Cannot send string longer than Short.MAX_VALUE characters (got " + n + " characters)");
        }
        byte[] b = object.getBytes(StandardCharsets.UTF_8);
        Types.VAR_INT.writePrimitive(buffer, b.length);
        buffer.writeBytes(b);
    }

    public static final class OptionalStringType
    extends OptionalType<String> {
        public OptionalStringType() {
            super(Types.STRING);
        }
    }
}

