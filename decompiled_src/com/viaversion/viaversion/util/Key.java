/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import java.util.regex.Pattern;

public final class Key {
    private static final Pattern PATTERN = Pattern.compile("([0-9a-z_.-]*:)?[0-9a-z_/.-]*");
    private static final int MINECRAFT_NAMESPACE_LENGTH = "minecraft:".length();

    public static String stripNamespace(String identifier) {
        int index2 = identifier.indexOf(58);
        if (index2 == -1) {
            return identifier;
        }
        return identifier.substring(index2 + 1);
    }

    public static String stripMinecraftNamespace(String identifier) {
        if (identifier.startsWith("minecraft:")) {
            return identifier.substring(MINECRAFT_NAMESPACE_LENGTH);
        }
        if (!identifier.isEmpty() && identifier.charAt(0) == ':') {
            return identifier.substring(1);
        }
        return identifier;
    }

    public static boolean equals(String firstIdentifier, String secondIdentifier) {
        return Key.stripMinecraftNamespace(firstIdentifier).equals(Key.stripMinecraftNamespace(secondIdentifier));
    }

    public static String namespaced(String identifier) {
        int index2 = identifier.indexOf(58);
        if (index2 == -1) {
            String string = identifier;
            return "minecraft:" + string;
        }
        if (index2 == 0) {
            String string = identifier;
            return "minecraft" + string;
        }
        return identifier;
    }

    public static boolean isValid(String identifier) {
        return PATTERN.matcher(identifier).matches();
    }
}

