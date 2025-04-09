/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.bukkit.handlers.BukkitChannelInitializer;
import com.viaversion.viaversion.bukkit.platform.BukkitViaInjector;
import io.netty.channel.Channel;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;

public final class PaperViaInjector {
    public static final boolean PAPER_INJECTION_METHOD = PaperViaInjector.hasPaperInjectionMethod();
    public static final boolean PAPER_PROTOCOL_METHOD = PaperViaInjector.hasServerProtocolMethod();
    public static final boolean PAPER_PACKET_LIMITER = PaperViaInjector.hasPacketLimiter();
    public static final boolean PAPER_IS_STOPPING_METHOD = PaperViaInjector.hasIsStoppingMethod();

    private PaperViaInjector() {
    }

    public static int getServerProtocolVersion() {
        if (!PAPER_PROTOCOL_METHOD) {
            throw new UnsupportedOperationException("Paper method not available");
        }
        return Bukkit.getUnsafe().getProtocolVersion();
    }

    public static void setPaperChannelInitializeListener() throws ReflectiveOperationException {
        Class<?> listenerClass = Class.forName("io.papermc.paper.network.ChannelInitializeListener");
        Object channelInitializeListener = Proxy.newProxyInstance(BukkitViaInjector.class.getClassLoader(), new Class[]{listenerClass}, (proxy, method, args2) -> {
            if (method.getName().equals("afterInitChannel")) {
                BukkitChannelInitializer.afterChannelInitialize((Channel)args2[0]);
                return null;
            }
            return method.invoke(proxy, args2);
        });
        Class<?> holderClass = Class.forName("io.papermc.paper.network.ChannelInitializeListenerHolder");
        Method addListenerMethod = holderClass.getDeclaredMethod("addListener", Key.class, listenerClass);
        addListenerMethod.invoke(null, Key.key((String)"viaversion", (String)"injector"), channelInitializeListener);
    }

    public static void removePaperChannelInitializeListener() throws ReflectiveOperationException {
        Class<?> holderClass = Class.forName("io.papermc.paper.network.ChannelInitializeListenerHolder");
        Method addListenerMethod = holderClass.getDeclaredMethod("removeListener", Key.class);
        addListenerMethod.invoke(null, Key.key((String)"viaversion", (String)"injector"));
    }

    private static boolean hasServerProtocolMethod() {
        try {
            Class.forName("org.bukkit.UnsafeValues").getDeclaredMethod("getProtocolVersion", new Class[0]);
            return true;
        }
        catch (ClassNotFoundException | NoSuchMethodException e) {
            return false;
        }
    }

    private static boolean hasPaperInjectionMethod() {
        return PaperViaInjector.hasClass("io.papermc.paper.network.ChannelInitializeListener");
    }

    private static boolean hasIsStoppingMethod() {
        try {
            Bukkit.class.getDeclaredMethod("isStopping", new Class[0]);
            return true;
        }
        catch (NoSuchMethodException e) {
            return false;
        }
    }

    private static boolean hasPacketLimiter() {
        return PaperViaInjector.hasClass("com.destroystokyo.paper.PaperConfig$PacketLimit") || PaperViaInjector.hasClass("io.papermc.paper.configuration.GlobalConfiguration$PacketLimiter");
    }

    public static boolean hasClass(String className) {
        try {
            Class.forName(className);
            return true;
        }
        catch (ClassNotFoundException e) {
            return false;
        }
    }
}

