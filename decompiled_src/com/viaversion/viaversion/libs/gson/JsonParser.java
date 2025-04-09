/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.gson;

import com.google.errorprone.annotations.InlineMe;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.Strictness;
import com.viaversion.viaversion.libs.gson.internal.Streams;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser {
    @Deprecated
    public JsonParser() {
    }

    public static JsonElement parseString(String json) throws JsonSyntaxException {
        return JsonParser.parseReader(new StringReader(json));
    }

    public static JsonElement parseReader(Reader reader) throws JsonIOException, JsonSyntaxException {
        try {
            JsonReader jsonReader = new JsonReader(reader);
            JsonElement element = JsonParser.parseReader(jsonReader);
            if (!element.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonSyntaxException("Did not consume the entire document.");
            }
            return element;
        }
        catch (MalformedJsonException e) {
            throw new JsonSyntaxException(e);
        }
        catch (IOException e) {
            throw new JsonIOException(e);
        }
        catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    public static JsonElement parseReader(JsonReader reader) throws JsonIOException, JsonSyntaxException {
        Strictness strictness = reader.getStrictness();
        if (strictness == Strictness.LEGACY_STRICT) {
            reader.setStrictness(Strictness.LENIENT);
        }
        try {
            JsonElement jsonElement = Streams.parse(reader);
            return jsonElement;
        }
        catch (StackOverflowError e) {
            throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e);
        }
        catch (OutOfMemoryError e) {
            throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e);
        }
        finally {
            reader.setStrictness(strictness);
        }
    }

    @Deprecated
    @InlineMe(replacement="JsonParser.parseString(json)", imports={"com.viaversion.viaversion.libs.gson.JsonParser"})
    public JsonElement parse(String json) throws JsonSyntaxException {
        return JsonParser.parseString(json);
    }

    @Deprecated
    @InlineMe(replacement="JsonParser.parseReader(json)", imports={"com.viaversion.viaversion.libs.gson.JsonParser"})
    public JsonElement parse(Reader json) throws JsonIOException, JsonSyntaxException {
        return JsonParser.parseReader(json);
    }

    @Deprecated
    @InlineMe(replacement="JsonParser.parseReader(json)", imports={"com.viaversion.viaversion.libs.gson.JsonParser"})
    public JsonElement parse(JsonReader json) throws JsonIOException, JsonSyntaxException {
        return JsonParser.parseReader(json);
    }
}

