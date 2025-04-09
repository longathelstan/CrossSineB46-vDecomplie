/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingDataLoader;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class TranslatableRewriter<C extends ClientboundPacketType>
extends ComponentRewriter<C> {
    private static final Map<String, Map<String, String>> TRANSLATABLES = new HashMap<String, Map<String, String>>();
    private final Map<String, String> translatables;

    public static void loadTranslatables() {
        if (!TRANSLATABLES.isEmpty()) {
            throw new IllegalStateException("Translatables already loaded!");
        }
        TranslatableRewriter.fillTranslatables(BackwardsMappingDataLoader.INSTANCE.loadFromDataDir("translation-mappings.json"), TRANSLATABLES);
    }

    public static void fillTranslatables(JsonObject jsonObject, Map<String, Map<String, String>> translatables) {
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            HashMap<String, String> versionMappings = new HashMap<String, String>();
            translatables.put(entry.getKey(), versionMappings);
            for (Map.Entry<String, JsonElement> translationEntry : entry.getValue().getAsJsonObject().entrySet()) {
                versionMappings.put(translationEntry.getKey(), translationEntry.getValue().getAsString());
            }
        }
    }

    public TranslatableRewriter(BackwardsProtocol<C, ?, ?, ?> protocol, ComponentRewriter.ReadType type) {
        this(protocol, type, protocol.getClass().getSimpleName().replace("Protocol", "").split("To")[0].replace("_", "."));
    }

    public TranslatableRewriter(BackwardsProtocol<C, ?, ?, ?> protocol, ComponentRewriter.ReadType type, String version) {
        super(protocol, type);
        Map<String, String> translatableMappings = TranslatableRewriter.getTranslatableMappings(version);
        if (translatableMappings == null) {
            String string = version;
            protocol.getLogger().warning("Missing " + string + " translatables!");
            this.translatables = new HashMap<String, String>();
        } else {
            this.translatables = translatableMappings;
        }
    }

    @Override
    protected void handleTranslate(JsonObject root, String translate) {
        String newTranslate = this.mappedTranslationKey(translate);
        if (newTranslate != null) {
            root.addProperty("translate", newTranslate);
        }
    }

    @Override
    protected void handleTranslate(UserConnection connection, CompoundTag parentTag, StringTag translateTag) {
        String newTranslate = this.mappedTranslationKey(translateTag.getValue());
        if (newTranslate != null) {
            parentTag.put("translate", new StringTag(newTranslate));
        }
    }

    public @Nullable String mappedTranslationKey(String translationKey) {
        return this.translatables.get(translationKey);
    }

    public static Map<String, String> getTranslatableMappings(String sectionIdentifier) {
        return TRANSLATABLES.get(sectionIdentifier);
    }
}

