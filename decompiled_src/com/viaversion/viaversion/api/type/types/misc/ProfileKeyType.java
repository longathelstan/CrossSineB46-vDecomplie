/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class ProfileKeyType
extends Type<ProfileKey> {
    public ProfileKeyType() {
        super(ProfileKey.class);
    }

    @Override
    public ProfileKey read(ByteBuf buffer) {
        return new ProfileKey(buffer.readLong(), (byte[])Types.BYTE_ARRAY_PRIMITIVE.read(buffer), (byte[])Types.BYTE_ARRAY_PRIMITIVE.read(buffer));
    }

    @Override
    public void write(ByteBuf buffer, ProfileKey object) {
        buffer.writeLong(object.expiresAt());
        Types.BYTE_ARRAY_PRIMITIVE.write(buffer, object.publicKey());
        Types.BYTE_ARRAY_PRIMITIVE.write(buffer, object.keySignature());
    }

    public static final class OptionalProfileKeyType
    extends OptionalType<ProfileKey> {
        public OptionalProfileKeyType() {
            super(Types.PROFILE_KEY);
        }
    }
}

