/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.util;

import org.bukkit.Bukkit;

public final class NMSUtil {
    private static final String BASE = Bukkit.getServer().getClass().getPackage().getName();
    private static final String NMS = BASE.replace("org.bukkit.craftbukkit", "net.minecraft.server");
    private static final boolean DEBUG_PROPERTY = NMSUtil.loadDebugProperty();

    private static boolean loadDebugProperty() {
        try {
            Class<?> serverClass = NMSUtil.nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
            Object server = serverClass.getDeclaredMethod("getServer", new Class[0]).invoke(null, new Object[0]);
            return (Boolean)serverClass.getMethod("isDebugging", new Class[0]).invoke(server, new Object[0]);
        }
        catch (ReflectiveOperationException e) {
            return false;
        }
    }

    public static Class<?> nms(String className) throws ClassNotFoundException {
        String string = className;
        String string2 = NMS;
        return Class.forName(string2 + "." + string);
    }

    public static Class<?> nms(String className, String fallbackFullClassName) throws ClassNotFoundException {
        try {
            String string = className;
            String string2 = NMS;
            return Class.forName(string2 + "." + string);
        }
        catch (ClassNotFoundException ignored) {
            return Class.forName(fallbackFullClassName);
        }
    }

    public static Class<?> obc(String className) throws ClassNotFoundException {
        String string = className;
        String string2 = BASE;
        return Class.forName(string2 + "." + string);
    }

    public static boolean isDebugPropertySet() {
        return DEBUG_PROPERTY;
    }
}

