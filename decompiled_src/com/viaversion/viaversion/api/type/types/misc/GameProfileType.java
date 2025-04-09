/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.minecraft.GameProfile;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.util.UUID;

public final class GameProfileType
extends Type<GameProfile> {
    public GameProfileType() {
        super(GameProfile.class);
    }

    @Override
    public GameProfile read(ByteBuf buffer) {
        String name = (String)Types.OPTIONAL_STRING.read(buffer);
        UUID id = (UUID)Types.OPTIONAL_UUID.read(buffer);
        int propertyCount = Types.VAR_INT.readPrimitive(buffer);
        GameProfile.Property[] properties = new GameProfile.Property[propertyCount];
        for (int i = 0; i < propertyCount; ++i) {
            String propertyName = (String)Types.STRING.read(buffer);
            String propertyValue = (String)Types.STRING.read(buffer);
            String propertySignature = (String)Types.OPTIONAL_STRING.read(buffer);
            properties[i] = new GameProfile.Property(propertyName, propertyValue, propertySignature);
        }
        return new GameProfile(name, id, properties);
    }

    @Override
    public void write(ByteBuf buffer, GameProfile value) {
        Types.OPTIONAL_STRING.write(buffer, value.name());
        Types.OPTIONAL_UUID.write(buffer, value.id());
        Types.VAR_INT.writePrimitive(buffer, value.properties().length);
        for (GameProfile.Property property : value.properties()) {
            Types.STRING.write(buffer, property.name());
            Types.STRING.write(buffer, property.value());
            Types.OPTIONAL_STRING.write(buffer, property.signature());
        }
    }
}

