/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_0_0_1tor1_1.rewriter;

public class ChatFilter {
    private static final char[] ALLOWED_CHARACTERS = new char[]{' ', '!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', '\u2302', '\u00c7', '\u00fc', '\u00e9', '\u00e2', '\u00e4', '\u00e0', '\u00e5', '\u00e7', '\u00ea', '\u00eb', '\u00e8', '\u00ef', '\u00ee', '\u00ec', '\u00c4', '\u00c5', '\u00c9', '\u00e6', '\u00c6', '\u00f4', '\u00f6', '\u00f2', '\u00fb', '\u00f9', '\u00ff', '\u00d6', '\u00dc', '\u00f8', '\u00a3', '\u00d8', '\u00d7', '\u0192', '\u00e1', '\u00ed', '\u00f3', '\u00fa', '\u00f1', '\u00d1', '\u00aa', '\u00ba', '\u00bf', '\u00ae', '\u00ac', '\u00bd', '\u00bc', '\u00a1', '\u00ab', '\u00bb', '_', '^', '\''};

    public static String filter(String message) {
        String allowed = new String(ALLOWED_CHARACTERS);
        for (int i = 0; i < message.length(); ++i) {
            String toReplace = Character.toString(message.charAt(i));
            if (allowed.contains(toReplace)) continue;
            message = message.replaceAll(toReplace, "*");
        }
        return message;
    }
}

