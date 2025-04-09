/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_11_1to1_12.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.SerializerVersion;

public final class ChatItemRewriter {
    public static void toClient(JsonElement element) {
        block7: {
            block5: {
                JsonObject obj;
                block8: {
                    block6: {
                        if (!(element instanceof JsonObject)) break block5;
                        obj = (JsonObject)element;
                        if (!obj.has("hoverEvent")) break block6;
                        JsonElement jsonElement = obj.get("hoverEvent");
                        if (!(jsonElement instanceof JsonObject)) {
                            return;
                        }
                        JsonObject hoverEvent = (JsonObject)jsonElement;
                        if (!hoverEvent.has("action") || !hoverEvent.has("value")) {
                            return;
                        }
                        String type = hoverEvent.get("action").getAsString();
                        JsonElement value = hoverEvent.get("value");
                        if (type.equals("show_item")) {
                            CompoundTag compound = ComponentUtil.deserializeLegacyShowItem(value, SerializerVersion.V1_8);
                            hoverEvent.addProperty("value", SerializerVersion.V1_12.toSNBT(compound));
                        }
                        break block7;
                    }
                    if (!obj.has("extra")) break block8;
                    ChatItemRewriter.toClient(obj.get("extra"));
                    break block7;
                }
                if (!obj.has("translate") || !obj.has("with")) break block7;
                ChatItemRewriter.toClient(obj.get("with"));
                break block7;
            }
            if (element instanceof JsonArray) {
                JsonArray array = (JsonArray)element;
                for (JsonElement value : array) {
                    ChatItemRewriter.toClient(value);
                }
            }
        }
    }
}

