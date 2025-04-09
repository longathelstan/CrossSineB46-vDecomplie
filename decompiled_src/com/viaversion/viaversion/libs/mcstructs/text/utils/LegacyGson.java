/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.utils;

import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;

public class LegacyGson {
    private static final String MODERN_ALLOWED_ESCAPES = "utbnrf\n'\"\\/";
    private static final String LENIENT_EXCEPTION = "Use JsonReader.setLenient(true) to accept malformed JSON";

    public static String fixInvalidEscapes(String json) {
        StringBuilder fixedJson = new StringBuilder();
        boolean quote = false;
        char quoteChar = '\u0000';
        boolean escape = false;
        for (char c : json.toCharArray()) {
            if (!escape) {
                if (c == '\"' || c == '\'') {
                    if (!quote) {
                        quote = true;
                        quoteChar = c;
                    } else if (quoteChar == c) {
                        quote = false;
                    }
                } else if (quote && c == '\\') {
                    escape = true;
                }
            } else {
                if (MODERN_ALLOWED_ESCAPES.indexOf(c) == -1) {
                    fixedJson.setLength(fixedJson.length() - 1);
                }
                escape = false;
            }
            fixedJson.append(c);
        }
        return fixedJson.toString();
    }

    public static void checkStartingType(String json, boolean lenient) {
        if (lenient) {
            return;
        }
        char c = LegacyGson.nextNonWhitespace(json);
        if (c == ']' || c == ';' || c == ',' || c == '[' || c == '{') {
            return;
        }
        throw new JsonSyntaxException(new MalformedJsonException(LENIENT_EXCEPTION));
    }

    private static char nextNonWhitespace(String s) {
        char[] chars = s.toCharArray();
        int i = 0;
        while (i < chars.length) {
            char c;
            if ((c = chars[i++]) == '\n' || c == ' ' || c == '\r' || c == '\t') continue;
            if (c == '/' || c == '#') {
                throw new JsonSyntaxException(new MalformedJsonException(LENIENT_EXCEPTION));
            }
            return c;
        }
        return '{';
    }
}

