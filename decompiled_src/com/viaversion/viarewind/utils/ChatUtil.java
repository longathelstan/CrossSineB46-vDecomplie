/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.utils;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import java.util.logging.Level;
import java.util.regex.Pattern;

@Deprecated
public class ChatUtil {
    static final Pattern UNUSED_COLOR_PATTERN = Pattern.compile("(?>(?>\u00a7[0-fk-or])*(\u00a7r|\\Z))|(?>(?>\u00a7[0-f])*(\u00a7[0-f]))");
    static final ComponentRewriter<ClientboundPacketType> LEGACY_REWRITER = new ComponentRewriter<ClientboundPacketType>(null, ComponentRewriter.ReadType.JSON){

        @Override
        protected void handleTranslate(JsonObject object, String translate) {
            String text = Protocol1_12_2To1_13.MAPPINGS.getMojangTranslation().get(translate);
            if (text != null) {
                object.addProperty("translate", text);
            }
        }
    };

    public static String jsonToLegacy(String json) {
        if (json == null || json.equals("null") || json.isEmpty()) {
            return "";
        }
        try {
            return ChatUtil.jsonToLegacy(JsonParser.parseString(json));
        }
        catch (Exception e) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
                String string = json;
                ViaRewind.getPlatform().getLogger().log(Level.WARNING, "Could not convert component to legacy text: " + string, e);
            }
            return "";
        }
    }

    public static String jsonToLegacy(JsonElement component) {
        if (component.isJsonNull() || component.isJsonArray() && component.getAsJsonArray().isEmpty() || component.isJsonObject() && component.getAsJsonObject().isEmpty()) {
            return "";
        }
        if (component.isJsonPrimitive()) {
            return component.getAsString();
        }
        try {
            LEGACY_REWRITER.processText(null, component);
            String legacy = ComponentUtil.jsonToLegacy(component);
            while (legacy.startsWith("\u00a7f")) {
                legacy = legacy.substring(2);
            }
            return legacy;
        }
        catch (Exception ex) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
                JsonElement jsonElement = component;
                ViaRewind.getPlatform().getLogger().log(Level.WARNING, "Could not convert component to legacy text: " + jsonElement, ex);
            }
            return "";
        }
    }

    public static String removeUnusedColor(String legacy, char last) {
        if (legacy == null) {
            return null;
        }
        legacy = UNUSED_COLOR_PATTERN.matcher(legacy).replaceAll("$1$2");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < legacy.length(); ++i) {
            char current = legacy.charAt(i);
            if (current != '\u00a7' || i == legacy.length() - 1) {
                builder.append(current);
                continue;
            }
            if ((current = legacy.charAt(++i)) == last) continue;
            builder.append('\u00a7').append(current);
            last = current;
        }
        return builder.toString();
    }
}

