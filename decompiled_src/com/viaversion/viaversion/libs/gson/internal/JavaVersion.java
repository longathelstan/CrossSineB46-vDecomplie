/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.gson.internal;

public final class JavaVersion {
    private static final int majorJavaVersion = JavaVersion.determineMajorJavaVersion();

    private static int determineMajorJavaVersion() {
        String javaVersion = System.getProperty("java.version");
        return JavaVersion.parseMajorJavaVersion(javaVersion);
    }

    static int parseMajorJavaVersion(String javaVersion) {
        int version = JavaVersion.parseDotted(javaVersion);
        if (version == -1) {
            version = JavaVersion.extractBeginningInt(javaVersion);
        }
        if (version == -1) {
            return 6;
        }
        return version;
    }

    private static int parseDotted(String javaVersion) {
        try {
            String[] parts = javaVersion.split("[._]", 3);
            int firstVer = Integer.parseInt(parts[0]);
            if (firstVer == 1 && parts.length > 1) {
                return Integer.parseInt(parts[1]);
            }
            return firstVer;
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    private static int extractBeginningInt(String javaVersion) {
        try {
            char c;
            StringBuilder num = new StringBuilder();
            for (int i = 0; i < javaVersion.length() && Character.isDigit(c = javaVersion.charAt(i)); ++i) {
                num.append(c);
            }
            return Integer.parseInt(num.toString());
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    public static int getMajorJavaVersion() {
        return majorJavaVersion;
    }

    public static boolean isJava9OrLater() {
        return majorJavaVersion >= 9;
    }

    private JavaVersion() {
    }
}

