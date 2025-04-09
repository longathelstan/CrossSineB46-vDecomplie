/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.StringType;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import io.netty.buffer.ByteBuf;

public class ComponentType
extends Type<JsonElement> {
    static final StringType STRING_TAG = new StringType(262144);

    public ComponentType() {
        super(JsonElement.class);
    }

    @Override
    public JsonElement read(ByteBuf buffer) {
        String s = STRING_TAG.read(buffer);
        try {
            return JsonParser.parseString(s);
        }
        catch (JsonSyntaxException e) {
            if (Via.getManager().isDebug()) {
                String string = s;
                Via.getPlatform().getLogger().severe("Error when trying to parse json: " + string);
            }
            throw e;
        }
    }

    @Override
    public void write(ByteBuf buffer, JsonElement object) {
        STRING_TAG.write(buffer, object.toString());
    }

    public static final class OptionalComponentType
    extends OptionalType<JsonElement> {
        public OptionalComponentType() {
            super(Types.COMPONENT);
        }
    }
}

